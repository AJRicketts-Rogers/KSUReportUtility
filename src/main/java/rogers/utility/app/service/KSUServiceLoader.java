package rogers.utility.app.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		
		for (OsmOrderEntity osmEntity : osmList) {
			logger.debug("loading OSM Order " + osmEntity.getORDER_SEQ_ID());
			OSMOrderTrackerEntity okEntity = createKSUOSMBean(osmEntity);
			if (osmEntity.getAmendMent() == null && !isZapOrder(osmEntity)) {
				try {
				// System.out.println("loading OSM ID " + okEntity.getOsmId());
				int count = ksuOsmRepo.countByOsmId(okEntity.getOsmId());
				
				if (count == 0) {
//					logger.info("Saving order " + okEntity.getOsmId() + " to db.");
					ksuOsmRepo.save(okEntity);
					tempCount++;
				}
				// else
				// System.out.println("Duplicate OSM ID " + okEntity.getOsmId());
				} catch (Exception e) {
					logger.error("Exception in Saving ", e);
				}
			} 
			
			else {
				logger.debug("This is an Amend Order  >> " + osmEntity.getORDER_SEQ_ID());
			}
			
			if(tempCount >= 500) {
				ksuOsmRepo.flush();
				tempCount=0;
			}
		}
		
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
	
	private boolean isZapOrder(OsmOrderEntity osmEntity) {
//		logger.info("Finding som entities by order id");
		
		Integer orderId = osmEntity.getORDER_SEQ_ID();
		Optional<String> orderTypeOpt = somRepo.findOrderTypeByOSM_ORDER_ID(String.valueOf(orderId));
		String orderType = null;
		
		if (orderTypeOpt.isPresent()) {
			
			orderType = orderTypeOpt.get();
			logger.info("Pulled order type for order " + orderId + ". Order type = " + orderType);
			if (!orderType.toLowerCase().contains("zap")) {
				logger.info("Not a zap order, including");
				return false;
			}
		}
		else {
			logger.info("Order type for order " + orderId + " is null, including");
			return false;
		}
			
		
		return true;
		
	}

	public void setConfig(String url1, String url2, String user2, String password2) {
		this.url = url1;
		this.urlSecondary = url2;
		this.user = user2;
		this.password = password2;

	}
}
