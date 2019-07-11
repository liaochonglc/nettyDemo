package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NioTestCopy {
    public static void main(String[] args) throws Exception{
        String inputFile = "src/main/in.txt";
        String outputFile = "src/main/out.txt";
        RandomAccessFile inAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outAccessFile = new RandomAccessFile(outputFile,"rw");
        long length = new File(inputFile).length();
        FileChannel inChannel = inAccessFile.getChannel();
        FileChannel outChannel= outAccessFile.getChannel();
        MappedByteBuffer map = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);
        Charset charset = Charset.forName("utf-8");
        //解码
        CharsetDecoder decoder = charset.newDecoder();
        //编码
        CharsetEncoder encoder = charset.newEncoder();
        CharBuffer deBuffer = decoder.decode(map);
        ByteBuffer encode = encoder.encode(deBuffer);
        outChannel.write(encode);
        inAccessFile.close();
        outAccessFile.close();
    }
}
