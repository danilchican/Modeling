package com.bsuir.modeling.data;

import com.bsuir.modeling.generator.Generator;

import java.util.Scanner;

import static com.bsuir.modeling.data.Printer.print;

public class InputManager {

    private Scanner in = new Scanner(System.in);

    private Generator generator = new Generator();

    /**
     * Enter starting data.
     */
    public void input() {
        print("Input A: ");
        generator.setA(in.nextInt());

        print("Input M: ");
        generator.setM(in.nextInt());

        print("Input R0: ");
        generator.setR0(in.nextFloat());

        if (!generator.isValidValues()) {
            input();
        }
    }

    /**
     * Get generator instance.
     *
     * @return generator.
     */
    public Generator getGenerator() {
        return generator;
    }
}
