//---------------------- BinarySearchTree.java -------------------------
import java.util.*;
import java.io.*;
/**
 * BinarySearchTree -- this class uses a private inner class for
 *      tree nodes. (Although in this version it is public so I can 
 *      do a prefix walk in DrawPanel.)
 *
 * @author rdb
 * April 2, 2008
 * 
 * Modified: April 6
 *      added iterator and node deletion code.
 * 
 * mlb Nov 2008 for BSTrecursion
 * rdb April 1, 2009 for revised BSTrecursion
 *           Students implement height(), sum(), nodeDepth(), rebuild()
 * rdb March 2011: height, nodeDepth, setBalance, rebuild
 * rdb March 2013: Added batch mode
 *                 height, inOrderString, find, findMax
 * rdb March 2014: reformatted; made checkstyle-correct
 */

public class BinarySearchTree 
{
    //-------------------- instance variables ---------------------
    private Node   _root;
    private int    _size;
    
    //-------------------- constructors --------------------------
    /**
     * Construct an empty tree with no nodes.
     */
    public BinarySearchTree()
    {
        _root = null;
        _size = 0;
    }
    /**
     * Construct a tree with a root.
     * @param rootData Data    data for root node
     */
    public BinarySearchTree( Data rootData )
    {
        _root = new Node( rootData );
        _size = 1;
    }
    //------------------ inOrderString() ------------------------------
    /**
     * Traverse the tree in in-order fashion to generate a string 
     * representing the nodes of the tree.
     * 
     * This method uses a private utility method to generate a string for
     * a subtree rooted at a particular node.
     * 
     * @return String
     */
    public String inOrderString()
    {
        return inOrderString( _root );
    }
    //------------------ inOrderString( node ) ---------------
    /**
     * Generate String representation of subtee rooted at "node".
     * 
     * @param n Node 
     * @return String
     */
    private String inOrderString( Node n )
    {
        ////////////////////////////////////////////////////////////////
        // Append the string for the node's Data object after generating 
        // the left subtree and before generating the right subtree
        //
        // Don't forget the base case
        ////////////////////////////////////////////////////////////////
        
        String result = "";
        
        if( n != null )
        {
            result += inOrderString( n.left );
            result += " " + n.data.toString() + " ";
            result += inOrderString( n.right );
        }
        
        return result;
    }
    //-------------------- height() ----------------------------------
    /**
     * The height of the tree is the depth of the deepest node.
     * 
     * @return int
     */
    public int height()
    {
        return height( _root );
    }
    //------------------- height( Node ) ----------------------
    /**
     * The height of a node of the tree is the maximum number of steps
     * to reach a leaf from this node.
     * 
     * @param n Node         subtree whose height is returned
     * @return int           height of Node n
     */
    private int height( Node n )
    {
        ///////////////////////////////////////////////////////////////
        // Height of a node is 1 more than the max height of its children
        //
        // Don't forget the base case
        ///////////////////////////////////////////////////////////////
        
        if( n == null )
            return 0;
        else
            return 1 + Math.max( height( n.left ), height( n.right ) );
    }
    
    //-------------------- find( String ) -------------------------
    /**
     * Given a key value, search the tree to find the node that has
     * that key value, if it exists.
     * 
     * Return the Data object from the node or null.
     * 
     * @param key String     string to search for as a key in the tree
     * @return Data          if id found, return Data object for id
     *                       else return null
     */
    public Data find( String key )
    {
        return find( _root, key );
    }
    
    //------------------ find( Node, String ) ----------------------
    /**
     * Recursion is done in this method.
     *   return the Data object with the specified key 
     *   if it is in the subtree rooted at Node n.
     * 
     * @param n Node         subtree to search for key
     * @param key String     key to search for
     * @return Data          Data object containing found key, or null
     */
    private Data find( Node n, String key )
    {
        ///////////////////////////////////////////////////////////
        // If searchKey equals node's data key, return its Data object.
        // Otherwise if the searchKey < node's key, recurse down
        //   the left subtree, else recurse down the right subtree
        //
        // Don't forget you have to check for null references somewhere;
        //   that's the sign that the searchKey isn't in the tree
        ///////////////////////////////////////////////////////////
        
        if( key.equals( n.data.key ) )
            return n.data;
        else if( key.compareTo( n.data.key ) < 0 )
        {
            if( n.left == null )
                return null;
            else
                return find( n.left, key );
        }
        else
        {
            if( n.right == null )
                return null;
            else
                return find( n.right, key );
        }
        
    }
    
    //-------------------- maxFind() --------------------------------
    /**
     * Find the node in the tree that has the  maximum value field.
     * Remember the tree is organized by "key", not "value", so you need
     * to traverse the entire tree. It doesn't matter what traversal
     * you do. However, it make sense to define a  method that finds the
     * max value in a subtree and recursively call that for each child
     * subtree.
     * 
     * @return Data   returned Data containing max value,
     *                or null if tree is empty.
     */
    public Data maxFind()
    {
        return maxFind( _root );
    }
    //-------------------- maxFind( Node ) ----------------------------
    /**
     * Find maximum value in a subtree. Each invocation finds the max of 
     * the left subtree, the max of the right subtree, and returns the 
     * node with the max of those 2 values and the Data from that node.
     * 
     * @param n Node       subtree to find max data for
     * @return Data        the Data object with the max data value
     */
    private Data maxFind( Node n )
    {
        ///////////////////////////////////////////////////////////////
        // Method should return the max Data object from the subtree
        //     whose root is "n".
        // It needs to compare the value of n's Data object with the max
        //     of the Data objects returned from the left and right 
        //     subtrees and return the one with the largest value.
        ///////////////////////////////////////////////////////////////
        
        if( n == null )
            return null;
        
        if( n.right == null && n.left == null )
            return n.data;
        else
        {
            Data left = maxFind( n.left );
            Data right = maxFind( n.right );
            
            if( left == null )
            {
                if( n.data.value > right.value )
                    return n.data;
                else
                    return right;
            }
            else if( right == null )
            {
                if( n.data.value > left.value )
                    return n.data;
                else
                    return left;
            }
            else
            {
                if( left.value > right.value )
                {
                    if( n.data.value > left.value )
                        return n.data;
                    else
                        return left;
                }
                else
                {
                    if( n.data.value > right.value )
                        return n.data;
                    else
                        return right;
                }
            }
        }
            
    }   
    //////////////////////////////////////////////////////////////////
    // MAKE NO CHANGES BELOW HERE
    //////////////////////////////////////////////////////////////////
    
    //--------------------- root() ----------------------------------
    /**
     * Return the root of the tree. This is package access so DrawPanel
     * can do a prefix walk of the tree. Would be better to have multiple
     * iterators.
     * @return Node
     */
    BinarySearchTree.Node root()
    {
        return _root;
    }
    //--------------------- add -----------------------------------
    /**
     * Add a node to the tree in its proper position determined by the
     * "key" field of the Data object. This method uses the addNode 
     * recursive utility method.
     * 
     * @param data Data         Data object to be added to the tree.
     */
    public void add( Data data )
    {
        boolean added = true;
        if ( _root == null )
            _root = new Node( data );
        else
            added = addNode( _root, data );
        if ( added )
            _size++;
    }
    
    //------------------ addNode( Node, Data ) -----------------------
    /**
     * A recursive method to add a new Data object to the subtree
     * rooted at the first argument.
     * 
     * @param parent Node       subtree to which Data is to be added
     * @param newOne Data       Data object to be added to the tree
     * @return boolean          true if add was successful
     */
    private boolean addNode( Node parent, Data newOne )
    {
        boolean added = true;
        int cmp = newOne.compareTo( parent.data );
        if ( cmp < 0 )
        {
            if ( parent.left != null )
                added = addNode( parent.left, newOne );
            else 
            {
                parent.left = new Node( newOne );
                parent.left.parent = parent;
            }
        }
        else if ( cmp == 0 )
        {
            System.err.println( "== key found: Not adding: " + newOne );
            added = false;
        }
        else
        {
            if ( parent.right != null )
                added = addNode( parent.right, newOne );
            else 
            {
                parent.right = new Node( newOne );
                parent.right.parent = parent;
            }
        }
        return added;
    }
    //-------------------- size(  ) -------------------------
    /**
     * Return the number of nodes in the tree.
     * @return int   size of tree
     */
    public int size()
    {
        return _size;
    }
    //-------------------------- toString() -------------------------
    /**
     * Generate a string representation of the tree.
     * 
     * @return String       a string representation of the tree.
     */
    public String toString()
    {
        return toString( _root, "   ", "=" ) ;        
    }
    
    /**
     * Recursively generate a string representation for a Node of a tree;
     *    indent is increased for increasing depth.
     * Branch is a short string that prefixes each node indicating
     *        whether the node is a left (L) or right (R) child.
     * @return String        string rep of subtree
     * @param n Node         subtree to convert to string
     * @param indent String  prefix string for indentation level
     * @param branch String  (L) or (R)
     */
    private String toString( Node n, String indent, String branch )
    {
        String s = "";
        if ( n != null )
        {
            String prefix = indent.substring( 0, indent.length() - 2 ) 
                + branch;
            s += prefix + n.data.toString() + "\n";
            if ( n.left != null )
                s += toString( n.left, indent + "  ", "L " );
            if ( n.right != null )
                s += toString( n.right, indent + "  ", "R " );
        }
        return s;
    }
    //+++++++++++++++++++++++ inner class Node ++++++++++++++++++++++
    /**
     * The Node class does not have to be seen outside this class, so
     * it is private.
     * @author rdb
     */
    public class Node
    {
        //-------------- instance variables ---------------------------
        Data data;
        Node left;
        Node right;
        Node parent;
        
        //--------------- constructor --------------------------------
        /**
         * Construct a node with Data.
         * 
         * @param  d Data    data for the node.
         */
        public Node( Data d )
        {
            data = d;
            left = null;
            right = null;
            parent = null;
        }
    }
    //--------------------- main -------------------------------------
    /**
     * A convenience main that runs the app for this problem.
     * @param args String[]   command line args.
     */
    public static void main( String[] args )
    {
        TreeRecursion.main( args );
    }
}