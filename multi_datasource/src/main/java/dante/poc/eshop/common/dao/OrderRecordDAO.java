package dante.poc.eshop.common.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRecordDAO extends JdbcDaoSupport {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public OrderRecordDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<Map<String, Object>> find(String id){
		return getJdbcTemplate().queryForList(
				"SELECT * FROM OrderRecord WHERE order_id = ?",
				new Object[]{id});
	}
}
