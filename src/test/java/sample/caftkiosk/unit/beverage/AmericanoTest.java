package sample.caftkiosk.unit.beverage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "아메리카노");	// JUnit5
        assertThat(americano.getName()).isEqualTo("아메리카노");	// assertJ
    }

	@Test
	void getPrice() {
		Americano americano = new Americano();
		assertThat(americano.getPrice()).isEqualTo(4000);	// assertJ
	}
}
