package com.lc.test.server;

import com.lc.test.DataInfo;
import io.netty.channel.*;
import org.springframework.stereotype.Component;


@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.MyMessage myMessage) throws Exception {
        DataInfo.MyMessage.DataType dataType = myMessage.getDataType();
        if(dataType == DataInfo.MyMessage.DataType.StudentType){
            DataInfo.Student student = myMessage.getStudent();
            System.out.println(student.getName());
            System.out.println(student.getAge());
        }else if(dataType == DataInfo.MyMessage.DataType.DogType){
            DataInfo.Dog dog = myMessage.getDog();
            System.out.println("-------"+dog.getName());
            System.out.println("-------"+dog.getAge());
        }else{
            DataInfo.Cat cat = myMessage.getCat();
            System.out.println("======="+cat.getName());
            System.out.println("======="+cat.getCity());
        }
    }
}
