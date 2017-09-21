package com.bsuir.modeling.index;

import com.bsuir.modeling.generator.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Main func.
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Generator> generators = new ArrayList<Generator>() {{
            add(new EvenGenerator()); // A: -0,7; B: -0,5
            add(new GaussianGenerator()); // N: 6; Mx: 80; std: 40
            add(new ExponentialGenerator()); // Lambda: 0,01
            add(new GammaGenerator()); // N: 8; Lambda: 8
            add(new TriangleGenerator()); // A: 0; B: 10
            add(new SimpsonGenerator()); // A: -10; B: 0
        }};

        generators.forEach(Generator::input);
        generators.forEach(g -> {
            g.generate();
            g.print();
            g.showHistogram();
        });
    }
}
