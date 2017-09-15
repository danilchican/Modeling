package com.bsuir.modeling.index;

import com.bsuir.modeling.data.InputManager;
import com.bsuir.modeling.drawing.Graph;
import com.bsuir.modeling.drawing.Histogram;
import com.bsuir.modeling.generator.Generator;

import static com.bsuir.modeling.data.Printer.print;
import static com.bsuir.modeling.data.Printer.printData;

public class Main {

    /**
     * Main func.
     *
     * @param args
     */
    public static void main(String[] args) {
        // 100 000
        // 127
        // 2048
        // 53,93
        InputManager manager = new InputManager();
        Generator generator = manager.getGenerator();

        manager.input();
        generator.generateValues();
        generator.calculateHistogram();

        printData(generator);

        Graph histogram = new Histogram("Histogram", generator.getHistogramData());
        histogram.show();

        generator.checkComparingRange();
       // generator.round();

        print("Period: " + generator.calculatePeriod());
        print("Aperiod: " + generator.calculateAperiod());
    }
}
