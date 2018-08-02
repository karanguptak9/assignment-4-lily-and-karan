package FirstGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

import javax.imageio.ImageIO;

public class Bullet extends MovableObject {
	private static final int SPEED = 10;
	public static int WIDTH, HEIGHT;
	private boolean good;
	private static BufferedImage [] bulletImg = new BufferedImage[60];
	private static Sound sp;
	private static BufferedImage [] bigBulletImg = new BufferedImage[60];
	private boolean big = false;

	static {
		try {
			BufferedImage tempImg = ImageIO.read(GameWorld.class.getResource("Resources/Shell_light_strip60.png"));
			for (int i = 0, locationX = 0, locationY = 0, width = tempImg.getWidth() / 60, height = tempImg.getHeight(); i < 60; i++) {
				bulletImg[i] = tempImg.getSubimage(locationX, locationY, width, height);
				locationX += width;
			}
			WIDTH = bulletImg[0].getWidth();
			HEIGHT = bulletImg[0].getHeight();

			tempImg = ImageIO.read(GameWorld.class.getResource("Resources/Shell_basic_strip60.png"));
			for (int i = 0, locationX = 0, locationY = 0, width = tempImg.getWidth() / 60, height = tempImg.getHeight(); i < 60; i++) {
				bigBulletImg[i] = tempImg.getSubimage(locationX, locationY, width, height);
				locationX += width;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Bullet(int x, int y, int directionIndex, boolean good, GameWorld game) {
		super(x, y, game);
		super.setSpeed(SPEED);
		this.good = good;
		super.setDirectionIndex(directionIndex);
		sp = new Sound(2, "Resources/Explosion_small.wav");
	}

	public Bullet(int x, int y, int directionIndex, boolean good, GameWorld game, boolean big) {
		this(x, y, directionIndex, good, game);
		this.big = big;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(big ? bigBulletImg[super.getDirectionIndex()] : bulletImg[super.getDirectionIndex()], super.getX(), super.getY(), null);

		move();
	}

	@Override
	protected void move() {
		int speed = super.getSpeed();
		int x = super.getX();
		x += Math.cos(Math.toRadians(super.getDirectionIndex() * 6)) * speed;
		super.setX(x);

		int y = super.getY();
		y -= Math.sin(Math.toRadians(super.getDirectionIndex() * 6)) * speed;
		super.setY(y);

		x = super.getX();
		y = super.getY();
		if (x < 0 || y < 0 || x > super.getGame().getGamewidth() || y > super.getGame().getGameheight()) {
			super.setDamaged(true);
		}
	}

	@Override
	public Rectangle getRec() {
		return new Rectangle(super.getX(), super.getY(), WIDTH, HEIGHT);
	}

	@Override
	public void update(Observable obj, Object arg) {
		GameEvents ge = (GameEvents) arg;
		if (2 == ge.getType() && this == ge.getCaller()) {
			sp.play();
			GameWorld game = super.getGame();
			boolean b = big ? true : false;
			game.explosions.add(new Explosion(super.getX(), super.getY(), game, b));
			super.setDamaged(true);
			super.getGame().bullets.remove(this);
		}
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public boolean isGood() {
		return good;
	}


}
