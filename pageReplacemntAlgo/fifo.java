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
		int pages[] = {1,2,3,1,2,5,6,1,4};
		int capacity = 3;
		System.out.println("the no page faults are: "+pageFaults(pages, pages.length, capacity));
	}
}
