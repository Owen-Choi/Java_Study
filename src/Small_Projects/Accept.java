package Small_Projects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Accept implements Runnable{
    ServerSocket serverSocket;
    Socket client;
    DataInputStream dis;
    DataOutputStream dos;
    String name;
    NET_term_Server nts = new NET_term_Server();
    public Accept(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        while(true) {
            try {
                client = serverSocket.accept();
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                // 지금은 선착순으로 숫자를 부여했지만 후에는 데이터베이스에서 정보 가져와서 넣어주기.
                name = "user " + ++NET_term_Server.user_num;
                nts.Create_Handler(client, dis, dos, name, true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
