package testtask.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Gold {
    private AtomicInteger gold = new AtomicInteger(0);

    public int change(int payment) {
        return gold.addAndGet(payment);
    }

    public int get() {
        return gold.get();
    }
}
