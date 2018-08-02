
package SecondGame;


import java.awt.Graphics2D;

public class Draw implements DrawType
{
    @Override
    public void drawThis(GameObject thing, Graphics2D g2, GameWorld gs)
    {
        thing.draw(g2, gs);
    }
}
