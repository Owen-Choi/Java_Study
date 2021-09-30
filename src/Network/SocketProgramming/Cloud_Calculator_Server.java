package Network.SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Cloud_Calculator_Server {
    static StringTokenizer st;                              //For indentify command came from client
    public static void main(String[] args) throws Exception{
        int Pnum = 10002;
        ServerSocket listener = new ServerSocket(Pnum);
        System.out.println("Waiting for client : ");
        // binding client socket ::
        Socket ConnectedSocket = listener.accept();
        System.out.println("Client connected : " + ConnectedSocket);
        // Input stream to get input from client ::
        DataInputStream FromClient = new DataInputStream(ConnectedSocket.getInputStream());
        // Output stream to give output to client ::
        DataOutputStream ToClient = new DataOutputStream(ConnectedSocket.getOutputStream());
        while(true) {
            try {
                // Read command from client ::
                String temp = FromClient.readUTF();
                // return result or error ::
                InputProcessor(temp, ToClient);
                // if there isn't client, exception may occur ::
            } catch (IOException e) {
                System.out.println("There's no more client here. terminating program");
                System.exit(0);
            }
        }
    }
    static void InputProcessor(String Input, DataOutputStream ToClient) throws IOException{
        st = new StringTokenizer(Input, " ");
        String CommandHeader = st.nextToken();
        if(CommandHeader.toUpperCase().equals("EXIT")) {
            ToClient.writeUTF("Program is now terminating process....");
            System.exit(0);
        }
        // If the token number is less than 3, then the command Parameter is out of format ::
        if(st.countTokens() != 2) {
            ToClient.writeUTF(st.countTokens() > 2 ? "Incorrect : Too many arguments" : "Incorrect : argument is not enough");
            return;
        }
        long First, Second;
        long result;
        // We provide case ignorance ::
        // if the first token of command is EXIT, then terminate the program ::
        switch (CommandHeader.toUpperCase()) {
            case "ADD" -> {
                try {
                    First = Long.parseLong(st.nextToken());
                    Second = Long.parseLong(st.nextToken());
                    result = First + Second;
                    ToClient.writeUTF(Long.toString(result));
                    // if Input values exceed integer range, then exception(number format) will be occured ::
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
                // if the second value is Zero, then divide by zero exception will be occur ::
                if (Second == 0) {
                    ToClient.writeUTF("Incorrect : Divide by Zero Exception");
                }
                else
                    ToClient.writeUTF(Long.toString(First / Second));
            }
            // if the command format is ignored, then command violation exception will be occur ::
            default -> ToClient.writeUTF("Incorrect : wrong command");
        }
    }
}
