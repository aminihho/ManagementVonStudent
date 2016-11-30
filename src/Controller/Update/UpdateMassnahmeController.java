package Controller.Update;

import Model.Update.UpdateMassnahmeModel;
import View.Sonstiges.DialogFenster;
import View.Update.UpdateMassnahmeView;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by annelie on 20.08.16.
 */
public class UpdateMassnahmeController implements ActionListener, KeyListener, WindowListener {

    //Modell:
    private UpdateMassnahmeModel model;
    //View:
    private UpdateMassnahmeView view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    public UpdateMassnahmeController(UpdateMassnahmeModel _model, UpdateMassnahmeView _view){
        model=_model;
        view=_view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();


            if(source == view.enterBtn){
                //System.out.println("alte massnahmme: " + model.getMassnahme());
                //System.out.println("neue massnahmme: " + view.massnahmeTxtFld.getText());

                boolean ergebnis = model.updateMassnahme(view.massnahmeTxtFld.getText());
                DialogFenster dialog = new DialogFenster();

                if(ergebnis){
                    dialog.erfolgDialog(view, "Die Maßnahme wurde erfolgreich geändert", "Maßnahme ändern");
                    view.dispose();
                }

                else {
                    dialog.errorDialog(view, "Es kam zu einem Fehler beim Ändern der Maßnahme");
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

            boolean ergebnis = model.updateMassnahme(view.massnahmeTxtFld.getText());
            DialogFenster dialog = new DialogFenster();

            if(ergebnis){
                dialog.erfolgDialog(view, "Die Maßnahme wurde erfolgreich geändert", "Maßnahme ändern");
                view.dispose();
                model.closeConnection();
            }

            else {
                dialog.errorDialog(view, "Es kam zu einem Fehler beim Ändern der Maßnahme");
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
