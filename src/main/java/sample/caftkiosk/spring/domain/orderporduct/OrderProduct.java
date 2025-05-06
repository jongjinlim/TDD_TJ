package sample.caftkiosk.spring.domain.orderporduct;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.caftkiosk.spring.domain.BaseEntity;
import sample.caftkiosk.spring.domain.order.Order;
import sample.caftkiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public OrderProduct(Order order, Product product) {
		this.order = order;
		this.product = product;
	}
}
