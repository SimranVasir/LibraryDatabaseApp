import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkGUI
{
    private Library library;
    private JFrame ClerkFrame;
    private JPanel ClerkPane;
    
    
    public ClerkGUI(Library lib){
        
        library = lib;
        clerkGUI();
    }
    
    void clerkGUI()
    {
       
    	ClerkFrame = new JFrame( "ClerkGUI" );
    	ClerkFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    	ClerkPane = new JPanel( new GridLayout( 3, 50 ) );

    	//ClerktextArea = new JTextArea( 20, 30 );
        ClerkFrame.getContentPane().add( ClerkPane, BorderLayout.CENTER );
        //ClerkFrame.getContentPane().add( ClerktextArea, BorderLayout.NORTH );
        
        JPanel mainMenuEast = new JPanel( new GridLayout( 1, 50 ) );
        ClerkFrame.getContentPane().add( mainMenuEast, BorderLayout.EAST );
        JButton button1, button2, button3, button4, button5, button6, button7,button8;

        button1 = new JButton( "Add a Borrower" );
        button2 = new JButton( "Insert Borrowing" );
        button3 = new JButton( "Process Returns" );
        button4 = new JButton( "Check Over Dues" );
        button5 = new JButton( "Show Borrowers" );
        button6 = new JButton( "Show Borrowing" );
        button7 = new JButton( "Show Holds" );
        button8 = new JButton( "Show Fines" );
        
        ClerkPane.add( button1 );
        ClerkPane.add( button2 );
        ClerkPane.add( button3 );
        ClerkPane.add( button4 );
        ClerkPane.add( button5 );
        ClerkPane.add( button6 );
        ClerkPane.add( button7 );
        ClerkPane.add( button8 );
        
        button1.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	
            	new InsertBorrowerGUI(library);
                
            }
        } );
        button2.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new InsertBorrowingGUI(library);
                
            }
        } );
        button3.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new ProcessReturnGUI(library);
                
            }
        } );
        button4.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new ShowTableData("Overdue Items", library.generateOverdueItemsColumnNames(), library.generateOverdueItems());
                
            }
        } );
        
        button5.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                     new ShowTableData("Borrowers" , library.showEntryNames( "Borrower" ), library.showEntriesInTable( "Borrower" )) ;
                
            }
        } );
        
        button6.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                     new ShowTableData( "Borrowings", library.showEntryNames( "Borrowing" ), library.showEntriesInTable( "Borrowing" )) ;
                
            }
        } );
        
        button7.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                     new ShowTableData("HoldRequests" , library.showEntryNames( "HoldRequest" ), library.showEntriesInTable( "HoldRequest" )) ;
                
            }
        } );
        
        button8.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new ShowTableData("Fines", library.showEntryNames( "Fine" ), library.showEntriesInTable( "Fine" )) ;
                
            }
        } );
        
        ClerkFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        ClerkFrame.pack();
        ClerkFrame.setVisible( true );
        
        
    }
    
}