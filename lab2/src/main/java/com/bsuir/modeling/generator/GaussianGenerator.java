package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Histogram;

import java.util.ArrayList;
import java.util.List;

public class GaussianGenerator extends Generator {

    /**
     * Input params.
     */
    private int n = 6;
    private double Mx = 0.45;
    private double std = 0.33;

    /**
     * Params to generate random numbers.
     */
    private final int a = 127;
    private final int m = 2048;
    private double r0 = 53.73;

    private static final int MAX_N = 12;

    private void setN(int n) {
        this.n = n;
    }

    private void setMx(double mx) {
        Mx = mx;
    }

    private void setStd(double std) {
        this.std = std;
    }

    private void setR0(double r0) {
        this.r0 = r0;
    }

    private int getN() {
        return n;
    }

    private double getMx() {
        return Mx;
    }

    private double getStd() {
        return std;
    }

    @Override
    public double calculateMx() {
        return getMx();
    }

    /**
     * Calculate Dispersion value.
     *
     * @return Dx
     */
    @Override
    public double calculateDx() {
        return Math.pow(getStd(), N_POW);
    }

    /**
     * Print generator data.
     */
    @Override
    public void print() {
        Printer.print(this.getClass().getName() + ":");
        Printer.print("N = " + getN());

        super.print();
    }

    @Override
    protected double getRandomNumber() {
        List<Double> rList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double number = a * r0;

            setR0(number % m);
            rList.add(r0 / m);
        }

        double sum = rList.stream().mapToDouble(Double::doubleValue).sum();

        return getMx() + getStd() * Math.sqrt(MAX_N / getN())
                * (sum - getN() / 2);
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("GaussianGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        Printer.print(this.getClass().getName() + ":");

        Printer.print("Input N: ");
        setN(in.nextInt());

        Printer.print("Input Mx: ");
        setMx(in.nextDouble());

        Printer.print("Input Std: ");
        setStd(in.nextDouble());

        if (!isValidValues()) {
            Printer.print("Your data is invalid. Try again.");
            input();
        }
    }

    @Override
    protected boolean isValidValues() {
        return n >= 0 && n <= MAX_N && Mx > 0 && std > 0;
    }
}
