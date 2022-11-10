package fileWork;

import java.io.File;
import java.util.Scanner;

public class FileScan {
    private static Scanner scF;

    public static void create(String path) {
        try {
            scF = new Scanner(new File(path));
        } catch (Exception e) {
            System.out.println("Не вдалося відкрити файл!");
            System.exit(0);
        }
    }

    public static void printAll(){
        while (scF.hasNext())
            System.out.println(scF.nextLine());

        scF.reset();
    }
    public static void close() {
        try {
            scF.close();
        }
        catch (Exception e) {

        }
    }

}
