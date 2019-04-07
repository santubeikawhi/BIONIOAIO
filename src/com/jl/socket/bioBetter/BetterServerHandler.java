package com.jl.socket.bioBetter;

import com.jl.socket.utils.Calculate;

import javax.script.ScriptException;
import java.io.*;
import java.net.Socket;

/**
 * @ClassName BetterServerHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 22:01
 * @Version 1.0
 */
public class BetterServerHandler implements Runnable{
    private Socket socket;

    public BetterServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String expression  = in.readLine();
            System.out.println("算术表达式为："+expression);
            String result = "";
            try {
                result = Calculate.cal(expression).toString();
            } catch (ScriptException e) {
                result = "计算出错："+ e.getMessage();
            }
            out.write(result);
            out.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
