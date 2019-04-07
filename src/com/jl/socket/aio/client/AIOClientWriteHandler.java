package com.jl.socket.aio.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName AIOClientWriteHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 15:39
 * @Version 1.0
 */
public class AIOClientWriteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel asynchronousSocketChannel;
    private CountDownLatch latch;

    public AIOClientWriteHandler(AsynchronousSocketChannel asynchronousSocketChannel, CountDownLatch latch) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.latch = latch;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        if(attachment.hasRemaining()){
            asynchronousSocketChannel.write(attachment,attachment,this);
        }else{
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            asynchronousSocketChannel.read(byteBuffer,byteBuffer,new AIOClientReadHandler(asynchronousSocketChannel,latch));
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}
