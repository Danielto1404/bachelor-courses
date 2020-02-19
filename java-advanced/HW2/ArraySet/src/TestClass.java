import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
        List<Integer> lst = Collections.checkedList(List.of(1, 2, 4, 9, 13, 124), Integer.class);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(Collections.binarySearch(lst, sc.nextInt()));
        }
    }
}
