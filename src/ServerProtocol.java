import java.util.*;

public class ServerProtocol {

    private Dealer dealer = new Dealer();
    private Player hero = new Player("Player 1");
    private Player villain = new Player("Computer");
    private LinkedList<Player> t = new LinkedList<>();


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
                theOutput += villain.toString(false) + "\n"+ hero.toString(true);
                System.out.println(table.toString());
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
                theOutput = "Player1 Check\n";
                dealer.flop();
                theOutput += "Board: " + dealer.boardToString();
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

//    public static void runGame() {
//        printBanner();
//        System.out.println();
//        Scanner scr = new Scanner(System.in);
//
//        //create table of 6 players
//        Table table = new Table();
//        boolean donePlaying = false;
//
//        //loop while playing
//        while (!donePlaying) {
//            System.out.println("Would you like to play a hand? Y / N");
//            if (!scr.next().equalsIgnoreCase("y")) {
//                donePlaying = true;
//            } else {
//                Dealer dealer = new Dealer();
//                dealer.preFlop(table);
//                dealer.flop();
//                dealer.printBoard();
//                dealer.turn();
//                dealer.printBoard();
//                dealer.river();
//                dealer.printBoard();
//                dealer.assignHandRanks(table);
//                table.toString();
//            }
//        }
//
//    }

    private static String printBanner(){
        return("\n\n*******************************************************************\n" +
                "*  _____                    _   _       _     _ _                 *\n" +
                "* |_   _|____  ____ _ ___  | | | | ___ | | __| ( ) ___ _ __ ___   *\n" +
                "*   | |/ _ \\ \\/ / _` / __| | |_| |/ _ \\| |/ _` |/ / _ \\ '_ ` _ \\  *\n" +
                "*   | |  __/>  < (_| \\__ \\ |  _  | (_) | | (_| | |  __/ | | | | | *\n" +
                "*   |_|\\___/_/\\_\\__,_|___/ |_| |_|\\___/|_|\\__,_|  \\___|_| |_| |_| *\n" +
                "*                                                                 *\n" +
                "*******************************************************************\n\n" +
                "Welcome to this variant of Texas Hold'em. You will begin with 100 " +
                "chips. You are dealt 2 cards and 5 cards are dealt by the dealer." +
                "Like Blackjack, you are playing against the dealer. You can always " +
                "check you hand or bet any amount on any betting street, and the " +
                "dealer will always call you. The player with the better hand at" +
                "hand at showdown will win the pot.\n");
    }

    private static String printAvailableOptions(){
        return("\nAvailable Moves: CHECK BET <int> EXIT\n" +
                "\nEND");
    }
}