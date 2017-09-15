package com.bsuir.modeling.index;

import com.bsuir.modeling.generator.EvenGenerator;
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
            add(new EvenGenerator()); // A: -5; B:15
        }};

        generators.forEach(Generator::input);
        generators.forEach(g -> {
            g.generate();
            g.print();
            g.showHistogram();
        });
    }
}
