import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerProtocol {
    private static final String NEW_GAME = "NEW GAME";
    private static final String CHECK = "CHECK";
    private static final String BET = "BET";
    private static final String FOLD = "FOLD";
    private Dealer dealer = new Dealer();
    private Player player = new Player();

    private String state = NEW_GAME;


    public String processInput(String theInput) {
        String theOutput = "";

        String[] args = theInput.trim().split("\\s");

        if(args[0].equalsIgnoreCase("start")){
            try{
                theOutput = "game start";
            } catch (NumberFormatException e){
                theOutput = "ERROR: NumberFormatException";
            }
        } else if (args[0].equalsIgnoreCase("exit")) {
            theOutput = "Game Over";
        } else if (args[0].equalsIgnoreCase(FOLD)) {
            try {

            } catch (NumberFormatException e) {
                theOutput = "ERROR: NumberFormatException";
            }


        } else if (args[0].equalsIgnoreCase(CHECK)) {
            try {


            } catch (NumberFormatException e) {
                theOutput = "ERROR: NumberFormatException";
            }

        } else if (args[0].equalsIgnoreCase(BET)) {


        } else {
            theOutput = "ERROR: invalid argument";
        }

        return theOutput;
    }

    public static void runGame() {
        printBanner();
        System.out.println();
        Scanner scr = new Scanner(System.in);

        //create table of 6 players
        Table table = new Table();
        boolean donePlaying = false;

        //loop while playing
        while (!donePlaying) {
            System.out.println("Would you like to play a hand? Y / N");
            if (!scr.next().equalsIgnoreCase("y")) {
                donePlaying = true;
            } else {
                Dealer dealer = new Dealer();
                dealer.preFlop(table);
                dealer.flop();
                dealer.printBoard();
                dealer.turn();
                dealer.printBoard();
                dealer.river();
                dealer.printBoard();
                dealer.assignHandRanks(table);
                table.printSelf();
            }
        }

    }

    private static String printBanner(){
        return("*******************************************************************\n" +
                "*  _____                    _   _       _     _ _                 *\n" +
                "* |_   _|____  ____ _ ___  | | | | ___ | | __| ( ) ___ _ __ ___   *\n" +
                "*   | |/ _ \\ \\/ / _` / __| | |_| |/ _ \\| |/ _` |/ / _ \\ '_ ` _ \\  *\n" +
                "*   | |  __/>  < (_| \\__ \\ |  _  | (_) | | (_| | |  __/ | | | | | *\n" +
                "*   |_|\\___/_/\\_\\__,_|___/ |_| |_|\\___/|_|\\__,_|  \\___|_| |_| |_| *\n" +
                "*                                                                 *\n" +
                "*******************************************************************\n\n");
    }
}