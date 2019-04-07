package com.jl.socket.nio;

import java.net.ServerSocket;

/**
 * @ClassName NIOServer
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 22:22
 * @Version 1.0
 */
public class NIOServer {
    private static int DEFUALT_PORT = 54321;
    private static NIOServerHandle nioServerHandle= null;
    public static void start(){
        start(DEFUALT_PORT);
    }

    private static synchronized void start(int port){
        if(nioServerHandle != null){
            nioServerHandle.stop();
        }
        nioServerHandle = new NIOServerHandle(port);
        new Thread(nioServerHandle,"server").start();
    }

    public static void main(String[] args){
        start();
    }
}
