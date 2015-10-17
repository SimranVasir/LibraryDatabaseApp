import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProcessReturnGUI
{
    private Library library;
    private JFrame frame;

    public ProcessReturnGUI( Library lib )
    {

        library = lib;
        processReturnsGUI();
    }

    void processReturnsGUI()
    {
       
        String[] labels = {  "callNumber: " , "copyNo: " };
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
        frame = new JFrame( "Process Return" );
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
                
                
                int copyNo = Integer.parseInt( text[1] );
                
                int borid = library.validBorrowing( text[0], copyNo );
                
                System.out.println("borid: " +borid);
                
                if( borid > 0){
                   
                        library.updateBorrowing( borid );
                        JOptionPane.showMessageDialog( null, "Book has been returned" , "Book has been returned", JOptionPane.INFORMATION_MESSAGE);
                        
                        if(library.holdExists( text[0], copyNo )){
                        	JOptionPane.showMessageDialog( null, "A email has been send to the holder of book" , "Holdexist", JOptionPane.INFORMATION_MESSAGE );
                        }
                    
                        int dueDays = library.checkOverDueDays( borid );
                        
                        if(dueDays > 0 && dueDays <=15){
                            
                            library.insertFine( borid, 0 );
                            
                        }
                        else 
                            if(dueDays > 15)
                            {
                                if(!library.fineExists( borid )){
                                library.insertFine( borid, 30 );
                                }
                            }
                    }
                else
                    JOptionPane.showMessageDialog( null, "CallNumber is Invalid. No such borrowing event exists" , "Invalid CallNumber", JOptionPane.WARNING_MESSAGE );
                    //System.out.println("borrow id invalid");
            }

        } );

    }
}
    
    
