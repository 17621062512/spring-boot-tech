package eight;

import com.codahale.metrics.Counter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricTest {
    private static Counter counter = new Counter();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            counter.inc();
            if (counter.getCount() > 7L) {
                log.error("");
                counter = new Counter();
            }
            log.info(String.valueOf(counter.getCount()));
        }
    }
}
