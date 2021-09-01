package Network.SocketProgramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public void Connect() {
        try {
            socket = new Socket("192.168.8.8", 10002);
            System.out.println("Client : Connect complete");
        } catch(IOException e) {
        }
    }

    public String Datarecv() {
        try {
            return dataInputStream.readUTF();
        }catch (IOException e){
        }
        return null;
    }

    public void dataSend(String data) {
        try {
            dataOutputStream.writeUTF(data);
        }catch (IOException e){
        }
    }

    public void StreamSetting() {
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
        }
    }

    public Client() {
        Connect();  //접속을 먼저 함.
        StreamSetting();    //그 후 스트림 세팅.
        dataSend("I'm Comming!");
        System.out.println(Datarecv());
        // 이 코드에서는 클라이언트 소켓의 close까지 server에서 수행하므로 Close함수는 따로 선언하지 않겠음
    }

    public static void main(String[] args) {
        Client MyFirstClient = new Client();
    }
}

