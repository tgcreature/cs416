/**
 * InvalidOperand - A subclass of Operand representing an invalid 
 * type of token in an expression. An InvlaidOperand is any of the 
 * following symbols:
 *     123aaa 1a
 * 
 * @author Travis Calley
 */
public class InvalidOperand extends Operand
{
    // instance variables -------------------------------------------------
    private String _invalidOperand;
    
    /**
     * Default constructor.
     * 
     * @param token String
     */
    public InvalidOperand( String token )
    {
        _invalidOperand = token;
    }
    
    /**
     * Converts the token to a printable String.
     * 
     * @return String
     */
    public String toString()
    {
        return "<ERROR>";
    }
}