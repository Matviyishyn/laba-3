package battle;
import colors.TextColors;
import druid.*;
import fileWork.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/** Бій команда на команду */
public class TeamVsTeam {

    BasicDruid[] whiteTeam;
    BasicDruid[] blackTeam;
    private int roundN = 1;


    public TeamVsTeam(BasicDruid[] white, BasicDruid[] black, String path) {
        whiteTeam = white.clone();
        blackTeam = black.clone();

        int delta = Math.abs(whiteTeam.length - blackTeam.length);
        double bufAttack = delta * 1.22;
        double bufHealth = delta * 1.14;

        if (whiteTeam.length > blackTeam.length){
            Arrays.stream(blackTeam).forEach(x -> x.wasBaffed(bufAttack,bufHealth));
        }
        else if (whiteTeam.length < blackTeam.length) {
            Arrays.stream(whiteTeam).forEach(x -> x.wasBaffed(bufAttack,bufHealth));
        }

        FilePrint.create(path);
    }

    public void battle(){
        FilePrint.print("------------------------------------------------\nБіла команда атакує\n");
        if (move(whiteTeam, blackTeam)) return;
        FilePrint.print("Інформація про чорну команду:");
        printInfoTeam(blackTeam);

        FilePrint.print("------------------------------------------------\nЧорна команда атакує\n");
        if (move(blackTeam, whiteTeam)) return;
        FilePrint.print("Інформація про білу команду:");
        printInfoTeam(whiteTeam);
        FilePrint.print("------------------------------------------------\n" + TextColors.BLUE +
                (roundN++) + " раунд закінчився. Інформація про команди:" + TextColors.RESET);
        printInfoTeams();

        battle();
    }

    private boolean move(BasicDruid[] alldruids, BasicDruid[] enemy) {
        BasicDruid[] druidsAlive = Arrays.stream(alldruids).filter(x -> !x.isDied()).toArray(BasicDruid[] :: new);

        for (BasicDruid dr : druidsAlive) {
            if (dr.getClass() == WizardDruid.class) {
                if (Arrays.stream(enemy).filter(x -> !x.isDied()).count() == 1) {
                    BasicDruid e = Arrays.stream(enemy).filter(x -> !x.isDied()).findFirst().get();
                    IntStream.range(0, 2).forEach(x -> dr.makeMove(e));
                }
                else {
                    Comparator<BasicDruid> cp = Comparator.comparing(BasicDruid::getHealth);
                    Arrays.stream(enemy).filter(x -> !x.isDied()).sorted(cp).limit(2).forEach(dr::makeMove);
                }

            } else {
                if (dr.getClass() == HillerDruid.class)
                    Arrays.stream(druidsAlive).filter(x -> !x.isDied()).forEach(x -> x.wasHilled(((HillerDruid) dr).getHillKoef()));

                Arrays.stream(enemy).filter(x -> !x.isDied()).limit(1).forEach(dr::makeMove);
            }
            FilePrint.print("");
            if (anybodyWon()) return true;
        }
        return false;
    }

    private boolean anybodyWon(){
        boolean whiteWin = Arrays.stream(whiteTeam).anyMatch(x->!x.isDied());
        boolean blackWin = Arrays.stream(blackTeam).anyMatch(x->!x.isDied());

        if (!whiteWin || !blackWin){
            IntStream.range(0,3).forEach(x -> FilePrint.print("------------------------------------------------"));
            if (whiteWin)
                FilePrint.print(TextColors.CYAN +"Біла команда перемогла" + TextColors.RESET);
            else
                FilePrint.print(TextColors.CYAN + "Чорна команда перемогла" + TextColors.RESET);

            printInfoTeams();
            FilePrint.close();
            return true;
        }
        return false;
    }

    private void printInfoTeams(){
        FilePrint.print("Біла команда:");
        printInfoTeam(whiteTeam);

        FilePrint.print("\nЧорна команда:");
        printInfoTeam(blackTeam);
    }

    private void printInfoTeam(BasicDruid[] team){
        for (BasicDruid dr : team){
            if (dr.isDied())
                FilePrint.print(TextColors.RED + "Друід " + dr.getType() + " " + dr.getName() + " мертвий" + TextColors.RESET);
            else
                FilePrint.print(dr.toString());
        }
    }

}
