package Small_Projects;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accept_Thread implements Runnable{
    ServerSocket server;
    Socket client;
    public Accept_Thread(ServerSocket serverSocket) {
        server = serverSocket;
    }
    @Override
    public void run() {
        while(true) {
            try {
                client = server.accept();
                System.out.println("client connected :: " + client);

            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
