package Model.Insert;

import Model.Sonstiges.Connection_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by annelie on 02.05.16.
 */
public class InsertStatusStudentModel extends Observable {
    Connection _conn = null;
    PreparedStatement pst = null, pst2 = null;
    ResultSet result = null;
    public String fehlerString = "";
    String urz = "";
    public Object defaultObject = "";

    ArrayList<String> dataStudent;
    ArrayList<String> dataStatus;


    public InsertStatusStudentModel(){
        /**
         * Konstruktor:
         * Verbindung zur Datenbank herstellen
         */
        Connection_DB _connection = new Connection_DB();
        _conn=_connection.getConnection();

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

        if (pst2 != null) {
            try {
                pst2.close();
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

    public String findUrz(String name){
        try{
            // Query 1
            String query ="select urz from student where name = '"+ name +"';";

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

    public String getErrorMessage(){return fehlerString; }

    public int insertValues(String urz, String status){
        try{
            // Query
            String query ="INSERT INTO student_status VALUES(?, ?)";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, status);
            pst.setString(2, urz);

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
            System.out.println("Fehler String: " + fehlerString);

            return -1;
        }

    }

    public int insertValues2(String urz, String status, String status2){
        try{
            /**
             * Query 1
             */
            String query ="INSERT INTO student_status VALUES("+ status +", "+ urz +");";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
            pst.close();



            /**
             * Query 2
             */
            String query2 ="INSERT INTO student_status VALUES(?, ?)";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst2 = _conn.prepareStatement(query2);
            pst2.setString(1, status2);
            pst2.setString(2, urz);

            System.out.println("Query: " + pst2);

            // ein Abfrage auf die Tabelle
            pst2.executeUpdate();

            /// Statement closed und ResultStatement closed
            pst2.close();
            System.out.println("Insert succeed");

            return 1;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return -1;
        }
    }

    public ArrayList<String> returnStundentName(){

        try {
            // Query 1
            String query ="select name, vorname, urztuc from student ORDER BY name";
            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            dataStudent = new ArrayList<String>();
            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while(result.next()){
                String vorname = result.getString("name");
                String nachname = result.getString("vorname");
                String urztuc= result.getString("urztuc");


                dataStudent.add(vorname);
                dataStudent.add(nachname);
                dataStudent.add(urztuc);
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

    public ArrayList<String> returnStatusName(){

        try {
            // Query 1
            String query ="select status_typ from status ORDER BY status_typ";
            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            dataStatus = new ArrayList<String>();
            int columnCount = result.getMetaData().getColumnCount();

            //holt die Tupel
            while(result.next()){
                String status = result.getString("status_typ");
                dataStatus.add(status);

            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return dataStatus;
    }

    public ArrayList<String> selectMultiple(String query,String []spalten) {
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

    public  void setDefaultObject(Object student){
        defaultObject = student;
    }
}
