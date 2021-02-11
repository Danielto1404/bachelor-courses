import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * User: Aksenov Vitaly
 * Date: 24.10.2018
 * Time: 12:32
 */
public class PCMSInteractProcessor {
    public static final int OK_EXIT_CODE = 0;
    public static final int WA_EXIT_CODE = 10;

    public static final int KEY = 54654365;

    public static final int TRIES = 1000;

    public Random rnd;

    public byte[][][][] X;
    public byte[][][][] Y;

    public class Block {
        String base64;
        byte[] bytes;
        long id;
        Node node;
    }

    public class Node {
        Node l, r, p;
        byte[] hash;
        String base64;
    }

    public class Query {
        long id;
        String base64;
        String[] proof;
    }

    int h = 8;

    Node root;

    MessageDigest digest;
    Block[] blocks;

    public Node addBlock(Block block) {
        Node curr = root;
        for (int i = h - 1; i >= 0; i--) {
            if (((1L << i) & block.id) == 0) {
                if (curr.l == null) {
                    curr.l = new Node();
                    curr.l.p = curr;
                }
                curr = curr.l;
            } else {
                if (curr.r == null) {
                    curr.r = new Node();
                    curr.r.p = curr;
                }
                curr = curr.r;
            }
        }
        byte[] to_hash = new byte[block.bytes.length + 1];
        for (int i = 0; i < block.bytes.length; i++) {
            to_hash[i + 1] = block.bytes[i];
        }
        curr.hash = digest.digest(to_hash);
        curr.base64 = block.base64;
        block.node = curr;
        return curr;
    }

    public void fillHashes(Node node) {
        if (node.hash != null) {
            return;
        }
        if (node.l != null) {
            fillHashes(node.l);
        }
        if (node.r != null) {
            fillHashes(node.r);
        }
        byte[] lbytes = node.l == null ? new byte[0] : node.l.hash;
        byte[] rbytes = node.r == null ? new byte[0] : node.r.hash;
        byte[] to_hash = new byte[lbytes.length + rbytes.length + 2];
        to_hash[0] = 1;
        for (int i = 0; i < lbytes.length; i++) {
            to_hash[i + 1] = lbytes[i];
        }
        to_hash[lbytes.length + 1] = 2;
        for (int i = 0; i < rbytes.length; i++) {
            to_hash[i + 2 + lbytes.length] = rbytes[i];
        }
        node.hash = digest.digest(to_hash);
    }

    public void solveQuery(Query query) {
        Node curr = root;
        query.proof = new String[h];
        for (int i = h - 1; i >= 0; i--) {
            if (curr == null) {
                query.proof[i] = "";
                continue;
            }
            if ((query.id & (1L << i)) == 0) {
                if (curr.r == null) {
                    query.proof[i] = "";
                } else {
                    query.proof[i] = Base64.getEncoder().encodeToString(curr.r.hash);
                }
                curr = curr.l;
            } else {
                if (curr.l == null) {
                    query.proof[i] = "";
                } else {
                    query.proof[i] = Base64.getEncoder().encodeToString(curr.l.hash);
                }
                curr = curr.r;
            }
        }
        query.base64 = curr == null ? "" : curr.base64;
    }

    public byte[] packY(int id) {
        byte[] join = new byte[2 * 256 * 32];
        int l = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 32; k++) {
                    join[l++] = Y[id][i][j][k];
                }
            }
        }
        return join;
    }

    public byte[] packX(int id, String hash) {
        byte[] join = new byte[256 * 32];

        int l = 0;
        for (int i = 0; i < 256; i++) {
            int toChoose = hash.charAt(i) == '0' ? 0 : 1;
            for (int j = 0; j < 32; j++) {
                join[l++] = X[id][toChoose][i][j];
            }
        }
        return join;
    }

    public void generate() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        X = new byte[256][2][256][32];
        Y = new byte[256][2][256][32];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 256; k++) {
                    for (int l = 0; l < 32; l++) {
                        rnd.nextBytes(X[i][j][k]);
                    }
                    Y[i][j][k] = digest.digest(X[i][j][k]);
                }
            }
        }
        root = new Node();
        blocks = new Block[256];
        for (int i = 0; i < 256; i++) {
            blocks[i] = new Block();
            blocks[i].id = i;
            blocks[i].bytes = packY(i);
            blocks[i].base64 = Base64.getEncoder().encodeToString(blocks[i].bytes);
            addBlock(blocks[i]);
        }
        fillHashes(root);
    }

    public String changeRandomSymbol(String s) {
        while (true) {
            int x = rnd.nextInt(s.length() - 1);
            char[] chars = s.toCharArray();
            chars[x] = (char) ('a' + rnd.nextInt(26));
            String n = new String(chars);
            if (!n.equals(s)) {
                return n;
            }
        }
    }

    public int run(String infilename, String outfilename) throws IOException {
        try (Scanner in = new Scanner(new File(infilename));
             PrintWriter out = new PrintWriter(outfilename)) {
            try {
                int seed = in.nextInt();
                rnd = new Random(seed);
                try (Scanner pipeIn = new Scanner(System.in);
                     PrintWriter pipeOut = new PrintWriter(System.out)) {

                    generate();

                    ArrayList<Integer> order = new ArrayList<>();
                    for (int i = 0; i < 256; i++) {
                        order.add(i);
                    }
                    Collections.shuffle(order, rnd);

                    pipeOut.println(Base64.getEncoder().encodeToString(root.hash));
                    int it = rnd.nextInt(256);
                    for (int queries = 0; queries < TRIES; queries++) {
                        int id = order.get(it);
                        pipeOut.println(id);
                        pipeOut.flush();
                        String hash = pipeIn.next();
                        byte[] packX = packX(id, hash);

                        Query query = new Query();
                        query.id = id;

                        solveQuery(query);

                        boolean bad = rnd.nextBoolean();
                        if (bad) {
                            if (rnd.nextBoolean()) {
                                int i = rnd.nextInt(query.proof.length);
                                query.proof[i] = changeRandomSymbol(query.proof[i]);
                            } else {
                                packX[rnd.nextInt(packX.length)] += 1 + rnd.nextInt(254);
                            }
                        }
                        if (!bad || seed % 2 == 1) {
                            it = (it + 1) % 256;
                        }
                        pipeOut.println(Base64.getEncoder().encodeToString(packX));
                        pipeOut.println(query.base64);
                        for (int i = 0; i < query.proof.length; i++) {
                            pipeOut.println(query.proof[i]);
                        }
                        hash = "";
                        for (int i = 0; i < 256; i++) {
                            hash += rnd.nextBoolean() ? "1" : "0";
                        }
                        pipeOut.println(hash);
                        pipeOut.flush();

                        String correct = pipeIn.next();
                        if (!correct.equals("YES") && !correct.equals("NO")) {
                            return writeResult(out, "WA", "Wrong format", OK_EXIT_CODE);
                        }
                        if (bad) {
                            if (correct.equals("YES")) {
                                return writeResult(out, "WA", "Data is incorrect, but participant thinks that it is correct", WA_EXIT_CODE);
                            }
                        } else {
                            if (correct.equals("NO")) {
                                return writeResult(out, "WA", "Data is correct, but participant thinks that it is incorrect", WA_EXIT_CODE);
                            }
                        }

                        String can = pipeIn.next();
                        if (can.equals("NO")) {
                            continue;
                        }
                        if (!can.equals("YES")) {
                            return writeResult(out, "WA", "Wrong format", WA_EXIT_CODE);
                        }
                        String ans = pipeIn.next();
                        packX = packX(id, hash);
                        if (ans.equals(Base64.getEncoder().encodeToString(packX))) {
                            return writeResult(out, "OK", "Correct answer", OK_EXIT_CODE);
                        } else {
                            return writeResult(out, "WA", "Wrong answer", WA_EXIT_CODE);
                        }
                    }

                    return writeResult(out, "WA", "Out of queries", WA_EXIT_CODE);
                }
            } catch (Exception e) {
                return writeResult(out, "WA", "Broken protocol", WA_EXIT_CODE);
            }
        }
    }

    private int writeResult(final PrintWriter out, final String outcome, final String comment, int exitCode) {
        out.println(KEY);
        out.println(outcome);
        out.println(comment);
        return exitCode;
    }

    public static void main(String[] args) throws IOException {
        System.exit(
                new PCMSInteractProcessor().run(
                        args.length >= 2 ? args[0] :
                                "input.txt",
                        args.length >= 2 ? args[1] :
                                "output.txt"
                ));
    }
}
