
package SecondGame;


import java.awt.event.KeyEvent;
import java.util.Observable;


public class GameEvents extends Observable
{

    private int type;
    private Object caller, target;

    public void setCollision(GameObject caller, GameObject target)
    {
        type = 1;
        this.caller = caller;
        this.target = target;

        setChanged();

        this.notifyObservers(this);
    }

    public void setKeys(KeyEvent key)
    {
        type = 2;
        this.target = key;
        setChanged();

        notifyObservers(this);
    }
       
    public int getType()
    {
        return type;
    }

    public Object getCaller()
    {
        return caller;
    }

    public Object getTarget()
    {
        return target;
    }
}