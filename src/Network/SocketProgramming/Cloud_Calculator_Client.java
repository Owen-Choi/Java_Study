package Network.SocketProgramming;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Cloud_Calculator_Client {
    static StringTokenizer st;
    static String IPAddress;
    static int Pnum;
    public static void main(String[] args) throws Exception{
        try {
            File file = new File("C:\\Users\\oldst\\Desktop\\201835539 최철웅\\server_info.dat");
            byte[] binary = new byte[(int)file.length()];
            InputStream is = new FileInputStream(file);
            is.read(binary);
            is.close();
            st = new StringTokenizer(new String(binary));
            IPAddress = st.nextToken();
            Pnum = Integer.parseInt(st.nextToken());
        }catch(Exception e) {
            System.out.println(e.toString());
        }
        // pre-defined "myself" IP address ::
        // 이제 이 변수 2개를 파일에서 읽어오는 작업 필요함 ::
        // connect to server by using IPAddress and Pnum ::
        Socket client = new Socket(IPAddress, Pnum);
        // Input stream to get input from user ::
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Input stream to get input from server ::
        DataInputStream FromServer = new DataInputStream(client.getInputStream());
        // Output stream to give output to server ::
        DataOutputStream ToServer = new DataOutputStream(client.getOutputStream());
        // brief guide about cloud calculator's protocol : command ::
        System.out.println(":: Welcome to Cloud Calculator! ::");
        System.out.println("Please Enter Command with the format : ");
        System.out.println("\t (Arithmetic Expression) Integer1 Integer2");
        System.out.println("\t Possible Arithmetic Expression : ADD, MINUS, DIV, MULTIPLE \n");
        System.out.println("\t If you want to terminate calculator, please enter 'Exit' ");
        String Command;
        while(true) {
            System.out.print("Please Enter the Command : ");
                try {
                    Command = br.readLine();
                    ToServer.writeUTF(Command);
                    System.out.println(FromServer.readUTF());
                    st = new StringTokenizer(Command," ");
                    if(st.nextToken().toUpperCase().equals("EXIT")) {
                        System.exit(0);
                    }
                } catch (IOException e) {
                    System.out.println("Error from Client");
                }
        }
    }
}
