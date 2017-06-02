// imports ----------------------------------------------------------------
import javax.swing.*;
import java.awt.*;

/**
 * RoundTest.java -- a skeleton for a comprehensive test of
 *    ARoundRectangle. This should be expanded sufficiently that
 *    it is clear from looking at the output that you have tested
 *    the ARoundRectangle thoroughly and understand its parameters.
 * 
 * @author Travis Calley
 */
public class RoundTest extends JPanel
{
    //-------------- instance variables ------------------------------
    private ARoundRectangle _rr0, _rr1, _rr2, _rr3;
    
    //------------------ constructor ---------------------------------
    /**
     * Default constructor.
     */
    public RoundTest( )
    {
        _rr0 = new ARoundRectangle( 90, 90, 140, 140, 180, 180 );
        _rr0.setColor( Color.BLACK );
        System.out.println( "_rr0 Color: " + _rr0.getColor() );
        
        _rr1 = new ARoundRectangle();
        _rr1.setFrameColor( Color.RED );
        System.out.println( "_rr1 Frame Color: " + _rr1.getFrameColor() );
        _rr1.setFillColor( Color.BLUE );
        System.out.println( "_rr1 Fill Color: " + _rr1.getFillColor() );
        _rr1.setThickness( 5 );
        _rr1.setLocation( 400, 0 );
        System.out.println( "_rr1 Location: " + _rr1.getXLocation() +
                           ", " + _rr1.getYLocation() );
        
        _rr2 = new ARoundRectangle( 350, 100 );
        _rr2.setArcSize( 20, 20 );
        _rr2.setSize( 100, 50 );
        System.out.println( "W: " + _rr2.getWidth() + " H: " 
                               + _rr2.getHeight() );
        Point rr2Point = new Point( 300, 50 );
        _rr2.setLocation( rr2Point );
        
        _rr3 = new ARoundRectangle( Color.GREEN );
        System.out.println( "_rr3 Location before move: " + _rr3.getXLocation()
                               + ", " + _rr3.getYLocation() );
        _rr3.moveBy( 0, 300 );
        System.out.println( "_rr3 Location after move: " + _rr3.getXLocation()
                               + ", " + _rr3.getYLocation() );
        _rr3.setLineWidth( 2 );
        _rr3.setFrameColor( Color.BLACK );
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
        //////////////////////////////////////////////////////////////
        // invoke display method of all AShape objects
        //////////////////////////////////////////////////////////////
        _rr0.display( brush );
        _rr1.display( brush );
        _rr2.display( brush );
        _rr3.display( brush );
    }
    
    //------------------------ main -----------------------------------
    /**
     * Main method.
     * 
     * @param args String[]
     */ 
    public static void  main( String[] args )
    {
        JFrame f = new JFrame( "ARoundRectangle test" );
        f.setSize( 500, 400 );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel panel = new RoundTest();
        f.add( panel );
        f.setVisible( true ); 
    }            
}