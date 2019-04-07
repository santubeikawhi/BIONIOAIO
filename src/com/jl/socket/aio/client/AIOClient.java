package com.jl.socket.aio.client;

/**
 * @ClassName AIOClient
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 12:49
 * @Version 1.0
 */
public class AIOClient {
    private static int DEFAULT_PORT = 54321;
    private static String DEFAULT_HOST = "127.0.0.1";
    private static AIOAsyncClientHandler aioAsyncClientHandler;

    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }

    private static synchronized void start(String host,int port){
        if(aioAsyncClientHandler != null)
            return;
        aioAsyncClientHandler = new AIOAsyncClientHandler(host,port);
        new Thread(aioAsyncClientHandler,"Client").start();
    }

    public static boolean sendMessage(String message){
        if(message.equals("q")){
            return false;
        }else{
            aioAsyncClientHandler.sendMessage(message);
            return true;
        }
    }
}
