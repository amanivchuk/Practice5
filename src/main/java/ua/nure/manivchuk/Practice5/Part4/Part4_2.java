package ua.nure.manivchuk.Practice5.Part4;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by nec on 30.11.17.
 */
public class Part4_2 {

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
        System.out.println("----------------------------------------");
    }


    public void multiThreadMax(){
        int[] maxValue = new int[SIZE];

        MaxIndexArray[] runners = new MaxIndexArray[SIZE];
        Thread[] thread = new Thread[SIZE];
        long time = System.currentTimeMillis();

        for(int k = 0; k < SIZE; k++){
            runners[k] = new MaxIndexArray(array[k], 0, array[k].length);
            thread[k] = new Thread(runners[k]);
            thread[k].start();
        }
        for(int k = 0; k < SIZE; k++){
            try {
                thread[k].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total time of multiple thread: " + (System.currentTimeMillis() - time));
        for(int k = 0; k < SIZE; k++){
            maxValue[k] = runners[k].getMaxValue();
        }
        Arrays.sort(maxValue);
        System.out.println("MultipleThread max number: " + maxValue[maxValue.length-1]);
    }

    public void singledThreadMax() {
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
        System.out.println("Total time of single thread: " + (System.currentTimeMillis() - time));
        System.out.println("SingleThread max number: " + maxValue);
    }

    public static void main(String[] args) {
        Part4_2 p = new Part4_2();
        p.fill();
        p.multiThreadMax(); /*Start multiple thread*/
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p.singledThreadMax(); /*Start single thread*/
    }
}

class MaxIndexArray implements Runnable{

    private int[] array;
    private int from;
    private int to;
    private volatile int maxValue;

    public int getMaxValue() {
        return maxValue;
    }

    public MaxIndexArray(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        int currentMaxValue = array[from];
        for(int k = from; k < to; k++){
             currentMaxValue = Math.max(currentMaxValue, array[k]);
        }
        maxValue = currentMaxValue;
    }
}
