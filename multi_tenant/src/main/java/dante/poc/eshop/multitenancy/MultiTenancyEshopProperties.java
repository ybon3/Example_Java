package dante.poc.eshop.multitenancy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class demonstration how to read duplicate properties as List, and customize properties, then put into java class
 * <p>
 * Read from properties file: prefix with "multimerchant.eshop.dataSources"
 */
@Configuration
@ConfigurationProperties(prefix = "multitenancy.eshop")
public class MultiTenancyEshopProperties {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String modelPackage;
	private List<DataSourceProperties> dataSourcesProps;

	public MultiTenancyEshopProperties() { logger.debug("Created"); }

	public List<DataSourceProperties> getDataSources() {
		return dataSourcesProps;
	}

	public void setDataSources(List<DataSourceProperties> dataSourcesProps) {
		this.dataSourcesProps = dataSourcesProps;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	/**
	 * match properties like:
	 * <ul>
	 * 	<li>tenantId
	 * 	<li>url --> {@link DataSourceProperties}
	 * 	<li>username --> {@link DataSourceProperties}
	 * 	<li>password --> {@link DataSourceProperties}
	 * 	<li>driverClassName --> {@link DataSourceProperties}
	 * </ul>
	 */
	public static class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {

		private String tenantId;

		public String getTenantId() {
			return tenantId;
		}
		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
	}
}
