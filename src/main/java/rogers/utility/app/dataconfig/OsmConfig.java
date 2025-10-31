package rogers.utility.app.dataconfig;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import rogers.utility.app.utility.CryptoUtils;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "osmEntityManagerFactory", transactionManagerRef = "osmTransactionManager", basePackages = {
		"rogers.utility.app.osm.repo" })

public class OsmConfig implements BeanPostProcessor{

	  @Override
	  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
	  
	// System.out.println("#################  OsmConfig CAllled "+beanName+"--"+bean);
		  
		 
		  if (bean instanceof HikariDataSource) {
			  HikariDataSource localPropeties = (HikariDataSource) bean;
			  try {
			//	  System.out.println("################# OSM  PASSWORD  "+localPropeties.getPassword());
				localPropeties.setPassword(CryptoUtils.decrypt(localPropeties.getPassword()));
			} catch (Exception e) {
				
				localPropeties.setPassword(localPropeties.getPassword());
			}
			//  System.out.println("################# OSM  PASSWORD  "+localPropeties.getPassword());
			  
		    }
		  return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	  }
	

	  
	@Bean(name = "osmdataSource")
	@ConfigurationProperties(prefix = "spring.osmdatasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "osmEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean osmEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("osmdataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("rogers.utility.app.osm.entity").persistenceUnit("OsmEntity")
				.build();
	}

	@Bean(name = "osmTransactionManager")
	public PlatformTransactionManager osmTransactionManager(
			@Qualifier("osmEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
