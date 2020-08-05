package dante.poc.eshop.service;


import java.util.ArrayList;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dante.poc.eshop.ctrl.object.ObjOrderDetail;
import dante.poc.eshop.ctrl.object.ObjOrderRecord;
import dante.poc.eshop.persistence.dao.OrderRecordDAO;
import dante.poc.eshop.persistence.model.OrderDetail;
import dante.poc.eshop.persistence.model.OrderRecord;

@Service
@Transactional
public class OrderService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderRecordDAO orderRecordDAO;

	private Gson gson = new Gson();

	public String getOrderRecordById(String orderId) {
		OrderRecord orderRecord = orderRecordDAO.find(orderId);
		if (orderRecord != null) {
			return gson.toJson(toObjOrderRecord(orderRecord));
		} else {
			return "{}";
		}
	}

	public static ObjOrderRecord toObjOrderRecord(OrderRecord src) {
		ObjOrderRecord objOrderRecord = new ObjOrderRecord();
		objOrderRecord.orderId = src.getOrderId();
		objOrderRecord.comment = src.getComment();
		objOrderRecord.currency = src.getCurrency();
		objOrderRecord.memberId = src.getMemberId();
		objOrderRecord.customerEmail = src.getCustomerEmail();
		objOrderRecord.orderStatus = src.getOrderStatus();
		objOrderRecord.orderTime = src.getOrderTime();
		objOrderRecord.orderType = src.getOrderType();
		objOrderRecord.productAmount = src.getProductAmount();
		if (src.getOrderDetails().size() > 0) {
			objOrderRecord.orderDetails = new ArrayList<ObjOrderDetail>();
			for (OrderDetail orderDetail : src.getOrderDetails()) {
				ObjOrderDetail objOrderDetail = toObjOrderDetail(orderDetail);
				objOrderRecord.orderDetails.add(objOrderDetail);
			}
		}
		return objOrderRecord;
	}

	public static ObjOrderDetail toObjOrderDetail(OrderDetail src) {
		ObjOrderDetail objOrderDetail = new ObjOrderDetail();
		objOrderDetail.id = src.getId();
		objOrderDetail.currentTotalAmount = src.getCurrentTotalAmount();
		objOrderDetail.lastUpd = src.getLastUpd();
		objOrderDetail.quantity = src.getQuantity();
		objOrderDetail.orderCount = src.getOrderCount();
		objOrderDetail.orderRecordId = src.getOrderRecord().getOrderId();
		objOrderDetail.discount = src.getDiscount();
		return objOrderDetail;
	}
}
