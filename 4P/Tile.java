// imports ----------------------------------------------------------------
import java.util.*;

/**
 * Tile for BoggleBoard -- skeleton
 *
 * Knows its letter and its location on the board, but you
 *    may want to revise the the "letter" handling to
 *    account for the fact that "q" is really "qu".
 *
 * Needs to know about its visited status
 * and who its neighboring tiles are.
 *
 * @author Travis Calley
 */
public class Tile
{
    //---------------------- instance variables ---------------------------
    private String          _letter;
    private int             _row, _col;
    private Boolean         _visited, _valid;
    private Vector<Tile>    _neighbors;
    
    //------------------ constructor --------------------------------------
    /**
     * Default constructor.
     * 
     * @param r int rows
     * @param c int cols
     * @param let String letter
     */
    public Tile( int r, int c, String let )
    {
        _row = r;
        _col = c;
        _visited = false;
        _valid = false;
        _neighbors = new Vector<Tile>();
        
        if( let.equals( "q" ) )
            _letter = "qu";
        else
            _letter = let;
    }
    //----------------------- getNeighborVector() -------------------------
    /**
     * Returns the vector of neighbors.
     * 
     * @return _neighbors
     */
    public Vector<Tile> getNeighborVector()
    {
        return _neighbors;
    }
    //------------------ getCol() -----------------------------------------
    /**
     * returns the col location.
     * 
     * @return _col
     */
    public int getCol()
    {
        return _col;
    }
    //------------------ getRow() -----------------------------------------
    /**
     * returns the row location.
     *
     * @return _row
     */
    public int getRow()
    {
        return _row;
    }
    //------------------ getLetter( ) -------------------------------------
    /**
     * Returns the letter this tile contains.
     * 
     * @return _letter
     */
    public String getLetter()
    {
        return _letter;
    }
    //--------------------------- toString() ------------------------------
    /**
     * Returns the letter this tile contains as a string.
     * 
     * @return _letter
     */
    public String toString()
    {
        return _letter;
    }
    //------------------------- wasVisited() ------------------------------
    /**
     * Returns whether or not the tile has been visited.
     * 
     * @return _visited
     */
    public Boolean wasVisited()
    {
        return _visited;
    }
    //------------------------ setVisited() -------------------------------
    /**
     * Sets if the tile has been visited.
     * 
     * @param yesNo Boolean
     */
    public void setVisited( Boolean yesNo )
    {
        _visited = yesNo;
    }
    //---------------------- isValidNeighbor() ----------------------------
    /**
     * Gets whether or not this tile is a valid neighbor.
     * 
     * @return _valid
     */
    public Boolean isValidNeighbor()
    {
        return _valid;
    }
    //------------- setValidNeighbor( Boolean yesNo ) ---------------------
    /**
     * Sets whether or not this tile is a valid neighbor.
     * 
     * @param yesNo Boolean
     */
    public void setValidNeighbor( Boolean yesNo )
    {
        _valid = yesNo;
    }
}