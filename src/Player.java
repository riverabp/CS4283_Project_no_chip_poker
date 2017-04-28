import java.util.Random;

/**
 *A player has a stack of chips, 2 hole cards, and a name. A player can make bets, check, fold, call, and raise.
 */
public class Player {

    public final double DEFAULT_STACK = 200;
    public final int HOLE_CARDS = 2;
    public final int HAND_CARDS = 5;


    private double myStack;
    private Card[] myHoleCards;
    private Card[] myHand;
    private String myName;
    private Card.HankRank myHandRank;

    /**
     * default ctor
     */
    public Player(){
        myStack = DEFAULT_STACK;
        Random random = new Random();
        myName = Integer.toString(random.nextInt(100000));
        myHoleCards = new Card[HOLE_CARDS];
        myHand = new Card[HAND_CARDS];
        myHandRank = Card.HankRank.HIGH_CARD;
    }

    /**
     * default ctor
     */
    public Player(String n){
        myStack = DEFAULT_STACK;
        Random random = new Random();
        myName = n;
        myHoleCards = new Card[HOLE_CARDS];
        myHand = new Card[HAND_CARDS];
        myHandRank = Card.HankRank.HIGH_CARD;
    }

    /**
     * default ctor
     */
    public Player(Card c1, Card c2){
        myStack = DEFAULT_STACK;
        Random random = new Random();
        myName = Integer.toString(random.nextInt(100000));
        myHoleCards = new Card[HOLE_CARDS];
        setHoleCards(c1,c2);
        myHand = new Card[HAND_CARDS];
        myHandRank = Card.HankRank.HIGH_CARD;
    }

    /**
     * Change hole cards
     * @param c1
     * @param c2
     */
    public void setHoleCards(Card c1, Card c2){
        myHoleCards[0] = c1;
        myHoleCards[1] = c2;
    }

    /**
     * Set best n-card hand
     * @param hand
     */
    public void setHand(Card[] hand){
        for (int i = 0; i < hand.length; i++){
            myHand[i] = hand[i];
        }
    }

    /**
     * Set best hand rank
     * @param hand
     */
    public void setHandRank(Card.HankRank hand){
        myHandRank = hand;
    }

    /**
     * @return hole card
     */
    public Card getCard(int index){
        return myHoleCards[index];
    }

    /**
     * print players stack, cards, and name
     */
    public String toString(){
        String r = "";
        r += ("name: " + myName + "\n");
        r += ("stack: " + myStack + "\n");
        r += ("hole cards: \n");
        for (int i = 0; i < myHoleCards.length; i++){
            r += myHoleCards[i].toString();
        }
        r += ("hand: "+myHandRank+"\n");
        return r;
    }
}
