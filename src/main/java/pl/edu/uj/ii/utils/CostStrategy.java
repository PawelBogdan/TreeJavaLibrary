package pl.edu.uj.ii.utils;

public interface CostStrategy {
    double getCost(Rectangle rectangle);
    double getCost(Rectangle rectangle1, Rectangle rectangle2);
}
