// imports ----------------------------------------------------------------
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

/**
 * Class that uses a few shapes to make a composite object.
 * 
 * @author Travis Calley
 */
public class First implements AShape
{
    // instance variables -------------------------------------------------
    private AEllipse _circ;
    private ARectangle _rect;
    private ALine _line;
    private ARoundRectangle _roundRect, _roundRect2;
    private ArrayList<AShape> _shapes;
    
    /**
     * Constructor to set location.
     * 
     * @param x int x-location
     * @param y int y-location
     */
    public First( int x, int y )
    {
        _shapes = new ArrayList<AShape>();
        
        _circ = new AEllipse();
        _circ.setSize( 40, 40 );
        _circ.setColor( Color.BLACK );
        _circ.setLocation( x + 30, y );
        _shapes.add( _circ );
        
        _rect = new ARectangle( 20, 40 );
        _rect.setLocation( x, y + 55 );
        _rect.setColor( Color.GREEN );
        _shapes.add( _rect );
        
        _roundRect = new ARoundRectangle( Color.BLUE );
        _roundRect.setArcSize( 20, 20 );
        _roundRect.setLocation( x + 25, y + 40 );
        _roundRect.setSize( 50, 100 );
        _shapes.add( _roundRect );
        
        _roundRect2 = new ARoundRectangle( x, y + 120, 100, 30, 40, 40 );
        _shapes.add( _roundRect2 );
        
        _line = new ALine( x + 75, y + 50, x + 125, y + 25 );
        _line.setColor( Color.GRAY );
        _line.setThickness( 10 );
        _shapes.add( _line );
    }
    
    //----------------------- display( Graphics2D ) -------------------
    /**
     * display - calls draw and fill awt methods.
     *
     * @param brush2D  java.awt.Graphics2D
     */
    public void display( java.awt.Graphics2D brush2D )
    {
        for( AShape shape : _shapes )
        {
            shape.display( brush2D );
        }
    }
}