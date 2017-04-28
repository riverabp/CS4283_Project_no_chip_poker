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

        if(args[0].equalsIgnoreCase("start")){
            try{
                theOutput = printBanner()  + "\n";
                t.add(hero);
                t.add(villain);
                table = new Table(t);
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
                if(dealer.getState() == Dealer.STATE.PREFLOP){
                    dealer.flop();
                    theOutput += "Board: " + dealer.boardToString();
                    dealer.setState(Dealer.STATE.POSTFLOP);
                } else if(dealer.getState() == Dealer.STATE.POSTFLOP){
                    dealer.turn();
                    theOutput += "Board: " + dealer.boardToString();
                    dealer.setState(Dealer.STATE.POSTTURN);
                }else if(dealer.getState() == Dealer.STATE.POSTTURN){
                    dealer.river();
                    theOutput += "Board: " + dealer.boardToString();
                    dealer.setState(Dealer.STATE.POSTRIVER);
                } else if(dealer.getState() == Dealer.STATE.POSTRIVER){
                    theOutput += "Board: " + dealer.boardToString();
                    dealer.setState(Dealer.STATE.SHOWDOWN);
                }
                theOutput += printAvailableOptions();
            } catch (NumberFormatException e) {
                theOutput = "ERROR: NumberFormatException";
            }

        } else if (args[0].equalsIgnoreCase("BET")) {
            theOutput = "player bet: ";
            int b = Integer.parseInt(args[1]);
            hero.bet(b);
            villain.bet(b);
            table.setPot(table.getPot() + (2 * b));
            theOutput += b;
            theOutput += "Pot: " + table.getPot();
            theOutput += printAvailableOptions();
        } else {
            theOutput = "ERROR: invalid argument";
            theOutput += printAvailableOptions();
        }

        return theOutput;
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
                "Welcome to this variant of Texas Hold'em. You will begin with 100\n " +
                "chips. You are dealt 2 cards, and 5 cards are dealt by the dealer.\n" +
                "Like Blackjack, you are playing against the dealer. You can always\n " +
                "check you hand or bet any amount on any betting street, and the\n " +
                "dealer will always call you. The player with the better hand at\n" +
                "showdown will win the pot.\n");
    }

    private static String printAvailableOptions(){
        return("\nAvailable Moves: check bet <int> exit\n" +
                "\nEND");
    }
}