// imports -----------------------------------------------------------
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/** 
 * FractalBox.java.
 * 
 * @author Travis Calley
 */
public class FractalBox extends Rectangle2D.Float 
{
    //---------------- class variables ------------------------------
    
    //---------------- instance variables ------------------------------
    private FractalBox[]   _children;
    private int            _myDepth;
    private final Color[] _colors = { Color.BLUE, Color.GREEN, Color.RED,
                                      Color.ORANGE, Color.BLACK, Color.YELLOW,
                                      Color.CYAN, Color.GRAY, Color.ORANGE };
    
    //------------------------ constructors ----------------------------
    /**
     * constructor must recursively create children.
     * 
     * @param depth int
     */
    public FractalBox( int depth )
    {
        _myDepth = depth;
        _children = new FractalBox[ 4 ];

        //***************************************************************
        //  Need to create the 4 children unless depth has reached the 
        //    max specified in the FractalSpec class
        //  
        //***************************************************************
        if( _myDepth < FractalSpecs.maxDepth )
        {
            for( int i = 0; i < _children.length; i++ )
                _children[ i ] = new FractalBox( _myDepth + 1 );
        }
    }
    
    // --------------- set location of all four children ------------------
    /**
     * Sets location of top child.
     */
    private void setTop()
    {
        if( _children[ 0 ] != null )
        {
            double pWidth = this.getWidth();
            double pHeight = pWidth * FractalSpecs.aspectRatio;
            
            double width = pWidth * FractalSpecs.childSizeRatio;
            double height = pHeight * FractalSpecs.childSizeRatio;    
            
            double x = this.getX() + pWidth * FractalSpecs.childOffset;
            double y = this.getY() - height;
            
            _children[ 0 ].setFrame( x, y, width, height );
        }
    }
    /**
     * Sets location of left child.
     */
    private void setLeft()
    {
        if( _children[ 1 ] != null )
        {
            double pWidth = this.getWidth();
            double pHeight = pWidth * FractalSpecs.aspectRatio;
            
            double width = FractalSpecs.childSizeRatio * pWidth;
            double height = pHeight * FractalSpecs.childSizeRatio;    
            
            double x = this.getX() - width;
            double y = this.getY() + pHeight - height - pHeight * 
                FractalSpecs.childOffset;
            
            _children[ 1 ].setFrame( x, y, width, height );
        }
    }
    /**
     * Sets location of right child.
     */
    private void setRight()
    {
        if( _children[ 2 ] != null )
        {
            double pWidth = this.getWidth();
            double pHeight = pWidth * FractalSpecs.aspectRatio;
            
            double width = FractalSpecs.childSizeRatio * pWidth;
            double height = pHeight * FractalSpecs.childSizeRatio;    
            
            double x = this.getX() + pWidth;
            double y = this.getY() + pHeight * FractalSpecs.childOffset;
            
            _children[ 2 ].setFrame( x, y, width, height );
        }
    }
    /**
     * Sets location of bottom child.
     */
    private void setBottom()
    {
        if( _children[ 3 ] != null )
        {
            double pWidth = this.getWidth();
            double pHeight = pWidth * FractalSpecs.aspectRatio;
            
            double width = FractalSpecs.childSizeRatio * pWidth;
            double height = pHeight * FractalSpecs.childSizeRatio;    
            
            double x = this.getX() + pWidth - width - pWidth * 
                FractalSpecs.childOffset;
            double y = this.getY() + pHeight;
            
            _children[ 3 ].setFrame( x, y, width, height );
        }
    }
    
    //------------------- propagate() -----------------------------------
    /** 
     * this method (or one like it) initiates the recursive update of
     * location and sizes of all the FractalShapes beginning with this one.
     * 
     * Don't do it all here, though. Make helper methods to keep the code
     * simpler.
     */
    public void propagate()
    {
        //**********************************************************
        //   need to compute location/size info for the children recursively
        //**********************************************************
        
        setTop();
        setLeft();
        setRight();
        setBottom();
        
        for( FractalBox child: _children )
            if( child != null )
                child.propagate();
    }
    
    //--------------------- display( Graphics2D ) -----------------------
    /**
     * method called by FractalGUI.paintComponent.
     * 
     * @param context Graphics2D
     */   
    public void display( Graphics2D context ) 
    {
        Color saveColor = context.getColor();
        Color myColor = _colors[ _myDepth - 1 ];
        //////////////////////////////////////////////////////////////
        // Actual color needs to be determined by depth, so will proably want
        //   an array of colors
        //////////////////////////////////////////////////////////////
        // To implement the semi-transparent option, once you've chosen
        //   an opaque color based on depth, you can use the color values
        //   from that object to create a new color object with the same
        //    RGB, but with an A (alpha) of 0.5 or lower.
        ///////////////////////////////////////////////////////////////
        // draw myself
        
        if( !FractalSpecs.opaqueFill )
        {
            int r = myColor.getRed();
            int g = myColor.getGreen();
            int b = myColor.getBlue();
            Color transparent = new Color( r, g, b, 50 );
            myColor = transparent;
        }
        
        context.setColor( myColor );
        
        if( FractalSpecs.fill )
            context.fill( this );
        else
            context.draw( this );  
               
        ////////////////////////////////////////////////////////////////
        // Need to recursively draw all children
        //
        // Each depth should have a different color.
        ///////////////////////////////////////////////////////////////
        myColor = _colors[ _myDepth ];
        context.setColor( myColor );
        
        if( !FractalSpecs.opaqueFill )
        {
            int r = myColor.getRed();
            int g = myColor.getGreen();
            int b = myColor.getBlue();
            Color transparent = new Color( r, g, b, 50 );
            myColor = transparent;
            context.setColor( myColor );
        }
        
        if( _children != null )
        {
            for( FractalBox child: _children )
            {
                if( child != null )
                    child.display( context );
            }
        }
        context.setColor( saveColor );
    }
} // end of FractalBox