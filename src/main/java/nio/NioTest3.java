package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {
    public static void main(String[] args) throws Exception{
        InputStream inputStream = new FileInputStream("src/main/a.txt");
        OutputStream outputStream = new FileOutputStream("src/main/b.txt");
        FileChannel input = ((FileInputStream) inputStream).getChannel();
        FileChannel output = ((FileOutputStream) outputStream).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true){
            buffer.clear();
            int read = input.read(buffer);
            System.out.println(read);
            if(read==-1){
                break;
            }
            buffer.flip();
            output.write(buffer);
        }
    }
}
