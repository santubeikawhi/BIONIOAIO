package com.jl.socket.bioBetter;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName BetterClientHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 22:08
 * @Version 1.0
 */
public class BetterClientHandler {
    private static int DEFUALT_PORT = 54321;
    private static String DEFAULT_IP = "127.0.0.1";

    public static void send(String expression){
        send(DEFUALT_PORT,DEFAULT_IP,expression);
    }

    private static void send(int port,String ip,String expression){
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
             socket = new Socket(ip,port);
             printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             printWriter.write(expression);
             printWriter.flush();
            socket.shutdownOutput();
            String result = bufferedReader.readLine();
            System.out.println("计算结果:"+result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                    bufferedReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(printWriter != null){
                printWriter.close();
                printWriter = null;
            }
            try {
                if(socket != null){
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
