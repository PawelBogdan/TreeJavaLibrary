package pl.edu.uj.ii.utils;

public class EntropyCostStrategy implements CostStrategy {
    @Override
    public double getCost(Rectangle rectangle) {
        return Costs.covarianceCost(rectangle.getWidth(), rectangle.getHeight(), rectangle.getPoints());
    }

    @Override
    public double getCost(Rectangle rectangle1, Rectangle rectangle2) {
        return getCost(rectangle1) + getCost(rectangle2);
    }
}
