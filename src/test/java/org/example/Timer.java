package org.example;

import java.time.Duration;
import java.time.Instant;

public class Timer {

    private Instant init;

    public void start() {
        init = Instant.now();
    }

    public void end() {
        if (init == null) {
            throw new RuntimeException("not start time");
        } else {
            System.out.println(Duration.between(init, Instant.now()).getSeconds());
        }
    }
}
