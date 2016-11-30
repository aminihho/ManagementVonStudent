package Model.Insert;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Random;

/**
 * Created by annelie on 18.04.16.
 */
public class InsertStudentModel extends Observable {
    Connection _conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String fehlerString = "";

    public InsertStudentModel(){
        /**
         * Konstruktor:
         * Verbindung zur Datenbank herstellen
         */
        Connection_DB _connection = new Connection_DB();
        _conn=_connection.getConnection();

    }


    /**
     * Destruktor
     */
    protected void finalize(){

        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) { /* ignored */}
        }

        if (_conn != null) {
            try {
                _conn.close();
                System.out.println("------------- PostgreSQL JDBC Connection Closed --------------");
            } catch (SQLException e) { /* ignored */}
        }

    }

    public void closeConnection(){

        if (_conn != null) {

            try {
                _conn.close();
                System.out.println("------------- PostgreSQL JDBC Connection Closed --------------");
            }
            catch (Exception ex) { /*?*/ }
        }
    }



    public int insertValues(String nachnameStr, String vornameStr, String gebDatStr, String fakuStr, String telStr, String emailStr, String urzTUCStr, String urzDB){

        try{
            // Query
            String query ="INSERT INTO student VALUES(?, ?, ?, ?, ?, ?, ?, '{}',? )";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, nachnameStr);
            pst.setString(2, vornameStr);
            pst.setString(3, gebDatStr);
            pst.setString(4, fakuStr);
            pst.setString(5, telStr);
            pst.setString(6, emailStr);
            pst.setString(7, urzDB);
            pst.setString(8, urzTUCStr);

            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            System.out.println("Insert succeed");
            pst.close();

            return 1;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return -1;
        }

    }

    public boolean insertBemerkung(String urz, String bemerkung){

        try{
            // Query
            String query ="INSERT INTO student_bem (urz, bemerkung) VALUES ('"+urz+"', '"+bemerkung+"')";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            System.out.println("Insert succeed");
            pst.close();

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return false;
        }

    }

    public String getErrorMessage(){return fehlerString; }
}
