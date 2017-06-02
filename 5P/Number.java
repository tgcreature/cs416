/**
 * Number - A subclass of Operand representing a specific type of token in
 * an expression. A Number is any of the following symbols:
 *     1 2 3.14159 -88 12.32e45
 * 
 * @author Travis Calley
 */
public class Number extends Operand
{
    // instance variables -------------------------------------------------
    private float _number;
    
    /**
     * Default constructor.
     * 
     * @param token String
     */
    public Number( String token )
    {
        _number = Float.parseFloat( token );
    }
    
    /**
     * Converts number to a printable string.
     * 
     * @return String
     */
    public String toString()
    {
        return "@" + _number;
    }
}