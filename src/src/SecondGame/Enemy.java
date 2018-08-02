
package SecondGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class Enemy extends SingleObject {

    private int type = 0;
    private int fulldamage;
    private int speed;
    private Level level = new Level();
    private ArrayList<ArrayList> design;
    private Stats degree = new Stats();


    public Enemy(int x, int y, int imgTick, int speed, Image[] img, GameEvents events,int fulldamage, int damageto,
                 ArrayList<ArrayList> design, int type, Level level ,Stats degree) {
        super(x, y, imgTick, speed, img, events, fulldamage, damageto);
        this.speed = speed;
        this.type = type;
        this.level = level;
        this.fulldamage = fulldamage;
        this.degree.setStats(0);

    }

    @Override
    public void BrickCollision(GameObject caller) {
        if (caller instanceof Brick) {
            fulldamage -= ((Brick) caller).getDamageTo();
            if (fulldamage <= 0) {
                this.setDone(true);

            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        GameEvents events = (GameEvents) arg;
        if(events.getType() == 1)
        {
            if(events.getTarget() == this)
            {
                if (events.getCaller() instanceof SingleObject) {
                    ((SingleObject) events.getCaller()).hit(this);
                }
                BrickCollision((GameObject)events.getCaller());
            }
        }

    }

    @Override
    public void update(int w, int h)
    {
        super.update(w, h);
        this.increaseImgTick();
    }

    @Override
    public void move() {
        if(type == 1) {
            int widthChange = (int)(Math.cos(degree.getStats())* getSpeed());
            this.setX(this.getX() + widthChange);
            if(this.getX() >= 460) {
                degree.setStats(Math.PI);
            } else if (this.getX() <= 170) {
                degree.setStats(0.0);
            } else {
                return;
            }
        }


    }

    @Override
    public void dead() {
        this.setRDone(true);
    }

}
