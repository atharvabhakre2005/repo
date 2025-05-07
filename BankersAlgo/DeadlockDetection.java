public class DeadlockDetection {
    static int P = 5;
    static int R = 3; 

    public static void main(String[] args) {
        int[][] allocation = {
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 3},
            {2, 1, 1},
            {0, 0, 2}
        };

        int[][] request = {
            {0, 0, 0},
            {2, 0, 2},
            {0, 0, 0},
            {1, 0, 0},
            {0, 0, 2}
        };

        int[] available = {0, 0, 0};

        boolean[] finish = new boolean[P];

        while (true) {
            boolean progress = false;

            for (int i = 0; i < P; i++) {
                if (!finish[i]) {
                    boolean canProceed = true;
                    for (int j = 0; j < R; j++) {
                        if (request[i][j] > available[j]) {
                            canProceed = false;
                            break;
                        }
                    }

                    if (canProceed) {
                        for (int j = 0; j < R; j++) {
                            available[j] += allocation[i][j];
                        }
                        finish[i] = true;
                        progress = true;
                        System.out.println("Process P" + i + " has completed.");
                    }
                }
            }

            if (!progress) break; 
        }

        boolean deadlock = false;
        System.out.print("Processes in deadlock: ");
        for (int i = 0; i < P; i++) {
            if (!finish[i]) {
                System.out.print("P" + i + " ");
                deadlock = true;
            }
        }

        if (!deadlock)
            System.out.println("None. No deadlock detected.");
    }
}
