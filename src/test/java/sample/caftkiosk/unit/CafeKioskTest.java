package sample.caftkiosk.unit;

import org.junit.jupiter.api.Test;
import sample.caftkiosk.unit.beverage.Americano;
import sample.caftkiosk.unit.beverage.Latte;
import sample.caftkiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
    }

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(1);	// 얘랑
		assertThat(cafeKiosk.getBeverageList()).hasSize(1);	// 얘랑 같은의미
		assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void addSeveralBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano, 2);

		assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);
	}

	@Test
	void addZeroBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		assertThatThrownBy(() -> cafeKiosk.add(americano,0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.")
		;
	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);
		assertThat(cafeKiosk.getBeverageList()).hasSize(1);

		cafeKiosk.remove(americano);
		assertThat(cafeKiosk.getBeverageList()).hasSize(0);
		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}

	@Test
	void clear() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		assertThat(cafeKiosk.getBeverageList()).hasSize(2);

		cafeKiosk.clear();
		assertThat(cafeKiosk.getBeverageList()).isEmpty();
	}

	@Test
	void createOrder() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder();
		assertThat(order.getBeverageList()).hasSize(1);
		assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void createOrderWithCurrentTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder(LocalDateTime.of(2025,5,2,10,0));
		assertThat(order.getBeverageList()).hasSize(1);
		assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void createOrderOutsideOpenTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2025,5,2,9,59)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.")
				;
	}
}
