/**
 * TreeNode - An abstract class used to represent three different types 
 * of TreeNodes. This class has three subclasses: NumberNode, VariableNode,
 * and OperatorNode. They are used to store different values in the TreeNodes
 * and are used to easily identify what must be done when evaluating 
 * an expression tree.
 * 
 * @author Travis Calley
 */
public abstract class TreeNode
{
    TreeNode          left;
    TreeNode          right;
    private String    _key;
    
    /**
     * Gets the value.
     * 
     * @return value
     */
    public float getValue()
    {
        return 0.0f;
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