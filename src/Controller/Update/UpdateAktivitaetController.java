package Controller.Update;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Update.IndexUpdateAktivitaetModel;
import Model.Update.IndexUpdatePersonModel;
import Model.Update.UpdateAktivitaetModel;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Update.IndexUpdateAktivitaetView;
import View.Update.IndexUpdatePersonView;
import View.Update.UpdateAktivitaetView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.06.16.
 */
public class UpdateAktivitaetController implements ActionListener, KeyListener, WindowListener{

        //Modell:
        private UpdateAktivitaetModel model;
        //View:
        private UpdateAktivitaetView view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public UpdateAktivitaetController(UpdateAktivitaetModel _model, UpdateAktivitaetView _view)
        {
            model=_model;
            view=_view;
        }



        public void actionPerformed(ActionEvent actionEvent) {
            DialogFenster dialog = new DialogFenster();
            Object source = actionEvent.getSource();


            // eine Bestimmt aktivitaet wirt bearbeitet
            if(source == view.enterBtn ){
                String zeitraum = view.textZeitraum.getText();
                String beschreibung = view.textBeschreibung.getText();
                String aktName = view.aktName.getText();
                String aktNameAlt = model.getAktivitaet();
                String massnahme = view.massnahmeCb.getSelectedItem().toString();

                if(aktName.equals("")){
                    dialog.errorDialog(view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
                }

                else {
                    String hatMassnahme = model.returnMassnahmeVonAktivitaet(aktNameAlt);
                    System.out.println("massnahme: " + hatMassnahme + " Der Aktivitaet " + aktNameAlt);

                    // Die Aktivitaet HAT MASSNAHME (Eintrag in m_a)
                    if(!hatMassnahme.equals("")){

                        if(massnahme.equals("")){
                            dialog.errorDialog(view, "Die Aktivität benötigt eine Massnahme. Bitte überprüfen Sie Ihre Eingabe");
                        }

                        else {
                            boolean ergebnis = model.updateAktivitaet(zeitraum, beschreibung, aktName, aktNameAlt);
                            boolean ergebnis2 = model.updateMassnahme(aktName, massnahme);

                            if (ergebnis && ergebnis2){
                                dialog.erfolgDialog(view, "Die Daten wurden erfolgreich bearbeitet", "Aktivität ändern");
                                view.setVisible(false);
                                model.closeConnection();
                            }

                            else {
                                dialog.errorDialog(view, "Es kam zu einem Fehler bei der Bearbeitung der Daten");
                                String fehlerString = model.getFehlerString();

                                if(!fehlerString.isEmpty()){
                                    dialog.infoDialog(view, fehlerString);
                                }
                            }
                        }


                    }

                    // Die Aktivitaet hat KEINE MASSNAHME (Eintrag in m_a), insert anstatt update
                    else {

                        if(massnahme.equals("")){
                            dialog.errorDialog(view, "Die Massnahme einer Aktivität kann nicht leer bleiben. Bitte wählen Sie eine Massnahme aus.");
                        }

                        else {
                            boolean ergebnis = model.updateAktivitaet(zeitraum, beschreibung, aktName, aktNameAlt);
                            boolean ergebnis2 = model.insertMassnahme(aktName, massnahme);

                            if (ergebnis && ergebnis2){
                                dialog.erfolgDialog(view, "Die Daten wurden erfolgreich bearbeitet", "Aktivität ändern");
                                view.setVisible(false);
                            }

                            else {
                                dialog.errorDialog(view, "Es kam zu einem Fehler bei der Bearbeitung der Daten");
                                String fehlerString = model.getFehlerString();

                                if(!fehlerString.isEmpty()){
                                    dialog.infoDialog(view, fehlerString);
                                }
                            }
                        }



                    }
                }


            }

            if(actionEvent.getSource() == view.zurueckBtn ){
                view.setVisible(false);
                model.closeConnection();
            }

            /**
             * Auswahl eines Elements des Menus
             */
            if(source instanceof JMenuItem){
                JMenuItem sourceMenu = (JMenuItem)(actionEvent.getSource());
                String commandMenu = sourceMenu.getText();

                model.closeConnection();

                if(commandMenu.equals(INSERT_STUDENT)){
                    view.dispose();
                    InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Person neu einfügen");
                }

                if(commandMenu.equals(INSERT_AKT_STU)){
                    view.dispose();
                    InsertAktStudentView fensterInsertAktStu = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität einer Person eintragen");
                }

                if(commandMenu.equals(INSERT_STATUS_STUDENT)){
                    view.dispose();
                    InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank | Status einer Person eintragen");
                }

                if(commandMenu.equals(INSERT_AKT)){
                    view.dispose();
                    InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");
                }

                if(commandMenu.equals(INSERT_STATUS)){
                    view.dispose();
                    InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");
                }

                if(commandMenu.equals(SELECT_EINFACH)){
                    view.dispose();
                    SelectEinfachView fensterSucheEinfach = new SelectEinfachView(new SelectEinfachModel(), "InProTUC Datenbank | Einfache Suche");

                }

                if(commandMenu.equals(SELECT_ERWEITERT)){
                    view.dispose();
                    SelectErweitertView fensterSucheEinfach = new SelectErweitertView(new SelectErweitertModel(), "InProTUC Datenbank | Erweiterte Suche");
                }

                if(commandMenu.equals(UPDATE_STUDENT)){
                    view.dispose();
                    IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
                }

                if(commandMenu.equals(UPDATE_AKTIVITAET)){
                    view.dispose();
                    IndexUpdateAktivitaetView fensterUpdateAktivitaet = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");
                }
            }


        }

    public void keyPressed(KeyEvent e) {
        DialogFenster dialog = new DialogFenster();
        Object source = e.getKeyCode();

        if(source == 10){
            String zeitraum = view.textZeitraum.getText();
            String beschreibung = view.textBeschreibung.getText();
            String aktName = view.aktName.getText();
            String aktNameAlt = model.getAktivitaet();
            String massnahme = view.massnahmeCb.getSelectedItem().toString();

            if(aktName.equals("")){
                dialog.errorDialog(view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
            }

            else {
                String hatMassnahme = model.returnMassnahmeVonAktivitaet(aktNameAlt);

                // Die Aktivitaet HAT MASSNAHME (Eintrag in m_a)
                if(!hatMassnahme.equals("")){

                    if(massnahme.equals("")){
                        dialog.errorDialog(view, "Die Aktivität benötigt eine Massnahme. Bitte überprüfen Sie Ihre Eingabe");
                    }

                    else {
                        boolean ergebnis = model.updateAktivitaet(zeitraum, beschreibung, aktName, aktNameAlt);
                        boolean ergebnis2 = model.updateMassnahme(aktName, massnahme);

                        if (ergebnis && ergebnis2){
                            dialog.erfolgDialog(view, "Die Daten wurden erfolgreich bearbeitet", "Aktivität ändern");
                            view.setVisible(false);
                            model.closeConnection();
                        }

                        else {
                            dialog.errorDialog(view, "Es kam zu einem Fehler bei der Bearbeitung der Daten");
                            String fehlerString = model.getFehlerString();

                            if(!fehlerString.isEmpty()){
                                dialog.infoDialog(view, fehlerString);
                            }
                        }
                    }


                }

                // Die Aktivitaet hat KEINE MASSNAHME (Eintrag in m_a), insert anstatt update
                else {

                    if(massnahme.equals("")){
                        dialog.errorDialog(view, "Die Massnahme einer Aktivität kann nicht leer bleiben. Bitte wählen Sie eine Massnahme aus.");
                    }

                    else {
                        boolean ergebnis = model.updateAktivitaet(zeitraum, beschreibung, aktName, aktNameAlt);
                        boolean ergebnis2 = model.insertMassnahme(aktName, massnahme);

                        if (ergebnis && ergebnis2){
                            dialog.erfolgDialog(view, "Die Daten wurden erfolgreich bearbeitet", "Aktivität ändern");
                            view.setVisible(false);
                        }

                        else {
                            dialog.errorDialog(view, "Es kam zu einem Fehler bei der Bearbeitung der Daten");
                            String fehlerString = model.getFehlerString();

                            if(!fehlerString.isEmpty()){
                                dialog.infoDialog(view, fehlerString);
                            }
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


    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        //System.out.println("Window closed");

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
}
