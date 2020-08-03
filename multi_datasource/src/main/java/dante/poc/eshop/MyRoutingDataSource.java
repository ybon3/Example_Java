package dante.poc.eshop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyRoutingDataSource extends AbstractRoutingDataSource {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Object determineCurrentLookupKey() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		// See more: DataSourceInterceptor
		String keyDS = (String) request.getAttribute("keyDS");

		logger.info("KeyDS=" + keyDS);

		if (keyDS == null) {
			keyDS = "XYZ_DS";
		}

		return keyDS;
	}

	public void initDataSources(DataSource dataSource1, DataSource dataSource2) {
		Map<Object, Object> dsMap = new HashMap<Object, Object>();
		dsMap.put("XYZ_DS", dataSource1);
		dsMap.put("HIMIRROR_DS", dataSource2);

		this.setTargetDataSources(dsMap);
	}
}