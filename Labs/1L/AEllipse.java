//++++++++++++++++++ AEllipse.java ++++++++++++++++++++++++++++++++++
import java.awt.geom.*;
import java.awt.*;

/**
 * AEllipse.java: version 1a
 * 
 * Extends Java's Ellipse2D.Double class, adding the capabilities to
 * set color, location, and size, to move to a specified
 * location, and to display itself on a panel.
 * 
 * This class is a modification of the SmartEllipse class from
 * Sanders and van Dam, Object-Oriented Programming in Java, Chapter 7.
 * 
 * The modifications include:
 * 1. reformatting to match CS415/416 style
 * 2. added more wheels functions
 *       getXLocation(), getYLocation(), setColor()
 * 3. added display method that then calls the awt fill and draw methods.
 * 4. this version's interface is entirely "int"-based, whereas the
 *    Smart classes from the book have some double and partial rotation;
 *    the awt classes have fully "double" interfaces.
 * 
 * @author rdb, Brown Univ
 * Last modified: January 2015
 * 01/29/11 rdb - Added getColor, getFrameColor, getFillColor
 * 01/12/15 rdb: make style-checker compatible
 */

public class AEllipse extends java.awt.geom.Ellipse2D.Double 
                      implements AShape
{
    //---------------- instance variables ------------------------
    protected Color   _borderColor;
    protected Color   _fillColor;
    protected int     _lineWidth = 1;
    
    final protected int     defaultW = 30;
    final protected int     defaultH = 30;
    final protected Color   defaultColor = Color.RED;
    
    //--------------------  constructors ---------------------------
    /**
     * Constructor from SmartEllipse.
     * 
     * @param aColor Color color of the object
     */
    public AEllipse( Color aColor )
    { 
        this( 0, 0 );
        setColor( aColor );
    }
    /**
     * Another wheels-like constructor.
     * 
     * @param x int position
     * @param y int position
     */
    public AEllipse( int x, int y )
    {
        this.setLocation( x, y );
        this.setSize( defaultW, defaultH );
        this.setColor( defaultColor );
    }
    
    /**
     * A default constructor.
     */
    public AEllipse()
    { 
        this( 0, 0 );
        setColor( defaultColor );
    }
    
    //++++++++++++++++++ wheels-like convenience methods +++++++++++++++++
    //----------------------- setFrameColor( Color ) ---------------------
    /**
     * setFrameColor -- a wheels method.
     * 
     * @param aColor Color color of the object
     */
    public void setFrameColor( Color aColor ) 
    {
        _borderColor = aColor;
    }
    //----------------------- setFillColor( Color ) ----------------------
    /**
     * setFillColor -- a wheels method.
     * 
     * @param aColor Color color of the object
     */
    public void setFillColor( Color aColor ) 
    {
        _fillColor = aColor;
    }
    //----------------------- setColor( Color ) --------------------------
    /**
     * setColor -- a wheels method. Sets both frame and fill to same value.
     * 
     * @param aColor Color color of the object
     */
    public void setColor( Color aColor ) 
    {
        _fillColor   = aColor;
        _borderColor = aColor;
    }
    //----------------------- getColor() ---------------------------------
    /**
     * getColor -- a wheels method -- returns fillColor.
     * 
     * @return Color
     */
    public Color getColor() 
    {
        return _fillColor;
    }
    //----------------------- getFillColor() -----------------------------
    /**
     * getFillColor -- a wheels method -- returns fillColor.
     * 
     * @return Color
     */
    public Color getFillColor() 
    {
        return _fillColor;
    }
    //----------------------- getFrameColor() ----------------------------
    /**
     * getFrameColor -- a wheels method -- returns frameColor.
     * 
     * @return Color
     */
    public Color getFrameColor() 
    {
        return _borderColor;
    }
    //----------------------- setThickness( int ) -------------------------
    /**
     * setThickness -- a wheels method.
     * 
     * @param w int width of line
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    //----------------------- setLineWidth( int ) ------------------------
    /**
     * setLineWidth -- same as setThickness; awt uses the term "LineWidth".
     * 
     * @param w int width of line
     */
    public void setLineWidth( int w ) 
    {
        _lineWidth = w;
    }
    //----------------------- getXLocation() -----------------------------
    /**
     * getXLocation() - a wheels method.
     * 
     * @return int value of x location
     */
    public int getXLocation()
    {
        return (int) this.getX();
    }
    //----------------------- getYLocation() -----------------------------
    /**
     * getYLocation() - a wheels method.
     * 
     * @return int value of y location
     */
    public int getYLocation()
    {
        return (int) this.getY();
    }
    //------------------- setLocation( Point ) ---------------------------
    /**
     * setLocation( Point ) -- a wheels method.
     *
     * @param p Point position
     */
    public void setLocation( Point p ) 
    {
        setLocation( p.x, p.y );
    }
    //------------------- setLocation( int, int ) ------------------------
    /**
     * setLocation( int, int ) -- wheels-like.
     * 
     * @param x int position
     * @param y int position
     */
    public void setLocation( int x, int y ) 
    {
        // The setFrame method of java.awt.geom.RectangularShape
        //   sets both the location and size of the shape at the same time
        //   The wheels-like interface sets location and size separately,
        //   so we need to get the size info in order to set the location.
        super.setFrame( x, y, this.getWidth(), this.getHeight() );
    }
    //----------------------- setSize( int, int ) ------------------------
    /**
     * setSize( int, int )  - a wheels method.
     * 
     * @param aWidth int width
     * @param aHeight int height
     */
    public void setSize ( int aWidth, int aHeight ) 
    {
        super.setFrame( this.getX(), this.getY(), aWidth, aHeight );
    }
    //----------------------- moveBy( int, int ) -------------------------
    /**
     * moveBy( dx, dy ) -- move the location by delta.
     * 
     * @param dX int delta move in x
     * @param dY int delta move in y
     */
    public void moveBy( int dX, int dY ) 
    {
        super.setFrame( this.getX() + dX,
                       this.getY() + dY,
                       this.getWidth(),
                       this.getHeight() );
    }
    //----------------------- display( Graphics2D ) ----------------------
    /**
     * display - calls draw and fill awt methods (this is an rdb method).
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void display( java.awt.Graphics2D brush2D )
    {
        fill( brush2D );
        draw( brush2D );
    }
    
    //-------------------------- fill( Graphics2D ) ---------------------
    /**
     * fill - overrides parent method
     * 
     *  With our wheels-like classes, we encapsulate the color of the
     *  object with the object itself. This is NOT how AWT works. AWT
     *  colors are "modal", which means that you set a current color and
     *  all objects are drawn with the current color setting. 
     * 
     *  Consequently, here we save the current color setting (which
     *  is an attribute of the brush), set the current color of the brush 
     *  to the color we want for this object, draw this object, and then 
     *  restore the brush to its original color setting.
     * 
     * Note: if the application does ALL its graphics using our set of 
     * A-objects, the save/restore would not be necessary since every
     * displayed object would just set its own color. The save/restore
     * allows the application to intermix A-objects with other AWT 
     * graphics.
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void fill( java.awt.Graphics2D brush2D )
    {
        Color savedColor = brush2D.getColor(); 
        brush2D.setColor( _fillColor );
        brush2D.fill( this );     // "this" class, AEllipse, is a subclass
        // of java.awt.geom.Ellipse2D.Double
        // A Graphics2D object knows how to fill it.
        brush2D.setColor( savedColor );
    }
    //--------------------------- draw( Graphics2D ) ---------------------
    /**
     * draw - overrides parent method
     * 
     * As with the fill method, we need to save the current brush color,
     * set the brush to the color for the border, draw the border, and then
     * restore the old brush color.
     * 
     * We also have to do the same for the line thickness, which is 
     * implemented in AWT as an attribut of a Stroke object.
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void draw( java.awt.Graphics2D brush2D ) 
    {
        Color savedColor = brush2D.getColor();
        brush2D.setColor( _borderColor );
        java.awt.Stroke savedStroke = brush2D.getStroke();
        
        // There are lots of subclasses of Stroke; the simplest is 
        //   BasicStroke that allows you to define a line width.
        brush2D.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        
        brush2D.draw( this ); // "this" class, AEllipse, is subclass
                              // of java.awt.geom.Ellipse2D.Double
                              // A Graphics2D object knows how to draw
                              // the border of this kind of object.
        brush2D.setStroke( savedStroke );
        brush2D.setColor( savedColor );
    }
}
