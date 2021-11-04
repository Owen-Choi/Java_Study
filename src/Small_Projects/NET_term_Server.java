package Small_Projects;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class NET_term_Server {
    static final int port = 10032;
    static Vector<Client_Handler> user_list = new Vector<>();
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);

    }

    class Client_Handler implements Runnable{
        Socket user;
        final DataInputStream dis;
        final DataOutputStream dos;
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        String userID;
        boolean CanPlay;
        //유저 정보 필요한거 더 추가하기. 고유번호
        //로그인 후 서버가 만들어 줄 클라이언트의 정보에 대한 생성자.
        public Client_Handler(Socket user, DataInputStream dis, DataOutputStream dos, String userID, boolean CanPlay) throws IOException {
            this.user = user;
            this.dis = dis;
            this.dos = dos;
            this.userID = userID;
            this.CanPlay = CanPlay;
        }
        String msg;
        @Override
        public void run() {
            while(true) {
                try {
                    msg = dis.readUTF();
                    System.out.println(msg);
                    // 일단은 입력받은 메세지 모두에게 전달, 여기서 귓속말, 혹은 게임초대 등의 기능을 넣고싶으면 프로토콜을 정의해야한다.
                    for (Client_Handler User : user_list) {
                        User.dos.writeUTF(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Accept_thread implements Runnable{
        ServerSocket serverSocket;
        Socket client;
        public Accept_thread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }
        @Override
        public void run() {
            while(true) {
                try {
                    client = serverSocket.accept();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
