package pl.edu.uj.ii.utils;

import java.util.Random;

/**
 * @author Paweł Bogdan
 * Na podstawie: https://github.com/kmisztal/CEC/blob/master/src/main/java/cec/cluster/Point.java
 */
public class Utils {
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static Point createRandomPoint(int weight, int dimension, double boundary) {
        double coordinates[] = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            coordinates[i] = RANDOM.nextDouble() * boundary;
        }
        return new Point(weight, coordinates);
    }
}
