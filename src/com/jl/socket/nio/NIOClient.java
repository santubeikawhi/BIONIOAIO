package com.jl.socket.nio;

import java.io.IOException;

/**
 * @ClassName NIOClient
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/29 9:57
 * @Version 1.0
 */
public class NIOClient {
    private static int DEFAULT_PORT = 54321;
    private static String DEFAULT_IP = "127.0.0.1";
    private static NIOClientHandle nioClientHandle;
    public static void start(){
        start(DEFAULT_IP,DEFAULT_PORT);
    }

    private static synchronized void start(String ip,int port){
        if(nioClientHandle != null)
            nioClientHandle.stop();
        nioClientHandle = new NIOClientHandle(ip,port);
        new Thread(nioClientHandle,"client").start();
    }

    public static boolean sendMessage(String message) throws IOException {
        if(message.equals("q")) return false;
        nioClientHandle.sendMessage(message);
        return true;
    }
}
