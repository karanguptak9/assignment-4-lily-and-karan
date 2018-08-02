
package FirstGame;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GameWorld extends JPanel implements Runnable {
	private Thread thread;
	private static final GameWorld game = new GameWorld();
	static final int WidthDisplayed = 1280;
	static final int HeightDisplayed = 1280;
	private BufferedImage bimg = null;
	private Graphics2D graphics;
	private Map map = Map.getInstance();
	private GameEvents gameEvents = new GameEvents();
	private CollisionDetector collisionDetector = new CollisionDetector(this);

	Tank tank1 = new Tank(520, 50, true, this);
	Tank tank2 = new Tank(520, 1160, false, this);
	List<Bullet> bullets = new ArrayList<Bullet>();
	List<Explosion> explosions = new ArrayList<Explosion>();
	List<TankObject> walls = new ArrayList<TankObject>();
	private Image background;
	private BufferedImage leftView, rightView, miniMap;
	private Sound sp;


	public void initiate() {
		try {
			background = ImageIO.read(GameWorld.class.getResource("Resources/Background.png"));
			sp = new Sound(1, "Resources/Music.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void drawBackground(Graphics g) {
		int tileWidth = background.getWidth(this);
		int tileHeight = background.getHeight(this);

		int numberX = (int) (WidthDisplayed / tileWidth);
		int numberY = (int) (HeightDisplayed / tileHeight);

		for (int i = 0; i <= numberY; i++) {
			for (int j = 0; j <= numberX; j++) {
				g.drawImage(background, j * tileWidth, i * tileHeight, tileWidth, tileHeight,  this);
			}
		}
	}

	private void addToWallList(List<TankObject> walls, List<Location> wallMap) {
		int size = wallMap.size();
		for (int i = 0; i < size; i++) {
			Location location = wallMap.get(i);
			int x = location.getX() * 32;
			int y = location.getY() * 32;
			TankObject singleWall = (location.getTankObject().equals("Wall")) ? new Wall(x, y, this) : new DestructableWall(x, y, this);
			walls.add(singleWall);
		}
	}

	private void getWallList() {
		map.read();
		List<Location> wallMap = map.getWallMap();
		addToWallList(walls, wallMap);
	}

	private void drawWall(Graphics g) {
		if (walls.isEmpty()) {
			getWallList();
		}

		for(int i = 0; i < walls.size(); i++) {
			walls.get(i).draw(g);
		}
	}

	private void drawDemo(Graphics g) {
		drawBackground(g);
		drawWall(g);

		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			collisionDetector.collideWith(bullet, tank1);
			collisionDetector.collideWith(bullet, tank2);
			collisionDetector.collideWith(bullet, walls);
		}
		collisionDetector.collideWith(tank1, tank2);
		collisionDetector.collideWith(tank1, walls);

		tank1.draw(g);
		tank2.draw(g);
	}

	public void paint(Graphics g) {
		if (bimg == null) {
			bimg = (BufferedImage) createImage(WidthDisplayed, HeightDisplayed);
			graphics = bimg.createGraphics();
		}
		drawDemo(graphics);
		try {
			int leftX = (tank1.getX() - 200 <= 0) ? 0 : (tank1.getX() - 200);
			if (leftX + 400 > WidthDisplayed) {
				leftX = WidthDisplayed - 401;
			}
			int leftY = (tank1.getY() - 240 <= 0) ? 0 : (tank1.getY() - 240);
			if (leftY + 480 > HeightDisplayed) {
				leftY = HeightDisplayed - 481;
			}
			leftView = bimg.getSubimage(leftX,leftY, 400, 480);

			collisionDetector.collideWith(tank2, tank1);
			collisionDetector.collideWith(tank2, walls);

			int rightX = (tank2.getX() - 200 <= 0) ? 0 : (tank2.getX() - 200);
			if (rightX + 400 > WidthDisplayed) {
				rightX = WidthDisplayed - 401;
			}
			int rightY = (tank2.getY() - 240 <= 0) ? 0 : (tank2.getY() - 240);
			if (rightY + 480 > HeightDisplayed) {
				rightY = HeightDisplayed - 481;
			}
			rightView = bimg.getSubimage(rightX, rightY, 400, 480);
			miniMap = bimg;
		} catch (RasterFormatException e) {
			e.printStackTrace();
		}
		g.drawImage(leftView, 0, 0, null);
		g.drawImage(rightView, 420, 0, null);
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(400, 0, 20, 480);

		Font font = g.getFont();
		g.setFont(new Font("Dialog", Font.BOLD, 50));
		g.setColor(Color.RED);
		g.drawString("" + tank1.getScore(), 350, 50);
		g.setColor(Color.BLUE);
		g.drawString("" + tank2.getScore(), 440, 50);
		g.setFont(font);

		g.setColor(color);
		g.drawImage(miniMap, 350, 335, 120, 120, null);
	}

	public void start() {
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public void run() {
		Thread me = Thread.currentThread();
		while (thread == me) {
			repaint();
			try {
				thread.sleep(50);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public static GameWorld getInstance(){
		return game;
	}

	public GameEvents getObservable() {
		return gameEvents;
	}

	public static int getGamewidth() {
		return WidthDisplayed;
	}

	public static int getGameheight() {
		return HeightDisplayed;
	}
}

class GameEvents extends Observable {
	private int type;
	private static final int KEY = 1;
	private static final int COLLISION = 2;
	private static final int RELEASED = 3;
	private Object event;
	private Object caller, target;
	private int [] number = new int[2];

	public void setValue(KeyEvent e) {
		type = KEY;
		event = e;
		setChanged();
		notifyObservers(this);
	}

	public void setKeys(KeyEvent e) {
		type = RELEASED;
		event = e;
		setChanged();
		notifyObservers(this);
	}

	public void setValue(TankObject caller, TankObject target) {
		type = COLLISION;
		this.caller = caller;
		this.target = target;
		setChanged();
		notifyObservers(this);
	}

	public void setValue(TankObject caller, TankObject target, int number) {
		this.number[0] = number;
		setValue(caller, target);
	}

	public void setValue(TankObject caller, TankObject target, int number1, int number2) {
		number[0] = number1;
		number[1] = number2;
		setValue(caller, target);
	}

	public int getType() {
		return type;
	}

	public Object getEvent() {
		return event;
	}

	public Object getCaller() {
		return caller;
	}

	public Object getTarget() {
		return target;
	}

	public int [] getNumber() {
		return number;
	}


	public static void main(String argv[]) {
		final GameWorld game = GameWorld.getInstance();
		JFrame f = new JFrame("TANK WAR");
		f.addKeyListener(new Controller(game.getObservable()));
		f.getContentPane().add("Center", game);
		f.pack();
		f.setSize(new Dimension(800, 480));
		game.initiate();
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.start();
	}

}




