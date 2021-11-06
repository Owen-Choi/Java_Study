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
        Writer writer = new Writer(user, dis, dos);
        Reader reader = new Reader(user, dis, dos, writer);
        Thread writer_thread = new Thread(writer);
        Thread reader_thread = new Thread(reader);
        writer_thread.start();
        reader_thread.start();
        System.out.println("대기실에 입장되셨습니다.");
    }
}

class Writer implements Runnable {
    Socket userOwn;
    DataOutputStream dos;
    DataInputStream dis;
    String HeadTag = null;
    BufferedReader br;
    // 소켓은 받을 필요 없나?
    public Writer(Socket userOwn, DataInputStream dis, DataOutputStream dos) {
        this.userOwn = userOwn;
        this.dis = dis;
        this.dos = dos;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            while(!ChangeMode());
            while(true) {
                String msgToSend = br.readLine();
                dos.writeUTF(HeadTag + "##" + msgToSend);
                System.out.println(HeadTag + "##" + msgToSend);
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
                dos.writeUTF(TAG.INVITE.name() + "##"+"inviting user");
                //HeadTag = TAG.INVITE_REPLY.name();
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
    public void HeadTag_Setter(String newHeadTag) {
        HeadTag = newHeadTag;
    }
}

class Reader implements Runnable {
    Socket userOwn;
    DataOutputStream dos;
    DataInputStream dis;
    StringTokenizer st;
    Writer writer;
    public Reader(Socket userOwn, DataInputStream dis, DataOutputStream dos, Writer writer) {
        this.userOwn = userOwn;
        this.dis = dis;
        this.dos = dos;
        this.writer = writer;
    }
    @Override
    public void run() {
        String msgToRead;
        try {
            while(true) {
                msgToRead = dis.readUTF();
                MSG_Processor(msgToRead);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void MSG_Processor(String msgToRead) throws IOException{
        st = new StringTokenizer(msgToRead, "##");
        String Header = st.nextToken();
        if(Header.equals(TAG.INVITE_REPLY.name())) {
            // 헤더가 INVITE_REPLY라는 것은 사용자가 초대할 다른 사용자의 정보를 넘겨주어야 한다는 뜻이다.
            System.out.println(st.nextToken());
            // Header의 정보가 INVITE_REPLY가 아닌 INVITE로 들어간다.
            writer.HeadTag_Setter(TAG.INVITE_REPLY.name());
        }
        else if(Header.equals(TAG.INVITE_REQUEST.name())) {
            System.out.println(st.nextToken());
            System.out.println("초대에 응하실려면 1, 거절하실려면 2를 입력해주세요.");
            //INVITE_PD 뒤에 1이 붙으면 수락, 2가 붙으면 거절의 의미이다.
            //HeadTag가 INVITE_PD로 바뀌지 않는다. 원인이 뭐지?
            writer.HeadTag_Setter(TAG.INVITE_PD.name());
        }
        else if(Header.equals(TAG.INVITE_PERMIT.name())) {
            System.out.println(st.nextToken());
            //게임 진행에 대비할 것 있으면 하기
        }
        else if(Header.equals(TAG.INVITE_DENY.name())) {
            System.out.println(st.nextToken());
        }
        // Header가 위 조건문에 속하지 않는다는 것은 다른 user가 보낸 채팅이라는 뜻이다.
        else
            System.out.println(Header + " : " + st.nextToken());
    }
}