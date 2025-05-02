import java.util.*;

class Process {
    int pid, at, bt, remainingBT, ct, tat, wt;

    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.remainingBT = bt;
    }
}

public class RoundRobinScheduling {
    public static void main(String[] args) {
        int n = 3;
        int quantum = 4;

        Process[] processes = {
            new Process(1, 0, 24),
            new Process(2, 0, 3),
            new Process(3, 0, 3)
        };

        Arrays.sort(processes, Comparator.comparingInt(p -> p.at));

        int currentTime = 0;
        int completed = 0;
        Queue<Process> queue = new LinkedList<>();
        List<Integer> timeStamps = new ArrayList<>();
        List<Integer> ganttChart = new ArrayList<>();
        boolean[] inQueue = new boolean[n];

        timeStamps.add(0);
        int index = 0;

        while (index < n && processes[index].at <= currentTime) {
            queue.add(processes[index]);
            inQueue[index] = true;
            index++;
        }

        while (completed < n) {
            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = queue.poll();

            ganttChart.add(p.pid);
            int executionTime = Math.min(quantum, p.remainingBT);
            timeStamps.add(currentTime + executionTime);
            
            currentTime += executionTime;
            p.remainingBT -= executionTime;

            while (index < n && processes[index].at <= currentTime) {
                queue.add(processes[index]);
                inQueue[index] = true;
                index++;
            }

            if (p.remainingBT == 0) {
                p.ct = currentTime;
                p.tat = p.ct - p.at;
                p.wt = p.tat - p.bt;
                completed++;
            } else {
                queue.add(p);
            }
        }

        System.out.println("\nGantt Chart:");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" ----");
        }
        System.out.println();

        System.out.print("|");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" P" + ganttChart.get(i) + " |");
        }
        System.out.println();

        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" ----");
        }
        System.out.println();

        for (int time : timeStamps) {
            System.out.printf("%-5d", time);
        }
        System.out.println();

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        float avgWT = 0, avgTAT = 0;
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
            avgWT += p.wt;
            avgTAT += p.tat;
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", (avgTAT / n));
        System.out.printf("\nAverage Waiting Time: %.2f\n", (avgWT / n));
    }
}