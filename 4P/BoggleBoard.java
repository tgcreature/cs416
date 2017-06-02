// imports ----------------------------------------------------------------
import java.io.*;
import java.util.*;

/**
 * BoggleBoard.java - A skeleton class for implementing the board for
 *                    the game of Boggle.
 *
 * @author Travis Calley
 */
public class BoggleBoard
{
    //--------------------- instance variables -------------------------
    private Tile[][]          _board;
    private int               _nCols;
    private int               _nRows;
    private ArrayList<String> _letters;
    private int               _wordCount;

    //------------------ constructor -----------------------------------
    /**
     * Default constructor.
     * 
     * @param lettersOnBoard ArrayList<String>
     * @param rows int
     * @param cols int
     */
    BoggleBoard( ArrayList<String> lettersOnBoard, int rows, int cols )
    {
        _letters = lettersOnBoard;
        _nCols = cols;
        _nRows = rows;
        _wordCount = 0;
        
        ////////////////////////////////////////////////////////////////
        // 1. Need to create the board and "populate" it with the letters
        //    passed in the ArrayList. Assign entries from letters by row!
        //    That is, do all of row 0, then row 1, etc.
        // 2. For each Tile in the board, need to create a list of its
        //    valid neighbors (in all 8 directions). Remember that tiles
        //    on the boundaries don't have 8 neighbors.
        /////////////////////////////////////////////////////////////////
        _board = new Tile[_nRows][_nCols];
        
        int count = 0;
        for( int r = 0; r < _nRows; r++ )
        {
            for( int c = 0; c < _nCols; c++ )
            {
                _board[r][c] = new Tile( r, c, _letters.get( count ) );
                count++;
            }
        }
        
        setValidNeighbors();
    }
    
    //--------------------- setValidNeighbors() ---------------------------
    /**
     * Loops through the board and sets all valid neighbors.
     */
    private void setValidNeighbors()
    {
        for( int r = 0; r < _nRows; r++ )
        {
            for( int c = 0; c < _nCols; c++ )
            {
                Vector<Tile> neighbors = _board[r][c].getNeighborVector();
                for( int dR = -1; dR <= +1; dR++ )
                {
                    for( int dC = -1; dC <= +1; dC++ )
                    {
                        if( !( dR == 0 && dC == 0 ) )
                        {
                            //It's not this cell, compute neighbor's (row col)
                            int neighRow = r + dR;
                            int neighCol = c + dC;
                            // Now check if neighbor exists
                            if( 0 <= neighRow && neighRow < _nRows &&
                                0 <= neighCol && neighCol < _nCols )
                            {
                                Tile temp = _board[neighRow][neighCol];
                                temp.setValidNeighbor( true );
                                neighbors.add( temp );
                            }
                        }
                    }
                }
            }
        }
    }
    
    //---------------------- getWordCount() -------------------------------
    /**
     * return the number of words found in the last solution.
     * if findWords has not yet been called, returns -1;
     * 
     * @return _wordCount
     */
    public int getWordCount()
    {
        /////////////////////////////////////////////////////////////////
        // return the number of words found in last call to findWords()
        //    or -1 if no call yet made
        /////////////////////////////////////////////////////////////////
        return _wordCount;
    }
    
    //---------------- formatTree( Iterator<String> ts ) ------------------
    /**
     * Returns a string of the formatted Tree with a line break every
     * 10 words and the last word has no comma.
     * 
     * @param ts TreeSet<String>
     * @return String
     */
    private String formatTree( Iterator<String> ts )
    {
        String result = "";
        while( ts.hasNext() )
        {
            _wordCount++;
            String temp = ts.next();
            
            if( _wordCount % 10 == 0 && _wordCount != 0 )
                result += temp + ", \n";
            else if( _wordCount % 10 != 0 && !ts.hasNext() )
                result += temp;
            else
                result += temp + ", ";
        }
        result += "\n";
        
        return result;
    }
    
    //------------------------- findWords() -----------------------------
    /**
     * Find all the valid words in this board (based on current parameter
     *    settings).
     * As words are found, add them to a Java TreeSet object (which sorts
     *    them alphabetically for you) -- see Java API documentation.
     * Return the words in a single String, separated by commas with
     *    10 words per line (except last line).
     * Most of the work is done by the private recursive method,
     *    findWords( String, Tile )
     * 
     * @return String
     */
    public String findWords()
    {
        //////////////////////////////////////////////////////////////////
        // For each tile in the board
        //    findWords( TreeSet, "", tile ) to find all words that start there
        // Convert the TreeSet into a single String with 10 words per line,
        //    separated by commas.
        // return this string.
        //////////////////////////////////////////////////////////////////
        
        TreeSet<String> validWords = new TreeSet<String>();
        
        for( int r = 0; r < _nRows; r++ )
            for( int c = 0; c < _nCols; c++ )
                findWords( validWords, "", _board[r][c] );
        
        Iterator<String> words = validWords.iterator();
        
        String wordList = formatTree( words );
        
        return wordList;
    }
    
    //---------------- findWords( TreeSet<String>, String, Tile ) -----------
    /**
     * Given a partial word ending at a neighbor of the tile passed in,
     *    add the tile's letter to the partial word, and check if it is a word
     *    and if it terminates the search along this path; recurse if not.
     * 
     * @param foundWords TreeSet<String>
     * @param word String
     * @param t Tile
     */
    private void findWords( TreeSet<String> foundWords, String word, Tile t )
    {
        ///////////////////////////////////////////////////////////////////
        //  if tile has not been visited (on this path)
        //     set tile's status as visited
        //     add tile's letter to word
        //     lookup word using search method of Boggle.dictionary
        //     if it is a word
        //        add it to the TreeSet
        //     if it is a word or it might begin another word
        //        get neighbor tiles of this tile
        //        for each neighbor of this tile
        //           invoke findWords(...) recursively
        //      reset the tiles visited flag to false
        ////////////////////////////////////////////////////////////////////
        int exists = 0;
        Vector<Tile> neighbors = t.getNeighborVector();
        
        if( !t.wasVisited() )
        {
            t.setVisited( true );
            word += t.toString();
            exists = Boggle.dictionary.search( word );
            
            if( exists == 1 ) // if it is a word add it to the foundWords
            {
                foundWords.add( word );
            }
            if( exists == 1 || exists == 0 ) // if it is a word or could be a
            {                                // word check its neighbors
                // get neighbor of cell
                for( Tile neighbor : neighbors )
                    findWords( foundWords, word, neighbor );
            }
            t.setVisited( false ); // resets the tiles visited flag to false
        }
    }
    //-------------------- toString() ---------------------------------------
    /**
     * convert the board to a String representation.
     * 
     * @return String
     */
    public String toString()
    {
        StringBuffer out = new StringBuffer();
        for ( int r = 0; r < _nRows; r++ )
        {
            for ( int c = 0; c < _nCols; c++ )
            {
                out.append( _board[ r ][ c ].getLetter() + "\t" );
                if ( _board[ r ][ c ] .getLetter().length() == 1 )
                    out.append( " " );  // add another blank for most letters
            }
            out.append( "\n" );
        }
        return out.toString();
    }
    //+++++++++++++++++++++++ main: invoke application ++++++++++++++++++++++
    /**
     * Main method.
     * 
     * @param args String[]
     */
    public static void main( String [] args )
    {
        Boggle.main( args );
    }
}
