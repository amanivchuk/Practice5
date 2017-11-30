package ua.nure.manivchuk.Practice5.Part5_2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * Created by nec on 30.11.17.
 */
public class Part5_2 {
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
        } catch (IOException | InterruptedException e) {
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
        Part5_2 p = new Part5_2();

        Thread whiteSpace = new Thread(new WriteWhiteSpaceInFile());
        whiteSpace.start();
        try {
            whiteSpace.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

                    System.out.println(textToWrite);
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

class WriteWhiteSpaceInFile implements Runnable{
    private static final int COLUMNS = 20;
    private String textToWrite = " ";
    private int position = 0;

    @Override
    public void run() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/part5.txt", "rw");
            int line = 0;
            while (line++ < 10) {
                for (int i = 0; i < COLUMNS; i++) {
                    file.seek(position++);
                    file.write(textToWrite.getBytes(Charset.forName("UTF-8")));
                    System.out.print(textToWrite);
                    if(i == COLUMNS-1){
                        file.write(System.lineSeparator().getBytes(Charset.forName("UTF-8")));
                        position++;
                    }
                }
                System.out.println();
            }
            file.close();
        }catch (Exception e){}
    }
}