package dante.poc.eshop.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Entity implementation class for Entity: OrderRecord
 *
 */
@Entity
@Table(name="OrderRecord")

public class OrderRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "order_id", nullable = false, columnDefinition = "char(15)")
	private String orderId;

	@Column(name="comment", columnDefinition="nvarchar(255)")
	private String comment;

	@Column(name="currency", columnDefinition="nvarchar(20)")
	private String currency;

	@Column(name="member_id", nullable = false, columnDefinition="numeric(19, 0)")
	private Long memberId;

	@Column(name="customer_email", columnDefinition="nvarchar(255)")
	private String customerEmail;

	@Column(name="order_status", columnDefinition="varchar(20)")
	private String orderStatus;

	@Column(name="order_time", columnDefinition="datetime default (getutcdate())")
	private Date orderTime;

	@Column(name="order_type", columnDefinition="varchar(10)")
	private String orderType;

	@Column(name="product_amount", columnDefinition="numeric(17, 4)  default (0)")
	private BigDecimal productAmount;

	@OneToMany(mappedBy="orderRecord", fetch = FetchType.LAZY, orphanRemoval = true, cascade=CascadeType.ALL)
	private List<OrderDetail> orderDetails;

	public OrderRecord() {
		super();
	}
	public String getOrderId() {
		return this.orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getMemberId() {
		return this.memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getOrderStatus() {
		return this.orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderTime() {
		return this.orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderType() {
		return this.orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public BigDecimal getProductAmount() {
		return this.productAmount;
	}
	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}
	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
