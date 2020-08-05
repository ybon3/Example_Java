package dante.poc.eshop.ctrl.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjOrderRecord {
	public String orderId;
	public String comment;
	public String currency;
	public Long memberId;
	public String customerEmail;
	public String orderStatus;
	public Date orderTime;
	public String orderType;
	public BigDecimal productAmount;
	public List<ObjOrderDetail> orderDetails;
}