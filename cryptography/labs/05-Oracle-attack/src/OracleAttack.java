import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class OracleAttack {

    private static final String ATTACK = "Yes";
    private static final String NO_ATTACK = "No";
    private static final String UNDEFINED = "N/A";

    private static final String YES = "YES";
    private static final String NO = "NO";

    private static final String WRONG_PADDING = "Wrong padding";

    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    private static byte[] base64Decode(String input) {
        return base64Decoder.decode(input);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String AES_base64_string = reader.readLine();
        String IV_base64_string = reader.readLine();

        byte[] IV = base64Decode(IV_base64_string);

        byte checkPaddingByte = IV[2];

        // Увеличиваем 3 байт => если было сообщение No (то есть 3 байт отвечает за padding),
        // то мы получим ответ Wrong padding.
        IV[2]++;
        System.out.println(NO);
        System.out.println(AES_base64_string);
        System.out.println(base64Encoder.encodeToString(IV));

        String response = reader.readLine();

        // Если сообщение о Wrong padding => было зашифрованно сообщение No.
        if (response.equals(WRONG_PADDING)) {
            System.out.println(YES);
            System.out.println(NO_ATTACK);
            return;
        }



        byte paddingFor3LengthMSG = 13;
        byte paddingFor2LengthMSG = 14;

        // Теперь изменяем массив IV так как будто у нас в AES зашифровано N/A =>
        // Меняем ксорим 3 байт с последний буквой N/A ('A') -> и потом все это ксорим  отступом для сообщения длины 2.
        // ( Делаем сообщение "N/" )
        IV[2] = checkPaddingByte;
        IV[2] ^= (byte) (UNDEFINED.charAt(2) ^ paddingFor2LengthMSG);
        for (int i = 3; i < 16; ++i) {
            IV[i] ^= paddingFor2LengthMSG ^ paddingFor3LengthMSG;
        }

        System.out.println(NO);
        System.out.println(AES_base64_string);
        System.out.println(base64Encoder.encodeToString(IV));

        response = reader.readLine();
        System.out.println(YES);

        // Если получили Wrong padding => было зашифрованно сообщение Yes.
        if (response.equals(WRONG_PADDING)) {
            System.out.println(ATTACK);
            return;
        }
        System.out.println(UNDEFINED);
    }
}
