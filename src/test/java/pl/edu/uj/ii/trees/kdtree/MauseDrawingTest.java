package pl.edu.uj.ii.trees.kdtree;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.edu.uj.ii.utils.Plot;
import pl.edu.uj.ii.utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * @author Paweł Bogdan
 */
public class MauseDrawingTest {
    Logger log = Logger.getLogger(MauseDrawingTest.class);

    @Test
    public void drawMause() throws IOException {
        List<Point> points = Utils.readPoints("src/test/resources/data/mause.txt");
        Plot plot = new Plot();
        plot.disp();
    }

}
