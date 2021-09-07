package Network.SocketProgramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client_Donghuk {
    Socket client;
    DataInputStream DIS;
    DataOutputStream DOS;
    void Connect() {
        try {
            client = new Socket("172.30.1.3", 10002);
            // 클라이언트 생성 및 연결 성공
            System.out.println("Client Connected");
        }catch(IOException e){
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
    public Client_Donghuk(){
        Connect();
        StreamSetting();
        DataSend("잘 받았나요?");
        Datarecv();
    }
    public static void main(String[] args) {
        new Client_Donghuk();
    }
}