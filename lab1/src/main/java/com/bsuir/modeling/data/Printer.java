package com.bsuir.modeling.data;

import com.bsuir.modeling.generator.Generator;

public class Printer {

    /**
     * Print data to screen.
     *
     * @param message
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * Print all generator data.
     *
     * @param generator
     */
    public static void printData(Generator generator) {
        print("A: " + generator.getA());
        print("M: " + generator.getM());

        print("Mx: " + generator.calculateMx());
        print("Dx: " + generator.calculateDx());
        print("Std: " + generator.calculateStd());

        // print("Values: " + generator.getValues());
        print("Histogram data: " + generator.getHistogramData());
    }
}