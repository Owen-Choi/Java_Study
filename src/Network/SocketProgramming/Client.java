package Network.SocketProgramming;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public void Connect() {
        try {
            // ip값이 바뀌면 연결이 안되니 주의할 것
            socket = new Socket("192.168.219.102", 10002);
            System.out.println("Client : Connect complete");
        } catch(IOException e) {
        }
    }

    public void Datarecv() {
            new Thread(new Runnable() {
                String Datainput;
                boolean keep = true;
                @Override
                public void run() {
                    while(keep){
                        try{
                            Datainput = dataInputStream.readUTF();
                            if(Datainput.equals("/exit"))
                                keep = false;
                            else
                                System.out.println(Datainput);
                        }catch(Exception e){
                        }
                    }
                }
            }).start();
    }

    public void dataSend() {
        new Thread(new Runnable() {
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Scanner sc = new Scanner(System.in);
            String Dataoutput;
            boolean keep = true;
            @Override
            public void run() {
                try{
                    while(keep){
                        Dataoutput = sc.nextLine();
                        if(Dataoutput.equals("/exit"))
                            keep = false;
                        else
                            dataOutputStream.writeUTF(Dataoutput);
                    }
                }catch(Exception e){
                }
            }
        }).start();
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
        dataSend();
        Datarecv();
        // 이 코드에서는 클라이언트 소켓의 close까지 server에서 수행하므로 Close함수는 따로 선언하지 않겠음
    }

    public static void main(String[] args) {
        new Client();
    }
}

