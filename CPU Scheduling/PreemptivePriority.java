import java.util.*;

class Process {
    int pid, at, bt, priority, rt, ct, tat, wt;

    Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
        this.rt = bt;
    }
}

public class PreemptivePriority {
    public static void main(String[] args) {
        int n = 5;
        Process[] processes = {
            new Process(1, 0, 4, 3),
            new Process(2, 1, 5, 2),
            new Process(3, 2, 8, 2),
            new Process(4, 0, 7, 1),
            new Process(5, 3, 3, 3)
        };

        int completed = 0, time = 0, minPriority, highestPriorityIdx = -1;
        boolean found;
        int totalWT = 0, totalTAT = 0;

        // List for Gantt Chart Representation
        List<Integer> ganttChart = new ArrayList<>();
        List<Integer> timeStamps = new ArrayList<>();
        timeStamps.add(0);

        while (completed != n) {
            minPriority = Integer.MAX_VALUE;
            found = false;

            // Check which process with highest priority is ready to execute
            for (int i = 0; i < n; i++) {
                if (processes[i].at <= time && processes[i].rt > 0 && processes[i].priority < minPriority) {
                    minPriority = processes[i].priority;
                    highestPriorityIdx = i;
                    found = true;
                }
            }

            // If no process is found, increment time
            if (!found) {
                time++;
                continue;
            }

            // If a new process is scheduled, add to Gantt chart
            if (ganttChart.isEmpty() || ganttChart.get(ganttChart.size() - 1) != processes[highestPriorityIdx].pid) {
                ganttChart.add(processes[highestPriorityIdx].pid);
                timeStamps.add(time);
            }

            // Execute the process for one time unit
            processes[highestPriorityIdx].rt--;
            time++;

            // If process finishes execution
            if (processes[highestPriorityIdx].rt == 0) {
                completed++;
                processes[highestPriorityIdx].ct = time;
                processes[highestPriorityIdx].tat = processes[highestPriorityIdx].ct - processes[highestPriorityIdx].at;
                processes[highestPriorityIdx].wt = processes[highestPriorityIdx].tat - processes[highestPriorityIdx].bt;

                totalWT += processes[highestPriorityIdx].wt;
                totalTAT += processes[highestPriorityIdx].tat;
            }
        }

        timeStamps.add(time);

        // Print Gantt Chart
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

        for (int i = 1; i < timeStamps.size(); i++) {
            System.out.printf("%-5d", timeStamps.get(i)); 
        }
        System.out.println();

        // Print Process Table
        System.out.println("\nProcess\tAT\tBT\tPriority\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.priority + "\t\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        }

        // Calculate and print average Turnaround Time and Waiting Time
        System.out.printf("\nAverage Turnaround Time: %.2f", (totalTAT / (float) n));
        System.out.printf("\nAverage Waiting Time: %.2f\n", (totalWT / (float) n));
    }
}
