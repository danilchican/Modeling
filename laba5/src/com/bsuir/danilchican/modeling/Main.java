package com.bsuir.danilchican.modeling;

public class Main {
    static final int N1 = 6;
    static final int N2 = 2;

    private static final int MIU = 9;
    private static final double LAMBDA = 2.5;

    public static void main(String[] args) {
        Queue queue = new Queue(LAMBDA, MIU, CYCLES);
        queue.run(0);

        print(queue);
    }

    private static void print(Queue queue) {
        System.out.println("Lc (ср. кол-во ожидающих источн.): " + (queue.getNkCounter() / (double) CYCLES));
        System.out.println("A (абс. пропуск. способность): " + (queue.getOutCount() / (double) CYCLES));
        System.out.println("Wc (ср. вр. ожидания ответа): " + (queue.getCounter1() / queue.getOutCount()));
    }

    private static final int CYCLES = 400;
}