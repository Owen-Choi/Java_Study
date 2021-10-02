package Small_Projects;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Multi_Thread_Chatting_Client {
    final static int ServerPort = 10002;
    public static void main(String[] args) throws IOException, UnknownHostException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ip = InetAddress.getByName("localhost");
        Socket client = new Socket(ip, ServerPort);
        DataInputStream dis = new DataInputStream(client.getInputStream());
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String msg = br.readLine();
                        dos.writeUTF(msg);
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                while(true) {
                    try {
                        msg = dis.readUTF();
                        System.out.println(msg);
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendMessage.start();
        readMessage.start();

    }
}
