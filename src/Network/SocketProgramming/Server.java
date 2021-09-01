package Network.SocketProgramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket server;
    Socket client;      //서버 소켓에서 어셉트할(정보를 저장할) 소켓이 필요하기 때문에 소켓도 하나 선언
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public void Setting() {
        try {
            server = new ServerSocket(10002);       //이 한 줄이 생성과 바인드 과정.
            client = server.accept();                    //이 과정이 어셉트
            // 어셉트 이후에는 소켓이 접속이 완료 되었다고 생각해도 됨.
            System.out.println("Client Socket Connected");

            dataInputStream = new DataInputStream(client.getInputStream()); //클라이언트로부터 inputstream을 받아옴.
            dataOutputStream = new DataOutputStream(client.getOutputStream());  // 클라이언트로부터 outputstream을 받아옴.
            // 이 두개의 stream을 통해서 client와 소통하게됨.
            String FromClient = dataInputStream.readUTF();
            System.out.println(FromClient);
            // client로부터 읽어온 문장
            dataOutputStream.writeUTF("Thank you for sending messages");
            // client에게 보내는 문장
        } catch (IOException e) {
        }
    }
    // 서버를 모두 사용했으면 닫아줘야 한다. 닫는 기능을 하는 함수
    public void CloseAll() {
        try {
            server.close();
            client.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e){
        }
    }

    public Server() {
        Setting();
        CloseAll();
    }

    public static void main(String[] args) {
        Server MyFirstServer = new Server();
    }
}

