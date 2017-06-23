package pl.edu.uj.ii.trees.kdtree;


import pl.edu.uj.ii.exceptions.WrongDimensionException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Paweł Bogdan
 */
public class KDTreeFactory {

    private static void checkPoints(List<Point> points) throws WrongDimensionException {
        if (points.size() == 0) {
            throw new IllegalArgumentException("Input list is empty");
        }
        int dimension = points.get(0).getDimension();
        if (!points.stream().allMatch(p-> p.getDimension() == dimension)) {
            throw new WrongDimensionException("At least one point has inproper dimension");
        }
    }

    public static KDTree createSimpleBinaryKDTree(List<Point> points) throws WrongDimensionException {
        checkPoints(points);
        return new AbstractKDTree(points) {
            @Override
            protected int getDivisionCoordinate(List<Point> points, int depth) {
                return depth % dimension;
            }

            @Override
            protected List<Integer> getDivisionIndecies(List<Point> points, int divisionCoordinate, int depth) {
                return Arrays.asList((points.size() % 2 == 0 ? points.size() / 2 : (points.size() + 1) / 2));
            }
        };
    }
}
