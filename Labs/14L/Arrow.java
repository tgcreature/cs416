//----------------------------- Arrow.java --------------------------
import java.awt.geom.*;
import java.awt.*;

/**
 * Arrow.java: a convenience class for simplifying access to awt Line2D.
 *              the model for this class is the wheels Line class.
 *              And adding something that could be called an arrow head.
 * 
 * This implementation is pretty wimpy, it just draws a small ellipse for
 * an arrow head. Some day it could draw a triangle whose orientation
 * dependson the angle of the line and even have multiple line segments.
 *          
 * This class is patterned after the SmartEllipse and SmartRectangle 
 * classes from Ch 7 of Sanders and van Dam, Object-Oriented Programming
 * with Java as revised into our Ellipse and Rectangle classes.
 * 
 * @author rdb
 * March 2008
 * Last modified: 03/16/14
 */

public class Arrow extends Line
{
    //----------------- instance variables ------------------------
    private Ellipse2D.Double _head;
    
    //------------------- constructors -----------------------------
    /**
     * the endpoints of the arrow change dynamically; usually arrow
     * is created before knowing the end points.
     */
    public Arrow()
    {
        this( 0, 0, 0, 0 );
    }
    /**
     * Can specify 2 points for the end points.
     * @param start Point    start point of arrow
     * @param end Point      end
     */
    public Arrow( Point start, Point end )
    {
        this( start.x, start.y, end.x, end.y );
    }
    /**
     * Can specify 4 coordinates for the end points.
     * @param x1 double
     * @param x2 double
     * @param y1 double
     * @param y2 double
     */
    public Arrow( double x1, double y1, double x2, double y2 )
    {
        super( x1, y1, x2, y2 );
        this.setColor( Color.BLACK );
        _head = new Ellipse2D.Double();
        _head.setFrame( x2 - 4, y2 - 2, 4, 4 );
    }
    
    //-------------- setLine( Point, Point ) ---------------------------
    /**
     * 2 Points is  most convenient way to define line -- in this app.
     * @param p1 Point
     * @param p2 Point
     */
    public void setLine( Point p1, Point p2 )
    {
        setLine( p1.x, p1.y, p2.x, p2.y );
        _head.setFrame( getX2() - 4, getY2() - 2, 4, 4 );
    }
    
    //------------------- draw( Graphics2D )---------------------------
    /**
     * This method is called at repaint time to re-draw the arrow and its
     *      arrow head.
    * @param aBrush Graphics2D
     */
    public void draw( Graphics2D aBrush )
    {
        Color savedColor = aBrush.getColor();
        aBrush.setColor( _lineColor );
        java.awt.Stroke savedStroke = aBrush.getStroke();
        aBrush.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        aBrush.draw( this );
        aBrush.draw( _head );
        aBrush.fill( _head );
        aBrush.setStroke( savedStroke );
        aBrush.setColor( savedColor );
    }
}