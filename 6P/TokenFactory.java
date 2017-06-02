// imports ---------------------------------------------------------------
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * TokenFactory: a factory class, which makes tokens from String fields.
 * This is an example of a Factory pattern.
 *
 * @author Travis Calley
 */
public class TokenFactory
{

    /**
     * Takes a string and makes a scanner from the string. Then
     * puts that token through a series of if-statements to
     * see if it matches a given pattern to make an EToken from it.
     * 
     * @param s String
     * @return result EToken
     */
    public static EToken makeToken( String s )
    {
        Scanner lineScan = new Scanner( s );
        
        while( lineScan.hasNext() )
        {
            String token = lineScan.next();
            
            ///////////////////////////////////////////////////////////////
            // CHECKS IF THE CURRENT STRING IS AN OPERATOR.
            ///////////////////////////////////////////////////////////////
            if( ( "+-/*%()=" ).indexOf( token ) != -1 )
            {
                return new Operator( token );
            }
            
            ///////////////////////////////////////////////////////////////
            // CHECKS IF THE CURRENT STRING IS PARSABLE TO A FLOAT.
            // IF IT IS PARSABLE MAKE A NEW NUMBER ETOKEN.
            ///////////////////////////////////////////////////////////////
            try
            {
                Float.parseFloat( token );
                return new Number( token );
            }
            catch( NumberFormatException e )
            {
                // Not a valid Number
            }
            
            ///////////////////////////////////////////////////////////////
            // CHECKS IF THE CURRENT STRING MATCHES THE PATTERN OF A
            // VALID VARIABLE NAME AND CHECKS IF THE STARTING CHARACTER
            // IS NOT EQUALS TO A NUMBER 0-9. THE '^' MEANS START
            // OF A LINE; '?' MEANS OR; '\w' MEANS a-z,A-Z,0-9.
            ///////////////////////////////////////////////////////////////
            Pattern pattern = Pattern.compile( "^_?\\$?[a-zA-Z]\\w*$" );
            Matcher matcher = pattern.matcher( token );
           
            if( matcher.find() )
                return new Variable( token );
            else
                return new InvalidOperand( token );
        }
        
        return null;
    }

    /**
     * Calls EToken.main to make my life easier testing the cases.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        EToken.main( args );
    }
}
