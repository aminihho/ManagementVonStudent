package Controller.Update;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;
import Model.Update.*;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Update.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by annelie on 20.08.16.
 */
public class IndexUpdateMassnahmeController implements ActionListener, KeyListener,MouseListener, WindowListener {


    //Modell:
    private IndexUpdateMassnahmeModel model;
    //View:
    private IndexUpdateMassnahmeView view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    public IndexUpdateMassnahmeController(IndexUpdateMassnahmeModel _model, IndexUpdateMassnahmeView _view)
    {
        model=_model;
        view=_view;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        DialogFenster dialog = new DialogFenster();

        //  Neue Aktivitäten hinzufuegen
        int row = view.massnahmeTabelle.getSelectedRow();
        if (row < 0 )
            row = 0;

        if (source instanceof JButton){
            /**
             * Eine Aktivität ändern
             */
            if(source == view.updateBtn){
                //Daten holen um im TextField darzustellen
                UpdateMassnahmeModel modelaktivitat = new UpdateMassnahmeModel();
                modelaktivitat.setMassnahme(view.massnahmeTabelle.getValueAt(row, 0).toString());

                UpdateMassnahmeView login =new UpdateMassnahmeView(modelaktivitat,"InProTUC Datenbank | Maßnahme ändern");
                initView();
            }

            if (source == view.neuBtn){
                InsertMassnahmeView neu = new InsertMassnahmeView(new InsertMassnahmeModel(), "InProTUC Datenbank | Neue Maßnahme");
                initView();

            }

            if(source == view.refreshBtn){
                initView();
            }

            if(source == view.zurueckBtn){
                view.dispose();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            if(source == view.loschenBtn){

                int response = dialog.dialogFensterFragen(view, "Möchten Sie die ausgewählte Maßnahme wirklich löschen?", "Maßnahme löschen");
                String value  = view.massnahmeTabelle.getValueAt(row, 0).toString();

                System.out.println("value" + value);

                if(response == 0){
                    boolean ergebnis = model.deleteMassnahme(value);

                    if (ergebnis){
                        dialog.erfolgDialog(view , "Die Daten wurden erfolgreich gelöscht", "Maßnahme löschen");
                        initView();
                    }

                    else {
                        dialog.errorDialog(view, "Es kam zu einem Fehler beim Löschen der Daten");
                        String fehlerString = model.returnFehlerString();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(view, fehlerString);
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

                view.dispose();
                model.closeConnection();
                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Student neu einfügen");
            }

            if(commandMenu.equals(INSERT_AKT_STU)){
                view.dispose();
                model.closeConnection();
                InsertAktStudentView fensterInsertAktStu = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_STATUS_STUDENT)){
                view.dispose();
                model.closeConnection();
                InsertStatusStudentView fensterInsertStatusStu = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank | Status einer Person eintragen");
            }

            if(commandMenu.equals(INSERT_AKT)){
                view.dispose();
                model.closeConnection();
                InsertAktivitaetView fensterInsertAkt = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität neu einfügen");
            }

            if(commandMenu.equals(INSERT_STATUS)){
                view.dispose();
                model.closeConnection();
                InsertStatusView fensterInsertStatus = new InsertStatusView(new InsertStatusModel(), "InProTUC Datenbank | Status neu einfügen");
            }

            if(commandMenu.equals(INSERT_BEMERKUNG)){
                view.dispose();
                model.closeConnection();
                InsertBemStudentView fensterInsertBem = new InsertBemStudentView(new InsertBemStudentModel(), "InProTUC Datenbank | Bemerkung zur Person eintragen");

            }

            if(commandMenu.equals(SELECT_EINFACH)){
                view.dispose();
                model.closeConnection();
                SelectEinfachView fensterSucheEinfach = new SelectEinfachView(new SelectEinfachModel(), "InProTUC Datenbank | Einfache Suche");

            }

            if(commandMenu.equals(SELECT_ERWEITERT)){
                view.dispose();
                model.closeConnection();
                SelectErweitertView fensterSucheEinfach = new SelectErweitertView(new SelectErweitertModel(), "InProTUC Datenbank | Erweiterte Suche");
            }

            if(commandMenu.equals(UPDATE_STUDENT)){
                view.dispose();
                model.closeConnection();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            if(commandMenu.equals(UPDATE_AKTIVITAET)){
                view.dispose();
                model.closeConnection();
                IndexUpdateAktivitaetView fensterUpdateAktivitaet = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "InProTUC Datenbank | Aktivität ändern");
            }

            if(commandMenu.equals(UPDATE_STATUS)){
                view.dispose();
                model.closeConnection();
                IndexUpdateStatusView fenster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "InProTUC Datenbank | Status ändern" );
            }

            if(commandMenu.equals(UPDATE_MASSNAHME)){
                view.dispose();
                model.closeConnection();
                IndexUpdateMassnahmeView fensterUpdateAktivitaet = new IndexUpdateMassnahmeView(new IndexUpdateMassnahmeModel(), "InProTUC Datenbank | Maßnahme ändern");
            }
        }

    }

    private void  initView(){
        Functions function = new Functions();

        String[] titleMassnahme = new String[]{
                "Maßnahme"
        };

        String[][] dataAktivitat = function.arrayListTo2DArrayVonString(model.returnAllMassnahmeArrayList());
        view.defultModelAktivitat.setDataVector(dataAktivitat, titleMassnahme);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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
