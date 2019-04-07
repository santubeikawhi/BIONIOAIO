package com.jl.socket.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName AIOClientReadHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 15:43
 * @Version 1.0
 */
public class AIOClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel asynchronousSocketChannel;
    private CountDownLatch latch;

    public AIOClientReadHandler(AsynchronousSocketChannel asynchronousSocketChannel, CountDownLatch latch) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.latch = latch;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        String alresult = null;
        try {
            alresult = new String(bytes,"UTF-8");
            System.out.println("客户端收到结果:"+ alresult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.err.println("数据读取失败...");
        try {
            asynchronousSocketChannel.close();
            latch.countDown();
        } catch (IOException e) {
        }
    }
}
