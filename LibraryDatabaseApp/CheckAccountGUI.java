import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckAccountGUI extends JPanel
{
    Library library;
    int bid;
    
    public CheckAccountGUI(Library lib, int borrowerId)
    {
        library = lib;
        bid = borrowerId;
        processReturnsGUI();
    }  
    
    void processReturnsGUI(){
        
        JPanel LibraryPane0;
        JPanel LibraryPane1;
        JPanel LibraryPane2;
        
        JFrame frame = new JFrame("Account Information");
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        
                
        LibraryPane0 = new JPanel( new BorderLayout() );
        LibraryPane1 = new JPanel( new BorderLayout() );
        LibraryPane2 = new JPanel( new BorderLayout() );

        JTable borrowingtable = new JTable(library.checkAccountBorrowingData(bid), library.checkAccountBorrowingColumnNames(bid));
        
        JTable boridFinetable = new JTable(library.checkAccountFinesData(bid), library.checkAccountFinesColumnNames(bid));
        
        JTable holdsTable = new JTable(library.checkAccountHoldsData(bid), library.checkAccountHoldsColumnNames(bid));
        
        
        //table.setPreferredSize(new Dimension(100, 100));
        JScrollPane scrollPane0 = new JScrollPane(borrowingtable);
        JScrollPane scrollPane1 = new JScrollPane(boridFinetable);
        JScrollPane scrollPane2 = new JScrollPane(holdsTable);
        
        //LibraryPane.add( table );
        LibraryPane0.add( scrollPane0 );
        LibraryPane1.add( scrollPane1 );
        LibraryPane2.add( scrollPane2 );
        
        frame.getContentPane().add( LibraryPane0, BorderLayout.NORTH );
        frame.getContentPane().add( LibraryPane1, BorderLayout.CENTER );
        frame.getContentPane().add( LibraryPane2, BorderLayout.SOUTH );
        frame.pack();
        frame.setVisible(true);
     

    }

}
