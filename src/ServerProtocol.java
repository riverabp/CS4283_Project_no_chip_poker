import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerProtocol {

    private Dealer dealer = new Dealer();
    private Player hero = new Player("hero");
    private Player villain = new Player("villain");
    private LinkedList<Player> t = new LinkedList<Player>();


    public String processInput(String theInput) {
        String theOutput = "";

        String[] args = theInput.trim().split("\\s");

        if(args[0].equalsIgnoreCase("start")){
            try{
                theOutput = printBanner()  + "\n";
                t.add(hero);
                t.add(villain);
                Table table = new Table(t);
                dealer.preFlop(table);
                String preflop = table.toString();
                theOutput += preflop;
                System.out.println(preflop);
                theOutput += printAvailableOptions();
            } catch (NumberFormatException e){
                theOutput = "ERROR: NumberFormatException";
            }
        } else if (args[0].equalsIgnoreCase("exit")) {
            theOutput = "Game Over";
        } else if (args[0].equalsIgnoreCase("FOLD")) {
            try {
                theOutput = "folded.";
            } catch (NumberFormatException e) {
                theOutput = "ERROR: NumberFormatException";
            }


        } else if (args[0].equalsIgnoreCase("CHECK")) {
            try {
                theOutput = "player check.";
            } catch (NumberFormatException e) {
                theOutput = "ERROR: NumberFormatException";
            }

        } else if (args[0].equalsIgnoreCase("BET")) {
            theOutput = "player bet";
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
                table.toString();
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
                "*******************************************************************\n\n" +
                "Welcome to Texas Hold'em. You will begin with 200 chips. The blinds are\n" +
                "set at 1/2. You are playing heads up against the computer and will start\n" +
                "on the button.\n");
    }

    private static String printAvailableOptions(){
        return("\nAvailable Moves: FOLD CALL BET <int> EXIT\n" +
                "\nEND");
    }
}