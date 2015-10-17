import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBooksGUI
{
    private Library library;
    private JFrame BorrowerFrame;
    private JPanel BorrowerPane;
     
    
    public SearchBooksGUI(Library lib){
        
        library = lib;
        searchGUI();
    }
    
    void searchGUI()
    {
       
        BorrowerFrame = new JFrame( "Search Books" );
        BorrowerFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        BorrowerPane = new JPanel( new GridLayout( 2, 50 ) );

        //ClerktextArea = new JTextArea( 20, 30 );
        BorrowerFrame.getContentPane().add( BorrowerPane, BorderLayout.CENTER );
        //BorrowerFrame.getContentPane().add( ClerktextArea, BorderLayout.NORTH );
        
        JPanel mainMenuEast = new JPanel( new GridLayout( 1, 50 ) );
        BorrowerFrame.getContentPane().add( mainMenuEast, BorderLayout.EAST );
        JButton button1, button2, button3;

        button1 = new JButton( "Search by title" );
        button2 = new JButton( "Search by author" );
        button3 = new JButton( "Search by subject" );
        
        BorrowerPane.add( button1 );
        BorrowerPane.add( button2 );
        BorrowerPane.add( button3 );
        button1.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                BorrowerFrame.dispose();
            }
        });
        button1.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new SearchBooksByTitleGUI(library);
                
            }
        } );
        button2.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new SearchBooksByAuthorGUI(library);
                
            }
        } );
        button3.addActionListener( new ActionListener() // should be correct
        {
            public void actionPerformed( ActionEvent e )
            {
                new SearchBooksBySubjectGUI(library);
                
            }
        } );
        
        
        BorrowerFrame.pack();
        BorrowerFrame.setVisible( true );
        
        
    }

    
}