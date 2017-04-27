import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;
            ServerProtocol sp = new ServerProtocol();
            outputLine = sp.processInput("start");
            out.println(outputLine);

            while (true) {
                inputLine = in.readLine();
                outputLine = sp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Game Over"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}