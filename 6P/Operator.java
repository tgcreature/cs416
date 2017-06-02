/**
 * Operator - A subclass of EToken representing a specific type of token in
 * an expression. An operator is any of the following symbols:
 *     + - / * % ( ) =
 * 
 * @author Travis Calley
 */
public class Operator extends EToken
{
    // instance variables -------------------------------------------------
    private String _operator;
    
    /**
     * Default constructor.
     * 
     * @param token String
     */
    public Operator( String token )
    {
        _operator = token;
    }
    
    /**
     * Returns a printable string of the given operator.
     * 
     * @return String
     */
    public String toString()
    {
        return "<" + _operator + ">";
    }
    
    /**
     * Get name.
     * 
     * @return String
     */
    public String getName()
    {
        return _operator;
    }
}