package Model.Update;

import Model.Sonstiges.Connection_DB;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;

import java.util.Observable;
import java.util.ArrayList;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Created by annelie  and kourda on 13.06.16.
 */
public class IndexUpdatePersonModel extends Observable {

    private Connection conn = null;
    private PreparedStatement pst = null;
    ResultSet result = null;
    private String suchQuery = "SELECT name, vorname, urz FROM student ORDER BY name";
    private String suchQueryUrz = "SELECT urz FROM student ORDER BY name LIMIT 1";
    public boolean suchErgebnis;


    public IndexUpdatePersonModel(){
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

    public void setSuchQuery(String query){
        this.suchQuery = query;
    }
    public String getScuhQuery(){
        return this.suchQuery; 
    }
    public void setSuchQueryUrz(String query){
        this.suchQueryUrz = query;
    }

    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht nach alle extierend Student in der Tabelle Studen.
     * Die Rückgabewert ist ein Tablle, in der alle Studenten gespeichert wird.
     */
    public ArrayList<ArrayList<String>> ListeAllerStudenten() {
        ArrayList<ArrayList<String>> listeStudent=new ArrayList<ArrayList<String>>();

        // Query
        String query = suchQuery;
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

            ArrayList<String> zeile=new ArrayList<String>();
            while(result.next())
            {
                
                String urz = result.getString("urz");
                String nameStudent = result.getString("name");
                String vornameStudent = result.getString("vorname");

                // Speichern urz name und Vorname in einer Arraylist
                zeile.add(nameStudent);
                zeile.add(vornameStudent);
                zeile.add(urz);
                listeStudent.add(zeile);
                zeile=new ArrayList<String>();
            }
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }
        return listeStudent;

    }


    /***
     * Holt die ID alle Aktivitäten, die einer Person zugeordnet sind
     */
    public ArrayList<ArrayList<String>> ListeAlleAktivitaeten(String urz)
    {
        ArrayList<ArrayList<String>> listeAktivitaeten = new ArrayList<ArrayList<String>>();

        // Query:
        String qurey ="select m_a.aktivitaet_name, beschreibung from aktivitaet " +
                "inner join m_a on (aktivitaet.aktivitaet_name = m_a.aktivitaet_name) " +
                "inner join s_m_a on m_a.id = s_m_a.id_m_a where s_m_a.urz='"+ urz +"' " +
                "ORDER BY beschreibung;";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(qurey);
            // Query wird modifiziert
            result = pst.executeQuery();
            ArrayList<String> zeile=new ArrayList<String>();

            while(result.next()) {
                String aktivitaetName=result.getString("aktivitaet_name");

                // Speichern aktivitaetName in einer Arraylist
                zeile.add(aktivitaetName);
                listeAktivitaeten.add(zeile);

                zeile = new ArrayList<String>();
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return listeAktivitaeten;
    }

    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht die Information über Student.
     * @parm1: ist die Urz eines Student.
     */
    public ArrayList <ArrayList<String>> listeAktivitatmitMobilitaet(ArrayList<String> listeaktivität, String urz) {
        ArrayList<ArrayList<String>> liste=new ArrayList<ArrayList<String>>();
        Functions fonction = new Functions();
        int lengthliste = listeaktivität.size();

        for(int j = 0; j < lengthliste; j++){

            String aktivitatet = listeaktivität.get(j).toString();
            ArrayList<String> zeile = new ArrayList<String>();

            String query="select beschreibung, aktivitaet_name, zeitraum from aktivitaet where aktivitaet_name = '"+ aktivitatet + "'";

            try{
                // ein Objekt der Klasse PrepareStatement wird  erzeugt.
                pst=conn.prepareStatement(query);
                // Query wird modifiziert
                result = pst.executeQuery();

                while(result.next()) {
                    String beshreibung = result.getString("beschreibung");
                    String aktivitatName = result.getString("aktivitaet_name");
                    String zeitraum=result.getString("zeitraum");

                    // Speichern aktivitaetName in einer Arraylist
                    zeile.add(beshreibung);
                    zeile.add(aktivitatName);
                    zeile.add(zeitraum);

                    if(aktivitaetIstMobilitaet(beshreibung) == 1){
                        query = "select  durchfuehrung, art from aktivitaet INNER JOIN m_a ON (aktivitaet.aktivitaet_name= m_a.aktivitaet_name) INNER JOIN s_m_a ON (m_a.id = s_m_a.id_m_a) INNER JOIN student_mob ON (s_m_a.id = id_s_m_a) where s_m_a.urz = '"+urz+ "' and aktivitaet.aktivitaet_name = '" + aktivitatName+"'";
                        String[] spalten = {"durchfuehrung","art"};
                        ArrayList<String> zeile2 = new ArrayList<String>();
                        zeile2 = SelectMultiple(query, spalten);
                        zeile.addAll(zeile2);
                    }

                }

                /// Statement closed und ResultStatement closed
                result.close();
                pst.close();
            }
            catch(SQLException exception ){
                System.out.println(exception);
            }
            liste.add(zeile);
        }

        return liste;
    }

    public int aktivitaetIstMobilitaet (String aktivitaet){

        int bool = 0;

        try {
            // Query 1
            String query = "SELECT mob('" + aktivitaet +"');";
            // PrepareStatement wird  erzeugt.
            PreparedStatement pst = conn.prepareStatement(query);

            // ein Abfrage auf die Tabelle
            ResultSet result = pst.executeQuery();

            // Der Ergebnis der Funktion wird hier geholt
            while(result.next()){
                bool = result.getInt(1);
            }
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return bool;
    }

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

    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht nach alle Status von einen Student.
     * @parm1: ist die Urz eines Student.
     */
    public ArrayList<ArrayList<String>> ListeAlleStatus(String urz) {

        ArrayList<ArrayList<String>> listeStatus=new ArrayList<ArrayList<String>>();
        String query="SELECT status_typ FROM student_status WHERE urz = '"+urz+"' ORDER BY status_typ";
        // String query="select status_typ from student_status where urz='alma2'";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);

            // Query wird modifiziert
            result=pst.executeQuery();
            ArrayList<String> zeile=new ArrayList<String>();

            while(result.next()){
                String status=result.getString("status_typ");
                // Speichern aktivitaetName in einer Arraylist
                zeile.add(status);
                listeStatus.add(zeile);
                zeile=new ArrayList<String>();
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }


        return listeStatus;
    }

    public ArrayList<ArrayList<String>> ListeBermerkungen(String urz){
        ArrayList<ArrayList<String>> listBermerkung=new ArrayList<ArrayList<String>>();

        String query="select urz, bemerkung from student_bem where urz='"+urz+"'";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();
            ArrayList<String> zeile=new ArrayList<String>();

            while(result.next()){
                String status=result.getString("bemerkung");
                // Speichern aktivitaetName in einer Arraylist
                zeile.add(status);
                listBermerkung.add(zeile);
                zeile = new ArrayList<String>();
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return listBermerkung;
    }

    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht die Information über Student.
     * @parm1: ist die Urz eines Student.
     */
    public ArrayList<String> ListeInformationStudent(String urz)
    {
        ArrayList<String> zeile=new ArrayList<String>();
        String query="select name, vorname, urztuc, fakultaet, geburtsdatum, email, telefon from student where urz='"+urz+"'";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next()){
                String name=result.getString("name");
                String vorname=result.getString("vorname");
                String urztuc = result.getString("urztuc");
                String geburtsdatum=result.getString("geburtsdatum");
                String fakultaet=result.getString("fakultaet");
                String telefon=result.getString("telefon");
                String email=result.getString("email");

                // Speichern aktivitaetName in einer Arraylist
                zeile.add(name);
                zeile.add(vorname);
                zeile.add(urztuc);
                zeile.add(fakultaet);
                zeile.add(geburtsdatum);
                zeile.add(email);
                zeile.add(telefon);

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

    public ArrayList<ArrayList<String>> returnAktivitaetenMitMobilitaet(String urz) {

        ArrayList<ArrayList<String>> listeAktivitaeten = new ArrayList<ArrayList<String>>();

        // Query:
        String qurey = "select m_a.aktivitaet_name, beschreibung, zeitraum, s_m_a.id from aktivitaet " +
                "inner join m_a on (aktivitaet.aktivitaet_name = m_a.aktivitaet_name) " +
                "inner join s_m_a on (m_a.id = s_m_a.id_m_a)" +
                "where s_m_a.urz ='" + urz + "'" +
                "ORDER BY beschreibung;";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(qurey);
            // Query wird modifiziert
            ResultSet result = pst.executeQuery();
            ArrayList<String> zeile=new ArrayList<String>();

            while(result.next()) {
                String beschreibung = result.getString("beschreibung");
                String aktivitaetName = result.getString("aktivitaet_name");
                String zeitraum = result.getString("zeitraum");
                //Für die überprüfung von Mobilität
                String id_s_m_a = result.getString("id");
                //Falls Aktivität Mobiltät hat
                String durchfuehrung = "";
                String art = "";


                // Speichern aktivitaetName in einer Arraylist
                zeile.add(beschreibung);
                zeile.add(aktivitaetName);
                zeile.add(zeitraum);

                ArrayList<String> mob = hatMobilitaet(id_s_m_a);
                if(!mob.isEmpty()){
                    System.out.println("hellow");
                    durchfuehrung = mob.get(0);
                    art = mob.get(1);
                }

                zeile.add(durchfuehrung);
                zeile.add(art);

                listeAktivitaeten.add(zeile);
                zeile = new ArrayList<String>();
            }

            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return listeAktivitaeten;
    }

    public ArrayList<String> hatMobilitaet(String id_s_m_a) {

        ArrayList<String> zeile = new ArrayList<String>();

        // Query:
            String query = "select * from student_mob where id_s_m_a = '" + id_s_m_a + "'";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            ResultSet result = pst.executeQuery();

            if(result.next()){
                while(result.next()) {
                    String durchfuehrung = result.getString("durchfuehrung");
                    String art = result.getString("art");

                    // Speichern aktivitaetName in einer Arraylist
                    zeile.add(durchfuehrung);
                    zeile.add(art);

                }
            }

            else
                zeile.isEmpty();


            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return zeile;
    }


    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht ob ein Student ein Bermerkung hat.
     * @parm1: ist die Urz eines Student.
     */

    public int StudentHatBermerkung(String urz){
        int anzahlBermerkung=0;
        String query="select Array1DLength('"+urz+"')";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next()){
                anzahlBermerkung=Integer.parseInt(result.getString("array1dlength"));

            }
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return anzahlBermerkung;
    }

    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht alle Bemerkungen von Student.
     * @parm1: ist die Urz eines Student.
     */

    public String[] ListBermerkung(String urz){
        String[] listBermerkung={""};
        String query="select bemerkungen from student where urz='"+urz+"'";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

            while(result.next())
            {
                Array array=result.getArray("bemerkungen");
                listBermerkung=(String[])array.getArray();

            }
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }

        return listBermerkung;
    }

    public String getMassnahme(String nameAkt){

        String massnahme = "";
        String query = "select massnahme_name from m_a where aktivitaet_name = '"+nameAkt+"';";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
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



    /***
     * Dise funktion macht ein Abfrage zum daten bank dann sucht die urz von der ersten Student.
     */
    public String TopUrz(){

        String urz = "";
        String query = suchQueryUrz;

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next())
                urz=result.getString("urz");
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();
        }
        catch(SQLException exception ){
            System.out.println(exception);
        }
        return urz ;
    }

    public boolean urzExistiert(String urzGesucht){
        try{
            boolean bool;
            String query=" select * from student where urz = '"+urzGesucht+"'";
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            if(result.next())
                bool = true;

            else
                bool = false;
            /// Statement closed und ResultStatement closed
            result.close();
            pst.close();

            return bool;
        }

        catch(SQLException exception ){
            System.out.println(exception);
            return  false;
        }
    }
    
    
   


}

