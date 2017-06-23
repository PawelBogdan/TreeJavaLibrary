package pl.edu.uj.ii.trees.rectanglize;

import org.apache.log4j.Logger;
import org.ejml.simple.SimpleMatrix;
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

    public RectangelizeTreeImpl(List<Point> points, int slicesToCheck) throws WrongDimensionException {
        if (!points.stream().allMatch(p -> p.getDimension() == 2)) {
            throw new WrongDimensionException("Only 2D data available");
        }
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
        this.root = findPartition(new Rectangle(xMin, yMax, xMax, yMin), points, 0, 10);

    }

    private static double getCovarianceMatrix(List<Point> points) {
        SimpleMatrix result = new SimpleMatrix(2,2);
        double avgX = 0;
        double avgY = 0;
        for (Point p : points) {
            avgX += p.get(0);
            avgY += p.get(1);
        }
        avgX /= points.size();
        avgY /= points.size();

        double varX = 0;
        double varY = 0;
        for (Point p : points) {
            varX += (p.get(0) - avgX) * (p.get(0) - avgX);
            varY += (p.get(1) - avgY) * (p.get(1) - avgY);
        }
        varX /= points.size();
        varY /= points.size();

        double covXY = 0;
        for (int i = 0; i < points.size(); ++i) {
            for (int j = i+1; j < points.size(); ++j) {
                covXY += (points.get(i).get(0) - points.get(j).get(0)) *(points.get(i).get(1) - points.get(j).get(1));
            }
        }
        covXY /= (points.size()*points.size());
        result.set(0,0,varX);
        result.set(1,1,varY);
        result.set(0,1,covXY);
        result.set(1,0,covXY);
        return result.determinant();
    }

    protected RectangleNode findPartition(Rectangle rectangle, List<Point> points, int depth, int maxDepth) {
        if (depth == maxDepth) {
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

        for (int i = 1; i < slicesToCheck; i++) {
            double x1 = xMin;
            double x2 = xMin + i*smallWidth;
            double x3 = xMax;
            double y1 = yMax;
            double y2 = yMin;
            double leftWidth = i*smallWidth;
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
            double leftCost = leftWidth * height * getCovarianceMatrix(leftPoints);
            double rightCost = rightWidth * height * getCovarianceMatrix(rightPoints);
            if (leftCost*rightCost < minCost) {
                minCost = leftCost*rightCost;
                dimension = 0;
                slice = i;
            }
        }


        for (int i = 1; i < slicesToCheck; i++) {
            double y1 = yMax;
            double y2 = yMax - (i)*smallHeigh;
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
            double topCost = topHeight*width*getCovarianceMatrix(topPoints);
            double bottomCost = bottomHeight*width*getCovarianceMatrix(bottomPoints);
            if (topCost*bottomCost < minCost) {
                minCost = topCost*bottomCost;
                dimension = 1;
                slice = i;
            }
        }

        if (dimension == 0) {
            double x1 = xMin;
            double x2 = xMin + slice*smallWidth;
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
            Rectangle leftRectangle = new Rectangle(x1, y1, x2, y2);
            Rectangle rightRectangle = new Rectangle(x2, y1, x3, y2);
            RectangleNode topChild = findPartition(leftRectangle, leftPoints, depth+1, maxDepth);
            RectangleNode bottomChild = findPartition(rightRectangle, rightPoints, depth+1, maxDepth);
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
            double y2 = yMax - (slice)*smallHeigh;
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
            Rectangle topRectangle = new Rectangle(x1, y1, x2, y2);
            Rectangle bottomRectangle = new Rectangle(x1, y2, x2, y3);
            RectangleNode leftChild = findPartition(topRectangle, topPoints, depth+1, maxDepth);
            RectangleNode rightChild = findPartition(bottomRectangle, bottomPoints, depth+1, maxDepth);
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
