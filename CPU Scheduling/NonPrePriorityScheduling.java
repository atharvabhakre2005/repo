//NonPrePriorityScheduling
import java.util.*;

class Process {
    int pid, at, bt, priority, ct, tat, wt;

    Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
    }
}

public class NonPrePriorityScheduling {
    public static void main(String[] args) {
        int n = 5;
        Process[] processes = {
            new Process(1, 0, 3, 3),
            new Process(2, 1, 6, 4),
            new Process(3, 3, 1, 9),
            new Process(4, 2, 2, 7),
            new Process(5, 4, 4, 8)
        };

        Arrays.sort(processes, Comparator.comparingInt(p -> p.at));

        int currentTime = 0, totalTAT = 0, totalWT = 0;
        List<Integer> ganttChart = new ArrayList<>();
        List<Integer> timeStamps = new ArrayList<>();
        timeStamps.add(0);

        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int index = 0;

        while (index < n || !pq.isEmpty()) {
            while (index < n && processes[index].at <= currentTime) {
                pq.add(processes[index]);
                index++;
            }

            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = pq.poll();
            ganttChart.add(p.pid);

            p.ct = currentTime + p.bt;
            p.tat = p.ct - p.at;
            p.wt = p.tat - p.bt;

            totalWT += p.wt;
            totalTAT += p.tat;

            currentTime = p.ct;
            timeStamps.add(currentTime);
        }

        System.out.println("\nGantt Chart:");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" ----");
        }
        System.out.println();

        System.out.print("|");
        for (int pid : ganttChart) {
            System.out.printf(" P%d |", pid);
        }
        System.out.println();

        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" ----");
        }
        System.out.println();

        for (int t : timeStamps) {
            System.out.printf("%-5d", t);
        }
        System.out.println();

        System.out.println("\nProcess\tAT\tBT\tPriority\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.priority + "\t\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", (totalTAT / (float) n));
        System.out.printf("\nAverage Waiting Time: %.2f\n", (totalWT / (float) n));
    }
}
