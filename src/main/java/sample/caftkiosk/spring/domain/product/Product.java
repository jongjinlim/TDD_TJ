package sample.caftkiosk.spring.domain.product;

import lombok.Getter;

@Getter
public class Product {
    private Long id;

    private String productNumber;

    private ProductType type;

    private ProductSellingType sellingType;

    private String name;

    private int price;
}
