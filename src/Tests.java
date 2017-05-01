import java.util.LinkedList;

/**
 * Created by Ben on 2/2/17.
 */
public class Tests {
    public static void main(String[] args){
        System.out.println("*********************************************");
        System.out.println("Begin Testing");
        System.out.println("*********************************************\n");

        //Create players
        LinkedList<Player> p = new LinkedList<>();

        p.add(new Player(new Card(8,'c'),new Card(8,'h'))); //quads
        p.add(new Player(new Card(7,'d'),new Card(7,'h'))); //full house
        p.add(new Player(new Card(11,'c'),new Card(5,'d'))); //straight
        p.add(new Player(new Card(6,'s'),new Card(7,'s'))); //straight flush
        p.add(new Player(new Card(14,'s'),new Card(2,'d'))); //pair
        p.add(new Player(new Card(10,'s'),new Card(4,'d'))); //two pair

        Card[] c = new Card[5]; //5 board cards
        c[0] = new Card(10,'s');
        c[1] = new Card(9,'s');
        c[2] = new Card(8,'s');
        c[3] = new Card(7,'c');
        c[4] = new Card(6,'d');

        //Create & print Table
        Table myTable = new Table(p);
        Dealer myDealer = new Dealer();
        myDealer.setBoard(c);
        myDealer.assignHandRanks(myTable);
        System.out.println(myDealer.boardToString() + "\n");
        System.out.println(myTable.toString());


        System.out.println("*********************************************");
        System.out.println("Done Testing");
        System.out.println("*********************************************");
    }

}
