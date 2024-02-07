package sample.caftkiosk.spring.api.service.order;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@SpringBootTest
@DataJpaTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @DisplayName("")
    @Test
    void createOrder() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when

        // then
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when

        // then
    }

    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

//        Product product1 = createProduct();

//        Stock stock1 = new Stock();

        // when

        // then
    }
}