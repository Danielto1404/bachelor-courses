import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

// Flag:= 54465f32-30323021-53506243

public class Main {
    public static byte[] getSliceOfArray(byte[] arr, int startIndex, int endIndex) {
        byte[] slice = Arrays.copyOfRange(arr, startIndex, endIndex);
        return slice;
    }

    public static boolean check(String name, String serial) {
        byte[] nameb = name.getBytes(StandardCharsets.UTF_8);
        if (nameb.length != 12) {
            return false;
        } else {
            int[] valid = new int[]{ByteBuffer.wrap(getSliceOfArray(nameb, 0, 4)).getInt(), ByteBuffer.wrap(getSliceOfArray(nameb, 4, 8)).getInt(), ByteBuffer.wrap(getSliceOfArray(nameb, 8, 12)).getInt()};
            valid[0] ^= valid[1];
            valid[1] ^= valid[0];
            valid[0] ^= valid[1];
            valid[1] ^= valid[2];
            valid[2] ^= valid[1];
            valid[1] ^= valid[2];
            // Output encoded serial.
            System.out.println(Integer.toHexString(valid[0]) + '-' + Integer.toHexString(valid[1]) + '-' + Integer.toHexString(valid[2]));
            return serial.equals(Integer.toHexString(valid[0]) + '-' + Integer.toHexString(valid[1]) + '-' + Integer.toHexString(valid[2]));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("... and your serial?");
        String serial = scanner.nextLine();
        if (check(name, serial)) {
            System.out.println("Your serial is valid!");
        } else {
            System.out.println("Your serial is invalid...");
        }

    }
}