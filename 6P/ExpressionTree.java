// imports ----------------------------------------------------------------
import java.util.*;

/**
 * Tree - This class is the main tree class. It stores the root node and
 * its left and right children. It also has a reference to the symbol table
 * just in case it needs to add values to it.
 * 
 * @author Travis Calley
 */
public class ExpressionTree
{
    // class variables -------------------------------------------------
    TreeNode root;
    
    // instance variables -------------------------------------------------
    Stack<OperatorNode> _opStack = new Stack<OperatorNode>();
    Stack<TreeNode> _randStack = new Stack<TreeNode>();
    
    /**
     * Empty constructor.
     */
    public ExpressionTree()
    {
        
    }
    
    /**
     * Recursively prints the trees subtree.
     * 
     * @param node TreeNode
     * @param depth int
     */
    public void print( TreeNode node, int depth )
    {
        if( node == null )
            return;
        
        print ( node.right, depth + 1 );
        
        String s = "";
        for( int i = 0; i < depth; i++ )
            s += "    ";
        s += node.getKey();
        System.out.println( s );
        print( node.left, depth + 1 );
    }
    
    /**
     * Prints the entire tree.
     */
    public void printTree()
    {
        print( root, 0 );
    }
    
    /**
     * Returns the precedence of an operator.
     * 
     * @param node OperatorNode
     * @return int
     */
    private int prec( OperatorNode node )
    {
        String key = node.getKey();
        if( key.equals( "*" ) || key.equals( "/" ) || key.equals( "%" ) )
            return 2;
        else if( key.equals( "+" ) || key.equals( "-" ) )
            return 1;
        else
            return 0;
    }
    
    /**
     * Sets the node and its left and right children.
     * 
     * @param op TreeNode
     */
    private void pushOpNode( TreeNode op )
    {
        OperatorNode opNode = (OperatorNode)op;
        opNode.right = _randStack.pop();
        opNode.left = _randStack.pop();
        _randStack.push( opNode );

        if( _opStack.isEmpty() || _opStack.peek().getKey().equals( "(" ) )
        {
            root = opNode;
            root.left = opNode.left;
            root.right = opNode.right;
        }
    }
    
    /**
     * Builds an ExpressionTree from the inputted line.
     * 
     * @param tokens ArrayList<EToken>
     */
    public void buildTree( ArrayList<EToken> tokens )
    {
        for( EToken token: tokens )
        {
            if( token instanceof Operand )
            {
                if( token instanceof Variable )
                    _randStack.push( new VariableNode( token.getName() ) );
                else
                    _randStack.push( new NumberNode( token.getValue() ) );
            }
            else if( token.getName().equals( "(" ) )
                _opStack.push( new OperatorNode( token.getName() ) );
            else if( token.getName().equals( ")" ) )
            {
                while ( !_opStack.peek().getKey().equals( "(" ) )
                    pushOpNode( _opStack.pop() );
                _opStack.pop();
            }
            else
            {
                OperatorNode temp = new OperatorNode( token.getName() );
                while( !_opStack.isEmpty() && 
                       prec( _opStack.peek() ) >= prec( temp ) )
                    pushOpNode( _opStack.pop() );
                _opStack.push( temp );
            }
        }
        
        while( !_opStack.isEmpty() )
        {
            pushOpNode( _opStack.pop() );
        }
    }
    
    /**
     * Main method that calls TreeUtilities to test different
     * ExpressionTree functions.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        TreeUtilities.main( args );
    }
}