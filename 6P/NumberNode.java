/**
 * Number node takes a Number as it's value and stores it in a Node in the
 * Tree, this class makes it easier to know that the value being evaluated is
 * a number and nothing else has to be done except for return the value.
 * 
 * @author Travis Calley
 */
public class NumberNode extends TreeNode
{
    // instance variables -------------------------------------------------
    private float        _value;
    private String       _key;
    
    // class variables ----------------------------------------------------
    TreeNode               left;
    TreeNode               right;
    
    /**
     * Default constructor.
     * 
     * @param val float
     */
    public NumberNode( float val )
    {
        _value = val;
        _key = String.valueOf( _value );
    }
    
    /**
     * Returns the value of this NumberNode.
     * 
     * @return _value
     */
    public float getValue()
    {
        return _value;
    }
    
    /**
     * Gets the key.
     * 
     * @return _key
     */
    public String getKey()
    {
        return _key;
    }
}