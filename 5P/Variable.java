/**
 * Variable - A subclass of Operand representing a specific type of token in
 * an expression. A Variable is any of the following symbols:
 *     aaa sales_tax _circleArea box500w $directory abc123$_
 * 
 * @author Travis Calley
 */
public class Variable extends Operand
{
    // instance variables -------------------------------------------------
    private String _variable;
    
    /**
     * Default constructor.
     * 
     * @param token String
     */
    public Variable( String token )
    {
        _variable = token;
    }
    
    /**
     * Converts the token to a printable String.
     * 
     * @return String
     */
    public String toString()
    {
        return "@" + _variable;
    }
}