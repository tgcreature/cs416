//----------------------- DrawPanel.java ------------------------------
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/** 
 * DrawPanel.java: A panel for displaying the application graphics.
 *
 * @author Matthew Plumlee
 * modified by rdb
 * CS416 Spring 2008
 * Last edited: 
 * 03/17/14  rdb: made checkstyle-compatible
 */

public class DrawPanel extends JPanel 
{
    //------------------- instance variables ------------------------
    private BSTNode   _bst   = null;          // current list to be displayed
    private ArrayList<GNode> _glist = null;  // list of graphical nodes.
    
    //------------- magic constants
    private int       _deltaX = 1; // space between nodes
    private int       _deltaY = 20;
    private int       _firstX = 10;    // x loc for first node on a row
    private int       _firstY = 0 ;    // y loc for first row
    private int       _nextX;     // location for next node to be added
    private int       _nextY;
    private int       _nodeWidth = 45;  // space for the node
    private int       _labelWidth = 40; // width of the label for the node
    private int       _maxLabelWidth = 60; // max size of JLabel for node
    private int       _labelXoffset = 0;
    private int       _nodeHeight = 30;
    private int       _stepX      = _deltaX + _nodeWidth;
    private int       _stepY      = _deltaY + _nodeHeight;
    private int       _defaultW   = 700;
    private int       _defaultH   = 500;
    
    
    //--------------------- constructor ----------------------------
    /**
     * No arguments needed for constructor.
     */
    public DrawPanel () 
    {
        super();
        setLayout( null );
        //setSize( new Dimension( _defaultW, _defaultH ) );
        setPreferredSize( new Dimension( _defaultW, _defaultH ) );
        setBackground( new Color( 240, 240, 240 ) );
        
    }
    //----------------------- setTree( tree ) ----------------
    /**
     * Define a new tree to display.
     * @param bst BSTNode    the tree
     */
    public void setTree( BSTNode bst )
    {
        _bst = bst;
        update();
    }
    //----------------------  setNodeWidth( int ) -------------------
    /**
     * Change the width of each node.
     * @param newW int       width
     */
    public void setNodeWidth( int newW )
    {
        if ( newW > 0 )
        {
            _nodeWidth = newW;
            _stepX = _nodeWidth + _deltaX;
            _labelWidth = Math.min( _maxLabelWidth, _nodeWidth );
            _labelXoffset = 0;
            if ( _nodeWidth > _maxLabelWidth )
            {
                _labelWidth = _maxLabelWidth;
                _labelXoffset = ( _nodeWidth - _maxLabelWidth ) / 2;
            }
        }
        update();
    }
    //------------------------ update() -----------------------------
    /**
     * Update the graphical representation of the list being shown.
     */
    public void update()
    {
        //if ( _bst == null )
        //return;
        
        int treeH = height( _bst );
        if( treeH == 0 )
            treeH = 6;
        int leafCount = (int) Math.pow( 2, treeH - 1 );
        
        int width = leafCount * _stepX;
        int height = treeH * _stepY;
        
        setPreferredSize( new Dimension( width, height ) );
        
        cleanupGNodes();
        
        _glist = new ArrayList<GNode>();
        GNode groot = prefixWalk( _bst, _firstX + width / 2, width / 2, 0 );
        
        this.revalidate();
        this.repaint();
    } 
    //--------------------- height( BSTNode ) ----------------------------
    /**
     * Get height of the tree rooted at this node.
     * @param node BSTnode
     * @return int
     */
    private int height( BSTNode node )
    {
        if ( node == null ) 
            return 0;
        return Math.max( height( node.left ), height( node.right ) ) + 1;
    }
    //--------------------- prefixWalk( Node, .... ) -----------------
    /**
     * Walk the tree via a prefix walk.
     * @param node BSTNode        current node in tree
     * @param xc   int            center of node position
     * @param w    int            width of node
     * @param depth int           depth of node in tree
     * @return GNode
     */
    private GNode prefixWalk( BSTNode node, 
                             int xc, int w, int depth )
    {
        if ( node == null )
            return null;
        
        GNode gnode = new GNode( node.data.toString(), 
                                _labelWidth, _nodeHeight );
        _glist.add( gnode );
        this.add( gnode );
        int xLoc = xc - _labelWidth / 2 + _labelXoffset;
        gnode.setLocation( xLoc, _firstY + depth * _stepY );
        gnode.left  = prefixWalk( node.left,  xc - w / 2, w / 2, depth + 1 );
        gnode.right = prefixWalk( node.right, xc + w / 2, w / 2, depth + 1 );
        return gnode;
    }
    
    //----------------------- setPosition ----------------------------
    /**
     * Determine the location of the "next" node to be displayed.
     * @param label GNode     find location for this GNode
     */
    public void setPosition( GNode label )
    {
        label.setLocation( _nextX, _nextY );
        _nextX += _deltaX + _nodeWidth;
        if ( _nextX + _nodeWidth > _defaultW )
        {
            _nextX = _firstX;
            _nextY += _deltaY + _nodeHeight;
        }
        label.repaint();
    }
    
    //---------------------- cleanupGNodes() ----------------------------
    /**
     * When we change to a new DS, need to get all the JComponents
     * that have been added to the JPanel and remove them!
     */
    private void cleanupGNodes()
    {
        if ( _glist == null )
            return;
        Iterator<GNode> iter = _glist.iterator();
        while ( iter.hasNext() )
        {
            GNode gNode = iter.next();
            this.remove( gNode );
        }
    }
    //-------------------- paintComponent ------------------------------
    /**
     * Draw the arrows between GNodes in the tree.
     * @param g Graphics
     */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        if ( _glist != null && _glist.size() > 1 )
        {        
            Graphics2D g2 = (Graphics2D) g;
            Arrow      leftArrow = new Arrow();
            leftArrow.setColor( Color.BLACK );
            
            Arrow      rightArrow = new Arrow();
            rightArrow.setColor( Color.RED );
            
            Iterator<GNode> iter = _glist.iterator();
            
            while ( iter.hasNext() )
            {
                GNode nCur  = iter.next();       
                if ( nCur.left != null )
                {
                    leftArrow.setLine( nCur.getStart(), nCur.left.getEnd() );
                    leftArrow.draw( g2 );
                }
                if ( nCur.right != null )
                {
                    rightArrow.setLine( nCur.getStart(), nCur.right.getEnd() );
                    rightArrow.draw( g2 );
                }
            }
        }
    }
}