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
        // 클라이언트 소켓을 accept 합니다. ::
        Socket ConnectedSocket = listener.accept();
        System.out.println("Client connected : " + ConnectedSocket);
        // 클라이언트로부터 입력을 받기 위해 DataInputStream을 선언합니다. ::
        DataInputStream FromClient = new DataInputStream(ConnectedSocket.getInputStream());
        // 클라이언트에게 출력을 주기 위해 DataOutputStream을 선언합니다. ::
        DataOutputStream ToClient = new DataOutputStream(ConnectedSocket.getOutputStream());
        while(true) {
            try {
                // 클라이언트로부터 명령어를 읽어옵니다 ::
                String temp = FromClient.readUTF();
                // 계산함수를 호출하여 결과 혹은 오류를 클라이언트에게 전달합니다 ::
                InputProcessor(temp, ToClient, listener, ConnectedSocket);
                // 클라이언트가 없다면, 서버를 종료합니다. ::
            } catch (IOException e) {
                System.out.println("There's no more client here. terminating program");
                System.exit(0);
            }
        }
    }
    static void InputProcessor(String Input, DataOutputStream ToClient, ServerSocket serverSocket, Socket client) throws IOException{
        st = new StringTokenizer(Input, " ");
        String CommandHeader = st.nextToken();
        // 만약 명령어의 시작부분이 EXIT 라면 프로그램을 종료합니다. ::
        if(CommandHeader.toUpperCase().equals("EXIT")) {
            ToClient.writeUTF("Program is now terminating process....");
            try {
                Close(serverSocket, client);
            }catch(Exception e) {
                System.out.println(e.toString());
            }
            System.exit(0);
        }
        // 만약 토큰의 개수가 2개가 아니라면, 많고 적음을 따져서 오류를 발생시킵니다. ::
        if(st.countTokens() != 2) {
            ToClient.writeUTF(st.countTokens() > 2 ? "Incorrect : Too many arguments" : "Incorrect : argument is not enough");
            return;
        }
        long First, Second;
        long result;
        // 계산기는 대소문자에 관계없이 동작합니다. ::
        switch (CommandHeader.toUpperCase()) {
            case "ADD" -> {
                try {
                    First = Long.parseLong(st.nextToken());
                    Second = Long.parseLong(st.nextToken());
                    result = First + Second;
                    ToClient.writeUTF(Long.toString(result));
                    // 만약 입력받은 First나 Second 값이 숫자가 아니거나 범위를 넘어서게 되면 오류를 발생시킵니다. ::
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
                // 만약 두번째 입력값이 0이라면, 오류를 발생시킵니다. ::
                if (Second == 0) {
                    ToClient.writeUTF("Incorrect : Divide by Zero Exception");
                }
                else
                    ToClient.writeUTF(Long.toString(First / Second));
            }
            // 명령어 프로토콜이 지켜지지 않았다면 오류를 발생시킵니다. ::
            default -> ToClient.writeUTF("Incorrect : wrong command");
        }
    }
    static void Close(ServerSocket serverSocket, Socket client) throws Exception{
        serverSocket.close();
        client.close();
    }
}
