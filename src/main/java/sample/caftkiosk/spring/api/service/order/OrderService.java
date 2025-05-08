package sample.caftkiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.caftkiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.caftkiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.caftkiosk.spring.domain.order.OrderRepository;
import sample.caftkiosk.spring.api.service.order.response.OrderResponse;
import sample.caftkiosk.spring.domain.order.Order;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;
import sample.caftkiosk.spring.domain.product.ProductType;
import sample.caftkiosk.spring.domain.stock.Stock;
import sample.caftkiosk.spring.domain.stock.StockRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

	/**
	 * 재고 감소 -> 동시성 고민
	 * optimistic lock / pessimistic lock / ...
	 * @param request
	 * @param registeredDateTime
	 * @return
	 */
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

		deductStockQuantities(products);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
    }

	private void deductStockQuantities(List<Product> products) {
		// 재고 차감 체크가 필요한 상품들 filter
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);	// 재고 엔티티 조회
		Map<String, Long> prodctCountingMap = createCountingMapBy(stockProductNumbers);	// 상품별 counting

		// 재고 차감 시도
		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = prodctCountingMap.get(stockProductNumber).intValue();
			if(stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);
		}
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
				.collect(Collectors.toMap(product -> product.getProductNumber(), p -> p));

		return productNumbers.stream()
				.map(productMap::get)
				.collect(Collectors.toList());
	}

	private static List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
				.filter(product -> ProductType.containsStockType(product.getType()))
				.map(Product::getProductNumber)
				.collect(Collectors.toList());
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		Map<String, Stock> stockMap = stocks.stream()
				.collect(Collectors.toMap(Stock::getProductNumber, s -> s));
		return stockMap;
	}

	private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
				.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}
}
