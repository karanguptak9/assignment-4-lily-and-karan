package FirstGame;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.net.*;

public class Map {
	private static final Map map = new Map();
	private BufferedReader br = null;
	private static final int SIZE = 40;
	int lineNumber = 0;
	List<Location> wallMap = new ArrayList<Location>();

	public void read() {
		URL url = Map.class.getResource("Resources/wallMap");
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				char [] charArray = line.toCharArray();
				for (int i = 0; i < SIZE; i++) {
					if (charArray[i] == 'P') {
						wallMap.add(new Location(i, lineNumber, "Wall"));
					} else if (charArray[i] == 'D') {
						wallMap.add(new Location(i, lineNumber, "DestructableWall"));
					}
				}

				lineNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<Location> getWallMap() {
		return wallMap;
	}

	public static Map getInstance() {
		return map;
	}
}

class Location {
	private int x;
	private int y;
	private String thing;

	public Location(int x, int y, String thing) {
		this.x = x;
		this.y = y;
		this.thing = thing;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getTankObject() {
		return thing;
	}
}
