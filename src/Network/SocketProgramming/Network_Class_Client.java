/*
package Network.SocketProgramming;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Network_Class_Client {
    public static void main(String[] args) throws IOException {
        String sentence;
        String MS;
        String serverIP = "127.0.0.1";
        int nport = 6789;

        Socket clientSocket = new Socket(serverIP,nport);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        sentence = new Date().toString();
        outToServer.write(sentence + '\n');
        MS = inFromServer.readLine();
        System.out.println("From Server : " + MS);
    }
}
*/