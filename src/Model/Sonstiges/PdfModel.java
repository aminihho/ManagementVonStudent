package Model.Sonstiges;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

/**
 * Created by annelie on 08.11.16.
 */
public class PdfModel extends Observable {
    Connection _conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;

    public boolean akt = false, sta = false, bem = false, mob;

    String [][] aktivitat, status, bemerkung;
    String [] person;

    public PdfModel(){
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

    public String getMassnahme(String nameAkt){

        String massnahme = "";
        String query = "select massnahme_name from m_a where aktivitaet_name = '"+nameAkt+"';";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next())
                massnahme = result.getString("massnahme_name");

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }
        return massnahme;
    }


    public void setAktivitat(String[][] aktivitat){
        this.aktivitat = aktivitat;
    }

    public void setStatus(String[][] status){
        this.status = status;
    }

    public void setBemerkung(String[][] bemerkung){
        this.bemerkung = bemerkung;
    }

    public void setPerson(String[] person){
        this.person = person;
    }

    public String[][] getAktivitat(){
        return aktivitat;
    }

    public String[][] getStatus(){
        return status;
    }

    public String[][] getBemerkung(){
        return bemerkung;
    }

    public String[] getPerson(){
        return person;
    }
}
