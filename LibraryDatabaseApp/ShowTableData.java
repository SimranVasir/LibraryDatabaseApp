import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ShowTableData
{
public ShowTableData(String tableName, String[] columnNames, String data[][]){
   
    JPanel LibraryPane;
    
    JFrame frame = new JFrame("" + tableName);
    frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
 

    LibraryPane = new JPanel( new BorderLayout() );


    JTable table = new JTable(data, columnNames);
    
    //table.setPreferredSize(new Dimension(100, 100));
    JScrollPane scrollPane = new JScrollPane(table);
    
    scrollPane.setPreferredSize( new Dimension(600, 400) );
    
    //LibraryPane.add( table );
    LibraryPane.add( scrollPane );
    
    
    frame.getContentPane().add( LibraryPane, BorderLayout.CENTER );
    frame.pack();
    frame.setVisible(true);

}
    
}

