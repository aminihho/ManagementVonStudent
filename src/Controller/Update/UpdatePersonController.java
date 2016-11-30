package Controller.Update;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
import Model.Update.*;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Update.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Created by annelie on 13.06.16.
 */
public class UpdatePersonController implements ActionListener, KeyListener,MouseListener, WindowListener, PopupMenuListener {

    //Modell:
    private UpdatePersonModel model;
    //View:
    private UpdatePersonView view;

    public boolean mob;
    public Object popupValue = "", nameAkt = "", beschreibungAkt= "";
    public String fehlerString = "";


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    /**
     * @param _model ist der Model gehört zu diesem Klasse.
     * @param _view ist der View gehört zu diesem Klasse.
     */
    public UpdatePersonController(UpdatePersonModel _model,UpdatePersonView _view)
    {
        model=_model;
        view=_view;

    }

    public void loadTabelleAktivität(String urz){
        Functions funktion = new Functions();

        ArrayList<ArrayList<String>> list_aktivitat = model.listeAlleAktivitaeten(urz);

        if(list_aktivitat.size()>0) {
            ArrayList<String> listeAkti = funktion.arrayListe2DTo1DArrayListe(list_aktivitat);
            ArrayList<ArrayList<String>>listeAktiviZei = model.listeAktivitatmitMobilitaet(listeAkti,urz);
            String [][]aktivitat = funktion.arrayListTo2DArrayVonString(listeAktiviZei);
            mob = funktion.mobExists(aktivitat);

            if(mob){
                System.out.println("mob");

                String[] titleTabelle = {"Aktivität","Id der Aktivität","Zeitraum","Durchfürung","Art",""};
                view.setDataVector(aktivitat,view.modelAktivitaet,view.tableAktivitaet,"Aktivitat", titleTabelle);
            }

            else{
                System.out.println("not mob");

                String[] titleTabelle = {"Aktivität","Id der Aktivität","Zeitraum",""};
                view.setDataVector(aktivitat,view.modelAktivitaet,view.tableAktivitaet,"Aktivitat", titleTabelle);
            }
        }

        // Person hat KEINE Aktivitäten
        else {
            String[][] aktivitat = new String[][]{};
            String[] titleLeer = new String[]{"keine Aktivität vorhanden"};
            this.view.modelAktivitaet.setDataVector(aktivitat, titleLeer);
        }
    }

    public void loadTabelleStatus(String urz){
        Functions funktion = new Functions();
        ArrayList<ArrayList<String>> listStatus = model.listeAlleStatus(urz);

        if(listStatus.size()>0) {
            String[][] statuslist=funktion.arrayListTo2DArrayVonString(listStatus);
            String[] titleT={"Status",""};
            view.setDataVector(statuslist,view.modelStatus,view.tableStatus,"Status",titleT );

        }

        else {
            // Die Daten für das Table
            String[][] status = new String[][]{};

            // Die Column-Titles
            String[] titelstatus = new String[]{
                    "keinen Status vorhanden"
            };
            this.view.modelStatus.setDataVector(status, titelstatus);
        }
    }


    public void  reloadBemerkung(String urz){


        Functions funktion = new Functions();
        ArrayList <ArrayList<String>> listeBemerkungen = this.model.listeBemerkungen(urz);

        if(listeBemerkungen.size()>0){
            System.out.println("student hat bemerkung");

            String[][] bemerkungenData = funktion.arrayListTo2DArrayVonString(listeBemerkungen);
            String[] titleBem={"Bemerkung",""};
            view.setDataVector(bemerkungenData, view.modelBemerkung, view.tableBemerkung,"Bemerkung",titleBem );
        }
        else {
            // Die Daten für das Table
            String[][] bemerkung = new String[][]{};

            // Die Column-Titles
            String[] titelBemerkung = new String[]{
                    "keine Bemerkungen"
            };
            this.view.modelBemerkung.setDataVector(bemerkung, titelBemerkung);

        }
    }

    public void loadPersönlicheInformation(String urz){
        Functions funktion = new Functions();
        String[] dataperson = funktion.arrayListTo1DString(model.listeInformationStudent(urz));
        view.updatePersonlicheInformation(dataperson);
    }

    /**
     * Refresh eines Fensters
     */
    public void  inialilView(){

        Functions funktion =new Functions();
        String urz =model.Geturz();

        loadTabelleAktivität(urz);
        loadTabelleStatus(urz);
        loadPersönlicheInformation(urz);
        reloadBemerkung(urz);
    }


    public void actionPerformed(ActionEvent arg0) {

        Object source = arg0.getSource();

        String urz = model.Geturz();
        Functions classfunction = new Functions();

        /**
         * Auswahl eines Elements des Menus
         */
        if(source instanceof JMenuItem){
            JMenuItem sourceMenu = (JMenuItem)(arg0.getSource());
            String commandMenu = sourceMenu.getText();
            DialogFenster dialog = new DialogFenster();

            if(commandMenu.equals(INSERT_STUDENT)){

                view.dispose();
                model.closeConnection();
                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Student neu einfügen");
            }

            if(commandMenu.equals(INSERT_AKT_STU)){
                view.dispose();
                model.closeConnection();
                InsertAktStudentView fensterInsertAktStu = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_STATUS_STUDENT)){
                view.dispose();
                model.closeConnection();
                InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank | Status einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_AKT)){
                view.dispose();
                model.closeConnection();
                InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");
            }

            if(commandMenu.equals(INSERT_STATUS)){
                view.dispose();
                model.closeConnection();
                InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");
            }

            if(commandMenu.equals(INSERT_BEMERKUNG)){
                view.dispose();
                model.closeConnection();
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
                view.dispose();
                model.closeConnection();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern oder löschen");
            }

            if(commandMenu.equals(UPDATE_AKTIVITAET)){
                view.dispose();
                model.closeConnection();
                IndexUpdateAktivitaetView fensterUpdateAktivitaet = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");
            }

            if(commandMenu.equals(UPDATE_STATUS)){
                view.dispose();
                model.closeConnection();
                IndexUpdateStatusView fenster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "InProTUC Datenbank | Status ändern" );
            }

            if(commandMenu.equals(UPDATE_MASSNAHME)){
                view.dispose();
                model.closeConnection();
                IndexUpdateMassnahmeView fensterUpdateAktivitaet = new IndexUpdateMassnahmeView(new IndexUpdateMassnahmeModel(), "InProTUC Datenbank | Maßnahme ändern");
            }


            /*********************************
             * 			Status ersetzen
             *********************************/
            if(commandMenu.equals("Status ersetzen")){

                String defaultStatus = popupValue.toString();

                String message2 = "Zu ersetzen: \"" + defaultStatus + "\".\nWählen Sie einen neuen Status aus.";

                // Hier werden alle andere Status aufgerufen um als Option Ersatz zu Verfügung zu stellen
                ArrayList<String> listeStatus = model.Select("select status_typ from status order by status_typ", "status_typ");
                Object[] possibilities = listeStatus.toArray();
                String neuStatus = dialog.dialogFensterInput(view , possibilities, defaultStatus,message2,"Status ändern");

                if(!neuStatus.equals("")){
                    // Überprüfen, ob die Person den Status1 schon registriert hat
                    String query = "SELECT urz, status_typ FROM student_status WHERE urz = '"+ urz +"' AND status_typ = '"+ neuStatus +"';";
                    String[] spalten = {"urz", "status_typ"};
                    System.out.println(query);
                    ArrayList<String> wiederholung = model.selectMultiple(query, spalten);

                    if(!wiederholung.isEmpty()){
                        dialog.errorDialog(view, "Die Person hat bereits den Status \""+ neuStatus +"\"");
                    }

                    else if(wiederholung.isEmpty()){
                        // Status wird ersetz
                        String querySql="update student_status SET status_typ='"+ neuStatus +"' where urz='"+ urz +"' and status_typ='"+ defaultStatus +"'";
                        boolean statusSql = model.UpdateZeileVonTabelle(querySql);

                        if(statusSql){
                            dialog.erfolgDialog(view, "Der Status der Person wurden erfolgreich bearbeitet", "Status einer Person ändern");
                            loadTabelleStatus(urz);
                        }

                        else{
                            dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor.");
                            fehlerString = model.getFehlerString();

                            if(!fehlerString.equals("")){
                                dialog.infoDialog(view, fehlerString);
                            }

                        }
                    }
                }

                else
                    return;

            }
            /*********************************
             * 			Status Ändern
             *********************************/
            if(commandMenu.equals("Status ändern")){
                IndexUpdateStatusView fenster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "InProTUC Datenbank | Status ändern" );
            }


            /*********************************
             * 			Aktivität ersetzen
             *********************************/
            if(commandMenu.equals("Aktivität ersetzen")){

                String defaultAktivitaet = beschreibungAkt.toString();

                //Model für das Neue Fenster wird initialisiert
                UpdateAktPersModel modelAktAendern = new UpdateAktPersModel();

                //Daten zur Person und die zu veränderndeAktivität werden im Model gespeichert
                String beschAktivitaet = beschreibungAkt.toString();
                String nameAktivitaet = nameAkt.toString();

                int defaultIDAktivitaet = modelAktAendern.findId_m_a(nameAktivitaet);
                Object defaultObject = beschAktivitaet + " – " + nameAktivitaet;

                System.out.println("Aktivitaet zu ersetzen = " + defaultObject);
                System.out.println("ID der Aktivitaet = " + defaultIDAktivitaet);

                modelAktAendern.setUrz(urz);
                modelAktAendern.setDefaultObject(defaultObject);
                modelAktAendern.setDefaultIDAktivitaet(defaultIDAktivitaet);
                modelAktAendern.setDefaultNameAktivitaet(nameAktivitaet);

                // Neues Fenster wird geöffnet
                UpdateAktPersView dialogUpdate = new UpdateAktPersView(modelAktAendern, "Aktivität einer Person ändern");

            }

            /*********************************
             * 			Aktivität ändern
             *********************************/
            if(commandMenu.equals("Aktivität ändern")){

                IndexUpdateAktivitaetView aktivitaetaendern = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");

            }


            /*********************************
             * 			Bemerkung Ändern
             *********************************/
            if(commandMenu.equals("Bemerkung ersetzen")) {

                Functions funktion = new Functions();
                String alteBemerkung = popupValue.toString();

                String message2 = "Zu ersetzen: " + alteBemerkung + ".\nGeben Sie neue Werte ein.";
                String neueValue = dialog.dialogFensterInputText(view, message2, "Bemerkung ändern");

                System.out.println(alteBemerkung);
                System.out.println(neueValue);

                if(!neueValue.equals("")){
                    ArrayList<ArrayList<String>> listeBemerkungen = model.listeBemerkungen(urz);
                    ArrayList<String> listeBem = funktion.arrayListe2DTo1DArrayListe(listeBemerkungen);
                    boolean wiederholung = funktion.exists(neueValue, listeBem);

                    if (wiederholung) {
                        dialog.errorDialog(view, "Die Person hat bereits die Bemerkung \"" + neueValue + "\"");
                    }
                    else {
                        String query = "UPDATE student_bem SET bemerkung = '" + neueValue + "' WHERE bemerkung = '" + alteBemerkung + "';";
                        boolean ergebnis = model.UpdateZeileVonTabelle(query);

                        if (ergebnis) {
                            dialog.erfolgDialog(view, "Die neue Daten wurden erfolgreich gespeichert", "Bemerkung einer Person ändern");
                            reloadBemerkung(urz);
                        }
                        else {
                            dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                            fehlerString = model.getFehlerString();
                            if(!fehlerString.equals("")){
                                dialog.infoDialog(view, fehlerString);
                            }
                        }
                    }
                }

                else
                    return;
            }

        }






        /**
         * Persönliche Daten einer Person ändern
         */
        if(source == view.btnUpdate){

            DialogFenster dialog = new DialogFenster();
            Functions funktion = new Functions();
            int result = dialog.dialogFensterFragen(view, "Möchten Sie die Daten der Person wirklich ändern?", "Daten Person ändern");

            if(result==0){

                String name = view.textName.getText();
                String vorname = view.textvorname.getText();
                String urzTuc = view.textUrz.getText();
                String defaultUrzTuc = model.findUrzTuc(urz);

                String gD=view.textGDatum.getText();
                String email=view.textEmail.getText();
                String telefon=view.textTelfonne.getText();
                String fk=view.textFakult.getText();

                // Überprüfen, ob der eingegebenen Urz-Kürzel bereits existiert
                urzTuc = urzTuc.toLowerCase();
                defaultUrzTuc = defaultUrzTuc.toLowerCase();
                ArrayList<String> listeUrz = model.listeUrzTuc();
                boolean wiederholung = funktion.existsOnce(urzTuc, defaultUrzTuc, listeUrz);

                System.out.println("default urztuc: " + defaultUrzTuc);
                System.out.println("einagbe urztuc: " + urzTuc);
                System.out.println("einagbe name: " + name);
                System.out.println("einagbe vorname: " + vorname);

                if(name.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Nachnamen der Person ein");
                }

                if(vorname.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Vornamen der Person ein");
                }

                if(urzTuc.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Urz-Kürzel der Person ein");
                }

                if(wiederholung){
                    System.out.println("wiederholung urz");
                    dialog.errorDialog(view, "Der eingegebene Urz-Kürzel existiert bereits");
                }

                // Name und Vorname NOT null
                if((!name.equals(""))&&(!vorname.equals("")) && (!urzTuc.equals("")) && (!wiederholung)) {

                    String query="UPDATE student SET name= '"+name+"',vorname= '"+vorname+"', urztuc ='"+urzTuc+"', geburtsdatum= '"+gD+"',fakultaet= '"+fk+"',telefon= '"+telefon+"',email= '"+email+"' where urz='"+urz+"'";
                    System.out.println(query);

                    boolean statusAbfrage = model.UpdateZeileVonTabelle(query);

                    if(statusAbfrage){
                            dialog.erfolgDialog(view, "Die Änderungen wurden erfolgreich gespeichert", "Daten Person ändern");
                    }

                    else{
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        fehlerString = model.getFehlerString();
                        if(!fehlerString.equals("")){
                            dialog.infoDialog(view, fehlerString);
                        }
                    }
                }


            }

        }

        /**
         * Person löschen
         */
        if(source == view.btnLoeschen){

            DialogFenster dialog = new DialogFenster();
            int result = dialog.dialogFensterFragen(view, "Möchten Sie die Person mit allen ihren Aktivitäten und Status löschen?", "Daten Person ändern");

            if(result==0){
                boolean ergebnis = model.deletePerson(urz);

                if(ergebnis){
                    dialog.erfolgDialog(view, "Die Person wurde erfolgreich gelöscht", "Person löschen");
                    view.dispose();
                }

                else {
                    dialog.errorDialog(view, "Es kam zu einem Fehler beim Löschen der Daten");
                    fehlerString = model.getFehlerString();
                    if(!fehlerString.equals("")){
                        dialog.infoDialog(view, fehlerString);
                    }
                }
            }
        }


        /**
         * Aktivität einer Person löschen
         */
        if(source== view.btndelAkt){
            System.out.println("Aktivität einer Person löschen");

            int row =view.tableAktivitaet.getSelectedRow();
            String beschreibungAkt = view.tableAktivitaet.getValueAt(row, 0).toString();
            String nameAkt = view.tableAktivitaet.getValueAt(row, 1).toString();
            DialogFenster dialog = new DialogFenster();

            int res = dialog.dialogFensterFragen(view, "Möchten Sie die Aktivität " + beschreibungAkt + " – " +nameAkt+" wirklich löschen?","Aktivität einer Person löschen");

            if(res == 0){
                int id_m_a = model.findId_m_a(nameAkt);
                boolean ergebnis = model.deleteAktivitaetPerson(urz, id_m_a);

                //Löschen erfolgreich
                if(ergebnis){
                    dialog.erfolgDialog(view, "Die Aktivität der Person wurde erfolreich gelöscht", "Aktivität einer Person löschen");
                    loadTabelleAktivität(urz);
                }

                //Löschen NICHT erfolgreich
                else {
                    dialog.errorDialog(view, "Es kam ein Fehler beim Löschen der Daten vor");
                    fehlerString = model.getFehlerString();
                    if(!fehlerString.equals("")){
                        dialog.infoDialog(view, fehlerString);
                    }
                }
            }
        }

        /**
         * Status einer Person löschen
         */
        if(source == view.btndelStatus){

            int row=view.tableStatus.getSelectedRow();
            String statusPerson=view.tableStatus.getValueAt(row, 0).toString();

            DialogFenster dialog= new DialogFenster();

            int res = dialog.dialogFensterFragen(view, "Möchten den Status " + statusPerson + " wirklich löschen?","Status löschen");

            if(res==0){

                String query="DELETE FROM student_status WHERE status_typ = '" + statusPerson + "' AND urz = '" + urz + "'";
                boolean statusAbfrage = model.loeschenZeile(query);

                if(statusAbfrage){
                    dialog.erfolgDialog(view, "Der Status der Person wurde erfolgreich gelöscht", "Status einer Person löschen");
                    loadTabelleStatus(urz);
                }

                else {
                    dialog.errorDialog(view, "Es kam ein Fehler beim Löschen der Daten vor");
                    fehlerString = model.getFehlerString();
                    if(!fehlerString.equals("")){
                        dialog.infoDialog(view, fehlerString);
                    }
                }
            }
        }

        /**
         * Bemerkung einer Person löschen.
         */
        if(source == view.btndelBermerkung){

            int row=view.tableBemerkung.getSelectedRow();
            int column=0;
            String value = view.tableBemerkung.getValueAt(row, column).toString();

            DialogFenster dialog = new DialogFenster();
            int res = dialog.dialogFensterFragen(view, "Möchten Sie die Bemerkung wirklich löschen?","Bemerkung löschen");

            if(res==0){
                System.out.println("löschen: " + value);
                String query = "DELETE FROM student_bem WHERE bemerkung = '"+value+"';";
                boolean ergebnis = model.UpdateZeileVonTabelle(query);

                if(ergebnis){
                    dialog.erfolgDialog(view, "Die Bemerkung wurde erfolgreich gelöscht", "Bemerkung einer Person löschen");
                        reloadBemerkung(urz);
                }
                else{
                    dialog.errorDialog(view, "Es kam ein Fehler beim Löschen der Daten vor");
                    String fehlerString = model.getFehlerString();
                    fehlerString = model.getFehlerString();
                    if(!fehlerString.equals("")){
                        dialog.infoDialog(view, fehlerString);
                    }
                }

            }

        }

        /**
         *   züruck nach Index Page
         */
        if (source == view.btnReturn){

            view.dispose();
            model.closeConnection();
        }

        /**
         * Refresh
         */
        if(source == view.refreshBtn){
            inialilView();
        }
    }



    public void mouseClicked(MouseEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        Object keyCode = e.getKeyCode();
        String urz = model.Geturz();

        if(keyCode.equals(KeyEvent.VK_ENTER)) {

            DialogFenster dialog = new DialogFenster();
            Functions funktion = new Functions();
            int result = dialog.dialogFensterFragen(view, "Möchten Sie die Daten der Person wirklich ändern?", "Daten Person ändern");

            if(result==0){

                String name = view.textName.getText();
                String vorname = view.textvorname.getText();
                String urzTuc = view.textUrz.getText();
                String defaultUrzTuc = model.findUrzTuc(urz);

                String gD=view.textGDatum.getText();
                String email=view.textEmail.getText();
                String telefon=view.textTelfonne.getText();
                String fk=view.textFakult.getText();

                // Überprüfen, ob der eingegebenen Urz-Kürzel bereits existiert
                urzTuc = urzTuc.toLowerCase();
                defaultUrzTuc = defaultUrzTuc.toLowerCase();
                ArrayList<String> listeUrz = model.listeUrzTuc();
                boolean wiederholung = funktion.existsOnce(urzTuc, defaultUrzTuc, listeUrz);

                System.out.println("default urztuc: " + defaultUrzTuc);
                System.out.println("einagbe urztuc: " + urzTuc);
                System.out.println("einagbe name: " + name);
                System.out.println("einagbe vorname: " + vorname);

                if(name.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Nachnamen der Person ein");
                }

                if(vorname.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Vornamen der Person ein");
                }

                if(urzTuc.equals("")){
                    dialog.errorDialog(view, "Bitte geben Sie den Urz-Kürzel der Person ein");
                }

                if(wiederholung){
                    System.out.println("wiederholung urz");
                    dialog.errorDialog(view, "Der eingegebene Urz-Kürzel existiert bereits");
                }

                // Name und Vorname NOT null
                if((!name.equals(""))&&(!vorname.equals("")) && (!urzTuc.equals("")) && (!wiederholung)) {

                    String query="UPDATE student SET name= '"+name+"',vorname= '"+vorname+"', urztuc ='"+urzTuc+"', geburtsdatum= '"+gD+"',fakultaet= '"+fk+"',telefon= '"+telefon+"',email= '"+email+"' where urz='"+urz+"'";
                    System.out.println(query);

                    boolean statusAbfrage = model.UpdateZeileVonTabelle(query);

                    if(statusAbfrage){
                        dialog.erfolgDialog(view, "Die Änderungen wurden erfolgreich gespeichert", "Daten Person ändern");
                    }

                    else{
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        fehlerString = model.getFehlerString();
                        if(!fehlerString.equals("")){
                            dialog.infoDialog(view, fehlerString);
                        }
                    }
                }


            }
        }
    }

    public void keyReleased(KeyEvent e) {


    }

    public void keyTyped(KeyEvent e) {


    }


    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent arg0) {

        if(view.tableAktivitaet.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tableAktivitaet.rowAtPoint(point);
            column 	= view.tableAktivitaet.columnAtPoint(point);

            if((row>=0)&&(column>=0)){
                beschreibungAkt = view.tableAktivitaet.getValueAt(row, 0);
                nameAkt = view.tableAktivitaet.getValueAt(row, 1);

                System.out.println("popup Status: " + nameAkt);
            }
        }

        if(view.tableStatus.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tableStatus.rowAtPoint(point);
            column 	= view.tableStatus.columnAtPoint(point);

            if((row>=0)&&(column>=0)){
                popupValue = view.tableStatus.getValueAt(row, 0);

                System.out.println("popup Status: " + popupValue);
            }
        }

        if(view.tableBemerkung.equals(arg0.getSource())){

            Point point = arg0.getPoint();
            int row,column;
            row 	= view.tableBemerkung.rowAtPoint(point);
            column 	= view.tableBemerkung.columnAtPoint(point);

            if((row>=0)&&(column>=0)){
                popupValue = view.tableBemerkung.getValueAt(row, 0);

                System.out.println("popup Bemerkung: " + popupValue);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        model.closeConnection();

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {

    }
}
