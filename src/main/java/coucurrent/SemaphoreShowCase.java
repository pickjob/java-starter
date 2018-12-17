package coucurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

public class SemaphoreShowCase {
    private static final Logger logger = LogManager.getLogger(SemaphoreShowCase.class);

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(100);
        try {
            for (int i = 0; i < 105; i++) {
                semaphore.acquire();
                logger.info(semaphore.availablePermits());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
