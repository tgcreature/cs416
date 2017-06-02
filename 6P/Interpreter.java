// imports ----------------------------------------------------------------
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Interpreter.java - parses string input representing an infix arithmetic
 *                 expression into tokens, and builds an expression tree.
 *                 The expression can use the operators =, +, -, *, /, %.
 *                 and can contain arbitrarily nested parentheses.
 *                 The = operator is assignment and must be absolutely lowest
 *                 precedence.
 * 
 * @author Travis Calley
 */
public class Interpreter //extends JFrame
{
    //----------------------  class variables  ------------------------
    
    //---------------------- instance variables ----------------------
    private boolean      _printTree = true;  // if true print tree after each
    //    expression tree is built 
    private SymbolTable _symbolTable;
    //----------- constants
    
    //--------------------------- constructor -----------------------
    /**
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     * 
     * @param args String[]
     */
    public Interpreter( String[] args ) 
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
    
    /**
     * Turns a command line arguement of assigning a variable into an action.
     * Adds this new variable to the symbol table for later evaluation.
     * 
     * @param line String
     * @return ExpressionTree
     */
    private ExpressionTree assignVariable( String line )
    {
        Scanner s = new Scanner( line );
        String varName = "";
        String assignment = "";
        String possibleTree = "";
        
        try
        {
            varName = s.next();
            assignment = s.next();
            int count = 0;
            while( s.hasNext() )
            {
                String temp = s.next();
                if( s.hasNext() )
                    possibleTree += temp + " ";
                else
                    possibleTree += temp;
                count++;
            }
            if( count > 1 )
            {
                ExpressionTree tree = new ExpressionTree();
                tree.buildTree( tokenize( possibleTree ) );
                _symbolTable.setValue( varName, 
                                       TreeUtilities.processTree( tree ) );
                return tree;
            }
            else
                _symbolTable.setValue( varName, 
                                       Float.parseFloat( possibleTree ) );
        }
        catch( EmptyStackException e )
        {
            System.err.println( "No number to assign to the variable." );
        }
        catch( NoSuchElementException nse )
        {
            System.err.println( "No element exists for assignment." );
        }
        return null;
    }  
    
    /**
     * Gets the value of the string arguements from the SymbolTable.
     * 
     * @param line String
     * @return String
     */
    private String findSymbolTableValues( String line )
    {
        Scanner s = new Scanner( line );
        String command = s.next();
        String result = "{ \n";
        
        while( s.hasNext() )
        {
            String var = s.next();
            if( s.hasNext() )
                result += "    " + var + ": " + 
                          String.valueOf( _symbolTable.getValue( var ) ) 
                          + "\n";
            else
                result += "    " + var + ": " + 
                          String.valueOf( _symbolTable.getValue( var ) );
        }
        result += "\n}";
        return result;
    }
    
    /**
     * Contains invalid operand.
     * 
     * @param tokens ArrayList<EToken>
     * @return boolean
     */
    private boolean containsInvalidOperand( ArrayList<EToken> tokens )
    {
        for( EToken tok: tokens )
        {
            if( tok instanceof InvalidOperand )
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if any ETokens are invalid.
     * 
     * @param tokens ArrayList<EToken>
     * @param line String
     * @return String
     */
    private String checkValidTokens( ArrayList<EToken> tokens, String line )
    {
        for( EToken tok: tokens )
        {
            if( tok instanceof InvalidOperand )
            {
                System.out.println(  "Input: " + line );
                return "Invalid operand in input";
            }
        }
        return "input is valid";
    }
    
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line -- it shouldn't matter whether it comes from
     * the file or the popup dialog box. It might be convenient to return
     * return something to the caller in the form of a String that can
     * be printed or displayed.
     * 
     * @param line String
     * @return String
     */
    public String processLine( String line )
    {
        ExpressionTree tree = new ExpressionTree();
        ExpressionTree subTree = null;
        ArrayList<EToken> tokens = tokenize( line );
        ///////////////////////////////////////////////////////////////////
        // IF STATEMENT TO HANDKE THE GENERAL @LOOKUP COMMAND. IF @LOOKUP
        // IS IN THE BOUNDS OF LINE; THEN IF LINE IS @LOOKUP PRINT THE
        // ENTIRE SYMBOLTABLE, ELSE GET THE VALUES OF FOLLOWING ARGUEMENTS.
        ///////////////////////////////////////////////////////////////////
        if( line.contains( "@lookup" ) )
        {
            if( line.equals( "@lookup" ) )
                return _symbolTable.toString();
            else
            {
                return findSymbolTableValues( line );
            }
        }
        else if( line.startsWith( "#" ) )
        {
            return line;
        }
        ///////////////////////////////////////////////////////////////////
        // CHECKS FOR ANY INVALID OPERANDS IN THE TOKEN ARRAYLIST.
        ///////////////////////////////////////////////////////////////////
        else if( tokens != null && containsInvalidOperand( tokens ) )
        {
            return checkValidTokens( tokens, line );
        }
        ///////////////////////////////////////////////////////////////////
        // ASSIGNS THE VARIABLE LEFT OF ASSIGNMENT OPERATOR TO VALUE ON
        // THE RIGHT SIDE OF THE ASSIGNMENT OPERATOR.
        ///////////////////////////////////////////////////////////////////
        else if( line.contains( "=" ) )
        {
            subTree = assignVariable( line );
            tree.buildTree( tokens );
        }
        ///////////////////////////////////////////////////////////////////
        // EXECUTE THE CODE FOR THE @PRINT COMMAND FROM COMMAND LINE.
        // IF THE LINE IS JUST @PRINT, PRINT THE TREE. IF THE LINE IS
        // @PRINT OFF, TURN OFF AUTOMATIC PRINTING. IF @PRINT ON TURN ON
        // AUTOMATIC PRINTING.
        ///////////////////////////////////////////////////////////////////
        else if( line.contains( "@print" ) )
        {
            if( line.equals( "@print" ) )
            {
                
                return "";
            }
            else
            {
                Scanner print = new Scanner( line );
                String command = print.next();
                String toggle = print.next();
                
                if( toggle.equals( "on" ) )
                    _printTree = true;
                else
                    _printTree = false;
            }
        }
        
        ///////////////////////////////////////////////////////////////////
        System.out.println(  "Input: " + line );
        String trimmed = line.trim();
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
            return line;
        else
        {
            tree.buildTree( tokens );
            if( subTree != null )
                tree.root.right = subTree.root;
            if( _printTree )
            {
                System.out.println( "Tree:" );
                tree.printTree();
                System.out.println( "----------------------- " + line +
                                    " -----------------------" );
            }
            if( tree.root != null )
                return String.valueOf( TreeUtilities.processTree( tree ) + "\n" 
                                       + "================================" +
                                       "================================" );
            else
                return String.valueOf( _symbolTable.getValue( line.trim() ) + 
                                       "\n" + "================================"
                                       + "================================" );
        }
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
    
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get a file name interactively, then 
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
    
    //+++++++++++++++++++++++++ inner class +++++++++++++++++++++++++++++++
    //---------------------------- TextFilter -----------------------------
    /**
     * This class is used with FileChooser to limit the choice of files
     * to those that end in *.txt.
     * 
     */
    public class TextFilter extends javax.swing.filechooser.FileFilter
    {
        /**
         * Gets file.
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
         * Get description.
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
        Interpreter app = new Interpreter( args );
    }
}