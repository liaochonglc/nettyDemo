package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest4 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8888);
        serverSocketChannel.bind(address);
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);
        //这本身是阻塞的
        SocketChannel accept = serverSocketChannel.accept();
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = accept.read(byteBuffers);
                if(read==-1){
                    return;
                }
                byteRead += read;
                System.out.println("---------+"+byteRead);
                //把对象转成字符串
                Arrays.asList(byteBuffers).stream().map(buffer -> "posion:"
                        + buffer.position() + "," + "limit:" + buffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(b -> {
                b.flip();
            });
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = accept.write(byteBuffers);
                byteWrite += write;
            }
            Arrays.asList(byteBuffers).stream().forEach(buffer -> {
                buffer.clear();
            });
            System.out.println("byteRead:"+byteRead+" byteWrite:"+byteWrite+" messageLength:"+messageLength);
        }
    }
}
