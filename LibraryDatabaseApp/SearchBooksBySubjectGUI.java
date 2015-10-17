import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchBooksBySubjectGUI
{
    private Library library;
    private JFrame frame;
    public SearchBooksBySubjectGUI(Library lib){
        
        library = lib;
        searchBySubject();
    }
    void searchBySubject()
    {
       
        String[] labels = { "Subject: " };
        JButton button = new JButton( "Submit" );
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

        p.add( button1 );
        p.add( button );
        button1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        // Lay out the panel.
        SpringUtilities.makeCompactGrid( p, textFields.length+ 1, 2, // rows, cols
                6, 6, // initX, initY
                6, 6 ); // xPad, yPad

        // Create and set up the window.
        frame = new JFrame( "Search Book By Subject" );
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
              
                new ShowTableData("Books by Subject", library.searchBooksByColumnNames(2 , text[0]) , library.searchBooksBy(2 , text[0]));
            }

        } );

    }

    
}