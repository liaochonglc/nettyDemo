package com.lc.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup work = null;
        try {
            work = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work).channel(NioSocketChannel.class)
                    .handler(new NettyClientChannelInitializer());
            ChannelFuture localhost = bootstrap.connect("127.0.0.1", 8888).sync();
            localhost.channel().closeFuture().sync();
        }finally {
            work.shutdownGracefully();
        }
    }
}
