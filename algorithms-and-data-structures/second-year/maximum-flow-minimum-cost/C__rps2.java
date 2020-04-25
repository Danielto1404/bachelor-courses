import java.util.Arrays;
import java.util.Scanner;

public class C__rps {

    public static class Player {
        int rock, paper, scissor;

        public Player(int rock, int paper, int scissor) {
            this.rock = rock;
            this.paper = paper;
            this.scissor = scissor;
        }

        public int minimizeWin(Player other) {
            int rockWins = other.rock - rock - paper; // Some value should be greater or equal zero!
            int paperWins = other.paper - paper - scissor;
            int scissorsWins = other.scissor - scissor - rock;
            return Arrays.stream(new int[]{0, rockWins, paperWins, scissorsWins})
                    .max()
                    .getAsInt();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Player rostislav = new Player(sc.nextInt(), sc.nextInt(), sc.nextInt());
        Player miroslav = new Player(sc.nextInt(), sc.nextInt(), sc.nextInt());
        System.out.println(rostislav.minimizeWin(miroslav));
    }
}
