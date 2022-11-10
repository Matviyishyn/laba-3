package druid;

import colors.TextColors;
import fileWork.FilePrint;

/** Сильний друїд, хоча моє досить малий запас здоров'я
 * Вдобавок у сильного друїда є берсекрмод який збільшує урон
 * */
public class StrongDruid extends BasicDruid {

    private double bersercMode = 1.3;

    public StrongDruid(String name) {
        this.type = "berserc";
        this.damage = 65;
        this.name = name;
        this.accuracy = 0.8;
        reset();
    }

    public void makeMove(BasicDruid enemy){
        if (health <= 70)
            enemy.gotDamage((this.Damage(bersercMode)));
        else
            enemy.gotDamage(this.Damage());
    }

    @Override
    public void wasHilled(double koef) {
        FilePrint.print(TextColors.BLUE + "Друід " + type + " " + name + " вилікувався на " + (int) (koef * 200) + " hp");
        health += (int) (koef * 200);
    }

    public void reset(){
        this.health = 200;
    }

}
