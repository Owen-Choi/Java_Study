package Network.SocketProgramming;

import java.io.*;
import java.net.Socket;

public class Cloud_Calculator_Client {
    static final String IPA = "127.0.0.1";
    static final int PortNum = 6405;
    public static void main(String[] args) throws IOException {
        Socket client = new Socket(IPA, PortNum);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader FromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        DataOutputStream ToServer = new DataOutputStream(client.getOutputStream());
        System.out.println(":: Welcome to Cloud Calculator! ::");
        System.out.println("Please Enter Command with the format : ");
        System.out.println("\t (Arithmetic Expression) Integer1 Integer2");
        System.out.println("\t Possible Arithmetic Expression : ADD, MINUS, DIV, MOD \n");
        while(true) {
            System.out.print("Please Enter the command : ");
            ToServer.writeBytes(br.readLine());
            System.out.println(FromServer.readLine());
        }
    }
}
