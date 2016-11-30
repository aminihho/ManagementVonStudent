package Model.Update;

import Model.Sonstiges.Connection_DB;
import Model.Sonstiges.Functions;

import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;


/**
 * Created by annelie on 13.06.16.
 */
public class UpdatePersonModel extends Observable  {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet result = null;
    Statement stmt = null;


    /**
     * Konstruktor der Klasse
     */
    private String updateUrz = "";
    public String fehlerString = "";


    public UpdatePersonModel(){
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


    /**
     *
     * @param _urz
     */
    public void  setUrz(String _urz)
    {
        updateUrz =_urz;

    }
    /**
     *
     * @return urz ;
     */
    public String Geturz()
    {
        return updateUrz;
    }

    /** ;
     *
     * @param urz in der wird die gesuchte Urz von ein Student gespeichert.
     * @return
     */
    public ArrayList<ArrayList<String>> listeAlleAktivitaeten(String urz) {

        ArrayList<ArrayList<String>> listeAktivitaeten = new ArrayList<ArrayList<String>>();

        // Query:
        String qurey ="select m_a.aktivitaet_name from aktivitaet " +
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
            result = pst.executeQuery();
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
        String query = "select * from student_mob where id_s_m_a = '"+id_s_m_a+"'";

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

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

    /**
     *  Diese Funktion überpruft, ob eine Aktivität hat eine Dürchführung.
     */
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
                        zeile2 = selectMultiple(query, spalten);
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

    public ArrayList<String> selectMultiple(String query,String []spalten) {
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

    public ArrayList<ArrayList<String>> listeAlleStatus(String urz) {

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

    public ArrayList<ArrayList<String>> listeBemerkungen(String urz) {

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

    public ArrayList<String> listeInformationStudent(String urz)
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

    public ArrayList<String> listeUrzTuc()
    {
        ArrayList<String> zeile=new ArrayList<String>();

        String query="select urztuc from student;";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next()){
                String urztuc = result.getString("urztuc");

                // Speichern aktivitaetName in einer Arraylist
                zeile.add(urztuc);
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

    public String[] listeBermerkungen2(String urz){
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

    public int StudentHatBermerkung(String urz){
        int anzahlBermerkung=0;
        String query="select Array1DLength('"+urz+"')";
        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result=pst.executeQuery();

            while(result.next())
            {
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
    /**
     *
     * @param query the query
     * @param Columnname
     * @return
     */
    public ArrayList<String> Select(String query,String Columnname){
        ArrayList<String> zeile=new ArrayList<String>();

        try{
            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst=conn.prepareStatement(query);
            // Query wird modifiziert
            result = pst.executeQuery();

            while(result.next())
            {
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

    public boolean deletePerson(String urz){

        try{
            // Query
            String query ="delete from s_m_a where urz = '" + urz + "';" +
                    "delete from student_status where urz = '" + urz + "';" +
                    "delete from student where urz = '" + urz + "'";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
            /// Statement closed und ResultStatement closed
            pst.close();

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return false;
        }

    }

    public boolean deleteAktivitaetPerson(String urz, int id_m_a){

        try{
            // Query
            String query ="DELETE FROM s_m_a WHERE urz = '"+urz+"' AND id_m_a = '"+id_m_a+"'";

            // ein Objekt der Klasse PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            System.out.println("Query: " + pst);

            // ein Abfrage auf die Tabelle
            pst.executeUpdate();
            /// Statement closed und ResultStatement closed
            pst.close();

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);

            fehlerString = exception.getMessage();
            System.out.println(fehlerString);

            return false;
        }

    }

    public int findId_m_a(String nameAkt) {
        int id_m_a = 0;

        try {
            // Query 1
            String query = "select id from m_a where aktivitaet_name = '" + nameAkt + "';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(0, nameAkt);

            System.out.println(query);

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
            System.out.println(fehlerString);

            return 0;
        }
    }

    public String findUrzTuc(String urz){
        String urztuc = "";

        try{
            // Query 1
            String query ="select urztuc from student where urz = '"+urz+"';";

            // PrepareStatement wird  erzeugt.
            pst = conn.prepareStatement(query);
            //pst.setString(1, name);

            // ein Abfrage auf die Tabelle
            result = pst.executeQuery();

            //holt die Tupel
            while (result.next()){
                urztuc = result.getString("urztuc");
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

        return urztuc;
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

            return true;
        }

        catch(SQLException exception ){
            System.out.println(exception);
        }

        return false;


    }

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

            i=1;
        }

        catch(SQLException exception ){
            fehlerString = exception.toString();
        }
        if(i==0)
            return false;
        else
            return true;
    }

    public String getFehlerString(){return fehlerString; }



}

