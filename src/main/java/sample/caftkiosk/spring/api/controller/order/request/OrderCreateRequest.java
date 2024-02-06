package sample.caftkiosk.spring.api.controller.order.request;

import java.util.List;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private List<String> productNumbers;
}
