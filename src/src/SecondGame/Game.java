
package SecondGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JApplet;

abstract public class Game extends JApplet implements Runnable
{
    private BufferedImage bimg;
    private Thread thread;
    
    @Override
    public void init()
    {
        initSound();
        
        initImages();
    }
    
    abstract public void initSound();
    abstract public void initImages();
    abstract public void drawAll(int w, int h, Graphics2D g2);
    
    public Image getSprite(String name)
    {
        URL url = this.getClass().getResource(name);
        Image img = getToolkit().getImage(url);
        try
        {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return img;
    }

    public BufferedImage getBufferedSprite(String name){
        Image img = getSprite(name);
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
        bGr.dispose();
        return bimage;
    }
    
    public Graphics2D createGraphics2D(int w, int h)
    {
        Graphics2D g2;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h)
        {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }
    
    @Override
    public void paint(Graphics g)
    {
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        drawAll(d.width, d.height, g2);
        g2.dispose();
        g.drawImage(bimg, 0, 0, this);
    }
    
    @Override
    public void run()
    {
        Thread me = Thread.currentThread();
        setFocusable(true);
        while (thread == me)
        {
            repaint();
            try
            {
                Thread.sleep(25);
            } catch (InterruptedException e)
            {
                break;
            }
        }
    }
    
    @Override
    public void start()
    {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
}
