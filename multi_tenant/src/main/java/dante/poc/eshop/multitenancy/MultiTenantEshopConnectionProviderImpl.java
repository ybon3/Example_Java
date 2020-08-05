package dante.poc.eshop.multitenancy;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Assist LocalContainerEntityManagerFactoryBean obtain weave DataSource Map
 */
public class MultiTenantEshopConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 6623982367042525842L;

	@Autowired
	private Map<String, DataSource> dataSourcesEshop;

	@Override
	protected DataSource selectAnyDataSource() {
		return this.dataSourcesEshop.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return this.dataSourcesEshop.get(tenantIdentifier);
	}
}
