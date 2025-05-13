package sample.caftkiosk.spring.domain.order;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.caftkiosk.spring.domain.BaseEntity;
import sample.caftkiosk.spring.domain.orderporduct.OrderProduct;
import sample.caftkiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDatetime;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@Builder
	private Order(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDatetime) {
		this.orderStatus = orderStatus;
		this.totalPrice = calculateTotalPrice(products);
		this.registeredDatetime = registeredDatetime;
		this.orderProducts = products.stream()
				.map(product -> new OrderProduct(this, product))
				.collect(Collectors.toList());
	}

	public static Order create(List<Product> products, LocalDateTime registeredDatetime) {
        return Order.builder()
				.orderStatus(OrderStatus.INIT)
				.products(products)
				.registeredDatetime(registeredDatetime)
				.build();
    }

	private int calculateTotalPrice(List<Product> products) {
		return products.stream()
				.mapToInt(Product::getPrice)
				.sum();
	}
}
