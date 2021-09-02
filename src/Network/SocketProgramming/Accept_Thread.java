package Network.SocketProgramming;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accept_Thread implements Runnable {
    ServerSocket serverSocket;
    Socket client;

    public Accept_Thread(ServerSocket server){
        serverSocket = server;
    }

    @Override
    public void run() {
        // while(true)는 run 함수와는 별개로 계속해서 돌릴려면 필요한 것 같다.
        while(true) {
            try {
                client = serverSocket.accept();
                System.out.println("Client Connected : " + client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
