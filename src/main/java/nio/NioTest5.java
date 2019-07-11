package nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioTest5 {
    public static void main(String[] args) throws Exception {
        //想要批量处理客户端信息 p2p这样的也是 需要在这个写个静态map集合 key是你自己的Id，value是socketChannel
        int[] port = new int[5];
        Selector selector = Selector.open();
        port[0] = 5000;
        port[1] = 5001;
        port[2] = 5002;
        port[3] = 5003;
        port[4] = 5004;
        for (int i = 0; i < port.length; i++) {
            //ServerSocketChannel这个负责关注连接的
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //调成非阻塞
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            //这里是固定的绑定
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port[i]);
            serverSocket.bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口" + port[i]);
        }
        while (true) {
            int read = 0;
            //这玩意是返回它所关注的数量
            int number = selector.select();
            if (number == 0) {
                return;
            }
            System.out.println("number:" + number);
            //获取SelectionKey的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("SelectionKey:" + selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                boolean status;
                SelectionKey selectionKey = iterator.next();
                //如果接入了 连接上了
                if (selectionKey.isAcceptable()) {
                    //这是selecttionKey对应的channel 这是根据注册类型来选择的
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    //这个accept其实就是连接的客户端那个对象
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    //监听读取的
                    //如果是客户端连接的话首先是必须socketChannel,SelectionKey.连接
                    accept.register(selector, SelectionKey.OP_READ);
                    //删除那个set集合的selectKey 这个一定要 也可以直接clear
                    iterator.remove();
                    System.out.println("获取客户端连接" + serverSocketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel accept = (SocketChannel) selectionKey.channel();
                    int byteRead = 0;
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        read = accept.read(byteBuffer);
                        if (read < 0) {
                            status = true;
                            break;
                        }
                        byteBuffer.flip();
                        //把接收的数据一模一样的返回回去
                        accept.write(byteBuffer);
                        byteRead += read;
                    }
                    iterator.remove();
                    if(status){
                        return;
                    }
                }
            }
        }
    }
}
