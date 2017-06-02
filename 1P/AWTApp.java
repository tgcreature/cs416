/**
 * AWTApp.java -- CS416 Programming assignment 1; a simple awt application
 * 
 * This is a Swing application based on the pattern described in Ch 7 of
 * Sanders and van Dam, Object-Oriented Programming in Java.
 * 
 * All the application-specific code is in the AWTPanel class
 * 
 * @author Travis Calley
 */

import javax.swing.*;

public class AWTApp extends JFrame
{
    //------------------ constructor ---------------------------------
    /**
     * Construct a JFrame for the application and put a JPanel in it.
     */
    public AWTApp( String title, String[] args )
    {
        super( title );            // specify window title
        this.setSize( 700, 500 );  // define window size
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        // All application specific code is in the AWTPanel constructor.
        // AWTPanel extends JPanel. 
        
        AWTPanel panel = new AWTPanel( args ); // create graphics in JPanel
        this.add( panel );                     // add it to the frame
        
        this.setVisible( true );
    }
    
    //------------------------ main -----------------------------------
    public static void main( String[] args )
    {
        AWTApp app = new AWTApp( "Drawing in AWT/Swing", args );
    }
}