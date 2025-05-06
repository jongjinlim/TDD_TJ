package sample.caftkiosk.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import sample.caftkiosk.spring.api.service.product.response.ProductResponse;
import sample.caftkiosk.spring.domain.order.Order;
import sample.caftkiosk.spring.domain.order.OrderStatus;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
	private List<ProductResponse> products;

	@Builder
	public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
		this.id = id;
		this.totalPrice = totalPrice;
		this.registeredDateTime = registeredDateTime;
		this.products = products;
	}

	public static OrderResponse of(Order order) {
		return OrderResponse.builder()
				.id(order.getId())
				.totalPrice(order.getTotalPrice())
				.registeredDateTime(order.getRegisteredDatetime())
				.products(order.getOrderProducts().stream()
						.map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
						.collect(Collectors.toList())
				)
				.build();
	}
}
