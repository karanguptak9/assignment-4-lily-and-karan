
package SecondGame;


import java.awt.Image;
import java.util.*;

abstract public class SingleObject extends GameObject implements Observer
{

    private int damage;
    private int fulldamage;
    private int damageto;


    public SingleObject(int x, int y, int imgTick, int speed, Image[] img,
            GameEvents events, int fulldamage, int damageto)
    {
        super(x, y, imgTick, speed, img, events);
        this.damage = 0;
        this.fulldamage = fulldamage;
        this.damageto = damageto;
        events.addObserver(this);
    }
    abstract public void BrickCollision(GameObject caller);

    @Override
    public void hit(SingleObject u)
    {
        u.changeDamage(getDamageTo());
    }


    
    public int getDamage()
    {
        return damage;
    }
    
    public void changeDamage(int change)
    {
        damage += change;
    }
    
    public void setDamage(int damage)
    {
        this.damage = damage;
    }
    
    public int getMax()
    {
        return fulldamage;
    }

    public void setMax(int fulldamage)
    {
        this.fulldamage = fulldamage ;
    }
    
    public int getDamageTo()
    {
        return damageto;
    }
}
