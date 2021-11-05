package Small_Projects;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class NET_term_Server {
    static final int port = 10032;
    static int user_num = 0;
    static Vector<Client_Handler> user_list = new Vector<>();
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        Accept accept_thread = new Accept(serverSocket);
        Thread at = new Thread(accept_thread);
        at.start();
    }

    public void Create_Handler(Socket user, DataInputStream dis, DataOutputStream dos, String userID, boolean canPlay) throws IOException {
        Client_Handler client_handler = new Client_Handler(user, dis, dos, userID, true);
        user_list.add(client_handler);
        System.out.println("new user : " + client_handler.userID);
        Thread t = new Thread(client_handler);
        t.start();
    }
}

class Client_Handler implements Runnable{
    Socket user;
    final DataInputStream dis;
    final DataOutputStream dos;
    BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
    String userID;
    boolean CanPlay;
    StringBuilder sb = new StringBuilder();
    //유저 정보 필요한거 더 추가하기. 고유번호
    //로그인 후 서버가 만들어 줄 클라이언트의 정보에 대한 생성자.
    public Client_Handler(Socket user, DataInputStream dis, DataOutputStream dos, String userID, boolean CanPlay) throws IOException {
        this.user = user;
        this.dis = dis;
        this.dos = dos;
        this.userID = userID;
        this.CanPlay = CanPlay;
    }
    @Override
    public void run() {
        String msg;
        try {
            while (true) {
                msg = dis.readUTF();
                if(msg.equals("showInfo")){
                    for (Client_Handler User : NET_term_Server.user_list) {
                        sb.append(User.userID).append('\n');
                    }
                    this.dos.writeUTF(sb.toString());
                    sb.setLength(0);
                }
                else {
                    System.out.println(this.userID + " : " + msg);
                    // 일단은 입력받은 메세지 모두에게 전달, 여기서 귓속말, 혹은 게임초대 등의 기능을 넣고싶으면 프로토콜을 정의해야한다.
                    for (Client_Handler User : NET_term_Server.user_list) {
                        User.dos.writeUTF(this.userID + "#" + msg);
                    }
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        try {
            dis.close();
            dos.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
