import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connect to SQL Server, execute a SELECT query, print the results.
 *
 */
public class SelectFromSQLServer
{
    //The SQL Server JDBC Driver is in
    //C:\Program Files\Microsoft JDBC Driver 6.0 for SQL Server\sqljdbc_6.0\enu\auth\x64
    //private static final String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

    //The JDBC connection URL which allows for Windows authentication is defined below.
 //   private static final String jdbcURL = "jdbc:sqlserver://localhost;instance=WIN-4NGU94CB4PG;user=tom;password=house@$2012;databasename=examen;integratedSecurity=true;";

  //    private static final String jdbcURL = "jdbc:sqlserver://WIN-4NGU94CB4PG:1433;databasename=examen;user:tom;password:house@$2012";
    //To make Windows authentication work we have to set the path to sqljdbc_auth.dll at the command line

    /**
     * main method.
     *
     * @param  args  command line arguments
     */
    public static void conectar(String[] args)
    {


        System.out.println("Program started");
        try
        {
            Class.forName(jdbcDriver).newInstance();

            System.out.println("JDBC driver loaded");
        }
        catch (Exception err)
        {
            System.err.println("Error loading JDBC driver");
            err.printStackTrace(System.err);
            System.exit(0);
        }

        return ;
        /*
        Connection databaseConnection= null;
        try
        {
            //Connect to the database
            databaseConnection = DriverManager.getConnection(jdbcURL);
            System.out.println("Connected to the database");

            //declare the statement object
            Statement sqlStatement = databaseConnection.createStatement();

            //declare the result set
            ResultSet rs = null;

            //Build the query string, making sure to use column aliases
            String queryString="select ";
            queryString+="nombre as nombre, ";
            queryString+="distancia as distancia, ";
            queryString+="velocidad_angular as 'velocidad_angular' ";
            queryString+="from planeta";

            //print the query string to the screen
            System.out.println("\nQuery string:");
            System.out.println(queryString);

            //execute the query
            rs=sqlStatement.executeQuery(queryString);

            //print a header row
            System.out.println("\nNombre\t|\tDistancia\t|\tVelocidad angular");
            System.out.println("----------------------\t|\t----------------\t|\t------------");

            //loop through the result set and call method to print the result set row
            while (rs.next())
            {
                printResultSetRow(rs);
            }

            //close the result set
            rs.close();
            System.out.println("Closing database connection");

            //close the database connection
            databaseConnection.close();
        }
        catch (SQLException err)
        {
            System.err.println("Error connecting to the database");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Program finished");


        */
    }

    /**
     * Prints each row in the ResultSet object to the screen.
     *
     * @param  rs  the result set from the SELECT query
     * @throws SQLException SQLException thrown on error
     */
    public static void printResultSetRow(ResultSet rs) throws SQLException
    {
        //Use the column name alias as specified in the above query
        String Nombre= rs.getString("Nombre");
        String Distancia= rs.getString("Distancia");
        String Velocidad_Angular= rs.getString("Velocidad_Angular");
        System.out.println(Nombre+"\t|\t"+ Distancia + "\t|\t" + Velocidad_Angular);
    }

    public static void main() {
        String[] param= new String[1];
        param[0]="";
        conectar(param);
    }
}