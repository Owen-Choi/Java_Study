package Network.SocketProgramming;

import java.net.Socket;

public class Multi_Thread_Client {
    public static void main(String[] args) {
        try{
            Socket client = new Socket("Forbidden", 10002);
            System.out.println("Client 접속성공");
        }catch (Exception e){
        }
    }
}
