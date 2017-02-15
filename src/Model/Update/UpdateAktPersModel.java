package Model.Update;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 31.08.16.
 */
public class UpdateAktPersModel extends Observable {

    Connection conn = null;
    PreparedStatement pst = null;
    String fehlerString = "";
    ResultSet result = null;

    public String urz = "";
    public Object defaultObject = "";
    public String defaultNameAktivitaet = "";
    public int defaultIDAktivitaet;
    public int id_m_a = 0, id_s_m_a = 0;

    /**
     * Konstruktor der Klasse
     */
    public UpdateAktPersModel(){
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


    public ArrayList<String> returnAktivitaetNameUndID(){

        ArrayList<String> dataAktivitaet = new ArrayList<String>();

        try {
            // Query 1
            String query = "select aktivitaet.aktivitaet_name, beschreibung from aktivitaet INNER JOIN m_a on (m_a.aktivitaet_name = aktivitaet.aktivitaet_name) ORDER BY beschreibung;";
            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();
            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while (result.next()) {
                String beschreibung = result.getString("beschreibung");
                String name = result.getString("aktivitaet_name");

                dataAktivitaet.add(beschreibung);
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

    public ArrayList<String> selectMultiple(String query,String []spalten)
    {
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs=pst.executeQuery();

            while(rs.next())
            {
                for(int i=0;i<spalten.length; i++) {

                    zeile.add(rs.getString(spalten[i]));
                }

            }

            /// Statement closed und ResultStatement closed
            rs.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }


        return zeile;
    }

    public int findId_m_a(String nameAkt) {

        try {
            // Query 1
            String query = "select id from m_a where aktivitaet_name = '" + nameAkt + "';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(0, nameAkt);

            

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()) {
                id_m_a = result.getInt("id");
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

            return id_m_a;

        } catch (SQLException exception) {
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: "+fehlerString);

            return 0;
        }
    }

    public int findId_s_m_a(int id_m_a){

        try {
            // Query 1
            String query = "select id from s_m_a where urz = '" + urz + "' AND id_m_a = '" + id_m_a + "';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(0, nameAkt);

            

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()) {
                id_s_m_a = result.getInt("id");
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

            return id_s_m_a;

        } catch (SQLException exception) {
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return 0;
        }

    }

    public String findNameAkt(String beschAkt) {

        String nameAkt = "";

        try {
            // Query 1
            String query = "select aktivitaet_name from aktivitaet where beschreibung = '"+beschAkt+"';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(0, nameAkt);

            

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()) {
                nameAkt = result.getString("aktivitaet_name");
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

            return nameAkt;

        }

        catch (SQLException exception) {
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println("Fehler String: "+fehlerString);

            return "";
        }
    }


    public boolean updatePersAktivitaet(int id_m_a){

        try{
            // Query
            String query ="UPDATE s_m_a SET id_m_a = " + id_m_a + " WHERE urz = '" + urz + "' AND id_m_a = '" + defaultIDAktivitaet + "';";

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
    public boolean insertIntoMobilität(int id_s_m_a, String durchführung, String art){

        try{
            // Query
            String query ="INSERT INTO student_mob (id_s_m_a, durchfuehrung, art)" +
                    "VALUES('"+id_s_m_a+"', '"+durchführung+"', '"+art+"'); ";

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

    public boolean deleteMobilität(int id_s_m_a){

        try{
            // Query
            String query ="DELETE FROM student_mob WHERE id_s_m_a = '" + id_s_m_a + "'";

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

    public String getFehlerString(){return  fehlerString; }

    public void setUrz(String urz){
        this.urz = urz;
    }

    public void setDefaultObject(Object defaultOption){
        this.defaultObject = defaultOption;
    }

    public void setDefaultIDAktivitaet(int defaultIDAkt){
        this.defaultIDAktivitaet = defaultIDAkt;
    }

    public void setDefaultNameAktivitaet(String nameAktivitaet){this.defaultNameAktivitaet = nameAktivitaet; }



}
