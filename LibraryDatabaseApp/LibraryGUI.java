import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LibraryGUI implements ActionListener
{

    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel LibraryPane;
    private JTextArea textArea;
    private JLabel label;
    private Library lib;

    public LibraryGUI(Library library)
    {
        lib = library;
    }

    public void showLibrary()
    {
        frame = new JFrame( "LibraryGUI" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        LibraryPane = new JPanel( new GridLayout( 2, 50 ) );

        textArea = new JTextArea("Welcome to the Library! Please select your role!",3, 25 );
        frame.getContentPane().add( LibraryPane, BorderLayout.CENTER );
        frame.getContentPane().add( textArea, BorderLayout.NORTH );
        
        JPanel mainMenuEast = new JPanel( new GridLayout( 1, 50 ) );
        frame.getContentPane().add( mainMenuEast, BorderLayout.EAST );
      
        initializeMenu();
        
        frame.pack();
        frame.setVisible( true );
    }


    private void initializeMenu()
    {
        JMenu menu;
        JMenuItem menuItem1, menuItem2, menuItem3;
        menu = new JMenu( "Select Role" );
        menuItem1 = new JMenuItem( "Clerk" );
        menuItem2 = new JMenuItem( "Borrower" );
        menuItem3 = new JMenuItem( "Libirian" );
         
        menuItem1.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                new ClerkGUI(lib);
            }
        } );
        menuItem2.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                new BorrowerGUI(lib);
            }
        } );
        menuItem3.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                new LibirianGUI(lib);
            }
        } );
        menu.add( menuItem1 );
        menu.add( menuItem2 );
        menu.add( menuItem3 );
         
        menuBar = new JMenuBar();
        menuBar.add( menu );
        frame.setJMenuBar( menuBar );
    }

    public void actionPerformed( ActionEvent e )
    {

    }

   /* public static void main( String args[] )
    {
        LibraryGUI g = new LibraryGUI();
        g.showLibrary();

    }*/
}

