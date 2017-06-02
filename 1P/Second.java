// imports ----------------------------------------------------------------
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

/**
 * Class that uses multiple shapes to make a composite object.
 * 
 * @author Travis Calley
 */
public class Second implements AShape
{
    // instance variables -------------------------------------------------
    //private AEllipse _circ;
    //private ARectangle _rect;
    private ALine _line;
    private ARoundRectangle _roundRect, _roundRect2;
    private ARegularPoly _rp1, _rp2;
    private ArrayList<AShape> _shapes;
    
    /**
     * Constructor that takes a location.
     * 
     * @param x int x-location
     * @param y int y-location
     */
    public Second( int x, int y )
    {   
        _shapes = new ArrayList<AShape>();
        
        _roundRect = new ARoundRectangle( x, y, 40, 50, 50, 10 );
        _shapes.add( _roundRect );
        
        _roundRect2 = new ARoundRectangle( x, y + 50, 100, 30, 40, 40 );
        _roundRect2.setColor( Color.BLACK );
        _shapes.add( _roundRect2 );
        
        _rp1 = new ARegularPoly( x + 15, y + 20, 5, 25 );
        _rp1.setColor( Color.GREEN );
        _shapes.add( _rp1 );
        
        _rp2 = new ARegularPoly( x + 60, y + 5, 10, 30 );
        _rp2.setColor( Color.BLUE );
        _shapes.add( _rp2 );
        
        _line = new ALine( x, y + 10, x + 100, y + 10 );
        _line.setThickness( 5 );
        _line.setColor( Color.WHITE );
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