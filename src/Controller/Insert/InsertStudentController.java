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
import java.util.Random;

/**
 * Created by annelie on 18.04.16.
 */
public class InsertStudentController implements ActionListener,KeyListener, WindowListener{

    private InsertStudentModel _model;
    private InsertStudentVIew _view;

    public int ergebnis;
    boolean ergebnis2 = true;

    String wahlTag, wahlMonat, wahlJahr, gebDatString;
    String nachnameString, vornameString, urzString, fakuString, telString, emailString, bemerkString;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public InsertStudentController(InsertStudentModel model, InsertStudentVIew view) {
        this._model = model;
        this._view = view;

    }

    public String cat(String a, String b){
        String ergebnis = "";
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);

        int len1 = a.length();
        int len2 = b.length();

        a = a.substring(0, a.length() - ((len1*2)/3));
        b = b.substring(0, b.length() - ((len2*2)/3));

        ergebnis = a + b + randomInt;
        ergebnis = ergebnis.toLowerCase();
        ergebnis = ergebnis.replaceAll("\\s+","");

        return ergebnis;

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        /**
         * Combo Box
         * Speichert die Asuwahl des Combobox in einer Variabel
         */
        if (source instanceof JComboBox) {

            if (actionEvent.getSource().equals(_view.tagCb)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlTag = (String) cb.getSelectedItem();
                System.out.println(wahlTag);


            }

            if (actionEvent.getSource().equals(_view.monatCb)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlMonat = (String) cb.getSelectedItem();
                System.out.println(wahlMonat);

            }

            if (actionEvent.getSource().equals(_view.jahrCb)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlJahr = (String) cb.getSelectedItem();
                System.out.println(wahlJahr);

            }

            gebDatString = wahlTag + ". " + wahlMonat + " " + wahlJahr;
            System.out.println(gebDatString);

        }


        if (source instanceof JButton) {

            /**
             * Betätigung vom Zurück-Button
             */
            if (source.equals(_view.zurueck)) {

                _view.setVisible(false);
                _model.closeConnection();
                IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            /**
             * Betätigung vom Senden-Button
             */
            if (source.equals(_view.senden)) {

                nachnameString = _view.nachnameTextField.getText();
                vornameString = _view.vornameTextField.getText();
                urzString = _view.urzTextField.getText();
                fakuString = _view.fakuTextField.getText();
                telString = _view.telTextField.getText();
                emailString = _view.emailTextField.getText();
                bemerkString = _view.bemerkungTextArea.getText();
                //Hier wird die Urz konkatiniert
                String urzDBString = cat(vornameString, nachnameString);

                DialogFenster dialog = new DialogFenster();

                if(vornameString.equals("")){
                    dialog.errorDialog(_view, "Der Vorname der Person fehlt. Bitte überprüfen Sie Ihre Eingabe");
                }

                if(nachnameString.equals("")){
                    dialog.errorDialog(_view, "Der Nachname der Person fehlt. Bitte überprüfen Sie Ihre Eingabe");
                }

                if(urzString.equals("")){
                    dialog.errorDialog(_view, "Der Urz-Kürzel der Person fehlt. Bitte überprüfen Sie Ihre Eingabe");

                }

                else if((!vornameString.equals("")) && (!nachnameString.equals("")) && (!urzString.equals(""))){
                    //Insert
                    ergebnis = _model.insertValues(nachnameString, vornameString, gebDatString, fakuString, telString, emailString, urzString, urzDBString);

                    // Insert Bemerkung falls nicht leer
                    if(!bemerkString.isEmpty()){
                        ergebnis2 = _model.insertBemerkung(urzDBString, bemerkString);
                    }

                    /**
                     * Eingabe nicht erfolgreich
                     */
                    if(ergebnis == 1 && ergebnis2){
                        dialog.erfolgDialog(_view, "Die Daten der neuen Person wurden erfolgreich gespeichert", "Neue Person");
                        _view.initView();
                    }

                    /**
                     * Eingabe erfolgreich
                     */
                    else if(ergebnis == -1){
                        dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten");
                        String fehlerString = _model.getErrorMessage();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(_view, fehlerString);
                        }
                    }
                }

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
    public void keyTyped (KeyEvent keyEvent){

    }

    @Override
    public void keyPressed (KeyEvent keyEvent){
        Object source = keyEvent.getKeyCode();

        if(source.equals(10)){
            nachnameString = _view.nachnameTextField.getText();
            vornameString = _view.vornameTextField.getText();
            urzString = _view.urzTextField.getText();
            fakuString = _view.fakuTextField.getText();
            telString = _view.telTextField.getText();
            emailString = _view.emailTextField.getText();
            bemerkString = _view.bemerkungTextArea.getText();
            //Hier wird die Urz konkatiniert
            String urzDBString = cat(vornameString, nachnameString);

            DialogFenster dialog = new DialogFenster();

            if(vornameString.equals("")){
                dialog.errorDialog(_view, "Der Vorname der Person fehlt. Bitte überprüfen Sie Ihre Eingabe");
            }

            if(nachnameString.equals("")){
                dialog.errorDialog(_view, "Der Nachname der Person fehlt. Bitte überprüfen Sie Ihre Eingabe");
            }

            else if((!vornameString.equals("")) && (!nachnameString.equals(""))){
                ergebnis = _model.insertValues(nachnameString, vornameString, gebDatString, fakuString, telString, emailString, urzString, urzDBString);

                //eingabe erfolgreich
                if(ergebnis == 1){
                    dialog.erfolgDialog(_view, "Die Daten der neuen Person wurden erfolgreich gespeichert", "Neue Person");
                    _view.initView();
                }

                //eingabe nicht erfolgreich
                else if(ergebnis == -1){
                    dialog.errorDialog(_view, "Es kam ein Fehler beim Speichern der Daten");
                    String fehlerString = _model.getErrorMessage();

                    if(!fehlerString.isEmpty()){
                        dialog.infoDialog(_view, fehlerString);
                    }
                }
            }



        }

    }

    @Override
    public void keyReleased (KeyEvent keyEvent){

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





