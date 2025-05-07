//nextfit
import java.util.*;

public class nextFitAlgo {

	static void NextFit(int blockSize[], int m, int processSize[], int n) {
		int allocation[] = new int[n], j = 0, t = m - 1;

		Arrays.fill(allocation, -1);

		for(int i = 0; i < n; i++) {
			while (j < m) {
				if(blockSize[j] >= processSize[i]) {
					allocation[i] = j;
					blockSize[j] -= processSize[i];
					t = (j - 1) % m;
					break;
				}
				if (t == j) {
					t = (j - 1) % m;
					break;
				}
				j = (j + 1) % m;
			}
		}

		System.out.printf("\n%-12s%-15s%-10s\n", "Process No.", "Process Size", "Block no.");
		for (int i = 0; i < n; i++) {
			System.out.printf("%-12d%-15d", i + 1, processSize[i]);
			if (allocation[i] != -1) {
				System.out.printf("%-10d\n", allocation[i] + 1);
			} else {
				System.out.printf("%-10s\n", "Not Allocated");
			}
		}
		
	}

	public static void main(String[] args) {
		int blockSize[] = {5, 10, 20};
		int processSize[] = {10, 20, 5};
		int m = blockSize.length;
		int n = processSize.length;
		NextFit(blockSize, m, processSize, n);
	}
}
