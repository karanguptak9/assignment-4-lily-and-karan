package FirstGame;

abstract public class TankStats extends MovableObject {
	private int life;
	private int score;

	protected TankStats(int x, int y, GameWorld game) {
		super(x, y, game);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
