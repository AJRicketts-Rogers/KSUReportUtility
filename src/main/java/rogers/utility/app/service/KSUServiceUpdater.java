package rogers.utility.app.service;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import rogers.utility.app.dataconfig.KsuConfig;
import rogers.utility.app.ksu.entity.ConfigEntity;
import rogers.utility.app.ksu.entity.KsuEntity;
import rogers.utility.app.ksu.entity.OSMOrderTrackerEntity;
import rogers.utility.app.ksu.repo.ConfigRepository;
import rogers.utility.app.ksu.repo.KSUOSMRecordRepository;
import rogers.utility.app.ksu.repo.KSURepository;
import rogers.utility.app.model.FilterConfig;
import rogers.utility.app.model.OSMResponseBean;
import rogers.utility.app.model.OrderItem;
import rogers.utility.app.model.OsmBean;
import rogers.utility.app.osm.entity.OsmEntity;
import rogers.utility.app.osm.repo.OSMRepository;
import rogers.utility.app.service.osmws.OSMClient;
import rogers.utility.app.transformer.BeanTransformer;
import rogers.utility.app.transformer.KSUFilterConfig;
import rogers.utility.app.utility.GeneratePOJOFromXml;
import rogers.utility.app.utility.JsonResponseGenerator;
import rogers.utility.app.utility.XmlUtility;

@Service
public class KSUServiceUpdater {
	private static final Logger logger = LogManager.getLogger(KSUServiceUpdater.class);
	@Autowired
	private OSMRepository osmRepo;

	@Autowired
	private KSURepository ksuRepo;

	@Autowired
	private KSUOSMRecordRepository ksuOsmRepo;

	@Autowired
	private ConfigRepository configRepo;

	
	@Autowired
	private KsuConfig ksuJpa;
	
	FilterConfig config;

	private String url;
	private String urlSecondary;
	
	private String user;
	private String password;
	
	private String filterConfigFile;
	
	public void loadFilterConfig(String filterConfigFile) {
		this.filterConfigFile=filterConfigFile;
		this.config = getFilterConfig();
	}

	public FilterConfig getFilterConfig() {
		logger.info("Loading FilterConfig "+filterConfigFile);
		FilterConfig filterConfig = null;
		// System.out.print("##### osmDetails >> " +url+"---");
		// System.out.println(" ################# " + new
		// File("filterconfig.xml").getAbsolutePath());
		// Resource resource=resourceLoader.getResource("classpath:filterconfig.xml");
		try {

			File file = new File(filterConfigFile);
			filterConfig = (FilterConfig) GeneratePOJOFromXml.convert(file, FilterConfig.class);
		} catch (Exception e) {
			logger.error("FilterConfig is not Loaded ",e);
			
		}
		
		return filterConfig;
	}

	private void setFilterConfigBean() throws Exception {

		if(config!=null && config.getCfsList()!=null) {
			
			KSUFilterConfig.config=this.config;
			
		}else {
			throw  new Exception("Filter Config not Set for CFS's");
		}
		
		
	}
	public void queryKSUOSMAndUpate(ConfigEntity configs) throws Exception {

		setFilterConfigBean();		
		List<OSMOrderTrackerEntity> osmKsuList = null;
		osmKsuList = ksuOsmRepo.findOSMOrderTrackerEntityByStatu("LOADED", PageRequest.of(0, configs.getOperationLimit()));
		logger.warn("Querying Tracker DB for Laoded Orders " + osmKsuList.size());

		for (OSMOrderTrackerEntity osmOrderTrackerEntity : osmKsuList) {
			String osmId = "" + osmOrderTrackerEntity.getOsmId();

			logger.debug("Calling Update for " + osmId);
			try {
				OSMResponseBean result = generateResponseBeanStore(osmId);
			
				if (result.getOsmBean() != null) {

					String allPayload = JsonResponseGenerator.generateResponse(result.getOsmBean());
					String rawPayload = JsonResponseGenerator.generateResponse(result.getOsmRawBean());

					osmOrderTrackerEntity.setOperationPayload(rawPayload);
					osmOrderTrackerEntity.setResultPayload(allPayload);
					if (result.getOsmBean().getKsuStatus() != null && result.getOsmBean().getKsuStatus().length() > 0)
						osmOrderTrackerEntity.setKsuStatus(result.getOsmBean().getKsuStatus());
					else
						osmOrderTrackerEntity.setKsuStatus("INCOMPLETE");

					if (result.getErrorDescription() != null) {
						osmOrderTrackerEntity.setStatus("LOADED");
						String errorDesc = result.getErrorDescription();
						if (errorDesc.length() > 500)
							errorDesc = errorDesc.substring(0, 500);
						osmOrderTrackerEntity.setErrorDesc(errorDesc);

					} else {
						osmOrderTrackerEntity.setStatus("COMPLETED");
						osmOrderTrackerEntity.setErrorDesc("SUCCESS");
					}
				} else {
					if (result.getErrorDescription() != null) {
						String errorDesc = result.getErrorDescription();
						if (errorDesc.length() > 500)
							errorDesc = errorDesc.substring(0, 500);
						osmOrderTrackerEntity.setErrorDesc(errorDesc);
						if(errorDesc.equalsIgnoreCase("EMPTYXML")) {
							osmOrderTrackerEntity.setStatus("EMPTYXML");
						}else if(errorDesc.equalsIgnoreCase("WS-ISSUE")) {
							osmOrderTrackerEntity.setStatus("LOADED");
						}else if(errorDesc.equalsIgnoreCase("ORDERNOTFOUND")) {
							osmOrderTrackerEntity.setStatus("ORDERNOTFOUND");
						}else{
							osmOrderTrackerEntity.setStatus("FAILED");
						}
						
						
					} else {
						osmOrderTrackerEntity.setErrorDesc("Unknow Exception");
						osmOrderTrackerEntity.setStatus("LOADED");
					}
					
				}
				ksuOsmRepo.saveAndFlush(osmOrderTrackerEntity);
				
				logger.debug("Updated " + osmId);
			} catch (Exception e) {
				String errorDesc = e.getMessage();
				if (errorDesc != null) {
					if (errorDesc.length() > 500)
						errorDesc = errorDesc.substring(0, 500);
					osmOrderTrackerEntity.setErrorDesc(errorDesc);
				} else {
					osmOrderTrackerEntity.setErrorDesc("Unknow Exception");
				}
				
				
				ksuOsmRepo.saveAndFlush(osmOrderTrackerEntity);
				logger.error("Exception In Mapping",e);	
				

			}
			
			
			if(osmOrderTrackerEntity.getErrorDesc().contains("CONFIGISSUE-CFSLIST")) {
				logger.error("Execution halted due to issue with Config loading!");
				break;
			}

		}

	}



	public OSMResponseBean generateResponseBeanStore(String osmId) throws Exception {
		// new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01")
		OSMResponseBean resposeBean = new OSMResponseBean();
		List<OsmEntity> osmList = null;
		osmList = osmRepo.findOsmId(Integer.parseInt(osmId));

		if (!osmList.isEmpty()) {
			for (OsmEntity oentity : osmList) {
				logger.info("   Order ID   " + oentity.getORDER_SEQ_ID() + "--" + oentity.getREFERENCE_NUMBER());
				OSMClient wsCall = new OSMClient(this.url,this.urlSecondary, this.user, this.password);
				List<KsuEntity> ksuEntities = ksuRepo.findKsuEntitiesByOSM_ORDER_ID(String.valueOf(oentity.getORDER_SEQ_ID()));
				try {
					String responseXml =null;
					try {
						responseXml = wsCall.extractXMl(String.valueOf(oentity.getORDER_SEQ_ID()));
					} catch (Exception e1) {
						logger.error("Exception in  WS Call ",e1);
						resposeBean.setErrorDescription(e1.getMessage());
						logger.warn("Calling Second time with other url");
						try {
							responseXml = wsCall.extractXMlSeconday(String.valueOf(oentity.getORDER_SEQ_ID()));
							resposeBean.setErrorDescription(null);
						} catch (Exception e2) {
							logger.error("Exception in  WS Call Secondary",e2);
							resposeBean.setErrorDescription(e2.getMessage());
						}
					}
					if (responseXml != null && responseXml.length() > 0) {
						HashMap<String, OrderItem> mapper = XmlUtility.readXPath(responseXml);
						if (mapper != null && !mapper.isEmpty()) {
							OsmBean obean = BeanTransformer.convertEtoBean(oentity, ksuEntities);
							try {
								resposeBean.setOsmBean(KSUFilterConfig.generateRenderBean(mapper, obean));
							} catch (Exception e) {
									if(e.getMessage()!=null && e.getMessage().equals("CONFIGISSUE-CFSLIST")) {
										
										logger.error("Config cfs list became null !",e);
										this.config = getFilterConfig();
										setFilterConfigBean();
										logger.warn(" Setting  Config Again");										
										
									}
							}
							if(resposeBean.getOsmBean()==null) {
									resposeBean.setOsmBean(KSUFilterConfig.generateRenderBean(mapper, obean));
								
							}
							resposeBean.setOsmRawBean(obean);
						} else {

							resposeBean.setErrorDescription("EMPTYXML");
						}
					} else {
						resposeBean.setErrorDescription("WS-ISSUE");
					}

					// System.out.println("obeans >> " + obeans);
				} catch (Exception er) {
					logger.error("Exception in Generating KSU ",er);
					resposeBean.setErrorDescription("Unknown Exception : " + er.getMessage());
				}

				logger.debug(" ######## Execution Completed For OSM ID  >> " + oentity.getORDER_SEQ_ID());

			}
		}else {
			resposeBean.setErrorDescription("ORDERNOTFOUND");
		}
		return resposeBean;
	}

	public void setConfig(String url1,String url2, String user2, String password2) {
		this.url = url1;
		this.urlSecondary = url2;
		this.user = user2;
		this.password = password2;

	}
}
