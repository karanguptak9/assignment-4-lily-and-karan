package SecondGame;

import java.awt.*;
import java.util.Observable;

public class Block extends SingleObject {

		int health = 1;
		int type = 0;
		Level level = new Level( );

		public Block( int locationX, int locationY, int imgTick, int speed, Image[] img, GameEvents events, int type, Level level ){
				super( locationX, locationY, imgTick, speed, img, events, 1, 0 );
				this.type = type;
				this.level = level;
				if( type == 3 ){
						health = health * 2;
				}
		}

		@Override
		public void BrickCollision( GameObject caller ){
				if( caller instanceof Brick ){
						if( type == 1 ){
								return;
						}
						if( type == 2 ){
								if( SuperRainbowReef.players != null ){
										SuperRainbowReef.players.get( 0 ).setMax( SuperRainbowReef.players.get( 0 ).getMax( ) + 1 );
								}
						}
						health = health - ( ( Brick ) caller ).getDamageTo( );
						if( health <= 0 ){
								this.setDone( true );
						}
				}
		}

		@Override
		public void update( Observable o, Object arg ){
				GameEvents events = ( GameEvents ) arg;
				if( events.getType( ) == 1 ){
						if( events.getTarget( ) == this ){
								if( events.getCaller( ) instanceof SingleObject ){
										( ( SingleObject ) events.getCaller( ) ).hit( this );
								}
								BrickCollision( ( GameObject ) events.getCaller( ) );
						}
				}

		}

		public int getType( ){
				return type;
		}

		@Override
		public void move( ){

		}

		@Override
		public void dead( ){
				this.setRDone( true );
		}

		@Override
		public void hit( SingleObject single ){

		}
}