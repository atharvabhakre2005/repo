import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private final int id;
    private final Semaphore leftFork;
    private final Semaphore rightFork;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();

                // To avoid deadlock, pick up the lower-numbered fork first
                if (id % 2 == 0) {
                    leftFork.acquire();
                    rightFork.acquire();
                } else {
                    rightFork.acquire();
                    leftFork.acquire();
                }

                eat();

                leftFork.release();
                rightFork.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Philosopher " + id + " was interrupted.");
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        final int NUM_PHILOSOPHERS = 5;
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Semaphore leftFork = forks[i];
            Semaphore rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];

            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            philosophers[i].start();
        }
    }
}
