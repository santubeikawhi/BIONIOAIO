package com.jl.socket.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName AIOAsyncClientHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 12:54
 * @Version 1.0
 */
public class AIOAsyncClientHandler implements Runnable {
    private String ip;
    private int port;
    private AsynchronousSocketChannel asynchronousSocketChannel;

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    private CountDownLatch latch;

    public AIOAsyncClientHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            this.asynchronousSocketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        try {
            asynchronousSocketChannel.connect(new InetSocketAddress(ip,port),this,new AIOClientAcceptHandler(asynchronousSocketChannel));
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        byte[] bytes = message.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        asynchronousSocketChannel.write(byteBuffer,byteBuffer,new AIOClientWriteHandler(asynchronousSocketChannel,latch));
    }
}
