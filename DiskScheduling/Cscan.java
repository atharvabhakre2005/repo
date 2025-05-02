import java.util.*;

public class Cscan {

    static int size = 8;
    static int disk_size = 200;

    public static void CSCAN(int arr[], int head) {
        int seek_count = 0;
        int distance, cur_track;

        Vector<Integer> left = new Vector<Integer>();
        Vector<Integer> right = new Vector<Integer>();
        Vector<Integer> seek_sequence = new Vector<Integer>();

        left.add(0);
        right.add(disk_size - 1);

        for (int i = 0; i < size; i++) {
            if (arr[i] < head)
                left.add(arr[i]);
            if (arr[i] > head)
                right.add(arr[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }

        head = 0;
        seek_count += (disk_size - 1);

        for (int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - head);
            seek_count += distance;
            head = cur_track;
        }

        System.out.println("Total number of seek operations = " + seek_count);
        System.out.println("Seek Sequence is");

        for (int i = 0; i < seek_sequence.size(); i++) {
            System.out.println(seek_sequence.get(i));
        }
    }

    public static void main(String[] args) throws Exception {
        int arr[] = {176, 79, 34, 60, 92, 11, 41, 114};
        int head = 50;

        System.out.println("Initial position of head: " + head);
        CSCAN(arr, head);
    }
}
