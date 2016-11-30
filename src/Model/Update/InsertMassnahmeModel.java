package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

/**
 * Created by annelie on 22.08.16.
 */
public class InsertMassnahmeModel extends Observable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String fehlerString = "";

    /**
     * Konstruktor der Klasse
     */
    public InsertMassnahmeModel(){
        // Verbindung zum Datenbank hestellen.
        Connection_DB _connection = new Connection_DB();
        conn=_connection.getConnection();
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

        if (conn != null) {
            try {
                conn.close();
                System.out.println("------------- PostgreSQL JDBC Connection Closed --------------");
            } catch (SQLException e) { /* ignored */}
        }

    }

    public void closeConnection(){

        if (conn != null) {

            try {
                conn.close();
                System.out.println("------------- PostgreSQL JDBC Connection Closed --------------");
            }
            catch (Exception ex) { /*?*/ }
        }
    }

    public  boolean insertMassnahme(String nameMassnahme){
        try{
            // Query
            String query ="INSERT INTO massnahme VALUES(?)";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            pst.setString(1, nameMassnahme);

            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            pst.close();

            return true;

        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: " + fehlerString);

            return false;
        }

    }


    public  String getFehlerString(){
        return fehlerString;
    }

}
