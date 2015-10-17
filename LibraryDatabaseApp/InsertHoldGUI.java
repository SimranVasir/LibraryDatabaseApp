import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InsertHoldGUI
{
    private Library library;
    private JFrame frame;
    public InsertHoldGUI(Library lib){
        
        library = lib;
        insertHoldGUI();
    }
    void insertHoldGUI()
    {
       
        String[] labels = { "bid: ", "callNumber: " };
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
        frame = new JFrame( "Insert Hold Request" );
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
                    textFields[i].setText( "" );
                    

                }
                
                
                int bid = Integer.parseInt( text[0] );
                
                
                
                if(library.validBorrower(bid)){
                   
                   
                        int bookCopy1 = library.findBookCopy(text[1],1);
                        
                        if(bookCopy1 != 0){
                            
                            JOptionPane.showMessageDialog( null, "Books is in library" , "Book is in", JOptionPane.WARNING_MESSAGE );
                        }
                        else{
                            
                            library.insertHold( bid, text[1] );
                            JOptionPane.showMessageDialog( null, "Hold has been inserted" , "Hold has been inserted", JOptionPane.INFORMATION_MESSAGE);
                           //System.out.println("books doesn ot extis");
                            }
                        
                        }
                
                else
                    JOptionPane.showMessageDialog( null, "BorrowerID is invalid" , "Invalid BorrowerID", JOptionPane.WARNING_MESSAGE );
                    //System.out.println("borrow id invalid");
                    }

        } );

    }

    
}