// imports ---------------------------------------------------------------
import java.util.*;
import java.awt.*;

/**
 * Dispatcher.java -- Controls the activities of the EMTVehicle
 *    Resonsibilities:
 *    1. Know the current state of the vehicle: is it at home,
 *       heading to an emergency, heading for hospital, heading home.
 *    2. Tell the vehicle what to do next. Normally, this will be
 *       after it gets to its next destination. However, if the
 *       vehicle is heading home and an emergency site gets added,
 *       it should get re-directed to the site.
 *    3. It must know about or be able to get information from
 *       a. the emergency sites collection
 *       b. the hospital collection
 *       c. the timer (because it needs to know when that 
 *          vehicle needs to wait for 2 seconds. The easiest
 *          way to do this is to simply "restart()" the timer.
 *          This works because there is nothing else moving in
 *          this application except one vehicle. If that weren't
 *          the case, the timer would have to keep running and
 *          the code would have to count events in order to figure
 *          out when 2 seconds had elapsed.)
 * 
 * Framework:
 *    This class should implement the Animated interface, and get
 *    called (by EMTPanel) on every frame event. For example, 
 *    if the vehicle is home or going home and there is an entry 
 *    in the sites array, the dispatcher needs to tell the vehicle 
 *    to go to the next site in the array. There are of course 
 *    several other cases.
 * 
 * @author Travis Calley
 */
public class Dispatcher implements Animated
{
    //-------------------- instance variables ---------------------
    private ArrayList<Hospital>         _hospitals;
    private ArrayList<EmergencySite>    _sites;
    private EMTVehicle                  _emt;
    private JLine                       _path;
    private FrameTimer                  _timer;
    private int _emtHomeX, _emtHomeY;
    private EmergencySite cur;
    private Hospital hos;
    //------------------ constructor -----------------------------
    /**
     * Default constructor.
     * 
     * @param hospitals ArrayList<Hospital>
     * @param sites ArrayList<EmergencySite>
     * @param emt EMTVehicle
     * @param line JLine
     * @param timer FrameTimer
     */
    public Dispatcher( 
                      ArrayList<Hospital> hospitals, 
                      ArrayList<EmergencySite> sites,
                      EMTVehicle emt, JLine line,
                      FrameTimer timer
                          // what else do you need or want?
                      )
    {
        _hospitals = hospitals;
        _sites = sites;
        _emt = emt;
        _emtHomeX = _emt.getHomePoint().x;
        _emtHomeY = _emt.getHomePoint().y;
        _path = line;
        cur = null;
        hos = null;
        _timer = timer;
    }
    //---------------------- setNextSite() -------------------------
    /**
     * identify the next emergency site, if there is one.
     * 
     * @return EmergencySite
     */
    private EmergencySite getNextSite()
    {
        EmergencySite next = null;
        //////////////////////////////////////////////////////////////
        // get next site from list of sites or from someone who has
        //    list of sites.
        //////////////////////////////////////////////////////////////
        
        if( _sites.size() != 0 )
            next = _sites.get( 0 );
        
        return next;
    }
    //--------------- getClosestHospital( Point ) --------------------
    /**
     * find the closest hospital site to the parameter
     *    This method is complete.
     * Note that we don't bother to compute the correct distance,
     *    we make the decision based on the square of the distance
     *    which saves the computation of the square root, a reasonably
     *    costly operation that serves no purpose for this test.
     * 
     * @param  loc  Point  location of emt vehicle
     * @return      Hospital closest hospital to vehicle at loc
     */
    private Hospital getClosestHospital( Point loc )
    {
        double distanceSq = Float.MAX_VALUE;
        Hospital   closest    = null;
        for ( Hospital h: _hospitals )
        {
            double d2 = loc.distanceSq( h.getLocation() );
            if ( d2 < distanceSq )
            {
                distanceSq = d2;
                closest = h;
            }
        }
        return closest;
    }
    
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++
    private boolean _animated = true;
    //---------------------- isAnimated() ----------------------------
    /**
     * Returns if animated.
     * 
     * @return _animated
     */
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) -----------------
    /**
     * Sets animated.
     * 
     * @param onOff Boolean
     */
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //--------------------------------------------------------------
    /**
     * A method newFrame calls to check if emt.isAnimated().
     */
    public void animate()
    {
        _emt.newFrame();
        
        if( hos != null && cur.wasVisited() )
        {
            _path.setPoints( _emt.getX(), _emt.getY(), hos.getLocation().x,
                            hos.getLocation().y );
            cur.setLocation( _emt.getX(), _emt.getY() );
        }
        else if( cur != null && !cur.wasVisited() )
        {
            cur.setDraggable( false );
            cur.setClickable( false );
            _path.setPoints( _emt.getX(), _emt.getY(), cur.getXLocation(),
                            cur.getYLocation() );
        }
        if( _emt.getGoalX() == _emtHomeX && 
            _emt.getGoalY() == _emtHomeY )
        {
            _path.setPoints( _emt.getX(), _emt.getY(), _emt.getX(), 
                            _emt.getY() );
            if( getNextSite() != null )
            {
                _emt.travelTo( cur.getXLocation(), cur.getYLocation(),
                              EMTApp.highSpeed );
            }
        }
    }
    
    /**
     * Check if emt is at goal.
     */
    public void checkGoal()
    {
        _timer.stop();
        _emt.setAnimated( false );
        // if cur isn't null find the closest hospital to go to.
        if( cur != null && _emt.getX() == cur.getXLocation() 
            && _emt.getY() == cur.getYLocation() )
        {
            hos = getClosestHospital( new Point( cur.getXLocation(), 
                                                 cur.getYLocation() ) );
            cur.setVisited( true );
        }
        
        // if hospital isn't null go there.
        if( hos != null && !cur.wasVisited() )
        {
            hos = null;
        }
        else if( hos != null )
        {
            _timer.start();
            _emt.travelTo( hos.getLocation().x, hos.getLocation().y, 
                           EMTApp.highSpeed );
            // if the EMTVehicle is at the hospital remove the eSite
            // and make the hospital null.
            if( _emt.getX() == hos.getLocation().x &&
                _emt.getY() == hos.getLocation().y )
            {
                hos = null;
                _sites.remove( cur );
                cur.setVisited( false );
                cur.setVisible( false );
                cur = null;
            }
        }
        
        if( hos == null && cur != null )
        {
            _timer.start();
            _emt.travelTo( cur.getXLocation(), cur.getYLocation(),
                           EMTApp.highSpeed );
        }
        // Travel home if there are no more eSites and hospitals to visit.
        if( hos == null && getNextSite() == null )
        {
            _timer.start();
            _emt.travelTo( _emtHomeX, _emtHomeY, EMTApp.normalSpeed );
        }
        else if( hos != null )
        {
            _timer.start();
            _emt.travelTo( hos.getLocation().x, hos.getLocation().y, 
                           EMTApp.highSpeed );
        }
    }
    
    /**
     * Check if _emt not animated.
     */
    public void notAnimated()
    {
        System.out.println( "home" );
        if( cur != null )
        {
            _timer.start();
            _emt.travelTo( cur.getXLocation(), cur.getYLocation(),
                           EMTApp.highSpeed );
        }
        
        if( hos != null && cur != null && cur.wasVisited() )
        {
            _timer.start();
            _emt.travelTo( hos.getLocation().x, hos.getLocation().y,
                           EMTApp.highSpeed );
        }
    }
    //---------------------- newFrame ------------------------------
    /**
     * invoked for each frame of animation; figure out what to do with
     *    the vehicle at this frame.
     */
    public void newFrame()
    {
        if( getNextSite() != null )
            cur = getNextSite();
        else
            cur = null;
        
        if( _emt.isAnimated() )
            animate();
        else if( _emt.atGoal() )
            checkGoal();
        else if( !_emt.isAnimated() )
            notAnimated();
    }
    //+++++++++++++++ end Animated interface +++++++++++++++++++++++++
}