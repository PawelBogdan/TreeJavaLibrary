package pl.edu.uj.ii.trees.kdtree;

import org.junit.Test;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.trees.rectanglize.RectangelizeTree;
import pl.edu.uj.ii.trees.rectanglize.RectangelizeTreeImpl;
import pl.edu.uj.ii.trees.rectanglize.RectangleNode;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Plot;
import pl.edu.uj.ii.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Paweł Bogdan
 */
public class MauseRectanglizeTest {

    private void findRectangles(Plot plot, RectangleNode root) {
        if (root.getChildren().isEmpty()) {
            plot.addRectangle(root.getRectangle(), Color.RED);
        } else {
            for (Node n : root.getChildren()) {
                RectangleNode rn = (RectangleNode) n;
                findRectangles(plot, rn);
            }
        }
    }

    @Test
    public void test() throws IOException, WrongDimensionException {
//        String filename = "src/test/resources/data/mause.txt";
        String filename = "src/test/resources/data/rec.csv";
        RectangelizeTree tree = new RectangelizeTreeImpl(Utils.readPoints(filename), 5);
        RectangleNode root = tree.getTree();
        Plot plot = new Plot();
        plot.addPoints(root.getValues(), Color.BLUE);
        findRectangles(plot, root);
        plot.disp();
        Scanner sc = new Scanner(System.in);
        sc.next();
    }
}
