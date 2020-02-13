package Algorithms.disjoinset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class AstroGrad {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        ArrayList<Integer> queue = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            String[] events = reader.readLine().split("\\s");
            int event = Integer.parseInt(events[0]);
            if (event == 1) {
                queue.add(Integer.parseInt(events[1]));
            } else if (event == 2) {
                queue.remove(0);
            } else if (event == 3) {
                queue.remove(queue.size() - 1);
            } else if (event == 4) {
                int id = Integer.parseInt(events[1]);
                System.out.println(queue.indexOf(id));
            } else {
                System.out.println(queue.get(0));
            }
        }
    }
}
