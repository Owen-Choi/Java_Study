package Network.SocketProgramming;

import java.io.IOException;
import java.net.ServerSocket;

public class Multi_Thread_Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(10002);
            // 이 문법들은 외우자. 익숙해질 필요가 있다.
            Accept_Thread AT = new Accept_Thread(serverSocket);
            new Thread(AT).start();
            //만약 Accept Thread 없이 Socket client = serverSocket.accept() 코드였다면
            //블록킹에 의해 아래 문자열이 출력되지 않을 것이다.
            System.out.println("There's no blocking here");
        }catch (Exception e){
        }
    }
}
