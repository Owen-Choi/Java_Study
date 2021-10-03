package Network.SocketProgramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class MTC_Server {
    static final int Port = 10005;
    static Vector<ClientHandler> vector = new Vector<>();
    public static void main(String[] args) {
        Socket client;
        int guest_number = 1;
        try {
            ServerSocket serverSocket = new ServerSocket(Port);
            while(true) {
                System.out.println("Waiting for new Chatter :: ");
                client = serverSocket.accept();
                System.out.println("Hello Chatter" + guest_number + "!");
                DataInputStream dis = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                ClientHandler guest = new ClientHandler(client, dis, dos, "Chatter" + guest_number);
                Thread thread = new Thread(guest);
                thread.start();
                System.out.println("Adding chatter" + guest_number + " to chatting room");
                vector.add(guest);
                guest_number++;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
class ClientHandler implements Runnable{
    DataInputStream dis;
    DataOutputStream dos;
    Socket chatter;
    String name;
    boolean loggedin;
    public ClientHandler(Socket chatter, DataInputStream dis, DataOutputStream dos, String name) {
        this.chatter = chatter;
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        loggedin = true;
    }
    @Override
    public void run() {
        String rcv;
        while(true) {
            try {
                rcv = dis.readUTF();
                if(rcv.equals("logout")) {
                    System.out.println(name + " is logged out");
                    loggedin = false;
                    Close(dis, dos, chatter);
                    break;
                }
                StringTokenizer st;
                String header, msg;
                st = new StringTokenizer(rcv, "#");
                header = st.nextToken();
                msg = st.nextToken();
                for (ClientHandler ch: MTC_Server.vector) {
                    if(ch.name.equals(header) && ch.loggedin) {
                        ch.dos.writeUTF(this.name + " : " + msg);
                        break;
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    static void Close(DataInputStream dis, DataOutputStream dos, Socket Chatter) throws Exception{
        dis.close();
        dos.close();
        Chatter.close();
    }
}
