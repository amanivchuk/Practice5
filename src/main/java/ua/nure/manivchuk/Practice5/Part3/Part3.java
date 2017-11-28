package ua.nure.manivchuk.Practice5.Part3;

import java.util.concurrent.TimeUnit;

/**
 * Created by nec on 28.11.17.
 */
public class Part3 {
    private int c1;
    private int c2;

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public void ouput(){
        setC1(getC1() + 1);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setC2(getC2() + 1);
        System.out.print(getC1() - getC2() + " ");
    }


    public synchronized void ouputSynchronized(){
        setC1(getC1() + 1);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setC2(getC2() + 1);
        System.out.print(getC1() - getC2() + " ");
    }

    public static void startPart3() {
        final Part3 counter = new Part3();
        System.out.println("------------ Non synchronized method ------------");
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    counter.ouput();
                    System.out.println();
                }
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------------ Synchronized method ------------");
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    counter.ouputSynchronized();
                    System.out.println();
                }
            }).start();
        }
    }
    public static void main(String[] args) {
       startPart3();
    }
}
