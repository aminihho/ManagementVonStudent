package Controller.Insert;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;
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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by annelie on 23.04.16.
 */
public class InsertAktivitaetController implements ActionListener, KeyListener, ItemListener, WindowListener {

    private InsertAktivitaetModel _model;
    private InsertAktivitaetView _view;

    //String massnahmeWahl, aktivitaetEingabe, massnahmeEingabe, beschreibungEingabe, zeitraumEingabe;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";



    public InsertAktivitaetController(InsertAktivitaetModel model, InsertAktivitaetView view){
        this._model = model;
        this._view = view;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        Object source = actionEvent.getSource();

        /**
         * Betätigung vom Senden-Button
         */
        if(source.equals(_view.btnSenden)) {

            DialogFenster dialog = new DialogFenster();
            Functions funktion = new Functions();

            // Überprüfen ob die Aktivität schon im System existiert
            String aktivitaetEingabe = _view.aktTextField.getText();
            ArrayList<String> listAktivitaet = _model.listeAktivitaet();
            boolean wiederholung = funktion.exists(aktivitaetEingabe, listAktivitaet);

            // Massnahme muss gewählt werden
            if((!_view.neueMassnahmeRBtn.isSelected()) && (!_view.bestehendeMassnahmeRBtn.isSelected())){
                dialog.infoDialog(_view, "Bitte geben Sie an, ob die Maßnahme der Aktivität eine neue oder eine bestehende ist");
            }

            if(wiederholung){
                dialog.errorDialog(_view, "Die Aktivitäts-ID existiert bereits. Bitte geben Sie eine andere ID ein");
            }

            else {
                /**
                 * Neue Massnahme
                 */
                if(_view.neueMassnahmeRBtn.isSelected()){

                    String beschreibungEingabe = _view.aktBeschreibungTxtFld.getText();
                    String zeitraumEingabe = _view.zeitraumTextField.getText();
                    // Überprüfen ob die Massnahme schon im System existiert
                    String massnahmeEingabe = _view.neueMassnahmeTextField.getText();
                    ArrayList<String> listMassnahme = _model.listeMassnahme();
                    boolean wiederholungMassnahme = funktion.exists(massnahmeEingabe, listMassnahme);

                    if(wiederholungMassnahme){
                        dialog.errorDialog(_view, "Die Massnahme exisitert bereits");
                    }

                    if(aktivitaetEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(massnahmeEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine Maßnahme. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(beschreibungEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt einen Namen. Bitte überprüfen Sie Ihre Eingabe");
                    }


                    else if ((!wiederholungMassnahme) && (!aktivitaetEingabe.equals("")) && (!massnahmeEingabe.equals("")) && (!beschreibungEingabe.equals(""))) {

                        int ergebnis = _model.insertValuesAktivtaetundMassnahme(aktivitaetEingabe, zeitraumEingabe, beschreibungEingabe, massnahmeEingabe);

                        if (ergebnis == 1) {
                            dialog.erfolgDialog(_view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                            initView();
                        }
                        else{
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            String fehlerString = _model.getErrorMessage();
                            dialog.infoDialog(_view, fehlerString);

                        }
                    }


                }

                /**
                 * Bestehende Massnahme
                 */
                if(_view.bestehendeMassnahmeRBtn.isSelected()){

                    String aktName = _view.aktTextField.getText();
                    String aktBeschreibung = _view.aktBeschreibungTxtFld.getText();
                    String zeitraum = _view.zeitraumTextField.getText();

                    String massnahme = _view.bestehendeMassnahmeCb.getSelectedItem().toString();

                    System.out.println(aktName);
                    System.out.println(aktBeschreibung);
                    System.out.println(massnahme);

                    if(aktBeschreibung.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt einen Namen. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(aktName.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(massnahme.equals("-")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine Maßnahme. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    else if (!aktName.equals("") && (!massnahme.equals("-")) && (!aktBeschreibung.equals(""))) {

                        int ergebnis = _model.insertValuesAktivtaetundAddMassnahme(aktName, zeitraum, aktBeschreibung, massnahme);

                        if (ergebnis == 1) {
                            dialog.erfolgDialog(_view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                            initView();
                        }
                        else{
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

        /**
         * Betätigung vom Zurück-Button
         */
        if(source.equals(_view.btnZurueck)){

            _view.dispose();
            _model.closeConnection();

            IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");

        }

        /**
         * Auswahl eines Elements des Menus
         */
        if(source instanceof JMenuItem){
            JMenuItem sourceMenu = (JMenuItem)(actionEvent.getSource());
            String commandMenu = sourceMenu.getText();

            //Verbindung wird geschlossen
            _model.closeConnection();

            /**
             * Weiterleitung zum ausgewählten Fenster
             */
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

        // Clear Text Field
        _view.aktTextField.setText("");
        _view.aktBeschreibungTxtFld.setText("");
        _view.zeitraumTextField.setText("");

        _view.neueMassnahmeTextField.setText("");
        _view.bestehendeMassnahmeCb.setSelectedIndex(0);

        _view.group.clearSelection();
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent){
        Object source = itemEvent.getItemSelectable();

        /**
         * Reagiert auf die Radio Buttons
         */
        if (source == _view.bestehendeMassnahmeRBtn){

            if(_view.bestehendeMassnahmeRBtn.isSelected()){
                _view.bestehendeMassnahmeCb.setEnabled(true);
            }

            else
                _view.bestehendeMassnahmeCb.setEnabled(false);
        }

        if (source == _view.neueMassnahmeRBtn){
            if (_view.neueMassnahmeRBtn.isSelected()){
                _view.neueMassnahmeTextField.setEditable(true);
                _view.bestehendeMassnahmeCb.setEnabled(false);
            }

            else
                _view.neueMassnahmeTextField.setEditable(false);


        }


    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object keyCode = keyEvent.getKeyCode();

        if(keyCode.equals(KeyEvent.VK_ENTER)){

            DialogFenster dialog = new DialogFenster();
            Functions funktion = new Functions();

            // Überprüfen ob die Aktivität schon im System existiert
            String aktivitaetEingabe = _view.aktTextField.getText();
            ArrayList<String> listAktivitaet = _model.listeAktivitaet();
            boolean wiederholung = funktion.exists(aktivitaetEingabe, listAktivitaet);

            // Massnahme muss gewählt werden
            if((!_view.neueMassnahmeRBtn.isSelected()) && (!_view.bestehendeMassnahmeRBtn.isSelected())){
                dialog.infoDialog(_view, "Bitte geben Sie an, ob die Maßnahme der Aktivität eine neue oder eine bestehende ist");
            }

            if(wiederholung){
                dialog.errorDialog(_view, "Die Aktivitäts-ID existiert bereits. Bitte geben Sie eine andere ID ein");
            }

            else {
                /**
                 * Neue Massnahme
                 */
                if(_view.neueMassnahmeRBtn.isSelected()){


                    String beschreibungEingabe = _view.aktBeschreibungTxtFld.getText();
                    String zeitraumEingabe = _view.zeitraumTextField.getText();
                    // Überprüfen ob die Massnahme schon im System existiert
                    String massnahmeEingabe = _view.neueMassnahmeTextField.getText();
                    ArrayList<String> listMassnahme = _model.listeMassnahme();
                    boolean wiederholungMassnahme = funktion.exists(massnahmeEingabe, listMassnahme);

                    if(wiederholungMassnahme){
                        System.out.println("HELLOW");
                        dialog.errorDialog(_view, "Die Massnahme exisitert bereits");
                    }

                    if(aktivitaetEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(massnahmeEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine Maßnahme. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(beschreibungEingabe.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt einen Namen. Bitte überprüfen Sie Ihre Eingabe");
                    }


                    else if ((!wiederholungMassnahme) && (!aktivitaetEingabe.equals("")) && (!massnahmeEingabe.equals("")) && (!beschreibungEingabe.equals(""))) {

                        int ergebnis = _model.insertValuesAktivtaetundMassnahme(aktivitaetEingabe, zeitraumEingabe, beschreibungEingabe, massnahmeEingabe);

                        if (ergebnis == 1) {
                            dialog.erfolgDialog(_view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                            initView();
                        }
                        else{
                            dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten vor");
                            String fehlerString = _model.getErrorMessage();

                            if(!fehlerString.equals("")){
                                dialog.infoDialog(_view, fehlerString);
                            }

                        }
                    }


                }

                /**
                 * Bestehende Massnahme
                 */
                if(_view.bestehendeMassnahmeRBtn.isSelected()){

                    String aktName = _view.aktTextField.getText();
                    String aktBeschreibung = _view.aktBeschreibungTxtFld.getText();
                    String zeitraum = _view.zeitraumTextField.getText();

                    String massnahme = _view.bestehendeMassnahmeCb.getSelectedItem().toString();

                    System.out.println(aktName);
                    System.out.println(aktBeschreibung);
                    System.out.println(massnahme);

                    if(aktBeschreibung.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt einen Namen. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(aktName.equals("")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    if(massnahme.equals("-")){
                        dialog.errorDialog(_view, "Die Aktivität benötigt eine Maßnahme. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    else if (!aktName.equals("") && (!massnahme.equals("-")) && (!aktBeschreibung.equals(""))) {

                        int ergebnis = _model.insertValuesAktivtaetundAddMassnahme(aktName, zeitraum, aktBeschreibung, massnahme);

                        if (ergebnis == 1) {
                            dialog.erfolgDialog(_view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                            initView();
                        }
                        else{
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
