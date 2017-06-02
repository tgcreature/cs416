// imports ----------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * EmergencySite -- represents an emergency site. 
 *   Dragging (mousePressed followed by mouseDragged) repositions it
 *     Not allowed to drag if its the current EmergencySite, easiest
 *     way to do that is make that site not draggable
 *  MouseClicked -- if clicked, this site is no longer an emergency 
 *     site; remove it from the set of sites or somehow make it known
 *     that it is not real emergency site any more.
 * 
 * @author rdb
 * Last edited: 01/28/14  
 */
public class EmergencySite extends JRectangle
    implements MouseListener, MouseMotionListener, Draggable
{
    //--------------------- class variables -------------------------
    static int    size = 10;
    
    //--------------------- instance variables ----------------------
    private boolean     _visited = false;
    
    //--------------------- constants -------------------------------
    private Color  fillColor = Color.RED;
    private Color  lineColor = Color.BLACK;
    
    //---------------------- constructors ---------------------------
    /**
     * Generate a site at the specified location.
     * 
     * @param p Point   location of emergency
     */
    public EmergencySite( Point p )
    {
        super( p.x, p.y );
        setFillColor( fillColor );
        setFrameColor( lineColor );
        setSize( size, size );
        
        addMouseListener( this );
        addMouseMotionListener( this );
    }
    
    /**
     * Sets whether or not the EmerygencySite is clickable.
     * 
     * @param b Boolean
     */
    public void setClickable( Boolean b )
    {
        _clickable = b;
    }
    
    //+++++++++++++++++++ Draggable interface methods ++++++++++++++
    private boolean   _draggable = true; // true if obj can be dragged
    
    /**
     * Sets the this object to draggable or not.
     * 
     * @param onOff boolean
     */
    public void setDraggable( boolean onOff )
    {
        _draggable = onOff;
    }
    
    /**
     * Returns whether or not this is draggable.
     * 
     * @return _draggable
     */
    public boolean isDraggable()
    {
        return _draggable;
    }
    
    /**
     * Returns true if a point is in the bounds of this object.
     * 
     * @param point Point2D
     * @return bounds
     */
    public boolean contains( java.awt.geom.Point2D point )
    {
        return getBounds().contains( point );
    }
    
    /**
     * Sets whether or not the site was visited.
     * 
     * @param yesNo Boolean
     */
    public void setVisited( Boolean yesNo )
    {
        _visited = yesNo;
    }
    
    /**
     * Returns whether or not the site has been visited.
     * 
     * @return _visited
     */
    public Boolean wasVisited()
    {
        return _visited;
    }
    
    //++++++++++++++++ mouse methods / instance variables ++++++++++++
    private Point _saveMouse;   // last mouse position
    //   used for dragging      
    //+++++++++++++++++++++++++ mouseListener methods +++++++++++++++
    //-------------- mousePressed -----------------------------------
    /**
     * mousePressed.
     * 
     * @param me MouseEvent
     */
    public void mousePressed( MouseEvent me )
    {
        /////////////////////////////////////////////////////////////
        // me.getPoint(), which we've used before is the location of 
        // mouse "inside" the JComponent; this won't work.
        //
        // We need position of mouse in the container that holds the
        // JComponent: getParent().getMousePosition()
        //
        // Assign it to the instance variable, _saveMouse
        /////////////////////////////////////////////////////////////
        
        _saveMouse = getParent().getMousePosition();
        
    }
    //-------------- mouseClicked -----------------------------------
    private Boolean _clickable = true;
    /**
     * Removes or adds this object to the ArrayList.
     * 
     * @param me MouseEvent
     */
    public void mouseClicked( MouseEvent me )
    {
        //////////////////////////////////////////////////////////////
        // On mouse click, this site should be removed from the
        //    sites array.
        // Figure out a way for this class to 
        //    - know about the sites array (so it can remove itself), OR
        //    - for this class to be able to call somebody that it 
        //      knows about who knows about the sites array, OR
        //    - to put information into this object so that some other 
        //      object will know this site is no longer a real site
        //      and should not be visited by the emt vehicle.
        //////////////////////////////////////////////////////////////
        
        if( _clickable )
        {
            if( getColor() == Color.RED )
                setFillColor( Color.BLUE );
            else
                setFillColor( Color.RED );
            
            int curX = getXLocation();
            int curY = getYLocation();
            
            for( int i = 0; i < EMTPanel.eSites.size(); i++ )
            {
                EmergencySite temp = EMTPanel.eSites.get( i );
                int otherX = temp.getXLocation();
                int otherY = temp.getYLocation();
                
                if( curX == otherX && curY == otherY )
                {
                    if( getColor() == Color.BLUE )
                        EMTPanel.eSites.remove( this );
                    else
                        EMTPanel.eSites.add( this );
                }
            }
        }
    }
    //--------------- unimplemented mouse listener methods -----------
    /**
     * Unimplemented.
     * 
     * @param me MouseEvent
     */
    public void mouseReleased( MouseEvent me )
    {
    }
    /**
     * Unimplemented.
     * 
     * @param me MouseEvent
     */
    public void mouseEntered( MouseEvent me )
    {
    }
    /**
     * Unimplemented.
     * 
     * @param me MouseEvent
     */
    public void mouseExited( MouseEvent me )
    {
    }
    
    //+++++++++++++++++++ mouseMotionListener methods ++++++++++++++++
    //---------------- mouseDragged ----------------------------------
    /**
     * mouseDragged.
     * 
     * @param me MouseEvent
     */
    public void mouseDragged( MouseEvent me )
    {
        //////////////////////////////////////////////////////////////
        //  IF this object is draggable
        //     Get new position of mouse:
        //         getParent().getMousePosition()
        //     For each of x and y coordinates, compute
        //       dX = newX - oldX (stored in _saveMouse.x)
        //       dY = newY - oldY (stored in _saveMouse.y)
        //     invoke moveBy( dX, dY ) 
        //     Save new position in _saveMouse
        //     getParent().repaint()
        //////////////////////////////////////////////////////////////
        
        if( _draggable )
        {
            Point curMouse = getParent().getMousePosition();
            
            int newX = curMouse.x;
            int newY = curMouse.y;
            int oldX = _saveMouse.x;
            int oldY = _saveMouse.y;
                
            int dX = newX - oldX;
            int dY = newY - oldY;
            
            moveBy( dX, dY );
            
            _saveMouse = curMouse;
            getParent().repaint();
        }
        
        
    }
    //----------------- mouseMoved not implemented -------------------
    /**
     * Unimplemented.
     * 
     * @param me MouseEvent
     */
    public void mouseMoved( MouseEvent me )
    {
    }
    //+++++++++++++++++ end MouseMotionListeners +++++++++++++++++++++
    
    //--------------------- main -----------------------------------
    /**
     * unit test.
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
        
        EmergencySite s1 = new EmergencySite( new Point( 200, 200 ) );
        testPanel.add( s1 );
        
        EmergencySite s2 = new EmergencySite( new Point( 200, 100 ) );
        testPanel.add( s2 );
        
        testFrame.setVisible( true );       
    }
}