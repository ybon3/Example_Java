package dante.poc.eshop.multitenancy;

/**
 * 可以存取每一個 thread 專屬的變數(AKA thread-scope variable)
 */
public class MultiTenantContext {
	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

	public static void setTenantId(String tenantId) {
		CONTEXT.set(tenantId);
	}

	public static String getTenantId() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}
}
