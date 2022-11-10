package battle;

import colors.TextColors;
import druid.BasicDruid;
import druid.HillerDruid;
import druid.WizardDruid;
import fileWork.FilePrint;

import java.util.stream.IntStream;

/** Дуель між двома друїдами */
public class OneVsOne {
    private BasicDruid drWhite, drBlack;

    public OneVsOne(BasicDruid r1, BasicDruid r2, String path){
        drWhite = r1;
        drBlack = r2;
        FilePrint.create(path);
    }

    public void battle(){
        move(drWhite, drBlack);
        if (anybodyWon()) return;
        FilePrint.print("");

        move(drBlack, drWhite);
        if (anybodyWon()) return;
        FilePrint.print("");

        battle();
    }

    private void move(BasicDruid dr, BasicDruid enemy){
        if (dr.getClass() == WizardDruid.class)
            IntStream.range(0,2).forEach(i -> dr.makeMove(enemy));

        else {
            if (dr.getClass() == HillerDruid.class)
                dr.wasHilled(((HillerDruid) dr).getHillKoef());
            dr.makeMove(enemy);
        }
    }

    private boolean anybodyWon(){
        if (drWhite.isDied() || drBlack.isDied()){
            boolean whiteWinner = drBlack.isDied();
            FilePrint.print(TextColors.CYAN +
                    (whiteWinner ? (drWhite.toString()+ " (Біла ") : (drBlack.toString() + " (Чорна ")) +
                    "команда) - перемога" + TextColors.RESET);
            FilePrint.close();
            return true;
        }
        return false;
    }

}