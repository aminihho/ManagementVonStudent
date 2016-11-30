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
 * Created by annelie on 02.05.16.
 */
public class InsertStatusStudentController implements ActionListener,KeyListener, WindowListener {
    private InsertStatusStudentModel _model;
    private InsertStatusStudentView _view;

    //String wahlStudent, wahlStatus, wahlStatus1, wahlStatus2;
    String urz;
    int ergebnis, ergebnis1;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";



    public InsertStatusStudentController(InsertStatusStudentModel model, InsertStatusStudentView view){
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
            if(source.equals(_view.sendenBtn)){

                String student = _view.studentCb.getSelectedItem().toString();
                String status1 = _view.statusCb.getSelectedItem().toString();
                String status2 = _view.status1Cb.getSelectedItem().toString();

                DialogFenster dialog = new DialogFenster();

                if(student.equals("-")){
                    dialog.errorDialog(_view, "Bitte wählen Sie eine Person aus");
                }

                if (status1.equals("-") && status2.equals("-")){
                    dialog.errorDialog(_view, "Bitte geben Sie mindestens einen Status ein");
                }


                // Student muss angegeben wurden
                else if(!student.equals("-")){

                    //Nachname wird aus dem kompletten Namen gesplittet
                    String[] name = student.split(",");
                    String nachname = name[0];
                    //urz des Studenten wird anhand des Namens geholt
                    urz = _model.findUrz(nachname);
                    System.out.println("urz:" + urz);

                    /**
                     * Ein Insert-Statement: status1
                     */
                    if((!status1.equals("-")) && (status2.equals("-"))){

                        // Überprüfen, ob die Person den Status schon registriert hat
                        String query = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                        String[] spalten = {"urz", "status_typ"};
                        System.out.println(query);
                        ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

                        if(!wiederholung.isEmpty()){
                            dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status1+"\"");
                        }

                        else {
                            //Daten werden eingefügt
                            ergebnis = _model.insertValues(urz, status1);

                            // Insert suceed
                            if(ergebnis == 1){
                                dialog.erfolgDialog(_view, "Die neue Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                                _view.initView();
                            }

                            //Fehler beim Insert
                            else if(ergebnis != 1){
                                dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                                String fehlerString = _model.getErrorMessage();

                                if(!fehlerString.isEmpty()){
                                    dialog.infoDialog(_view, fehlerString);
                                }
                            }
                        }




                    }

                    /**
                     * Ein Insert-Statement: status2
                     */
                    if((status1.equals("-")) && (!status2.equals("-"))){

                        // Überprüfen, ob die Person den Status schon registriert hat
                        String query = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status2+"';";
                        String[] spalten = {"urz", "status_typ"};
                        System.out.println(query);
                        ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

                        if(!wiederholung.isEmpty()){
                            dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status2+"\"");
                        }

                        else {
                            //Daten werden eingefügt
                            ergebnis = _model.insertValues(urz, status2);

                            // Insert suceed
                            if(ergebnis == 1){
                                dialog.erfolgDialog(_view, "Die neuen Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                                _view.initView();
                            }

                            //Fehler beim Insert
                            else if(ergebnis != 1){
                                dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                                String fehlerString = _model.getErrorMessage();

                                if(!fehlerString.isEmpty()){
                                    dialog.infoDialog(_view, fehlerString);
                                }
                            }
                        }




                    }


                    /**
                     * Zwei Insert-Statements
                     */
                    if((!status1.equals("-")) && (!status2.equals("-"))){

                        // Überprüfen, ob die Person den Status1 schon registriert hat
                        String query1 = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                        String[] spalten1 = {"urz", "status_typ"};
                        System.out.println(query1);
                        ArrayList<String> wiederholung1 = _model.selectMultiple(query1, spalten1);

                        // Überprüfen, ob die Person den Status2 schon registriert hat
                        String query2 = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                        String[] spalten2 = {"urz", "status_typ"};
                        System.out.println(query2);
                        ArrayList<String> wiederholung2 = _model.selectMultiple(query2, spalten2);

                        if(!wiederholung1.isEmpty()){
                            dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status1+"\"");
                        }

                        if(!wiederholung2.isEmpty()){
                            dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status2+"\"");
                        }

                        if(status1.equals(status2) && (!status1.equals("-"))){
                            dialog.errorDialog(_view, "Es wurde zwei mal den gleichen Status für einen Student eingegeben");
                        }

                        else if((!status1.equals(status2)) && wiederholung1.isEmpty() && wiederholung2.isEmpty()){
                            //Daten werden eingefügt
                            ergebnis = _model.insertValues(urz, status1);
                            ergebnis1 = _model.insertValues(urz, status2);

                            // Insert suceed
                            if(ergebnis == 1 && ergebnis1 == 1){
                                dialog.erfolgDialog(_view, "Die neuen Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                                _view.initView();
                            }

                            //Fehler beim Insert
                            else if(ergebnis != 1){
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

            /**
             * Betätigung vom NeuerStatus-Button
             */
            if(source.equals(_view.neuerStatusBtn)){

                _view.dispose();
                _model.closeConnection();

                InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");


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
        Object source = keyEvent.getKeyCode();

        if(source.equals(KeyEvent.VK_ENTER)){

            String student = _view.studentCb.getSelectedItem().toString();
            String status1 = _view.statusCb.getSelectedItem().toString();
            String status2 = _view.status1Cb.getSelectedItem().toString();

            DialogFenster dialog = new DialogFenster();

            if(student.equals("-")){
                dialog.errorDialog(_view, "Bitte wählen Sie eine Person aus");
            }

            if (status1.equals("-") && status2.equals("-")){
                dialog.errorDialog(_view, "Bitte geben Sie mindestens einen Status ein");
            }


            // Student muss angegeben wurden
            else if(!student.equals("-")){

                //Nachname wird aus dem kompletten Namen gesplittet
                String[] name = student.split(",");
                String nachname = name[0];
                //urz des Studenten wird anhand des Namens geholt
                urz = _model.findUrz(nachname);
                System.out.println("urz:" + urz);

                /**
                 * Ein Insert-Statement: status1
                 */
                if((!status1.equals("-")) && (status2.equals("-"))){

                    // Überprüfen, ob die Person den Status schon registriert hat
                    String query = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                    String[] spalten = {"urz", "status_typ"};
                    System.out.println(query);
                    ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

                    if(!wiederholung.isEmpty()){
                        dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status1+"\"");
                    }

                    else {
                        //Daten werden eingefügt
                        ergebnis = _model.insertValues(urz, status1);

                        // Insert suceed
                        if(ergebnis == 1){
                            dialog.erfolgDialog(_view, "Die neue Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                            _view.initView();
                        }

                        //Fehler beim Insert
                        else if(ergebnis != 1){
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.isEmpty()){
                                dialog.infoDialog(_view, fehlerString);
                            }
                        }
                    }




                }

                /**
                 * Ein Insert-Statement: status2
                 */
                if((status1.equals("-")) && (!status2.equals("-"))){

                    // Überprüfen, ob die Person den Status schon registriert hat
                    String query = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status2+"';";
                    String[] spalten = {"urz", "status_typ"};
                    System.out.println(query);
                    ArrayList<String> wiederholung = _model.selectMultiple(query, spalten);

                    if(!wiederholung.isEmpty()){
                        dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status2+"\"");
                    }

                    else {
                        //Daten werden eingefügt
                        ergebnis = _model.insertValues(urz, status2);

                        // Insert suceed
                        if(ergebnis == 1){
                            dialog.erfolgDialog(_view, "Die neuen Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                            _view.initView();
                        }

                        //Fehler beim Insert
                        else if(ergebnis != 1){
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.isEmpty()){
                                dialog.infoDialog(_view, fehlerString);
                            }
                        }
                    }




                }


                /**
                 * Zwei Insert-Statements
                 */
                if((!status1.equals("-")) && (!status2.equals("-"))){

                    // Überprüfen, ob die Person den Status1 schon registriert hat
                    String query1 = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                    String[] spalten1 = {"urz", "status_typ"};
                    System.out.println(query1);
                    ArrayList<String> wiederholung1 = _model.selectMultiple(query1, spalten1);
                    // Überprüfen, ob die Person den Status2 schon registriert hat
                    String query2 = "SELECT urz, status_typ FROM student_status WHERE urz = '"+urz+"' AND status_typ = '"+status1+"';";
                    String[] spalten2 = {"urz", "status_typ"};
                    System.out.println(query2);
                    ArrayList<String> wiederholung2 = _model.selectMultiple(query2, spalten2);

                    if(!wiederholung1.isEmpty()){
                        dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status1+"\"");
                    }

                    if(!wiederholung2.isEmpty()){
                        dialog.errorDialog(_view, "Die angegebene Person hat bereits den Status \""+status2+"\"");
                    }

                    if(status1.equals(status2) && (!status1.equals("-"))){
                        dialog.errorDialog(_view, "Es wurde zwei mal den gleichen Status für einen Student eingegeben");
                    }

                    else if((!status1.equals(status2)) && wiederholung1.isEmpty() && wiederholung2.isEmpty()){
                        //Daten werden eingefügt
                        ergebnis = _model.insertValues(urz, status1);
                        ergebnis1 = _model.insertValues(urz, status2);

                        // Insert suceed
                        if(ergebnis == 1 && ergebnis1 == 1){
                            dialog.erfolgDialog(_view, "Die neuen Daten wurden erfolgreich gespeichert", "Status einer Person hinzufügen");
                            _view.initView();
                        }

                        //Fehler beim Insert
                        else if(ergebnis != 1){
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
