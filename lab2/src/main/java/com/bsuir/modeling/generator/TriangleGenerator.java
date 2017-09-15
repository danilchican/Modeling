package com.bsuir.modeling.generator;

import com.bsuir.modeling.drawing.Histogram;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class TriangleGenerator extends EvenGenerator {

    private static final int COUNT_R = 2;

    /**
     * Print generator data.
     */
    @Override
    public void print() {
        super.print();
    }

    @Override
    protected double getRandomNumber() {
        List<Double> rList = new ArrayList<>();

        for (int i = 0; i < COUNT_R; i++) {
            double number = a * r0;

            setR0(number % m);
            rList.add(r0 / m);
        }

        OptionalDouble max = rList.stream().mapToDouble(Double::doubleValue).max();

        return a + (b - a) * max.getAsDouble();
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("TriangleGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        super.input();
    }

    @Override
    protected boolean isValidValues() {
        return super.isValidValues();
    }
}
