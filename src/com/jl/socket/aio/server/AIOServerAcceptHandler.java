package com.jl.socket.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @ClassName AIOServerAcceptHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/6 21:17
 * @Version 1.0
 */
public class AIOServerAcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AIOAsyncServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AIOAsyncServerHandler attachment) {
        AIOServer.clientCount++;
        System.out.println("连接的客户端数："+AIOServer.clientCount);
        attachment.serverSocketChannel.accept(attachment,this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //异步读  第三个参数为接收消息回调的业务Handler
        result.read(byteBuffer,byteBuffer,new AIOServerReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, AIOAsyncServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
