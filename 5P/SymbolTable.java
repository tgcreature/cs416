// imports ---------------------------------------------------------------
import java.util.*;

/**
 * SymbolTable - this class  maintains a symbol table for variables.
 * 
 * This functionality lends itself to a class that uses the Singleton pattern.
 * That is, it enforces a restriction that only 1 instance can ever be created.
 * 
 * @author Travis Calley
 */
public class SymbolTable
{
    //--------------------- class variables -------------------------------
    private static SymbolTable theTable = null;
    
    //--------------------- instance variables ----------------------------
    // Use a Hashtable or a HashMap to store information (value) using the id 
    // as the key
    ///////////////////////////////////////////////////////////
    
    private HashMap<String, Float> _hashMap;    
    
    //------------- constructor --------------------------------------------
    /**
     * Note the constructor is private!
     */
    private SymbolTable()
    {
        //////// Create the hashtable or hashmap ///////////////////
        
        _hashMap = new HashMap<String, Float>();
    }
    //------------- instance -----------------------------------------------
    /**
     * user code must call a static method to get the reference to the 
     * only allowed instance -- first call creates the instance.
     * 
     * @return theTable SymbolTable
     */
    public static SymbolTable instance()
    {
        if ( theTable == null )
            theTable = new SymbolTable();
        return theTable;
    }
    
    //------------------------ setValue( String, float  ) ---------------------
    /**
     * Set an identifier's value to the specified value.
     * 
     * @param var String
     * @param num float
     */
    public void setValue( String var, float num )
    {
        /////////////////////////////////////////////////////// 
        //    Need to save information into the hash table
        //
        // You are allowed to change the signatures; for example, you
        //    can use Float or Number as the parameter type, both
        //    here and in getValue
        //////////////////////////////////////////////////////////
        
        _hashMap.put( var, num );
        
    }
    //------------------------ getValue( String ) ---------------------------
    /**
     * Look up an identifier in the hash table and return its value.
     * If the identifier is not in the table, add it with a value of 0
     * and return 0.
     * 
     * @param var String
     * @return result float
     */
    public float getValue( String var )
    {
        /////////////////////////////////////////////////////////////
        //  Use var as hash table key get value; return it as a float
        //    or a Number (Float)
        ////////////////////////////////////////////////////////////
        
        try
        {
            return _hashMap.get( var );
        }
        catch( NullPointerException e )
        {
            setValue( var, 0.0f );
            return getValue( var );
        }
    }
    //------------------------- toString() -------------------------------
    /**
     * Turns the HashMap into a printable string.
     * 
     * @return results String
     */
    public String toString()
    {
        String results = "{ ";
        
        for( String elem: _hashMap.keySet() )
        {
            results += "\n    " + elem + ": " + getValue( elem );
        }
        
        if( results.equals( "{ " ) )
            results += "}";
        else
            results += "\n}";
        
        return results;
    }
    //--------------------------- main -----------------------------------
    /**
     * Simple unit testing for SymbolTable.
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        SymbolTable st = SymbolTable.instance();
        st.setValue( "a", 4.0f );
        float val = st.getValue( "a" );
        System.out.println( "Test: should print 4: " + val );
        
        val = st.getValue( "b" );
        System.out.println( "Test: should print 0: " + val );
        
        st.setValue( "a", 6.0f );
        val = st.getValue( "a" );
        System.out.println( "Test: should print 6: " + val );
        
        
        ///////////////////////////////////////////////////////////////////
        // TESTING THE TOSTRING METHOD BY ADDING MORE VALUES TO THE
        // HASHMAP.
        ///////////////////////////////////////////////////////////////////
        //st.setValue( "b" , 13.5f );
        st.setValue( "c", -30.54f );
        st.setValue( "d", 5.5f );
        st.setValue( "a", 69.0f );
        System.out.println( st.toString() );
    }
}
