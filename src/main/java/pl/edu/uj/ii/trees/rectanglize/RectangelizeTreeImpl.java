package pl.edu.uj.ii.trees.rectanglize;

import org.apache.log4j.Logger;
import pl.edu.misztal.data.Cluster;
import pl.edu.misztal.data.Point;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Paweł Bogdan
 */
public class RectangelizeTreeImpl implements RectangelizeTree {

    protected RectangleNode root;
    protected int dimension;
    protected List<Point> points;
    protected int slicesToCheck;
    Logger log = Logger.getLogger(RectangelizeTreeImpl.class);

    public RectangelizeTreeImpl(List<Point> points, int slicesToCheck, int maxDepth) throws WrongDimensionException {
//        if (!points.stream().allMatch(p -> p.getDimension() == 2)) {
//            throw new WrongDimensionException("Only 2D data available");
//        }
        this.points = points;
        this.slicesToCheck = slicesToCheck;
        Point tempPoint = points.get(0);
        double xMin = tempPoint.get(0);
        double xMax = tempPoint.get(0);
        double yMin = tempPoint.get(1);
        double yMax = tempPoint.get(1);
        for (Point p : points) {
            double x = p.get(0);
            double y = p.get(1);
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
        }
        this.root = findPartition(new Rectangle(points, getCost(xMax-xMin, yMax-yMin, points), xMin, yMax, xMax, yMin), points, 0, maxDepth);

    }

    private static double getCost(double width, double height, List<Point> points) {
        if (points.size() == 0) return 0;
        Cluster cluster = new Cluster(points.get(0).getDimension());
        for (Point p : points) {
            cluster.add(p);
        }
        return width * height * cluster.getCov().get(3, 3);
    }

    private static double getCostCompare(double firstCost, double secondCost) {
//        return firstCost + secondCost;
        return firstCost * secondCost;
//        return Math.min(firstCost, secondCost);
    }

    protected RectangleNode findPartition(Rectangle rectangle, List<Point> points, int depth, int maxDepth) {
        if (depth >= maxDepth) {
            log.info("Max depth reached, returning null");
            return new RectangleNode(rectangle, points, -1, new ArrayList<>(), new ArrayList<>());
        }
        if (points.isEmpty()) {
            log.info("There is no point so there is no sense in divididing area");
            return null;
        }
        log.info("Starting partition, for " + points.size() + " points. Depth: " + depth);
        double xMin = rectangle.getX1();
        double xMax = rectangle.getX2();
        double yMax = rectangle.getY1();
        double yMin = rectangle.getY2();
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();
        log.info(String.format("Considering big rectangle [(%f, %f), (%f, %f)]", xMin, yMax, xMax, yMin));
        log.info(String.format("Its size: (%f, %f)", width, height));
        double smallWidth = width / slicesToCheck;
        double smallHeigh = height / slicesToCheck;

        double minCost = Double.MAX_VALUE;
        double dimension = -1;
        double slice = -1;
        double fCost = 0;
        double sCost = 0;

        for (int i = 1; i < slicesToCheck; i++) {
            double x1 = xMin;
            double x2 = xMin + i * smallWidth;
            double x3 = xMax;
            double y1 = yMax;
            double y2 = yMin;
            double leftWidth = i * smallWidth;
            double rightWidth = width - leftWidth;
            List<Point> leftPoints = new ArrayList<>();
            List<Point> rightPoints = new ArrayList<>();
            for (Point p : points) {
                if (p.get(0) <= x2) {
                    leftPoints.add(p);
                } else {
                    rightPoints.add(p);
                }
            }
            double leftCost = getCost(leftWidth, height, leftPoints);
            double rightCost = getCost(rightWidth, height, rightPoints);
            if (getCostCompare(leftCost, rightCost) < minCost) {
                minCost = getCostCompare(leftCost, rightCost);
                dimension = 0;
                slice = i;
                fCost = leftCost;
                sCost = rightCost;
            }
        }


        for (int i = 1; i < slicesToCheck; i++) {
            double y1 = yMax;
            double y2 = yMax - (i) * smallHeigh;
            double y3 = yMin;
            double x1 = xMax;
            double x2 = xMin;
            double topHeight = i * smallHeigh;
            double bottomHeight = height - topHeight;
            List<Point> topPoints = new ArrayList<>();
            List<Point> bottomPoints = new ArrayList<>();
            for (Point p : points) {
                if (p.get(1) >= y2) {
                    topPoints.add(p);
                } else {
                    bottomPoints.add(p);
                }
            }
            double topCost = getCost(width, topHeight, topPoints);
            double bottomCost = getCost(width, bottomHeight, bottomPoints);
            if (getCostCompare(topCost, bottomCost) < minCost) {
                minCost = getCostCompare(topCost, bottomCost);
                dimension = 1;
                slice = i;
                fCost = topCost;
                sCost = bottomCost;
            }
        }

        if (dimension == 0) {
            double x1 = xMin;
            double x2 = xMin + slice * smallWidth;
            double x3 = xMax;
            double y1 = yMax;
            double y2 = yMin;
            List<Point> leftPoints = new ArrayList<>();
            List<Point> rightPoints = new ArrayList<>();
            for (Point p : points) {
                if (p.get(0) <= x2) {
                    leftPoints.add(p);
                } else {
                    rightPoints.add(p);
                }
            }
            Rectangle leftRectangle = new Rectangle(leftPoints, fCost, x1, y1, x2, y2);
            Rectangle rightRectangle = new Rectangle(rightPoints, sCost, x2, y1, x3, y2);
            RectangleNode topChild = null;
            topChild = findPartition(leftRectangle, leftPoints, depth + 1, maxDepth);
            RectangleNode bottomChild = null;
            bottomChild = findPartition(rightRectangle, rightPoints, depth + 1, maxDepth);
            List<Node> children = new ArrayList<>();
            if (topChild != null) {
                children.add(topChild);
            }
            if (bottomChild != null) {
                children.add(bottomChild);
            }
            return new RectangleNode(rectangle, points, 0, Arrays.asList(x2), children);

        } else {
            double y1 = yMax;
            double y2 = yMax - (slice) * smallHeigh;
            double y3 = yMin;
            double x1 = xMin;
            double x2 = xMax;
            List<Point> topPoints = new ArrayList<>();
            List<Point> bottomPoints = new ArrayList<>();
            for (Point p : points) {
                if (p.get(1) >= y2) {
                    topPoints.add(p);
                } else {
                    bottomPoints.add(p);
                }
            }
            Rectangle topRectangle = new Rectangle(topPoints, fCost, x1, y1, x2, y2);
            Rectangle bottomRectangle = new Rectangle(bottomPoints, sCost, x1, y2, x2, y3);
            RectangleNode leftChild = null;
//            if (leftCov > 0.084)
            leftChild = findPartition(topRectangle, topPoints, depth + 1, maxDepth);
            RectangleNode rightChild = null;
//            if (rightCov > 0.084)
            rightChild = findPartition(bottomRectangle, bottomPoints, depth + 1, maxDepth);
            List<Node> children = new ArrayList<>();
            if (leftChild != null) {
                children.add(leftChild);
            }
            if (rightChild != null) {
                children.add(rightChild);
            }
            return new RectangleNode(rectangle, points, 1, Arrays.asList(y2), children);
        }
    }

    @Override
    public RectangleNode getTree() {
        return root;
    }

}
