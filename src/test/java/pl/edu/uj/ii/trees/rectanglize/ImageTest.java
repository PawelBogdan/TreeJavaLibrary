package pl.edu.uj.ii.trees.rectanglize;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.ui.ImageFrame;
import pl.edu.misztal.readers.Data;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.trees.kdtree.MauseDrawingTest;
import pl.edu.uj.ii.utils.EntropyCostStrategy;
import pl.edu.uj.ii.utils.Node;
import pl.edu.uj.ii.utils.Plot;
import pl.edu.uj.ii.utils.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

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

            Graphics2D graph = image.getBufferedImage().createGraphics();
            graph.setColor(Color.RED);
            graph.setStroke(new BasicStroke(1));

            Rectangle rec = root.getRectangle();
            graph.drawRect((int)rec.getX1(), image.getHeight()-(int)rec.getY1(), (int)rec.getWidth(), (int)rec.getHeight());
            graph.dispose();
            log.info(rec.toString());
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
        Rectangle r = new Rectangle(data.getData(), new pl.edu.misztal.data.Point(0, 0.0, 150.0), new pl.edu.misztal.data.Point(0, 249.0, 120.0));
//        Rectangle r = new Rectangle(data.getData());
        Image image = new Image("src/test/resources/data/img1.png");
        java.util.List<Rectangle> rectangleList = r.findOptimalDivision(new EntropyCostStrategy(), 5, image, Arrays.asList(Color.CYAN, Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED));
        System.out.println("-------------------------");
        System.out.println("Wynikowe prostokąty:");
        System.out.println("     " + rectangleList.get(0));
        System.out.println("     " + rectangleList.get(1));
        new ImageFrame(image).display();
        Thread.sleep(1000*60);
//        RectangelizeTree tree = new RectangelizeTreeImpl(data.getData(), 5, 10);
//        RectangleNode root = tree.getTree();
//        Plot plot = new Plot();
//        plot.addPoints(root.getValues(), Color.BLUE);
//        findRectangles(plot, root);
//        plot.disp();

//        Image image = new Image("src/test/resources/data/img1.png");
//        findRectanglesOnImage(image, root);
//        new ImageFrame(image).display();
//        Thread.sleep(1000*30);
    }
}