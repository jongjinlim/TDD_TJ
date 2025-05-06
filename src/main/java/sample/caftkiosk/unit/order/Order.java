package sample.caftkiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.unit.Beverage;

@Getter
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverageList;
}
