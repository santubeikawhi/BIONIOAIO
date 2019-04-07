package com.jl.socket.bio;

import com.jl.socket.utils.Calculate;

import javax.script.ScriptException;
import java.io.*;
import java.net.Socket;

/**
 * @ClassName BIOServerHandler
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/25 23:14
 * @Version 1.0
 */
public class BIOServerHandler implements Runnable{
    private Socket socket ;

    public BIOServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            String expression  = in.readLine();
            String result = "";
            try {
                 result = Calculate.cal(expression).toString();
            } catch (ScriptException e) {
                result = "计算错误："+e.getMessage();
                e.printStackTrace();
            }
            out.write(result);
            out.flush();
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
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
