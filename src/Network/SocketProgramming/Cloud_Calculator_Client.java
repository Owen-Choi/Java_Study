package Network.SocketProgramming;

import java.io.*;
import java.net.Socket;

public class Cloud_Calculator_Client {
    public static void main(String[] args) throws Exception{
        String IPAddress = "127.0.0.1";
        int Pnum = 10002;
        Socket client = new Socket(IPAddress, Pnum);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream FromServer = new DataInputStream(client.getInputStream());
        DataOutputStream ToServer = new DataOutputStream(client.getOutputStream());
        /*String temp = br.readLine();
        System.out.println(temp);*/
        System.out.println(":: Welcome to Cloud Calculator! ::");
        System.out.println("Please Enter Command with the format : ");
        System.out.println("\t (Arithmetic Expression) Integer1 Integer2");
        System.out.println("\t Possible Arithmetic Expression : ADD, MINUS, DIV, MOD \n");
        while(true) {
            try {
                ToServer.writeUTF(br.readLine());
                System.out.println(FromServer.readUTF());
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
