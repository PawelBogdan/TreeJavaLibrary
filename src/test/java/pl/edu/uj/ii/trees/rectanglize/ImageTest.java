package pl.edu.uj.ii.trees.rectanglize;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.ui.ImageFrame;
import pl.edu.misztal.readers.Data;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.trees.kdtree.MauseDrawingTest;
import pl.edu.uj.ii.utils.*;
import pl.edu.uj.ii.utils.Rectangle;


import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by krzys on 23.06.2017.
 */
public class ImageTest {
    Logger log = Logger.getLogger(MauseDrawingTest.class);

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

    private void findRectanglesOnImage(Image image, RectangleNode root) {
        if (root.getChildren().isEmpty()) {
//            image.addRectangle(root.getRectangle(), Color.RED);

            Graphics graph = image.getBufferedImage().getGraphics();
            graph.setColor(Color.RED);
            Rectangle rec = root.getRectangle();
            graph.drawRect((int)rec.getX1(), image.getHeight()-(int)rec.getY1(), (int)rec.getWidth(), (int)rec.getHeight());
            graph.dispose();
            System.out.println(rec);
        } else {
            for (Node n : root.getChildren()) {
                RectangleNode rn = (RectangleNode) n;
                findRectanglesOnImage(image, rn);
            }
        }
    }



    @Test
    public void imageTest() throws IOException, InterruptedException, WrongDimensionException {
        Data data = new Data();
        data.read("src/test/resources/data/img1.png", Data.DataType.IMAGE_PNG.getIdentifier());
        RectangelizeTree tree = new RectangelizeTreeImpl(data.getData(), 5, 5);
        RectangleNode root = tree.getTree();
//        Plot plot = new Plot();
//        plot.addPoints(root.getValues(), Color.BLUE);
//        findRectangles(plot, root);
//        plot.disp();

        Image image = new Image("src/test/resources/data/img1.png");
        findRectanglesOnImage(image, root);
        new ImageFrame(image).display();
        Thread.sleep(1000*15);
    }
}