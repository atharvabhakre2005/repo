public class ReaderWriter {
    static int readerCount = 0;
    static final Object lock = new Object();

    static class Reader implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                readerCount++;
                System.out.println(Thread.currentThread().getName() + " starts reading. Readers: " + readerCount);
            }

            // Simulate reading
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " stops reading.");
                readerCount--;
                if (readerCount == 0) {
                    lock.notifyAll(); // Notify writers
                }
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (readerCount > 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " starts writing.");
            }

            // Simulate writing
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " stops writing.");
                lock.notifyAll(); // Notify readers or writers
            }
        }
    }

    public static void main(String[] args) {
        Thread r1 = new Thread(new Reader(), "Reader-1");
        Thread r2 = new Thread(new Reader(), "Reader-2");
        Thread w1 = new Thread(new Writer(), "Writer-1");
        Thread r3 = new Thread(new Reader(), "Reader-3");
        Thread w2 = new Thread(new Writer(), "Writer-2");

        r1.start();
        r2.start();
        w1.start();
        r3.start();
        w2.start();
    }
}
