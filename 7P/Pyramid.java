//+++++++++++++++++++++++++ Pyramid.java ++++++++++++++++++++++++++++
import javax.swing.JPanel;
import java.awt.*;
import java.util.*;

/**
 * Pyramid - Represent a directed acyclic graph that defines a 
 *              pyramid organization suitable for the PyramidSolitaire
 *              game. The goal of this class is to focus it on the 
 *              data structure and expect all real game and graphics
 *              functionality to be done elsewhere. The nodes of the
 *              Pyramid, however, do have size and location information
 *              and the Pyramid has a top card location.
 * 
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * This is a very skeleton implementation. You do NOT need to use any
 * or all of the features implied by this skeleton. These are just
 * suggestions that might be useful. 
 * 
 * AND more methods are certainly needed.
 * 
 * The only requirements are:
 *    1. You should use the PyramidNode class to represent each node in
 *       the "tree".
 *    2. Each PyramidNode object needs to have references to its left
 *       and right children nodes (if it has them).
 *    3. Your test for whether you can  move a card should be based on
 *       the information in Pyramid and/or PyramidNode.
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * @author Travis Calley
 * 
 * @param <T>    Pyramid nodes have a data field of type T
 */
public class Pyramid<T extends Geometry> 
{
    //-------------------- instance variables --------------------
    private int  _rows;
    private int  _xCenter;    // x is CENTER of top card of pyramid
    private int  _yCenter;    // y center of top card
    private int  _nWidth;
    private int  _nHeight;
    private PyramidNode<T> _root;

    //--------------------- constructor --------------------------
    /**
     * Constructor.
     * 
     * @param nRows int        number rows in the pyramid
     * @param x int            x location of center of the pyramid
     * @param y int            y location of center of top card
     * @param nW int           width of nodes of pyramid
     * @param nH int           height of nodes of pyramid
     */
    public Pyramid( int nRows, int x, int y, int nW , int nH )
    {
        _rows = nRows;
        _xCenter = x;
        _yCenter = y;
        _nWidth = nW;
        _nHeight = nH;
    }
    
    //---------------- setRoot( PyramidNode ) -----------------------
    /**
     * Sets the root of the BinaryTree.
     * 
     * @param node PyramidNode<T>
     */
    public void setRoot( PyramidNode<T> node )
    {
        _root = node;
    }

    //---------------- clear( PyramidNode ) --------------------------
    /**
     * Recursively clears the pyramid.
     * 
     * @param node PyramidNode<T>
     */
    public void clear( PyramidNode<T> node )
    {
        if( node.getLeft() == null )
            return;
        else if( node.getRight() == null )
            return;
        else
        {
            clear( node.getLeft() );
            clear( node.getRight() );
            node = null;
        }
    }
    
    //----------------- clear( ) -------------------------------------
    /**
     * Empty all nodes of content.
     */
    public void clear()
    {
        clear( _root );
    }
    
    //------------------- getNode( int, int  ) -----------------------
    /**
     * Get the PyramidNode at level l, position p. 
     * 
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * This is a particularly questionable method. It's not clear that
     * this specific level of information is really necessary. I used
     * it in my first solution, but as I transitioned that solution into
     * starter code + solution, I realized that it should be pretty 
     * straightforward to come up with a solution that doesn't need it.
     * 
     * Feel free to use it or not.
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * 
     * @param l int     level of pyramid
     * @param p int     position in row ( 0 .. l-1 )
     * @throws IndexOutOfBoundsException
     * @return PyramidNode<T>
     */
    public PyramidNode<T> getNode( int l, int p )  // get l,n node
    { 
        return _root;
    }
    //------------------- getRoot() -----------------------
    /**
     * Get the root PyramidNode. 
     * 
     * @return PyramidNode
     */
    public PyramidNode<T> getRoot()  // get root node
    { 
        return _root;
    }
    //-------------------------- isEmpty() ----------------------------
    /**
     * One way of making this test is to assume that the entire pyramid
     *    is "empty" if the top element does not contain valid data.
     * 
     * @return boolean    true if no valid data is in the pyramid.
     */
    public boolean isEmpty()
    {
        if( _root == null )
            return true;
        else
            return false;
    }
    
    /**
     * Recursively prints the trees subtree.
     * 
     * @param node TreeNode
     * @param depth int
     */
    public void print( PyramidNode<T> node, int depth )
    {
        if( node == null )
            return;
        
        print ( node.getRight(), depth + 1 );
        
        String s = "";
        for( int i = 0; i < depth; i++ )
            s += "    ";
        s += node.getData().toString();
        System.out.println( s );
        print( node.getLeft(), depth + 1 );
    }
    
    /**
     * Prints the entire tree.
     */
    public void printTree()
    {
        print( _root, 0 );
    }
    
    //--------------------------- main --------------------------------
    /**
     * This main is a convenience call to the application.
     * 
     * @param args String[]            command line arguments.
     */
    public static void main( String[] args )
    {
        // Invoke main class's main
        PyramidSolitaire.main( args );
    }
}
