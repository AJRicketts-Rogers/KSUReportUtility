package rogers.utility.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import rogers.utility.app.ksu.entity.ConfigEntity;
import rogers.utility.app.ksu.repo.ConfigRepository;
import rogers.utility.app.service.KSUServiceLoader;
import rogers.utility.app.service.KSUServiceUpdater;
import rogers.utility.app.utility.CryptoUtils;
import rogers.utility.app.utility.ReloadablePropertySourceFactory;

@SpringBootApplication
@PropertySource(ignoreResourceNotFound = true, value = "applicationruntime.properties")
public class OSMKSULoaderMainApp implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger(OSMKSULoaderMainApp.class);
	public static String entryArg = "LOADOSMONLY";// "LOADOSMDATA"

	public static void main(String[] args) {
		entryArg = args[0];
		Properties properties = null;
		try {
			properties = ReloadablePropertySourceFactory.loader(loadApllicationProperties());
		} catch (Exception e) {
			logger.error("Exception in OSMKSULoaderMainApp::main",e);
		}

		SpringApplication application = new SpringApplication(OSMKSULoaderMainApp.class);
		application.run(args);
		
	}

	@Autowired
	private ConfigRepository configRepo;

	@Autowired
	private KSUServiceUpdater ksuServiceUpdater;
	
	@Autowired
	private KSUServiceLoader ksuServiceLoader;
	
	@Value("${osm.ws.url}")
	private String url;
	@Value("${osm.ws.url.secondary}")
	private String urlSecondary;
	@Value("${osm.ws.user}")
	private String user;
	@Value("${osm.ws.password}")
	private String password;

	@Value("${filter.config}")
	private String filterConfigFile;
	
	public static File loadApllicationProperties() throws FileNotFoundException {
		return ResourceUtils.getFile("applicationruntime.properties");
	}

	@Override
	public void run(String... args) throws Exception {
	
		try {
			this.password = CryptoUtils.decrypt(this.password);
		//	System.out.println("this.password " + this.password);
		} catch (Exception e) {
			logger.error("Exception in OSMKSULoaderMainApp::run",e);
		}

		

		if (entryArg.equalsIgnoreCase("LOADOSMONLY")) {
			logger.info("OSM Order  Loading Started.....");
			ksuServiceLoader.setConfig(this.url,this.urlSecondary, this.user, this.password);
			Optional<ConfigEntity> configs = configRepo.findByName("OSMORDERLOADER");

			ConfigEntity config = new ConfigEntity();
			config.setName("OSMORDERLOADER");
			try {
				if (configs.isPresent())
					config = configs.get();

				logger.info("Config  " + config);
				if (config == null) {
					logger.info("Create New Config  " + config);
					config = new ConfigEntity();
					//config.setInterval(7200);
					config.setInterval(21600); //21600s = 6 hours
					config.setLastRunTime(new Timestamp(System.currentTimeMillis()));
					config.setEndTime(new Timestamp(System.currentTimeMillis()));
					config.setDescription("SUCCESS");
					config.setLocked("LOCKED");

				} else {
					if (config.getLocked() == null || !config.getLocked().equalsIgnoreCase("LOCKED"))
						config.setLocked("LOCKED");
					else {
						logger.warn("Alerdy  Running Process,  Check DB and Java Process");
						return;
					}
				}

				// configRepo.save(config);
				configRepo.saveAndFlush(config);

				ksuServiceLoader.queryOSMAndUpate(config);
				
				
			} catch (Exception er) {
				
				config.setDescription("Exception " + er.getMessage());
				logger.error("Exception in Loading",er);
			}finally {
				if (config != null) {
					config.setLocked("OPEN");
					}
				configRepo.saveAndFlush(config);
				logger.info("OSM Order  Loading Completed!");
			}

		} else {
			logger.info("OSM Order  Updating Started.....");
			ksuServiceUpdater.setConfig(this.url,this.urlSecondary, this.user, this.password);
			Optional<ConfigEntity> configs = configRepo.findByName("OSMORDEREXECUTER");
			ConfigEntity config = null;
			if (configs.isPresent())
				config = configs.get();
			logger.info("Config  " + config);
			if (config == null) {
				logger.info("Create New Config  " + config);
				config = new ConfigEntity();
				config.setName("OSMORDEREXECUTER");
				config.setOperationLimit(100);
				config.setLastRunTime(new Timestamp(System.currentTimeMillis()));
				config.setDescription("SUCCESS");
				configRepo.save(config);
			}

			try {
				
				ksuServiceUpdater.loadFilterConfig(filterConfigFile);
				ksuServiceUpdater.queryKSUOSMAndUpate(config);
				logger.info("OSM Order  Updated!");
			} catch (Exception e) {
				logger.error("App Exception ! Exceution Halted " ,e);
			}
			
		}

	}
}
