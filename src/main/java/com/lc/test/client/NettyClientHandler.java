package com.lc.test.client;

import com.lc.test.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class NettyClientHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.MyMessage message) throws Exception {

    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        System.out.println(randomInt);
        DataInfo.MyMessage myMessage = null;
        if (0 == randomInt) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.StudentType).
                            setStudent(DataInfo.Student.newBuilder().
                                    setName("廖翀").setAge(20).build()).build();
        } else if (1 == randomInt) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.DogType).
                            setDog(DataInfo.Dog.newBuilder().
                                    setName("柴犬").setAge(1).build()).build();
        } else {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.CatType).
                            setCat(DataInfo.Cat.newBuilder().
                                    setName("梨花喵").setCity("深圳").build()).build();
        }
        ctx.channel().writeAndFlush(myMessage);
    }
}
