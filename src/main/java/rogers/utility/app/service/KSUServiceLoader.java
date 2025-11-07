package rogers.utility.app.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rogers.utility.app.ksu.entity.ConfigEntity;
import rogers.utility.app.ksu.entity.KsuEntity;
import rogers.utility.app.ksu.entity.OSMOrderTrackerEntity;
import rogers.utility.app.ksu.entity.SomEntity;
import rogers.utility.app.ksu.repo.ConfigRepository;
import rogers.utility.app.ksu.repo.KSUOSMRecordRepository;
import rogers.utility.app.ksu.repo.KSURepository;
import rogers.utility.app.ksu.repo.SOMRepository;
import rogers.utility.app.model.FilterConfig;
import rogers.utility.app.osm.entity.OsmOrderEntity;
import rogers.utility.app.osm.repo.OSMRepository;
import rogers.utility.app.utility.GeneratePOJOFromXml;

@Service
public class KSUServiceLoader {
	private static final Logger logger = LogManager.getLogger(KSUServiceLoader.class);
	private static final int BATCH_SIZE = 1000;
	
	@Autowired
	private OSMRepository osmRepo;

	@Autowired
	private KSURepository ksuRepo;
	
	@Autowired 
	private SOMRepository somRepo;

	@Autowired
	private KSUOSMRecordRepository ksuOsmRepo;

	@Autowired
	private ConfigRepository configRepo;

	FilterConfig config;

	private String url;
	private String urlSecondary;
	private String user;
	private String password;
	

	public KSUServiceLoader() {
		logger.info("Loading KSUServiceLoader");
		this.config = getFilterConfig();
	}

	public FilterConfig getFilterConfig() {
		FilterConfig filterConfig = null;
		// System.out.print("##### osmDetails >> " +url+"---");
		// System.out.println(" ################# " + new
		// File("filterconfig.xml").getAbsolutePath());
		// Resource resource=resourceLoader.getResource("classpath:filterconfig.xml");
		try {

			File file = new File("filterconfig.xml");
			filterConfig = (FilterConfig) GeneratePOJOFromXml.convert(file, FilterConfig.class);
		} catch (Exception e) {

		}
		return filterConfig;
	}
	 public void queryOSMAndUpate(ConfigEntity config2) {
		
		Calendar yesterday = Calendar.getInstance();
		Calendar tomrrow = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		tomrrow.add(Calendar.DATE, 1);
		Calendar startC = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").getCalendar();
		startC.setTimeInMillis(yesterday.getTimeInMillis());
		Calendar endC = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").getCalendar();
		endC.setTimeInMillis(tomrrow.getTimeInMillis());
		
		logger.info("Loading with new config: " + config2);
		List<OsmOrderEntity> osmList = null;
		logger.info("Reading OSM  DB ..." + startC.getTime() + "----" + endC.getTime());
		osmList = osmRepo.findAllCompletedByDateBetween(startC.getTime(), endC.getTime());
	
		int counter = osmList.size();
		logger.warn("Querying " + counter + " Completed Orders");
		int tempCount = 0;
		int totalCount = 0;
	
		logger.info("Filtering Zap and Amend Orders");
		List<OsmOrderEntity> osmListNoZapOrAmend = filterOrders(osmList);

		for (OsmOrderEntity osmEntity : osmListNoZapOrAmend) {
			OSMOrderTrackerEntity okEntity = createKSUOSMBean(osmEntity);
			try {
				int count = ksuOsmRepo.countByOsmId(okEntity.getOsmId());
			
				if (count == 0) {
					ksuOsmRepo.save(okEntity);
					tempCount++;
					totalCount++;
				}
//				else {
//					logger.info("Duplicate OSM ID " + okEntity.getOsmId());					
//				}
			} catch (Exception e) {
				logger.error("Exception in Saving ", e);
			}
			
			if (tempCount >= 500) {
				ksuOsmRepo.flush();
				tempCount = 0;
			}
		}
		logger.debug(totalCount + " orders loaded.");
		
		ksuOsmRepo.flush();
		Timestamp currenttime = new Timestamp(System.currentTimeMillis());
		config2.setLastRunTime(currenttime);
		config2.setStartTime(new Timestamp(startC.getTimeInMillis()));
		config2.setEndTime(currenttime);
		config2.setLocked("OPEN");
		
		logger.info("Config Saved.." + config2);
		
	}
	private OSMOrderTrackerEntity createKSUOSMBean(OsmOrderEntity osmEntity) {

		OSMOrderTrackerEntity entity = new OSMOrderTrackerEntity();
		entity.setCompletedDate(new Timestamp(osmEntity.getOrdCompletionnDate().getTime()));
		entity.setCreateDate(new Timestamp(osmEntity.getOrdCreationDate().getTime()));
		entity.setOsmId(osmEntity.getORDER_SEQ_ID());
		entity.setOrderNumber(osmEntity.getREFERENCE_NUMBER());
		entity.setStatus("LOADED");
		entity.setTaskName("OSMORDERLOADER");
		entity.setKsuStatus("PENDING");
		entity.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));

		return entity;
	}
	
	private List<OsmOrderEntity> filterOrders(List<OsmOrderEntity> osmEntityList) {
	    List<OsmOrderEntity> filteredEntities = new ArrayList<>();
        int zapOrderCount = 0;
        int amendOrderCount = 0;

	    for (int i = 0; i < osmEntityList.size(); i += BATCH_SIZE) {
	        List<OsmOrderEntity> batch = osmEntityList.subList(i, Math.min(i + BATCH_SIZE, osmEntityList.size()));

	        // Map ORDER_SEQ_ID to OsmOrderEntity for quick lookup
	        Map<String, OsmOrderEntity> batchEntityMap = batch.stream()
	            .collect(Collectors.toMap(
	                entity -> entity.getORDER_SEQ_ID().toString(),
	                Function.identity()
	            ));
	        
	        // Extract order IDs for the SOM query
	        List<String> osmOrderIdsList = new ArrayList<>(batchEntityMap.keySet());

	        // Query SOM for order types
	        List<Object[]> batchResults = somRepo.findOrderTypesByOSM_ORDER_IDs(osmOrderIdsList);

	        for (Object[] row : batchResults) {
				
				Object osmOrderIdObj = row[0];
				if (osmOrderIdObj == null) {
				    logger.warn("Skipping row with null OSM_ORDER_ID");
				    continue;
				}
				String osmOrderId = osmOrderIdObj.toString();

	            Object orderTypeObj = row[1];
	            String orderType = orderTypeObj != null ? orderTypeObj.toString() : null;

	            if (orderType != null && orderType.toLowerCase().contains("zap")) {
	                logger.info("Order " + osmOrderId + " is a zap order, continuing...");
	                zapOrderCount++;
	                continue;
	            }

	            // Add to result list only if not a zap order or an amend order
	            OsmOrderEntity entity = batchEntityMap.get(osmOrderId);
	            if (entity != null && entity.getAmendMent() == null) {
	            	filteredEntities.add(entity);
	            }
	            else {
	            	logger.info("Order " + osmOrderId + " is an Amend Order");
	            	amendOrderCount++;
	            }
	        }    
	    }

	    logger.info("Number of zap orders = " + zapOrderCount);
	    logger.info("Number of amend orders = " + amendOrderCount);

	    return filteredEntities;
	}


	public void setConfig(String url1, String url2, String user2, String password2) {
		this.url = url1;
		this.urlSecondary = url2;
		this.user = user2;
		this.password = password2;

	}
}
