public class Encoder {
    static void swap(byte[] bytes, int offset) {
        int length = bytes.length;

        for (int i = 0; i < length - offset; i += offset) {
            byte swap = bytes[i];
            bytes[i] = bytes[i - 1 + offset];
            bytes[i - 1 + offset] = swap;
        }

    }

    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].length() <= 128) {
                // Change order of function calls from offset:= 2 to offset:= 10.
                byte[] dest = args[0].getBytes();
                swap(dest, 2);
                swap(dest, 3);
                swap(dest, 4);
                swap(dest, 5);
                swap(dest, 6);
                swap(dest, 7);
                swap(dest, 8);
                swap(dest, 9);
                swap(dest, 10);
                System.out.println(new String(dest));
            } else {
                System.out.println("String is too long");
            }
        } else {
            System.out.println("Give me a string");
        }

    }
}
