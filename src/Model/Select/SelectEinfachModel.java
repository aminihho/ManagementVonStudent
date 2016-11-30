package Model.Select;

import Model.Sonstiges.Connection_DB;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

/**
 * Created by annelie on 06.05.16.
 */
public class SelectEinfachModel extends Observable {
    Connection _conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    String fehlerString;
    DefaultTableModel model;
    Vector<Object> columnnames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    int columns;

    public SelectEinfachModel(){
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


    public ArrayList<String> select(String query, String Columnname){
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

            while(result.next()){
                String name = result.getString(Columnname);
                // Speichern aktivitaetName in einer Arraylist
                zeile.add(name);
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }


        return zeile;
    }

    public ArrayList<String> selectMultiple(String query,String []spalten) {
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs=pst.executeQuery();

            while(rs.next()){

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

    public  boolean queryNotEmpty(String query){
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = _conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                rs.close();
                pst.close();
                return true;
            }
            else{
                rs.close();
                pst.close();
                return false;
            }
        }

        catch(SQLException exception ){
            System.out.println(exception);
            return false;
        }

    }


}
