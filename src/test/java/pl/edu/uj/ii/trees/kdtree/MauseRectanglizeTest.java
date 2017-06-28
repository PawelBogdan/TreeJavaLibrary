package pl.edu.uj.ii.trees.kdtree;

import org.junit.Test;
import pl.edu.misztal.readers.Data;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.trees.rectanglize.RectangelizeTree;
import pl.edu.uj.ii.trees.rectanglize.RectangelizeTreeImpl;
import pl.edu.uj.ii.trees.rectanglize.RectangleNode;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Plot;

import java.awt.*;
import java.io.IOException;

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
    public void test() throws IOException, WrongDimensionException, InterruptedException {
        Data data = new Data();
        data.read("src/test/resources/data/mause.txt", Data.DataType.TEXT_SPACE.getIdentifier());
        RectangelizeTree tree = new RectangelizeTreeImpl(data.getData(), 5, 10);
        RectangleNode root = tree.getTree();
        Plot plot = new Plot();
        plot.addPoints(root.getValues(), Color.BLUE);
        findRectangles(plot, root);
        plot.disp();
        Thread.sleep(1000*15);
    }
}
