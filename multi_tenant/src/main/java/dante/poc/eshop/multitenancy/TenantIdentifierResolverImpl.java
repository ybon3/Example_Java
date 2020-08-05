package dante.poc.eshop.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * Assist LocalContainerEntityManagerFactoryBean obtain tenantId
 */
public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return MultiTenantContext.getTenantId();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
}
