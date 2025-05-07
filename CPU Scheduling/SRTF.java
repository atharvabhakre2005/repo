//SRTF
import java.util.*;

class Process {
    int pid, at, bt, rt, ct, tat, wt;

    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.rt = bt; 
    }
}

public class SRTF {
    public static void main(String[] args) {
        int n = 4;
        Process[] processes = {
            new Process(1, 0, 5),
            new Process(2, 1, 3),
            new Process(3, 2, 4),
            new Process(4, 4, 1)
        };

        int completed = 0, time = 0, minRt, shortest = -1;
        boolean found;
        int totalWT = 0, totalTAT = 0;

        
        List<Integer> ganttChart = new ArrayList<>();
        List<Integer> timeStamps = new ArrayList<>();
        timeStamps.add(0);

        while (completed != n) {
            minRt = Integer.MAX_VALUE;
            found = false;

            for (int i = 0; i < n; i++) {
                if (processes[i].at <= time && processes[i].rt > 0 && processes[i].rt < minRt) {
                    minRt = processes[i].rt;
                    shortest = i;
                    found = true;
                }
            }

            if (!found) {
                time++;
                continue;
            }

            
            if (ganttChart.isEmpty() || ganttChart.get(ganttChart.size() - 1) != processes[shortest].pid) {
                ganttChart.add(processes[shortest].pid);
                timeStamps.add(time);
            }

            
            processes[shortest].rt--;
            time++;

           
            if (processes[shortest].rt == 0) {
                completed++;
                processes[shortest].ct = time;
                processes[shortest].tat = processes[shortest].ct - processes[shortest].at;
                processes[shortest].wt = processes[shortest].tat - processes[shortest].bt;

                totalWT += processes[shortest].wt;
                totalTAT += processes[shortest].tat;
            }
        }

        timeStamps.add(time);

        
        System.out.println("\nGantt Chart:");

        
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(" ----");
        }
        System.out.println();

        
        System.out.print("|");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.printf(" P%d |", ganttChart.get(i));
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

        
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", (totalTAT / (float) n));
        System.out.printf("\nAverage Waiting Time: %.2f\n", (totalWT / (float) n));
    }
}
