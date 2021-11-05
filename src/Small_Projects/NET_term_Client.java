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
                System.out.println("1. chatting \n" +
                        "2. invite \n" +
                        "3. show information ");
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
            String msgToRead;
            @Override
            public void run() {
                try {
                    while(true) {
                        msgToRead = dis.readUTF();
                        //showInfo 명령어에서 오류 발생.
                        /*if(st.countTokens() == 1) {
                            System.out.println(st.nextToken());
                        }
                        else
                            System.out.println(st.nextToken() + " : " + st.nextToken());*/
                    }

                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
            public void MSG_Processor() throws IOException{
                st = new StringTokenizer(msgToRead, "##");
                String Header = st.nextToken();
                if(Header.equals(TAG.INVITE_REPLY)) {
                    // 헤더가 INVITE_REPLY라는 것은 사용자가 초대할 다른 사용자의 정보를 넘겨주어야 한다는 뜻이다.
                    // 예외적으로 이때만 reader thread에서 입력을 주도록 하겠다.
                    System.out.println(st.nextToken());
                    dos.writeUTF(TAG.INVITE_REPLY.name() + br.readLine());
                }
                // Header가 위 조건문에 속하지 않는다는 것은 다른 user가 보낸 채팅이라는 뜻이다.
                else
                    System.out.println(Header + " : " + st.nextToken());
            }
        });
        System.out.println("Read/Write thread start");
        writer.start();
        reader.start();
    }

}
