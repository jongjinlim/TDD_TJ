package sample.caftkiosk.spring.domain.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.caftkiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor
public class Order {

    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDatetime;

    public Order(List<Product> products) {

    }

    public static Order create(List<Product> products) {
        return new Order(products);
    }
}
