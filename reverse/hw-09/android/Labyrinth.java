import java.util.Base64;

public class Labyrinth {
    public static void main(String[] args) {
        byte[] robot_flatten_map = Base64.getDecoder().decode("6enp6enp6enp6enp6enp6enp6enp6eml6urq6unq6erp6urq6urq6urq6enp6unq6enp6unq6erp6enq6enp6unp6erp6erq6urq6urq6urq6urp6unp6enq6erq6unp6enp6unq6enp6unp6enp6unq6erq6urq6unp6urq6erq6unp6erp6unp6enp6erp6enp6enp6enp6enq6urp6urq6urq6urq6urq6erqsunp6unp6erp6unp6enp6enp6enq6enp6erq6unq6erq6urq6urq6urq6urp6enp6enp6enp6enp6enp6enp6enp6ek=");
        for (int i = 0; i < robot_flatten_map.length; i++) {
            robot_flatten_map[i] = (byte) (robot_flatten_map[i] ^ 202);
        }
        System.out.println(robot_flatten_map.length);
        for (int i = 0; i < robot_flatten_map.length / 22; ++i) {
            for (int j = 0; j < 22; ++j) {
                System.out.print((char) robot_flatten_map[i * 22 + j]);
            }
            System.out.println();
        }


// Fucking path:= DDDDDD RR UUU RR D RRRRR DD LLL DD RRRRRRRRRRR UU RR

//  ######################
//  #o    # # #         ##
//  # # ### # # ### ### ##
//  # ##             # ###
//  # #   ##### # ### ####
//  # # #      ##   #   ##
//  # # ###### ###########
//  #   #            #  x#
//  # ### # ########## ###
//  #   # #             ##
//  ######################


// FLAG:= spbctf{that's_so_amazing_android_is_easy}

    }
}
