package pl.edu.uj.ii.utils;

import org.ejml.simple.SimpleMatrix;

/**
 * @author Paweł Bogdan
 * Na podstawie: https://github.com/kmisztal/CEC/blob/master/src/main/java/cec/cluster/Point.java
 */
public class Point implements Comparable<Point> {
    private final SimpleMatrix x;
    private final double weight;

    public Point(double weight, double... x) {
        final int dim = x.length;
        this.weight = weight;
        this.x = new SimpleMatrix(dim, 1, true, x);
    }

    public Point(double weight, int dimension) {
        this.x = new SimpleMatrix(dimension, 1);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getDimension() {
        return x.numRows();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; i < x.numRows(); ++i) {
            ret.append(x.get(i, 0));
            ret.append(" ");
        }
        return ret.toString();
    }

    public double dist(Point p) {
        if (this.getDimension() != p.getDimension())
            throw new RuntimeException("Distance of points from different dimensions");
        double ret = 0;
        for (int i = 0; i < getDimension(); ++i) {
            ret += Math.pow(get(i) - p.get(i), 2.0);
        }
        return Math.sqrt(ret);
    }

    public double get(int i) {
        return x.get(i, 0);
    }

    @Override
    public int compareTo(Point t) {
        final int dim = getDimension();
        if (dim != t.getDimension()) {
            return 1;
        }
        for (int i = 0; i < dim; ++i) {
            if (get(i) != t.get(i)) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.weight, weight) != 0) return false;
        return x != null ? x.equals(point.x) : point.x == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = x != null ? x.hashCode() : 0;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
