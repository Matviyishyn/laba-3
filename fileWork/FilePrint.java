package fileWork;

import java.io.File;
import java.io.FileWriter;

public class FilePrint {
    private static FileWriter fw;

    public static void create(String path) {
        try {
            fw = new FileWriter(new File(path), false);
        }
        catch (Exception e) {
            System.out.println("Не вдалося відкрити файл!");
            System.exit(0);
        }
    }

    public static void print(String s){
        try {
            fw.write(s + "\n");
        }
        catch (Exception e){
            System.out.println("Не вдалося записати значення");
        }

    }

    public static void close() {
        try {
            fw.close();
        }
        catch (Exception e) {

        }
    }

}
