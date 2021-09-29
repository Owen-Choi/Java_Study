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
                System.out.println("Error");
            }
        }
    }
    static void InputProcessor(String Input, DataOutputStream ToClient) throws IOException{
        st = new StringTokenizer(Input, " ");
        // If the token number is less than 3, then the command Parameter is out of format ::
        if(st.countTokens() != 3) {
            ToClient.writeBytes(st.countTokens() > 3 ? "Too many arguments" : "argument is not enough");
            return;
        }
        int First, Second;
        switch (st.nextToken()) {
            case "ADD" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (Math.abs(First + Second) < Integer.MAX_VALUE)
                    ToClient.writeUTF(Integer.toString(First + Second));
                else
                    ToClient.writeUTF("Arithmetic result is out of integer's range");
            }
            case "MINUS" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (Math.abs(First - Second) < Integer.MAX_VALUE)
                    ToClient.writeUTF(Integer.toString(First - Second));
                else
                    ToClient.writeUTF("Arithmetic result is out of integer's range");
            }
            case "MULTIPLE" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (Math.abs(First * Second) < Integer.MAX_VALUE)
                    ToClient.writeUTF(Integer.toString(First * Second));
                else
                    ToClient.writeUTF("Arithmetic result is out of integer's range");
            }
            case "DIV" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (/*First == 0 || */Second == 0)
                    ToClient.writeUTF("Divide by Zero Exception");
                else
                    ToClient.writeUTF(Integer.toString(First / Second));
            }
            default -> ToClient.writeUTF("Command Format Exception : Enter only Uppercase");
        }
    }
}
