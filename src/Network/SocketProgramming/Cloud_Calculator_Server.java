package Network.SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Cloud_Calculator_Server {
    static StringTokenizer st;
    public static void main(String[] args) throws Exception{
        int Pnum = 10002;
        ServerSocket listener = new ServerSocket(Pnum);
        System.out.println("Waiting for client : ");
        Socket ConnectedSocket = listener.accept();
        System.out.println("Client connected : " + ConnectedSocket);
        DataInputStream FromClient = new DataInputStream(ConnectedSocket.getInputStream());
        DataOutputStream ToClient = new DataOutputStream(ConnectedSocket.getOutputStream());
        while(true) {
            try {
                String temp = FromClient.readUTF();
                InputProcessor(temp, ToClient);
            } catch (IOException e) {
                System.out.println("There's no more client here. terminating program");
                System.exit(0);
            }
        }
    }
    static void InputProcessor(String Input, DataOutputStream ToClient) throws IOException{
        st = new StringTokenizer(Input, " ");
        // If the token number is less than 3, then the command Parameter is out of format ::
        if(st.countTokens() != 3) {
            ToClient.writeUTF(st.countTokens() > 3 ? "Incorrect : Too many arguments" : "Incorrect : argument is not enough");
            return;
        }
        long First, Second;
        long result;
        switch (st.nextToken().toUpperCase()) {
            case "ADD" -> {
                try {
                    First = Long.parseLong(st.nextToken());
                    Second = Long.parseLong(st.nextToken());
                    result = First + Second;
                    ToClient.writeUTF(Long.toString(result));
                }catch(Exception e) {
                    ToClient.writeUTF(e.toString());
                }
            }
            case "MINUS" -> {
                try {
                    First = Long.parseLong(st.nextToken());
                    Second = Long.parseLong(st.nextToken());
                    result = First - Second;
                    ToClient.writeUTF(Long.toString(result));
                }catch (Exception e) {
                    ToClient.writeUTF(e.toString());
                }
            }
            case "MULTIPLE" -> {
                try {
                    First = Long.parseLong(st.nextToken());
                    Second = Long.parseLong(st.nextToken());
                    result = (long)First * Second;
                    ToClient.writeUTF(Long.toString(result));
                } catch (Exception e) {
                    ToClient.writeUTF(e.toString());
                    return;
                }
            }
            case "DIV" -> {
                First = Long.parseLong(st.nextToken());
                Second = Long.parseLong(st.nextToken());
                if (Second == 0) {
                    ToClient.writeUTF("Incorrect : Divide by Zero Exception");
                }
                else
                    ToClient.writeUTF(Long.toString(First / Second));
            }
            case "EXIT" -> {
                ToClient.writeUTF("Program is now terminating....");
                System.exit(0);
            }
            default -> ToClient.writeUTF("Incorrect : wrong command");
        }
    }
}
