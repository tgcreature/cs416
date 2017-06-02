// imports ----------------------------------------------------------------
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Point;
import javax.swing.*;

/**
 * ARegularPoly.java-- a wheels-like convenience class for generating
 * regular polygons; and for using the AWT polygon object.
 *
 * This is a skeleton that needs to be completed.
 *    
 * @author Travis Calley
 */
public class ARegularPoly implements AShape
{
    //---------------- class variables ---------------------------
    private Color            defaultColor = Color.RED;
    
    //---------------- instance variables ------------------------
    // attributes
    private Color            _borderColor;
    private Color            _fillColor;
    private int              _rotation  = 0;
    private int              _lineWidth = 2;
    
    // polygon variables
    private java.awt.Polygon   _polygon = null;
    private int                _nSides;
    private int                _radius;  
    private java.awt.Rectangle _bnds; // rectangular bounds for poly
    private int                _ulX;  // upperLeft corner of bounding box
    private int                _ulY;  //   of the base vertices
    private double[]           _x;    // coords of poly in double at origin 
    private double[]           _y;
    
    //--------------------  constructors ---------------------------
    /**
     * Constructor from ARegularPoly.
     * 
     * @param x int x-location
     * @param y int y-location
     * @param nSides int number of sides
     * @param radius int radius
     */
    public ARegularPoly( int x, int y, int nSides, int radius )
    { 
        _nSides = nSides;
        _radius = radius;
        setColor( defaultColor );
        makeVertices();         // make verts relative to origin at center
        makePolygon( x, y );    // make java.awt.Polygon 
    }
    
    //-------------------- makeVertices() ------------------------------
    /**
     * Generate double vertex arrays for the x,y "base" vertices for this 
     * RegularPolygon specification. The base vertices represent the shape
     * the regular polygon with its center at (0,0).
     * 
     * Do all calculation in double based on regular polygon with center at 
     * 0,0. First vertex is always at alpha = 0, horizontal line from center 
     * parallel to x-axis of length radius; i.e., at ( radius, 0 ); rest are
     * at equal angles around the origin.
     * 
     * These vertices are used to create the java.awt.Polygon object which
     * has to be scaled, rotated, and translated before being converted
     * to the int arrays needed for java.awt.Polygon.
     */
    private void makeVertices()
    {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        
        _x = new double[ _nSides ];
        _y = new double[ _nSides ];
        double alpha = ( _rotation / 180.0 ) * Math.PI;        
        double   dAlpha = 2 * Math.PI / _nSides;
        
        for ( int i = 0; i < _nSides; i++ )
        {
            _x[ i ] = Math.cos( alpha ) * _radius;
            _y[ i ] = Math.sin( alpha ) * _radius;
            if ( _x[ i ] < minX )
                minX = _x[ i ];
            if ( _y[ i ] < minY )
                minY = _y[ i ];
            
            alpha += dAlpha;
        }
        _ulX = (int)Math.round( minX );
        _ulY = (int)Math.round( minY );
    }
    
    //-------------------- makePolygon( int, int ) --------------------------
    /**
     * Create a java.awt.Polygon obj for this RegularPolygon spec;
     *   so that the upper left corner of its bounding box is at x,y
     * 
     * The double coordinates in _x[] and _y[] need to be translated 
     * so that the upper left corner of the bounding box of the vertices
     * is at the location specified by the parameters.
     * 
     * The upper left corner of the bounding box of the vertices is
     * stored in the instance variables _ulX and _ulY.
     * 
     * Each vertex ( _x[i], _y[i] ) must be translated by 
     *          (locX - _ulX, locY - _ulY )
     * then converted to int and the result assigned to the 
     * positions in the int arrays x[i], y[i].
     * 
     * Since you've just made a new java.awt.Polygon, you also
     * need to get its bounds and save that in the _bnds instance variable
     * 
     * @param locX int x-location
     * @param locY int y-location
     */
    private void makePolygon( int locX, int locY )
    {
        ////////////////////////////////////////////////////////////////
        // Need to make the java.awt.Polygon object that represents
        //   this RegularPolygon. See the comments above for help
        ////////////////////////////////////////////////////////////////
        _polygon = new java.awt.Polygon();
        
        int count = 0;
        while( count < _x.length )
        {
            double tempX = _x[count];
            tempX = tempX + locX - _ulX;
            int x = (int)tempX;
            double tempY = _y[count];
            tempY = tempY + locY - _ulY;
            int y = (int)tempY;
            _polygon.addPoint( x, y );
            count++;
        }
        
        _bnds = new java.awt.Rectangle( _polygon.getBounds() ); // Set bounds
    }
    
    //----------------------- setRotation( int ) -------------------------
    /**
     * setRotation -- a wheels method.
     * 
     * @param aRotation int rotation
     */
    public void setRotation( int aRotation ) 
    {
        ///////////////////////////////////////////////////////////////
        // This requires regenerating the base vertices and then modifying
        // the vertices of the java.awt.Polygon or creating a new one.
        // Be careful to "remember" the current location of the Polygon
        // so you can know where to move the new specification of the
        // Polygon. Since you need to do all this for setRadius also, 
        // it better be in a subroutine. 
        ////////////////////////////////////////////////////////////////
        
        makeVertices();
        int curX = getXLocation();
        int curY = getYLocation();
        _polygon.reset();
        
        _rotation = aRotation;
        makeVertices();
        makePolygon( curX, curY );
    }
    
    //---------------------- setRadius( int ) ----------------------------
    /**
     * Set the radius of the regular polygon to r.
     * 
     * @param r int radius
     */
    public void setRadius( int r )
    {
        ///////////////////////////////////////////////////////////////
        // This requires regenerating the base vertices and then modifying
        // the vertices of the java.awt.Polygon or creating a new one.
        // Be careful to "remember" the current location of the Polygon
        // so you can know where to move the new specification of the
        // Polygon. Since you need to do all this for setRotation also, 
        // it better be in a subroutine. 
        ////////////////////////////////////////////////////////////////
        
        makeVertices();
        int curX = getXLocation();
        int curY = getYLocation();
        _polygon.reset();
        
        _radius = r;
        makeVertices();
        makePolygon( curX, curY );
    }
    
    //-------------------------- fill( Graphics2D ) ----------------------
    /**
     * fill - overrides parent method.
     * 
     * @param aBetterBrush Graphics2D Brush
     */
    public void fill( java.awt.Graphics2D aBetterBrush )
    {
        ////////////////////////////////////////////////////////////
        // 1. Look at the implementaton of fill for AEllipse
        // 2. Look at the java api documentation for Graphics2D
        ////////////////////////////////////////////////////////////
        
        Color savedColor = aBetterBrush.getColor(); 
        aBetterBrush.setColor( _fillColor );
        aBetterBrush.fill( _polygon );
        aBetterBrush.setColor( savedColor );
        
    }
    
    //--------------------------- draw( Graphics2D ) ---------------------
    /**
     * draw - overrides parent method.
     * 
     * @param aBrush Graphics2D brush
     */
    public void draw( java.awt.Graphics2D aBrush ) 
    {
        ////////////////////////////////////////////////////////////
        // 1. Look at the implementaton of draw for AEllipse
        // 2. Look at the java api documentation for Graphics2D
        ////////////////////////////////////////////////////////////
        
        Color savedColor = aBrush.getColor();
        aBrush.setColor( _borderColor );
        java.awt.Stroke savedStroke = aBrush.getStroke();
        
        aBrush.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        
        aBrush.draw( _polygon );
        
        aBrush.setStroke( savedStroke );
        aBrush.setColor( savedColor );
        
    }
    
    //++++++++++++++ wheels-like AShape convenience methods ++++++++++++++
    //----------------------- setSize( int, int ) ------------------------
    /**
     * setSize( int, int )  - a wheels method.
     *     This method will be implemented differently for ARegularPoly
     *     because a regular polygon cannot be differentially scaled in 
     *     x and y or else it would no longer be a regular polygon. 
     *     Hence, you must force aWidth and aHeight to be the same. 
     *     Arbitrarily, use the average of the 2 values as the size.
     *     You should print an error message if the setSize call specifies
     *     different values for width and height -- and then compute the 
     *     averaged.
     * 
     *     Then use the size to change the radius:
     *        new radius = 1/2 size.
     *     This isn't perfectly consistent with the definition of setSize
     *     for other children of AShape, where the shape should just fit
     *     in the box defined by the setSize width, height paramters. 
     *     Turning the size into a radius means that the polygon will
     *     just fit in the circle defined by the radius. Computing the box
     *     would be more complicated since the minimum size depends on the
     *     orientation of the specific regular polygon.
     * 
     * @param aWidth int width
     * @param aHeight int height
     */
    public void setSize( int aWidth, int aHeight ) 
    {
        //////////////////////////////////////////////////////////
        // complete the code as described in comments above
        //////////////////////////////////////////////////////////
        
        if( aWidth == aHeight )
        {
            int newRadius = ( aWidth + aHeight ) / 2;
            
            setRadius( newRadius );
        }
    }
    
    //--------The remaining wheels-like methods are done -----------------
    //----------------------- setFrameColor( Color ) ---------------------
    /**
     * setFrameColor -- a wheels method.
     * 
     * @param aColor frame color
     */
    public void setFrameColor( Color aColor ) 
    {
        _borderColor = aColor;
    }
    
    //----------------------- setFillColor( Color ) ----------------------
    /**
     * setFillColor -- a wheels method.
     * 
     * @param aColor fill color
     */
    public void setFillColor( Color aColor ) 
    {
        _fillColor = aColor;
    }
    
    //----------------------- setColor( Color ) --------------------------
    /**
     * setColor -- a wheels method.
     * 
     * @param aColor fill and frame color
     */
    public void setColor( Color aColor ) 
    {
        _fillColor   = aColor;
        _borderColor = aColor;
    }
    
    //----------------------- setThickness( int ) ------------------------
    /**
     * setThickness -- a wheels method.
     * 
     * @param w int line width
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    
    //----------------------- setLineWidth( int ) ------------------------
    /**
     * setLineWidth -- same as setThickness, but I like the name better.
     * 
     * @param w int line width
     */
    public void setLineWidth( int w ) 
    {
        setThickness( w );
    }
    
    //----------------------- getXLocation() -----------------------------
    /**
     * getXLocation() - a wheels method.
     * 
     * @return x-locatoin
     */
    public int getXLocation()
    {
        return (int) _bnds.getX();
    }
    
    //----------------------- getYLocation() -----------------------------
    /**
     * getYLocation() - a wheels method.
     *
     * @return y-location
     */
    public int getYLocation()
    {
        return (int) _bnds.getY();
    }
    
    //------------------- setLocation( Point ) ---------------------------
    /**
     * setLocation( Point ) -- a wheels method.
     * 
     * @param p Point for location
     */
    public void setLocation( Point p ) 
    {
        setLocation( p.x, p.y );
    }
    
    //------------------- setLocation( int, int ) ------------------------
    /**
     * setLocation( int, int ) -- wheels-like.
     * 
     * @param x int x-location
     * @param y int y-location
     */
    public void setLocation( int x, int y ) 
    {
        moveBy( (int)( x - _bnds.getX() ), (int)( y - _bnds.getY() ) );    
    }
    
    //----------------------- move( int, int ) ---------------------------
    /**
     * moveBy( dx, dy ) -- move the location by delta.
     * 
     * @param aChangeInX delta x
     * @param aChangeInY delta y
     */
    public void moveBy( int aChangeInX, int aChangeInY ) 
    {
        _polygon.translate( aChangeInX, aChangeInY );
        _bnds = _polygon.getBounds();
    }
    
    //----------------------- display( Graphics2D ) ----------------------
    /**
     * display - calls draw and fill awt methods (this is an rdb method).
     * 
     * @param aBetterBrush Graphics2D brush
     */
    public void display( java.awt.Graphics2D aBetterBrush )
    {
        fill( aBetterBrush );
        draw( aBetterBrush );
    }
}  // End class ARegularPoly