import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerProtocol {
    private static final String CHECK = "CHECK";
    private static final String BET = "BET";
    private static final String FOLD = "FOLD";
    private TreeMap<Player,ReentrantReadWriteLock> map = null;
    private PokerGame game = new PokerGame();

    public ServerProtocol(PokerGame game, TreeMap<Account,ReentrantReadWriteLock> m) {
        this.map = m;
        this.game = game;
    }

    public String processInput(String theInput) {
        String theOutput = "";

        String[] args = theInput.trim().split("\\s");

        if(args[0].equals(BALANCE)){
            try {
                int accountNumber = Integer.parseInt(args[1]);
                Account acc = piggyBank.getAccount(accountNumber);
                if(acc == null){
                    theOutput = "ERROR: account not found";
                } else {
                    map.get(acc).readLock().lock();
                    theOutput += acc.getBalance();
                    map.get(acc).readLock().unlock();
                }
            } catch (NumberFormatException e){
                theOutput = "ERROR: NumberFormatException";
            }


        } else if (args[0].equals(TRANSFER)) {
            try{
                Account a1 = piggyBank.getAccount(Integer.parseInt(args[1]));
                Account a2 = piggyBank.getAccount(Integer.parseInt(args[2]));
                int amount = Integer.parseInt(args[3]);

                try{
                    map.get(a1).readLock().lock();
                    map.get(a2).readLock().lock();
                    a1.transferTo(amount, a2);
                    map.get(a1).readLock().unlock();
                    map.get(a2).readLock().unlock();
                    theOutput = "OK";
                } catch(LowFundsException e){
                    theOutput = "Error: insufficient funds";
                    map.get(a1).readLock().unlock();
                    map.get(a2).readLock().unlock();
                }

            } catch (NumberFormatException e){
                theOutput = "ERROR: NumberFormatException";
            }

        } else if (args[0].equals(SNAPSHOT)){
            Collection<Account> accs = piggyBank.getAllAccounts();
            for(Account a : accs){
                map.get(a).readLock().lock();
                theOutput += a.getAccountNumber() + ":" + a.getBalance() + ",";
                map.get(a).readLock().unlock();
            }

        } else {
            theOutput = "ERROR: invalid argument";
        }

        return theOutput;
    }
}