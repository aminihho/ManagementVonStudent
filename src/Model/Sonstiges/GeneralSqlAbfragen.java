package Model.Sonstiges;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by annelie on 13.06.16.
 */
public class GeneralSqlAbfragen {

    String fehlerString;
    public String fehler = "";

    public GeneralSqlAbfragen(){
        Connection_DB _connection=new Connection_DB();
        conn=_connection.getConnection();
    }

    Connection conn=null;
    PreparedStatement pst=null;
    Statement stmt = null;

    /**
     *   diese Funktion löscht ein Zeile von einer Tabelle.
     */
    public boolean ZeileVonTabelleLöschen (String tabelleName,String spalteName,String value) {
        String query="DELETE FROM " + tabelleName +" WHERE " + spalteName +" = '" + value + "';";

        System.out.println(query);

        try{

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            stmt=conn.createStatement();
            // Query wird modifiziert
            stmt.executeUpdate(query);

            /// Statement closed und ResultStatement closed
            stmt.close();
            conn.close();

            return true;
        }

        catch(SQLException exception ){

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return false;
        }

    }

    public String returnFehlerString(){
        return fehlerString;
    }



    public boolean UpdateZeileVonTabelle (String query)
    {
        try{

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            stmt=conn.createStatement();
            // Query wird modifiziert
            stmt.executeUpdate(query);
            /// Statement closed und ResultStatement closed
            stmt.close();
            conn.close();
            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);
        }

        return false;


    }

    /**
     *
     * @param query
     * @param spalten  spaltennamen der Tabelle
     * @return
     */
    public ArrayList<String> SelectMultiple(String query,String []spalten)
    {
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs=pst.executeQuery();

            while(rs.next())
            {
                for(int i=0;i<spalten.length; i++)
                {
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

    /**
     *
     * @param query the query
     * @param Columnname
     * @return
     */
    public ArrayList<String> Select(String query,String Columnname){
        ArrayList<String> zeile = new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs=pst.executeQuery();

            while(rs.next())
            {
                String name=rs.getString(Columnname);
                // Speichern aktivitaetName in einer Arraylist
                zeile.add(name);

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

    public String SelectString(String query,String Columnname){
        String name = "";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
               name = rs.getString(Columnname);

            }

            /// Statement closed und ResultStatement closed
            rs.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return name;
    }

    /**
     *   diese Funktion löscht ein Zeile von einer Tabelle.
     * @param tabelleName ist die Name der Tabelle.
     * @param spalteName ist die Name der Spalte.
     * @param value Wert der Spalte.
     * @return boolean
     */
    public boolean loeschenZeile  (String query  )
    {
        int i=0;
        try{

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            stmt=conn.createStatement();
            // Query wird modifiziert
            stmt.executeUpdate(query);

            /// Statement closed und ResultStatement closed
            stmt.close();
            conn.close();
            i=1;
        }

        catch(SQLException exception ){
            fehler = exception.toString();
        }
        if(i==0)
            return false;
        else
            return true;
    }

    /***
     * Diese Function prüfen, Ob ResultSet leer ist oder nicht.
     * @parm1: name der Tablle
     * RückgabeWert:True falls ein Tabelle nicht leer ist, sonnst false.
     */
    public boolean TestObQueryLeer(String tableName){
        String query = "SELECT COUNT(*) FROM "+tableName;
        Connection_DB classconnection=new Connection_DB();
        Connection con =classconnection.getConnection();

        try {
            PreparedStatement pst=con.prepareStatement(query);
            ResultSet rs=pst.executeQuery();
            int anzhahlZeilen=rs.getInt(1);
            if(anzhahlZeilen<=0){
                return false ;
            }
        }
        catch (SQLException exeception){
            System.out.println(exeception);
        }

        return true ;
    }

    /**
     *  Diese Funktion überpruft, ob eine Aktivität hat eine Dürchführung.
     */
    public int AktivitaetIstMobilitaet (String aktivitaet){

        int bool = 0;
        Connection_DB classconnection = new Connection_DB();
        Connection _conn =classconnection.getConnection();

        try {
            // Query 1
            String query = "SELECT mob('" + aktivitaet +"');";
            // PrepareStatement wird  erzeugt.
            PreparedStatement pst = _conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            ResultSet result = pst.executeQuery();

            // Der Ergebnis der Funktion wird hier geholt
            while(result.next()){
                bool = result.getInt(1);
            }
            result.close();
            pst.close();
            _conn.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return bool;
    }

}
