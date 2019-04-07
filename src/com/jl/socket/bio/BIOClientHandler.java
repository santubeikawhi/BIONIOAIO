package com.jl.socket.bio;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @ClassName BIOClientHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/26 9:00
 * @Version 1.0
 */
public class BIOClientHandler {
    private static int DEFAULT_PORT = 54321;
    private static String DEFAULT_IP = "127.0.0.1";

    public static void send(String expression){
        send(DEFAULT_IP,DEFAULT_PORT,expression);
    }

    private static void send(String ip,int port,String expression){
        System.out.println("算术表达式为："+expression);
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            socket = new Socket(ip,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(expression);
            out.flush();
            socket.shutdownOutput();
            String result = in.readLine();
            System.out.println("计算结果为："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
