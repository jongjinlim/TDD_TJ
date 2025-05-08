package sample.caftkiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.caftkiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.caftkiosk.spring.api.service.product.response.ProductResponse;
import sample.caftkiosk.spring.domain.product.Product;
import sample.caftkiosk.spring.domain.product.ProductRepository;
import sample.caftkiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 * CQRS - Command / Query
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

	// 동시성 이슈
	// UUID ?
	@Transactional
	public ProductResponse createProduct(ProductCreateRequest request) {
		// productNumber
		String nextProductNumber = createNextProductNumber();

		Product product = request.toEntity(nextProductNumber);
		Product savedProduct = productRepository.save(product);

		return ProductResponse.of(savedProduct);
	}

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

	private String createNextProductNumber() {
		String latestProductNumber = productRepository.findLatestProduct();
		if(latestProductNumber == null) {
			return "001";
		}

		int latestProductNumberInt = Integer.parseInt(latestProductNumber);
		int nextProductNumberInt = latestProductNumberInt + 1;
		return String.format("%03d", nextProductNumberInt);
	}
}
