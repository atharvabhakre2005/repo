import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    static final int NUM_PHILOSOPHERS = 5;
    static final int THINKING = 0;
    static final int HUNGRY = 1;
    static final int EATING = 2;

    static int[] state = new int[NUM_PHILOSOPHERS];
    static Semaphore[] sem = new Semaphore[NUM_PHILOSOPHERS];
    static Semaphore mutex = new Semaphore(1);

    static class Philosopher extends Thread {
        int id;
        int left;
        int right;

        Philosopher(int id) {
            this.id = id;
            left = (id + NUM_PHILOSOPHERS - 1) % NUM_PHILOSOPHERS;
            right = (id + 1) % NUM_PHILOSOPHERS;
        }

        public void run() {
            try {
                while (true) {
                    think();
                    takeForks();
                    eat();
                    putForks();
                }
            } catch (InterruptedException e) {
                System.out.println("Philosopher " + id + " was interrupted.");
            }
        }

        void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is THINKING.");
            Thread.sleep(1000 + (int)(Math.random() * 2000)); 
        }
        

        void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is EATING.");
            Thread.sleep(2000); 
        }
        

        void takeForks() throws InterruptedException {
            mutex.acquire();
            state[id] = HUNGRY;
            System.out.println("Philosopher " + id + " is HUNGRY.");
            test(id);
            mutex.release();
            sem[id].acquire();
        }

        void putForks() throws InterruptedException {
            mutex.acquire();
            state[id] = THINKING;
            System.out.println("Philosopher " + id + " PUT DOWN forks and starts THINKING.");
            test(left);
            test(right);
            mutex.release();
        }

        void test(int i) {
            if (state[i] == HUNGRY &&
                state[(i + NUM_PHILOSOPHERS - 1) % NUM_PHILOSOPHERS] != EATING &&
                state[(i + 1) % NUM_PHILOSOPHERS] != EATING) {
                state[i] = EATING;
                sem[i].release();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            sem[i] = new Semaphore(0);
            state[i] = THINKING;
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            new Philosopher(i).start();
        }
    }
}
