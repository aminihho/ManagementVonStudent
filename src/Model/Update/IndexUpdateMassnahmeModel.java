package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 20.08.16.
 */
public class IndexUpdateMassnahmeModel extends Observable {

    Connection conn = null;
    PreparedStatement pst = null;
    String fehlerString = "";
    ResultSet result = null;

    /**
     * Konstruktor der Klasse
     */
    public IndexUpdateMassnahmeModel(){
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

    /***
     * Sucht alle existierende Aktivitäten, gibt sie als ArrayList zurück
     */
    public ArrayList<ArrayList<String>> returnAllMassnahmeArrayList() {

        ArrayList<ArrayList<String>> listeMassnahme=new ArrayList<ArrayList<String>>();

        // Query
        String query ="SELECT * FROM massnahme ORDER BY massnahme_name";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs=pst.executeQuery();

            ArrayList<String> zeile=new ArrayList<String>();

            while(rs.next()){

                String status=rs.getString("massnahme_name");

                // Speichern urz name und Vorname in einer Arraylist
                zeile.add( status);

                listeMassnahme.add(zeile);
                zeile=new ArrayList<String>();
            }
            /// Statement closed und ResultStatement closed
            rs.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }
        return listeMassnahme;

    }

    public boolean deleteMassnahme(String nameMassnahme){

        try{
            // Query
            String query ="delete from massnahme where massnahme_name = '"+nameMassnahme+"';";

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

    public String returnFehlerString(){return  fehlerString; }
}
