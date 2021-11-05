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
        // writer thread
        Thread writer = new Thread(new Runnable() {
            String HeadTag = null;
            @Override
            public void run() {
                try {
                    while(!ChangeMode());
                    while(true) {
                        String msgToSend = br.readLine();
                        dos.writeUTF(HeadTag + "##" + msgToSend);
                    }
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
            public boolean ChangeMode() throws IOException{
                System.out.println("please choose the mode number : ");
                System.out.println("1. chatting" +
                        "2. invite" +
                        "3. show information");
                int tempnumber = Integer.parseInt(br.readLine());
                switch (tempnumber) {
                    case 1 :
                        HeadTag = TAG.CHAT.name();
                        break;
                    case 2 :
                        HeadTag = TAG.INVITE.name();
                        break;
                    case 3 :
                        HeadTag = TAG.SHOW_INFO.name();
                        break;
                    default:
                        System.out.println("invalid value. try again");
                        return false;
                }
                return true;
            }
        });
        // reader thread
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String msgToRead = dis.readUTF();
                        //showInfo 명령어에서 오류 발생.
                        st = new StringTokenizer(msgToRead, "##");
                        if(st.countTokens() == 1) {
                            System.out.println(st.nextToken());
                        }
                        else
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
