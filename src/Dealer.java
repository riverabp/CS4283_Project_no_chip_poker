import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A Dealer has a deck of cards and draws cards to place on the board. A dealer is also responsible for
 * determining the winner in a hand.
 */
public class Dealer {

    public static final int BOARD_SIZE = 5;
    public static final int BURNED_CARDS = 3;
    public static final int DEFAULT_HOLE_CARDS = 2;
    public static final int DEFAULT_HAND_CARDS = 5;

    private Card[] board;
    private Card[] burnedCards;
    private int boardSize;
    private Deck deck;
    private STATE state;
    public enum STATE{
        PREDEAL,
        PREFLOP,
        POSTFLOP,
        POSTTURN,
        POSTRIVER,
    }

    /**
     * Default ctor.
     */
    Dealer(){
        boardSize = 0;
        board = new Card[BOARD_SIZE];
        burnedCards = new Card[BURNED_CARDS];
        deck = new Deck();
        state = STATE.PREDEAL;
    }

    public STATE getState(){
        return this.state;
    }

    public void setState(STATE s){
        this.state = s;
    }

    /**
     * @return Card drawn card
     */
    public Card dealCard(){
        return deck.draw();
    }

    /**
     * deals two hole cards to each player
     * @param table list of players
     */
    public void preFlop(Table table){

        String r = "";
        for (int i = 0; i < table.getPlayerCount(); i++){
            Card c1 = deck.draw();
            Card c2 = deck.draw();
            table.getPlayer(i).setHoleCards(c1,c2);
        }
        this.state = STATE.PREFLOP;
    }

    /**
     * Burn one card and place three on the board
     */
    public void flop(){
        burnedCards[0] = deck.draw();
        board[0] = deck.draw();
        board[1] = deck.draw();
        board[2] = deck.draw();
        boardSize = 3;
        state = STATE.POSTFLOP;
    }

    /**
     * Burn one card and place the turn on the board
     */
    public void turn(){
        burnedCards[1] = deck.draw();
        board[3] = deck.draw();
        boardSize = 4;
        state = STATE.POSTTURN;
    }

    /**
     * Burn one card and place the river on the board
     */
    public void river(){
        burnedCards[2] = deck.draw();
        board[4] = deck.draw();
        boardSize = 5;
        state = STATE.POSTRIVER;
    }

    /**
     * Set board cards for testing purposes
     * @param c
     */
    public void setBoard(Card[] c){
        board = c;
        boardSize = c.length;
    }

    /**
     * Assigns a hand rank to every player
     * @param table
     * @return
     */
    public void assignHandRanks(Table table){
        for (int i = 0; i < table.getPlayerCount(); i++){
            Player p = table.getPlayer(i);
            Card[] availableCards = new Card[DEFAULT_HOLE_CARDS + boardSize];
            for(int j = 0; j < DEFAULT_HOLE_CARDS; j++){
                availableCards[j] = p.getCard(j);
            }
            for (int j = 0; j < boardSize; j++){
                availableCards[j + DEFAULT_HOLE_CARDS] = board[j];
            }

            //sort all 7 cards
            Arrays.sort(availableCards, Collections.reverseOrder());
            boolean hasPair = false;
            boolean hasTwoPair = false;
            boolean hasTrips = false;
            boolean hasQuads = false;
            boolean hasFullhouse = false;
            int[] counts = new int[15];
            Card[] a = availableCards;
            for (int j = 0; j < a.length; j++){
                if(a[j].getRank() == 2){
                    counts[2]++;
                } else if(a[j].getRank() == 3){
                    counts[3]++;
                }else if(a[j].getRank() == 4){
                    counts[4]++;
                }else if(a[j].getRank() == 5){
                    counts[5]++;
                }else if(a[j].getRank() == 6){
                    counts[6]++;
                }else if(a[j].getRank() == 7){
                    counts[7]++;
                }else if(a[j].getRank() == 8){
                    counts[8]++;
                }else if(a[j].getRank() == 9){
                    counts[9]++;
                }else if(a[j].getRank() == 10){
                    counts[10]++;
                }else if(a[j].getRank() == 11){
                    counts[11]++;
                }else if(a[j].getRank() == 12){
                    counts[12]++;
                }else if(a[j].getRank() == 13){
                    counts[13]++;
                }else if(a[j].getRank() == 14){
                    counts[14]++;
                }
            }

            for (int j = 0; j < counts.length; j++){
                if(counts[j] == 4){
                    hasQuads = true;
                } else if(counts[j] == 3){
                    if(hasPair){
                        hasFullhouse = true;
                    } else {
                        hasTrips = true;
                    }
                } else if(counts[j] == 2){
                    if(hasTrips){
                        hasFullhouse = true;
                    } else if(hasPair){
                        hasTwoPair = true;
                    } else {
                        hasPair = true;
                    }
                }
            }
            //availableCards = moveCardGroupsToFront(availableCards);
            //move pairs to the front


            //Check straight flush
            if(hasStraightFlush(availableCards)){
                setHand(p, availableCards, Card.HankRank.STRAIGHT_FLUSH);
            }

            //check quads
            else if(hasQuads) {
                setHand(p, availableCards, Card.HankRank.FOUR_OF_A_KIND);
            }

            //check full house
            else if(hasFullhouse){
                setHand(p, availableCards, Card.HankRank.FULL_HOUSE);
            }
            //check flush
            else if(hasFlush(availableCards)){
                setHand(p, availableCards, Card.HankRank.FLUSH);
            }
            //check straight
            else if(hasStraight(availableCards)){
                setHand(p, availableCards, Card.HankRank.STRAIGHT);
            }

            //check trips
            else if(hasTrips) {
                setHand(p, availableCards, Card.HankRank.THREE_OF_A_KIND);
            }
            //check two pair
            else if(hasTwoPair) {
                setHand(p, availableCards, Card.HankRank.TWO_PAIR);
            }
            //check one pair and high card
            else if(hasPair){
                setHand(p, availableCards, Card.HankRank.PAIR);
            } else {
                setHand(p, availableCards, Card.HankRank.HIGH_CARD);
            }
        }
    }

    /**
     * Print the board cards to the console
     */
    public String boardToString(){
        String r = "";
        System.out.print("Board Cards: ");
        for(int i = 0; i < boardSize; i++){
            r += board[i].toString() + " ";
        }
        System.out.print("\n\n");
        return r;
    }

    /**
     * assign n cards to a player's hand
     */
    private void setHand(Player p, Card[] a, Card.HankRank h){
        Card[] hand = new Card[DEFAULT_HAND_CARDS];
        for(int j = 0; j < DEFAULT_HAND_CARDS; j++){
            hand[j] = a[j];
        }
        p.setHand(hand);
        p.setHandRank(h);

    }

    /**
     * Move Quads, trips, then pairs to the front of a list of n-cards.
     * Returns an array of n cards with groups of same-ranked cards placed to the left.
     * If there are multiple groups of cards (e.g. a full house), groups will be sorted in
     * descending order based on the size of the group. If there are two groups of the same
     * size (e.g. two pair), the group of higher ranked cards will be placed first.
     */
    private Card[] moveCardGroupsToFront(Card[] c){
        LinkedList<Card> cList = new LinkedList<>(Arrays.asList(c));
        Collections.reverse(cList);
        int quadsRank = 0;
        int tripsRank = 0;
        int pairRank = 0;
        boolean hasTrips = false;
        boolean hasPair = false;
        int movedCards = 0;

        for (int i = 0; i < cList.size();i++){
            int sameRankCards = 1;

            for(int j = i + 1; j < cList.size(); j++){
                if(cList.get(i).getRank() == cList.get(j).getRank()){
                    sameRankCards++;
                } else {
                    break;
                }
            }
            if(sameRankCards == 4){
                quadsRank = cList.get(i).getRank();
                movedCards += 4;
            } else if (sameRankCards == 3){
                tripsRank = cList.get(i).getRank();
                hasTrips = true;
                movedCards += 3;
            } else if (sameRankCards == 2){
                pairRank = cList.get(i).getRank();
                hasPair = true;
                movedCards += 2;
            }
            if(sameRankCards > 1) {
                if(hasTrips) {
                    for (int j = i; j < sameRankCards + i; j++) {
                        cList.addFirst(cList.get(j));
                        cList.remove(j + 1);
                    }
                }
            }
            i += sameRankCards - 1;
        }

        //sort high cards in descending order after groups
        for(int i = cList.size() - 1; i >= movedCards; i--){
            {
                cList.addLast(cList.get(i));
                cList.remove(i);
            }
        }
        Card[] cReturn = new Card[c.length];
        return cList.toArray(cReturn);

    }



    /**
     * Determine if a set of n cards is a flush
     */
    private boolean hasStraightFlush(Card[] a){
        LinkedList<Card> cList = new LinkedList<>(Arrays.asList(a));
        //remove duplicates
        for (int i = 0; i < cList.size() - 1;i++) {
            if (cList.get(i).getRank() - cList.get(i+1).getRank() != 1){
                cList.remove(i+1);
                i--;
            }
        }

        //if not in order
        if(cList.size() < 5){
            return false;
        }
        for (int i = 0; i < cList.size() - 1; i++) {

            if ((cList.get(i).getRank() - cList.get(i+1).getRank()) > 1 ||
                    cList.get(i).getSuit() != cList.get(i+1).getSuit()){
                return false;
            }
        }
        return true;
    }

    /**
     * Determine if a set of n cards is a flush
     */
    private boolean hasFlush(Card[] a){
        int clubCount = 0, spadeCount = 0, diamondCount = 0, heartCount = 0;

        for (int i = 0; i < a.length;i++){
            if(a[i].getSuit() == 'd'){
                diamondCount++;
            } else if(a[i].getSuit() == 'c'){
                clubCount++;
            }else if(a[i].getSuit() == 's'){
                spadeCount++;
            }else if(a[i].getSuit() == 'h'){
                heartCount++;
            }
        }

        return (clubCount >= 5 || spadeCount >= 5 || diamondCount >= 5 || heartCount >= 5);
    }

    /**
     * Determine if a set of n cards is a flush
     */
    private boolean hasStraight(Card[] a){
        LinkedList<Card> cList = new LinkedList<>(Arrays.asList(a));
        //remove duplicates
        for (int i = 0; i < cList.size() - 1;i++) {
            if (cList.get(i).getRank() - cList.get(i+1).getRank() == 0){
                cList.remove(i+1);
                i--;
            }
        }

        for (int i = 0; i < cList.size() - 1; i++) {

            if ((cList.get(i).getRank() - cList.get(i+1).getRank()) > 1){
                cList.remove(i);
                i--;
            }
        }
        if(cList.size() < 5){
            return false;
        }

        return true;
    }
}