import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class insertHasSubjectGUI
{
    private Library library;
    private JFrame frame;
    public insertHasSubjectGUI(Library lib){
        
        library = lib;
        InsertSubject();
    }
    void InsertSubject()
    {
       
        String[] labels = { "callNumber: ", "subject: " };
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
        frame = new JFrame( "Insert Subject" );
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
                if(library.vaildBook( text[0] )){
                    
                
                
                library.insertHasSubject( text[0], text[1] );
                JOptionPane.showMessageDialog( null, "Subject has been inserted" , "Subject has been inserted", JOptionPane.INFORMATION_MESSAGE);
                }else
                JOptionPane.showMessageDialog( null, "No such a book!" , "Invalid Book", JOptionPane.WARNING_MESSAGE );
                
            }

        } );

    }

    
}