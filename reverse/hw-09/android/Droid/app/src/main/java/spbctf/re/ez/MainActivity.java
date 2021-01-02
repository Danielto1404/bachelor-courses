package spbctf.re.ez;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends Activity {
    public EditText a;

    public class a implements View.OnClickListener {
        public a() {
        }

        public void onClick(View view) {
            MainActivity mainActivity = MainActivity.this;
            mainActivity.a(mainActivity.a.getText().toString());
        }
    }

    public void a(String str) {
        byte[] bytes = str.getBytes();
        // TODO: Initialize flatten map
        byte[] robot_flatten_map = Base64.decode("6enp6enp6enp6enp6enp6enp6enp6eml6urq6unq6erp6urq6urq6urq6enp6unq6enp6unq6erp6enq6enp6unp6erp6erq6urq6urq6urq6urp6unp6enq6erq6unp6enp6unq6enp6unp6enp6unq6erq6urq6unp6urq6erq6unp6erp6unp6enp6erp6enp6enp6enp6enq6urp6urq6urq6urq6urq6erqsunp6unp6erp6unp6enp6enp6enq6enp6erq6unq6erq6urq6urq6urq6urp6enp6enp6enp6enp6enp6enp6enp6ek=\n", 0);
        for (int i = 0; i < robot_flatten_map.length; i++) {
            robot_flatten_map[i] = (byte) (robot_flatten_map[i] ^ 202);
        }
        for (int i = 0; i < robot_flatten_map.length / 22; ++i) {
            for (int j = 0; j < 22; ++j) {
                System.out.print((char) robot_flatten_map[i * 22 + j]);
            }
            System.out.println();
        }
        byte[] decode2 = Base64.decode("h4SWl4CSvbIOBxLhtau1qZmnq5WODwgBq5WoorSpr6KZr7WZowcVv7s=\n", 0);
        int y = 1;
        int x = 1;
        for (int i = 0; i < bytes.length; i++) {
            // Down
            if (bytes[i] == 'D') {
                int i5 = 0;
                while (i5 < 34) {
                    i5++;
                    decode2[i] = (byte) (decode2[i] ^ (i5 * 239));
                }
                y++;
                // Right
            } else if (bytes[i] == 'R') {
                int i6 = 0;
                while (i6 < 34) {
                    i6++;
                    decode2[i] = (byte) (decode2[i] ^ (i6 * 97));
                }
                x++;
                // Left
            } else if (bytes[i] == 'L') {
                int i7 = 0;
                while (i7 < 34) {
                    i7++;
                    decode2[i] = (byte) (decode2[i] ^ (i7 * 65));
                }
                x--;
                // Up
            } else if (bytes[i] == 'U') {
                int i8 = 0;
                while (i8 < 34) {
                    i8++;
                    decode2[i] = (byte) (decode2[i] ^ (i8 * 65));
                }
                y--;
            } else {
                int i9 = 0;
                while (i9 < 34) {
                    i9++;
                    decode2[i] = (byte) (decode2[i] ^ (i9 * 65));
                }
            }
            // TODO: Make flatten coordinate
            int flatten = (y * 22) + x;
            if (robot_flatten_map[flatten] == ' ' || robot_flatten_map[flatten] == 'x' || robot_flatten_map[flatten] == 'o') {
                int i11 = 0;
                while (i11 < 34) {
                    i11++;
                    decode2[i] = (byte) (decode2[i] ^ (i11 * 67));
                }
            } else {
                int i12 = 0;
                while (i12 < 41) {
                    int i13 = i12 + 1;
                    decode2[i12] = (byte) (decode2[i12] ^ (i13 * 69));
                    i12 = i13;
                }
            }
        }
        System.out.println(robot_flatten_map[(y * 22) + x] == 'x' ? new String(decode2) : "Try again");
        Toast makeText = Toast.makeText(this, robot_flatten_map[(y * 22) + x] == 'x' ? new String(decode2) : "Nope", Toast.LENGTH_SHORT);
        makeText.setGravity(48, 0, 0);
        makeText.show();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        this.a = findViewById(R.id.solution);
        findViewById(R.id.solve).setOnClickListener(new a());
    }
}
