package Controller.Update;

import Model.Update.InsertStatusModelKlein;
import View.Sonstiges.DialogFenster;
import View.Update.InsertStatusViewKlein;

import java.awt.event.*;

/**
 * Created by annelie on 23.08.16.
 */
public class InsertStatusControllerKlein implements ActionListener, KeyListener, WindowListener {

    //Modell:
    private InsertStatusModelKlein model;
    //View:
    private InsertStatusViewKlein view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    public InsertStatusControllerKlein(InsertStatusModelKlein _model, InsertStatusViewKlein _view){
        model=_model;
        view=_view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        DialogFenster dialog = new DialogFenster();

        if(source == view.enterBtn){
            String value = view.statusTxtFld.getText();
            boolean ergebnis = model.insertStatus(value);

            if(ergebnis){
                dialog.erfolgDialog(view, "Der neue Status wurde erfolgreich gespeichert", "Neuer Status");
                view.dispose();
            }

            else {
                dialog.errorDialog(view, "Es kam zu einem Fehler beim Speichern der Daten");
                String fehlerString = model.getFehlerString();

                if(!fehlerString.isEmpty()){
                    dialog.infoDialog(view, fehlerString);
                }
            }
        }

        if(source == view.zurueckBtn){
            view.dispose();
            model.closeConnection();
        }

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Object source = keyEvent.getKeyCode();

        if(source.equals(10)){

            DialogFenster dialog = new DialogFenster();
            String value = view.statusTxtFld.getText();
            boolean ergebnis = model.insertStatus(value);

            if(ergebnis){
                dialog.erfolgDialog(view, "Der neue Status wurde erfolgreich gespeichert", "Neuer Status");
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
