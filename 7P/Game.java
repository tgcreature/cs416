//+++++++++++++++++++++++++ Game.java +++++++++++++++++++++++++++++++++++++
// imports ----------------------------------------------------------------
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

/**
 * Game.java - implementation of a solitaire card game.
 * 
 * @author Travis Calley
 */
public class Game extends JPanel
{
    //----------------------- class variables -----------------------------
    private static int   seed = 0;
    static         Game  theGame;
    static         int   numRows = 4;
    
    //----------------------- instance variables --------------------------
    private CardStack             _drawPile = null; 
    private CardStack             _discards = null;
    private ArrayList<Card>       _baseDeck = null;
    private int                   _parentWidth;
    private Pyramid<Card>         _pyramid;
    private Random                _rng;
    private ArrayList<Card>       _pyramidCards;
    private Stack<Move>           _undo;
    private Boolean               _over = false;
    
    //----- positioning variables
    private   int discardX  = 60,  discardY  = 140;
    private   int drawPileX = 60,  drawPileY = 40;
    private   int pyramidX  = 400,   pyramidY  = 40;
    
    //---------------------- constructor ----------------------------------
    /**
     * Game is where most of the game-based code is found.
     * 
     * @param pWidth int     width of the panel (apprx)
     */
    public Game( int pWidth )
    {
        this.setLayout( null );
        theGame = this;
        
        _parentWidth = pWidth;
        _baseDeck = new ArrayList<Card>();
        _pyramidCards = new ArrayList<Card>();
        _undo = new Stack<Move>();
        
        createDeck();
        _discards = new CardStack( this, discardX, discardY );
        _discards.setYOffset( 2 );
        
        _drawPile = new CardStack( this, drawPileX, drawPileY );
        _drawPile.setXOffset( 0 );
        _rng = new Random( seed );
        makeNewDeck();
    }
    //------------------------- drawCard() --------------------------------
    /**
     * Draw a card from the display pile.
     * 
     * @return String
     */
    public String drawCard()
    {
        String msg = null;
        ////////////////////////////////////////////////////////////////
        //
        // To draw a card, you need to pop it from the _drawPile 
        //   and push it onto the _discards pile.
        // Of course, check if it is empty.
        // If the draw pile stack is empty, this method should return
        //   a non-null message -- indicating the game is over.
        ///////////////////////////////////////////////////////////////
        if ( _drawPile.size() != 0 )
        {
            Card next = _drawPile.pop();
            _undo.push( new Move( true, next, -1, next.getNode() ) );
            _discards.push( next );
        }
        else
            msg = "No more cards to draw." ;
        
        update();
        return msg;
    }
    //------------------------ makeNewDeck() ------------------------------
    /**
     * Create new deck.
     */
    public void makeNewDeck()
    {
        Collections.shuffle( _baseDeck, _rng );
        replay();
    }
    
    //---------------------- replay( ) ------------------------------------
    /**
     * Replay the game.
     */
    public void replay()
    {
        _discards.clear();
        _over = false;
        _pyramidCards.clear(); 
     
        deckToDrawPile( _baseDeck );          
        dealCards( _drawPile );
        
        update();
    }
    //---------------------- draw( ) --------------------------------------
    /**
     * Draw from draw pile. Simulate click on draw pile.
     */
    public void draw()
    {
        play( _drawPile.top() );
    }
    //---------------------- undoPlay( ) ----------------------------------
    /**
     * Undo the previous play.
     */
    public void undoPlay()
    {
        /////////////////////////////////////////////////////////////
        // 7I: Do the "inverse" of the last play
        /////////////////////////////////////////////////////////////
        
        try
        {
            Move temp = _undo.pop();
            Card cur = temp.getCard();
            int i = temp.getIndex();
            
            if( temp.getMoveType() )
            {
                _discards.pop();
                _drawPile.push( cur );
            }
            else
            {
                _discards.pop();
                _pyramidCards.set( i, cur );
                Card pCard = _pyramidCards.get( i );
                pCard.setNode( temp.getNode() );
                pCard.setLocation( cur.getNode().getPrevPoint() );
            }
        }
        catch( EmptyStackException e )
        {
            // no moves to undo
        }
        _over = false;
        makeBinaryTree();
        reDrawPyramid();
        update();
    }
    //---------------------- autoPlay( ) ----------------------------------
    /**
     * Go into auto play mode.
     */
    public void autoPlay()
    {
        Boolean play = false;
        
        play = autoMove();
        
        if( !play )
            draw();
        
        update();
    }
    //------------------- autoMove( Boolean ) -----------------------------
    /**
     * Tries to play any of the PyramidCards. This method is called
     * by both the autoPlay() method and autoPlayAll() method.
     * 
     * @return Boolean
     */
    private Boolean autoMove(  )
    {
        Boolean play = false;
        Boolean sameLevel = false;
        
        for( int j = numRows - 1; j >= 0; j-- )
        {
            for( int i = 0; i < _pyramidCards.size(); i++ )
            {           
                Card temp = _pyramidCards.get( i );
                
                try
                {
                    sameLevel = temp.getNode().getLevel() == j;
                }
                catch( NullPointerException e )
                {
                    sameLevel = false;
                }
                
                if( temp != null && sameLevel )
                    play = play( temp );
                
                if( play )
                    return play;
            }
        }
        return play;
    }
    //---------------------- autoPlayAll( ) -------------------------------
    /**
     * Make autoPlays until end of game.
     */
    public void autoPlayAll()
    {
        //////////////////////////////////////////////////////////////
        // Part of 7P
        //////////////////////////////////////////////////////////////
        
        while( !_drawPile.isEmpty() && 
               _pyramidCards.get( 0 ).getNode() != null )
        {
            autoPlay();
        }
        update();        
    }
    //------------------------ play( Card ) -------------------------------
    /**
     * Play a card.
     * 
     * @param picked Card     a card that is to be played.
     * @return boolean        true means a valid play occurred.
     */
    public boolean play( Card picked )
    {
        boolean playedFromPyramid = false;
        
        if ( picked == _drawPile.top() )
            drawCard();
        
        else if ( playPyramidCard( picked ) )
        {
            playedFromPyramid = true;
        }
        
        ///////////////////////////////////////////////////////////
        // 7I: Need to check here to see if game has ended
        ///////////////////////////////////////////////////////////   
        
        if( _pyramidCards.get( 0 ).getNode() == null && !_over )
        {
            _over = true;
            PyramidSolitaire.log( "Draw pile", _drawPile );
            PyramidSolitaire.log( "Play pile", _discards );
            System.out.println( "You Won! Score is: " 
                                   + _drawPile.size() );
        }
        else if( _drawPile.size() == 0 && !_over )
        {
            _over = true;
            PyramidSolitaire.log( "Draw pile", _drawPile );
            PyramidSolitaire.log( "Play pile", _discards );
            System.out.println( "You Lost! Score is: " 
                                   + getPyramidScore() );
        }
        
        return playedFromPyramid;       
    }
    //------------------------getPyramidScore()----------------------------
    /**
     * Returns the score of the pyramid.
     * 
     * @return int
     */
    private int getPyramidScore()
    {
        int count = 0;
        
        for( int i = 0; i < _pyramidCards.size(); i++ )
        {
            if( _pyramidCards.get( i ).getNode() != null )
            {
                count--;
            }
        }
        
        return count;
    }
    //------------------------ playPyramidCard ----------------------------
    /** 
     * Play a card from the pyramid.
     *
     * @param picked Card
     * @return boolean 
     */
    private boolean playPyramidCard( Card picked )
    {
        boolean success = false;
        
        PyramidNode cardNode = picked.getNode();
        {
            try
            {
                Point last = new Point( picked.getX(), picked.getY() );
                picked.getNode().setPrevPoint( last );
                Card.Rank pickedRank = picked.getRank();
                Card top = _discards.top();
                int diff = Math.abs( pickedRank.ordinal() 
                                        - top.getRank().ordinal() );
                
                if ( diff == 1 && cardNode.getLeft() == null && 
                     cardNode.getRight() == null || diff == 12 && 
                     cardNode.getLeft() == null && cardNode.getRight() == null )
                {
                    _discards.push( picked );
                    int i = _pyramidCards.indexOf( picked );
                    Card temp = _pyramidCards.get( i );
                    _undo.push( new Move( false, temp, i, cardNode  ) );
                    temp.setNode( null );
                    makeBinaryTree();
                    update();
                    success = true;
                }
            }
            catch( NullPointerException e )
            {
                // node is null
            }
        }
        return success;
    }
    //----------------------------- update() ------------------------------
    /**
     * Update the display components as needed.
     */
    public void update()
    {
        /////////////////////////////////////////////////////////////
        // You might need to do something here. I didn't, but there
        //    or valid implementations that might require it.
        /////////////////////////////////////////////////////////////
        
        // show all cards on the _discards stack 
        _discards.showCards( -1 );
        
        // show no cards on the draw pile
        _drawPile.showCards( 0 );
        this.repaint();    
    }
    
    //------------------- makeBinaryTree() --------------------------------
    /**
     * Makes a binary tree with a Pyramid main object and children
     * PyramidNode objects.
     */
    private void makeBinaryTree()
    {
        _pyramid = new Pyramid<Card>( numRows, pyramidX, pyramidY, 
                                     Card.width, Card.height );
        PyramidNode<Card> tempL;
        PyramidNode<Card> tempR;
        PyramidNode<Card> cur = null;
        int curI = 0;
        for( int level = 0; level < numRows; level++ )
        {
            curI += level;
            for( int i = curI; i <= curI + level; i++ )
            {
                try
                {
                    cur = _pyramidCards.get( i ).getNode();
                    cur.setLevel( level );
                    tempL = _pyramidCards.get( i + level + 1 ).getNode();
                    tempR = _pyramidCards.get( i + level + 2 ).getNode();
                }
                catch( Exception e )
                {
                    tempL = null;
                    tempR = null;
                }
                
                // sets root PyramidNode
                if( i == 0 )
                {
                    if( cur != null )
                    {
                        _pyramid.setRoot( cur );
                        _pyramid.getRoot().setData( _pyramidCards.get( i ) );
                        _pyramid.getRoot().setLeft( tempL );
                        _pyramid.getRoot().setRight( tempR );
                    }
                }
                // sets all other PyramidNodes that aren't the root
                else
                {
                    if( cur != null )
                    {
                        cur.setData( _pyramidCards.get( i ) );
                        cur.setLeft( tempL );
                        cur.setRight( tempR );
                    }
                }
            }
        }
    }
    
    //-------------------- dealCards() ------------------------------------
    /**
     * Deal the cards from the drawPile to the pyramid. 
     *    This version just stores all the dealt cards in an ArrayList.
     * @param deck CardStack    deck to deal from
     */
    public void dealCards( CardStack deck )
    {
        /////////////////////////////////////////////////////////////
        // This approach creates PyramidNodes for each Card in the 
        //    pyramid, but does not put the nodes into a tree.
        /////////////////////////////////////////////////////////////
        int xGap = 2;
        int yDelta = 30;
        for ( int level = 0; level < numRows; level++ )
        {
            int span = Card.width * ( level + 1 ) + xGap * level;
            
            int xPos = pyramidX - span / 2;
            int yPos = pyramidY + level * yDelta;
            for ( int n = 0; n < level + 1; n++ )
            {
                PyramidNode<Card> node = 
                    new PyramidNode<Card>( xPos, yPos, 
                                           Card.width, Card.height );
                Card card = deck.pop();
                _pyramidCards.add( card );
                card.setNode( node );
                node.setData( card );
                card.setFaceUp( true );
                card.setLocation( xPos, yPos );
                this.setComponentZOrder( card, 0 );
                xPos += Card.width + xGap;
            }
        }
        makeBinaryTree();
        reDrawPyramid();
    }
    //-------------------- reDrawPyramid ----------------------------
    /**
     * Draw the pyramid tree cards.
     */
    public void reDrawPyramid()
    {
        //////////////////////////////////////////////////////////////
        //  In its present form it just redisplays every card that was
        //     once in the pyramid, whereever they happen to be.
        //  It may or may not be good enough for your implementation.
        //////////////////////////////////////////////////////////////
        for ( Card card: _pyramidCards )
        {
            card.setFaceUp( true );
            this.setComponentZOrder( card, 0 );
        }
        
    }
    
    //////////////////////////////////////////////////////////////////
    // You probably don't need to edit anything below here,
    //    but you may if you wish.
    //////////////////////////////////////////////////////////////////
    //------------------------ newSeed( ) -----------------------------
    /**
     * Set new random number generator seed.
     * 
     * @param newSeed int
     */
    public void newSeed( int newSeed )
    {
        _rng = new Random( newSeed );
        seed = newSeed;
    }
    //------------------------ setRows( ) -----------------------------
    /**
     * Change the number of rows in the game.
     *     Won't have effect until next game is played.
     * @param rows int
     */
    public void setRows( int rows )
    {
        numRows = rows;
    }
    //------------------- theGame() --------------------------------
    /**
     * Singleton pattern requires this method to return a reference
     *    to only allowed instance.
     * @return Game
     */
    public static Game theGame()
    {
        return theGame;
    }
    //------------------ showDeck() ----------------------------------
    /**
     * Turn all cards in deck face up and spread them out.
     */
    public void showDeck()
    {
        _drawPile.setXOffset( 11 );
        _drawPile.showCards( -1 );
        this.repaint();
    }
    //------------------ hideDeck() ----------------------------------
    /**
     * Turn all cards in deck face down and stack them up again.
     */
    public void hideDeck()
    {
        _drawPile.setXOffset( 0 );
        _drawPile.showCards( 0 );
        this.repaint();
    }
    
    //------------------------ createDeck() ---------------------------
    /**
     * Create a deck of cards in the _base variable.
     */ 
    private void createDeck()
    {
        int  cardIndex = 0;
        
        for ( Card.Suit suit: Card.Suit.values() )
        {
            for ( Card.Rank rank: Card.Rank.values() )
            {
                Card card = new Card( rank, suit );
                _baseDeck.add( 0, card );
                this.add( card );
            }
        }
    }
    
    //------------------------ deckToDrawPile( Card[] ) ---------------
    /**
     * Copy an array of cards into CardStack representing draw pile.
     * 
     * @param deck ArrayList<Card>
     */
    private void deckToDrawPile( ArrayList<Card> deck )
    {
        _drawPile.clear();
        for ( int c = 0; c < 52; c++ )
            _drawPile.push( deck.get( c ) );
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++ new code ++++++++++++++++++++++++++++++++++
    //------------------------ setBaseDeck() --------------------------
    /**
     * Create new base deck.
     * @param newDeck ArrayList<Card>    the new deck.
     */
    public void setBaseDeck( ArrayList<Card> newDeck )
    {    
        if ( _baseDeck != null )
        {
            for ( Card card: _baseDeck )
                this.remove( card );   // remove from JPanel
        }
        _baseDeck.clear();
        
        for ( Card card: newDeck )
        {
            _baseDeck.add( card );
            this.add( card );
        }
        
        PyramidSolitaire.log( "Starting deck", _baseDeck );
        replay();
    }
    
    //---------------------- writeDeck( ) -----------------------------
    /**
     * Write the current _baseDeck to a file.
     */
    public void writeDeck()
    {
        String msg = "Enter file name of desired deck file";
        String outName = JOptionPane.showInputDialog( null, msg );
        //String outName = Utilities.getFileName();
        if ( outName != null && outName.length() > 0 )
            writeDeck( outName );
    }
    //---------------------- writeDeck( String ) ----------------------
    /**
     * Write the current _baseDeck to the named file.
     * @param fileName String     name of output file
     */
    public void writeDeck( String fileName )
    {
        PrintStream out;
        try
        {
            out = new PrintStream( new File( fileName ) );
        }
        catch ( FileNotFoundException fnf )
        {
            System.err.println( "*** Error: unable to open " + fileName );
            return;
        }
        out.println( cardsToString( _baseDeck ) );
        out.close();
    }
    //---------------------- readDeck( ) -----------------------------
    /**
     * Read a file to replace _baseDeck.
     */
    public void readDeck()
    {
        String inName = Utilities.getFileName();
        if ( inName != null && inName.length() > 0 )
        {
            ArrayList<Card> newDeck = readDeck( inName );
            if ( newDeck.size() != 52 )
                System.err.println( "*** ERROR. Game.readDeck: file has "
                                       + newDeck.size() + " cards!" );
            else                          
                setBaseDeck( newDeck );
        }
    }
    //------------------------ readCardFile -------------------------
    /**
     * Read card representations from a file. Input file should consist of
     *    card representations of the form rs separated by spaces, where
     *        r is one of  23456789XJQKA
     *        s is one of  CDHS
     *    Case is not relevant. Lines are not relevant.
     * @param filename String  
     * @return ArrayList<Card>
     */
    public ArrayList<Card> readDeck( String filename )
    { 
        Card.Rank[] rankValues = Card.Rank.values();
        Card.Suit[] suitValues = Card.Suit.values();
        //-------- This assumes Ace low! ----------------------
        String rankCodes = "A23456789XJQK";
        String suitCodes = "CDHS";
        Scanner scan = null;
        
        ArrayList<Card> cards = new ArrayList<Card>();
        try
        {
            scan = new Scanner( new File( filename ) );
        } 
        catch ( FileNotFoundException fnf )
        {
            System.out.println( "Can't find: " + filename );
            return null;
        }
        int count = 0;
        while ( scan.hasNextLine() )
        {
            String line = scan.nextLine();
            line = line.trim();
            if ( line.length() > 0 && line.charAt( 0 ) != '#' ) 
            {
                String[] words = line.split( " " );
                for ( int w = 0; w < words.length; w++ )
                {
                    String word = words[ w ];
                    char rankch = words[ w ].charAt( 0 );
                    char suitch = words[ w ].charAt( 1 );
                    int rankIndex = rankCodes.indexOf( rankch );
                    int suitIndex = suitCodes.indexOf( suitch );
                    if ( rankIndex < 0 || suitIndex < 0 )
                    {
                        System.err.println( "Scan error?: " + rankIndex +
                                            " " +   suitIndex + " " + 
                                            words[ w ] );
                    }
                    else
                    {
                        Card.Rank rank = rankValues[ rankIndex ];
                        Card.Suit suit = suitValues[ suitIndex ];
                        cards.add( new Card( rank, suit ) );
                        count++;
                    }
                }
            }
        }
        return cards;
    }
    //------------------- cardsToString( Iterable<Card> ) -------------
    /**
     * Create a compact string representation of a collection of Cards.
     * 
     * @param cardSet Iterable<Card>   the card collection to process
     * @return String
     */
    public static String cardsToString( Iterable<Card> cardSet )
    {
        //-------- This assumes Ace low! ----------------------
        String rankChar = "A23456789XJQK";
        String suitChar = "CDHS";
        StringBuffer out = new StringBuffer();
        int lineLen = 0;
        int maxLine = 38;
        for ( Card card: cardSet )
        {
            char r = rankChar.charAt( card.getRank().ordinal() );
            char s = suitChar.charAt( card.getSuit().ordinal() );
            out.append( "" + r + s + " " );
            lineLen += 3;
            if ( lineLen > maxLine )
            {
                out.append( "\n" );
                lineLen = 0;
            }
        }
        if ( lineLen > 0 )
            out.append( "\n" );
        return out.toString();
    }
    //--------------------------- main ---------------------------------
    /**
     * A convenient tool for invoking main class.
     * @param args String[]    Command line arguments
     */
    public static void main( String[] args )
    {
        // Invoke main class's main
        PyramidSolitaire.main( args );
    }
}
