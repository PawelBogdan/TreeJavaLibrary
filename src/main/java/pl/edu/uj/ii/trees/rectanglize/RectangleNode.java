package pl.edu.uj.ii.trees.rectanglize;

import pl.edu.misztal.data.Point;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paweł Bogdan
 */
public class RectangleNode extends Node {
    private Rectangle rectangle;

    public RectangleNode(Rectangle rectangle) {
        this(rectangle, -1, new ArrayList<>(), new ArrayList<>());
    }

    public RectangleNode(Rectangle rectangle, int divisionCoordinate, List<Double> divisionValues, List<Node> children) {
        super(rectangle.getPoints(), divisionCoordinate, divisionValues, children);
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
