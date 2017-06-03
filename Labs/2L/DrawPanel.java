/**
 * DrawPanel: template for JPanel class for a Swing based application.
 * 
 * Last modified: rdb 01/22/13 to use ArrayList of AShapes
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DrawPanel extends JPanel
{
    //------ instance variables for contents of panel -------
    ArrayList<AShape> aObjects;    // all A-objects that need to be drawn
    
    //------ Constructor ------------
    public DrawPanel()
    {
        super();
        setLayout( null );   // need this in order to draw in the panel.
        this.setBackground( Color.GRAY );
        
        // Create graphical AShapes and put into a list
        aObjects = new ArrayList<AShape>();
        
        JSnowMan man = new JSnowMan( 50, 50 );
        this.add( man );
        man = new JSnowMan( 250, 50 );
        this.add( man );
        man = new JSnowMan( 50, 300 );
        this.add( man );
        
        // create two JPolygon objects; nothing will appear until
        //    JPolygon implementation is completed
        int[] houseX = { 0, 0, 15, 15, 25, 25, 40, 40, 20 };
        int[] houseY = { 20, 60, 60, 30, 30, 60, 60, 20, 0 };
        
        JPolygon house = new JPolygon( houseX, houseY );
        house.setFrameColor( Color.BLACK );
        house.setFillColor( Color.GREEN );
        this.add( house );
        house.setLocation( 400, 100 );
        
        int[] hexagonX = { 0, 0, 20, 40, 40, 20  };
        int[] hexagonY = { 20, 40, 60, 40, 20, 0 };
        JPolygon hex = new JPolygon( hexagonX, hexagonY );
        hex.setLocation( 500, 100 );
        this.add( hex );
        
        // new Polygon
        int[] triX = { 0, 40, 60 };
        int[] triY = { 0, 40, 40  };
        JPolygon tri = new JPolygon( triX, triY );
        tri.setColor( Color.BLUE );
        tri.setLocation( 200, 200 );
        this.add( tri );
        
        // new Polygon
        int[] polyX = { 0, 0, 20, 30, 40, 40, 20 };
        int[] polyY = { 20, 40, 60, 80, 40, 20, 0 };
        JPolygon poly = new JPolygon( polyX, polyY );
        poly.setLocation( 350, 350 );
        poly.setColor( Color.WHITE );
        this.add( poly );
        
        //AEllipse
        AEllipse ell = new AEllipse();
        ell.setColor( Color.RED );
        ell.setSize( 50, 50 );
        ell.setLocation( 300, 250 );
        aObjects.add( ell );
        
        //ARectangle
        ARectangle rect = new ARectangle();
        rect.setColor( Color.BLUE );
        rect.setLocation( 150, 50 );
        aObjects.add( rect );
    }
    //--------- paintComponent( Graphics ) ---------------
    public void paintComponent( Graphics aBrush )
    {
        super.paintComponent( aBrush );
        
        // draw each AShape on the panel
        Graphics2D brush2D = (Graphics2D) aBrush;
        for ( AShape shape: aObjects )
            shape.display( brush2D );
    }
}