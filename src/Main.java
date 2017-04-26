import java.util.Scanner;

public class Main {

    public static final double SMALL_BLIND = 1.0;
    public static final double BIG_BLIND = 2.0;
    public final double DEFAULT_STACK = 200;
    public final int DEFAULT_HOLE_CARDS = 2;

    public static void main(String[] args) {
        PokerGame game = new PokerGame();
        game.runGame();
    }
}
