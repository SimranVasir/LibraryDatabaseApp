import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteBookGUI
{
    private Library library;
    private JFrame frame;

    public DeleteBookGUI( Library lib )
    {

        library = lib;
        getBid();
    }

    void getBid()
    {

        String[] labels = { "Enter callNumber: " };
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
        frame = new JFrame( "Delete Book" );
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
                String callNumber = text[ 0 ];

                int caseNum = library.deleteBookCase( callNumber );

                if(library.vaildBook( callNumber )){
                switch( caseNum )
                {
                case 0:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Book has been deleted",
                                    "Delete Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "A book copy has not been returned",
                                    "Book is out",
                                    JOptionPane.WARNING_MESSAGE );
                    break;
                case 2:
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "There is a fine associated with this book",
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
                        "Book doesn't exist",
                        "Book is invalid",
                        JOptionPane.WARNING_MESSAGE );
            }

        } );

    }

}