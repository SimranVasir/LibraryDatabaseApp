import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowerGUI
{
    private Library library;
    private JFrame BorrowerFrame;
    private JPanel BorrowerPane;
     
    
    public BorrowerGUI(Library lib){
        
        library = lib;
        borrowerGUI();
    }
    
    void borrowerGUI()
    {
       
    	BorrowerFrame = new JFrame( "BorrowerGUI" );
    	BorrowerFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    	BorrowerPane = new JPanel( new GridLayout( 2, 50 ) );

    	//ClerktextArea = new JTextArea( 20, 30 );
    	BorrowerFrame.getContentPane().add( BorrowerPane, BorderLayout.CENTER );
        //BorrowerFrame.getContentPane().add( ClerktextArea, BorderLayout.NORTH );
        
        JPanel mainMenuEast = new JPanel( new GridLayout( 1, 50 ) );
        BorrowerFrame.getContentPane().add( mainMenuEast, BorderLayout.EAST );
        JButton button1, button2, button3, button4;

        button1 = new JButton( "Search Book" );
        button2 = new JButton( "Check Account" );
        button3 = new JButton( "Pay Fine" );
        button4 = new JButton( "Hold Request" );
        
        BorrowerPane.add( button1 );
        BorrowerPane.add( button2 );
        BorrowerPane.add( button3 );
        BorrowerPane.add( button4 );
        
        button1.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new SearchBooksGUI(library);
                
            }
        } );
        button2.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                
            	new CheckAccountBidGUI(library);
                
            }
        } );
        button3.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new PayFineGUI(library);
                
            }
        } );
        button4.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new InsertHoldGUI(library);
                
            }
        } );
        
        BorrowerFrame.pack();
        BorrowerFrame.setVisible( true );
        
        
    }

    
}