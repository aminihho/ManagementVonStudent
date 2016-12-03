package Controller.Update;

import Controller.Sonstiges.PdfTest;
import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;
import Model.Sonstiges.PdfModel;

import Model.Update.*;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Sonstiges.PdfView;

import View.Update.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Point;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.json.JSONObject;

/**
 * Created by annelie und Bani.
 */
public class IndexUpdatePersonController implements MouseListener,KeyListener,ActionListener, PopupMenuListener{

    /// Model
    private IndexUpdatePersonModel model;
    /// View
    private IndexUpdatePersonView view;

    public String name = "", vorname = "", urztuc = "";
    private String urz="", defaultUrz = "";
    public static String filePath = "";

    String [][] aktivitat, status, bemerkung;
    String [] person;

    public Object popupValue = "", beschreibungAkt = "", nameAkt = "", zeitraumAkt = "", durchfuerungAkt = "", artAkt= "", urzPointer="";
    public boolean mob;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";
    private int anzahl_Studnets = 0;

    /*---------------------------------------------------------------------------------------
     * Konstruktor setzt das MVC
     * ---------------------------------------------------------------------------------------*/
    public IndexUpdatePersonController(IndexUpdatePersonModel model, IndexUpdatePersonView view){
        this.model = model;
        this.view = view;
    }

    
    // use in Excel 
    private ArrayList<ArrayList<String>> getAlleAktivitaetenEinesStudent (String urz){
          Functions funktion = new Functions();
        ArrayList <ArrayList<String>> listeAktivitaet = this.model.ListeAlleAktivitaeten(urz);

        if(listeAktivitaet.size()>0){
            ArrayList<String> listeAkti = funktion.arrayListe2DTo1DArrayListe(listeAktivitaet);
            ArrayList<ArrayList<String>> listeAktivitaeten = model.listeAktivitatmitMobilitaet(listeAkti,urz);
            return listeAktivitaeten; 
        }
        
        return new ArrayList<ArrayList<String>>(); 
    }
    
    
    
    public void loadTabelleAktivität(String urz){

        Functions funktion = new Functions();

        ArrayList <ArrayList<String>> listeAktivitaet = this.model.ListeAlleAktivitaeten(urz);

        if(listeAktivitaet.size()>0){
            ArrayList<String> listeAkti = funktion.arrayListe2DTo1DArrayListe(listeAktivitaet);
            ArrayList<ArrayList<String>> listeAktivitaeten = model.listeAktivitatmitMobilitaet(listeAkti,urz);
            aktivitat = funktion.arrayListTo2DArrayVonString(listeAktivitaeten);
            mob = funktion.mobExists(aktivitat);

            if(mob){
                view.modelAktivitaet.setDataVector(aktivitat, view.titleTabeleAkivitaet);
            }

            else {
                String [] titel = new String[]{"Aktivität", "ID der Aktivität", "Zeitraum"};
                view.modelAktivitaet.setDataVector(aktivitat, titel);
            }

        }

        else{
            // Die Daten für das Table
            aktivitat = new String[][]{};

            // Die Column-Titles
            String[] titelaktivitaet = new String[]{"keine Aktivität vorhanden"};
            this.view.modelAktivitaet.setDataVector(aktivitat, titelaktivitaet);
        }
    }

    public void loadTabelleStatus(String urz){
        Functions funktion = new Functions();
        ArrayList <ArrayList<String>> listeStatus = this.model.ListeAlleStatus(urz);

        if(listeStatus.size()>0){
            status = funktion.arrayListTo2DArrayVonString(listeStatus);
            this.view.modelStatus.setDataVector(status, this.view.titleTabelleStatus);
        }

        else{
            // Die Daten für das Table
            status = new String[][]{};

            // Die Column-Titles
            String[] titelstatus = new String[]{"keinen Status vorhanden"};
            this.view.modelStatus.setDataVector(status, titelstatus);
        }
    }
    public void loadBemerkung(String urz){

        view.panelBemHintergrund.setVisible(true);

        Functions funktion = new Functions();
        ArrayList <ArrayList<String>> listeBemerkungen = this.model.ListeBermerkungen(urz);

        if(listeBemerkungen.size()>0){
            bemerkung = funktion.arrayListTo2DArrayVonString(listeBemerkungen);
            view.modelBemerkung.setDataVector(bemerkung, this.view.titleTabelleBemerkung);
        }

        else {
            //view.panelBemHintergrund.setVisible(false);

            // Die Daten für das Table
            bemerkung = new String[][]{};
            // Die Column-Titles
            String[] titelBemerkung = new String[]{"keine Bemerkungen"};
            this.view.modelBemerkung.setDataVector(bemerkung, titelBemerkung);
        }
    }


    public void loadPersönlicheInformation(String urz){
        Functions funktion = new Functions();
        person = funktion.arrayListTo1DString(this.model.ListeInformationStudent(urz));
        view.updatePersonlicheInformation(person);
    }

     
    
    
    // gib die Anzhl der Studenten züruck. 
    private ArrayList <ArrayList<String>> getStudentListe(){
  
        ArrayList <ArrayList<String>>  listeStudent = this.model.ListeAllerStudenten();
         this.anzahl_Studnets = listeStudent.size(); 
        return listeStudent;

    }

    public void loadTabellePersonen(){
        Functions funktion = new Functions();
        ArrayList <ArrayList<String>>  listeStudent = this.getStudentListe(); 
        if(listeStudent.size()>0){
            String[][] studentData = funktion.arrayListTo2DArrayVonString(listeStudent);
            view.modelStudent.setDataVector(studentData, this.view.titelTabelleStudent);
        }

        else{
            // Die Daten für das Table
            String[][] studentData = new String[][]{};

            // Die Column-Titles
            String[] titelStudent = new String[]{"keine Person vorhanden"};
            this.view.modelStudent.setDataVector(studentData, titelStudent);
        }
    }



    public void datenVomErstenStudentDarstellen(){
        //Bestimmen den Nutzername von ersten Student
        String urz = model.TopUrz();

        Functions funktion = new Functions();

        // Tabelle Aktivität
        loadTabelleAktivität(urz);

        //Tabelle Status
        loadTabelleStatus(urz);

        //Information zur Person
        loadPersönlicheInformation(urz);

        //Bemerkungen zur Person
        loadBemerkung(urz);

    }



    /*********************************************************************************
     * Alle Aktivitaten einer Person werden durch Click auf die Tabelle eingeblendet
     *********************************************************************************/
    public void mouseClicked(MouseEvent arg0) {

        Functions funktion = new Functions();

        //Holt das Urz vom Student was gecklickt wurde
        int zeile = this.view.tableStudent.getSelectedRow();
        JTable traget = (JTable)arg0.getSource();
        String nameSpalte = traget.getColumnName(2);

        if(nameSpalte == "Urz (DB)"){
            urz = this.view.tableStudent.getValueAt(zeile, 2).toString();
            defaultUrz = urz;

            // Tabelle Aktivität
            loadTabelleAktivität(urz);

            //Tabelle Status
            loadTabelleStatus(urz);

            //Information zur Person
            loadPersönlicheInformation(urz);

            //Bemerkungen zur Person
            loadBemerkung(urz);

            name = view.tableStudent.getValueAt(zeile, 0).toString();
            vorname = view.tableStudent.getValueAt(zeile, 1).toString();
            urztuc = view.lblUrzValue.getText();
        }

    }


    public void initView(String urz){
        
        

        // Tabelle Personen
        loadTabellePersonen();

        // Tabelle Aktivität
        loadTabelleAktivität(urz);

        //Tabelle Status
        loadTabelleStatus(urz);

        //Information zur Person
        loadPersönlicheInformation(urz);

        //Bemerkungen zur Person
        loadBemerkung(urz);

    }


    public void actionPerformed(ActionEvent actionEvent){

        Object source = actionEvent.getSource();

        if (source instanceof JTable) {
            System.out.println("Pop");
        }

        if (source instanceof JButton) {

            /**
             * Tätigung des Update-Buttons
             */
            if (source==view.btnupdateDate) {

                //Falls Student ausgewählt
                if (urz != "") {
                    UpdatePersonModel modelupdadeStudent = new UpdatePersonModel();
                    modelupdadeStudent.setUrz(urz);

                    UpdatePersonView x = new UpdatePersonView(modelupdadeStudent, "InProTUC Datenbank | Daten eines Studenten ändern");
                    x.innit();
                }
                else {
                    //Fehlermeldung
                    DialogFenster dialog = new DialogFenster();
                    dialog.infoDialog(view, "Bitte wählen Sie eine Person aus");
                }
            }


            /**
             * Tätigung des Such-Buttons
             */
            if(source == view.suchBtn){

                view.dispose();
                model.closeConnection();

                SelectEinfachView suche = new SelectEinfachView(new SelectEinfachModel(), "InProTUC Datenbank | Person suchen");
            }

            /**
             * Tätigung des Home-Buttons
             */
            if(source == view.homeBtn){

                view.dispose();
                model.closeConnection();

                IndexUpdatePersonView suche = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern oder löschen");
            }

            /**
             * Tätigung des Refresh-Buttons
             */
            if (source == view.refreshBtn){

                Functions funktion = new Functions();

                String urzRef = "";
                boolean exists = model.urzExistiert(defaultUrz);

                if(exists)
                    urzRef = defaultUrz;

                else
                    urzRef = model.TopUrz();

                initView(urzRef);

            }

            /**
             * Tätigung des Print-Buttons
             */
            if (source == view.printBtn){
               
                this.getStudentListe();// Mann ruft die function, um die anzahl der Student zu bestement 
                if(this.anzahl_Studnets == 1 ){
                    this.CreatePdfDatei();
                }
                else{
                    String query = model.getScuhQuery(); 
                    query = query.replace("name, vorname, student.urz", "student.urz");
                    
                    this.getAlleInofrmorationAllerUser(query);
                    System.out.print("sql--->"+query);
                    //to do Create Excel-Datei
                }
                
            }
        }


        /**
         * Auswahl eines Elements des Menus
         */
        if(source instanceof JMenuItem){
            JMenuItem sourceMenu = (JMenuItem)(actionEvent.getSource());
            String commandMenu = sourceMenu.getText();


            if(commandMenu.equals("Neue Aktivität")) {

                String urzDb = urzPointer.toString();
                String query = "select name, vorname, urztuc from student where urz = '"+urzDb+"'";
                String[] spalten = {"name", "vorname", "urztuc"};
                ArrayList<String> vollName = model.SelectMultiple(query, spalten);

                name = vollName.get(0);
                vorname = vollName.get(1);
                String urzTuc = vollName.get(2);
                Object defaultName = name + ", " + vorname + " – " + urzTuc;

                //System.out.println(defaultName);
                //System.out.println(commandMenu);

                InsertAktStudentModel model = new InsertAktStudentModel();
                model.setDefaultObject(defaultName);

                InsertAktStudentView fensterAktStu = new InsertAktStudentView(model, "InProTUC Datenbank | Aktivität einer Person eintragen");


            }

            if(commandMenu.equals("Neuer Status")) {

                String urzDb = urzPointer.toString();
                String query = "select name, vorname, urztuc from student where urz = '"+urzDb+"'";
                String[] spalten = {"name", "vorname", "urztuc"};
                ArrayList<String> vollName = model.SelectMultiple(query, spalten);

                name = vollName.get(0);
                vorname = vollName.get(1);
                String urzTuc = vollName.get(2);
                Object defaultName = name + ", " + vorname + " – " + urzTuc;

                InsertStatusStudentModel model = new InsertStatusStudentModel();
                model.setDefaultObject(defaultName);

                InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(model, "InProTUC Datenbank | Status einer Person eintragen");

            }

            if(commandMenu.equals("Neue Bemerkung")) {


                String urzDb = urzPointer.toString();
                String query = "select name, vorname, urztuc from student where urz = '"+urzDb+"'";
                String[] spalten = {"name", "vorname", "urztuc"};
                ArrayList<String> vollName = model.SelectMultiple(query, spalten);

                name = vollName.get(0);
                vorname = vollName.get(1);
                String urzTuc = vollName.get(2);
                Object defaultName = name + ", " + vorname + " – " + urzTuc;

                //Model wird initialisiert um das Default Object für das Combo Box festzulegen
                InsertBemStudentModel model = new InsertBemStudentModel();
                model.setDefaultObject(defaultName);

                //View wird initialisiert
                InsertBemStudentView neuebemerkung = new InsertBemStudentView(model, "InProTUC Datenbank | Bemerkung einer Person einfügen");

            }

            if(commandMenu.equals("Neue Person")) {

                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Person neu einfügen");
            }


            if(commandMenu.equals("Status anzeigen")){

                DialogFenster dialog = new DialogFenster();
                // Holt Status
                String status = popupValue.toString();
                dialog.informationDialog(view, status , "Status anzeigen");
            }

            if(commandMenu.equals("Aktivität anzeigen")){
                // Holt Aktivität
                DialogFenster dialog = new DialogFenster();

                if(mob){
                   String message = "Aktivität:  " + beschreibungAkt +
                           "<br>ID der Aktivität:  " +nameAkt +
                           "<br>Zeitraum:  " + zeitraumAkt+
                           "<br>Durchführung:  " + durchfuerungAkt+
                           "<br>Art:  " +artAkt  ;

                    dialog.informationDialog(view, message , "Aktivität anzeigen");
                }

                else {
                    String message = "Aktivität:  " + beschreibungAkt +
                            "<br>ID der Aktivität:  " +nameAkt +
                            "<br>Zeitraum:  " + zeitraumAkt ;

                    dialog.informationDialog(view, message , "Aktivität anzeigen");
               }
            }

            if(commandMenu.equals("Bemerkung anzeigen")) {

                DialogFenster dialog = new DialogFenster();
                String bemerkung = popupValue.toString();
                // Holt Bemerkung
                dialog.informationDialog(view, bemerkung , "Bemerkung anzeigen");
            }

            if(commandMenu.equals(INSERT_STUDENT)){

                //view.dispose();
                //model.closeConnection();
                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Student neu einfügen");
            }

            if(commandMenu.equals(INSERT_AKT_STU)){
                //view.dispose();
                //model.closeConnection();
                InsertAktStudentView fensterInsertAktStu = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_STATUS_STUDENT)){
                //view.dispose();
                //model.closeConnection();
                InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank | Status einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_AKT)){
                //view.dispose();
                //model.closeConnection();
                InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");
            }

            if(commandMenu.equals(INSERT_STATUS)){
                //view.dispose();
                //model.closeConnection();
                InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");
            }

            if(commandMenu.equals(INSERT_BEMERKUNG)){
                //view.dispose();
                //model.closeConnection();
                InsertBemStudentView fensterInsertBem = new InsertBemStudentView(new InsertBemStudentModel(), "InProTUC Datenbank | Bemerkung zur Person eintragen");

            }

            if(commandMenu.equals(SELECT_EINFACH)){
                view.dispose();
                model.closeConnection();
                SelectEinfachView fensterSucheEinfach = new SelectEinfachView(new SelectEinfachModel(), "InProTUC Datenbank | Person suchen");

            }

            if(commandMenu.equals(SELECT_ERWEITERT)){
                view.dispose();
                model.closeConnection();
                SelectErweitertView fensterSucheEinfach = new SelectErweitertView(new SelectErweitertModel(), "InProTUC Datenbank | Erweiterte Suche");
            }

            if(commandMenu.equals(UPDATE_STUDENT)){
                //view.dispose();
                //model.closeConnection();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern oder löschen");
            }

            if(commandMenu.equals(UPDATE_AKTIVITAET)){
                //view.dispose();
                //model.closeConnection();
                IndexUpdateAktivitaetView fensterUpdateAktivitaet = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");
            }

            if(commandMenu.equals(UPDATE_STATUS)){
                //view.dispose();
                //model.closeConnection();
                IndexUpdateStatusView fenster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "InProTUC Datenbank | Status ändern" );
            }

            if(commandMenu.equals(UPDATE_MASSNAHME)){
                //view.dispose();
                //model.closeConnection();
                IndexUpdateMassnahmeView fensterUpdateAktivitaet = new IndexUpdateMassnahmeView(new IndexUpdateMassnahmeModel(), "InProTUC Datenbank | Maßnahme ändern");
            }
        }


    }




    public void keyPressed(KeyEvent arg0) {

    }

    public void keyReleased(KeyEvent arg0) {


    }

    public void keyTyped(KeyEvent arg0) {


    }

    public void mouseEntered(MouseEvent e) {

        if(view.tableAktivitaet.equals(e.getSource())){

            if(view.tableAktivitaet.getRowCount() > 0){

                Point point = e.getPoint();
                int row,column;
                row 	= view.tableAktivitaet.rowAtPoint(point);
                column 	= view.tableAktivitaet.columnAtPoint(point);

                if(view.tableAktivitaet.columnAtPoint(point) == 0){
                    System.out.println("Value: " + view.tableAktivitaet.getValueAt(row, 1));

                    String nameAktivitaet = view.tableAktivitaet.getValueAt(row, 1).toString();
                    String massnahme = model.getMassnahme(nameAktivitaet);

                    System.out.println("Maßnahme: " + massnahme);
                    view.tableAktivitaet.setToolTipText("Massnahme: " + massnahme);
                }
            }
        }
    }


    public void mouseExited(MouseEvent arg0) {


    }

    public void mousePressed(MouseEvent e) {


    }



    public void mouseReleased(MouseEvent arg0) {

        if(view.tableAktivitaet.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tableAktivitaet.rowAtPoint(point);
            column 	= view.tableAktivitaet.columnAtPoint(point);

            if(mob){
                beschreibungAkt = view.tableAktivitaet.getValueAt(row, 0);
                nameAkt = view.tableAktivitaet.getValueAt(row, 1);
                zeitraumAkt = view.tableAktivitaet.getValueAt(row, 2);
                durchfuerungAkt = view.tableAktivitaet.getValueAt(row, 3);
                artAkt = view.tableAktivitaet.getValueAt(row, 4);
            }

            else {
                beschreibungAkt = view.tableAktivitaet.getValueAt(row, 0);
                nameAkt = view.tableAktivitaet.getValueAt(row, 1);
                zeitraumAkt = view.tableAktivitaet.getValueAt(row, 2);
            }

        }

        if(view.tabelleStatus.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tabelleStatus.rowAtPoint(point);
            column 	= view.tabelleStatus.columnAtPoint(point);

            popupValue = view.tabelleStatus.getValueAt(row, 0);

            System.out.println("popup Status: " + popupValue);

        }

        if(view.tableBemerkung.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tableBemerkung.rowAtPoint(point);
            column 	= view.tableBemerkung.columnAtPoint(point);

            popupValue = view.tableBemerkung.getValueAt(row, 0);

            System.out.println("popup Bemerkung: " + popupValue);

        }

        if(view.tableStudent.equals(arg0.getSource())){
            Point point = arg0.getPoint();
            int row,column;

            row 	= view.tableStudent.rowAtPoint(point);
            column 	= view.tableStudent.columnAtPoint(point);

             if(column < 2 && row >= 0){

             urzPointer = view.tableStudent.getValueAt(row, 2);
             System.out.println("popup Bemerkung: " + urzPointer);
             }

        }





    }


    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
        Object source = popupMenuEvent.getSource();

        //System.out.println("Becoming Visible");


    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
        //System.out.println("Becoming Invisible");

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
        //System.out.println("Canceled");

    }
    
     // This Function create a Pdf for the selected person
    private void CreatePdfDatei(){
        
                try {
                    PdfModel pdfModel = new PdfModel();

                    if(aktivitat != null && aktivitat.length > 0){
                        System.out.println("hat aktivitaet");
                        pdfModel.akt = true;
                        pdfModel.setAktivitat(aktivitat);
                    }

                    if (status != null && status.length > 0){
                        System.out.println("hat status");
                        pdfModel.sta = true;
                        pdfModel.setStatus(status);
                    }

                    if(bemerkung != null && bemerkung.length > 0){
                        System.out.println("hat bemerkung");
                        pdfModel.bem = true;
                        pdfModel.setBemerkung(bemerkung);
                    }

                    if (person != null && person.length > 0){
                        pdfModel.setPerson(person);
                    }

                    if(mob){
                        pdfModel.mob = true;
                    }

                    else
                        pdfModel.mob = false;

                    PdfView test = new PdfView(pdfModel, "createPDF");

                }
                catch (Exception e){
                    e.printStackTrace();
                }
    }
    
    
    private void AddStudentInformationToJsonObjekt(JSONObject mainObject, String object_i,String[] informationStuden, String[][] list_aktivitaten, String[] list_status, String[] list_Bermerkung   ){
        // to do Create Structure  JsonObject 
        
        
        
    }
    
    
    
    public ArrayList<ArrayList<String>> getAlleInofrmorationAllerUser (String query){
        JSONObject mainObject = new JSONObject(); 
        Functions fq = new Functions(); 
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>() ; 
        GeneralSqlAbfragen function =  new GeneralSqlAbfragen(); 
        ArrayList <String> listeStudenten = function.Select(query, "urz");
        if(listeStudenten.isEmpty()){
            return result; 
        }
        for(int i =0; i< listeStudenten.size(); i++)
        {
            String urz = listeStudenten.get(i);
            // speiechern alle Information über ein Student
            ArrayList<String> listeInformationStudent = model.ListeInformationStudent(urz); 
            String[] liste_information_student = fq.arrayListTo1DString(listeInformationStudent);
            // Liste Akitvität mit Moblität für ein Student
            ArrayList <ArrayList<String>> listeAktivitatmitMobilitat = this.getAlleAktivitaetenEinesStudent(urz);
            String [][] aktivitaten = fq.arrayListTo2DArrayVonString(listeAktivitatmitMobilitat);
            // Speichern alle Status für ein Student 
            ArrayList<ArrayList<String>> listeAlleStatus = model.ListeAlleStatus(urz);
            String [] status = fq.arrayListTo1DString( fq.arrayListe2DTo1DArrayListe(listeAlleStatus) );
            // Speichern alle Bermerkungen eines Students
            ArrayList<ArrayList<String>> listeBermerkungen = model.ListeBermerkungen(urz);
            String [] bemerkungen = fq.arrayListTo1DString( fq.arrayListe2DTo1DArrayListe(listeBermerkungen) );
            String object_i = "Student_"+i;
           this.AddStudentInformationToJsonObjekt(mainObject, object_i, liste_information_student, aktivitaten, status, bemerkungen);
            
            
        }
        return result;
    }


}
