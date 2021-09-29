/*
package Network.SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Network_Class_Server {
    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket;
        String clientSentence;
        String CS;
        int nport;              //port num ::
        nport = 6789;
        welcomeSocket = new ServerSocket(nport);
        System.out.println("Server start....(port#= " + nport + ")\n");
        while(true) {
            Socket connectionSocket = welcomeSocket.accept();       //bind ::
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            clientSentence = inFromClient.readLine();
            System.out.println("From Client, The Current Date is : " + clientSentence);
            String temp = "Thanks!";
            outToClient.write(temp);
        }

    }
}
*/

