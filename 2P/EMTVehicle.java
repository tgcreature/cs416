// imports ---------------------------------------------------------------
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * EMTVehicle.java.
 *   
 * A very simple representation of an emt vehicle going to emergency
 * sites and hospitals.
 * 
 * This class is responsible for:
 *   1. remembering "home" position; one specified at creation time.
 *   2. having a public interface method that allows the dispatcher
 *      to tell it where to go next.
 *   3. being able to move itself by steps to that "goal" position
 *      at some specified speed. 
 *   4. given a goal position and a speed,   
 *       -computing the number of frames it will take to get there
 *       -computing the floating point incremental x and y
 *        changes needed for all but the last step
 *       -maintaining a floating point representation for the
 *        "precisely correct" position for each step.
 *       -converting the "correct" position to the closest
 *        pixel (int) location at each step; this means using
 *        (int)Math.round( floatX or floatY ), not just casting
 *        the float values to int.
 *   5. Stopping when it gets to the goal position and having some
 *      way that the dispatcher can find out that it got to its
 *      goal location.
 *   6. Having a public interface method that allows the dispatcher
 *      to get its home position or to tell it to go to its home
 *      position.
 * 
 * 
 * @author Travis Calley
 */
public class EMTVehicle extends JComponent implements Animated
{ 
    //---------------------- instance variables ---------------------
    private int     _goalX;     // if moving, this is target location
    private int     _goalY;
    private int     _speed;     // current speed
    private double  _locX;
    private double  _locY;
    private double  _stepX;
    private double  _stepY;
    private int     _nSteps;
    private double  _dx;
    private double  _dy;
    
    private int     _homeX;
    private int     _homeY;
    
    private Rectangle _bounds = null;
    private boolean _animated = false;
    
    // EMTVehicle display variables
    private JRectangle body;
    private int bodyW = 40, bodyH = 20;
    private int bodyX = 0;
    private int bodyY = 0;
    
    private int wheelY = 15;
    private int wheelDiam = 10;
    private JEllipse wheel1;
    private int wheel1X = 5;
    
    private JEllipse wheel2;
    private int wheel2X = 25;
    
    //------------------------- constructor -----------------------
    /**
     * Main constructor.
     * @param x  int x-coord of location
     * @param y  int y-coord of location
     * @param bodyColor  main vehicle color
     */
    public EMTVehicle ( int x, int y, Color bodyColor ) 
    {
        /////////////////////////////////////////////////////////
        // Save the location as "home"
        // Create 2 or more J-objects to represent the vehicle
        //    Don't make it too big, less than 50x50 would be good
        // Add the J-objects using the add method
        // set the location
        ///////////////////////////////////////////////////////////
        
        _homeX = x;
        _homeY = y;
        
        // Initialize and make the vehicle wheel1.
        wheel1 = new JEllipse();
        wheel1.setColor( Color.BLACK );
        wheel1.setLocation( wheel1X, wheelY );
        wheel1.setSize( wheelDiam, wheelDiam );
        add( wheel1 );

        // Initialize and make the vehicle wheel2.
        wheel2 = new JEllipse();
        wheel2.setColor( Color.BLACK );
        wheel2.setLocation( wheel2X, wheelY );
        wheel2.setSize( wheelDiam, wheelDiam );
        add( wheel2 );
        
        // Initiliaze and make the vehicle body.
        body = new JRectangle();
        body.setColor( bodyColor );
        body.setSize( bodyW, bodyH );
        body.setLocation( bodyX, bodyY );
        add( body );

        setLocation( x, y );
    }
    //------------------------- constructor ------------------------
    /**
     * Constructor that sets location and color.
     * 
     * @param loc Point( x, y )
     * @param col Color body color
     */
    public EMTVehicle ( Point loc, Color col ) 
    {  
        this( loc.x, loc.y, col );
    }
    //---------------- add( JComponent ) ---------------------------
    /**
     * override add method to compute and set bounds information as 
     * components are added to the object.
     * @param comp JComponent  component to be added
     */   
    public void add( JComponent comp )
    {
        super.add( comp );
        if ( _bounds == null )
            _bounds = new Rectangle( comp.getBounds() );
        else
            _bounds = _bounds.union( comp.getBounds() );
        super.setBounds( _bounds ); // update location/size     
    }
    //------------------ travelTo( int, int, int ) -------------------
    /**
     * travel to the specified location at the specified speed.
     * @param x int  x-pos of target location
     * @param y int  y-pos of target location
     * @param speed int  steps per frame to move
     * 
     */
    public void travelTo( int x, int y, int speed )
    {
        //////////////////////////////////////////////////////////////
        // Enable animation (setAnimated method)
        // compute distance from cur location to target
        // divide distance by speed to get n, # of "complete" steps
        // compute dx, dy (as float or double): 
        //                          step to take at each frame
        //  Hint: vehicle must travel from curX to goalX in n steps,
        //          so dx is ( goalX - curX ) / n; same for dy
        // define instance variables for cur location as float|double
        // save all this information in instance variables, to be used
        //     in newFrame method.
        //////////////////////////////////////////////////////////////
        
        _goalX = x;
        _goalY = y;
        _speed = speed;
        
        setAnimated( true );
        
        _locX = getX();
        _locY = getY();
        
        Double distance = Math.sqrt( ( _goalX - _locX ) * ( _goalX - _locX ) + 
                                 ( _goalY + _locY ) * ( _goalY + _locY ) );
        
        _nSteps = ( int )Math.round( distance / _speed );
        
        _dx = ( _goalX - _locX ) / _nSteps;
        _dy = ( _goalY - _locY ) / _nSteps;
    }
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++
    //---------------------- isAnimated() ----------------------------
    /**
     * Returns a boolean for whether or not the vehicle is animated.
     * 
     * @return _animated
     */
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) ------------------
    /**
     * Sets the animated boolean to true or false.
     * 
     * @param onOff Boolean
     */
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    
    /**
     * Gets the goal x.
     * 
     * @return goalX
     */
    public int getGoalX()
    {
        return _goalX;
    }
    
    /**
     * Gets the goal y.
     * 
     * @return goalY
     */
    public int getGoalY()
    {
        return _goalY;
    }
    
    //---------------------- newFrame() ------------------------------
    /**
     * invoked for each frame of animation; 
     * update the position of the vehicle; check if it has reached the
     * goal position -- if it has, turn off animation.
     */
    public void newFrame() 
    {
        if ( !_animated )
            return;
        //////////////////////////////////////////////////////////////
        // If we've reached the target, turn off animation and/or set 
        //    something so dispatcher knows that.
        // else if we still have full steps to take
        //    compute and set next floating point x,y location
        // else this is the last step
        //    set the location to the goal location
        //////////////////////////////////////////////////////////////
        
        if( getX() == _goalX && getY() == _goalY )
            setAnimated( false );
        else if( _nSteps >= 1 )
        {
            setLocation( (int)Math.round( getX() + _dx ), 
                         (int)Math.round( getY() + _dy ) );
            _nSteps--;
        }
        else
            setLocation( _goalX, _goalY );
        
        //getParent().repaint();
    }
    //+++++++++++++++ end Animated interface ++++++++++++++++++++++++
    
    // getters ------------------------------------------------------------
    /**
     * Get the home point of the EMTVehicle.
     * 
     * @return homePoint
     */
    public Point getHomePoint()
    {
        Point homePoint = new Point( _homeX, _homeY );
        return homePoint;
    }
    
    /**
     * Check if EMTVehicle has reached it's goal point.
     * 
     * @return boolean
     */
    public boolean atGoal()
    {
        if( getX() == _goalX && getY() == _goalY )
            return true;
        else
            return false;
    }
    
    /**
     * Returns a string of the last visited objects.
     * 
     * @return visited String
     */
    public String lastVisited()
    {
        return null;
    }
    
    //--------------------- main ------------------------------------------
    /**
     * Main method.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {     
        JFrame testFrame = new JFrame();
        testFrame.setSize( 700, 500 );  // define window size
        
        testFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel testPanel = new JPanel( (LayoutManager) null );
        testFrame.add( testPanel );
        
        EMTVehicle d1 = new EMTVehicle( 200, 200, Color.GREEN );
        testPanel.add( d1 );
        System.out.println( d1.getLocation() );
        
        EMTVehicle d2 = new EMTVehicle( new Point( 100, 200 ), 
                                        Color.BLUE );
        testPanel.add( d2 );
        
        testFrame.setVisible( true ); 
        
        d2.travelTo( 50, 50, 30 );
        
        while( true )
        {
            Utilities.sleep( 200 );
            d2.newFrame();
        }
    }
}
