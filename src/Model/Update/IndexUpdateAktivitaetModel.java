package Model.Update;

import Model.Sonstiges.Connection_DB;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;

import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 23.06.16.
 */
public class IndexUpdateAktivitaetModel extends Observable {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String fehlerString = "";
    ResultSet result = null;
    Statement stmt = null;


    /**
     * Konstruktor der Klasse
     */
    public IndexUpdateAktivitaetModel(){
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

        if (stmt != null) {
            try {
                stmt.close();
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


    public String returnMassnahme(String aktName){

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
            System.out.println("Fehler String: "+fehlerString);
        }


        return massnahme;
    }

    public ArrayList<ArrayList<String>> ListeAllerAktivitatenMitMassnahme() {

        ArrayList<ArrayList<String>> listeAktivitat = new ArrayList<ArrayList<String>>();
        String query ="select * from aktivitaet order by beschreibung";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            // Query wird modifiziert
            rs=pst.executeQuery();

            ArrayList<String> zeile=new ArrayList<String>();

            while(rs.next()){

                String beschreibung = rs.getString("beschreibung");
                String aktivitName = rs.getString("aktivitaet_name");
                String zeitraum = rs.getString("zeitraum");
                String massnahme = "";

                String test = returnMassnahme(aktivitName);

                if(!test.equals("")){
                    massnahme = test;
                }

                // Speichern urz name und Vorname in einer Arraylist
                zeile.add( beschreibung);
                zeile.add(aktivitName);
                zeile.add(zeitraum);
                zeile.add(massnahme);

                listeAktivitat.add(zeile);
                zeile = new ArrayList<String>();
            }
            /// Statement closed und ResultStatement closed
            rs.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return listeAktivitat;

    }

    public boolean deleteAktivitaet(String nameAkt){

        String query= "DELETE FROM aktivitaet WHERE aktivitaet_name = '"+nameAkt+"';";
        

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            stmt = conn.createStatement();
            // Query wird modifiziert
            stmt.executeUpdate(query);

            return true;
        }

        catch(SQLException exception ){

            fehlerString = exception.getMessage();
            System.out.println("Fehler Sting:"+fehlerString);

            return false;
        }

    }

    public String returnFehlerString(){return  fehlerString; }



}
