/** 
 * DisplayPanel.java: A panel for displaying the application graphics.
 *
 * Matthew Plumlee
 * modified by rdb
 * CS416 Spring 2008
 */

import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DisplayPanel extends JPanel 
{
    //------------------- instance variables ------------------------
    private JEditorPane _input;
    private JEditorPane _match;
    private JLabel      _matchLabel;
    
    //------------- magic constants
    private int       _defaultW   = 1000;
    private int       _defaultH   = 800;
    
    private int       _colWidth   = 500;     
    
    //--------------------- constructor ----------------------------
    public DisplayPanel () 
    {
        super();
        // default to FlowLayout
        //setLayout( new GridLayout( 0, 2 ) ); 
        GridBagLayout gridbag = new GridBagLayout();
        setLayout( gridbag );
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        makeTitle( "Input data", _colWidth, gridbag, gbc, 0, 0 );
        _matchLabel = makeTitle( "Matches", _colWidth, gridbag, gbc, 1, 0 );
        
        _input = makeEditorPane( _colWidth, gridbag, gbc, 0, 1 );
        _match = makeEditorPane( _colWidth, gridbag, gbc, 1, 1 );
    }
    //-------------------- makeTitle( String, int, int ) -----------
    /**
     * make a JLabel to serve as EditorPane title
     */
    private JLabel makeTitle( String text, int width, GridBagLayout gbag,
                             GridBagConstraints gbc, int gridx, int gridy )
    {
        Border border = new BevelBorder( BevelBorder.LOWERED, 
                                        Color.GRAY, Color.BLACK );
        JLabel title = new JLabel( text );
        //title.setSize( new Dimension( _colWidth, 40 ));
        title.setPreferredSize( new Dimension( width, 40 ));
        title.setBorder( border );
        title.setHorizontalAlignment( SwingConstants.CENTER );
        gbc.gridx = gridx; gbc.gridy = gridy;
        gbag.setConstraints( title, gbc );
        add( title );
        return title;
    }
    //-------------------- makeEditorPane( String, int, int ) -----------
    /**
     * make a JLabel to serve as EditorPane title
     */
    private JEditorPane makeEditorPane( int width, 
                                       GridBagLayout gbag, GridBagConstraints gbc, 
                                       int gridx, int gridy )
    {
        Border border = new BevelBorder( BevelBorder.LOWERED, 
                                        Color.GRAY, Color.BLACK );
        JEditorPane p = new JEditorPane( "text/plain", "  " );
        p.setEditable( false );
        p.setBorder( border );
        p.setPreferredSize( new Dimension( width, 700 ));
        gbc.gridx = gridx; gbc.gridy = gridy;
        gbag.setConstraints( p, gbc );
        add( p );
        return p;
    }
    
    
    //------------------------ setInput( String ) -------------------
    /**
     * update the input text area
     */
    public void setInput( String text )
    {
        _input.setText( text );
        _match.setText( " " );
        update();
    }
    //------------------------ setMatch( String ) --------------
    /**
     * update the match text area for Split
     */
    public void setMatch( String text )
    {
        _matchLabel.setText( "Matches" );
        _match.setText( text );
        update();
    }
    //------------------------ setMatch( String, count ) --------------
    /**
     * update the match text area
     */
    public void setMatch( String text, int count )
    {
        setMatchCount( count );
        _match.setText( text );
        update();
    }
    //---------------------- setMatchCount( count ) -------------------
    /**
     * update the match text label to include the count
     */
    public void setMatchCount( int count )
    {
        if ( count == 0 )
            _matchLabel.setText( "Matches" );
        else
            _matchLabel.setText( "Matches: count = " + count );
        update();
    }
    //------------------------ update() -----------------------------
    /**
     * update the graphical representation of the list being shown
     */
    public void update()
    {
        this.revalidate();
        this.repaint();
    } 
    
    //-------------------- paintComponent ------------------------------
    /**
     * draw the arrows between items
     */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
    }
}