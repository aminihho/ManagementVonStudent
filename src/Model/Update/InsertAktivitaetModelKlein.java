package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 23.08.16.
 */
public class InsertAktivitaetModelKlein extends Observable {


    Connection conn = null;
    PreparedStatement pst = null;
    String fehlerString = "";
    ResultSet result = null;

    /**
     * Konstruktor der Klasse
     */
    public InsertAktivitaetModelKlein(){
        // Verbindung zum Datenbank hestellen.
        Connection_DB _connection = new Connection_DB();
        conn =_connection.getConnection();
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

    public  boolean insertAktivitaet(String nameAkt, String beschreibungAkt, String zeitraum){
        try{
            // Query
            String query ="INSERT INTO aktivitaet (aktivitaet_name, zeitraum, beschreibung) VALUES('"+nameAkt+"', '"+zeitraum+"', '"+beschreibungAkt+"');";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            pst.close();

            return true;

        }

        catch(SQLException exception ) {
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: " + fehlerString);

            return false;
        }
    }

    public  boolean insertMassnahme( String nameAktivitaet, String massnahme){
        try{
            // Query
            String query = "INSERT INTO m_a(aktivitaet_name, massnahme_name) VALUES ('"+nameAktivitaet+"', '"+massnahme+"');";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            /// Statement closed und ResultStatement closed
            pst.close();

            return true;

        }

        catch(SQLException exception ) {
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: " + fehlerString);

            return false;
        }
    }

    public ArrayList<String> returnMassnahmeName(){

        ArrayList<String> dataMassnahme = new ArrayList<String>();

        try {
            // Query 1
            String query ="SELECT * FROM massnahme ORDER BY massnahme_name;";
            // PrepareStatement wird  erzeugt.
            pst =conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();


            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while(result.next()){
                String massnahme = result.getString("massnahme_name");
                dataMassnahme.add(massnahme);

            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataMassnahme;
    }

    public  String getFehlerString(){
        return fehlerString;
    }
}
