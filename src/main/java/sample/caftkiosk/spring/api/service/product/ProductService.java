package sample.caftkiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.caftkiosk.spring.api.service.product.response.ProductResponse;
import sample.caftkiosk.spring.api.service.product.request.ProductCreateRequest;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;
import sample.caftkiosk.spring.domain.product.ProductSellingStatus;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

	// 동시성 이슈
	// UUID ?
	public void createProduct(ProductCreateRequest request) {
		String nextProductNumber = createNextProductNumber();

		Product product = request.toEntity(nextProductNumber);
		Product savedProduct = productRepository.save(product);
	}

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

	private String createNextProductNumber() {
		String latestProductNumber = "";
		if(latestProductNumber == null) {

		}
		return "";
	}
}
