package Model.Sonstiges;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// psql -h pgsql.hrz.tu-chemnitz.de -U inprotuc_rw -d inprotuc

/**
 * Created by annelie on 20.04.16.
 */
public class Connection_DB {
    private Connection connection = null;

    public Connection_DB(){
        // Prüfen, dass die Treiber konfiguriert sind
        System.out.println("------------- PostgreSQL JDBC Connection Testing --------------");

        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println( "Treiber fuer Postgresql ist nicht konfiguriert in library path  ");
            e.printStackTrace();
            return ;
        }
        System.out.println("JDBC-Treiber ist konfiguriert.");

        //Verbindet sich mit der Datenbank
        try{
            connection= DriverManager.getConnection("jdbc:postgresql://pgsql.hrz.tu-chemnitz.de:5432/inprotuc","inprotuc_rw", "ThaWaiz3");
            System.out.println("Eingeloggt.");
        }
        catch(SQLException e){
            System.out.println("Connection Failed Check output console");
            e.printStackTrace();
            return;
        }




    }

    public Connection getConnection(){
        return this.connection;
    }

    public void closeConnection(){

        if (connection != null) {

            try { connection.close();
                System.out.println("------------- PostgreSQL JDBC Connection Closed --------------");
            }

            catch (Exception ex) { /*?*/ }
        }
    }
}
