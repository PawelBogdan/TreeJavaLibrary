package pl.edu.uj.ii.trees.kdtree;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.uj.ii.exceptions.NoChildFoundException;
import pl.edu.uj.ii.exceptions.TreeNotConstructedYetException;
import pl.edu.uj.ii.exceptions.WrongDimensionException;
import pl.edu.uj.ii.utils.Point;
import pl.edu.uj.ii.utils.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(value = Parameterized.class)
public class SimpleBinaryKDTreeRandomTest {
    Logger log = Logger.getLogger(SimpleBinaryKDTreeRandomTest.class);

    @Parameterized.Parameter(value = 0)
    public int setSize;

    @Parameterized.Parameter(value = 1)
    public int trials;

    @Parameterized.Parameters(name = "{index}: setSize = {0}, trials = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {10,10}, {100, 100}, {1000, 1000}, {10000, 10000}, {20000, 20000}
        });
    }

    @Test
    public void randomParametrizedTest() throws WrongDimensionException, TreeNotConstructedYetException, NoChildFoundException {
        log.info("Test for size: " + setSize + " and " + trials + " trials.");
        LocalTime startTime = LocalTime.now();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < setSize; ++i) {
            points.add(Utils.createRandomPoint(1, 3, 10));
        }
        LocalTime endTime = LocalTime.now();

        log.info(String.valueOf(setSize) + " points chosen. Duration: " + Duration.between(startTime, endTime).get(ChronoUnit.NANOS));


        log.info("Creating KD-tree");
        startTime = LocalTime.now();
        KDTree tree = KDTreeFactory.createSimpleBinaryKDTree(points);
        endTime = LocalTime.now();
        log.info("KD-tree created. Duration: " + Duration.between(startTime, endTime).get(ChronoUnit.NANOS));



        long classicalTime = 0;
        long treeTime = 0;
        for (int i = 0; i < trials; i++) {

            Point p = Utils.createRandomPoint(1, 3, 10);

            startTime = LocalTime.now();
            double dist = Double.MAX_VALUE;
            Point nearestPoint = null;
            for (Point point : points) {
                double d = point.dist(p);
                if (d < dist) {
                    dist = d;
                    nearestPoint = point;
                }
            }
            endTime = LocalTime.now();
            classicalTime += Duration.between(startTime, endTime).get(ChronoUnit.NANOS);
            Point expectedResult = nearestPoint;

            startTime = LocalTime.now();
            nearestPoint = tree.findNearestNeighbour(p);
            endTime = LocalTime.now();
            treeTime += Duration.between(startTime, endTime).get(ChronoUnit.NANOS);
            Assert.assertEquals(expectedResult, nearestPoint);
        }
        log.info("Avarege time for classical way: " + ((float)classicalTime / trials));
        log.info("Avarege time for KD tree way: " + ((float)treeTime / trials));
    }
}
