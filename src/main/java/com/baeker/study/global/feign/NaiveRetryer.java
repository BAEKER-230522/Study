package com.baeker.study.global.feign;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaiveRetryer implements Retryer {
    @Override
    public void continueOrPropagate(RetryableException e) {
        try {
            log.info("Retryer: {}", e.getMessage());
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    @Override
    public Retryer clone() {
        return new NaiveRetryer();
    }
}
