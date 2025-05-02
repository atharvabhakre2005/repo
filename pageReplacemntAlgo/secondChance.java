import java.util.*;
import java.io.*;

public class secondChance {
    public static void main(String args[]) throws IOException {
        String reference_string = "";
        int frames = 0;

        reference_string = "0 4 1 4 2 4 3 4 2 4 0 4 1 4 2 4 3 4";
        frames = 3;
        printHitsAndFaults(reference_string, frames);

        reference_string = "2 5 10 1 2 2 6 9 1 2 10 2 6 1 2 1 6 9 5 1";
        frames = 4;
        printHitsAndFaults(reference_string, frames);
    }

    static boolean findAndUpdate(int x, int arr[], boolean second_chance[], int frames) {
        int i;
        for (i = 0; i < frames; i++) {
            if (arr[i] == x) {
                second_chance[i] = true;
                return true;
            }
        }
        return false;
    }

    static int replaceAndUpdate(int x, int arr[], boolean second_chance[], int frames, int pointer) {
        while (true) {
            if (!second_chance[pointer]) {
                arr[pointer] = x;
                return (pointer + 1) % frames;
            }
            second_chance[pointer] = false;
            pointer = (pointer + 1) % frames;
        }
    }

    static void printHitsAndFaults(String reference_string, int frames) {
        int pointer, i, l, x, pf;
        pointer = 0;
        pf = 0;
        int arr[] = new int[frames];
        Arrays.fill(arr, -1);
        boolean second_chance[] = new boolean[frames];
        String str[] = reference_string.split(" ");
        l = str.length;

        for (i = 0; i < l; i++) {
            x = Integer.parseInt(str[i]);
            if (!findAndUpdate(x, arr, second_chance, frames)) {
                pointer = replaceAndUpdate(x, arr, second_chance, frames, pointer);
                pf++;
            }
        }
        System.out.println("Total page faults were " + pf);
    }
}
