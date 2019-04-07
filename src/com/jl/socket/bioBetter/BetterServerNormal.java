package com.jl.socket.bioBetter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName BetterServerNormal
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 21:53
 * @Version 1.0
 */
public class BetterServerNormal {
    private static int DEFUALT_PORT = 54321;
    private static ServerSocket serverSocket;
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void start(){
        start(DEFUALT_PORT);
    }

    private synchronized static void start(int port){
        if(serverSocket != null) return;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已启动，端口："+port);
            while(true){
               Socket socket = serverSocket.accept();
               executorService.execute(new BetterServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(serverSocket != null){
                    System.out.println("服务器已关闭");
                    serverSocket.close();
                    serverSocket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
