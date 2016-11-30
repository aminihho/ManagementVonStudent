package Controller.Insert;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Update.IndexUpdateAktivitaetModel;
import Model.Update.IndexUpdateMassnahmeModel;
import Model.Update.IndexUpdatePersonModel;
import Model.Update.IndexUpdateStatusModel;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Update.IndexUpdateAktivitaetView;
import View.Update.IndexUpdateMassnahmeView;
import View.Update.IndexUpdatePersonView;
import View.Update.IndexUpdateStatusView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by annelie on 17.09.16.
 */
public class InsertBemStudentController  implements ActionListener,KeyListener, WindowListener {
    InsertBemStudentModel _model;
    InsertBemStudentView _view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public InsertBemStudentController(InsertBemStudentModel model, InsertBemStudentView view){
        this._model = model;
        this._view = view;
    }

    public void initView(){
        _view.studentCb.setSelectedItem(_model.defaultObject);
        _view.bemerkung1.setText("");
        _view.bemerkung2.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if(source instanceof JButton){

            /**
             * Betätigung vom Senden-Button
             */
            if(source.equals(_view.sendenBtn)){

                DialogFenster dialog = new DialogFenster();

                String studentName = _view.studentCb.getSelectedItem().toString();
                String bemerkung1 = _view.bemerkung1.getText();
                String bemerkung2 = _view.bemerkung2.getText();

                String[] urzArray = studentName.split("–");
                String urztuc = urzArray[1].replaceAll("\\s","");
                String urz = "";
                System.out.println(urztuc);


                if(urztuc.equals("")){
                    dialog.errorDialog(_view, "Es liegt kein Urz-Kürzel von der Person vor. Bitte ändern Sie zuerst die Daten der Person.");
                }
                else{
                    urz = _model.findUrz(urztuc);
                }

                if(bemerkung1.isEmpty()){
                    dialog.errorDialog(_view, "Geben Sie eine Bemerkung im ersten Feld ein.");
                }

                if((!bemerkung1.equals("")) && (!urz.equals("")) && bemerkung2.equals("")){

                    String query = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung1+"'";
                    String[] spalten = {"urz", "bemerkung"};
                    ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);
                    System.out.println(query);

                    if(!wiederholung.isEmpty()){
                        dialog.errorDialog(_view, "Die Bemerkung zur Person existiert bereits");
                    }

                    else {
                        boolean ergebnis = _model.insertValues(urz, bemerkung1);

                        if(ergebnis){
                            dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Bemerkung einer Person hinzufügen");
                            initView();
                        }

                        else {
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.equals("")){
                                dialog.infoDialog(_view, fehlerString);
                            }
                        }
                    }

                }

                if((!bemerkung1.equals("")) && (!bemerkung2.equals("")) && (!urz.equals("")) ){
                    String query1 = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung1+"'";
                    String[] spalten1 = {"urz", "bemerkung"};
                    ArrayList<String> wiederholung1 = _model.selectMultiple(query1, spalten1);

                    String query2 = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung2+"'";
                    String[] spalten2 = {"urz", "bemerkung"};
                    ArrayList<String> wiederholung2 = _model.selectMultiple(query2, spalten2);


                    if(!wiederholung1.isEmpty()){
                        dialog.errorDialog(_view, "Die Bemerkung \""+bemerkung1+"\" exitiert bereits");
                    }

                    if(!wiederholung2.isEmpty()){
                        dialog.errorDialog(_view, "Die Bemerkung \""+bemerkung2+"\" exitiert bereits");
                    }

                    else if(wiederholung1.isEmpty() && wiederholung2.isEmpty()){
                        boolean ergebnis1 = _model.insertValues(urz, bemerkung1);
                        boolean ergebnis2 = _model.insertValues(urz, bemerkung2);

                        if(ergebnis1 && ergebnis2){
                            dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Bemerkung einer Person hinzufügen");
                            initView();
                        }

                        else {
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            dialog.infoDialog(_view, _model.getErrorMessage());
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

            /**
             * Betätigung vom NeuerStudent-Button
             */
            if(source.equals(_view.neuerStudentbtn)){
                _view.dispose();
                _model.closeConnection();

                InsertStudentVIew fensterNeuStu = new InsertStudentVIew(new InsertStudentModel(), "InProTUC Datenbank | Student neu einfügen");

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
        Object keycode = keyEvent.getKeyCode();

        if(keycode.equals(KeyEvent.VK_ENTER)){
            DialogFenster dialog = new DialogFenster();

            String studentName = _view.studentCb.getSelectedItem().toString();
            String bemerkung1 = _view.bemerkung1.getText();
            String bemerkung2 = _view.bemerkung2.getText();

            String[] urzArray = studentName.split("–");
            String urztuc = urzArray[1].replaceAll("\\s","");
            String urz = "";
            System.out.println(urztuc);


            if(urztuc.equals("")){
                dialog.errorDialog(_view, "Es liegt kein Urz-Kürzel von der Person vor. Bitte ändern Sie zuerst die Daten der Person.");
            }
            else{
                urz = _model.findUrz(urztuc);
            }

            if(bemerkung1.equals("")){
                dialog.errorDialog(_view, "Geben Sie eine Bemerkung im ersten Feld ein.");
            }

            if((!bemerkung1.equals("")) && (!urz.equals("")) && bemerkung2.equals("")){

                String query = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung1+"'";
                String[] spalten = {"urz", "bemerkung"};
                ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);
                System.out.println(query);

                if(!wiederholung.isEmpty()){
                    dialog.errorDialog(_view, "Die Bemerkung zur Person exitiert bereits");
                }

                else {
                    boolean ergebnis = _model.insertValues(urz, bemerkung1);

                    if(ergebnis){
                        dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Bemerkung einer Person hinzufügen");
                        initView();
                    }

                    else {
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.equals("")){
                            dialog.infoDialog(_view, fehlerString);
                        }
                    }
                }

            }

            if((!bemerkung1.equals("")) && (!bemerkung2.equals("")) && (!urz.equals("")) ){
                String query1 = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung1+"'";
                String[] spalten1 = {"urz", "bemerkung"};
                ArrayList<String> wiederholung1 = _model.selectMultiple(query1, spalten1);

                String query2 = "SELECT urz, bemerkung from student_bem WHERE urz = '"+urz+"' AND bemerkung = '"+bemerkung2+"'";
                String[] spalten2 = {"urz", "bemerkung"};
                ArrayList<String> wiederholung2 = _model.selectMultiple(query2, spalten2);


                if(!wiederholung1.isEmpty()){
                    dialog.errorDialog(_view, "Die Bemerkung \""+bemerkung1+"\" exitiert bereits");
                }

                if(!wiederholung2.isEmpty()){
                    dialog.errorDialog(_view, "Die Bemerkung \""+bemerkung2+"\" exitiert bereits");
                }

                else if(wiederholung1.isEmpty() && wiederholung2.isEmpty()){
                    boolean ergebnis1 = _model.insertValues(urz, bemerkung1);
                    boolean ergebnis2 = _model.insertValues(urz, bemerkung2);

                    if(ergebnis1 && ergebnis2){
                        dialog.erfolgDialog(_view, "Die Daten wurden erfolgreich gespeichert", "Bemerkung einer Person hinzufügen");
                        initView();
                    }

                    else {
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
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
