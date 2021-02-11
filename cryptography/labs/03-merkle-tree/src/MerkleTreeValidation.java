import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class MerkleTreeValidation {

    private enum Direction {
        LEFT,
        RIGHT
    }

    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static MessageDigest SHA256Hasher;

    static {
        try {
            SHA256Hasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static int pow2(int x) {
        return 1 << x;
    }

    private static ArrayList<Direction> getPathToRoot(int id, int treeHeight) {
        ArrayList<Direction> path = new ArrayList<>();
        for (int i = 0; i < treeHeight; i++) {
            int index = (id / pow2(i)) % 2;
            path.add(index == 0
                    ? Direction.LEFT
                    : Direction.RIGHT
            );
        }
        return path;
    }

    private static byte[] decodeBase64(String input) {
        return base64Decoder.decode(input);
    }

    private static byte[] decodeBase64Empty() {
        return decodeBase64("");
    }

    private static boolean isNull(String input) {
        return "null".equals(input);
    }

    private static byte[] smartConcat(byte[] leftDigits, byte[] rightDigits) {

        if (leftDigits == null && rightDigits == null)
            return null;

        int leftLength = leftDigits == null
                ? 0
                : leftDigits.length;

        int rightLength = rightDigits == null
                ? 0
                : rightDigits.length;


        byte[] mergeDigits = new byte[leftLength + rightLength + 2];

        mergeDigits[0] = 1;
        mergeDigits[leftLength + 1] = 2;

        if (leftDigits != null) {
            System.arraycopy(leftDigits, 0, mergeDigits, 1, leftLength);
        }

        if (rightDigits != null)
            System.arraycopy(rightDigits, 0, mergeDigits, 2 + leftLength, rightLength);

        SHA256Hasher.update(mergeDigits);
        return SHA256Hasher.digest();
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {

        Scanner reader = new Scanner(System.in);
        MessageDigest SHA256Hasher = MessageDigest.getInstance("SHA-256");

        int treeHeight = Integer.parseInt(reader.nextLine());
        byte[] rootHashBytes = decodeBase64(reader.nextLine());

        int q = Integer.parseInt(reader.nextLine());

        for (int i = 0; i < q; i++) {

            String[] values = reader.nextLine().split("\\s");
            int id = Integer.parseInt(values[0]);
            String hash = values[1];

            byte[] bytes;
            if (isNull(hash)) {
                SHA256Hasher.update(decodeBase64Empty());
                bytes = null;
            } else {
                byte[] base64Data = decodeBase64(hash);
                byte[] toConvert = new byte[base64Data.length + 1];
                toConvert[0] = 0;
                System.arraycopy(base64Data, 0, toConvert, 1, base64Data.length);
                SHA256Hasher.update(toConvert);
                bytes = SHA256Hasher.digest();
            }

            ArrayList<Direction> path = getPathToRoot(id, treeHeight);

            for (int j = 0; j < treeHeight; j++) {
                String levelBase64Hash = reader.nextLine();
                byte[] leftDigits = bytes, rightDigits = bytes;

                byte[] neighbourBytes = isNull(levelBase64Hash) ? null : decodeBase64(levelBase64Hash);

                switch (path.get(j)) {
                    case LEFT:
                        rightDigits = neighbourBytes;
                        break;
                    case RIGHT:
                        leftDigits = neighbourBytes;
                        break;
                    default:
                        break;
                }

                bytes = smartConcat(leftDigits, rightDigits);
            }

            System.out.println(Arrays.equals(bytes, rootHashBytes)
                    ? "YES"
                    : "NO"
            );
        }
    }
}
