/**
 * Created by Ben on 4/16/17.
 */
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){

        System.out.println("--- Server Started ---");
        int port = 4444;
        OutputStream outputStream = null;
        Socket s = null;


        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (listening) {
                new ServerThread(serverSocket.accept()).start();
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
