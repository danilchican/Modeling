package com.bsuir.danilchican.modeling.node;

import java.util.Random;

public class RequestDiscardingChannel extends RequestChannel {

    private long countOfDiscard = 0;

    public RequestDiscardingChannel(double pi) {
        setPi(pi);
    }

    public long getCountOfDiscard() {
        return countOfDiscard;
    }

    @Override
    public void process() {
        if (next == null) {
            return;
        }

        next.process();

        if (getRequest() != null) {
            if (!next.isFull()) {
                if (new Random().nextFloat() <= 1.0 - getPi()) {
                    next.activate(getRequest());
                    setRequest(null);
                }
            } else {
                if (new Random().nextFloat() <= 1.0 - getPi()) {
                    setRequest(null);
                    countOfDiscard++;
                }
            }
        }
    }
}