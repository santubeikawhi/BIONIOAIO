package com.jl.socket.aio;

import com.jl.socket.aio.client.AIOClient;
import com.jl.socket.aio.server.AIOServer;

import java.util.Scanner;

/**
 * @ClassName AIOMain
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/4/7 15:56
 * @Version 1.0
 */
public class AIOMain {
    public static void main(String[] args){
        try {
            AIOServer.start();
            Thread.sleep(1000);
            AIOClient.start();
            Scanner scanner = new Scanner(System.in);
            while (AIOClient.sendMessage(scanner.nextLine()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
