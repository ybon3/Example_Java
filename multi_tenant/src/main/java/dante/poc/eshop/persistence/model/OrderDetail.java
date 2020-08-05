package dante.poc.eshop.persistence.model;

import java.io.Serializable;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: OrderDetail
 *
 */
@Entity
@Table(name="OrderDetail")

public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, columnDefinition = "int")
	private Integer id;

	@Column(name="current_total_amount", columnDefinition = "numeric(17, 4)")
	private BigDecimal currentTotalAmount;

	@Column(name="last_upd", columnDefinition = "datetime default (getutcdate())")
	private Date lastUpd;

	@Column(name="quantity", columnDefinition = "smallint")
	private Integer quantity;

	@Column(name="order_count", columnDefinition = "smallint")
	private Integer orderCount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id")
	private OrderRecord orderRecord;

	@Column(name="discount", columnDefinition = "numeric(13, 4)")
	private BigDecimal discount;

	public OrderDetail() {
		super();
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getCurrentTotalAmount() {
		return this.currentTotalAmount;
	}
	public void setCurrentTotalAmount(BigDecimal currentTotalAmount) {
		this.currentTotalAmount = currentTotalAmount;
	}
	public Date getLastUpd() {
		return this.lastUpd;
	}
	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}
	public Integer getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getOrderCount() {
		return this.orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public OrderRecord getOrderRecord() {
		return this.orderRecord;
	}
	public void setOrderRecord(OrderRecord orderRecord) {
		this.orderRecord = orderRecord;
	}
	public BigDecimal getDiscount() {
		return this.discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}
