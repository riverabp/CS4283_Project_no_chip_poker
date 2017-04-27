/**
 * Created by Ben on 4/26/17.
 */
import java.io.*;
import java.net.*;
import java.util.stream.*;

public class PokerClient {
    public static void main(String[] args) throws IOException {

        String hostName = "ec2-34-208-123-243.us-west-2.compute.amazonaws.com";
        int portNumber = 4444;

        try {

            Socket s = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(s.getInputStream()));

            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromUser;
            String fromServer;
            String transmissionComplete = "END";
            fromServer = in.readLine();

            while (fromServer != null) {
                System.out.println(fromServer);
                if (fromServer.equalsIgnoreCase("Game Over")) {
                    break;
                } else if(fromServer.equalsIgnoreCase("END")){
                    fromUser = stdIn.readLine();
                    if (fromUser != null) {
                        System.out.println("Player: " + fromUser);
                        out.println(fromUser);
                    }
                } else {
                    fromServer = in.readLine();
                }

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
