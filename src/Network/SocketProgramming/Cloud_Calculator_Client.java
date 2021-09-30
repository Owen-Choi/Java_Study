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
            // 파일로부터 IP주소와 포트 번호를 읽어옵니다. ::
            File file = new File("C:\\Users\\oldst\\Desktop\\201835539 최철웅\\server_info.dat");
            byte[] binary = new byte[(int)file.length()];
            // file에서 값을 읽어오기 위해 InputStream을 선언합니다. ::
            InputStream is = new FileInputStream(file);
            is.read(binary);
            is.close();
            // tokenize return value ::
            st = new StringTokenizer(new String(binary));
            IPAddress = st.nextToken();
            Pnum = Integer.parseInt(st.nextToken());
        }catch(Exception e) {
            System.out.println(e.toString());
            IPAddress = "127.0.0.1";
            Pnum = 10002;
        }
        // 파일로부터 읽어온 IP주소와 포트번호를 이용해서 서버에 연결합니다. ::
        Socket client = new Socket(IPAddress, Pnum);
        // 유저로부터 입력을 받기 위해 BufferedReader를 선언합니다. ::
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Server로부터 입력을 받기 위해 DataInputStream을 선언합니다. ::
        DataInputStream FromServer = new DataInputStream(client.getInputStream());
        // 서버에게 출력을 주기 위해 DataOutputStream을 선언합니다. ::
        DataOutputStream ToServer = new DataOutputStream(client.getOutputStream());
        // 계산기 프토로콜을 간단하게 안내합니다. ::
        System.out.println(":: Welcome to Cloud Calculator! ::");
        System.out.println("Please Enter Command with the format : ");
        System.out.println("\t (Arithmetic Expression) Integer1 Integer2");
        System.out.println("\t Possible Arithmetic Expression : ADD, MINUS, DIV, MULTIPLE \n");
        System.out.println("\t If you want to terminate calculator, please enter 'Exit' ");
        String Command;
        while(true) {
            System.out.print("Please Enter the Command : ");
                try {
                    //유저로부터 커맨드를 입력받습니다. ::
                    Command = br.readLine();
                    // 서버에게 커맨드를 보냅니다. ::
                    ToServer.writeUTF(Command);
                    // 서버로부터 받은 문자열을 출력합니다. (답이든 오류든) ::
                    System.out.println(FromServer.readUTF());
                    // 만약 유저의 커맨드의 시작부분이 EXIT로 시작한다면 클라이언트 프로그램 종료. ::
                    st = new StringTokenizer(Command," ");
                    if(st.nextToken().toUpperCase().equals("EXIT")) {
                        client.close();
                        System.exit(0);
                    }
                } catch (IOException e) {
                    System.out.println("Error from Client");
                }
        }
    }
}
