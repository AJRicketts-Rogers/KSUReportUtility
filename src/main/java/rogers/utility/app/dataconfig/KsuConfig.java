package rogers.utility.app.dataconfig;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import rogers.utility.app.utility.CryptoUtils;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "ksuEntityManagerFactory", basePackages = { "rogers.utility.app.ksu.repo" })
public class KsuConfig implements BeanPostProcessor {
	private static final Logger logger = LogManager.getLogger(KsuConfig.class);
	DataSource ds;

	@Primary
	@Bean(name = "ksudataSource")
	@ConfigurationProperties(prefix = "spring.ksudatasource")
	public DataSource dataSource() {

		ds = DataSourceBuilder.create().build();
		return ds;
	}

	public void forceCommit() throws SQLException {
		Connection con = null;
		try {
			con=this.ds.getConnection();
			con.commit();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug("Exception occured while trying to connect "+ e.getMessage());
		}
		finally {
			if ( con!= null) {
				con.close();
			}
		}
		//this.ds.getConnection().commit();
	}

	
	public void setAutoCommit(boolean flag) {
		Connection con = null;
		try {
			con=this.ds.getConnection();
			con.setAutoCommit(flag);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug("Exception occured while trying to connect "+ e.getMessage());
		}
		finally {
			try {
			if ( con!= null) {
				con.close();
			}
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("Error trying to close connection"+ e.getMessage());
			}
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof HikariDataSource) {
			HikariDataSource localPropeties = (HikariDataSource) bean;
			try {
				localPropeties.setPassword(CryptoUtils.decrypt(localPropeties.getPassword()));
			} catch (Exception e) {
				// e.printStackTrace();
				localPropeties.setPassword(localPropeties.getPassword());
			}
			// System.out.println("################# KSU PASSWORD
			// "+localPropeties.getPassword());

		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof HikariDataSource) {
			HikariDataSource localPropeties = (HikariDataSource) bean;
			try {
				localPropeties.setAutoCommit(false);
			} catch (Exception e) {
			
			}
			// System.out.println("################# KSU PASSWORD
			// "+localPropeties.getPassword());

		}

		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
	
	@Primary
	@Bean(name = "ksuEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("ksudataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("rogers.utility.app.ksu.entity").persistenceUnit("KsuEntity").build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("ksuEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
