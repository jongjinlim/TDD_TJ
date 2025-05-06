package sample.caftkiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.caftkiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.caftkiosk.spring.domain.order.OrderRepository;
import sample.caftkiosk.spring.api.service.order.response.OrderResponse;
import sample.caftkiosk.spring.domain.order.Order;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;
import sample.caftkiosk.spring.domain.product.ProductType;
import sample.caftkiosk.spring.domain.stock.Stock;
import sample.caftkiosk.spring.domain.stock.StockRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
		// product
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);

		// 재고 차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers = products.stream()
            .filter(product -> ProductType.containsStockType(product.getType()))
            .map(Product::getProductNumber)
            .collect(Collectors.toList());

        // 재고 엔티티 조회
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream()
            .collect(Collectors.toMap(Stock::getProductNumber, s -> s));

        // 상품별 counting
        Map<String, Long> prodctCountingMap = stockProductNumbers.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // 재고 차감 시도
        for (String stockProductNumber : stockProductNumbers) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = prodctCountingMap.get(stockProductNumber).intValue();
            if(stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
		return OrderResponse.of(savedOrder);
    }
}
