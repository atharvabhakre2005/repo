//BankersSafteyAlgo
public class BankersSafteyAlgo {

    static int P = 5;
    static int R = 3;

    static void calculateNeed(int need[][], int maxm[][], int allot[][]) {
        for (int i = 0; i < P; i++)
            for (int j = 0; j < R; j++)
                need[i][j] = maxm[i][j] - allot[i][j];
    }

    static boolean isSafe(int processes[], int avail[], int maxm[][], int allot[][]) {
        int [][]need = new int[P][R];
        calculateNeed(need, maxm, allot);

        boolean []finish = new boolean[P];
        int []safeSeq = new int[P];
        int []work = new int[R];
        for (int i = 0; i < R ; i++)
            work[i] = avail[i];

        int count = 0;
        while (count < P) {
            boolean found = false;
            for (int p = 0; p < P; p++) {
                if (!finish[p]) {
                    int j;
                    for (j = 0; j < R; j++)
                        if (need[p][j] > work[j])
                            break;

                    if (j == R) {
                        for (int k = 0; k < R; k++)
                            work[k] += allot[p][k];
                        safeSeq[count++] = p;
                        finish[p] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.print("System is not in safe state. Deadlock detected!");
                return false;
            }
        }

        System.out.print("System is in safe state.\nSafe sequence is: ");
        for (int i = 0; i < P ; i++)
        System.out.print("P" + safeSeq[i] + " ");


        return true;
    }

    public static void main(String[] args) {
        int processes[] = {0, 1, 2, 3, 4};
        int avail[] = {3, 3, 2};

        int maxm[][] = {
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3}
        };

        int allot[][] = {
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2}
        };

        isSafe(processes, avail, maxm, allot);
    }
}
