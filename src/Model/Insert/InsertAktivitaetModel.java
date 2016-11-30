package Model.Insert;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 23.04.16.
 */
public class InsertAktivitaetModel extends Observable{
    Connection _conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    ArrayList<String> dataMassnahme;
    String fehlerString = "";


    public InsertAktivitaetModel(){
        /**
         * Konstruktor:
         * Verbindung zur Datenbank herstellen
         */

        Connection_DB _connection = new Connection_DB();
        _conn =_connection.getConnection();

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

    public int insertValuesAktivtaet(String aktName, String zeitraum, String beschreibung){
        try{
            // Query
            String query ="insert into aktivitaet values (?, ? , ?); ";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, aktName);
            pst.setString(2, zeitraum);
            pst.setString(3, beschreibung);

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

    public int insertValuesAktivtaetundMassnahme(String aktName, String zeitraum, String beschreibung, String massName){
        try{
            // Query
            String query ="INSERT INTO aktivitaet VALUES (?, ? , ?); " +
                    "INSERT INTO massnahme VALUES (?); " +
                    "INSERT INTO m_a (aktivitaet_name, massnahme_name) VALUES (?, ?);";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, aktName);
            pst.setString(2, zeitraum);
            pst.setString(3, beschreibung);
            pst.setString(4, massName);
            pst.setString(5, aktName);
            pst.setString(6, massName);

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

    public int insertValuesAktivtaetundAddMassnahme(String aktName, String zeitraum, String beschreibung, String massName){
        try{
            // Query
            String query ="INSERT INTO aktivitaet VALUES (?, ? , ?); " +
                    "INSERT INTO m_a (aktivitaet_name, massnahme_name) VALUES (?, ?);";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, aktName);
            pst.setString(2, zeitraum);
            pst.setString(3, beschreibung);
            pst.setString(4, aktName);
            pst.setString(5, massName);

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

    public String getErrorMessage(){return fehlerString; }

    public ArrayList<String> returnMassnahmeName(){
        try {
            // Query 1
            String query = "select massnahme_name from massnahme ORDER BY massnahme_name";
            // PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            dataMassnahme = new ArrayList<String>();
            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while (result.next()) {
                //String name = result.getString("aktivitaet_name");
                String name = result.getString("massnahme_name");
                //dataAktivitaet.add(name);
                dataMassnahme.add(name);
            }

            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataMassnahme;
    }

    /**
     *
     * @param query the query
     * @param Columnname
     * @return
     */
    public String select(String query,String Columnname){
        String value = "";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

            while(result.next()){
                value = result.getString(Columnname);
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }


        return value;
    }

    public ArrayList<String> listeAktivitaet(){

        ArrayList<String> dataAktivitaet = new ArrayList<String>();

        try {
            // Query 1
            String query = "SELECT aktivitaet_name FROM aktivitaet;";

            // PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while (result.next()) {
                String name = result.getString("aktivitaet_name");
                dataAktivitaet.add(name);
            }

            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataAktivitaet;
    }

    public ArrayList<String> listeMassnahme(){

        ArrayList<String> dataMassnahme = new ArrayList<String>();

        try {
            // Query 1
            String query = "SELECT massnahme_name FROM massnahme;";

            // PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while (result.next()) {
                String value = result.getString("massnahme_name");
                dataMassnahme.add(value);
            }

            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataMassnahme;
    }




}
