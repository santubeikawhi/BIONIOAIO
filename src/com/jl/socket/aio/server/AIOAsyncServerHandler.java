package com.jl.socket.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName AIOAsyncServerHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/6 21:11
 * @Version 1.0
 */
public class AIOAsyncServerHandler implements Runnable {
    public CountDownLatch latch;
    public AsynchronousServerSocketChannel serverSocketChannel;

    public AIOAsyncServerHandler(int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务端已启动，端口号："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        serverSocketChannel.accept(this,new AIOServerAcceptHandler());
    }
}
