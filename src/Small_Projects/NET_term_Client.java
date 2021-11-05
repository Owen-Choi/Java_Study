package Small_Projects;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class NET_term_Client {
    static final int serverPort = 10032;
    static StringTokenizer st;
    public static void main(String[] args) throws IOException, UnknownHostException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ip = InetAddress.getByName("localhost");
        Socket user = new Socket(ip, serverPort);
        DataInputStream dis = new DataInputStream(user.getInputStream());
        DataOutputStream dos = new DataOutputStream(user.getOutputStream());
        Thread writer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String msgToSend = br.readLine();
                        dos.writeUTF(msgToSend);
                    }
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String msgToRead = dis.readUTF();
                        //showInfo 명령어에서 오류 발생.
                        st = new StringTokenizer(msgToRead, "#");
                        System.out.println(st.nextToken() + " : " + st.nextToken());
                    }
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Read/Write thread start");
        writer.start();
        reader.start();
    }
}
