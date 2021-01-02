import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public class Encoder {

    private static String md5(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

        while (md5Hex.length() < 32) {
            md5Hex.insert(0, "0");
        }

        return md5Hex.toString();
    }

    private static class Pair {
        Character a, b;

        private Pair(char a, char b) {
            this.a = a;
            this.b = b;
        }

        public String md5() {
            return Encoder.md5(String.valueOf(a) + b);
        }

        @Override
        public String toString() {
            return a + " " + b;
        }
    }

    private static final Map<String, Integer> inputHashes = Map.of(
            "06fa567b72d78b7e3ea746973fbbd1d5", 0,
            "142ba1ee3860caecc3f86d7a03b5b175", 1,
            "54229abfcfa5649e7003b83dd4755294", 2,
            "8d2b901035fbd2df68a3b02940ff5196", 3,
            "727999d580f3708378e3d903ddfa246d", 4,
            "ea8a1a99f6c94c275a58dcd78f418c1f", 5,
            "c51ce410c124a10e0db5e4b97fc2af39", 6,
            "a5bfc9e07964f8dddeb95fc584cd965d", 7
    );

//    While fighting, only power is not enough

    public static void main(String[] args) {
        char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        ArrayList<Pair> pairs = new ArrayList<>();

        for (char a : chars) {
            for (char b : chars) {
                if (a != b) {
                    pairs.add(new Pair(a, b));
                }
            }
        }

        Pair[] results = new Pair[8];

        for (Pair p : pairs) {
            Integer index = inputHashes.get(p.md5());
            if (index != null) {
                results[index] = p;
            }
        }

        for (Pair p : results) {
            System.out.println(p);
        }

//        A F
//        7 A
//        9 1
//        3 E
//        C 8
//        A E
//        1 3
//        3 7
    }
}
