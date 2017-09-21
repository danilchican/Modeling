package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Histogram;

public class ExponentialGenerator extends Generator {

    /**
     * Params to generate random numbers.
     */
    private final int aMG = 1567;
    private final int m = 68030;
    private double r0 = 1567;

    private double lambda;

    private void setR0(double r0) {
        this.r0 = r0;
    }

    private void setLambda(double lambda) {
        this.lambda = lambda;
    }

    private double getLambda() {
        return lambda;
    }

    /**
     * Print generator data.
     */
    @Override
    public void print() {
        Printer.print(this.getClass().getName() + ":");
        Printer.print("Lambda = " + getLambda());

        super.print();
    }

    @Override
    protected double getRandomNumber() {
        double number = aMG * r0;
        setR0(number % m);

        return -Math.log(r0 / m) / lambda;
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("ExponentialGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        Printer.print(this.getClass().getName() + ":");

        Printer.print("Input Lambda: ");
        setLambda(in.nextDouble());

        if (!isValidValues()) {
            Printer.print("Your data is invalid. Try again.");
            input();
        }
    }

    @Override
    protected boolean isValidValues() {
        return lambda > 0;
    }
}
