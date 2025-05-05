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
                    Thread.sleep(1000);
                    item++;

                    empty.acquire(); 
                    mutex.acquire();   

                    buffer.add(item);  
                    System.out.println("Producer " + producerId + " produced: " + item);

                    mutex.release();   
                    full.release();    
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
                    full.acquire();    
                    mutex.acquire();   

                    int item = buffer.remove(); 
                    System.out.println("Consumer " + consumerId + " consumed: " + item);

                    mutex.release();   
                    empty.release();   

                    Thread.sleep(1500); 
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer " + consumerId + " interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        
        for (int i = 1; i <= 2; i++) {
            new Producer(i).start();
        }

        
        for (int i = 1; i <= 2; i++) {
            new Consumer(i).start();
        }
    }
}
