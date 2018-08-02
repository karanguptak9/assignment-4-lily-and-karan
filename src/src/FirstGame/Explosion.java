package FirstGame;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Explosion extends TankObject {
	private boolean dead = false;
	private int step = 0;
	private boolean large;



	public Explosion(int x, int y, GameWorld game, boolean large) {
		super(x, y, game);
		this.large = large;
	}

	@Override
	public void draw(Graphics g) {
		if (dead) {
			super.getGame().explosions.remove(this);
			return;
		}

		if ((large && step >= 7) || (!large && step >= 6)) {
			dead = true;
			return;
		}
		step++;
	}

	@Override
	public Rectangle getRec() {
		return null;
	}
}
