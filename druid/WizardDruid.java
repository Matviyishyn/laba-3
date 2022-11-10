package druid;

import colors.TextColors;
import fileWork.FilePrint;

/** Чародій може кидати вогняні кулі у 2 суперників з найменшим здоров'ям
 * Це єдиний друід який може атакувати зразу дві цілі якщо у нього достатньо мани
 * Якщо мани недостатньо, то він пропускає один з ходів, щоб її накопити
 * */
public class WizardDruid extends BasicDruid {

    int mana = 200;
    public WizardDruid(String name){
        this.type = "wizard";
        this.damage = 32;
        this.name = name;
        this.defense = 1.05;
        this.accuracy = 0.7;
        reset();
    }

    @Override
    public void makeMove(BasicDruid enemy) {
        if (mana > 35) {
            FilePrint.print(TextColors.YELLOW + "Друід " + type + " " + name + " говорить заклинання вогняної кулі" + TextColors.RESET);
            enemy.gotDamage(this.Damage());
            mana -= 35;
        }
        else
            restoringMana();
    }

    private void restoringMana(){
        mana += 90;
        FilePrint.print(TextColors.PURPLE + "Друід " + type + " " + name + " відновив 90 мани, тепер його мана = " + mana + TextColors.RESET);
    }

    @Override
    public void wasHilled(double koef) {
        FilePrint.print(TextColors.BLUE + "Друід " + type + " " + name + " вилікувався на " + (int) (koef * 200) + " hp");
        health += (int) (koef * 225);
    }

    public void reset(){
        this.health = 225;
    }

}
