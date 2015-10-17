import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibirianGUI
{
    private Library library;
    private JFrame LibirianFrame;
    private JPanel LibirianPane;
     
    
    public LibirianGUI(Library lib){
        
        library = lib;
        clerkGUI();
    }
    
    void clerkGUI()
    {
       
    	LibirianFrame = new JFrame( "LibrarianGUI" );
    	LibirianFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    	LibirianPane = new JPanel( new GridLayout( 3, 50 ) );

    	//ClerktextArea = new JTextArea( 20, 30 );
        LibirianFrame.getContentPane().add( LibirianPane, BorderLayout.CENTER );
        //LibirianFrame.getContentPane().add( ClerktextArea, BorderLayout.NORTH );
        
        JPanel mainMenuEast = new JPanel( new GridLayout( 1, 50 ) );
        LibirianFrame.getContentPane().add( mainMenuEast, BorderLayout.EAST );
        JButton button1, button2, button3, button4, button5, button7, button8,button11, button12;

        button1 = new JButton( "Add Books" );
        button2 = new JButton( "Remove a Book" );
        button3 = new JButton( "Remove a Borrower" );
        button4 = new JButton( "Report 1" );
        button5 = new JButton( "Report 2" );
      
        button7 = new JButton( "Show Books" );
        button8 = new JButton( "Show BookCopy" );
    
        button11 = new JButton( "Add Subject" );
        button12 = new JButton( "Insert Author " );
        
        LibirianPane.add( button1 );
        LibirianPane.add( button2 );
        LibirianPane.add( button3 );
        LibirianPane.add( button4 );
        LibirianPane.add( button5 );
        
        LibirianPane.add( button7 );
        LibirianPane.add( button8 );
     
        LibirianPane.add( button11 );
        LibirianPane.add( button12 );
        
        button1.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new InsertBookGUI(library);
                
            }
        } );
        button2.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new DeleteBookGUI(library);
                
            }
        } );
        button3.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
            	new DeleteBorrowerGUI(library);
                
            }
        } );
        button4.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new EnterSubjectForReport1GUI(library);
                
            }
        } );
        button5.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new InsertReportYearAndNumberGUI(library);
                
            }
        } );
      
        
        button7.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                     new ShowTableData("Books" , library.showEntryNames( "Book" ), library.showEntriesInTable( "Book" )) ;
                
            }
        } );
        
        button8.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                     new ShowTableData("BookCopies" , library.showEntryNames( "BookCopy" ), library.showEntriesInTable( "BookCopy" )) ;
                
            }
        } );
        
     
        
     
        
        button11.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                    new insertHasSubjectGUI(library);
                
            }
        } );
        
        button12.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                    new insertHasAuthorGUI(library);
                
            }
        } );
        
        LibirianFrame.pack();
        LibirianFrame.setVisible( true );
        
        
    }

    
}