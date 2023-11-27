package unam.darthsoviet.com.util;



import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Timer {
    ThreadLocal<Deque<Instant>> instants = ThreadLocal.withInitial(LinkedList::new);

    public void start() {
        instants.get().push(Instant.now());
    }

    public void end() {
        Instant instant = instants.get().pop();
        if (instant == null) {
            throw new RuntimeException("not start time");

        } else {
            System.out.println(Thread.currentThread().getId() +" : "+ Duration.between(instant, Instant.now()).get(ChronoUnit.SECONDS)+"s");
        }
    }
}
