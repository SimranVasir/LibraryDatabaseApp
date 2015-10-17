import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertBorrowerGUI
{
    Library library;
    
    public InsertBorrowerGUI(Library lib){
        
        library = lib;
        insertBorrowerGUI();
    }
    void insertBorrowerGUI()
    {
       
        String[] labels = { "*Password: ", "*Name: ", "*Address: ", "*Phone: ",
                "emailAddress: ", "*sinOrStNo: ", "*Expiry Date(dd-MMM-yy): ", "*Type: " };
        JButton button = new JButton( "Submit" );
        JButton button1 = new JButton( "CAncel" );
        final JTextField[] textFields = new JTextField[ 8 ];
        int numPairs = labels.length;

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
        final JFrame frame = new JFrame( "Insert Borrower" );
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
                    System.out.println( text + "HI");
                   
                }

                int type = Integer.parseInt( text[ 7 ] );

                library.insertBorrower( text[0], text[1], text[2], text[3],
                        text[4], text[5], text[6], type );
                JOptionPane
                .showMessageDialog(
                        null,
                        "Borrower is inserted",
                        "Borrower is inserted",
                        JOptionPane.INFORMATION_MESSAGE );
            }

        } );

    }

    
}