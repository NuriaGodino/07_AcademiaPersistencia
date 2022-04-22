package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@PropertySource(value = "classpath:config/application.properties")
@Configuration
@ComponentScan(basePackages = {"service"})
public class ServiceConfig {
	
	@Value("${driver}")
	String driver;
	@Value("${url}")
	String url;
	@Value("${usuario}")
	String usuario;
	@Value("${pwd}")
	String pwd;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource data = new DriverManagerDataSource();
		data.setDriverClassName(driver);
		data.setUrl(url);
		data.setUsername(usuario);
		data.setPassword(pwd);
		return data;
	}
	
	@Bean
	public HibernateJpaVendorAdapter adapter() { 
		HibernateJpaVendorAdapter adp = new HibernateJpaVendorAdapter(); 
		adp.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		return adp;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean factory(DataSource dataSource, HibernateJpaVendorAdapter adapter) {  
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("tiendaPU"); 
		factory.setDataSource(dataSource); 
		factory.setPackagesToScan("model"); 
		factory.setJpaVendorAdapter(adapter); 
		return factory;
	}

	// gestor de transacción
	@Bean
	public JpaTransactionManager txManager(LocalContainerEntityManagerFactoryBean factory) {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(factory.getObject());
		return manager;
	}
}
