package dante.poc.eshop.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dante.poc.eshop.common.dao.OrderRecordDAO;

@RestController
@RequestMapping(value = "/*/order",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrderCtrl {

	private Gson gson = new Gson();

	@Autowired private OrderRecordDAO orderRecordDAO;

	@RequestMapping(value = "/getById",
			method = RequestMethod.GET)
	public String getById(
			@RequestParam("orderId") String orderId) {

		return gson.toJson(orderRecordDAO.find(orderId));
	}
}
