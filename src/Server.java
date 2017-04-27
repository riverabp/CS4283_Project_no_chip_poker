/**
 * Created by Ben on 4/16/17.
 */
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){

        String host = null;
        int port = 0;
        OutputStream outputStream = null;
        Socket s = null;

        // Get host name and port number from args
        //test
        if(args.length!=2) {
            System.out.println("Please specify the hostname and the port number as command line args.");
            System.exit(1);
        } else {
            host = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                System.out.println("Invalid port number: "+args[1]);
                System.exit(1);
            }
        }

        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (listening) {
                Socket ss = serverSocket.accept();
                PrintWriter out = new PrintWriter(ss.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                ss.getInputStream()));
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        finally {
            if(outputStream !=null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {}
            }

            // Make sure that the socket is closed
            if(s != null && !s.isClosed()) {
                try {
                    s.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
