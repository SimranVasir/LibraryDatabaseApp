// We need to import the java.sql package to use JDBC
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */
public class Library implements ActionListener
{
    // command line reader
    private BufferedReader in = new BufferedReader( new InputStreamReader(
            System.in ) );

    private Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;

    /*
     * constructs login window and loads JDBC driver
     */
    public Library()
    {
        mainFrame = new JFrame( "User Login" );

        JLabel usernameLabel = new JLabel( "Enter username: " );
        JLabel passwordLabel = new JLabel( "Enter password: " );

        usernameField = new JTextField( 10 );
        passwordField = new JPasswordField( 10 );
        passwordField.setEchoChar( '*' );

        JButton loginButton = new JButton( "Log In" );

        JPanel contentPane = new JPanel();
        mainFrame.setContentPane( contentPane );

        // layout components using the GridBag layout manager

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout( gb );
        contentPane
                .setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets( 10, 10, 5, 0 );
        gb.setConstraints( usernameLabel, c );
        contentPane.add( usernameLabel );

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets( 10, 0, 5, 10 );
        gb.setConstraints( usernameField, c );
        contentPane.add( usernameField );

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets( 0, 10, 10, 0 );
        gb.setConstraints( passwordLabel, c );
        contentPane.add( passwordLabel );

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets( 0, 0, 10, 10 );
        gb.setConstraints( passwordField, c );
        contentPane.add( passwordField );

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets( 5, 10, 10, 10 );
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints( loginButton, c );
        contentPane.add( loginButton );

        // register password field and OK button with action event handler
        passwordField.addActionListener( this );
        loginButton.addActionListener( this );

        // anonymous inner class for closing the window
        mainFrame.addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent e )
            {
                System.exit( 0 );
            }
        } );

        // size the window to obtain a best fit for the components
        mainFrame.pack();

        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( ( d.width - r.width ) / 2,
                ( d.height - r.height ) / 2 );

        // make the window visible
        mainFrame.setVisible( true );

        // place the cursor in the text field for the username
        usernameField.requestFocus();

        try
        {
            // Load the Oracle JDBC driver
            DriverManager
                    .registerDriver( new oracle.jdbc.driver.OracleDriver() );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            System.exit( -1 );
        }
    }

    /*
     * connects to Oracle database named ug using user supplied username and
     * password
     */
    private boolean connect( String username, String password )
    {
        String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1521:ug";

        try
        {
            con = DriverManager.getConnection( connectURL, username, password );

            System.out.println( "\nConnected to Oracle!" );
            return true;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message:   " + ex.getMessage() );
            return false;
        }
    }

    /*
     * event handler for login window
     */
    public void actionPerformed( ActionEvent e )
    {
        if( connect( usernameField.getText(),
                String.valueOf( passwordField.getPassword() ) ) )
        {
            // if the username and password are valid,
            // remove the login window and display a text menu
            mainFrame.dispose();
            // showMenu();
            LibraryGUI g = new LibraryGUI( this );
            g.showLibrary();

        }
        else
        {
            loginAttempts++;

            if( loginAttempts >= 3 )
            {
                mainFrame.dispose();
                System.exit( -1 );
            }
            else
            {
                // clear the password
                passwordField.setText( "" );
            }
        }

    }

    /*
     * displays simple text interface
     */
    private void showMenu()
    {

        int choice;
        boolean quit;

        quit = false;

        try
        {
            // disable auto commit mode
            con.setAutoCommit( false );
            insertBorrower( "asdfads", "null", "null", "7788894500", "asdfasd",
                    "a;j", "10-Jan-10", 1 );

            /*
             * while( !quit ) { System.out.print(
             * "\n\nPlease choose one of the following: \n" ); System.out.print(
             * "1.  Insert Borrower\n" ); System.out.print(
             * "2.  Delete Borrower\n" ); System.out.print(
             * "3.  Update Borrower\n" ); System.out.print(
             * "4.  Show Borrower\n" ); System.out.print( "5.  Insert Book\n" );
             * System.out.print( "6.  Delete Book\n" ); System.out.print(
             * "7.  Show Book\n" ); System.out.print( "8.  Insert HasAuthor\n"
             * ); System.out.print( "9.  Delete HasAuthor\n" );
             * System.out.print( "10.  show HasAuthor\n" ); System.out.print(
             * "11.  Insert HasSubject\n" ); System.out.print(
             * "12.  Delete HasSubject\n" ); System.out.print(
             * "13.  Show HasSubject\n" ); System.out.print(
             * "14.  Insert BookCopy\n" ); System.out.print(
             * "15.  Delete BookCopy\n" ); System.out.print(
             * "16.  Show BookCopy\n" ); System.out.print(
             * "17.  Insert BorrowerType\n" ); System.out.print(
             * "18.  Delete BorrowerType\n" ); System.out.print(
             * "19.  Update BorrowerType\n" ); System.out.print(
             * "20.  insert HoldRequest\n" ); System.out.print(
             * "21.  Delete HoldRequest\n" ); System.out.print(
             * "22.  Insert Borrowing\n" ); System.out.print(
             * "23.  Delete Borrowing\n" ); System.out.print(
             * "24.  Update Borrowing\n" ); System.out.print(
             * "25.  Insert Fine\n" ); System.out.print( "26.  Delete Fine\n" );
             * System.out.print( "27.  Update Fine\n" ); System.out.print(
             * "28.  Quit\n>> " );
             * 
             * choice = Integer.parseInt( in.readLine() );
             * 
             * System.out.println( " " );
             * 
             * switch( choice ) { case 1:
             * 
             * break; case 2: deleteBorrower(); break; case 3: updateBorrower();
             * break; case 5: insertBook(); break; case 6: deleteBook(); break;
             * case 7: showBook(); break; case 8: insertHasAuthor(); break; case
             * 9: deleteHasAuthor(); break; case 10: showHasAuthor(); break;
             * case 11: insertHasSubject(); break; case 12: deleteHasSubject();
             * break; case 13: showHasSubject(); break; case 14:
             * insertBookCopy(); break; case 15: deleteBookCopy(); break; case
             * 16: showBookCopy(); break; // case 4: showBorrower(); break; case
             * 17: insertBorrowerType(); break; case 18: deleteBorrowerType();
             * break; case 19: updateBorrowerType(); break; case 20:
             * insertHoldRequest(); break; case 21: deleteHoldRequest(); break;
             * case 22: insertBorrowing(); break; case 23: deleteBorrowing();
             * break; case 24: updateBorrowing(); break; case 25: insertFine();
             * break; case 26: deleteFine(); break; case 27: updateFine();
             * break;
             * 
             * case 28: quit = true; }
             */

            con.close();
            in.close();
            System.out.println( "\nGood Bye!\n\n" );
            System.exit( 0 );
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );

            try
            {
                con.close();
                System.exit( -1 );
            }
            catch( SQLException ex )
            {
                System.out.println( "Message: " + ex.getMessage() );
            }
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
        }
    }

    /*
     * findBookCopy returns an available copyNo for a given callNumber. A copyNo
     * is returned if there is an available copy in the library (not on hold or
     * out) is there is no available copy, 0 is returned
     */

    public int findBookCopy( String mycallNumber, int status )
    {
        int copyNo = 0;
        Statement stmt;
        ResultSet rs = null;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT copyNo FROM BookCopy WHERE callNumber = '"
                            + mycallNumber + "'" + "AND status = " + status );

            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();

            int j = 0;
            while( rs.next() )
            {
                copyNo = rs.getInt( 1 );
                j++;
            }

            System.out.println( "numRows: " + j + " numCols " + numCols );

        }
        catch( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return copyNo;
    }

    /**
     * checks whether the borrower exists in the database
     * 
     * 
     * @param bid
     * @return
     */
    public boolean validBorrower( int bid )
    {
        PreparedStatement ps;
        boolean valid = false;
        try
        {

            ps = con.prepareStatement( "SELECT * FROM Borrower WHERE bid = ?" );
            ps.setInt( 1, bid );
            int rowCount = ps.executeUpdate();
            con.commit();
            ps.close();

            if( rowCount == 0 )
            {
                valid = false;
            }
            else
            {
                valid = true;
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }

        }
        return valid;
    }

    /*
     * inserts a Borrower
     */
    void insertBorrower( String password, String name, String address,
            String phoneNo, String emailAddress, String sinOrStNo,
            String expiryDate, int type )
    {

        PreparedStatement ps;

        try
        {

            // disable auto commit mode
            con.setAutoCommit( true );

            System.out.println( "password: " + password + " name: " + name
                    + " address: " + address + " phoneNo: " + phoneNo
                    + "sinorStNo " + sinOrStNo + "expirateDate " + expiryDate
                    + "type: " + type );

            ps = con.prepareStatement( "INSERT INTO Borrower VALUES (bid_counter.nextval,?,?, ?, ?,? ,?,?,?)" );

            ps.setString( 1, password );

            ps.setString( 2, name );

            ps.setString( 3, address );

            ps.setString( 4, phoneNo );

            ps.setString( 6, sinOrStNo );

            ps.setString( 7, expiryDate );

            ps.setInt( 8, type );

            if( emailAddress.length() == 0 )
            {
                ps.setString( 5, null );
            }
            else
            {
                System.out.println( "message?" );
                ps.setString( 5, emailAddress );
            }

            ps.executeUpdate();

            System.out.println( "222message?" );
            // commit work
            con.commit();

            ps.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * deletes a Borrower
     */
    public void deleteBorrower( int bid )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery( "DELETE FROM Borrower WHERE bid =" + bid );

            con.commit();

            rs.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * updates the name of a Borrower
     */
    public void updateBorrower()
    {
        int bid;
        String name;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "UPDATE branch SET name = ? WHERE bid = ?" );

            System.out.print( "\nBorrower ID: " );
            bid = Integer.parseInt( in.readLine() );
            ps.setInt( 2, bid );

            System.out.print( "\nNew Borrower Name: " );
            name = in.readLine();
            ps.setString( 1, name );

            int rowCount = ps.executeUpdate();
            if( rowCount == 0 )
            {
                System.out.println( "\nBorrower " + bid + " does not exist!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void insertBook( String callNumber, String isbn, String title,
            String mainAuthor, String publisher, int year )
    {

        PreparedStatement ps1, ps2;

        try
        {
            System.out.println( "I'm here" + callNumber );

            ps1 = con
                    .prepareStatement( "SELECT * FROM BookCopy WHERE callNumber = ?" );
            ps1.setString( 1, callNumber );
            int rowCount = ps1.executeUpdate();

            if( rowCount != 0 )
            {
                System.out.println( "I'm here1" );
                insertBookCopy( callNumber );
            }
            else
            {

                System.out.println( "I'm here 2" );
                ps2 = con
                        .prepareStatement( "INSERT INTO Book VALUES (?,?,?,?,?,?)" );

                ps2.setString( 1, callNumber );

                ps2.setString( 2, isbn );

                ps2.setString( 3, title );

                ps2.setString( 4, mainAuthor );

                ps2.setString( 5, publisher );

                ps2.setInt( 6, year );

                ps2.executeUpdate();

                insertBookCopy( callNumber );
                System.out.println( "I'm here3" );
                con.commit();
                ps1.close();
                ps2.close();

            }
            // commit work

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void insertBookCopy( String callNumber )
    {

        int copyNo = 1;
        int status = 1;
        PreparedStatement ps1, ps2;

        try
        {
            ps1 = con
                    .prepareStatement( "SELECT copyNo FROM BookCopy where callNumber =?" );
            ps1.setString( 1, callNumber );
            int rowCount = ps1.executeUpdate();

            if( rowCount != 0 )
            {
                System.out.println( "I'm here 5" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt
                        .executeQuery( "SELECT copyNo FROM BookCopy where callNumber = '"
                                + callNumber + "'" );

                System.out.println( "I'm here6" );
                while( rs.next() )
                {
                    copyNo = rs.getInt( 1 );
                }
                copyNo = copyNo + 1;
            }
            System.out.println( "I'm here7" );
            ps2 = con.prepareStatement( "INSERT INTO BookCopy VALUES (?,?,?)" );

            ps2.setString( 1, callNumber );

            ps2.setInt( 2, copyNo );

            ps2.setInt( 3, status );

            ps2.executeUpdate();
            System.out.println( "I'm here8" );
            // commit work
            con.commit();

            ps1.close();
            ps2.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /**
     * Deletes the borrower if it can be deleted and returns 0 upon success;
     * otherwise returns 1 if there is a borrowing event associated with the
     * borrower and the inDate is null returns 2 if there is a borrowing event
     * whose inDate is not null and the borrowing event has a Fine associated
     * with it that has not been paid
     * 
     * @param bid
     * @return
     */
    public int deleteBorrowerCases( int bid )
    {
        Statement stmt;
        ResultSet rs;
        int numCase = 0;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM Borrowing where bid = "
                    + bid + " AND inDate is null" );

            if( rs.next() )
            {

                numCase = 1;
                rs.close();
                return numCase;
            }

            rs = stmt
                    .executeQuery( " SELECT * FROM Borrowing, Fine WHERE Borrowing.borid = Fine.borid AND Borrowing.bid = "
                            + bid
                            + " AND Borrowing.inDate is not Null AND Fine.paidDate is null" );

            if( rs.next() )
            {
                numCase = 2;
                rs.close();
                return numCase;
            }

            numCase = 0;

            deleteBorrower( bid );

            con.commit();

            rs.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

        return numCase;
    }

    /**
     * Deletes the borrower if it can be deleted and returns 0 upon success;
     * otherwise returns 1 if there is a borrowing event associated with the
     * borrower and the inDate is null returns 2 if there is a borrowing event
     * whose inDate is not null and the borrowing event has a Fine associated
     * with it that has not been paid
     * 
     * @param bid
     * @return
     */

    public int deleteBookCase( String callNumber )
    {
        Statement stmt;
        ResultSet rs;
        int numCase = 0;

        try
        {
            stmt = con.createStatement();
            rs = stmt
                    .executeQuery( "SELECT * FROM Borrowing where callNumber = '"
                            + callNumber + "' AND inDate is null" );

            if( rs.next() )
            {

                numCase = 1;
                rs.close();
                return numCase;
            }

            rs = stmt
                    .executeQuery( " SELECT * FROM Borrowing, Fine WHERE Borrowing.borid = Fine.borid AND Borrowing.callNumber = '"
                            + callNumber
                            + "' AND Borrowing.inDate is not Null AND Fine.paidDate is null" );

            if( rs.next() )
            {
                numCase = 2;
                rs.close();
                return numCase;
            }

            numCase = 0;

            deleteBook( callNumber );

            con.commit();

            rs.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

        return numCase;
    }

    public void deleteBook( String callNumber )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery( "DELETE FROM Book WHERE callNumber ='"
                    + callNumber + "'" );

            con.commit();

            rs.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void showBook()
    {
        String isbn;
        String callNumber;
        String title;
        String mainAuthor;
        String publisher;
        String year;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM book" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println( " " );

            // display column names;
            for( int i = 0; i < numCols; i++ )
            {
                // get column name and print it

                System.out.printf( "%-15s", rsmd.getColumnName( i + 1 ) );
            }

            System.out.println( " " );

            while( rs.next() )
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                callNumber = rs.getString( "callNumber" );
                System.out.printf( "%-10.10s", callNumber );

                isbn = rs.getString( "isbn" );
                System.out.printf( "%-20.20s", isbn );

                title = rs.getString( "title" );
                System.out.printf( "%-20.20s", title );

                mainAuthor = rs.getString( "mainAuthor" );
                System.out.printf( "%-15.15s", mainAuthor );

                publisher = rs.getString( "publisher" );
                System.out.printf( "%-15.15s\n", publisher );

                year = rs.getString( "year" );
                System.out.printf( "%-15.15s\n", year );

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
        }
    }

    /*
     * input: tableName - name of the table params - comma seperated string
     * containing the parameters
     * 
     * Example: showAllEntriesInTable("HasAuthor",
     * "01234567890123, Harry Potter"); returns: double array => (")
     */

    /*
     * Function: showEntryNames() input: databaseName - name of the table
     * returns: double array
     */
    public String[] generateReport1ColumnNames( String subject )
    {
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd;

        try
        {
            stmt = con.createStatement();

            if( subject.equals( "" ) )
            {
                rs = stmt
                        .executeQuery( "SELECT CALLNUMBER,OUTDATE,DUEDATE FROM BORROWING WHERE INDATE IS NULL ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();
            }
            else
            {
                rs = stmt
                        .executeQuery( "SELECT BORROWING.CALLNUMBER, BORROWING.OUTDATE,BORROWING.DUEDATE, HASSUBJECT.SUBJECT FROM BORROWING,HASSUBJECT WHERE BORROWING.CALLNUMBER = HASSUBJECT.CALLNUMBER AND BORROWING.INDATE IS NULL AND HASSUBJECT.subject = '"
                                + subject + "' ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();
            }

            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public int[] highlightReport1Rows( String subject )
    {
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int numRows = 0;
        ArrayList<Integer> intList = new ArrayList<Integer>();

        try
        {
            stmt = con.createStatement();

            if( subject.equals( "" ) )
            {
                rs = stmt
                        .executeQuery( "SELECT CALLNUMBER,OUTDATE,sysdate-DUEDATE FROM BORROWING WHERE INDATE IS NULL ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

                System.out.println( "subject = " + subject );

                while( rs.next() )
                {
                    if( (int) ( rs.getFloat( 3 ) ) > 0 )
                    {
                        
                        intList.add( new Integer( numRows ) ) ;
                        numRows++;
                    }
                }

            }
            else
            {
                rs = stmt
                        .executeQuery( "SELECT BORROWING.CALLNUMBER, BORROWING.OUTDATE, sysdate-BORROWING.DUEDATE, HASSUBJECT.SUBJECT FROM BORROWING,HASSUBJECT WHERE BORROWING.CALLNUMBER = HASSUBJECT.CALLNUMBER AND BORROWING.INDATE IS NULL AND HASSUBJECT.subject = '"
                                + subject + "' ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

                System.out.println( "subject = " + subject );

                while( rs.next() )
                {
                    if( (int) ( rs.getFloat( 3 ) ) > 0 )
                    {
                        
                        intList.add( new Integer( numRows ) ) ;
                        numRows++;
                    }
                }

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            int[] resultArray = new int[ intList.size() ];
            for( int i = 0; i < resultArray.length; i++ )
            {

                resultArray[ i ] = intList.get( i ).intValue();
            }

            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public String[][] generateReport1( String subject )
    {
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int numRows = 0;

        try
        {
            stmt = con.createStatement();

            if( subject.equals( "" ) )
            {
                rs = stmt
                        .executeQuery( "SELECT CALLNUMBER,OUTDATE,DUEDATE FROM BORROWING WHERE INDATE IS NULL ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

                System.out.println( "subject = " + subject );

                while( rs.next() )
                {
                    numRows++;
                }

                rs = stmt
                        .executeQuery( "SELECT CALLNUMBER,OUTDATE,DUEDATE FROM BORROWING WHERE INDATE IS NULL ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

            }
            else
            {
                rs = stmt
                        .executeQuery( "SELECT BORROWING.CALLNUMBER, BORROWING.OUTDATE,BORROWING.DUEDATE, HASSUBJECT.SUBJECT FROM BORROWING,HASSUBJECT WHERE BORROWING.CALLNUMBER = HASSUBJECT.CALLNUMBER AND BORROWING.INDATE IS NULL AND HASSUBJECT.subject = '"
                                + subject + "' ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

                System.out.println( "subject = " + subject );

                while( rs.next() )
                {
                    numRows++;
                }

                rs = stmt
                        .executeQuery( "SELECT BORROWING.CALLNUMBER, BORROWING.OUTDATE,BORROWING.DUEDATE, HASSUBJECT.SUBJECT FROM BORROWING,HASSUBJECT WHERE BORROWING.CALLNUMBER = HASSUBJECT.CALLNUMBER AND BORROWING.INDATE IS NULL AND HASSUBJECT.subject = '"
                                + subject + "' ORDER BY callNumber" );
                // get info on ResultSet
                rsmd = rs.getMetaData();

            }

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            String[][] resultArray = new String[ numRows ][ numCols ];

            int j = 0;
            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /*
     * input: tableName - name of the table params - comma seperated string
     * containing the parameters
     * 
     * Example: showAllEntriesInTable("HasAuthor",
     * "01234567890123, Harry Potter"); returns: double array => (")
     */

    /*
     * Function: showEntryNames() input: databaseName - name of the table
     * returns: double array
     */
    public String[] generateMostPopularBooksReportColumnNames( int year )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT callNumber, Count(DISTINCT borid) AS bcount FROM Borrowing WHERE to_char(outDate, 'YYYY') = '"
                            + year
                            + "' GROUP BY callNumber ORDER BY bcount desc" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /**
     * generates the most popular books in a given year
     * 
     * @pre n <= the total number of books in the library
     * 
     * @param year
     * @param n
     * @return a 2-d array containing the most popular books
     * 
     * 
     */
    public String[][] generateMostPopularBooksReport( int year, int n )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT callNumber, Count(DISTINCT borid) AS bcount FROM Borrowing WHERE to_char(outDate, 'YYYY') = '"
                            + year
                            + "' GROUP BY callNumber ORDER BY bcount desc" );
            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            String[][] resultArray = new String[ n ][ numCols ];

            int j = 0;
            while( rs.next() && j < n )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public String[] generateOverdueItemsColumnNames()
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT callNumber,copyNo FROM Borrowing WHERE indate is null AND sysdate > dueDate " );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /**
     * generates the overDue Items
     * 
     * 
     * 
     * 
     */
    public String[][] generateOverdueItems()
    {
        Statement stmt;
        ResultSet rs;
        String callNumber = "";
        int borid = -1;
        int dueDays = -1;
        int copyNo = -1;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT callNumber,copyNo FROM Borrowing WHERE indate is null AND sysdate > dueDate " );
            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println( "isNull?: " + rs.next() );

            while( rs.next() )
            {
                callNumber = rs.getString( 1 );
                copyNo = rs.getInt( 2 );

                borid = validBorrowing( callNumber, copyNo );
                dueDays = checkOverDueDaysWithoutinDate( borid );

                System.out.println( "" + dueDays );
                if( dueDays > 15 )
                {
                    insertFine( borid, 30 );
                }
            }

            rs.close();
            rs = stmt
                    .executeQuery( "SELECT callNumber,copyNo FROM Borrowing WHERE indate is null AND sysdate > dueDate " );

            // get info on ResultSet
            rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            String[] nameArray = new String[ numCols ];
            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            int j = 0;

            rs.close();
            rs = stmt
                    .executeQuery( "SELECT callNumber,copyNo FROM Borrowing WHERE indate is null AND sysdate > dueDate " );

            // get info on ResultSet
            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /*
     * input: tableName - name of the table params - comma seperated string
     * containing the parameters
     * 
     * Example: showAllEntriesInTable("HasAuthor",
     * "01234567890123, Harry Potter"); returns: double array => (")
     */

    /*
     * Function: showEntryNames() input: databaseName - name of the table
     * returns: double array
     */
    public String[] searchBooksByColumnNames( int searchBy, String searchField )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            switch( searchBy )
            {
            case 1:
                rs = stmt
                        .executeQuery( "SELECT callNumber, title from Book where title = '"
                                + searchField + "'" );
                break;
            case 2:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, HasSubject.subject from Book, HasSubject where Book.callNumber = HasSubject.callNumber AND HasSubject.subject = '"
                                + searchField + "'" );
                break;
            case 3:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, Book.mainAuthor, HasAuthor.name from Book, HasAuthor where Book.callNumber = HasAuthor.callNumber AND (Book.mainAuthor ='"
                                + searchField
                                + "' OR HasAuthor.name = '"
                                + searchField + "')" );
                break;
            default:
                System.out.println( "Something doesn't work" );
                rs = null;
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /**
     * Searches and returns the books based on Title (1), Author(3), Subject (2)
     * 
     * 
     * @param SearchBy
     * @param SearchField
     * @return
     */

    public String[][] searchBooksBy( int searchBy, String searchField )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            switch( searchBy )
            {
            case 1:
                rs = stmt
                        .executeQuery( "SELECT callNumber, title from Book where title = '"
                                + searchField + "'" );
                break;
            case 2:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, HasSubject.subject from Book, HasSubject where Book.callNumber = HasSubject.callNumber AND HasSubject.subject = '"
                                + searchField + "'" );
                break;
            case 3:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, Book.mainAuthor, HasAuthor.name from Book, HasAuthor where Book.callNumber = HasAuthor.callNumber AND (Book.mainAuthor ='"
                                + searchField
                                + "' OR HasAuthor.name = '"
                                + searchField + "')" );
                break;
            default:
                System.out.println( "Something doesn't work" );
                rs = null;
            }
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            int j = 0;

            rs.close();

            switch( searchBy )
            {
            case 1:
                rs = stmt
                        .executeQuery( "SELECT callNumber, title from Book where title = '"
                                + searchField + "'" );
                break;
            case 2:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, HasSubject.subject from Book, HasSubject where Book.callNumber = HasSubject.callNumber AND HasSubject.subject = '"
                                + searchField + "'" );
                break;
            case 3:
                rs = stmt
                        .executeQuery( "SELECT Book.callNumber, Book.title, Book.mainAuthor, HasAuthor.name from Book, HasAuthor where Book.callNumber = HasAuthor.callNumber AND (Book.mainAuthor ='"
                                + searchField
                                + "' OR HasAuthor.name = '"
                                + searchField + "')" );
                break;
            default:
                System.out.println( "Something doesn't work" );

            }

            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /*
     * input: tableName - name of the table params - comma seperated string
     * containing the parameters
     * 
     * Example: showAllEntriesInTable("HasAuthor",
     * "01234567890123, Harry Potter"); returns: double array => (")
     */

    /*
     * Function: showEntryNames() input: databaseName - name of the table
     * returns: double array
     */
    public String[] showEntryNames( String tableName )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM " + tableName );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    /*
     * Function: showEntriesInTable() input: databaseName - name of the table
     * returns: double array
     */
    public String[][] showEntriesInTable( String tableName )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM " + tableName );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            String[] nameArray = new String[ numCols ];
            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            int j = 0;

            rs.close();
            rs = stmt.executeQuery( "SELECT * FROM " + tableName );
            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public void deleteHasAuthor()
    {
        String callNumber;
        String name;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM HasAuthor WHERE callNumber = ? AND name = ?" );

            System.out.print( "\nCall number of HasAuthor to delete: " );
            callNumber = in.readLine();
            ps.setString( 1, callNumber );

            System.out.print( "\nname of HasAuthor to delete: " );
            name = in.readLine();
            ps.setString( 2, name );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nHasAuthor with call number:"
                        + callNumber + "and name" + name + " does not exist!" );
            }
            else
            {
                System.out
                        .println( "\nHasAuthor with call number:" + callNumber
                                + "and name" + name + " has been deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void showHasAuthor()
    {
        String callNumber;
        String name;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM HasAuthor" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println( " " );

            // display column names;
            for( int i = 0; i < numCols; i++ )
            {
                // get column name and print it

                System.out.printf( "%-15s", rsmd.getColumnName( i + 1 ) );
            }

            System.out.println( " " );

            while( rs.next() )
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                callNumber = rs.getString( "callNumber" );
                System.out.printf( "%-10.10s", callNumber );

                name = rs.getString( "name" );
                System.out.printf( "%-20.20s", name );

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
        }
    }

    public void insertHasAuthor( String callNumber, String author )
    {
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "INSERT INTO HasAuthor VALUES (?,?)" );

            System.out.print( "\ncall number: " + callNumber );
            ps.setString( 1, callNumber );

            System.out.print( "\nauthor: " + author );
            ps.setString( 2, author );

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void insertHasSubject( String callNumber, String subject )
    {
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "INSERT INTO HasSubject VALUES (?,?)" );

            System.out.print( "\ncall number: " + callNumber );
            ps.setString( 1, callNumber );

            System.out.print( "\nsubject: " + subject );
            ps.setString( 2, subject );

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void deleteHasSubject()
    {
        String callNumber;
        String subject;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM HasSubject WHERE callNumber = ? AND subject = ?" );

            System.out.print( "\nCall number of HasSubject to delete: " );
            callNumber = in.readLine();
            ps.setString( 1, callNumber );

            System.out.print( "\nsubject of HasSubject to delete: " );
            subject = in.readLine();
            ps.setString( 2, subject );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nHasSubject with call number:"
                        + callNumber + "and subject" + subject
                        + " does not exist!" );
            }
            else
            {
                System.out.println( "\nHasSubject with call number:"
                        + callNumber + "and subject" + subject
                        + " has been deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void showHasSubject()
    {
        String callNumber;
        String subject;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM HasSubject" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println( " " );

            // display column names;
            for( int i = 0; i < numCols; i++ )
            {
                // get column name and print it

                System.out.printf( "%-15s", rsmd.getColumnName( i + 1 ) );
            }

            System.out.println( " " );

            while( rs.next() )
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                callNumber = rs.getString( "callNumber" );
                System.out.printf( "%-10.10s", callNumber );

                subject = rs.getString( "subject" );
                System.out.printf( "%-20.20s", subject );

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
        }
    }

    /*
     * inserts a Borrower
     */

    public void deleteBookCopy()
    {
        String callNumber;
        int copyNo;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM BookCopy WHERE callNumber = ? AND copyNo = ?" );

            System.out.print( "\nCall number of BookCopy to delete: " );
            callNumber = in.readLine();
            ps.setString( 1, callNumber );

            System.out.print( "\ncopyNo of BookCopy to delete: " );
            copyNo = Integer.parseInt( in.readLine() );
            ps.setInt( 2, copyNo );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nBookCopy with call number:" + callNumber
                        + "and copyNo" + copyNo + " does not exist!" );
            }
            else
            {
                System.out.println( "\nBookCopy with call number:" + callNumber
                        + "and copyNo" + copyNo + " has been deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public void showBookCopy()
    {
        String callNumber;
        String copyNo;
        String status;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM BookCopy" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println( " " );

            // display column names;
            for( int i = 0; i < numCols; i++ )
            {
                // get column name and print it

                System.out.printf( "%-15s", rsmd.getColumnName( i + 1 ) );
            }

            System.out.println( " " );

            while( rs.next() )
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                callNumber = rs.getString( "callNumber" );
                System.out.printf( "%-10.10s", callNumber );

                copyNo = rs.getString( "copyNo" );
                System.out.printf( "%-20.20s", copyNo );

                status = rs.getString( "status" );
                System.out.printf( "%-20.20s", status );

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
        }
    }

    // ********************** BORROWER TYPE

    public void insertBorrowerType()
    {
        int type;
        int bookTimeLimit;
        int bookDailyFine;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "INSERT INTO BorrowerType VALUES (?,?,?)" );

            System.out.print( "\nBorrowerType type: " );
            type = Integer.parseInt( in.readLine() );
            ps.setInt( 1, type );

            System.out.print( "\nBorrowerType bookTimeLimit: " );
            bookTimeLimit = Integer.parseInt( in.readLine() );
            ps.setInt( 2, bookTimeLimit );

            System.out.print( "\nBorrowerType bookDailyFine: " );
            bookDailyFine = Integer.parseInt( in.readLine() );
            ps.setInt( 3, bookDailyFine );

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * deletes a BorrowerType
     */
    public void deleteBorrowerType()
    {
        int type;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM BorrowerType WHERE type = ?" );

            System.out.print( "\nBorrowerType type: " );
            type = Integer.parseInt( in.readLine() );
            ps.setInt( 1, type );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nBorrowerType " + type
                        + " does not exist!" );
            }
            else
            {
                System.out.println( "\nBorrowerType" + type
                        + "has been successfully deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * updates the name of a BorrowerType
     */
    public void updateBorrowerType()
    {
        int type;
        int bookDailyFine;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "UPDATE branch SET bookDailyFine = ? WHERE type = ?" );

            System.out.print( "\nBorrowerType type: " );
            type = Integer.parseInt( in.readLine() );
            ps.setInt( 2, type );

            System.out.print( "\nNew BorrowerType bookDailyFine: " );
            bookDailyFine = Integer.parseInt( in.readLine() );
            ps.setInt( 1, bookDailyFine );

            int rowCount = ps.executeUpdate();
            if( rowCount == 0 )
            {
                System.out.println( "\nBorrowerType " + type
                        + " does not exist!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /**
     * This function check if a callNumber is valid
     * 
     */
    public boolean vaildBook( String callNumber )
    {

        boolean valid = false;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery( "SELECT * FROM Book WHERE callNumber = '"
                            + callNumber + "'" );

            if( rs.next() )
            {
                valid = true;
            }
            else
            {
                valid = false;
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
        return valid;
    }

    /**
     * This function check the Book Status
     * 
     */
    public boolean checkBookStatus( String callNumber, int copyNo, int status )
    {

        boolean valid = false;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery( "SELECT * FROM BookCopy WHERE callNumber = '"
                            + callNumber
                            + "'"
                            + "and copyNo = '"
                            + copyNo
                            + "' and status = '" + status + "'" );
            if( rs.equals( null ) )
            {
                valid = true;
            }
            else
            {
                valid = false;
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
        return valid;
    }

    public void insertHold( int bid, String callNumber )
    {

        PreparedStatement ps;

        try
        {

            ps = con.prepareStatement( "INSERT INTO HoldRequest VALUES(hid_counter.nextval,?,?,sysdate)" );
            ps.setInt( 1, bid );
            ps.setString( 2, callNumber );

            ps.executeUpdate();

            con.commit();
            ps.close();

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * deletes a Hold Request
     */
    public void deleteHoldRequest( int bid )
    {

        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM HoldRequest WHERE bid = ?" );

            ps.setInt( 1, bid );

            ps.executeUpdate();

            con.commit();

            ps.close();
        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public String[] checkAccountBorrowingColumnNames( int bid )
    {

        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * from Borrowing where Bid =" + bid
                    + " AND inDate is NULL" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }

    }

    public String[][] checkAccountBorrowingData( int bid )
    {
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * from Borrowing where Bid =" + bid
                    + " AND inDate is NULL" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            int j = 0;

            rs.close();
            rs = stmt.executeQuery( "SELECT * from Borrowing where Bid =" + bid
                    + " AND inDate is NULL" );
            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public String[] checkAccountFinesColumnNames( int bid )
    {

        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT Fine.borid, Fine.amount from Borrowing,Fine where Borrowing.bid ="
                            + bid
                            + " AND Borrowing.borid = Fine.borid and Fine.paidDate is null" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }

    }

    public String[][] checkAccountFinesData( int bid )
    {

        Statement stmt;
        ResultSet rs;
        int borid[];

        try
        {
            stmt = con.createStatement();

            rs = stmt
                    .executeQuery( "SELECT Fine.borid, Fine.amount from Borrowing,Fine where Borrowing.bid ="
                            + bid
                            + " AND Borrowing.borid = Fine.borid and Fine.paidDate is null" );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            int j = 0;

            rs.close();
            rs = stmt
                    .executeQuery( "SELECT Fine.borid, Fine.amount from Borrowing,Fine where Borrowing.bid ="
                            + bid
                            + " AND Borrowing.borid = Fine.borid and Fine.paidDate is null" );

            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    public String[] checkAccountHoldsColumnNames( int bid )
    {

        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * from HoldRequest where Bid ="
                    + bid );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            String[] nameArray = new String[ numCols ];

            // Grab the column names
            for( int i = 0; i < numCols; i++ )
            {
                nameArray[ i ] = rsmd.getColumnName( i + 1 );
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
            return nameArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }

    }

    public String[][] checkAccountHoldsData( int bid )
    {

        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery( "SELECT * from HoldRequest where Bid ="
                    + bid );

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();
            int columnType;

            int numRows = 0;
            while( rs.next() )
            {
                numRows++;
            }

            String[][] resultArray = new String[ numRows ][ numCols ];

            int j = 0;

            rs.close();
            rs = stmt.executeQuery( "SELECT * from HoldRequest where Bid ="
                    + bid );
            rsmd = rs.getMetaData();

            while( rs.next() )
            {
                for( int i = 0; i < numCols; i++ )
                {
                    // Grab the column values (by type)
                    columnType = rsmd.getColumnType( i + 1 );

                    // How do we know the values with the types?
                    // http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.sql.Types.DOUBLE
                    switch( columnType )
                    {
                    case ( 1 ): // 1 is char
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 2 ): // 2 is numeric
                    case ( 4 ): // 4 is int
                        resultArray[ j ][ i ] = "" + rs.getInt( i + 1 );
                        break;
                    case ( 12 ): // 12 is varchar
                        resultArray[ j ][ i ] = "" + rs.getString( i + 1 );
                        break;
                    case ( 8 ): // 8 is double
                        resultArray[ j ][ i ] = "" + rs.getDouble( i + 1 );
                        break;
                    case ( 91 ): // 91 is date
                        resultArray[ j ][ i ] = "" + rs.getDate( i + 1 );
                        break;
                    default:
                        System.out
                                .println( "Yo, Eddy's code is messed up here. Code="
                                        + columnType );
                        resultArray[ j ][ i ] = "" + rs.getObject( i + 1 );
                    }
                }

                j++;
            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();

            System.out.println( "j is " + j );
            return resultArray;
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            return null;
        }
    }

    // sets the status of the book to in or out (through updateStatus)
    // STATUS IS 0 for out
    // STATUS IS 1 for in
    // STATUS IS 2 for on-hold
    public void updateBookCopy( String mycallNumber, int mycopyNo,
            int updateStatus )
    {
        String callNumber;
        int copyNo;
        int status;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "UPDATE BookCopy SET status = ? WHERE callNumber = ? AND copyNo = ?" );

            // System.out.print("\nBorrowerType type: ");

            if( updateStatus == 1 || updateStatus == 0 || updateStatus == 2 )
            {
                callNumber = mycallNumber;
                ps.setString( 2, callNumber );

                // System.out.print("\nNew BorrowerType bookDailyFine: ");
                copyNo = mycopyNo;
                ps.setInt( 3, mycopyNo );

                status = updateStatus;
                ps.setInt( 1, status );

                ps.executeUpdate();

                con.commit();

                ps.close();
            }

            else
            {
                System.out
                        .println( "Status is not 0 (out) or 1(in) or 2(on-hold)" );
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * displays the details of the items that have been borrowed by a borrower
     */
    public String[] showBorrowingItemdetails( String callNumber, int copyNo )
    {
        Statement stmt;
        String[] itemDetails = new String[ 4 ];
        try
        {
            stmt = con.createStatement();

            ResultSet rs = stmt
                    .executeQuery( "SELECT Borrowing.callNumber, Book.title, Borrowing.dueDate, BookCopy.copyNo FROM Borrowing, Book, BookCopy WHERE Borrowing.callNumber = Book.callNumber AND BookCopy.callNumber= Book.callNumber AND borrowing.callNumber = '"
                            + callNumber + "' AND BookCopy.copyNo = " + copyNo );

            while( rs.next() )
            {
                itemDetails[ 0 ] = rs.getString( 1 );
                System.out.println( "callNumber: " + itemDetails[ 0 ] );

                itemDetails[ 1 ] = rs.getString( 2 );
                System.out.println( "title: " + itemDetails[ 1 ] );

                itemDetails[ 2 ] = rs.getString( 3 );
                System.out.println( "inDate: " + itemDetails[ 2 ] );

                itemDetails[ 3 ] = "" + rs.getInt( 4 );
                System.out.println( "copyNo: " + itemDetails[ 3 ] );
            }

            // commit work
            con.commit();

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
            }
        }

        return itemDetails;
    }

    // There shouldn't be an update HoldRequest should there??

    // ***************** Borrowing *****************

    public void insertBorrowing( int bid, String callNumber, int copyNo )
    {
        PreparedStatement ps;
        Statement stmt;
        try
        {
            stmt = con.createStatement();

            ResultSet rs = stmt
                    .executeQuery( "SELECT type FROM Borrower WHERE bid = '"
                            + bid + "'" );

            int type = -1;

            while( rs.next() )
            {
                type = rs.getInt( 1 );
                System.out.println( "type: " + type );
            }

            rs = stmt
                    .executeQuery( "SELECT bookTimeLimit FROM BorrowerType WHERE type = '"
                            + type + "'" );

            int bookTimeLimit = -1;

            while( rs.next() )
            {
                bookTimeLimit = rs.getInt( 1 );
                System.out.println( "bookTime Limit: " + bookTimeLimit );
            }

            ps = con.prepareStatement( "INSERT INTO Borrowing VALUES (borid_counter.nextval,?,?,?,sysdate,sysdate + "
                    + bookTimeLimit + ",?)" );

            ps.setInt( 1, bid );
            System.out.println( "bid: " + bid );
            ps.setString( 2, callNumber );
            System.out.println( "callNumber: " + callNumber );
            ps.setInt( 3, copyNo );
            System.out.println( "copyNo: " + copyNo );
            ps.setString( 4, null );
            // set inDate to null since when you first insert a borrowing... you
            // don't know the indate

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /**
     * checks whether the borrowing event associated with the given callNumber
     * and copyNo exists and has not been returned. If it doesn't exist then it
     * returns -1 as borid otherwise it returns the borid
     * 
     * @param callNumber
     * @param copyNo
     * @return
     */
    public int validBorrowing( String callNumber, int copyNo )
    {
        int borid = -1;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery( "SELECT borid FROM Borrowing WHERE callNumber = '"
                            + callNumber
                            + "'"
                            + "AND copyNo = "
                            + copyNo
                            + "AND inDate IS NULL" );

            while( rs.next() )
            {
                borid = rs.getInt( 1 );
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
        return borid;
    }

    public boolean holdExists( String callNumber, int copyNo )
    {

    	boolean holdExist = false;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery( "SELECT * FROM holdRequest WHERE callNumber = '"
                            + callNumber + "'" );

            System.out.println( "callNumber: " + callNumber );
            if( rs.next() )
            {
                System.out.println( "Hold exists" );
                updateBookCopy( callNumber, copyNo, 2 );
                return holdExist = true;
            }
            else
            {
                System.out.println( "Hold deoesn't exist" );
                updateBookCopy( callNumber, copyNo, 1 );
                return holdExist = false;
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
        return holdExist;

    }

    /**
     * checks whether there a fine is associated with a particual borrowing
     * event already or not. If it exists, method returns true, otherwise it
     * returns false
     * 
     * @param borid
     * @return
     */
    public boolean fineExists( int borid )
    {
        boolean fineExists = false;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery( "SELECT * FROM Fine WHERE borid = " + borid
                            + "AND paidDate is null" );

            if( rs.next() )
            {
                fineExists = true;
            }

        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

        return fineExists;

    }

    public void payFine( int borid )
    {

        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "UPDATE Fine SET paidDate = sysdate WHERE borid = ?" );

            System.out.print( "\nFine ID: " );
            ps.setInt( 1, borid );

            int rowCount = ps.executeUpdate();
            if( rowCount == 0 )
            {
                System.out.println( "\nfine with borid: " + borid
                        + " does not exist!" );
            }

            con.commit();

            ps.close();
        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

    }

    /*
     * deletes a Borrowing
     */
    public void deleteBorrowing( int borid )
    {
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM Borrower WHERE bid = ?" );

            ps.setInt( 1, borid );

            int rowCount = ps.executeUpdate();

            con.commit();

            ps.close();

            if( rowCount == 0 )
            {
                System.out.println( "\nBorrower " + borid + " does not exist!" );
            }
            else
            {
                System.out.println( "\nBorrower" + borid
                        + "has been successfully deleted!" );
            }
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * deletes a Borrowing
     */
    public void deleteBorrowing()
    {
        int borid;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM Borrower WHERE bid = ?" );

            System.out.print( "\nBorrowering ID: " );
            borid = Integer.parseInt( in.readLine() );
            ps.setInt( 1, borid );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nBorrower " + borid + " does not exist!" );
            }
            else
            {
                System.out.println( "\nBorrower" + borid
                        + "has been successfully deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    // Why would Borrowing need an update? -- for the inDate!
    public void updateBorrowing( int borid )
    {

        Statement stmt;

        try
        {
            stmt = con.createStatement();
            stmt.executeQuery( "UPDATE Borrowing SET inDate = sysdate WHERE borid = "
                    + borid );

            con.commit();

        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public int checkOverDueDays( int borid )
    {
        Statement stmt;
        int dueDays = -1;

        try
        {

            stmt = con.createStatement();

            ResultSet rs = stmt
                    .executeQuery( "SELECT inDate-dueDate FROM Borrowing WHERE borid = "
                            + borid );

            while( rs.next() )
            {

                dueDays = (int) rs.getFloat( 1 );
            }

            con.commit();
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

        return dueDays;
    }

    public int checkOverDueDaysWithoutinDate( int borid )
    {
        Statement stmt;
        int dueDays = -1;

        try
        {

            stmt = con.createStatement();

            ResultSet rs = stmt
                    .executeQuery( "SELECT sysdate-dueDate FROM Borrowing WHERE borid = "
                            + borid );

            while( rs.next() )
            {

                dueDays = (int) rs.getFloat( 1 );
            }

            con.commit();
            stmt.close();
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }

        return dueDays;
    }

    /**
     * This method creates a Fine for a borrowing event Enter an amount 0 if
     * amount is not 30$ (if amount needs to be calculated in the Fine function)
     * 
     * @param borid
     * @param amount -
     */
    public void insertFine( int borid, int amount )
    {

        PreparedStatement ps;
        Statement stmt;
        int numOverDueDays = 0;
        int type = -1;
        int bookDailyFine = 0;
        int fineAmount = 0;
        try
        {

            stmt = con.createStatement();

            ResultSet rs = stmt
                    .executeQuery( "SELECT Borrowing.inDate-Borrowing.dueDate, Borrower.type, BorrowerType.bookDailyFine FROM Borrower, BorrowerType, Borrowing "
                            + "WHERE Borrowing.bid = Borrower.bid AND Borrower.type= Borrowertype.type AND Borrowing.borid = "
                            + borid );

            while( rs.next() )
            {

                numOverDueDays = (int) rs.getFloat( 1 );
                System.out.println( "overDueDate: " + numOverDueDays );

                type = rs.getInt( 2 );
                System.out.println( "type: " + type );

                bookDailyFine = rs.getInt( 3 );
                System.out.println( "bookDailyFine: " + bookDailyFine );

            }

            if( amount == 0 )
            {
                fineAmount = numOverDueDays * bookDailyFine;

                ps = con.prepareStatement( "INSERT INTO Fine VALUES (fid_counter.nextval,?,?,sysdate, ?, "
                        + borid + ")" );

                ps.setInt( 1, type );
                ps.setInt( 2, fineAmount );
                ps.setString( 3, null );

                ps.executeUpdate();

            }
            else
            {

                ps = con.prepareStatement( "INSERT INTO Fine VALUES (fid_counter.nextval,?,?,sysdate, ?, "
                        + borid + ")" );

                ps.setInt( 1, type );
                ps.setInt( 2, amount );
                ps.setString( 3, null );

                ps.executeUpdate();

            }
            // commit work
            con.commit();

            ps.close();
        }

        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );
            try
            {
                // undo the insert
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * deletes a Borrower
     */
    public void deleteFine()
    {
        int fid;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "DELETE FROM Fine WHERE fid = ?" );

            System.out.print( "\nFine ID: " );
            fid = Integer.parseInt( in.readLine() );
            ps.setInt( 1, fid );

            int rowCount = ps.executeUpdate();

            if( rowCount == 0 )
            {
                System.out.println( "\nBorrower " + fid + " does not exist!" );
            }
            else
            {
                System.out.println( "\nBorrower" + fid
                        + "has been successfully deleted!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    /*
     * updates the name of a Borrower
     */
    public void updateFine()
    {
        int fid;
        String paidDate;
        PreparedStatement ps;

        try
        {
            ps = con.prepareStatement( "UPDATE branch SET paidDate = ? WHERE fid = ?" );

            System.out.print( "\nFine ID: " );
            fid = Integer.parseInt( in.readLine() );
            ps.setInt( 2, fid );

            System.out.print( "\nBorrowing in date: " );
            paidDate = in.readLine();
            ps.setString( 1, paidDate );

            int rowCount = ps.executeUpdate();
            if( rowCount == 0 )
            {
                System.out.println( "\nBorrower " + fid + " does not exist!" );
            }

            con.commit();

            ps.close();
        }
        catch( IOException e )
        {
            System.out.println( "IOException!" );
        }
        catch( SQLException ex )
        {
            System.out.println( "Message: " + ex.getMessage() );

            try
            {
                con.rollback();
            }
            catch( SQLException ex2 )
            {
                System.out.println( "Message: " + ex2.getMessage() );
                System.exit( -1 );
            }
        }
    }

    public static void main( String args[] )
    {

        Library b = new Library();

    }
}