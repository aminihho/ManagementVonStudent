package Controller.Insert;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
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
 * Created by annelie on 02.05.16.
 */
public class InsertStatusController implements ActionListener,KeyListener, WindowListener {

    private InsertStatusModel _model;
    private InsertStatusView _view;
    //String statusEingabe, statusStudentEingabe, studentWahl;
    String urz;
    public int ergebnis, ergebnis1, ergebnis2;
    public boolean cbActivated = false;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";



    public InsertStatusController(InsertStatusModel model, InsertStatusView view){
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

            //senden 1
            if(source.equals(_view.sendenBtn)) {

                DialogFenster dialog = new DialogFenster();
                Functions funktion = new Functions();

                String status1Value = _view.statusTextField.getText();
                String query = "SELECT status_typ FROM status;";
                ArrayList<String> listeStatus = _model.select(query, "status_typ");
                boolean wiederholung = funktion.exists(status1Value, listeStatus);

                if(wiederholung){
                    dialog.errorDialog(_view, "Der Status existiert bereits");
                }

                if(status1Value.equals("")){
                    dialog.errorDialog(_view, "Sie haben keinen neuen Status eingegeben");
                }

                else if (!status1Value.equals("") && (!wiederholung)) {

                    ergebnis = _model.insertValuesInStatus(status1Value);

                    // Insert suceed
                    if (ergebnis == 1) {
                        dialog.erfolgDialog(_view, "Der neue Status wurde erfolgreich gespeichert", "Neuer Status");
                        _view.initView();
                    }

                    else {
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(_view, fehlerString);
                        }
                    }

                }



            }

            //senden 2
            if(source.equals(_view.sendenBtn1)) {

                DialogFenster dialog = new DialogFenster();
                Functions funktion = new Functions();

                String studentEingabe = _view.studentCb.getSelectedItem().toString();
                String statusStudentEingabe = _view.statusTextField1.getText();
                // Überprüfen, ob Status schon existier
                String query = "SELECT status_typ FROM status;";
                ArrayList<String> listeStatus = _model.select(query, "status_typ");
                boolean wiederholung = funktion.exists(statusStudentEingabe, listeStatus);


                if(wiederholung){
                    dialog.errorDialog(_view, "Der Status existiert bereits");
                }

                if(studentEingabe.equals("-")){
                    dialog.errorDialog(_view, "Sie haben keine Person ausgewählt");
                }

                if(statusStudentEingabe.equals("")){
                    dialog.errorDialog(_view, "Sie haben keinen neuen Status eingegeben");

                }

                else if ((!studentEingabe.equals("-")) && (!statusStudentEingabe.equals("")) && (!wiederholung)){

                    //Nachname wird aus dem kompletten Namen gesplittet
                    String[] name = studentEingabe.split(",");
                    String nachname = name[0];
                    //urz des Studenten wird anhand des Namens geholt
                    urz = _model.findUrz(nachname);
                    System.out.println("urz:" + urz);

                    ergebnis1 = _model.insertValuesInStatus(statusStudentEingabe);
                    ergebnis2 = _model.insertValuesInStudent_status(urz, statusStudentEingabe);


                    // Insert suceed
                    if(ergebnis1 == 1 && ergebnis2 == 1){
                        dialog.erfolgDialog(_view, "Die neue Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                        _view.initView();
                    }

                    //Fehler beim Insert
                    else{
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(_view, fehlerString);
                        }
                    }

                }




            }

            /**
             * Betätigung vom Zurück-Button
             */
            if(source.equals(_view.zurueckBtn)){

                _view.dispose();
                _model.closeConnection();

                IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object source = keyEvent.getSource();
        Object keyCode = keyEvent.getKeyCode();

        //senden 1
        if(source.equals(_view.statusTextField)){

            if(keyCode.equals(KeyEvent.VK_ENTER)){
                DialogFenster dialog = new DialogFenster();
                Functions funktion = new Functions();

                String status1Value = _view.statusTextField.getText();
                String query = "SELECT status_typ FROM status;";
                ArrayList<String> listeStatus = _model.select(query, "status_typ");
                boolean wiederholung = funktion.exists(status1Value, listeStatus);

                if(wiederholung){
                    dialog.errorDialog(_view, "Der Status existiert bereits");
                }

                if(status1Value.equals("")){
                    dialog.errorDialog(_view, "Sie haben keinen neuen Status eingegeben");
                }

                else if (!status1Value.equals("") && (!wiederholung)) {

                    ergebnis = _model.insertValuesInStatus(status1Value);

                    // Insert suceed
                    if (ergebnis == 1) {
                        dialog.erfolgDialog(_view, "Der neue Status wurde erfolgreich gespeichert", "Neuer Status");
                        _view.initView();
                    }

                    else {
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(_view, fehlerString);
                        }
                    }

                }


            }
        }

        //senden 2
        if(source.equals(_view.statusTextField1) || source.equals(_view.studentCb)){

            if(keyCode.equals(KeyEvent.VK_ENTER)){
                DialogFenster dialog = new DialogFenster();
                Functions funktion = new Functions();

                String studentEingabe = _view.studentCb.getSelectedItem().toString();
                String statusStudentEingabe = _view.statusTextField1.getText();
                // Überprüfen, ob Status schon existier
                String query = "SELECT status_typ FROM status;";
                ArrayList<String> listeStatus = _model.select(query, "status_typ");
                boolean wiederholung = funktion.exists(statusStudentEingabe, listeStatus);


                if(wiederholung){
                    dialog.errorDialog(_view, "Der Status existiert bereits");
                }

                if(studentEingabe.equals("-")){
                    dialog.errorDialog(_view, "Sie haben keine Person ausgewählt");
                }

                if(statusStudentEingabe.equals("")){
                    dialog.errorDialog(_view, "Sie haben keinen neuen Status eingegeben");

                }

                else if ((!studentEingabe.equals("-")) && (!statusStudentEingabe.equals("")) && (!wiederholung)){

                    //Nachname wird aus dem kompletten Namen gesplittet
                    String[] name = studentEingabe.split(",");
                    String nachname = name[0];
                    //urz des Studenten wird anhand des Namens geholt
                    urz = _model.findUrz(nachname);
                    System.out.println("urz:" + urz);

                    ergebnis1 = _model.insertValuesInStatus(statusStudentEingabe);
                    ergebnis2 = _model.insertValuesInStudent_status(urz, statusStudentEingabe);


                    // Insert suceed
                    if(ergebnis1 == 1 && ergebnis2 == 1){
                        dialog.erfolgDialog(_view, "Die neue Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                        _view.initView();
                    }

                    //Fehler beim Insert
                    else{
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.isEmpty()){
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
