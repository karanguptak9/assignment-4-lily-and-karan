package SecondGame;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;

abstract public class GameObject
{
    private int locationx, locationy;

    private int speed;

    private Image[] img;

    private boolean done;

    private boolean reallyDone;

    private GameEvents events;

    private int imgTick;

    
    public GameObject(int locationx, int locationy, int imgTick, int speed, Image[] img,
            GameEvents events)
    {
        this.locationx = locationx;
        this.locationy = locationy;
        this.speed = speed;
        this.img = img;
        this.events = events;
        done = false;
        reallyDone = false;
        this.imgTick = imgTick;
    }

    public void draw(Graphics2D g2, ImageObserver obs)
    {
        int width = img[imgTick].getWidth(obs);
        int height = img[imgTick].getHeight(obs);
        AffineTransform trans = new AffineTransform();

        trans.translate(locationx - width / 2, locationy - height / 2);

        if (!done)
        {
            g2.drawImage(img[imgTick], trans, obs);
        }

    }

    public void update(int width, int height)
    {
        if (done)
        {
            dead();
        } else
        {
            action();

            move();
        }
    }

    abstract public void move();

    abstract public void dead();
    
    abstract public void hit(SingleObject u);

    public void action()
    {
    }

    public boolean collision(GameObject thing)
    {
        if(getDone())
        {
            return false;
        }

        return isOverlap(thing, thing.getX(), thing.getY());
    }

    public void setX(int locationx)
    {
        this.locationx = locationx;
    }

    public int getX()
    {
        return locationx;
    }

    public void changeX(int change)
    {
        this.locationx += change;
    }

    public void setY(int locationy)
    {
        this.locationy = locationy;
    }

    public int getY()
    {
        return locationy;
    }

    public void changeY(int change)
    {
        this.locationy += change;
    }

    public int getSpeed()
    {
        return speed;
    }

    public Image getImage()
    {
        return img[imgTick];
    }

    public void setDone(boolean done)
    {
        this.done = done;
    }
    
    public boolean getDone()
    {
        return done;
    }
    
    public void setRDone(boolean rdone)
    {
        reallyDone = rdone;
    }
    
    public boolean getRDone()
    {
        return reallyDone;
    }
    
    public int getHeight()
    {
        return img[imgTick].getHeight(null);
    }
    
    public int getWidth()
    {
        return img[imgTick].getWidth(null);
    }
    
    public GameEvents getEvents()
    {
        return events;
    }

    public void increaseImgTick() {
        this.imgTick++;
        if (this.imgTick >= this.img.length) {
            this.imgTick = 0;
        }
    }

    public void decreaseImgTick() {
        this.imgTick--;
        if (this.imgTick < 0) {
            this.imgTick = this.img.length-1;
        }
    }

    public int getImgTick() {
        return imgTick;
    }

    public boolean isOverlap(GameObject thing, int locationx, int locationy) {


        int radius1 = thing.getHeight()/2;
        int radius2 = this.getHeight()/2;
        for (int offset = 0; offset < this.getWidth()/2 ;offset += this.getHeight()/2) {
            double distance = Math.sqrt(Math.pow((double)(this.locationx + offset - locationx),2.0) + Math.pow((double)(this.locationy - locationy),2.0));
            if (distance < radius1 + radius2) {
                return true;
            }
            distance = Math.sqrt(Math.pow((double)(this.locationx - offset - locationx),2.0) + Math.pow((double)(this.locationy - locationy),2.0));
            if (distance < radius1 + radius2) {
                return true;
            }
        }
        return false;

    }

}