package sample.caftkiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import sample.caftkiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;

@Service
public class OrderService {

    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllBySellingStatusIn(null);
    }
}
