package com.jl.socket.bioBetter;

import java.util.Random;

/**
 * @ClassName BetterMain
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/27 22:16
 * @Version 1.0
 */
public class BetterMain {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BetterServerNormal.start();
            }
        }).start();
        Thread.sleep(1000);
        char operators[] = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);
                    BetterClientHandler.send(expression);
                    try {
                        Thread.currentThread().sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
