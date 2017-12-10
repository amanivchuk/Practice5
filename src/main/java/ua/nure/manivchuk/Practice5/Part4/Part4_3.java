package ua.nure.manivchuk.Practice5.Part4;

/**
 * Created by Lenovo on 12/10/2017.
 */
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Part4_3 {
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
    public void fillFromFile() {
        try {
            Scanner scanner = new Scanner(new File("part4.txt"));
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    array[i][j] = scanner.nextInt();
//                        System.out.print(array[i][j] + " ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        final Part4_3 p = new Part4_3();
        p.writeDataToFile();
        p.fillFromFile();
//        p.fill();


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

    private void writeDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("part4.txt"), "utf-8"))){
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 100; j++){
                    writer.write(random.nextInt(1000) + " ");
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
