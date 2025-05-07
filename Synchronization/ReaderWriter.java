//ReaderWriter
import java.util.concurrent.Semaphore;

class ReaderWriter {
    static int readCount = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore db = new Semaphore(1);

    static class Reader extends Thread {
        int readerId;

        Reader(int id) {
            this.readerId = id;
        }

        public void run() {
            try {
                while (true) {
                    mutex.acquire();
                    readCount++;
                    if (readCount == 1)
                        db.acquire();
                    mutex.release();

                    System.out.println("Reader " + readerId + " is reading.");
                    Thread.sleep(500);

                    mutex.acquire();
                    readCount--;
                    if (readCount == 0)
                        db.release();
                    mutex.release();

                    System.out.println("Reader " + readerId + " has finished reading.");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Reader " + readerId + " interrupted.");
            }
        }
    }

    static class Writer extends Thread {
        int writerId;

        Writer(int id) {
            this.writerId = id;
        }

        public void run() {
            try {
                while (true) {
                    db.acquire();

                    System.out.println("Writer " + writerId + " is writing.");
                    Thread.sleep(1000);

                    db.release();

                    System.out.println("Writer " + writerId + " has finished writing.");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                System.out.println("Writer " + writerId + " interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            new Reader(i).start();
        }

        for (int i = 1; i <= 2; i++) {
            new Writer(i).start();
        }
    }
}
