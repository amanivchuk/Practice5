package ua.nure.manivchuk.Practice5.Part4;

import java.util.Random;

/**
 * Created by nec on 28.11.17.
 */
public class Part4 {
    private int[][] array = new int[4][100];
    private volatile int max = 0;

    public void fill() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 100; j++) {
                array[i][j] = random.nextInt(1000);
            }
        }
    }

    public synchronized void max() {
        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 100; j++) {
                    if (max < array[i][j]) {
                        max = array[i][j];

                        Thread.sleep(1);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(max);
    }

    public void show(){
        System.out.println("Max = " + max);
    }

    public static void main(String[] args) {
        final Part4 p = new Part4();
        p.fill();
        for(int i = 0; i < 5; i++){
            new Thread(new Runnable() {
                public void run() {


                    p.max();
                }
            }).start();
        }
    p.show();

    }

}
