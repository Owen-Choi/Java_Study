package Network.SocketProgramming;

import java.io.*;
import java.net.*;

public class TCPServer
{
    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket;
        String clientSentence;
        String capitalizedSentence;
        int nPort;

        nPort = 6789;
        welcomeSocket = new ServerSocket(nPort);
        System.out.println("Server start.. (port#=" + nPort + ")\n");

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream  outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("FROM CLIENT: " + clientSentence );

            capitalizedSentence = clientSentence.toUpperCase() + '\n';

            outToClient.writeBytes(capitalizedSentence);
        }
    }
}

