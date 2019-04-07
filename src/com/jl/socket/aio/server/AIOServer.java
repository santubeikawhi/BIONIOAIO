package com.jl.socket.aio.server;

/**
 * @ClassName AIOServer
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/6 21:10
 * @Version 1.0
 */
public class AIOServer {
    private static int DEFAULT_PORT = 54321;
    public volatile static long clientCount = 0;
    private static AIOAsyncServerHandler aioAsyncServerHandler;
    public static void start(){
        start(DEFAULT_PORT);
    }

    private static synchronized void start(int port){
        if(aioAsyncServerHandler != null)
            return;
        aioAsyncServerHandler = new AIOAsyncServerHandler(port);
        new Thread(aioAsyncServerHandler,"server").start();
    }
}
