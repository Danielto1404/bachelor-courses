import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Base64Decoder {

    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final String answersFilePath = "/Users/daniilkorolev/Desktop/08/08/green/bin/answers.txt";
    private static final Path outputFilePath = Path.of(
            "/Users/daniilkorolev/Desktop/08/08/green/bin/bin.elf"
    );


    private static void connect(int iterations) {

        try (Socket echoSocket = new Socket("109.233.56.90", 63175);
             PrintWriter serverWriter = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) {

            for (int i = 0; i < iterations + 1; ++i) {

                if (i == iterations) {
                    System.out.print("\n\nFlag: " + serverReader.readLine());
                    return;
                }

                if (i == 0) {
                    System.out.println(serverReader.readLine());
                }

                System.out.println("~~~~~~~~~~~~~~~~~~~~\n" + serverReader.readLine());

                String base64Code = serverReader.readLine();

                byte[] base64Bytes = decoder.decode(base64Code);
                Files.write(outputFilePath, base64Bytes);

                System.out.println("Binary file have been written.");
                System.out.println("Executing angr...");

                new ProcessBuilder()
                        .command("python3", "/Users/daniilkorolev/Desktop/08/08/green/bin/bin_angr.py")
                        .redirectOutput(new File(answersFilePath))
                        .start()
                        .waitFor();


                try (BufferedReader reader = Files.newBufferedReader(Path.of(answersFilePath))) {
//                Answer example:
//                b'EFTn\x00\xf5\xf5\xf5\xf5\xf5\xf5\xf5\xf5\xf5\xb5

                    String rawAnswer = reader.readLine();
                    StringBuilder answer = new StringBuilder();
                    for (int j = 2; ; ++j) {
                        char c = rawAnswer.charAt(j);
                        if (Character.isLetter(c)) {
                            answer.append(c);
                        } else break;
                    }

                    System.out.println("Flag found: " + answer);
                    serverWriter.println(answer);
                }

                System.out.println(serverReader.readLine());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect(10);
    }
}


//    Welcome to THE BINARY CALCULATOR - binculator. You need to reverse 10 binaries 2 minutes each
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 1/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: IXPIkX
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 2/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: JvJm
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 3/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: RTtawE
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 4/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: BCAG
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 5/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: mQukom
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 6/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: FtCGql
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 7/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: wOEd
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 8/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: qSQmk
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 9/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: lqjm
//        answer: YES!
//        ~~~~~~~~~~~~~~~~~~~~
//        next try: 10/10
//        Binary file have been written.
//        Executing angr...
//        Flag found: LNSv
//        answer: YES!
//        Flag: flag: auto{x11_aaxxa_binculated}