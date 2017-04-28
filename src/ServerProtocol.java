import java.util.*;

public class ServerProtocol {

    private Dealer dealer = new Dealer();
    private Player hero = new Player("Player 1");
    private Player villain = new Player("Computer");
    private LinkedList<Player> t = new LinkedList<>();
    private Table table;


    public String processInput(String theInput) {
        String theOutput = "";

        String[] args = theInput.trim().split("\\s");

        if(dealer.getState() == Dealer.STATE.SHOWDOWN){
            theOutput += "showdown.";
        } else {
            if (args[0].equalsIgnoreCase("START")) {
                theOutput = printBanner() + "\n";
                t.add(hero);
                t.add(villain);
                table = new Table(t);
            } else if (args[0].equalsIgnoreCase("EXIT")) {
                theOutput = "Game Over";
            } else if (args[0].equalsIgnoreCase("POST")) {
                dealer.preFlop(table);
                theOutput += hero.toString(true);
            } else if (args[0].equalsIgnoreCase("CHECK")) {
                theOutput += deal();
            } else if (args[0].equalsIgnoreCase("BET")) {
                int b = Integer.parseInt(args[1]);
                hero.bet(b);
                villain.bet(b);
                table.setPot(table.getPot() + (2 * b));
                theOutput += "\nPot: " + table.getPot();
                theOutput += hero.toString(true);
                theOutput += deal();
            } else {
                theOutput = "ERROR: invalid argument";
            }
        }
        theOutput += printAvailableOptions();
        return theOutput;
    }

    private String deal(){
        String r = "";
        if (dealer.getState() == Dealer.STATE.PREFLOP) {
            dealer.flop();
            r += "\nBoard: " + dealer.boardToString();
        } else if (dealer.getState() == Dealer.STATE.POSTFLOP) {
            dealer.turn();
            r += "\nBoard: " + dealer.boardToString();
        } else if (dealer.getState() == Dealer.STATE.POSTTURN) {
            dealer.river();
            r += "\nBoard: " + dealer.boardToString();
        } else if (dealer.getState() == Dealer.STATE.POSTRIVER) {
            r += "\nBoard: " + dealer.boardToString();
            dealer.setState(Dealer.STATE.SHOWDOWN);
        }
        return r;
    }

    private static String printBanner(){
        return("\n\n*******************************************************************\n" +
                "*  _____                    _   _       _     _ _                 *\n" +
                "* |_   _|____  ____ _ ___  | | | | ___ | | __| ( ) ___ _ __ ___   *\n" +
                "*   | |/ _ \\ \\/ / _` / __| | |_| |/ _ \\| |/ _` |/ / _ \\ '_ ` _ \\  *\n" +
                "*   | |  __/>  < (_| \\__ \\ |  _  | (_) | | (_| | |  __/ | | | | | *\n" +
                "*   |_|\\___/_/\\_\\__,_|___/ |_| |_|\\___/|_|\\__,_|  \\___|_| |_| |_| *\n" +
                "*                                                                 *\n" +
                "*******************************************************************\n\n" +
                "Welcome to this variant of Texas Hold'em. You will begin with 100\n" +
                "chips. You are dealt 2 cards, and 5 cards are dealt by the dealer.\n" +
                "Like Blackjack, you are playing against the dealer. You can always\n" +
                "check you hand or bet any amount on any betting street, and the\n" +
                "dealer will always call you. The player with the better hand at\n" +
                "showdown will win the pot. You must post 1 chip to be dealt a\n" +
                "hand. See how high you can grow your stack!\n" +
                "\nEnter \"post\" to post your 1 chip blind.");
    }

    private String printAvailableOptions() {
        String r = "";
        if (dealer.getState() == Dealer.STATE.PREDEAL) {
            r += "\nChips: " + hero.getMyStack() + "\nAvailable Moves: post exit\n" +
                    "\nEND";
        } else {
            r += "\nChips: " + hero.getMyStack() + "\nAvailable Moves: check bet <int> exit\n" +
                    "\nEND";
        }
        return r;
    }
}