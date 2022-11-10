package druid;

import colors.TextColors;
import fileWork.FilePrint;

/** Хіллер лікує себе і своїх союзників
 * Хіллер це єдиний друїд який робить два ходи, лікує свою команду а потім п'є палкою по голові суперника
 * */
public class HillerDruid extends BasicDruid {

    double hillKoef = 0.05;
    public HillerDruid(String name){
        this.type = "hiller";
        this.name = name;
        this.defense = 1.05;
        this.accuracy = 0.9;
        this.damage = 30;
        reset();
    }

    @Override
    public void wasHilled(double koef) {
        FilePrint.print(TextColors.BLUE + "Друід " + type + " " + name + " вилікувався на " + (int) (koef * 200) + " hp" + TextColors.RESET);
        health += (int) (koef * 200);
    }

    public double getHillKoef() {
        return hillKoef;
    }

    public void reset(){
        this.health = 200;
    }

}
