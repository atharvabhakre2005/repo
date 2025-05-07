///fifo
import java.util.*;

public class fifo {

	static int pageFaults(int pages[], int n, int capacity) {
		HashSet<Integer> s = new HashSet<>(capacity);
		Queue<Integer> indexes = new LinkedList<>();
		int page_faults = 0;

		for (int i = 0; i < n; i++) {
			if (s.size() < capacity) {
				if (!s.contains(pages[i])) {
					s.add(pages[i]);
					page_faults++;
					indexes.add(pages[i]);
				}
			} else {
				if (!s.contains(pages[i])) {
					int val = indexes.peek();
					indexes.poll();
					s.remove(val);
					s.add(pages[i]);
					indexes.add(pages[i]);
					page_faults++;
				}
			}
		}
		return page_faults;
	}

	public static void main(String args[]) {
		int pages[] = {7,0,1,2,0,3,0,4,2,3,0,3,1,2,0};
		int capacity = 3;
		int faults = pageFaults(pages, pages.length, capacity);
		int hits = pages.length - faults;
		System.out.println("The number of page faults is: " + faults);
		System.out.println("The number of page hits is: " + hits);
	}
	
}
