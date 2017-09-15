package com.bsuir.danilchican.index;

import com.bsuir.danilchican.generator.EvenGenerator;
import com.bsuir.danilchican.generator.Generator;

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
            add(new EvenGenerator());
        }};

        generators.forEach(Generator::input);
        generators.forEach(g -> {
            g.generate();
            g.print();
            g.showHistogram();
        });
    }
}
