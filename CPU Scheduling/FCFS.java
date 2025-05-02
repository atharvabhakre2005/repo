import java.util.Arrays;

class Process {
    int no, at, bt, ct, tat, wt;

    public Process(int no, int at, int bt) {
        this.no = no;
        this.at = at;
        this.bt = bt;
    }
}

public class FCFS {
    public static void main(String[] args) {
        System.out.println("<-- FCFS Scheduling Algorithm -->");

        Process[] processes = {
            new Process(1, 0, 5),
            new Process(2, 3, 9),
            new Process(3, 6, 6)
        };

        Arrays.sort(processes, (p1, p2) -> p1.at - p2.at); // Sort by arrival time

        int n = processes.length;
        int currentTime = 0;

        for (int i = 0; i < n; i++) {
            if (currentTime < processes[i].at) {
                currentTime = processes[i].at;
            }
            processes[i].ct = currentTime + processes[i].bt;
            processes[i].tat = processes[i].ct - processes[i].at;
            processes[i].wt = processes[i].tat - processes[i].bt;

            currentTime = processes[i].ct;
        }

        double avgTAT = 0, avgWT = 0;
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            avgTAT += p.tat;
            avgWT += p.wt;
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n", p.no, p.at, p.bt, p.ct, p.tat, p.wt);
        }

        avgTAT /= n;
        avgWT /= n;
        System.out.printf("\nAverage Turnaround Time = %.2f\n", avgTAT);
        System.out.printf("Average Waiting Time = %.2f\n", avgWT);

        System.out.println("\nGantt Chart:");
        System.out.print(" ");
        for (Process p : processes) {
            for (int j = 0; j < p.bt; j++) System.out.print("--");
            System.out.print(" ");
        }
        System.out.println();
        System.out.print("|");
        for (Process p : processes) {
            for (int j = 0; j < p.bt - 1; j++) System.out.print(" ");
            System.out.print("P" + p.no);
            for (int j = 0; j < p.bt - 1; j++) System.out.print(" ");
            System.out.print("|");
        }
        System.out.println();
        System.out.print(" ");
        for (Process p : processes) {
            for (int j = 0; j < p.bt; j++) System.out.print("--");
            System.out.print(" ");
        }
        System.out.println();
        System.out.print("0");
        for (Process p : processes) {
            for (int j = 0; j < p.bt; j++) System.out.print("  ");
            System.out.print(p.ct);
        }
        System.out.println();
    }
}
