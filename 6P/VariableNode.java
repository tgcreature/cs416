/**
 * VariableNode - A class to represent a Variable in the Tree as a TreeNode.
 * This class makes it easier to evaluate the expression tree.
 * 
 * @author Travis Calley
 */
public class VariableNode extends TreeNode
{
    // instance variables -------------------------------------------------
    private SymbolTable    _symbolTable;
    private float          _value;
    private String         _key;
    
    // class variables ----------------------------------------------------
    TreeNode               left;
    TreeNode               right;
    
    /**
     * Default constructor.
     * 
     * @param name String
     */
    public VariableNode( String name )
    {
        _key = name;
        _symbolTable = SymbolTable.instance();
    }
    
    /**
     * Returns the value of this VariableNode.
     * 
     * @return _value;
     */
    public float getValue()
    {
        _value = _symbolTable.getValue( _key );
        return _value;
    }
    
    /**
     * Returns the key.
     * 
     * @return _key
     */
    public String getKey()
    {
        return _key;
    }
}