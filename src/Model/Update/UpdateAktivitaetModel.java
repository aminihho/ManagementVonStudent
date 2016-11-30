package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 23.06.16.
 */
public class UpdateAktivitaetModel extends Observable {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;


    private String aktivitaename = "";
    private String zeitraum = "";
    private String beschreibung ="";
    public Object massnahme = "";
    String fehlerString = "";

    ArrayList<String> dataMassnahme;

    /**
     * Konstruktor der Klasse
     */
    public UpdateAktivitaetModel(){
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


    public ArrayList<String> returnMassnahmeName(){

        try {
            // Query 1
            String query ="SELECT * FROM massnahme ORDER BY massnahme_name;";
            // PrepareStatement wird  erzeugt.
            pst =conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            dataMassnahme = new ArrayList<String>();
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


    public boolean updateAktivitaet(String zeitraum, String beschreibung, String aktName, String aktNameAlt){

        try{
            // Query;
            String query = "UPDATE aktivitaet SET zeitraum = '"+zeitraum+"', beschreibung = '"+beschreibung+"', aktivitaet_name = '"+aktName+"' WHERE aktivitaet_name = '"+aktNameAlt+"';";

            //"UPDATE m_a SET massnahme_name = '"+massnahme+"' WHERE aktivitaet_name = '"+aktName+"'";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
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
            System.out.println(fehlerString);

            return false;
        }


    }

    public String returnMassnahmeVonAktivitaet(String aktName){

        String massnahme = "";

        try{
            // Query 1
            String query = "SELECT massnahme.massnahme_name " +
                    "FROM aktivitaet " +
                    "inner join m_a on (m_a.aktivitaet_name = aktivitaet.aktivitaet_name) " +
                    "inner join massnahme on (massnahme.massnahme_name = m_a.massnahme_name) " +
                    "WHERE aktivitaet.aktivitaet_name = '" + aktName + "';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(1, name);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()){
                massnahme = result.getString("massnahme_name");
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);
        }


        return massnahme;
    }

    public boolean updateMassnahme(String aktName, String massnahme){

        try{
            // Query;
            String query = "UPDATE m_a SET massnahme_name = '"+massnahme+"' WHERE aktivitaet_name = '"+aktName+"';";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
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
            System.out.println(fehlerString);

            return false;
        }


    }

    public  boolean insertMassnahme(String aktName, String massnahme){
        try{
            // Query
            String query ="insert into m_a (aktivitaet_name, massnahme_name) VALUES ('"+aktName+"', '"+massnahme+"');";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
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
            System.out.println("SQL Exception: " + fehlerString);

            return false;
        }

    }

    public String getFehlerString(){
        return  fehlerString;
    }

    public void setAktivitaename(String name)
    {
        this.aktivitaename = name;
    }

    public void setZeitramum(String name)
    {
        this.zeitraum = name;
    }

    public void setBeschreibung(String name)
    {
        this.beschreibung = name;
    }

    public void setMassnahme(Object name)
    {
        this.massnahme = name;
    }

    public String getZeitraum()
    {
        return this.zeitraum;
    }

    public String getAktivitaet()
    {
        return this.aktivitaename;
    }

    public String getBeschreibung()
    {
        return this.beschreibung;
    }

    public Object getMassnahme()
    {
        return this.massnahme;
    }
}
