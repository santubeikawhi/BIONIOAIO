package com.jl.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName BIOServerNormal
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/25 23:07
 * @Version 1.0
 */
public class BIOServerNormal {
    private static int DEFUALT_PORT = 54321;
    private static ServerSocket server;

    public static void start(){
        try {
            start(DEFUALT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized static void start(int port){
        if(server != null){
            return;
        }
        try {
            server = new ServerSocket(port);
            System.out.println("服务端已启动，端口号为："+port);
            while(true){
                Socket socket = server.accept();
                new Thread(new BIOServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(server!=null){
                    System.out.println("服务器已关闭！");
                    server.close();
                    server = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
