package ua.nure.manivchuk.Practice5.Part4;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by nec on 28.11.17.
 */
public class Part4 {
    private static final int SIZE = 4;
    private int[][] array = new int[SIZE][100];
    private static final Random random = new Random(47);

    public void fill() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = random.nextInt(1000);
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int singledThreadMax() {
        long time = System.currentTimeMillis();
            int maxValue = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (array[i][j] > maxValue) {
                        maxValue = array[i][j];
                    }
                }
            }
        System.out.println("Time of single thread = " + (System.currentTimeMillis() - time));
        return maxValue;
    }

    public int[] multipleThread(){
        long time = System.currentTimeMillis();
        final int[] tmp = new int[SIZE];
        for(int i = 0; i < SIZE; i++){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final int count = i;

            new Thread(new Runnable() {
                public void run() {
                    tmp[count] = getMaximumValue(array[count]);
                }
            }).start();
        }
        System.out.println("Time of multiple thread = " + (System.currentTimeMillis() - time));
        return tmp;
    }

    private int getMaximumValue(int[] mas) {

        int max = mas[0];
        for(int i = 0; i < mas.length; i++){
            if(mas[i] > max){
                max = mas[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        final Part4 p = new Part4();
        p.fill();


        new Thread(new Runnable() {
            public void run() {
                System.out.println("SingleThread: " + p.singledThreadMax());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                int[] result = p.multipleThread();
                Arrays.sort(result);
                System.out.println("MultipleThread: " + result[result.length-1]);
            }
        }).start();


    }

}
