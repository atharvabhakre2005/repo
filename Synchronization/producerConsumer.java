import java.util.*;
import java.util.concurrent.Semaphore;

public class producerConsumer {
    static final int BUFFER_SIZE = 5;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore full = new Semaphore(0);
    static Semaphore empty = new Semaphore(BUFFER_SIZE);

    static Queue<Integer> buffer = new LinkedList<>();

    static class Producer extends Thread {
        int producerId;

        Producer(int id) {
            this.producerId = id;
        }

        public void run() {
            int item = 0;
            try {
                while (true) {
                    Thread.sleep(1000); // Simulate time to produce item
                    item++;

                    empty.acquire();   // Wait if buffer is full
                    mutex.acquire();   // Enter critical section

                    buffer.add(item);  // Place item in buffer
                    System.out.println("Producer " + producerId + " produced: " + item);

                    mutex.release();   // Exit critical section
                    full.release();    // Increment number of full slots
                }
            } catch (InterruptedException e) {
                System.out.println("Producer " + producerId + " interrupted.");
            }
        }
    }

    static class Consumer extends Thread {
        int consumerId;

        Consumer(int id) {
            this.consumerId = id;
        }

        public void run() {
            try {
                while (true) {
                    full.acquire();    // Wait if buffer is empty
                    mutex.acquire();   // Enter critical section

                    int item = buffer.remove(); // Consume item
                    System.out.println("Consumer " + consumerId + " consumed: " + item);

                    mutex.release();   // Exit critical section
                    empty.release();   // Increment number of empty slots

                    Thread.sleep(1500); // Simulate time to consume item
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer " + consumerId + " interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        // Start producers
        for (int i = 1; i <= 2; i++) {
            new Producer(i).start();
        }

        // Start consumers
        for (int i = 1; i <= 2; i++) {
            new Consumer(i).start();
        }
    }
}
