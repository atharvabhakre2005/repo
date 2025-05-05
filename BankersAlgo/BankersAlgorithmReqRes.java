public class BankersAlgorithmReqRes {
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
            System.out.print(safeSeq[i] + " ");

        return true;
    }

    static boolean requestResources(int process, int request[], int avail[], int maxm[][], int allot[][]) {
        int [][]need = new int[P][R];
        calculateNeed(need, maxm, allot);
        
        for (int i = 0; i < R; i++) {
            if (request[i] > need[process][i]) {
                System.out.println("Error: Process " + process + " has exceeded its maximum claim for resource " + i);
                return false;
            }
        }
        
        for (int i = 0; i < R; i++) {
            if (request[i] > avail[i]) {
                System.out.println("Process " + process + " must wait. Resources not available.");
                return false;
            }
        }
        
        int[] tempAvail = avail.clone();
        int[][] tempAllot = new int[P][R];
        
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                tempAllot[i][j] = allot[i][j];
            }
        }
        
        for (int i = 0; i < R; i++) {
            tempAvail[i] -= request[i];
            tempAllot[process][i] += request[i];
        }
        
        if (isSafe(new int[]{0, 1, 2, 3, 4}, tempAvail, maxm, tempAllot)) {
            System.out.println("Request can be granted immediately.");
            
            for (int i = 0; i < R; i++) {
                avail[i] -= request[i];
                allot[process][i] += request[i];
            }
            return true;
        } else {
            System.out.println("Request cannot be granted immediately as it would lead to an unsafe state.");
            return false;
        }
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

        System.out.println("Checking initial state:");
        boolean safe = isSafe(processes, avail, maxm, allot);
        System.out.println("\n");
        
        if (safe) {
            int request1[] = {1, 0, 2};
            System.out.println("Process 1 is requesting resources: [" + request1[0] + ", " + request1[1] + ", " + request1[2] + "]");
            requestResources(1, request1, avail, maxm, allot);
            System.out.println("\n");
            
            int request2[] = {3, 3, 0};
            System.out.println("Process 4 is requesting resources: [" + request2[0] + ", " + request2[1] + ", " + request2[2] + "]");
            requestResources(4, request2, avail, maxm, allot);
        }
    }
}