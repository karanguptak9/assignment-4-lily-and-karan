package FirstGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Controller extends KeyAdapter {
	private GameEvents gameEvents;

	public Controller(GameEvents gameEvents) {
		this.gameEvents = gameEvents;
	}

	public void keyPressed(KeyEvent e) {
		gameEvents.setValue(e);
	}

	public void keyReleased(KeyEvent e) {
		gameEvents.setKeys(e);
	}
}