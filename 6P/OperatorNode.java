/**
 * OperatorNode - Uses the given operator to evaluate its left and right
 * nodes.
 * 
 * @author Travis Calley
 */
public class OperatorNode extends TreeNode
{
    // instance variables -------------------------------------------------
    private String      _key;
    
    // class variables ----------------------------------------------------
    TreeNode            left;
    TreeNode            right;
    
    /**
     * Default constructor.
     * 
     * @param operator String
     */
    public OperatorNode( String operator )
    {
        _key = operator;
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