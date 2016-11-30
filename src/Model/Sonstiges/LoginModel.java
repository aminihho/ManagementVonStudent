package Model.Sonstiges;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by annelie on 13.06.16.
 */
public class LoginModel extends Observable  {

    Connection  _conn;
    PreparedStatement pst;
    private boolean  exist_user=false,exist_password=false;


    public LoginModel(){
        // Verbindung zum Datenbank hestellen.
        Connection_DB _connection=new Connection_DB();
        _conn=_connection.getConnection();
    }

    /**
     * Destruktor
     */
    protected void finalize(){

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

    /***
     * Dise funktion macht ein Abfrage zum daten bank und sucht ob das user in diese Daten Bank existieret oder nicht
     * @param user:String , mit diesem Parameter versuche ich ob  ein User in daten Bank hat das gleich name wie user falls ja :
     * dann werden die  boolean Variabele exist_user auf true ersetzen .
     */
    public boolean find_user(String user) {
        boolean exists = false;

        try{
            // Query
            String query ="SELECT username FROM users WHERE username = ?";
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=_conn.prepareStatement(query);
            // Query wird modifiziert
            pst.setString(1,user);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                exists = true;
            }

            /// Statement closed und ResultStatement closed
            rs.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
            exists = false;
        }

        return exists;
    }


    /***
     * Dise funktion macht ein Abfrage zum daten bank und sucht ob das Password  in diese Daten Bank existieret oder nicht
     * //@param user :String , mit diesem Parameter �berpr�fe  ich ob  ein Password  in daten Bank gleich ist als password  falls ja :
     * dann werden die  boolean Variabele exist_password  auf true ersetzen .
     */

    public boolean  find_Password(String password) {

        boolean exists = false;

        try{
            // Query vorbereiten
            String query="SELECT passwort FROM users WHERE passwort = ?";
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=_conn.prepareStatement(query);
            // Query wird modifiziert
            pst.setString(1,password);
            ResultSet rs=pst.executeQuery();

            while( rs.next()){
                exists = true;
            }

            /// Statement  und Resultstatement closed
            pst.close();
            rs.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
            exists = false;
        }

        return exists;
    }

}
