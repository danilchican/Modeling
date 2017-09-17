package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Histogram;

public class EvenGenerator extends Generator {

    /**
     * Interval of numbers.
     */
    protected double a;
    protected double b;

    private final int aMG = 127;
    private final int m = 2048;
    private double r0 = 53.73;

    protected void setA(double a) {
        this.a = a;
    }

    protected void setB(double b) {
        this.b = b;
    }

    private void setR0(double r0) {
        this.r0 = r0;
    }

    protected double getA() {
        return a;
    }

    protected double getB() {
        return b;
    }

    /**
     * Print generator data.
     */
    @Override
    public void print() {
        Printer.print(this.getClass().getName() + ":");
        Printer.print("A = " + getA() + ", B = " + getB());

        super.print();
    }

    @Override
    protected double getRandomNumber() {
        double number = aMG * r0;
        setR0(number % m);
        number = r0 / m;

        return a + (b - a) * number;
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("EvenGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        Printer.print(this.getClass().getName() + ":");

        Printer.print("Input A: ");
        setA(in.nextDouble());

        Printer.print("Input B: ");
        setB(in.nextDouble());

        if (!isValidValues()) {
            Printer.print("Your data is invalid. Try again.");
            input();
        }
    }

    @Override
    protected boolean isValidValues() {
        return a < b;
    }
}
