package pl.edu.uj.ii.utils;

import pl.edu.misztal.data.Cluster;
import pl.edu.misztal.data.Point;

import java.util.List;

/**
 * Created by krzys on 30.06.2017.
 */
public class Costs {
    public static double covarianceCost(double width, double height, List<Point> points) {
        if (points.size() == 0) return 0;
        Cluster cluster = new Cluster(points.get(0).getDimension());
        for (Point p : points) {
            cluster.add(p);
        }
        return width * height * cluster.getCov().get(3, 3);
    }


    public static double entropyCost(double width, double height, List<Point> points) {
        int[] hist = histogram(points, 3);
        double denominator = width * height;
        double ret = 0;
        for (int i : hist) {
            if (i != 0) {
                ret += (i / denominator) * Math.log(i / denominator);
            }
        }
        return -ret;
    }

    private static int[] histogram(List<Point> points, int colorPos) {
        int[] hist = new int[256];
        for (Point p : points) {
            hist[(int) (p.get(colorPos))]++;
        }
        return hist;
    }


}
