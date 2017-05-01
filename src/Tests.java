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

        //p.add(new Player(new Card(8,'c'),new Card(8,'h'))); //quads
        //p.add(new Player(new Card(7,'d'),new Card(7,'h'))); //full house
        //p.add(new Player(new Card(11,'c'),new Card(5,'d'))); //straight
        //p.add(new Player(new Card(6,'s'),new Card(7,'s'))); //straight flush
        //p.add(new Player(new Card(14,'s'),new Card(2,'d'))); //pair
        p.add(new Player(new Card(5,'c'),new Card(8,'h'))); //two pair

        Card[] c = new Card[5]; //5 board cards
        //Board: Ah 9d 7d 6c 7h
        //Hand: 5c 8h
        c[0] = new Card(14,'h');
        c[1] = new Card(9,'d');
        c[2] = new Card(7,'d');
        c[3] = new Card(6,'c');
        c[4] = new Card(7,'h');

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
