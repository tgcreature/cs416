// imports ---------------------------------------------------------------
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Lexer.java - parses string input representing an infix arithmetic
 *              expression into tokens.
 *              The expression can use the operators =, +, -, *, /, %.
 *              and can contain arbitrarily nested parentheses.
 *              The = operator is assignment and must be absolutely lowest
 *              precedence.
 * 
 * @author Travis Calley
 */
public class Lexer //extends JFrame
{
    //----------------------  class variables  ------------------------
    
    //---------------------- instance variables ----------------------
    
    private SymbolTable  _symbolTable;
    
    //----------- constants
    private String   dashes = " ---------------------------- ";
    private String   endOutputBlock =
        "====================================================================";
    
    //--------------------------- constructor -----------------------
    /**
     * Create the Lexer.
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     * 
     * @param args String[]
     */
    public Lexer( String[] args )
    {
        _symbolTable = SymbolTable.instance();
        
        if ( args.length > 0 )
            processFile( args[ 0 ] );
        else
            interactive();
    }
    //--------------------- processFile -----------------------------
    /**
     * Given a String containing a file name, open the file and read it.
     * Each line should represent an expression to be parsed.
     * For each line, process it, and print the result to System.out.
     * 
     * @param fileName String
     */
    public void processFile( String fileName )
    {
        try
        {
            File file = new File( fileName );
            Scanner fileScan = new Scanner( file );
            while( fileScan.hasNextLine() )
            {
                String line = fileScan.nextLine();
                String result = processLine( line );
                System.out.println( result );
            }
        }
        catch( FileNotFoundException e )
        {
            // file not found do nothing
        }
        
    }
    
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line and do the right thing; return the
     *    "results" which can be null.
     * 
     * @param line String
     * @return String
     */
    public String processLine( String line )
    {
        ///////////////////////////////////////////////////////////////////
        // IF STATEMENT TO HANDKE THE GENERAL @LOOKUP COMMAND. IF @LOOKUP
        // IS IN THE BOUNDS OF LINE; THEN IF LINE IS @LOOKUP PRINT THE
        // ENTIRE SYMBOLTABLE, ELSE GET THE VALUES OF FOLLOWING ARGUEMENTS.
        ///////////////////////////////////////////////////////////////////
        if( line.indexOf( "@lookup" ) != -1 )
        {
            if( line.equals( "@lookup" ) )
                return _symbolTable.toString();
            else
            {
                Scanner s = new Scanner( line );
                String command = s.next();
                
                while( s.hasNext() )
                {
                    String var = s.next();
                    return String.valueOf( _symbolTable.getValue( var ) );
                }
            }
        }
        
        System.out.println(  "Input: " + line );
        String trimmed = line.trim();
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
            return line;
        else
            return processExpr( trimmed );
    }
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get an file name interactively, then
     * go into a loop prompting for expressions to be entered one
     * at a time.
     */
    public void interactive()
    {
        JFileChooser fChooser = new JFileChooser( "." );
        fChooser.setFileFilter( new TextFilter() );
        int choice = fChooser.showDialog( null, "Pick a file of expressions" );
        if ( choice == JFileChooser.APPROVE_OPTION )
        {
            File file = fChooser.getSelectedFile();
            if ( file != null )
                processFile( file.getName() );
        }
        
        String prompt = "Enter an arithmetic expression: ";
        String expr = JOptionPane.showInputDialog( null, prompt );
        while ( expr != null && expr.length() != 0 )
        {
            String result = processLine( expr );
            JOptionPane.showMessageDialog( null, expr + "\n" + result );
            expr = JOptionPane.showInputDialog( null, prompt );
        }
    }
    //------------------ processExpr( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them, and
     * return a String representation of all of them.
     * 
     * @param line String
     * @return String
     */
    public String processExpr( String line )
    {
        ArrayList<EToken> tokens = tokenize( line );
        
        //
        // Visit all the tokens, and join them into one String
        //
        if ( tokens.size() == 0 )
        {
            return "EMPTY EXPRESSION";
        }
        
        String result = "";
        for ( EToken token : tokens )
        {
            result += token + " ";
        }
        
        return result;
    }
    
    //------------------ tokenize( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them.
     * 
     * @param line String
     * @return tokens ArrayList<EToken>
     */
    protected ArrayList<EToken> tokenize( String line )
    {
        ArrayList<EToken> tokens = new ArrayList<EToken>();
        Scanner lineScan = new Scanner( line );
        
        while( lineScan.hasNext() )
        {
            String cur = lineScan.next();
            EToken temp = TokenFactory.makeToken( cur );
            tokens.add( temp );
        }
        
        return tokens;
    }
    
    //---------------------------- TextFilter -----------------------------
    /**
     * This class is used with FileChooser to limit the choice of files
     * to those that end in *.txt.
     * 
     */
    public class TextFilter extends javax.swing.filechooser.FileFilter
    {
        /**
         * Checks if file is valid.
         * 
         * @param f File
         * @return boolean
         */
        public boolean accept( File f )
        {
            if ( f.isDirectory() || f.getName().matches( ".*txt" ) )
                return true;
            return false;
        }
        
        /**
         * Gets file information.
         * 
         * @return String
         */
        public String getDescription()
        {
            return "*.txt files";
        }
    }
    //--------------------- main -----------------------------------------
    /**
     * Main method.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        Lexer app = new Lexer( args );
    }
}
