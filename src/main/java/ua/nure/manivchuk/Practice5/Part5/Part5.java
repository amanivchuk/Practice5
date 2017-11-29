package ua.nure.manivchuk.Practice5.Part5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by nec on 29.11.17.
 */
public class Part5 {
    private String fileName = "src/main/resources/part5.txt";
    private int[] numbbers = {0,1,2,3,4,5,6,7,8,9};
    private int countLine = 0;
    private int countNumbers = 20;

    public void existFile(){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
    }

    public void startThreads(){
        for(int i = 0; i < numbbers.length; i++){
            new Thread(() -> {
                writeData();
            }).start();
        }
    }

    public synchronized void writeData(){
        try {
            RandomAccessFile fileStore = new RandomAccessFile(fileName, "rw");

            long position = 0;

            while(countLine ++ < 10){
                if( countNumbers < 20){
                    fileStore.seek(position);
                    fileStore.writeUTF(String.valueOf(numbbers[countLine]));
                    fileStore.writeUTF(System.lineSeparator());
                    position = fileStore.getFilePointer();
                    countNumbers++;
                }
            }


            /*while(countLine++ < 10) {
                for (int i = 0; i < countNumbers; i++) {
                    System.out.println(fileStore.getFilePointer());
                    write(fileStore, numbbers[countLine]);
                }
                fileStore.writeUTF(System.lineSeparator());
            }
            fileStore.close();*/
        } catch (Exception e) {}
    }


    public synchronized void write(RandomAccessFile fileStore, int writeData) throws IOException {
        fileStore.writeUTF(String.valueOf(writeData));
    }

    public void showDataFromFile(){
        StringBuffer str = new StringBuffer();
        try {
            RandomAccessFile fileStore = new RandomAccessFile(fileName, "rw");
            for(int i = 0; i < fileStore.length(); i+=3){
                fileStore.seek(i);
                str.append(fileStore.readUTF());
            }
            System.out.println(str.toString());
            fileStore.close();
        } catch (Exception e) {}
    }

    public static void main(String[] args) {

        Part5 part5 = new Part5();
        part5.existFile();
        part5.writeData();
        part5.showDataFromFile();
    }
}
