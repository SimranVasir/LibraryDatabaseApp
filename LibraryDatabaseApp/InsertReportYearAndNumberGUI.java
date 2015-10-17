import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InsertReportYearAndNumberGUI
{
    private Library library;
    private JFrame frame;
    public InsertReportYearAndNumberGUI(Library lib){
        
        library = lib;
        InsertReport();
    }
    void InsertReport()
    {
       
        String[] labels = { "year: ", "Number of books to display: " };
        JButton button = new JButton( "Generate" );
        JButton button1 = new JButton( "CAncel" );
        
        final JTextField[] textFields = new JTextField[ labels.length];
        
        // Create and populate the panel.
        JPanel p = new JPanel( new SpringLayout() );
       
        
        for( int i = 0; i < textFields.length; i++ )
        {
            JLabel l = new JLabel( labels[ i ], JLabel.TRAILING );
            p.add( l );
            textFields[ i ] = new JTextField( 40 );
            l.setLabelFor( textFields[ i ] );
            p.add( textFields[ i ] );

        }
        button1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        p.add( button1 );
        p.add( button );
        // Lay out the panel.
        SpringUtilities.makeCompactGrid( p, textFields.length+ 1, 2, // rows, cols
                6, 6, // initX, initY
                6, 6 ); // xPad, yPad

        // Create and set up the window.
        frame = new JFrame( "Report 2" );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        // Set up the content pane.
        p.setOpaque( true ); // content panes must be opaque
        frame.setContentPane( p );

        // Display the window.
        frame.pack();
        frame.setVisible( true );

        button.addActionListener( new ActionListener()
        {

            public void actionPerformed( ActionEvent e )
            {
                String[]  text = new String[ textFields.length ];
                
               
              
                
                for( int i = 0; i < textFields.length; i++ )
                {
                   
                    text[ i ] = textFields[ i ].getText();
                    

                }
                int year, n;
                year = Integer.parseInt( text[0] );
                n = Integer.parseInt( text[1] );
                
                new ShowTableData("Report 2", library.generateMostPopularBooksReportColumnNames(year), library.generateMostPopularBooksReport( year, n ));
                
            }

        } );

    }

    
}