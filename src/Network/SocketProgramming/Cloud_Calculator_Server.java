package Network.SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Cloud_Calculator_Server{
    static final int PortNum = 6405;
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PortNum);
        Socket client = serverSocket.accept();
        BufferedReader FromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        DataOutputStream ToClient = new DataOutputStream(client.getOutputStream());
        String Input;
        while(true) {
            Input = FromClient.readLine();
            System.out.println("Calculation Processing...");
            InputProcessor(Input, ToClient);
            System.out.println("Process End ::");
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
                    ToClient.writeBytes(Integer.toString(First + Second));
                else
                    ToClient.writeBytes("Arithmetic result is out of integer's range");
            }
            case "MINUS" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (Math.abs(First - Second) < Integer.MAX_VALUE)
                    ToClient.writeBytes(Integer.toString(First - Second));
                else
                    ToClient.writeBytes("Arithmetic result is out of integer's range");
            }
            case "MULTIPLE" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (Math.abs(First * Second) < Integer.MAX_VALUE)
                    ToClient.writeBytes(Integer.toString(First * Second));
                else
                    ToClient.writeBytes("Arithmetic result is out of integer's range");
            }
            case "DIV" -> {
                First = Integer.parseInt(st.nextToken());
                Second = Integer.parseInt(st.nextToken());
                if (/*First == 0 || */Second == 0)
                    ToClient.writeBytes("Divide by Zero Exception");
                else
                    ToClient.writeBytes(Integer.toString(First / Second));
            }
            default -> ToClient.writeBytes("Command Format Exception : Enter only Uppercase");
        }
    }

}
