package SecondGame;



import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.*;

abstract public class Player extends SingleObject implements Observer
{
    int left, right, up, down, fire, spfire;
    boolean mvLeft, mvRight, mvUp, mvDown, isFiring, isSp;
    
    int power;
    int shotDelay;
    int shotTime;
    int fastShotTime;
    int deadTime;
    GameWorld gameSpace;
    Player otherPlayer;
    int type;
    
    public Player(int x, int y, int imgTick, int speed, Image[] img,
            GameEvents events, int fulldamage, int damageto,
            int left, int right, int up, int down, int fire, int spfire,
            int shotTime, int fastShotTime, int deadTime, GameWorld gameSpace, int type)
    {
        super(x, y, imgTick, speed, img, events, fulldamage, damageto);
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.fire = fire;
        this.spfire = spfire;
        this.shotTime = shotTime;
        this.fastShotTime = fastShotTime;
        this.deadTime = deadTime;
        shotDelay = 0;
        power = 0;
        mvLeft = false;
        mvRight = false;
        mvUp = false;
        mvDown = false;
        isFiring = false;
        isSp = false;
        this.gameSpace = gameSpace;
        this.type = type;
    }

    @Override
    public void update(Observable o, Object arg)
    {

        GameEvents events = (GameEvents) arg;
        if(events.getType() == 1)
        {        
            if(events.getTarget() == this)
            {
                BrickCollision((GameObject)events.getCaller());
            }
        }
        else if(events.getType() == 2)
        {
            controls((KeyEvent)events.getTarget());
        }
    }
    public void controls(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == left || key == right) {
            keyRotate(e);
        }
        else if(key == up || key == down) {
            keyMove(e);
        }
        else if(key == fire || key == spfire)
        {
            keyFire(e);
        }
    }

    public void keyMove(KeyEvent key)
        {
            if (key.getID() == KeyEvent.KEY_PRESSED)
            {
                if (key.getKeyCode() == getUp() && !mvUp)
                {
                    mvUp = true;
                } else if (key.getKeyCode() == getDown() && !mvDown)
                {
                    mvDown = true;
                }
            }
            
            if (key.getID() == KeyEvent.KEY_RELEASED)
            {
                if (key.getKeyCode() == getUp() && mvUp)
                {
                    mvUp = false;
                } else if (key.getKeyCode() == getDown() && mvDown)
                {
                    mvDown = false;
                }
            }
        }
    public void keyRotate(KeyEvent key) {

        if (key.getID() == KeyEvent.KEY_PRESSED)
        {
            if (key.getKeyCode() == getLeft() && !mvLeft)
            {
                mvLeft = true;
            } else if (key.getKeyCode() == getRight() && !mvRight)
            {
                mvRight = true;
            }
        }

        if (key.getID() == KeyEvent.KEY_RELEASED)
        {
            if (key.getKeyCode() == getLeft() && mvLeft)
            {
                mvLeft = false;
            } else if (key.getKeyCode() == getRight() && mvRight)
            {
                mvRight = false;
            }
        }
    }
    
    public void keyFire(KeyEvent key)
        {
            if(key.getID() == KeyEvent.KEY_PRESSED)
            {
                if(key.getKeyCode() == getFire() && !isFiring)
                {
                    isFiring = true;
                }
                
                if(key.getKeyCode() == getSpFire() && !isSp)
                {
                    isSp = true;
                }
            }
            
            if(key.getID() == KeyEvent.KEY_RELEASED)
            {
                if(key.getKeyCode() == getFire() && isFiring)
                {
                    isFiring = false;
                }
                
                if(key.getKeyCode() == getSpFire() && isSp)
                {
                    isSp = false;
                }
            }

        }


    public int getPower()
    {
        return power;
    }
    
    public void setPower(int power)
    {
        this.power = power;
    }
    
    public int getShotDelay()
    {
        return shotDelay;
    }
    
    public void setShotDelay(int delay)
    {
        this.shotDelay = delay;
    }
    
    public void changeShotDelay(int change)
    {
        this.shotDelay += change;
    }
    
    public int getShotTime()
    {
        return shotTime;
    }
    
    public int getFastShotTime()
    {
        return fastShotTime;
    }
    
    public int getLeft()
    {
        return left;
    }
    
    public int getRight()
    {
        return right;
    }
    
    public int getUp()
    {
        return up;
    }
    
    public int getDown()
    {
        return down;
    }
    
    public int getFire()
    {
        return fire;
    }
    
    public int getSpFire()
    {
        return spfire;
    }
    
    public boolean getMvLeft()
    {
        return mvLeft;
    }
    
    public boolean getMvRight()
    {
        return mvRight;
    }
    
    public boolean getMvUp()
    {
        return mvUp;
    }
    
    public boolean getMvDown()
    {
        return mvDown;
    }
    
    public boolean getIsFiring()
    {
        return isFiring;
    }
    
    public boolean getIsSp()
    {
        return isSp;
    }

    public GameWorld gameSpace() {
        return gameSpace;
    }

    public void setOtherPlayer(Player playerParent) {
        this.otherPlayer = playerParent;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public int getType() {
        return type;
    }
}
