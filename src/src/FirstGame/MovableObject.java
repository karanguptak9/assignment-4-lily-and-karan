package FirstGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observer;

abstract class TankObject {
	private int x, y;
	private GameWorld game;

	protected TankObject(int x, int y, GameWorld game) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	abstract public void draw(Graphics g);

	abstract public Rectangle getRec();

	protected int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	protected GameWorld getGame() {
		return game;
	}
}


abstract class SingleObject extends TankObject implements Observer {
	private boolean damaged;

	protected SingleObject(int x, int y, GameWorld game) {
		super(x, y, game);
		game.getObservable().addObserver(this);
	}

	protected boolean isDamaged() {
		return damaged;
	}

	protected void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
}

abstract public class MovableObject extends SingleObject {
	private int SPEED;
	private int oldX, oldY;
	private int directionIndex;

	protected MovableObject(int x, int y, GameWorld game) {
		super(x, y, game);
		oldX = super.getX();
		oldY = super.getY();
	}

	abstract protected void move();

	protected int getSpeed() {
		return SPEED;
	}

	protected void setSpeed(int speed) {
		this.SPEED = speed;
	}

	protected int getOldX() {
		return oldX;
	}

	protected void setOldX(int oldX) {
		this.oldX = oldX;
	}

	protected int getOldY() {
		return oldY;
	}

	protected void setOldY(int oldY) {
		this.oldY = oldY;
	}

	protected int getDirectionIndex() {
		return directionIndex;
	}

	protected void setDirectionIndex(int directionIndex) {
		this.directionIndex = directionIndex;
	}
}

