import java.util.*;

public class Cscan {
    static int size = 8; // Number of requests
    static int disk_size = 200; // Maximum disk size

    public static void CSCAN(int arr[], int head) {
        int seek_count = 0; // Initialize seek count
        int distance, cur_track;

        Vector<Integer> left = new Vector<>();
        Vector<Integer> right = new Vector<>();
        Vector<Integer> seek_sequence = new Vector<>();

        left.add(0); // Add start of the disk
        right.add(disk_size - 1); // Add end of the disk

        // Divide requests in two parts: left and right of the head
        for (int i = 0; i < size; i++) {
            if (arr[i] < head)
                left.add(arr[i]);
            if (arr[i] > head)
                right.add(arr[i]);
        }

        // Sort both the vectors
        Collections.sort(left);
        Collections.sort(right);

        // Serve the requests going to the right
        for (int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);
            seek_sequence.add(cur_track); // Add to seek sequence
            distance = Math.abs(cur_track - head);
            seek_count += distance; // Update seek count
            head = cur_track; // Move head to current track
        }

        // Jump to the beginning of the disk
        head = 0;
        seek_count += (disk_size - 1); // Add distance for jump

        // Serve the requests going to the left
        for (int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);
            seek_sequence.add(cur_track); // Add to seek sequence
            distance = Math.abs(cur_track - head);
            seek_count += distance; // Update seek count
            head = cur_track; // Move head to current track
        }

        // Print total seek operations and the seek sequence
        System.out.println("Total number of seek operations = " + seek_count);
        System.out.println("Seek Sequence is");
        for (int i = 0; i < seek_sequence.size(); i++) {
            System.out.println(seek_sequence.get(i));
        }
    }

    public static void main(String[] args) {
        int arr[] = {176, 79, 34, 60, 92, 11, 41, 114}; // Requests
        int head = 50; // Initial head position

        System.out.println("Initial position of head: " + head);
        CSCAN(arr, head); // Call C-SCAN function
    }
}