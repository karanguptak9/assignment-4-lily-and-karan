package FirstGame;
import java.util.List;

public class CollisionDetector {
	GameEvents gameEvents;
	GameWorld game;
	
	public CollisionDetector(GameWorld game) {
		this.game = game;
		gameEvents = game.getObservable();
	}
	
	public void collideWith(SingleObject caller, TankObject target) {
		if (!caller.isDamaged() && caller.getRec().intersects(target.getRec())) {
			if (caller instanceof Bullet && target instanceof Tank) {
				Bullet bullet = (Bullet) caller;
				Tank tank = (Tank) target;
				if (bullet.isGood() == tank.isGood()) {
					return;
				}
			}
			
			if (caller instanceof Bullet || caller instanceof Tank) {
				gameEvents.setValue(caller, target);
			}
		}
	}
	
	public void collideWith(SingleObject caller, List<? extends TankObject> things) {
		for (int i = 0; i < things.size(); i++) {
			collideWith(caller, things.get(i));
		}
	}
}

