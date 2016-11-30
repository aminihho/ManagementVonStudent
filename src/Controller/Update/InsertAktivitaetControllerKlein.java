package Controller.Update;

import Model.Update.InsertAktivitaetModelKlein;
import Model.Update.InsertMassnahmeModel;
import View.Sonstiges.DialogFenster;
import View.Update.InsertAktivitaetViewKlein;

import java.awt.event.*;

/**
 * Created by annelie on 23.08.16.
 */
public class InsertAktivitaetControllerKlein implements ActionListener, KeyListener, WindowListener {

    //Modell:
    private InsertAktivitaetModelKlein model;
    //View:
    private InsertAktivitaetViewKlein view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    public InsertAktivitaetControllerKlein(InsertAktivitaetModelKlein _model, InsertAktivitaetViewKlein _view){
        model=_model;
        view=_view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        DialogFenster dialog = new DialogFenster();

        if(source == view.enterBtn){

            String id = view.idTxtFld.getText();
            String name = view.nameTxtFld.getText();
            String zeitraum = view.zeitraumTxtFld.getText();
            String massnahme = view.massnahmeCb.getSelectedItem().toString();

            //Wenn die ID leer ist
            if(id.equals("")){
                dialog.errorDialog(view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
            }

            //Wenn die Massnahme leer ist
            if(massnahme.equals("-")){
                dialog.errorDialog(view, "Die Aktivität benötigt eine Massnahme. Bitte überprüfen Sie Ihre Eingabe");
            }

            else if((!massnahme.equals("-")) && (!id.equals(""))){
                boolean ergebnis = model.insertAktivitaet(id, name, zeitraum);
                boolean ergebnis2 = model.insertMassnahme(id, massnahme);

                if(ergebnis && ergebnis2){
                    dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                    view.dispose();
                    model.closeConnection();
                }

                else {
                    dialog.errorDialog(view, "Es kam zu einem Fehler beim Speichern der Daten");
                    String fehlerString = model.getFehlerString();

                    if(!fehlerString.isEmpty()){
                        dialog.infoDialog(view, fehlerString);
                    }
                }

            }


        }

        if(source == view.zurueckBtn){
            view.dispose();
            model.closeConnection();
        }

    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object source = keyEvent.getKeyCode();
        DialogFenster dialog = new DialogFenster();

        //System.out.println(source);

        if(source.equals(10)){
            String id = view.idTxtFld.getText();
            String name = view.nameTxtFld.getText();
            String zeitraum = view.zeitraumTxtFld.getText();
            String massnahme = view.massnahmeCb.getSelectedItem().toString();

            //Wenn die ID leer ist
            if(id.equals("")){
                dialog.errorDialog(view, "Die Aktivität benötigt eine ID. Bitte überprüfen Sie Ihre Eingabe");
            }

            //Wenn die Massnahme leer ist
            if(massnahme.equals("-")){
                dialog.errorDialog(view, "Die Aktivität benötigt eine Massnahme. Bitte überprüfen Sie Ihre Eingabe");
            }

            else if((!massnahme.equals("-")) && (!id.equals(""))){
                boolean ergebnis = model.insertAktivitaet(id, name, zeitraum);
                boolean ergebnis2 = model.insertMassnahme(id, massnahme);

                if(ergebnis && ergebnis2){
                    dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Neue Aktivität");
                    view.dispose();
                    model.closeConnection();
                }

                else {
                    dialog.errorDialog(view, "Es kam zu einem Fehler beim Speichern der Daten");
                    dialog.infoDialog(view, model.getFehlerString());
                }

            }
        }


    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

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
}
