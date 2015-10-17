import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteBorrowerGUI
{
    private Library library;
    private JFrame frame;

    public DeleteBorrowerGUI( Library lib )
    {

        library = lib;
        getBid();
    }

    void getBid()
    {

        String[] labels = { "Enter Borrower Id: " };
        JButton button = new JButton( "Submit" );
        JButton button1 = new JButton( "CAncel" );

        final JTextField[] textFields = new JTextField[ labels.length ];

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
        // Lay out the panel.
        SpringUtilities.makeCompactGrid( p, textFields.length + 1, 2, // rows,
                                                                      // cols
                6, 6, // initX, initY
                6, 6 ); // xPad, yPad

        // Create and set up the window.
        frame = new JFrame( "Delete Borrower" );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        // Set up the content pane.
        p.setOpaque( true ); // content panes must be opaque
        frame.setContentPane( p );

        // Display the window.
        frame.pack();
        frame.setVisible( true );

        button1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        
        button.addActionListener( new ActionListener()
        {

            public void actionPerformed( ActionEvent e )
            {
                String[] text = new String[ textFields.length ];

                for( int i = 0; i < textFields.length; i++ )
                {
                    text[ i ] = textFields[ i ].getText();
                }
                int bid = Integer.parseInt( text[ 0 ] );

                if(library.validBorrower( bid )){
                int caseNum = library.deleteBorrowerCases( bid );

                switch( caseNum )
                {
                case 0:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Borrower has been deleted",
                                    "Delete Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "The borrower has a book that he/she has not returned",
                                    "Borrower has book out",
                                    JOptionPane.WARNING_MESSAGE );
                    break;
                case 2:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "There is a fine associated with a borrower",
                                    "Fine Exists",
                                    JOptionPane.WARNING_MESSAGE );
                    break;

                default:
                    System.out.println("something went wrong");
                }
            }else
                JOptionPane
                .showMessageDialog(
                        null,
                        "Borrower doesn't exist",
                        "Borrower is invalid",
                        JOptionPane.WARNING_MESSAGE );
            }

        } );

    }

}