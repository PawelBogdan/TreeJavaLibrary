package pl.edu.uj.ii.trees.rectanglize;

import pl.edu.misztal.data.Point;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Rectangle;

import java.util.List;

/**
 * @author Paweł Bogdan
 */
public class RectangleNode extends Node {
    private Rectangle rectangle;


    public RectangleNode(Rectangle rectangle, int divisionCoordinate, List<Double> divisionValues, List<Node> children) {
        super(divisionCoordinate, divisionValues, children);
        this.rectangle = rectangle;
    }

    public RectangleNode(Rectangle rectangle, List<Point> values, int divisionCoordinate, List<Double> divisionValues, List<Node> children) {
        super(values, divisionCoordinate, divisionValues, children);
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
