package pl.edu.uj.ii.utils;

import java.util.Random;

/**
 * @author Paweł Bogdan
 * Na podstawie: https://github.com/kmisztal/CEC/blob/master/src/main/java/cec/cluster/Point.java
 */
public class Utils {
    private final static Random RANDOM = new Random(System.currentTimeMillis());

//    public static Point createRandomPoint(int weight, int dimension, double boundary) {
//        double coordinates[] = new double[dimension];
//        for (int i = 0; i < dimension; ++i) {
//            coordinates[i] = RANDOM.nextDouble() * boundary;
//        }
//        return new Point(weight, coordinates);
//    }
//
//    public static List<Point> readPoints(String path) throws IOException {
//        Path p = Paths.get(path);
//        List<String> lines = Files.readAllLines(p);
//        List<Point> points = new ArrayList<>();
//        for (String line : lines) {
//            String[] s = line.split(" ");
//            double x = Double.parseDouble(s[0]);
//            double y = Double.parseDouble(s[1]);
//            Point point = new Point(1, x, y);
//            points.add(point);
//        }
//        return points;
//    }
}
