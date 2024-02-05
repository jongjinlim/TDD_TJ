package sample.caftkiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.caftkiosk.spring.api.service.product.response.ProductResponse;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;
import sample.caftkiosk.spring.domain.product.ProductSellingType;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }
}
