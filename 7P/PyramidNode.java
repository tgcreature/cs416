//++++++++++++++++++++++++++++++++ PyramidNode ++++++++++++++++++++++++++
import java.awt.Point;

/**
 * Node class for pyramid data structure. 
 *     Pyramid level d as exactly d+1 nodes.
 *     Nodes have location and size information even though they
 *        are not themselves displayed. The idea is that the "data"
 *        object (of type T, which must implement the Geometry interface)
 *        are likely to be displayed. The size information comes from
 *        those objects -- although they should be the same size or 
 *        things won't work very well. The location information is 
 *        determined by the pyramid structure.
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * The starting code for this class is barely enough to allow the
 * starting package to "appear" to play the game. This class is 
 * primarily used in the starter code to "save" the location in the
 * pyramid of a Card that has been played. This allows the "Undo" to
 * restore the card to its place in the pyramid.
 * 
 * You will surely need additional code here and may want to rewrite
 * some of what is here.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 
 * @author Travis Calley
 * 
 * @param <T> extends Geometry
 */
class PyramidNode<T extends Geometry >  implements Geometry
{
    //--------------------- instance variables --------------------
    private PyramidNode<T>   _left;
    private PyramidNode<T>   _right;

    private int       _xLoc;
    private int       _yLoc;
    private int       _width;
    private int       _height; 
    private Point     _prevLoc;
    private int       _level;
    
    private T         _data;        // 
    private boolean   _hasData;     // true if data object is in pyramid
    
    //----------------- constructors -----------------------------
    /**
     * Construct a PyramidNode with position and size information.
     * @param x int
     * @param y int
     * @param w int
     * @param h int
     */
    public PyramidNode( int x, int y, int w, int h )
    {
        _left = null;
        _right = null;
        _data = null;
        _hasData = false;
        _xLoc = x;
        _yLoc = y;
        _width = w;
        _height = w;
    }
    //------------------ setData( T content ) -------------------------
    /**
     * Define the data content to be saved in this node.
     * 
     * @param content T
     */
    public void setData( T content )
    {
        _data = content;
        _hasData = true;
    }
    //------------------ getData() -------------------------
    /**
     * Get the data content saved in this node.
     * @return T
     */
    public T getData()
    {
        return _data;
    }
    //------------------ toString() ------------------------------
    /**
     * Return a string representation for the node.
     * @return String
     */
    public String toString()
    {
        return "{." + _left + "." + _right + ":" + _data + "}";
    }
    //++++++++++++++++++++++ Geometry interface +++++++++++++++++++++
    //------------------ getX() ---------------
    /**
     * Return the x value of the object's location.
     * @return int
     */
    public int getX()
    {
        return _xLoc;
    }
    //------------------ getY() ---------------
    /**
     * Return the y value of the object's location.
     * @return int
     */
    public int getY()
    {
        return _yLoc;
    }
    //------------------ getLocation() ---------------
    /**
     * Return the object's location as a Point.
     * @return Point
     */    
    public Point getLocation()
    {
        return new Point( _xLoc, _yLoc );
    }
    //------------------ getHeight() ---------------
    /**
     * Return the height of the object's bounding box.
     * @return int
     */    
    public int getHeight()
    {
        return _height;
    }
    //------------------ getWidth() ---------------
    /**
     * Return the height of the object's bounding box.
     * @return int
     */    
    public int getWidth()
    {
        return _width;
    }
    //-----------------getLeft()------------------
    /**
     * Gets left child node.
     * 
     * @return PyramidNode<T>
     */
    public PyramidNode<T> getLeft()
    {
        return _left;
    }
    //-----------------getRight()------------------
    /**
     * Gets right child node.
     * 
     * @return PyramidNode<T>
     */
    public PyramidNode<T> getRight()
    {
        return _right;
    }
    //----------------setLeft()-------------------
    /**
     * Gets left child node.
     * 
     * @param node PyramidNode<T>
     */
    public void setLeft( PyramidNode<T> node )
    {
        _left = node;
    }
    //----------------setRight()-------------------
    /**
     * Gets right child node.
     * 
     * @param node PyramidNode<T>
     */
    public void setRight( PyramidNode<T> node )
    {
        _right = node;
    }
    //------------------setPrevPoint( Point )-----------------------
    /**
     * Sets previous point before a move.
     * 
     * @param p Point
     */
    public void setPrevPoint( Point p )
    {
        _prevLoc = p;
    }
    //-------------------getPrevPoint()------------------------------
    /**
     * Gets the previous point before a move.
     * 
     * @return _prevLoc
     */
    public Point getPrevPoint()
    {
        return _prevLoc;
    }
    //--------------------------getLevel()---------------------------
    /**
     * Gets the level of the PyramidNode.
     * 
     * @return _level
     */
    public int getLevel()
    {
        return _level;
    }
    //----------------------setLevel( int )--------------------------
    /**
     * Sets the level of pyramidNode.
     * 
     * @param l int
     */
    public void setLevel( int l )
    {
        _level = l;
    }
}
