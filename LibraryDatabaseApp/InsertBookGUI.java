import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertBookGUI
{
    Library library;
    
    public InsertBookGUI(Library lib){
        
        library = lib;
        insertBorrowerGUI();
    }
    void insertBorrowerGUI()
    {
       
        String[] labels = { "call number: ", "isbn: ", "title: ", "mainAuthor: ",
                 "publisher: ", "year: " };
        JButton button = new JButton( "Submit" );
        JButton button1 = new JButton( "CAncel" );
        
        int numPairs = labels.length;
        final JTextField[] textFields = new JTextField[ numPairs];
        
        // Create and populate the panel.
        JPanel p = new JPanel( new SpringLayout() );
        for( int i = 0; i < numPairs; i++ )
        {
            JLabel l = new JLabel( labels[ i ], JLabel.TRAILING );
            p.add( l );
            textFields[ i ] = new JTextField( 40 );
            l.setLabelFor( textFields[ i ] );
            p.add( textFields[ i ] );

        }
        
        p.add( button1 );
        p.add( button );
        // Lay out the panel.
        SpringUtilities.makeCompactGrid( p, numPairs + 1, 2, // rows, cols
                6, 6, // initX, initY
                6, 6 ); // xPad, yPad

        // Create and set up the window.
        final JFrame frame = new JFrame( "Insert Book" );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        button1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
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
                    //textFields[i].setText( "" );
                   

                }

                int year = Integer.parseInt( text[ 5 ] );
                System.out.println(""+ year);
                
                
                library.insertBook( text[0], text[1], text[2], text[3],
                       text[4], year );
                JOptionPane
                .showMessageDialog(
                        null,
                        "Book is inserted",
                        "Book is inserted",
                        JOptionPane.INFORMATION_MESSAGE );
            }

        } );

    }

    
}