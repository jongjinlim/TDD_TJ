package sample.caftkiosk.spring.api.service.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@DataJpaTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @DisplayName("")
    @Test
    void createOrder() {
        // given

        // when

        // then
    }

}