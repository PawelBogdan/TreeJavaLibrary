package pl.edu.uj.ii.trees.kdtree;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.edu.misztal.readers.Data;
import pl.edu.uj.ii.utils.Plot;

import java.awt.*;
import java.io.IOException;

/**
 * @author Paweł Bogdan
 */
public class MauseDrawingTest {
    Logger log = Logger.getLogger(MauseDrawingTest.class);

    @Test
    public void drawMause() throws IOException, InterruptedException {
        Data data = new Data();
        data.read("src/test/resources/data/mause.txt", Data.DataType.TEXT_SPACE.getIdentifier());
        Plot plot = new Plot();
        plot.addPoints(data.getData(), Color.BLUE);
        plot.disp();
        Thread.sleep(1000*30);
    }

}
