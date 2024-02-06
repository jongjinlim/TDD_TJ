package sample.caftkiosk.spring.api.service.order.response;

import java.time.LocalDateTime;
import sample.caftkiosk.spring.domain.order.OrderStatus;

public class OrderResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
}
