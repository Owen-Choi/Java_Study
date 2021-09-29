package Network.SocketProgramming;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

public class Network_Class_Client {
    public static void main(String[] args) throws IOException {
        String sentence;
        String MS;
        String serverIP = "127.0.0.1";
        int nport = 6789;

        Socket clientSocket = new Socket(serverIP,nport);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = new Date().toString();
        outToServer.writeBytes(sentence + '\n');
        MS = inFromServer.readLine();
        System.out.println("From Server : " + MS);
    }
}
