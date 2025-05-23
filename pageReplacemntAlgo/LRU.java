//LRU
import java.util.*;

public class LRU {


    public static void pageFaults(int frame_size, int[] ref, int len, int ctime) {
        int cnt = 0;
        List<Pair<Integer, Integer>> arr = new ArrayList<>();

        for (int i = 0; i < frame_size; i++) {
            arr.add(new Pair<>(-1, ctime));
        }

        for (int i = 0; i < len; i++) {
            int page = ref[i];
            boolean found = false;

            for (int it = 0; it < arr.size(); it++) {
                if (arr.get(it).first == page) {
                    arr.get(it).second = ctime;
                    found = true;
                    break;
                }
            }

            if (!found) {
                int pos = 0;
                int min = arr.get(pos).second;
                for (int itr = 0; itr < arr.size(); itr++) {
                    if (arr.get(itr).second < min) {
                        pos = itr;
                        min = arr.get(pos).second;
                    }
                }

                arr.remove(pos);
                arr.add(new Pair<>(page, ctime));
                cnt++;
            }
            ctime++;
        }
        int hits = len - cnt;
    System.out.println("The number of page faults is: " + cnt);
    System.out.println("The number of page hits is: " + hits);
        
    }
    public static void main(String[] args) {
        int[] ref = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
        int len = ref.length;
        int frame_size = 4;
        int ctime = 0;
        pageFaults(frame_size, ref, len, ctime);
    }
}

class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
