// imports ----------------------------------------------------------------
import java.util.*;

/**
 * TreeUtilities - This is a utility class that has many static methods.
 * One method is made to build a tree from a String. One is evaluating an
 * ExpressionTree. Another one is printing out the tree sideways.
 * 
 * @author Travis Calley
 */
public class TreeUtilities
{
    /**
     * Evaluates the operator and the children nodes. If the operator
     * is not an equals sign evaluate as normal. Otherwise, check if
     * the left node is a VariableNode and if it is not print an error.
     * 
     * @param op TreeNode
     * @param left float
     * @param right float
     * @return float
     */
    private static float evaluate( TreeNode op, float left, float right )
    {
        if( op.getKey().equals( "+" ) )
            return left + right;
        else if( op.getKey().equals( "-" ) )
            return left - right;
        else if( op.getKey().equals( "*" ) )
            return left * right;
        else if( op.getKey().equals( "/" ) )
            return left / right;
        else if( op.getKey().equals( "%" ) )
            return left % right;
        else
        {
            if( op.left instanceof VariableNode )
            {
                SymbolTable.instance().setValue( op.left.getKey(), right );
                return right;
            }
            else
            {
                System.err.println( "Invalid assignment: \"" + 
                                     op.left.getValue() + 
                                     "\" is not a variable." );
                return 0.0f;
            }
        }
    }
    
    /**
     * Finds the value of this current node recursively.
     * 
     * @param node TreeNode
     * @return float
     */
    private static float processNode( TreeNode node )
    {   
        if( node instanceof NumberNode )
            return node.getValue();
        else if( node instanceof VariableNode )
            return node.getValue();
        else if( node instanceof OperatorNode )
        {   
            float left = processNode( node.left );
            float right = processNode( node.right );
            return evaluate( node, left, right );
        }
        else
            return 0.0f;
    }
    
    /**
     * Takes a tree and return the float value after every node
     * has been evaluated.
     * 
     * @param tree ExpressionTree
     * @return float
     */
    public static float processTree( ExpressionTree tree )
    {
        if( tree.root != null )
            return processNode( tree.root );
        return 0.0f;
    }
    
    /**
     * Main method to thoroughly test the methods in this class.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        SymbolTable.instance().setValue( "a", 5 );
        String divider = "=======================================";
        ExpressionTree tree = new ExpressionTree();
        tree.root = new OperatorNode( "*" );
        tree.root.left = new OperatorNode( "-" );
        tree.root.left.left = new VariableNode( "a" );
        tree.root.left.right = new OperatorNode( "/" );
        tree.root.left.right.left = new NumberNode( 8 );
        tree.root.left.right.right = new NumberNode( 4 );
        tree.root.right = new OperatorNode( "+" );
        tree.root.right.left = new NumberNode( 4 );
        tree.root.right.right = new NumberNode( 7 );
        
        System.out.println( "Result: " + processTree( tree ) );
        
        System.out.println( divider );
        
        tree.printTree();
        
        System.out.println( divider );
        
        ExpressionTree assign = new ExpressionTree();
        tree.root = new OperatorNode( "=" );
        tree.root.left = new NumberNode( 10 );
        tree.root.right = new NumberNode( 15 );
        
        System.out.println( "Result: " + processTree( tree ) );
        //System.out.println( "b: " + SymbolTable.instance().getValue( "b" ) );
        
        System.out.println( divider );
        
        ArrayList<EToken> tokens = new ArrayList<EToken>();
        tokens.add( new Number( "5" ) );
        tokens.add( new Operator( "+" ) );
        tokens.add( new Number( "10" ) );
        
        ExpressionTree tree2 = new ExpressionTree();
        tree2.buildTree( tokens );
        tree2.printTree();
        System.out.println( "Result: " + processTree( tree2 ) );
        
        System.out.println( divider );
        
        ExpressionTree tree3 = new ExpressionTree();
        ArrayList<EToken> toks = new ArrayList<EToken>();
        toks.add( new Operator( "(" ) );
        toks.add( new Number( "5" ) );
        toks.add( new Operator( "*" ) );
        toks.add( new Number( "5" ) );
        toks.add( new Operator( ")" ) );
        tree3.buildTree( toks );
        tree3.printTree();
        System.out.println( "Result: " + processTree( tree3 ) );
        
        System.out.println( divider );
        
        ExpressionTree tree4 = new ExpressionTree();
        ArrayList<EToken> list = new ArrayList<EToken>();
        list.add( new Variable( "a" ) );
        list.add( new Operator( "=" ) );
        list.add( new Number( "3" ) );
        list.add( new Operator( "*" ) );
        list.add( new Number( "3" ) );
        tree4.buildTree( list );
        tree4.printTree();
        System.out.println( "Result: " + processTree( tree4 ) );
    }
}