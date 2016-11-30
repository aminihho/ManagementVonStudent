package Controller.Insert;


import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.IndexModel;
import Model.Update.IndexUpdateAktivitaetModel;
import Model.Update.IndexUpdateMassnahmeModel;
import Model.Update.IndexUpdatePersonModel;
import Model.Update.IndexUpdateStatusModel;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Sonstiges.IndexView;
import View.Update.IndexUpdateAktivitaetView;
import View.Update.IndexUpdateMassnahmeView;
import View.Update.IndexUpdatePersonView;
import View.Update.IndexUpdateStatusView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by annelie on 23.04.16.
 */

public class InsertAktStudentController implements ActionListener, KeyListener, WindowListener{

    private InsertAktStudentModel _model;
    private InsertAktStudentView _view;
    boolean mob = false;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public InsertAktStudentController(InsertAktStudentModel model, InsertAktStudentView view){
        this._model = model;
        this._view = view;

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (source instanceof JButton){

            /**
             * Betätigung vom Senden-Button
             */
            if(source.equals(_view.senden)){

                DialogFenster dialog = new DialogFenster();

                // Urz der Person wird gesucht
                String studentName = (String) _view.studentCb.getSelectedItem();
                String[] name = studentName.split(",");
                String nachname = name[0];
                String urz = _model.findUrz(nachname);

                // die ID von m_a wird gesucht (mit dem ID der Aktivitaet)
                String aktivitaetBeschreibung = (String) _view.aktivitaetCb.getSelectedItem();//get the selected item
                String[] aktivitaet = aktivitaetBeschreibung.split("–");
                String nameAkt = aktivitaet[1];
                nameAkt = nameAkt.replaceAll("\\s+","");
                int id_m_a = _model.findId_m_a(nameAkt);

                String query = "SELECT urz, id_m_a FROM s_m_a WHERE urz = '"+urz+"' AND id_m_a = '"+id_m_a+"';";
                String[] spalten = {"urz", "id_m_a"};
                System.out.println(query);
                ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

                // Falls die Aktivität nicht in m_a eingetragen wurde kann sie nicht in s_m_a hinzugefügt werden
                if(id_m_a == 0){
                    dialog.errorDialog(_view, "Der ausgewählten Aktivität wurde keine Maßnahme zugeornet. Bitte ändern Sie zuerst die Aktivität");
                }

                if(!wiederholung.isEmpty()){
                    dialog.errorDialog(_view, "Die ausgewählte Person ist bereits für die angegebenen Aktivität registriert");
                }

                else if(wiederholung.isEmpty() && id_m_a != 0){
                    //Die Daten werden ans Model geschickt
                    int ergebnis = _model.insertValues(urz, id_m_a);

                    if(!mob){
                        //Eingabe erfolgreich
                        if (ergebnis == 1) {
                            dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Aktivität einer Person eintragen");
                            initView();
                        }

                        //Eingabe nicht erfolgreich
                        else if (ergebnis == -1) {
                            dialog.errorDialog(_view, "Beim Einfügen ist ein Fehler aufgetreten");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.equals("")){
                                dialog.infoDialog(_view, fehlerString);
                            }

                        }
                    }

                    /**
                     * Falls es sich um eine Aktivität der Art Mobilität handelt, wird
                     * eine zusätzliche Eingabe in der Datenbank gemacht
                     */
                    if(mob){
                        String art = _view.artTextField.getText();
                        int id_s_m_a = _model.findId_s_m_a(urz, id_m_a);
                        String durchfuehrung;

                        if(_view.jaRBtn.isSelected()){
                            durchfuehrung = "ja";
                        }
                        else
                            durchfuehrung = "nein";

                        int ergrbnis2 = _model.insertValuesMobilität(id_s_m_a,durchfuehrung,art);

                        //Eingabe erfolgreich
                        if (ergebnis == 1 && ergrbnis2 == 1) {
                            dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Aktivität einer Person eintragen");
                            initView();

                        }

                        //Eingabe nicht erfolgreich
                        if (ergebnis == -1 || ergrbnis2 == -1) {
                            dialog.errorDialog(_view, "Beim Einfügen ist ein Fehler aufgetreten");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.equals("")){
                                dialog.infoDialog(_view, fehlerString);
                            }

                        }


                    }
                }


            }

            /**
             * Betätigung vom Zurück-Button
             */
            if(source.equals(_view.zurueck)){

                _view.dispose();
                _model.closeConnection();

                IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            /**
             * Betätigung vom NeuerStudent-Button
             */
            if(source.equals(_view.neuerStudentbtn)){

                _view.dispose();
                _model.closeConnection();

                InsertStudentVIew fensterNeuStu = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Student neu einfügen");

            }

            /**
             * Betätigung vom NeueAktivität-Button
             */
            if(source.equals(_view.neueAktivitaetbtn)){

                _view.dispose();
                _model.closeConnection();

                InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");


            }
        }

        /**
         * Auswahl Combo Box
         */
        if (source instanceof JComboBox){

            String a = (String) _view.aktivitaetCb.getSelectedItem();//get the selected item
            String[] aktivitaet = a.split("–");
            String nameAkt = aktivitaet[1];
            System.out.println(nameAkt.substring(1, 3));

            if(nameAkt.substring(1, 3).equals("M_"))
                _view.bool = 1;

            else
            _view.bool = 0;


            if(_view.bool == 1){
                System.out.println("Mob einschlaten");
                mob = true;
                mobVerwalten(true);
            }

            else{
                System.out.println("Mob ausschlaten");
                mob = false;
                mobVerwalten(false);
            }

        }

        /**
         * Auswahl eines Elements des Menus
         */
        if(source instanceof JMenuItem){
            JMenuItem sourceMenu = (JMenuItem)(actionEvent.getSource());
            String commandMenu = sourceMenu.getText();

            if(commandMenu.equals(INSERT_STUDENT)){

                _view.dispose();
                _model.closeConnection();
                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Student neu einfügen");
            }

            if(commandMenu.equals(INSERT_AKT_STU)){
                _view.dispose();
                _model.closeConnection();
                InsertAktStudentView fensterInsertAktStu = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_STATUS_STUDENT)){
                _view.dispose();
                _model.closeConnection();
                InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank | Status einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_AKT)){
                _view.dispose();
                _model.closeConnection();
                InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");
            }

            if(commandMenu.equals(INSERT_STATUS)){
                _view.dispose();
                _model.closeConnection();
                InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");
            }

            if(commandMenu.equals(INSERT_BEMERKUNG)){
                _view.dispose();
                _model.closeConnection();
                InsertBemStudentView fensterInsertBem = new InsertBemStudentView(new InsertBemStudentModel(), "InProTUC Datenbank | Bemerkung zur Person eintragen");

            }

            if(commandMenu.equals(SELECT_EINFACH)){
                _view.dispose();
                _model.closeConnection();
                SelectEinfachView fensterSucheEinfach = new SelectEinfachView(new SelectEinfachModel(), "InProTUC Datenbank | Einfache Suche");
            }

            if(commandMenu.equals(SELECT_ERWEITERT)){
                _view.dispose();
                _model.closeConnection();
                SelectErweitertView fensterSucheEinfach = new SelectErweitertView(new SelectErweitertModel(), "InProTUC Datenbank | Erweiterte Suche");
            }

            if(commandMenu.equals(UPDATE_STUDENT)){
                _view.dispose();
                _model.closeConnection();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            if(commandMenu.equals(UPDATE_AKTIVITAET)){
                _view.dispose();
                _model.closeConnection();
                IndexUpdateAktivitaetView fensterUpdateAktivitaet = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");
            }

            if(commandMenu.equals(UPDATE_STATUS)){
                _view.dispose();
                _model.closeConnection();
                IndexUpdateStatusView fenster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "InProTUC Datenbank | Status ändern" );
            }

            if(commandMenu.equals(UPDATE_MASSNAHME)){
                _view.dispose();
                _model.closeConnection();
                IndexUpdateMassnahmeView fensterUpdateAktivitaet = new IndexUpdateMassnahmeView(new IndexUpdateMassnahmeModel(), "InProTUC Datenbank | Maßnahme ändern");
            }
        }

    }

    public void initView(){
        _view.studentCb.setSelectedItem(_model.defaultObject);
        _view.aktivitaetCb.setSelectedIndex(0);
        _view.artTextField.setText("");
        _view.groupDurchfuehrungRBtn.clearSelection();
    }

    public void mobVerwalten(boolean mob){
        _view.controlPanel1.setEnabled(mob);
        _view.artLbl.setEnabled(mob);
        _view.durchfuehrungLbl.setEnabled(mob);
        _view.jaLbl.setEnabled(mob);
        _view.neinLbl.setEnabled(mob);
        _view.artTextField.setEditable(mob);
        _view.jaRBtn.setVisible(mob);
        _view.neinRBtn.setVisible(mob);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object keycode = keyEvent.getKeyCode();

        if(keycode.equals(10)){
            DialogFenster dialog = new DialogFenster();

            // Urz der Person wird gesucht
            String studentName = (String) _view.studentCb.getSelectedItem();
            String[] name = studentName.split(",");
            String nachname = name[0];
            String urz = _model.findUrz(nachname);

            // die ID von m_a wird gesucht (mit dem ID der Aktivitaet)
            String aktivitaetBeschreibung = (String) _view.aktivitaetCb.getSelectedItem();//get the selected item
            String[] aktivitaet = aktivitaetBeschreibung.split("–");
            String nameAkt = aktivitaet[1];
            nameAkt = nameAkt.replaceAll("\\s+","");
            int id_m_a = _model.findId_m_a(nameAkt);

            // Überprüfen ob Person schon Aktivität hat
            String query = "SELECT urz, id_m_a FROM s_m_a WHERE urz = '"+urz+"' AND id_m_a = '"+id_m_a+"';";
            String[] spalten = {"urz", "id_m_a"};
            System.out.println(query);
            ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

            // Falls die Aktivität nicht in m_a eingetragen wurde kann sie nicht in s_m_a hinzugefügt werden
            if(id_m_a == 0){
                dialog.errorDialog(_view, "Der ausgewählten Aktivität wurde keine Maßnahme zugeornet. Bitte ändern Sie zuerst die Aktivität");
            }

            if(!wiederholung.isEmpty()){
                dialog.errorDialog(_view, "Die ausgewählte Person ist bereits für die Aktivität \""+aktivitaetBeschreibung+"\" registriert");
            }

            else if(wiederholung.isEmpty() && id_m_a != 0){
                //Die Daten werden ans Model geschickt
                int ergebnis = _model.insertValues(urz, id_m_a);

                if(!mob){
                    //Eingabe erfolgreich
                    if (ergebnis == 1) {
                        dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Aktivität einer Person eintragen");
                        initView();
                    }

                    //Eingabe nicht erfolgreich
                    else if (ergebnis == -1) {
                        dialog.errorDialog(_view, "Beim Einfügen ist ein Fehler aufgetreten");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.equals("")){
                            dialog.infoDialog(_view, fehlerString);
                        }

                    }
                }

                /**
                 * Falls es sich um eine Aktivität der Art Mobilität handelt, wird
                 * eine zusätzliche Eingabe in der Datenbank gemacht
                 */
                if(mob){
                    String art = _view.artTextField.getText();
                    int id_s_m_a = _model.findId_s_m_a(urz, id_m_a);
                    String durchfuehrung;

                    if(_view.jaRBtn.isSelected()){
                        durchfuehrung = "ja";
                    }
                    else
                        durchfuehrung = "nein";

                    int ergrbnis2 = _model.insertValuesMobilität(id_s_m_a,durchfuehrung,art);

                    //Eingabe erfolgreich
                    if (ergebnis == 1 && ergrbnis2 == 1) {
                        dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Aktivität einer Person eintragen");
                        initView();

                    }

                    //Eingabe nicht erfolgreich
                    if (ergebnis == -1 || ergrbnis2 == -1) {
                        dialog.errorDialog(_view, "Beim Einfügen ist ein Fehler aufgetreten");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.equals("")){
                            dialog.infoDialog(_view, fehlerString);
                        }

                    }


                }
            }


        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        _model.closeConnection();

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
}
