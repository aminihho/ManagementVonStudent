package Controller.Update;

import Model.Update.InsertMassnahmeModel;
import View.Sonstiges.DialogFenster;
import View.Update.InsertMassnahmeView;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by annelie on 22.08.16.
 */
public class InsertMassnahmeController implements ActionListener, KeyListener, WindowListener {

    //Modell:
    private InsertMassnahmeModel model;
    //View:
    private InsertMassnahmeView view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    public InsertMassnahmeController(InsertMassnahmeModel _model, InsertMassnahmeView _view){
        model=_model;
        view=_view;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        DialogFenster dialog =  new DialogFenster();


        if(source instanceof JButton){
            /**
             * Betätigung vom Enter-Button
             */
            if(source == view.enterBtn){
                String nameMassnahme = view.massnahmeTxtFld.getText();
                boolean ergebnis = model.insertMassnahme(nameMassnahme);

                if(ergebnis){
                    dialog.erfolgDialog(view, "Die neue Maßnahme wurde erfolgreich gespeichert", "Maßnahme einfügen");
                    view.dispose();
                    model.closeConnection();
                }

                else {
                    dialog.errorDialog(view, "Es kam zu einem Fehler beim Einfügen der Daten");
                    String fehlerString = model.getFehlerString();

                    if(!fehlerString.isEmpty()){
                        dialog.infoDialog(view, fehlerString);
                    }
                }


            }

            /**
             * Betätigung vom Zurück-Button
             */
            if(source == view.zurueckBtn){
                view.dispose();
                model.closeConnection();
            }
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

            String nameMassnahme = view.massnahmeTxtFld.getText();
            boolean ergebnis = model.insertMassnahme(nameMassnahme);

            if(ergebnis){
                dialog.erfolgDialog(view, "Die neue Maßnahme wurde erfolgreich gespeichert", "Maßnahme einfügen");
                view.dispose();
            }

            else {
                dialog.errorDialog(view, "Es kam zu einem Fehler beim Einfügen der Daten");
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
