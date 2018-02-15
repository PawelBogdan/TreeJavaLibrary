package pl.edu.uj.ii.utils;

public class DivisionInfo {
    private Rectangle r1;
    private Rectangle r2;
    private double divisionValue;
    private int divisionDimension;
    private double divisionCost;

    public DivisionInfo(Rectangle r1, Rectangle r2, double divisionValue, int divisionDimension, double divisionCost) {
        this.r1 = r1;
        this.r2 = r2;
        this.divisionValue = divisionValue;
        this.divisionDimension = divisionDimension;
        this.divisionCost = divisionCost;
    }

    public Rectangle getR1() {
        return r1;
    }

    public void setR1(Rectangle r1) {
        this.r1 = r1;
    }

    public Rectangle getR2() {
        return r2;
    }

    public void setR2(Rectangle r2) {
        this.r2 = r2;
    }

    public double getDivisionValue() {
        return divisionValue;
    }

    public void setDivisionValue(double divisionValue) {
        this.divisionValue = divisionValue;
    }

    public int getDivisionDimension() {
        return divisionDimension;
    }

    public void setDivisionDimension(int divisionDimension) {
        this.divisionDimension = divisionDimension;
    }

    public double getDivisionCost() {
        return divisionCost;
    }

    public void setDivisionCost(double divisionCost) {
        this.divisionCost = divisionCost;
    }
}
