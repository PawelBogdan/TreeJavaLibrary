package pl.edu.uj.ii.utils;

import org.ejml.simple.SimpleMatrix;
import pl.edu.misztal.data.Cluster;
import pl.edu.misztal.data.Point;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Paweł Bogdan
 */
public class Rectangle {
    private final double x1;
    private final double x2;
    private final double y1;
    private final double y2;
    private final List<Point> points;
    private final double cost;

    public Rectangle(List<Point> points, double cost, double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.points = points;
        this.cost = cost;
    }


    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getWidth() {
        return Math.abs(x2-x1);
    }

    public double getHeight() {
        return Math.abs(y2-y1);
    }

    public double getCost() {
        return cost;
    }

//    @Override
//    public String toString() {
//        return "[" + x1 + "," + y1 + "," + x2 + "," + y2 + "](Size: " + this.points.size() + ")(Cost: " + this.cost + ")";
//    }

    @Override
    public String toString() {
        if (points.size() == 0) {
            return String.format("[%.3f, %.3f, %.3f, %.3f](Size: %d)(Cost: %.3f)", x1,y1,x2,y2, points.size(), cost);
        }
        Cluster cluster = new Cluster(points.get(0).getDimension());
        for (Point p : points) {
            cluster.add(p);
        }
        SimpleMatrix cov = cluster.getCov();
        Point point = points.get(0);
        Predicate<Point> theSameColor = p -> p.get(2) == point.get(2)
                && p.get(3) == point.get(3)
                && p.get(4) == point.get(4)
                && p.get(5) == point.get(5);
        boolean theSamePoints = points.stream().allMatch(theSameColor);
//        long distinct = points.stream().distinct().count();
        return String.format("[%.3f, %.3f, %.3f, %.3f](Size: %d)(Cost: %.3f)(Diagonal: %.3f, %.3f, %.3f, %.3f)(Color: %.3f, %.3f, %.3f, %.3f)(%s)",
                x1,y1,x2,y2, points.size(), cost,
                cov.get(2,2), cov.get(3,3), cov.get(4,4), cov.get(5,5),
                point.get(2), point.get(3), point.get(4), point.get(5),
                (theSamePoints ? "all the same" : "points differ"));
    }

}
