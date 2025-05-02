import java.util.concurrent.Semaphore;

class Buffer {
    int item;
    Semaphore mutex = new Semaphore(1);
    Semaphore empty = new Semaphore(1);
    Semaphore full = new Semaphore(0);

    void produce(int item) {
        try {
            empty.acquire();   // Wait if buffer is full
            mutex.acquire();   // Enter critical section

            this.item = item;
            System.out.println("Producer produced: " + item);

            mutex.release();   // Exit critical section
            full.release();    // Signal item is available
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void consume() {
        try {
            full.acquire();    // Wait if buffer is empty
            mutex.acquire();   // Enter critical section

            System.out.println("Consumer consumed: " + item);

            mutex.release();   // Exit critical section
            empty.release();   // Signal buffer is free
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {
    Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            buffer.produce(i);
        }
    }
}

class Consumer implements Runnable {
    Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            buffer.consume();
        }
    }
}

public class producerConsumer {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        new Producer(buffer);
        new Consumer(buffer);
    }
}
