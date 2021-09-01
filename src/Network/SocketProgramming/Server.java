package Network.SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    ServerSocket server;
    Socket client;      //서버 소켓에서 어셉트할(정보를 저장할) 소켓이 필요하기 때문에 소켓도 하나 선언
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public void Setting() {
        try {
            server = new ServerSocket(10002);       //이 한 줄이 생성과 바인드 과정.
            client = server.accept();                    //이 과정이 어셉트
            System.out.println("Welcome, client");
        } catch (IOException e) {
        }
    }

    public void StreamSetting() {
        try {
            dataInputStream = new DataInputStream(client.getInputStream());
            dataOutputStream = new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
        }
    }

    public void Datarecv() {
        new Thread(new Runnable() {
            boolean keep = true;
            String datainput;
            @Override
            public void run() {
                try {
                    while(keep){
                        datainput = dataInputStream.readUTF();
                        if(datainput.equals("/exit"))
                            keep = false;
                        else
                            System.out.println(datainput);
                    }
                }catch (Exception e){
                }
            }
        }).start();
    }

    public void dataSend() {
        new Thread(new Runnable() {
            Scanner sc = new Scanner(System.in);
            boolean keep = true;
            String dataOutput;
            @Override
            public void run() {
                try {
                    while(keep){
                        dataOutput = sc.nextLine();
                        if(dataOutput.equals("/exit"))
                            keep = false;
                        else
                            dataOutputStream.writeUTF(dataOutput);
                    }
                }catch(Exception e){
                }
            }
        }).start();
    }

    // 서버를 모두 사용했으면 닫아줘야 한다. 닫는 기능을 하는 함수
   /*public void CloseAll() {
        try {
            server.close();
            client.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e){
        }
    }*/

    public Server() {
        Setting();
        StreamSetting();
        Datarecv();
        dataSend();
        //CloseAll();
    }

    public static void main(String[] args) {
        new Server();
    }
}

