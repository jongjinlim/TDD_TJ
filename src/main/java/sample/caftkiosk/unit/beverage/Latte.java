package sample.caftkiosk.unit.beverage;

import sample.caftkiosk.unit.Beverage;

public class Latte implements Beverage {

    @Override
    public int getPrice() {
        return 4500;
    }

    @Override
    public String getName() {
        return "라떼";
    }
}
