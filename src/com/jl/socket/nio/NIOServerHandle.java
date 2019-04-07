package com.jl.socket.nio;

import com.jl.socket.utils.Calculate;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NIOServerHandle
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 22:23
 * @Version 1.0
 */
public class NIOServerHandle implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started;

    public NIOServerHandle(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            serverSocketChannel.configureBlocking(false);//开启非阻塞模式
            //绑定端口 backlog设为1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //监听客户端连接请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;//标记服务器已开启
            System.out.println("服务器已启动，端口号：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        while(started){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while(iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if(selector != null)
            try{
                selector.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void handleInput(SelectionKey selectionKey){
        try {
            if(selectionKey.isValid()){
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                }
                if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer byteBuffer  = ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(byteBuffer);
                    if(readBytes>0){
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bytes);
                        String expression = new String(bytes,"UTF-8");
                        System.out.println("服务器收到消息："+expression);
                        String result = null;
                        try {
                            result = Calculate.cal(expression).toString();
                        } catch (ScriptException e) {
                            e.printStackTrace();
                            result = "计算错误："+e.getMessage();
                        }
                        //发送应答消息
                        doWrite(socketChannel,result);
                    }else if(readBytes == 0){
                        //忽略
                    }else if(readBytes<0){
                        selectionKey.cancel();
                        socketChannel.close();
                    }
                }
                if(selectionKey.isWritable()){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void doWrite(SocketChannel socketChannel,String response){
        try {
            byte[] bytes = response.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
