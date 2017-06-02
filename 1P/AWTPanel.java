// imports ----------------------------------------------------------------
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/** 
 * AWTPanel.java: Swing panel for AWT draw assignment.
 * 
 * This is a framework for the main display window for an 
 * awt application. 
 *      
 * @author Travis Calley
 */

public class AWTPanel extends JPanel
{ 
    //-------------------- instance variables ----------------------
    //
    // declare here (among other things) the variables that reference
    //   AWT display objects (ellipses, rectangles, lines, etc.)
    //private House     robot1, robot2;
    
    private ARoundRectangle _roundRect;
    private AEllipse _ell;
    private ARectangle _rect;
    private ALine _line;
    private ARegularPoly _hex, _dec;
    private First _one, _two;
    private Second _s1, _s2;
    
    private ArrayList<AShape> _aObjects;
    
    //------------- Constructor ---------------------------------
    /**
     * Create awt objects to be displayed.
     * 
     * @param args String[]
     */
    public AWTPanel( String [] args ) 
    {
        // Create objects you want to display using awt graphical objects.
        // Use wheels-like "wrapper" classes, ARectangle, AEllipse, ALine
        // The ARectangle and AEllipse classes are minor variations of
        // SmartRectangle and SmartEllipse from the Sanders and van Dam.
        //
        // References to the objects you create need to be saved in an
        //   ArrayList or Vector of AShape objects.
        //
        
        // Initialize ArrayList of AShapes.
        _aObjects = new ArrayList<AShape>();
        
        // Initialize ARoundRectangle.
        _roundRect = new ARoundRectangle( 150, 150, 50, 100, 30, 30 );
        _roundRect.setColor( Color.GREEN );
        _aObjects.add( _roundRect );
        
        // Initialize AEllipse.
        _ell = new AEllipse();
        _ell.setColor( Color.BLUE );
        _ell.setLocation( 20, 50 );
        _aObjects.add( _ell );
        
        // Initialize ARectangle.
        _rect = new ARectangle();
        _rect.setColor( Color.BLACK );
        _rect.setLocation( 50, 200 );
        _aObjects.add( _rect );
        
        // Initialize ALine.
        _line = new ALine( 200, 50, 350, 30 );
        _line.setThickness( 5 );
        _aObjects.add( _line );
        
        // Initialize ARegularPoly.
        _hex = new ARegularPoly( 50, 50, 6, 50 );
        _hex.setLocation( 150, 300 );
        _aObjects.add( _hex );
        
        // Initialize a Decagon
        _dec = new ARegularPoly( 50, 50, 10, 50 );
        _dec.setLocation( 250, 50 );
        _aObjects.add( _dec );
        
        // Add two First objects
        _one = new First( 275, 250 );
        _aObjects.add( _one );
        _two = new First( 400, 60 );
        _aObjects.add( _two );
        
        // Add two Second objects.
        _s1 = new Second( 450, 350 );
        _aObjects.add( _s1 );
        _s2 = new Second( 525, 200 );
        _aObjects.add( _s2 );
    }  
    //------------- paintComponent ---------------------------------------
    /**
     * This method is called from the Java environment when it determines
     * that the JPanel display should be updated; you need to 
     * make appropriate calls here to re-draw the graphical images on
     * the display. Each object created in the constructor above should 
     * have its "fill" and/or "draw" methods invoked with a Graphics2D 
     * object. The Graphics object passed to paintComponent will be a 
     * a Graphics2D object, so it can be coerced to that type and
     * passed along to the "display" method of the objects you created.
     * 
     * Note that the "display" method is not an awt method; it is a
     * convenience method defined by the "wrapper" classes. 
     * The display method usually passes the graphical objects to both 
     * the Graphics2D.fill and Graphics2D.draw methods, except in the 
     * case of ALine graphical objects which cannot be "filled".
     * 
     * @param aBrush Graphics brush
     */
    public void paintComponent( Graphics aBrush )
    {
        super.paintComponent( aBrush );
        Graphics2D newBrush = (Graphics2D) aBrush; // coerce to Graphics2D
        
        //////////////////////////////////////////////////////////////
        // invoke display( newBrush ) for all A-objects in scene
        //////////////////////////////////////////////////////////////
        for( AShape shape : _aObjects )
        {
            shape.display( newBrush );
        }
    }
    //-------------------- main ------------------------------------------
    /**
     * Invoke AWTApp.main.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        AWTApp.main( args );
    }
}
