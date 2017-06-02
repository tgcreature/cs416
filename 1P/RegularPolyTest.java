// imports ----------------------------------------------------------------
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * RegularPolyTest.java -- this is a skeleton for a program to 
 *             thoroughly test the ARegularPoly class.
 * 
 *  It needs to be significantly expanded. 
 *       1. At the very least, every public method of ARegularPoly needs 
 *          to be invoked at least once -- even methods you didn't edit! 
 *          You must verify that you didn't break them. 
 *       2. And, every option of every method you did write needs to be
 *          tested thoroughly.
 *          
 * @author Travis Calley
 */
public class RegularPolyTest extends JPanel
{
    //-------------- instance variables ------------------------------
    private ARegularPoly _rp1, _rp2, _rp3, _rp4;
    
    //------------------ constructor ---------------------------------
    /**
     * Default constructor.
     */
    public RegularPolyTest( )
    {
        ////////////////////////////////////////////////////////
        // Create enough ARegularPoly objects to show that you 
        //   have thoroughly tested this code. At the very least,
        //   your tests should cause the execution of every public method
        //   in the class (not just the ones you had to write).
        ////////////////////////////////////////////////////////
        
        // Initializes and tests a first polygon.
        _rp1 = new ARegularPoly( 50, 50, 12, 100 );
        _rp1.setFrameColor( Color.GREEN );
        _rp1.setFillColor( Color.BLACK );
        _rp1.setThickness( 5 );
        _rp1.setSize( 10, 10 );
        
        // Initializes and tests a second polygon.
        _rp2 = new ARegularPoly( 300, 200, 3, 30 );
        _rp2.setColor( Color.BLUE );
        _rp2.setLineWidth( 3 );
        _rp2.setFrameColor( Color.RED );
        _rp2.setRadius( 75 );
        
        // Initializes and tests a third polygon.
        _rp3 = new ARegularPoly( 0, 0, 6, 50 );
        Point rp3Point = new Point( 150, 350 );
        _rp3.setLocation( rp3Point );
        _rp3.setRotation( 25 );
        
        // Initializes and tests a fourth polygon.
        _rp4 = new ARegularPoly( 0, 0, 7, 25 );
        _rp4.setLocation( 200, 200 );
        System.out.println( _rp4.getXLocation() + ", " + _rp4.getYLocation() );
        _rp4.moveBy( 250, 25 );
    }
    //------------- paintComponent( Graphics ) ----------------------
    /**
     * Paints the shapes.
     * 
     * @param g Graphics brush
     */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        Graphics2D brush = (Graphics2D)g;
        //////////////////////////////////////////////////////////
        // draw the objects you created in the constructor
        //////////////////////////////////////////////////////////
        _rp1.display( brush );
        _rp2.display( brush );
        _rp3.display( brush );
        _rp4.display( brush );
    }
    
    //------------------------ main -----------------------------------
    /**
     * Main method.
     * 
     * @param args String[]
     */
    public static void  main( String[] args )
    {
        JFrame f = new JFrame( "ARegularPoly test" );
        f.setSize( 700, 600 );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel panel = new RegularPolyTest();
        f.add( panel );
        f.setVisible( true ); 
    }            
}