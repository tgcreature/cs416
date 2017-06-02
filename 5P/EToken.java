
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
    
    //------------------ main unit test ------------------------
    /**
     * some basic unit tests.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        try
        {
            EToken plus  = TokenFactory.makeToken( "+" );
            EToken times = TokenFactory.makeToken( "*" );
            EToken a     = TokenFactory.makeToken( "a" );
            EToken one   = TokenFactory.makeToken( "1" );
            EToken empty = TokenFactory.makeToken( "" );
            EToken invalid = TokenFactory.makeToken( "123_abc" );
            EToken dolla = TokenFactory.makeToken( "$valid" );
            EToken undersc = TokenFactory.makeToken( "_alsoValid" );
            EToken caps  = TokenFactory.makeToken( "AlsoValid" );
            EToken floatTok = TokenFactory.makeToken( "123.321" );
            
            System.out.println( a + " " + plus + " " + one + " "
                                   + times + " " + empty + " " + invalid 
                                   + " " + dolla + " " + undersc + " " + caps
                                   + " " + floatTok );
        }
        catch ( Exception e )
        {
            System.out.println( "Bad token: " + e );
        }
    }
}
