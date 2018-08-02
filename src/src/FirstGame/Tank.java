package FirstGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Observable;

public class Tank extends TankStats {
	private static final int INITIALLIFE = 100;
	private static final int INITIALSCORE = 0;
	private static int WIDTH, HEIGHT;
	private boolean good;
	private Bar bar = new Bar();
	private BufferedImage [] tankImg = new BufferedImage[60];
	private boolean dirU = false, dirD = false, dirL = false, dirR = false;
	private int clock = 10;
	private static final int INITIALSPEED = 5;
	private boolean superF = false;

	public Tank(int x, int y, boolean good, GameWorld game) {
		super(x, y, game);
		super.setSpeed(INITIALSPEED);
		this.good = good;
		super.setOldX(x);
		super.setOldY(y);
		super.setLife(INITIALLIFE);
		super.setScore(INITIALSCORE);

		try {
			BufferedImage tempImg = good ? ImageIO.read(GameWorld.class.getResource("Resources/Tank_blue_basic_strip60.png")) : ImageIO.read(GameWorld.class.getResource("Resources/Tank_red_basic_strip60.png"));
			for (int i = 0, locationX = 0, locationY = 0, width = tempImg.getWidth() / 60, height = tempImg.getHeight(); i < 60; i++) {
				tankImg[i] = tempImg.getSubimage(locationX, locationY, width, height);
				locationX += width;
			}
			WIDTH = tankImg[0].getWidth();
			HEIGHT = tankImg[0].getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void draw(Graphics g) {
		if (super.isDamaged())  {
			clock--;
			if (clock == 0) {
				clock = 10;
				super.setDamaged(false);
				super.setLife(INITIALLIFE);
				int x = good ? 230 : 1060;
				super.setX(x);
				int y = good ? 220 : 1020;
				super.setY(y);
				super.setOldX(x);
				super.setOldY(y);
				super.setDirectionIndex(0);
			}
			return;
		}

		move();
		bar.draw(g);

		int directionIndex = super.getDirectionIndex();
		if (directionIndex < 0) {
			super.setDirectionIndex(directionIndex + 60);
		}

		g.drawImage(tankImg[super.getDirectionIndex()], super.getX(), super.getY(), null);

	}



	@Override
	public void update(Observable obj, Object arg) {
		GameEvents ge = (GameEvents) arg;
		if (1 == ge.getType()) {
			KeyEvent e = (KeyEvent) ge.getEvent();
			int directionIndex = super.getDirectionIndex();
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (good) {
						super.setDirectionIndex((++directionIndex) % 60);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (good) {
						super.setDirectionIndex((--directionIndex) % 60);
					}
					break;
				case KeyEvent.VK_UP:
					if (good) {
						int speed = super.getSpeed();
						int x = super.getX();
						x += Math.cos(Math.toRadians(directionIndex * 6)) * speed;
						super.setX(x);

						int y = super.getY();
						y -= Math.sin(Math.toRadians(directionIndex * 6)) * speed;
						super.setY(y);
					}
					break;
				case KeyEvent.VK_DOWN:
					if (good) {
						int speed = super.getSpeed();
						int x = super.getX();
						x -= Math.cos(Math.toRadians(directionIndex * 6)) * speed;
						super.setX(x);

						int y = super.getY();
						y += Math.sin(Math.toRadians(directionIndex * 6)) * speed;
						super.setY(y);
					}
					break;
				case KeyEvent.VK_A:
					if (!good) {
						dirL = true;
					}
					break;
				case KeyEvent.VK_D:
					if (!good) {
						dirR = true;
					}
					break;
				case KeyEvent.VK_W:
					if (!good) {
						dirU = true;
					}
					break;
				case KeyEvent.VK_S:
					if (!good) {
						dirD = true;
					}
					break;
			}
		} else if (3 == ge.getType()) {
			KeyEvent e = (KeyEvent) ge.getEvent();
			int directionIndex = super.getDirectionIndex();
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					if (!good) {
						dirL = false;
					}
					break;
				case KeyEvent.VK_D:
					if (!good) {
						dirR = false;
					}
					break;
				case KeyEvent.VK_W:
					if (!good) {
						dirU = false;
					}
					break;
				case KeyEvent.VK_S:
					if (!good) {
						dirD = false;
					}
					break;
				case KeyEvent.VK_ENTER:
					if (!good) {
						fire();
					}
					break;

				case KeyEvent.VK_C:
					if (!good) {
						superFire();
					}

					break;
				case KeyEvent.VK_SPACE:
					if (good) {
						fire();
					}
					break;

				case KeyEvent.VK_CONTROL:
					if (good) {
						superFire();
					}
					break;
			}
		} else if (2 == ge.getType()
						&& (this == ge.getCaller() && (ge.getTarget() instanceof Wall || ge.getTarget() instanceof DestructableWall)
						|| (ge.getCaller() instanceof Tank && this == ge.getTarget())
		)) {
			stay();
		} else if (2 == ge.getType() && this == ge.getTarget() && ge.getCaller() instanceof Bullet) {
			int life = super.getLife();
			super.setLife(life - 20);
			if (super.getLife() <= 0) {
				super.setDamaged(true);
				GameWorld game = super.getGame();
				game.explosions.add(new Explosion(super.getX(), super.getY(), game, true));
				Tank tank = good ? game.tank2 : game.tank1;
				int score = tank.getScore();
				tank.setScore(score + 1);
			}
		}
	}

	private void stay() {
		super.setX(super.getOldX());
		super.setY(super.getOldY());
	}

	@Override
	protected void move() {
		int x = super.getX();
		int y = super.getY();
		super.setOldX(x);
		super.setOldY(y);

		int directionIndex = super.getDirectionIndex();
		int speed = super.getSpeed();
		if (!good) {
			if (dirU) {
				x += Math.cos(Math.toRadians(directionIndex * 6)) * speed;
				y -= Math.sin(Math.toRadians(directionIndex * 6)) * speed;
			}
			if (dirD) {
				x -= Math.cos(Math.toRadians(directionIndex * 6)) * speed;
				y += Math.sin(Math.toRadians(directionIndex * 6)) * speed;
			}
			super.setX(x);
			super.setY(y);

			if (dirL) {
				super.setDirectionIndex((++directionIndex) % 60);
			}
			if (dirR) {
				super.setDirectionIndex((--directionIndex) % 60);
			}
		}

		x = super.getX();
		y = super.getY();
		GameWorld game = super.getGame();
		if (x < 0) {
			super.setX(0);
		}
		if (y < 30) {
			super.setY(30);
		}
		if (x + WIDTH > game.getGamewidth()) {
			super.setX(game.getGamewidth() - WIDTH);
		}
		if (y + HEIGHT > game.getGameheight()) {
			super.setY(game.getGameheight() - HEIGHT);
		}
	}

	private void fire() {
		if (super.isDamaged()) return;
		int bx = super.getX() + WIDTH /2 - Bullet.getWIDTH() / 2;
		int by = super.getY() + HEIGHT / 2 - Bullet.getHEIGHT() / 2;
		GameWorld game = super.getGame();
		game.bullets.add(new Bullet(bx, by, super.getDirectionIndex(), good, game));
	}

	@Override
	public Rectangle getRec() {
		return new Rectangle(super.getX(), super.getY(), WIDTH, HEIGHT);
	}

	@Override
	public void setDamaged(boolean damaged) {
		super.setDamaged(damaged);
		GameWorld game = super.getGame();
		if (good) {
			int score = game.tank2.getScore();
			game.tank2.setScore(score + 1);
		} else {
			int score = game.tank1.getScore();
			game.tank1.setScore(score + 1);
		}
	}

	public boolean isGood() {
		return good;
	}

	public void superFire() {
		if (superF) {
			if (super.isDamaged()) return;
			int bx = super.getX() + WIDTH /2 - Bullet.getWIDTH() / 2;
			int by = super.getY() + HEIGHT / 2 - Bullet.getHEIGHT() / 2;
			GameWorld game = super.getGame();
			game.bullets.add(new Bullet(bx, by, super.getDirectionIndex(), good, game, true));
		}
	}

	private class Bar {
		private void draw(Graphics g) {
			Color color = g.getColor();
			g.setColor(Color.GREEN);
			g.drawRect(Tank.super.getX(), Tank.super.getY() - 4, WIDTH, 3);
			int w = WIDTH * Tank.super.getLife() / 100;
			g.fillRect(Tank.super.getX(), Tank.super.getY() - 4, w, 3);
			g.setColor(color);
		}
	}

}

