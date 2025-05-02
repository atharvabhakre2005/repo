import java.util.*;

public class BankersAlgorithmDeadlock {

    static final int MAX = 10;
    static int n, m;
    static int alloc[][] = new int[MAX][MAX];
    static int max_need[][] = new int[MAX][MAX];
    static int avail[] = new int[MAX];
    static int need[][] = new int[MAX][MAX];
    static boolean finished[] = new boolean[MAX];

    
    static void calculateNeed() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max_need[i][j] - alloc[i][j];
            }
        }
    }

    
    static boolean isSafe() {
        int work[] = new int[MAX];
        for (int i = 0; i < m; i++) {
            work[i] = avail[i];
        }

        for (int i = 0; i < n; i++) {
            finished[i] = false;
        }

        int safeSeq[] = new int[MAX];
        int count = 0;

        
        while (count < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (!finished[i]) {
                    boolean possible = true;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            possible = false;
                            break;
                        }
                    }

                    if (possible) {
                        for (int j = 0; j < m; j++) {
                            work[j] += alloc[i][j];
                        }
                        safeSeq[count++] = i;
                        finished[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                return false;
            }
        }

        System.out.println("\nSafe Sequence: ");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + safeSeq[i] + " ");
        }
        System.out.println();
        return true;
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        System.out.print("Enter number of resources: ");
        m = sc.nextInt();

        System.out.println("\nEnter allocation matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                alloc[i][j] = sc.nextInt();
            }
        }

        System.out.println("\nEnter maximum need matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max_need[i][j] = sc.nextInt();
            }
        }

        System.out.println("\nEnter available resources:");
        for (int i = 0; i < m; i++) {
            avail[i] = sc.nextInt();
        }

        calculateNeed();

        if (!isSafe()) {
            System.out.println("\nSystem is in DEADLOCK.");
        } else {
            System.out.println("\nSystem is in a SAFE STATE.");
        }

        sc.close();
    }
}
