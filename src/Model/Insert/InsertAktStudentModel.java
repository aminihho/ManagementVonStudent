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
public class InsertAktStudentModel extends Observable {
    Connection_DB _connection = null;
    Connection _conn = null;
    PreparedStatement pst, pst2 = null;
    ResultSet result = null;

    ArrayList<String> dataStudent;
    ArrayList<String> dataAktivitaet;
    int bool = 0;
    public String fehlerString;
    String urz;
    int id_s_m_a, id_m_a;
    public Object defaultObject = "";

    public InsertAktStudentModel(){
        /**
         * Konstruktor:
         * Verbindung zur Datenbank herstellen
         */
        _connection = new Connection_DB();
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

        if (pst2 != null) {
            try {
                pst2.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (_conn != null) {
            try {
                _conn.close();
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

    public int findId_m_a(String nameAkt){
        try{
            _conn = _connection.getConnection();
            // Query 1
            String query ="select id from m_a where aktivitaet_name = '"+nameAkt+"';";

            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);

            System.out.println(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()){
                id_m_a = result.getInt("id");
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


        System.out.println(id_m_a);
        return id_m_a;
    }


    public int findId_s_m_a(String urz, int idMa){
        try{
            _conn = _connection.getConnection();
            // Query 1
            String query ="SELECT id FROM s_m_a WHERE urz = ? AND id_m_a = ?;";

            // PrepareStatement wird  erzeugt.
            pst =_conn.prepareStatement(query);
            pst.setString(1, urz);
            pst.setInt(2, idMa);


            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()){
                id_s_m_a = result.getInt("id");
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


        return id_s_m_a;
    }


    public String findUrz(String name){
        try{
            _conn = _connection.getConnection();
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

    public ArrayList<String> returnStundentName(){

        try {
            _conn = _connection.getConnection();
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



    public ArrayList<String> returnAktivitaetNameUndID(){
        try {
            _conn = _connection.getConnection();
            // Query 1
            String query = "SELECT aktivitaet.aktivitaet_name, beschreibung FROM aktivitaet " +
                    "INNER JOIN m_a ON (m_a.aktivitaet_name = aktivitaet.aktivitaet_name) ORDER BY beschreibung";

            // PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            dataAktivitaet = new ArrayList<String>();
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

    public int returnMobilitaet(String aktivitaet){

        try {
            _conn = _connection.getConnection();
            // Query 1
            String query = "SELECT mob('" + aktivitaet +"');";
            // PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            // Der Ergebnis der Funktion wird hier geholt
            while(result.next()){
                bool = result.getInt(1);
                System.out.println("Return Value ist: " + bool);
            }
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return bool;
    }

    public int insertValues(String urz, int id_m_a){
        try{
            _conn = _connection.getConnection();
            // Query
            String query ="INSERT INTO s_m_a (urz, id_m_a) VALUES( ?, ?)";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setString(1, urz);
            pst.setInt(2, id_m_a);
            System.out.println(pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
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

    public int insertValuesMobilität(int idSma, String durchführung, String art){
        try{

            _conn = _connection.getConnection();
            // Query
            String query ="INSERT INTO student_mob (id_s_m_a, durchfuehrung, art) VALUES (?, ?, ?);";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            pst.setInt(1, idSma);
            pst.setString(2, durchführung);
            pst.setString(3, art);

            System.out.println(pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
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

    public void setDefaultObject(Object defaultObject){
        this.defaultObject = defaultObject;
    }


}
