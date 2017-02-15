package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

/**
 * Created by annelie on 20.08.16.
 */
public class UpdateStatusModel extends Observable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String fehlerString = "";

    private String statusName;

    /**
     * Konstruktor der Klasse
     */
    public UpdateStatusModel(){
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



    public void setStatus(String name)
    {
        this.statusName = name;
    }

    public String getStatus(){
        return this.statusName;
    }

    public boolean updateStatus(String neueStatus){

        String alteStatus = getStatus();

        try{
            // Query
            String query ="update status set status_typ = '"+ neueStatus +"' where status_typ = '" + alteStatus + "';";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);


            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            pst.close();

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: "+fehlerString);

            return false;
        }

    }

    public String getFehlerString(){
        return  fehlerString;
    }
}
