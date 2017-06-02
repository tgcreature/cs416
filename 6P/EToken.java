/**
 * EToken - an abstract class representing a token in an expression.
 *             subclasses are Operator and Operand
 * 
 * @author Travis Calley
 */
public abstract class EToken
{
    /**
     * Converts a token into a printable string.
     * 
     * @return String
     */
    public String toString()
    {
        return "(Base Token)";
    }
    
    /**
     * Get value.
     *
     * @return float
     */
    public float getValue()
    {
        return 0.0f;
    }
    
    /**
     * Gets the name.
     * 
     * @return String
     */
    public String getName()
    {
        return "(EToken Name)";
    }
    
    //------------------ main unit test ------------------------
    /**
     * some basic unit tests.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {

    }
}
