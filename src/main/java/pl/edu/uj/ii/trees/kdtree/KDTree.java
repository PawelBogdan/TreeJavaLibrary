package pl.edu.uj.ii.trees.kdtree;


import pl.edu.uj.ii.exceptions.NoChildFoundException;
import pl.edu.uj.ii.exceptions.TreeNotConstructedYetException;
import pl.edu.uj.ii.exceptions.WrongDimensionException;

/**
 * @author Paweł Bogdan
 */
public interface KDTree {
    Point findNearestNeighbour(Point p) throws TreeNotConstructedYetException, WrongDimensionException, NoChildFoundException;
}
