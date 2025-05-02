public class FCFSAlgo
{
static int size = 8;

static void FCFS(int arr[], int head)
{
    int seek_count = 0;
    int distance, cur_track;

    for (int i = 0; i < size; i++) 
    {
        cur_track = arr[i];
        distance = Math.abs(cur_track - head);
        seek_count += distance;
        head = cur_track;
    }

    System.out.println("Total number of seek operations = " + seek_count);
    System.out.println("Seek Sequence is");

    for (int i = 0; i < size; i++)
    {
        System.out.println(arr[i]);
    }
}

public static void main(String[] args) 
{
    int arr[] = { 176, 79, 34, 60, 92, 11, 41, 114 };
    int head = 50;

    FCFS(arr, head);
}
}