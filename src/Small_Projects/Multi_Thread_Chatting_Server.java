package Small_Projects;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Multi_Thread_Chatting_Server {
    static final int Port = 10002;
    static Vector<ClientHandler> ar = new Vector<>();
    static int i = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Port);
        Socket client;
        while(true) {
            client = server.accept();
            System.out.println("New Client request received : " + client);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            System.out.println("Creating a new handler for this client....");
            ClientHandler mtch = new ClientHandler(client, "client "+i, dis, dos);
            Thread t = new Thread(mtch);
            System.out.println("Adding this client to active client list");
            ar.add(mtch);
            t.start();
            i++;
        }
    }
}
class ClientHandler implements Runnable {
    Socket Chatter;
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    boolean isloggedin;

    public ClientHandler(Socket Chatter, String name, DataInputStream dis, DataOutputStream dos) {
        this.Chatter = Chatter;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.isloggedin = true;
    }

    @Override
    public void run() {
        String rcv;
        try{
            while(true) {
                rcv = dis.readUTF();
                System.out.println(rcv);

                if(rcv.equals("logout")) {
                    isloggedin = false;
                    this.Chatter.close();
                    break;
                }
                StringTokenizer st = new StringTokenizer(rcv, "#");
                String MsgToSend = st.nextToken();
                String recipent = st.nextToken();

                for(ClientHandler mc : Multi_Thread_Chatting_Server.ar) {
                    if(mc.name.equals(recipent) && mc.isloggedin) {
                        mc.dos.writeUTF(this.name+" : "+MsgToSend);
                        break;
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        try {
            this.dis.close();
            this.dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
