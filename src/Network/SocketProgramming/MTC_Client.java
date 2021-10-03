package Network.SocketProgramming;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MTC_Client {
    static int Port = 10005;
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket client = new Socket(ip, Port);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            Thread SendMSG = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            dos.writeUTF(br.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Thread rcvMSG = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            System.out.println(dis.readUTF());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            SendMSG.start();
            rcvMSG.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
