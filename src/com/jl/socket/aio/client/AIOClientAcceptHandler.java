package com.jl.socket.aio.client;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @ClassName AIOClientAcceptHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 13:02
 * @Version 1.0
 */
public class AIOClientAcceptHandler implements CompletionHandler<Void,AIOAsyncClientHandler> {
    public AIOClientAcceptHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Void result, AIOAsyncClientHandler attachment) {
        System.out.println("客户端成功连接到服务器...");
    }

    @Override
    public void failed(Throwable exc, AIOAsyncClientHandler attachment) {
        System.err.println("连接服务器失败...");
        exc.printStackTrace();
        try {
            asynchronousSocketChannel.close();
            attachment.getLatch().countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
