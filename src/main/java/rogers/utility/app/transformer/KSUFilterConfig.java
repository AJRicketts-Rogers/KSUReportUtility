package rogers.utility.app.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rogers.utility.app.model.CFSBean;
import rogers.utility.app.model.FilterConfig;
import rogers.utility.app.model.KsuBean;
import rogers.utility.app.model.MileStoneRenderBean;
import rogers.utility.app.model.OSMRenderBean;
import rogers.utility.app.model.OrderItem;
import rogers.utility.app.model.OsmBean;
import rogers.utility.app.model.ParentServiceRenderBean;
import rogers.utility.app.model.ServiceRenderBean;

public class KSUFilterConfig {
	private static final Logger logger = LogManager.getLogger(KSUFilterConfig.class);

	public static FilterConfig config;
	
	public static OSMRenderBean generateRenderBean(HashMap<String, OrderItem> mapper, OsmBean bean) throws Exception {

		OSMRenderBean fbean = new OSMRenderBean();
		String cbp = null;
		fbean.setRaw(bean);
		if (bean.getKsuList() != null)
			for (KsuBean kbean : bean.getKsuList()) {
				if (kbean.getCbp() != null && kbean.getOrderId() != null) {
					fbean.setCbp(kbean.getCbp());
					fbean.setOrderNumber(kbean.getOrderId());
					break;
				}
			}
		fbean.setOrderAction(bean.getOrderAction());
		fbean.setStatus(bean.getStatus());
		if (bean.getCompletedDateTime() != null && !bean.getCompletedDateTime().contains("9999"))
			fbean.setCompledDate(bean.getCompletedDateTime());
		fbean.setCreatedDate(bean.getCreatedDateTime());

		fbean.setOsmOrderId(bean.getOsmId());
		fbean.setServiceList(generateServiceList(mapper, bean));
		boolean ksuStat = true;
		if (fbean.getServiceList() != null & !fbean.getServiceList().isEmpty()) {
			for (ParentServiceRenderBean pservice : fbean.getServiceList()) {
				for (ServiceRenderBean service : pservice.getChildService()) {
					for (MileStoneRenderBean ms : service.getMilestones()) {
						if (ms.getStatus().equalsIgnoreCase("FAILED")) {
							ksuStat = false;
							break;
						}
					}
				}
			}
		} else {
			ksuStat = false;
			fbean.setErrorDescription("EMPTYSERVICELIST");
		}

		fbean.setKsuStatus(ksuStat ? "COMPLETED" : "FAILED");
		return fbean;
	}

	private static List<ParentServiceRenderBean> generateServiceList(HashMap<String, OrderItem> mapper, OsmBean osm) throws Exception {

		List<ParentServiceRenderBean> serviceList = new ArrayList<>();
		if (mapper != null) {
			for (String pid : mapper.keySet()) {
				OrderItem item = mapper.get(pid);
				ParentServiceRenderBean pbean = new ParentServiceRenderBean();
				pbean.setId(pid);
				pbean.setName(item.getName().toUpperCase());
				pbean.setAction(item.getAction().toUpperCase());
				
				
				for (KsuBean kbean : osm.getKsuList()) {					
					if (kbean.getProductOrderId().equals(pid)) {
						pbean.setUniOfOrder(kbean.getUnitOfOrder());
						break;
					}
					
				}
				
				ArrayList<ServiceRenderBean> slist = new ArrayList<>();
				for (OrderItem child : item.getChildList()) {
					ServiceRenderBean tsbean = new ServiceRenderBean();
					tsbean.setParentName(item.getName().toUpperCase());
					tsbean.setParentAction(item.getAction().toUpperCase());
					tsbean.setAction(child.getAction().toUpperCase());
					tsbean.setId(child.getId());
					tsbean.setParentId(pid);
					tsbean.setName(child.getName().toUpperCase());
					tsbean.setMilestones(createMilestones(tsbean, osm.getKsuList()));
					slist.add(tsbean);
				}
				pbean.setChildService(slist);
				serviceList.add(pbean);
			}
		}

		/*
		 * for (KsuBean kbean:osm.getKsuList()) { String
		 * serviceName=kbean.getServiceName().toUpperCase(); ServiceRenderBean tsbean
		 * =generateConfigService(config,mapper,serviceName,osm); if(tsbean!=null)
		 * serviceList.add(tsbean); }
		 */
		logger.debug("serviceList  >> " + serviceList);

		return serviceList;
	}

	private static List<MileStoneRenderBean> createMilestones(ServiceRenderBean tsbean, List<KsuBean> ksuList) throws Exception {
		ArrayList<MileStoneRenderBean> mbeans = new ArrayList<>();
		logger.debug("Checking for Servicce  >> " + tsbean.getName()+"-"+tsbean.getAction() + "---" + tsbean.getParentName()+"-"+tsbean.getParentAction());
		CFSBean cbean = checkCFSConfig(tsbean);

		if (cbean != null) {
			if (cbean.getKsu() != null) {
				try {
				for (String sbean : cbean.getKsu()) {
					MileStoneRenderBean mbean = new MileStoneRenderBean();
					mbean.setName(sbean);
					for (KsuBean kbean : ksuList) {
						
						if (kbean.getProductOrderId().equals(tsbean.getParentId()) && kbean.getName().toUpperCase().equals(sbean)
								&& kbean.getServiceName().toUpperCase().equals(tsbean.getName())) {
							mbean.setStatus("SUCCESS");
							mbean.setCreatedTime(kbean.getCreatedTime());
							break;
						}
						
					}
					if (mbean.getStatus() == null)
						mbean.setStatus("FAILED");
					mbeans.add(mbean);
				}
				}catch (Exception e) {
					logger.error("KSU List Error >> "+tsbean);
				}
			}

		} else {
			MileStoneRenderBean mbean = new MileStoneRenderBean();
			mbean.setName("CONFIG NOT SET");
			mbean.setStatus("FAILED");
			mbeans.add(mbean);
		}

		return mbeans;
	}

	private static CFSBean checkCFSConfig(ServiceRenderBean tsbean) throws Exception {
		CFSBean temp = null;
		if(config.getCfsList()!=null) {
			for (CFSBean cbean : config.getCfsList()) {
				if (cbean.getServiceName().equals(tsbean.getName()) && cbean.getAction().equals(tsbean.getAction())
						&& cbean.getParentServiceName().equalsIgnoreCase(tsbean.getParentName()) && cbean.getParentAction().equals(tsbean.getParentAction())) {
					temp = cbean;
					break;
				}
			}
		}else {
			throw new Exception("CONFIGISSUE-CFSLIST");
		}

		return temp;
	}

	private static ServiceRenderBean generateConfigService(HashMap<String, OrderItem> mapper, String serviceName, OsmBean osm) {
		ServiceRenderBean tsbean = null;

		// {1410136967=OrderItem{action='PR', name='Modem_Main', id='1410136967',
		// childList=[OrderItem{action='PR', name='Gateway_CFS', id='1410136977',
		// childList=null}]},
		// 1410136971=OrderItem{action='PR', name='HSI_Main', id='1410136971',
		// childList=[OrderItem{action='PR', name='Internet_CFS', id='1410136985',
		// childList=null}]}}

		for (CFSBean cbean : config.getCfsList()) {
			if (cbean.getServiceName().equals(serviceName) && cbean.getAction().equals(osm.getOrderAction())) {

				tsbean = new ServiceRenderBean();
				tsbean.setName(serviceName);

				ArrayList<MileStoneRenderBean> mbeans = new ArrayList<>();
				for (String sbean : cbean.getKsu()) {
					MileStoneRenderBean mbean = new MileStoneRenderBean();
					mbean.setName(sbean);
					for (KsuBean kbean : osm.getKsuList()) {
						OrderItem item = mapper.get(kbean.getProductOrderId());
						if (item != null) {
							if (sbean.equals(kbean.getName().toUpperCase()) && item.getAction().equalsIgnoreCase(cbean.getParentAction())
									&& item.getName().equalsIgnoreCase(cbean.getParentServiceName())) {
								tsbean.setParentName(item.getName());
								mbean.setStatus("SUCCESS");
								mbean.setCreatedTime(kbean.getCreatedTime());
							}
						}
					}
					if (mbean.getStatus() == null)
						mbean.setStatus("FAILED");
					mbeans.add(mbean);
				}
				tsbean.setMilestones(mbeans);
			}
		}
		return tsbean;
	}

}
