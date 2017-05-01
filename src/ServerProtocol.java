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

        if (args[0].equalsIgnoreCase("START")) {
            theOutput = printBanner() + "\n";
            t.add(hero);
            t.add(villain);
            table = new Table(t);
        } else if (args[0].equalsIgnoreCase("EXIT")) {
            theOutput = "Game Over";
        } else if (args[0].equalsIgnoreCase("POST")) {
            dealer.preFlop(table);
            if(hero.getMyStack() >= 2) {
                hero.bet(2);
                table.setPot(table.getPot() + (2 * 2));
            }
            table.setPot(4);
            theOutput += hero.toString(true);
        } else if (args[0].equalsIgnoreCase("CHECK")) {
            theOutput += deal();
            if(hero.getMyStack() >= 2) {
                hero.bet(2);
                table.setPot(table.getPot() + (2 * 2));
            }
            theOutput += hero.toString(true);
            theOutput += "\nPot: " + table.getPot();
        } else if (args[0].equalsIgnoreCase("BET")) {
            int b = Integer.parseInt(args[1]);
            hero.bet(b);
            villain.bet(b);
            table.setPot(table.getPot() + (2 * b));
            theOutput += deal();
            theOutput += hero.toString(true);
            theOutput += "\nPot: " + table.getPot();
        } else if (args[0].equalsIgnoreCase("FOLD")){
            dealer.setState(Dealer.STATE.PREDEAL);
            table.setPot(0);
        }else  {
            theOutput = "ERROR: invalid argument";
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
        } else if (dealer.getState() == Dealer.STATE.POSTRIVER){
            dealer.assignHandRanks(table);
            if(villain.getHandRank().compareTo(hero.getHandRank()) > 0){
                table.setPot(0);
                r += "Computer Wins\n";
                r += "Computer Hand: " + villain.getHandRank();
                r += "\nComputer Hand: " + villain.toString(true);
                r += "\nYour Hand: " + hero.getHandRank();
                dealer.setState(Dealer.STATE.PREDEAL);
            } else {
                hero.winChips(table.getPot());
                table.setPot(0);
                r += "You Win!!\n";
                r += "Computer Hand: " + villain.getHandRank();
                r += "\nComputer Hand: " + villain.toString(true);
                r += "\nYour Hand: " + hero.getHandRank();
                dealer.setState(Dealer.STATE.PREDEAL);
            }
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
                "Welcome to this variant of Texas Hold'em. You will begin with 50\n" +
                "chips. You are dealt 2 cards, and 5 cards are dealt by the dealer.\n" +
                "Like Blackjack, you are playing against the dealer. You must post 2\n" +
                "chips to be dealt a hand. Checking also costs 2 chips, so fold if\n" +
                "you think you are going to lose. If you think you may win the hand,\n"+
                "you can bet any amount, and the dealer will always call you. If the\n"+
                "hand goes to showdown, the winner takes all\n"+
                "\nEnter \"post\" to receive your first hand." +
                "\nEnter \"exit\" at any time to quit.");
    }

    private String printAvailableOptions() {
        String r = "";
        if (dealer.getState() == Dealer.STATE.PREDEAL) {
            r += "\nChips: " + hero.getMyStack() +
                    "\n*******************************************************************" +
                    "\nAvailable Moves: post exit\n" +
                    "\nEND";
        } else {
            r += "\nChips: " + hero.getMyStack() + "\nAvailable Moves: fold check bet <int> exit\n" +
                    "\nEND";
        }
        return r;
    }
}