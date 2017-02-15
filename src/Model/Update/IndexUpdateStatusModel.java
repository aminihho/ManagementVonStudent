package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 19.08.16.
 */
public class IndexUpdateStatusModel extends Observable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String fehlerString = "";

    /**
     * Konstruktor der Klasse
     */
    public IndexUpdateStatusModel(){
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
    public ArrayList<ArrayList<String>> returnAllStatusArrayList() {

        ArrayList<ArrayList<String>> listeAktivitat=new ArrayList<ArrayList<String>>();

        // Query
        String query ="SELECT * FROM status ORDER BY status_typ";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            ArrayList<String> zeile=new ArrayList<String>();

            while(result.next()){

                String status=result.getString("status_typ");

                // Speichern urz name und Vorname in einer Arraylist
                zeile.add( status);

                listeAktivitat.add(zeile);
                zeile=new ArrayList<String>();
            }
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }
        return listeAktivitat;

    }

    public boolean deleteStatus(String nameStatus){

        try{
            // Query
            String query ="delete from status where status_typ = '"+nameStatus+"';";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
           

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
            /// Statement closed und ResultStatement closed
            pst.close();

            return true;
        }

        //TODO: Dass eventuell Fehler in einem JDialog angezeigt werden
        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("fehler:"+fehlerString);

            return false;
        }

    }

    public String getFehlerString(){return fehlerString; }
}
