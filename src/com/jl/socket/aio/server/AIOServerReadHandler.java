package com.jl.socket.aio.server;

import com.jl.socket.utils.Calculate;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @ClassName AIOServerReadHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/6 21:53
 * @Version 1.0
 */
public class AIOServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel asynchronousSocketChannel;
    public AIOServerReadHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);

        try {
            String expression = new String(message,"UTF-8");
            System.out.println("服务器接收到消息："+expression);
            String calrResult = null;
            try {
                calrResult = Calculate.cal(expression).toString();
            } catch (ScriptException e) {
                calrResult = "计算错误："+e.getMessage();
            }
           doWrite(calrResult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String result){
        try {
            byte[] bytes = result.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            asynchronousSocketChannel.write(byteBuffer,byteBuffer,new AIOServerWriteHandler(asynchronousSocketChannel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.getAsynchronousSocketChannel().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
