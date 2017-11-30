package ua.nure.manivchuk.Practice5.Part5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * Created by nec on 30.11.17.
 */
public class Part5 {

    private static final int THREADS = 10;
    private String fileName = "src/main/resources/part5.txt";
    public void startWrite(){
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            Thread[] threads = new Thread[THREADS];
            WriteFile[] runners = new WriteFile[THREADS];
            for(int i = 0; i < THREADS; i++){
                runners[i] = new WriteFile(file, i);
                threads[i] = new Thread(runners[i]);
                threads[i].start();
            }
            for(Thread t : threads){
                t.join();
            }
            file.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
    public void existFile(){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
    }

    public static void main(String[] args) {
        Part5 p = new Part5();
        p.existFile();
        p.startWrite();
    }
}

class WriteFile implements Runnable{
    private static final int THREADS = 10;
    private static final int COLUMNS = 20;
    private static final int EOL_LENGTH = System.lineSeparator().length();
    private RandomAccessFile randomAccessFile;
    private int idx;
    private String textToWrite;

    public WriteFile(RandomAccessFile randomAccessFile, int position) {
        this.randomAccessFile = randomAccessFile;
        this.idx = position;
        this.textToWrite = String.valueOf(position);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                int position = 0;
                position = idx * (COLUMNS + EOL_LENGTH);
                System.out.println("==> " + position);

                for (int i = 0; i < COLUMNS; i++) {
                    randomAccessFile.seek(position + i);
                    randomAccessFile.write(textToWrite.getBytes(Charset.forName("UTF-8")));
                }
                if (idx != THREADS - 1) {
                    randomAccessFile.write(System.lineSeparator().getBytes(Charset.forName("UTF-8")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
