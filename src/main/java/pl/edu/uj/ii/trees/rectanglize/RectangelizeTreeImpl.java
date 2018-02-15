package pl.edu.uj.ii.trees.rectanglize;

import org.apache.log4j.Logger;
import pl.edu.misztal.data.Point;
import pl.edu.uj.ii.utils.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paweł Bogdan
 */
public class RectangelizeTreeImpl implements RectangelizeTree {

    static Logger log = Logger.getLogger(RectangelizeTreeImpl.class);
    protected RectangleNode root;
    protected int slicesToCheck;

    public RectangelizeTreeImpl(List<Point> points, int slicesToCheck, int maxDepth) {
        this.slicesToCheck = slicesToCheck;
        this.root = findPartition(new Rectangle(points), 0, maxDepth);

    }

    protected RectangleNode findPartition(Rectangle rectangle, int depth, int maxDepth) {
        if (depth >= maxDepth) {
            log.info("Max depth reached, returning null");
            return new RectangleNode(rectangle);
        }
        if (rectangle.getPoints().isEmpty()) {
            log.info("There is no point so there is no sense in divididing area");
            return null;
        }
        log.info("Starting partition, for " + rectangle.getPoints().size() + " points. Depth: " + depth);
        log.info(String.format("Considering big rectangle %s", rectangle.toString()));
        DivisionInfo division = rectangle.findOptimalDivision(new EntropyCostStrategy(), slicesToCheck);
        if (division == null) {
            return new RectangleNode(rectangle);
        } else {
            RectangleNode node1 = findPartition(division.getR1(), depth+1, maxDepth);
            RectangleNode node2 = findPartition(division.getR2(), depth + 1, maxDepth);
            List<Node> children = Stream.of(node1, node2).filter(n -> n != null).collect(Collectors.toList());
            List<Double> divisionValues = Arrays.asList(division.getDivisionValue());
            return new RectangleNode(rectangle, division.getDivisionDimension(), divisionValues, children);
        }
    }

    @Override
    public RectangleNode getTree() {
        return root;
    }

}
