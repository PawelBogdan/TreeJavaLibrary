package pl.edu.uj.ii.utils;

import org.ejml.simple.SimpleMatrix;
import org.w3c.dom.css.Rect;
import pl.edu.misztal.data.Cluster;
import pl.edu.misztal.data.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Paweł Bogdan
 */
public class Rectangle {
    private final Point p1;
    private final Point p2;
    private final List<Point> points;

    public Rectangle(List<Point> points) {
        double maxX = points.stream().mapToDouble(p -> p.get(0)).max().orElse(0.0);
        double maxY = points.stream().mapToDouble(p -> p.get(1)).max().orElse(0.0);
        double minX = points.stream().mapToDouble(p -> p.get(0)).min().orElse(0.0);
        double minY = points.stream().mapToDouble(p -> p.get(1)).min().orElse(0.0);
        p1 = new Point(0, minX, maxY);
        p2 = new Point(0, maxX, minY);
        this.points = points;
    }

    public Rectangle(List<Point> points, Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.points = points
                .stream()
                .filter(
                        p ->
                                p.get(0) >= p1.get(0)
                                && p.get(0) <= p2.get(0)
                                && p.get(1) <= p1.get(1)
                                && p.get(1) >= p2.get(1))
                .collect(Collectors.toList());
    }


    public double getX1() {
        return p1.get(0);
    }

    public double getX2() {
        return p2.get(0);
    }

    public double getY1() {
        return p1.get(1);
    }

    public double getY2() {
        return p2.get(1);
    }

    public List<Point> getPoints() {
        return points;
    }

    public double getSize(int dim) {
        return Math.abs(p1.get(dim) - p2.get(dim));
    }

    public double getWidth() {
        return getSize(0);
    }

    public double getHeight() {
        return getSize(1);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public DivisionInfo findOptimalDivision(CostStrategy costStrategy, int slices) {
        DivisionInfo result = new DivisionInfo(null, null, -1, -1, Double.MAX_VALUE);
        for (int dim = 0; dim < p1.getDimension(); dim++) {
            double dd = getSize(dim)/slices;
            for (int slice = 1; slice < slices; slice++) {
                double dv = dim == 0 ? p1.get(dim) + dd*slice : p1.get(dim) - dd*slice ;
                final Point dp1 = dim == 0 ? new Point(0, dv, p2.get(1)) : new Point(0, p2.get(0), dv);
                final Point dp2 = dim == 0 ? new Point(0, dv, p1.get(1)) : new Point(0, p1.get(0), dv);
                Rectangle sub1 = new Rectangle(points, p1, dp1);
                Rectangle sub2 = new Rectangle(points, dp2, p2);
                double tempCost = costStrategy.getCost(sub1, sub2);
                if (tempCost < result.getDivisionCost()) {
                    result.setR1(sub1);
                    result.setR2(sub2);
                    result.setDivisionCost(tempCost);
                    result.setDivisionDimension(dim);
                    result.setDivisionValue(dv);
                }
            }
        }
        double allRectangleCost = costStrategy.getCost(this);
        if (costStrategy.shallDivide(allRectangleCost, result.getDivisionCost())) {
            return result;
        } else {
            return null;
        }
    }



    @Override
    public String toString() {
        return String.format("[%.3f, %.3f, %.3f, %.3f]", getX1(),getY1(),getX2(),getY2());
    }
//        if (points.size() == 0) {
//            return String.format("[%.3f, %.3f, %.3f, %.3f](Size: %d)", getX1(),getY1(),getX2(),getY2(), points.size());
//        }
//        Cluster cluster = new Cluster(points.get(0).getDimension());
//        for (Point p : points) {
//            cluster.add(p);
//        }
//        SimpleMatrix cov = cluster.getCov();
//        Point point = points.get(0);
//        Predicate<Point> theSameColor = p -> p.get(2) == point.get(2)
//                && p.get(3) == point.get(3)
//                && p.get(4) == point.get(4)
//                && p.get(5) == point.get(5);
//        boolean theSamePoints = points.stream().allMatch(theSameColor);
////        long distinct = points.stream().distinct().count();
//        return String.format("[%.3f, %.3f, %.3f, %.3f](Size: %d)(Diagonal: %.3f, %.3f, %.3f, %.3f)(Color: %.3f, %.3f, %.3f, %.3f)(%s)",
//                getX1(),getY1(),getX2(),getY2(), points.size(),
//                cov.get(2,2), cov.get(3,3), cov.get(4,4), cov.get(5,5),
//                point.get(2), point.get(3), point.get(4), point.get(5),
//                (theSamePoints ? "all the same" : "points differ"));
//    }

}
