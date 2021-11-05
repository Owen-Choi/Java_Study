package Small_Projects;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NET_term_Client {
    static final int serverPort = 10032;
    public static void main(String[] args) throws IOException, UnknownHostException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ip = InetAddress.getByName("localhost");
        Socket user = new Socket(ip, serverPort);
        DataInputStream dis = new DataInputStream(user.getInputStream());
        DataOutputStream dos = new DataOutputStream(user.getOutputStream());
        Thread writer = new Thread(new Runnable() {
            String msgToSend;
            @Override
            public void run() {
                try {
                    msgToSend = br.readLine();
                    dos.writeUTF(msgToSend);
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread reader = new Thread(new Runnable() {
            String msgToRead;
            @Override
            public void run() {
                try {
                    msgToRead = dis.readUTF();
                    System.out.println(msgToRead);
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.start();
        reader.start();
    }
}
