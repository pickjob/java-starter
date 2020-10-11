package basic.wait;


import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitNotifyShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(WaitNotifyShowCase.class);

    @Override
    public void showSomething() {
        logger.info("展示基础wait notify使用(不推荐使用)");
        final String[] chars = {"A", "B", "C"};
        for (int i = 0; i < chars.length; i++) {
            String pre = null;
            String self = chars[i];
            if (i == 0) {
                pre = chars[chars.length - 1];
            } else {
                pre = chars[i - 1];
            }
            PrintThread printer = new PrintThread(chars[i], pre, self);
            printer.start();
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    public static class PrintThread extends Thread {
        private static final Logger logger = LogManager.getLogger(PrintThread.class);
        private String character;
        private Object pre;
        private Object self;

        private PrintThread(String character, Object pre, Object self) {
            this.character = character;
            this.pre = pre;
            this.self = self;
            this.setName(character);
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                synchronized (pre) { // 先获取 prev 锁
                    synchronized (self) { // 再获取 self 锁
                        logger.info(character); //打印
                        count--;
                        self.notifyAll(); // 唤醒其他线程竞争self锁，注意此时self锁并未立即释放
                    }
                    if (count != 0) {
                        //此时执行完self的同步块，这时self锁才释放
                        try {
                            logger.info("中场休息");
                            pre.wait(); // 立即释放 prev锁，当前线程休眠，等待唤醒
                            /**
                             * JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行
                             */
                            logger.info("活过来");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
