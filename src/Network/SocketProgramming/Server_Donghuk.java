package Network.SocketProgramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Donghuk {
    ServerSocket server;
    Socket client;
    DataInputStream DIS;
    DataOutputStream DOS;
    void Connect() {
        try {
            server = new ServerSocket(10002);
            // 생성 바인드, 리슨
            client = server.accept();
            // 어셉트
        } catch (Exception e) {
        }
    }

    void StreamSetting() {
        try {
            DIS = new DataInputStream(client.getInputStream());
            DOS = new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
        }
    }

    void Datarecv() {
        try{
            System.out.println(DIS.readUTF());
        }catch(IOException e){
        }
    }

    void DataSend(String data) {
        try{
            DOS.writeUTF(data);
        }catch(IOException e){
        }
    }

    void CloseAll() {
        try {
            server.close();
            client.close();
            DIS.close();
            DOS.close();
        }catch(IOException e){
        }
    }
    // 서버소켓 생성
    // 클라이언트 소켓 받아옴
    // 데이터 입출력

    public Server_Donghuk() {
        Connect();
        StreamSetting();
        Datarecv();
        DataSend("잘 받았습니다");
        CloseAll();
    }
    public static void main(String[] args) {
        // 함수 실행
        new Server_Donghuk();
    }
}
