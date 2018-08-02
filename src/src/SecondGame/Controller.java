
package SecondGame;

import java.awt.event.*;

public class Controller extends KeyAdapter
{

    private GameEvents events;

    public Controller(GameEvents events)
    {
        this.events = events;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        events.setKeys(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        events.setKeys(e);
    }
}
