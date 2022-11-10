package menu;

import battle.OneVsOne;
import battle.TeamVsTeam;
import druid.*;
import fileWork.FileScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Menu {

    String path = "file.txt";
    ArrayList<BasicDruid> listD = new ArrayList<>();
    ArrayList<BasicDruid[]> listListD = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Menu() {
        cykl:
        while (true) {
            System.out.println("\nВиберіть команду:\n" +
                    "1 - Змінити файл виводу\n" +
                    "2 - Зробити одного друіда\n" +
                    "3 - Зробити команду друідів\n" +
                    "4 - Викликати бій 1 на 1 \n" +
                    "5 - Викликати бій команда на команду \n" +
                    "6 - Вийти з програми\n");

            int n = safeScanInt(6);

            int maxSize,first, second;
            switch (n){
                case 1:
                    System.out.print("Введіть шлях до файла: ");
                    changeFile(sc.nextLine());
                    break;

                case 2:
                    listD.add(scanDruid());
                    break;

                case 3:
                    System.out.print("Скільки друідів буде в одній команді? ");
                    int count = safeScanInt();
                    BasicDruid[] lst = new BasicDruid[count];
                    IntStream.range(0, count).forEach(i -> lst[i] = scanDruid());

                    listListD.add(lst);
                    break;

                case 4:
                    System.out.println("Виберіть Друідів: ");
                    IntStream.range(0,listD.size()).forEach(i -> System.out.println((i + 1) + ") " +
                            listD.get(i).getType() + " " + listD.get(i).getName()));
                    System.out.println((listD.size() + 1) + ") повернення у меню");

                    maxSize = listD.size() + 1;
                    first = safeScanInt(maxSize);
                    if (first == maxSize) break;

                    do {
                        second = safeScanInt(maxSize);
                    } while (second == first);

                    if (second == maxSize) break;

                    listD.get(first - 1).reset();
                    listD.get(second - 1).reset();

                    OneVsOne batl = new OneVsOne(listD.get(first - 1), listD.get(second - 1), path);

                    batl.battle();
                    FileScan.create(path);
                    FileScan.printAll();
                    FileScan.close();
                    break;

                case 5:
                    System.out.println("Виберіть команду:");
                    int nt = 1;
                    for (BasicDruid[] lstT : listListD) {
                        System.out.println(nt++ + ") команда: ");
                        IntStream.range(0, lstT.length).forEach(i -> System.out.println("\t" + (i + 1) + ") " +
                                lstT[i].getType() + " " + lstT[i].getName()));
                    }
                    System.out.println((listListD.size() + 1) + ") повернення у меню");
                    maxSize = listListD.size() + 1;
                    first = safeScanInt(maxSize);
                    if (first == maxSize) break;

                    do {
                        second = safeScanInt(maxSize);
                    } while (second == first);

                    if (second == maxSize) break;

                    Arrays.stream(listListD.get(first - 1)).forEach(BasicDruid::reset);
                    Arrays.stream(listListD.get(second - 1)).forEach(BasicDruid::reset);

                    TeamVsTeam batlt = new TeamVsTeam(listListD.get(first - 1), listListD.get(second - 1), path);

                    batlt.battle();
                    FileScan.create(path);
                    FileScan.printAll();
                    FileScan.close();
                    break;

                case 6:
                    break cykl;

            }

        }
    }

    public int safeScanInt() {
        return safeScanInt(Integer.MAX_VALUE);
    }

    public int safeScanInt(int max) {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                if (n > 0 && n <= max)
                    return n;
            } catch (Exception e) {
            }
            System.out.print("Некоректно введено! Попробуйте ще раз: ");
        }
    }

    private void changeFile(String file){
        this.path = file;
    }

    private int chooseDruid(){
        System.out.print("Виберіть тип друіда:\n" +
                "1 - берсерк\n" +
                "2 - танк\n" +
                "3 - хіллер\n" +
                "4 - чародій              ---------> ");

        return safeScanInt(4);
    }

    private BasicDruid scanDruid(){
        System.out.print("Введіть ім'я друіда: ");
        String name = sc.nextLine();

        return returnerTypeDruid(chooseDruid(), name);
    }

    private BasicDruid returnerTypeDruid(int n, String name){
        switch (n){
            case 1:
                return new StrongDruid(name);

            case 2:
                return new TankDruid(name);

            case 3:
                return new HillerDruid(name);

            case 4:
                return new WizardDruid(name);
        }
        return null;
    }
}
