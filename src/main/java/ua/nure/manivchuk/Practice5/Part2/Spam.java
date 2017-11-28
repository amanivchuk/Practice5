package ua.nure.manivchuk.Practice5.Part2;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by nec on 28.11.17.
 */
public class Spam {

    private int[] times;
    private String[] messages;


    Spam(int[] times, String[] messages) {
        this.times = times;
        this.messages = messages;
    }

    Thread t = new Thread(new Runnable() {
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                for (int i = 0, j = 0; i < messages.length && j < times.length; i++, j++) {
                    if(Thread.currentThread().isInterrupted() ){
                        break;
                    }
                    System.out.println(messages[i]);
                    TimeUnit.MILLISECONDS.sleep(times[j]);
                }
            } catch (InterruptedException e) {}
        }
    });

    void start() {
        t.start();
    }

    void stop() {
        t.interrupt();
    }



    public static void main(String[] args) {
        int[] times = {1300, 1500, 1800, 1400, 1900};
        String[] messages = {"Aaaa", "Bbbb", "Cccc", "Dddd", "Eeee",};

        Spam spam = new Spam(times, messages);
        spam.start();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        spam.stop();
        sc.close();
    }

}
