import java.util.ArrayList;
import java.util.Scanner;

public class ReverseMax {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> max_lines = new ArrayList<Integer>();
        ArrayList<Integer> max_columns = new ArrayList<Integer>();
        int max_length_line = 0;
        while (sc.hasNextLine()) {
            ArrayList<Integer> curr_num = new ArrayList<Integer>();
            for (String cur_line : sc.nextLine().split(" ")) {
                try {
                    curr_num.add(Integer.parseInt(cur_line));
                } catch (Exception e) {
                }
            }
            numbers.add(curr_num);
        }
        for (int i = 0; i < numbers.size(); i++) {
            int max_ln = Integer.MIN_VALUE;
            max_length_line = Math.max(max_length_line, numbers.get(i).size());
            for (int j = 0; j < numbers.get(i).size(); j++) {
                max_ln = Math.max(max_ln, numbers.get(i).get(j));
            }
            max_lines.add(max_ln);
        }
        for (int j = 0; j < max_length_line; j++) {
            int max_cl = Integer.MIN_VALUE;
            for (int i = 0; i < numbers.size(); i++) {
                if (numbers.get(i).size() > j) {
                    max_cl = Math.max(max_cl, numbers.get(i).get(j));
                }
            }
            max_columns.add(max_cl);
        }
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.get(i).size(); j++) {
                System.out.print(Math.max(max_columns.get(j), max_lines.get(i)) + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}