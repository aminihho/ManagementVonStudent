package Model.Insert;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 17.09.16.
 */
public class InsertBemStudentModel extends Observable {
    Connection _conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    public String fehlerString = "";
    public Object defaultObject = "";

    public InsertBemStudentModel(){
        /**
         * Konstruktor:
         * Verbindung zur Datenbank herstellen
         */
        Connection_DB _connection = new Connection_DB();
        _conn = _connection.getConnection();

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

    public ArrayList<String> returnStundentName(){

        ArrayList<String> dataStudent = new ArrayList<String>();;

        try {
            // Query 1
            String query ="select name, vorname, urztuc from student ORDER BY name";
            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while(result.next()){
                String vorname = result.getString("name");
                String nachname = result.getString("vorname");
                String urz = result.getString("urztuc");

                dataStudent.add(vorname);
                dataStudent.add(nachname);
                dataStudent.add(urz);

            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataStudent;
    }

    public boolean insertValues(String urz, String bemerkung){

        try{
            // Query
            String query ="INSERT INTO student_bem (urz, bemerkung) VALUES (?, ?); ";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, urz);
            pst.setString(2, bemerkung);
            System.out.println(pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return false;
        }



    }

    public String findUrz(String urztuc){
        String urz = "";

        try{
            // Query 1
            String query ="select urz from student where urztuc = '"+ urztuc +"';";

            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);
            //pst.setString(1, name);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()){
                urz = result.getString("urz");
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

        return urz;
    }

    public ArrayList<String> selectMultiple(String query,String []spalten)
    {
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
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


    public String getErrorMessage(){return fehlerString; }

    public void setDefaultObject(Object student){
        this.defaultObject = student;
    }
}
