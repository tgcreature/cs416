/**
 * Move.java - A class that records a cards move, so when the user presses
 * undo the card will restore to the previous location.
 * 
 * @author Travis Calley
 */
public class Move
{
    // instance variables -------------------------------------------------
    private Boolean _moveType;
    private Card    _card;
    private int     _index;
    private PyramidNode _node;
    
    /**
     * Constructor.
     * 
     * @param moveType Boolean
     * @param card Card
     * @param index int
     * @param node PyramidNode
     */
    public Move( Boolean moveType, Card card, int index, PyramidNode node )
    {
        _moveType = moveType; // if false, pyramid-to-discard
                              // else, drawPile-to-discard
        _card = card;
        _index = index; // if -1, not a pyramid card
        _node = node;
    }
    //---------------------getCard()--------------------------------------
    /**
     * Gets the card.
     * 
     * @return _card
     */
    public Card getCard()
    {
        return _card;
    }
    //-----------------------getMoveType()--------------------------------
    /**
     * Returns the type of move it was.
     * 
     * @return _moveType
     */
    public Boolean getMoveType()
    {
        return _moveType;
    }
    //--------------------------getIndex()-------------------------------
    /**
     * Returns index of the card in _pyramidCards ArrayList.
     * 
     * @return _index
     */
    public int getIndex()
    {
        return _index;
    }
    //------------------------------getNode()----------------------------
    /**
     * Gets the node associated with the move.
     * 
     * @return _node
     */
    public PyramidNode getNode()
    {
        return _node;
    }
}