package com.jl.socket.nio;

import java.io.IOException;
import java.util.Scanner;

/**
 * @ClassName NIOMain
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/3 22:22
 * @Version 1.0
 */
public class NIOMain {
    public static void main(String[] args){
        try {
            NIOServer.start();
            Thread.sleep(1000);
            NIOClient.start();
            while(NIOClient.sendMessage(new Scanner(System.in).nextLine()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
