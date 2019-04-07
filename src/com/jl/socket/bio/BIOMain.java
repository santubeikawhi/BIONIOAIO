package com.jl.socket.bio;

import java.util.Random;

/**
 * @ClassName BIOMain
 * @Description TODO
 * @Author Jiangl
 * @Date 2019/3/26 9:13
 * @Version 1.0
 */
public class BIOMain {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BIOServerNormal.start();
            }
        }).start();

        Thread.sleep(1000);

        char operators[] = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //随机产生算术表达式
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);
                    BIOClientHandler.send(expression);
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
