package Small_Projects;

import java.io.*;

public class FileIOPrac {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\oldst\\Desktop\\201835539 최철웅\\server_info.dat");
        try {
            byte[] binary = new byte[(int)file.length()];
            InputStream stream = new FileInputStream(file);
            stream.read(binary);
            stream.close();
            System.out.println(new String(binary));
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
