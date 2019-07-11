package zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8888));
        socketChannel.configureBlocking(true);
        FileChannel fileChannel = new FileInputStream("src/main/a.txt").getChannel();
        long l = System.currentTimeMillis();
        //实际传递的字节数
        //零拷贝
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("字节: "+count+","+"时间: "+(System.currentTimeMillis()-l));
        socketChannel.close();
    }
}
