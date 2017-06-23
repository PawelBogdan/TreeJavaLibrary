package pl.edu.uj.ii.trees.kdtree;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.edu.misztal.readers.Data;

import java.io.IOException;

/**
 * @author Paweł Bogdan
 */
public class MauseDrawingTest {
    Logger log = Logger.getLogger(MauseDrawingTest.class);

    @Test
    public void drawMause() throws IOException {
        Data data = new Data();
        data.read("src/test/resources/data/mause.txt", Data.DataType.TEXT_SPACE.getIdentifier());
    }

}
