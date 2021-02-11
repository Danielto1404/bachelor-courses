import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

public class MerkleSignature {

    /**
     * Class for private / public key.
     */
    private static class HashKeyPair {
        byte[] privateKeyBytes;
        byte[] publicKeyBytes;

        private static final int SPLIT_INDEX = SHA256_LENGTH;

        void setBytes(int index, byte[] bytes) {
            if (index < SPLIT_INDEX) {
                privateKeyBytes = bytes;
            } else {
                publicKeyBytes = bytes;
            }
        }

        byte[] getKeyFrom(int bit) {
            return bit == 0
                    ? privateKeyBytes
                    : publicKeyBytes;
        }
    }

    /**
     * Simple ENUM which helps to built path from leaf to root in merkle tree.
     */
    private enum Direction {
        LEFT,
        RIGHT
    }

    /**
     * Basic constants for merkle tree height and length of messages in protocol.
     */
    private static final int TREE_HEIGHT = 8;
    private static final int SHA256_LENGTH = 256;

    /**
     * Byte - 8 bits => for 256 bit message offset will be 32 == BLOCK_SIZE.
     */
    private static final int BLOCK_SIZE = 32;

    /**
     * Builtin decoder from BASE64.
     */
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    /**
     * Counter of completed key.
     */
    private static final int[] completedKeyArray = new int[SHA256_LENGTH];

    /**
     * ZEROS - responsible for left part of key pair.
     * ONES - responsible for right part of key pair.
     */
    private static final String ZEROS = String.join("", Collections.nCopies(SHA256_LENGTH, "0"));
    private static final String ONES = String.join("", Collections.nCopies(SHA256_LENGTH, "1"));

    /**
     * Builtin SHA256 algorithm implementation.
     */
    private static byte[] SHA256Bytes(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(input);
        } catch (NoSuchAlgorithmException e) {
            return new byte[SHA256_LENGTH];
        }
    }

    /**
     * @param id - Number of leaf in left-to-right order.
     * @return - ArrayList of Direction objects representing path from leaf to root.
     */
    private static ArrayList<Direction> getPathToRoot(int id) {
        ArrayList<Direction> path = new ArrayList<>();
        for (int i = 0; i < TREE_HEIGHT; i++) {
            int index = (id / pow2(i)) % 2;
            path.add(index == 0
                    ? Direction.LEFT
                    : Direction.RIGHT
            );
        }
        return path;
    }

    /**
     * @param x - Number of powers.
     * @return - 2 to the x.
     */
    private static int pow2(int x) {
        return 1 << x;
    }

    /**
     * @param input - String base64 input.
     * @return - Array of decoded bytes.
     */
    private static byte[] decodeBase64(String input) {
        return base64Decoder.decode(input);
    }

    /**
     * @param leftDigits  - Left node bytes of merkle tree.
     * @param rightDigits - Right node bytes of merkle tree.
     * @return - parent node bytes which created from left node || right node.
     */
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

        return SHA256Bytes(mergeDigits);
    }

    /**
     * @param rootHashByteArray - Expected root bytes array of merkle tree.
     * @param merkleProof       - List of proof steps from leaf to root.
     * @param leafDigits        - Start leaf bytes array.
     * @param id                - Number of leaf in left-to-right order.
     * @return True if proof is correct, False - otherwise.
     */
    private static boolean checkMerkleProof(byte[] rootHashByteArray,
                                            ArrayList<byte[]> merkleProof,
                                            byte[] leafDigits,
                                            int id) {


        byte[] digest = new byte[leafDigits.length + 1];
        digest[0] = 0;
        System.arraycopy(leafDigits, 0, digest, 1, leafDigits.length);

        digest = SHA256Bytes(digest);

        ArrayList<Direction> path = getPathToRoot(id);

        for (int j = 0; j < TREE_HEIGHT; j++) {

            byte[] leftDigits = digest, rightDigits = digest;

            if (path.get(j) == Direction.LEFT) {
                rightDigits = merkleProof.get(j);
            }
            if (path.get(j) == Direction.RIGHT) {
                leftDigits = merkleProof.get(j);
            }

            digest = smartConcat(leftDigits, rightDigits);
        }

        return Arrays.equals(digest, rootHashByteArray);
    }

    /**
     * @param signature - Array of concat signature keys.
     * @return - Array of decomposed signature keys.
     */
    private static ArrayList<byte[]> getSignatureDecomposition(byte[] signature) {
        ArrayList<byte[]> signatureHashKeys = new ArrayList<>();

        for (int i = 0; i < SHA256_LENGTH; i++) {
            byte[] keyBytes = new byte[BLOCK_SIZE];
            System.arraycopy(signature, i * BLOCK_SIZE, keyBytes, 0, BLOCK_SIZE);
            signatureHashKeys.add(keyBytes);
        }

        return signatureHashKeys;
    }

    /**
     * @param inputKeyPairs - Array of concat key pairs.
     * @return - Array of decomposed key pairs.
     */
    private static ArrayList<HashKeyPair> getKeyPairHashDecomposition(byte[] inputKeyPairs) {

        ArrayList<HashKeyPair> keyPairs = new ArrayList<>();

        for (int i = 0; i < SHA256_LENGTH; i++) {
            keyPairs.add(new HashKeyPair());
        }

        for (int i = 0; i < 2 * SHA256_LENGTH; i++) {
            byte[] keyBytes = new byte[BLOCK_SIZE];
            System.arraycopy(inputKeyPairs, i * BLOCK_SIZE, keyBytes, 0, BLOCK_SIZE);
            HashKeyPair keyPair = keyPairs.get(i % SHA256_LENGTH);
            keyPair.setBytes(i, keyBytes);
        }

        return keyPairs;
    }

    /**
     * @param document      - {0,1} - input document for which sign is given.
     * @param signature     - Big signature array of concat keys.
     * @param inputKeyPairs - Input keys for given signature.
     * @return boolean value determines correctness of signature for given document.
     */
    private static boolean checkSignCorrectness(String document, byte[] signature, byte[] inputKeyPairs) {

        ArrayList<byte[]> signatureKeys = getSignatureDecomposition(signature);
        ArrayList<HashKeyPair> keyPairs = getKeyPairHashDecomposition(inputKeyPairs);

        for (int i = 0; i < SHA256_LENGTH; i++) {
            byte[] bitKeyHash = SHA256Bytes(signatureKeys.get(i));
            int bit = document.charAt(i) == '0'
                    ? 0
                    : 1;

            if (!Arrays.equals(bitKeyHash, keyPairs.get(i).getKeyFrom(bit))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param keyPairs - Array of pair (private, public) keys of SHA256_LENGTH size.
     * @param document - {0,1} - input string for sign.
     * @return - Lamport sign.
     */
    private static String sign(ArrayList<ArrayList<byte[]>> keyPairs, String document) {

        byte[] signByteArray = new byte[SHA256_LENGTH * BLOCK_SIZE];

        for (int i = 0; i < SHA256_LENGTH; i++) {
            int bit = document.charAt(i) == '0'
                    ? 0
                    : 1;

            byte[] key = keyPairs.get(i).get(bit);
            System.arraycopy(key, 0, signByteArray, i * BLOCK_SIZE, BLOCK_SIZE);
        }

        return base64Encoder.encodeToString(signByteArray);
    }


    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String merkleStringRoot = reader.readLine();
        byte[] merkleByteRoot = decodeBase64(merkleStringRoot);

        String stringKeyID;

        ArrayList<ArrayList<ArrayList<byte[]>>> keyArray = new ArrayList<>();
        for (int i = 0; i < SHA256_LENGTH; i++) {
            keyArray.add(new ArrayList<>());
            for (int j = 0; j < SHA256_LENGTH; j++) {
                keyArray.get(i).add(new ArrayList<>());
                keyArray.get(i).get(j).add(new byte[]{});
                keyArray.get(i).get(j).add(new byte[]{});
            }
        }

        while ((stringKeyID = reader.readLine()) != null) {

            int keyID = Integer.parseInt(stringKeyID);
            String documentOut = completedKeyArray[keyID] == 0 ? ZEROS : ONES;
            System.out.println(documentOut);

            byte[] signature = decodeBase64(reader.readLine());
            byte[] keyPairs = decodeBase64(reader.readLine());

            boolean isSignCorrect = checkSignCorrectness(documentOut, signature, keyPairs);


            ArrayList<byte[]> merkleProof = new ArrayList<>();
            for (int i = 0; i < TREE_HEIGHT; i++) {
                String base64ProofStep = reader.readLine();
                byte[] proofStepBytes = decodeBase64(base64ProofStep);
                merkleProof.add(proofStepBytes);
            }

            boolean merkleProofCheckCondition = checkMerkleProof(merkleByteRoot, merkleProof, keyPairs, keyID);

            String documentForSign = reader.readLine();

            if (!isSignCorrect || !merkleProofCheckCondition) {
                System.out.println("NO");
            } else {
                System.out.println("YES");

                if (completedKeyArray[keyID] != 2) {
                    ArrayList<byte[]> signatureDecomposition = getSignatureDecomposition(signature);
                    for (int i = 0; i < SHA256_LENGTH; i++) {
                        keyArray.get(keyID).get(i).set(completedKeyArray[keyID], signatureDecomposition.get(i));
                    }
                    completedKeyArray[keyID]++;
                }
            }

            if (completedKeyArray[keyID] != 2) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
                System.out.println(sign(keyArray.get(keyID), documentForSign));
                return;
            }
        }
    }
}

