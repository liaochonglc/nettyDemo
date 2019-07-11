package nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            //这玩意比普通random更加的随机
            int ranNumber = new SecureRandom().nextInt(20);
            buffer.put(ranNumber);
        }
        //这玩意是状态翻转 不然没办法实现同时读和写
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
