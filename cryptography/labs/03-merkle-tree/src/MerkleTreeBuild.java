import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class MerkleTreeBuild {

    enum Direction {
        LEFT,
        RIGHT
    }

    private static Direction getDirection(long id) {
        return id % 2 == 1
                ? Direction.LEFT
                : Direction.RIGHT;
    }

    private static final long ROOT_ID = 0;
    private static final String NULL = "null";

    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    private static MessageDigest SHA256Hasher;

    static {
        try {
            SHA256Hasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static long pow2(long x) {
        return (1L) << x;
    }

    private static byte[] decodeBase64(String input) {
        return base64Decoder.decode(input);
    }

    private static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    private static byte[] smartConcat(byte[] leftDigits, byte[] rightDigits) {

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


    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        HashMap<Long, String> inputData = new HashMap<>();
        HashMap<Long, byte[]> merkleTree = new HashMap<>();

        long treeHeight = Long.parseLong(reader.nextLine());
        long blocksAmount = Long.parseLong(reader.nextLine());

        for (long i = 0; i < blocksAmount; ++i) {
            String[] values = reader.nextLine().split("\\s");
            Long id = Long.parseLong(values[0]);
            String hash = values[1];
            inputData.put(id, hash);
        }

        long queries = Long.parseLong(reader.nextLine());
        Long[] blockIDs = Arrays.stream(reader
                .nextLine()
                .split("\\s"))
                .map(Long::parseLong)
                .toArray(Long[]::new);

        long shift = pow2(treeHeight) - 1;

        for (long id : inputData.keySet()) {
            byte[] base64Data = decodeBase64(inputData.get(id));
            byte[] toConvert = new byte[base64Data.length + 1];
            toConvert[0] = 0;
            System.arraycopy(base64Data, 0, toConvert, 1, base64Data.length);
            SHA256Hasher.update(toConvert);

            merkleTree.put(id + shift, SHA256Hasher.digest());
        }

        ArrayDeque<Long> sortedIDsQueue = merkleTree
                .keySet()
                .stream()
                .sorted(Long::compareTo)
                .collect(Collectors
                        .toCollection(ArrayDeque::new)
                );


        while (!sortedIDsQueue.isEmpty()) {

            Long currentID = sortedIDsQueue.poll();

            byte[] leftDigits = null, rightDigits = null;

            if (getDirection(currentID) == Direction.LEFT) {
                leftDigits = merkleTree.get(currentID);

                if (!sortedIDsQueue.isEmpty()) {
                    long neighborID = sortedIDsQueue.peek();
                    if (neighborID == currentID + 1) {
                        sortedIDsQueue.pollFirst();
                        rightDigits = merkleTree.get(neighborID);
                    }
                }
            }

            if (getDirection(currentID) == Direction.RIGHT) {
                rightDigits = merkleTree.get(currentID);
            }

            Long parent = (currentID - 1) / 2;
            merkleTree.put(parent, smartConcat(leftDigits, rightDigits));

            if (parent != ROOT_ID) {
                sortedIDsQueue.add(parent);
            }
        }


        for (int i = 0; i < queries; i++) {

            System.out.println(blockIDs[i] + " " + inputData.getOrDefault(blockIDs[i], NULL));

            long id = blockIDs[i] + shift;

            for (int levelID = 0; levelID < treeHeight; levelID++, id = (id - 1) / 2) {
                long neighbour = getDirection(id) == Direction.LEFT
                        ? (id + 1)
                        : (id - 1);

                if (merkleTree.containsKey(neighbour)) {
                    System.out.println(
                            bytesToString(
                                    base64Encoder.encode(merkleTree.get(neighbour)
                                    )
                            )
                    );
                } else {
                    System.out.println(NULL);
                }
            }
        }
    }
}