package dante.poc.eshop.ctrl.object;

import java.math.BigDecimal;
import java.util.Date;

public class ObjOrderDetail {
	public Integer id;
	public BigDecimal currentTotalAmount;
	public Date lastUpd;
	public Integer quantity;
	public Integer orderCount;
	public String orderRecordId;
	public BigDecimal discount;
}
