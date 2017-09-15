package com.bsuir.modeling.index;

import com.bsuir.modeling.generator.EvenGenerator;
import com.bsuir.modeling.generator.ExponentialGenerator;
import com.bsuir.modeling.generator.GaussianGenerator;
import com.bsuir.modeling.generator.Generator;

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
             add(new EvenGenerator()); // A: -5; B: 15
             add(new GaussianGenerator()); // N: 6; Mx: 0.45; std: 0.33;
             add(new ExponentialGenerator()); // N: 50; Lambda: 5.68
        }};

        generators.forEach(Generator::input);
        generators.forEach(g -> {
            g.generate();
            g.print();
            g.showHistogram();
        });
    }
}
