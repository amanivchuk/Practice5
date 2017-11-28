package ua.nure.manivchuk.Practice5.Part1;

import java.util.concurrent.TimeUnit;

/**
 * Created by nec on 28.11.17.
 */
public class Part1 {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                } catch (InterruptedException e) {
                }
            }
        });

        t.start();
        MyThread myThread = new MyThread();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        myThread.interrupt();
    }
}

class MyThread extends Thread{

    public MyThread() {
        start();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
        }
    }
}
