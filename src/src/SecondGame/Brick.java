package SecondGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

public class Brick extends SingleObject {

    private ArrayList<ArrayList> design;
    private int type;
    private URL[] sound;
    private Stats degree = new Stats( );

    public Brick( int x, int y, int imgTick, int speed, Image[] img,
                  GameEvents events, int fulldamage, int damageto,
                  ArrayList<ArrayList> design, int type, URL[] sound ){
        super( x, y, imgTick, speed, img, events, fulldamage, damageto );
        this.design = design;
        this.type = type;
        this.sound = sound;
    }

    public void hit( SingleObject u ){
        if( u instanceof Block ){
            if( this.getX( ) > u.getX( ) - u.getWidth( ) / 2 && this.getX( ) < u.getX( ) + u.getWidth( ) / 2 ){
                degree.setStats( 2 * Math.PI - degree.getStats( ) );
            } else{
                degree.setStats( Math.PI - degree.getStats( ) );
            }
            this.largeMove( );
            this.largeMove( );
            this.largeMove( );
            if( ( ( Block ) u ).getType( ) != 1 ){
                SuperRainbowReef.score += 20;
            }
            AudioInputStream explSound;
            Clip clip;
            try{
                explSound = AudioSystem.getAudioInputStream( this.sound[ 0 ] );
                clip = AudioSystem.getClip( );
                clip.open( explSound );
                clip.start( );
            } catch( Exception e ){
                e.printStackTrace( );
            }
        } else if( u instanceof Enemy ){
            SuperRainbowReef.score += 50;
            AudioInputStream explSound;
            Clip clip;
            try{
                explSound = AudioSystem.getAudioInputStream( this.sound[ 1 ] );
                clip = AudioSystem.getClip( );
                clip.open( explSound );
                clip.start( );
            } catch( Exception e ){
                e.printStackTrace( );
            }
        }
        else if( u instanceof Player ){

            if( ( ( Player ) u ).getMvRight( ) && !( ( Player ) u ).getMvLeft( ) ){
                degree.setStats( 2 * Math.PI - degree.getStats( ) - ( 1.0 / 12.0 ) * Math.PI );

            } else if( ( ( Player ) u ).getMvLeft( ) && !( ( Player ) u ).getMvRight( ) ){
                degree.setStats( 2 * Math.PI - degree.getStats( ) + ( 1.0 / 12.0 ) * Math.PI );

            } else{
                degree.setStats( 2 * Math.PI - degree.getStats( ) );
            }
            if( degree.getStats( ) <= 0 ){
                degree.setStats( ( 1 / 6.0 ) * Math.PI );
            }
            if( degree.getStats( ) >= Math.PI ){
                degree.setStats( ( 5 / 6.0 ) * Math.PI );
            }

            this.largeMove( );
            this.largeMove( );
            this.largeMove( );

            AudioInputStream inputSound;
            Clip clip;
            try{
                inputSound = AudioSystem.getAudioInputStream( this.sound[ 2 ] );
                clip = AudioSystem.getClip( );
                clip.open( inputSound );
                clip.start( );
            } catch( Exception e ){
                e.printStackTrace( );
            }

        }

    }

    @Override
    public void move( ){
        int widthChange = ( int ) ( Math.cos( degree.getStats( ) ) * getSpeed( ) );
        int heightChange = ( int ) ( Math.sin( degree.getStats( ) ) * getSpeed( ) );
        this.setX( this.getX( ) + widthChange );
        this.setY( this.getY( ) - heightChange );

        if( this.getY( ) > 470 ){
            this.setDone( true );
            AudioInputStream inputSound;
            Clip clip;
            try{
                inputSound = AudioSystem.getAudioInputStream( this.sound[ 3 ] );
                clip = AudioSystem.getClip( );
                clip.open( inputSound );
                clip.start( );
            } catch( Exception e ){
                e.printStackTrace( );
            }
            return;
        }

        for( GameObject thing : ( ArrayList<GameObject> ) design.get( 0 ) ){
            if( thing == this ){
                continue;
            }
            if( this.collision( thing ) ){
                getEvents( ).setCollision( this, thing );
            }
        }
        for( GameObject thing : ( ArrayList<GameObject> ) design.get( 1 ) ){
            if( this.collision( thing ) ){
                getEvents( ).setCollision( this, thing );
            }
        }
        for( GameObject thing : ( ArrayList<GameObject> ) design.get( 2 ) ){
            if( this.collision( thing ) ){
                getEvents( ).setCollision( this, thing );
            }
        }


    }

    public void largeMove( ){
        int widthChange = ( int ) ( Math.cos( degree.getStats( ) ) * getSpeed( ) );
        int heightChange = ( int ) ( Math.sin( degree.getStats( ) ) * getSpeed( ) );
        this.setX( this.getX( ) + widthChange );
        this.setY( this.getY( ) - heightChange );
    }


    @Override
    public void update( Observable o, Object arg ){
        GameEvents events = ( GameEvents ) arg;
        if( events.getType( ) == 1 ){
            if( events.getTarget( ) == this ){
                BrickCollision( ( GameObject ) events.getTarget( ) );
            }
        }
        if( events.getType( ) == 2 ){
            if( events.getTarget( ) == this ){

            }
        }

    }

    public void update( int w, int h ){
        super.update( w, h );
        this.increaseImgTick( );

    }

    public int getType( ){
        return type;
    }

    @Override
    public void BrickCollision( GameObject caller ){

    }

    @Override
    public void dead( ){
        this.setRDone( true );
    }
}