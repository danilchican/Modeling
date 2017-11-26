package com.bsuir.danilchican.modeling;

import java.util.Random;

class Queue {
    private final double lambda;
    private final double miu;
    private final double time;

    private long outCount = 0;
    private double counter1 = 0;
    private double nkCounter = 0;

    Queue(double lambda, double miu, int time) {
        this.lambda = lambda;
        this.miu = miu;
        this.time = time;
    }

    void run(double currentTime) {
        this.currentTime = currentTime;

        generateNextRequests();

        if (currentTime >= time) {
            return;
        }

        updateChannels();
    }

    private void generateNextRequests() {
        for (int i = 0; i < Main.N1; i++) {
            if (!sourceFlags[i]) {
                nextRequest(i);
            }
        }
    }

    private void nextRequest(int i) {
        sourceFlags[i] = true;
        double tmpTime = 0;

        for (int j = 0; j < Main.N2; j++) {
            if (i == channelFlags[j]) {
                tmpTime = channelTime[j];
            }
        }

        // time of having new order
        sourceTime[i] = tmpTime + -Math.log(new Random().nextDouble()) / lambda;
    }

    private void updateChannels() {
        double minTime = time;
        double tmpTime;

        for (int i = 0; i < Main.N2; i++) {
            tmpTime = handleRequest(i);

            if (tmpTime < minTime) {
                minTime = tmpTime;
            }
        }

        run(minTime);
    }

    private double handleRequest(int i) {
        int index = findIndexOfMin();

        // channel release time
        if (currentTime >= channelTime[i]) {
            sourceFlags[index] = false;
            outCount++;
            channelFlags[i] = index;

            double tmp = -Math.log(new Random().nextDouble()) / miu;
            counter1 += tmp;

            channelTime[i] = (sourceTime[index] > currentTime)
                    ? sourceTime[index] + tmp
                    : currentTime + tmp;

            nkCounter += channelTime[i] - sourceTime[index];
        }

        return channelTime[i];
    }

    private int findIndexOfMin() {
        int tmpIndex = 0;

        for (int i = 0; i < Main.N1; i++) {
            if ((sourceTime[tmpIndex] > sourceTime[i]) && sourceFlags[i]) {
                tmpIndex = i;
            }
        }

        return tmpIndex;
    }

    long getOutCount() {
        return outCount;
    }

    double getCounter1() {
        return counter1;
    }

    double getNkCounter() {
        return nkCounter;
    }

    private double currentTime = 0;

    private double[] sourceTime = {0, 0, 0, 0, 0, 0};
    private boolean[] sourceFlags = {false, false, false, false, false, false};

    private int[] channelFlags = {-1, -1};
    private double[] channelTime = {0, 0};
}
