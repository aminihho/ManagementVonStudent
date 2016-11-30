package Controller.Update;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
import Model.Sonstiges.GeneralSqlAbfragen;
import Model.Sonstiges.IndexModel;
import Model.Update.*;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.DialogFenster;
import View.Sonstiges.IndexView;
import View.Update.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.06.16.
 */
public class IndexUpdateAktivitaetController implements ActionListener, KeyListener,MouseListener, WindowListener {

    //Modell:
    private IndexUpdateAktivitaetModel model;
    //View:
    private IndexUpdateAktivitaetView view;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public IndexUpdateAktivitaetController(IndexUpdateAktivitaetModel _model, IndexUpdateAktivitaetView _view)
    {
        model=_model;
        view=_view;
    }


    public void keyPressed(KeyEvent e) {


    }

    public void keyReleased(KeyEvent e) {


    }

    public void keyTyped(KeyEvent e) {


    }

    public void actionPerformed(ActionEvent arg0) {
        DialogFenster dialog = new DialogFenster();

        Object source = arg0.getSource();

        //  Neue Aktivitäten hinzufuegen
        int row = view.tabelAktivitat.getSelectedRow();
        if (row < 0 )
            row = 0;

        /**
         * Eine Aktivität ändern
         */
        if(source == view.btnupdate){
            UpdateAktivitaetModel modelaktivitat = new UpdateAktivitaetModel();

            modelaktivitat.setBeschreibung(view.tabelAktivitat.getValueAt(row, 0).toString());
            modelaktivitat.setAktivitaename( view.tabelAktivitat.getValueAt(row, 1).toString());
            modelaktivitat.setZeitramum(view.tabelAktivitat.getValueAt(row, 2).toString());
            modelaktivitat.setMassnahme(view.tabelAktivitat.getValueAt(row, 3));


            UpdateAktivitaetView login =new UpdateAktivitaetView(modelaktivitat,"InProTUC Datenbank | Aktivität ändern");
        }

        /**
         * Eine Aktivität löschen
         */
        if(source == view.btnDelet){

            //  GeneralSqlAbfragen sql = new GeneralSqlAbfragen();

             int response = dialog.dialogFensterFragen(view, "Möchten Sie die ausgewählte Aktivität wirklich löschen?", "Aktivität löschen");

            if(response == 0 ){

                String value  = view.tabelAktivitat.getValueAt(row, 1).toString();
                 boolean result = model.deleteAktivitaet(value);

                if(result){
                    dialog.erfolgDialog(view, "Die Aktivität wurde erfolgreich gelöscht.", "Aktivität löschen");
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

        if(source == view.btnBack){
            model.closeConnection();
            view.dispose();
            IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
        }

        if(source == view.btnNewButton){
            InsertAktivitaetViewKlein fensterInsertAkt = new InsertAktivitaetViewKlein(new InsertAktivitaetModelKlein(), "InProTUC Datenbank | Aktivität neu einfügen");
            initView();
        }

        if(source == view.refreshBtn){
            initView();
        }

        /**
         * Auswahl eines Elements des Menus
         */

        if(source instanceof JMenuItem){
            JMenuItem sourceMenu = (JMenuItem)(arg0.getSource());
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

        String[] titleAktivitat = new String[]{
                "Aktivität", "ID der Aktivität", "Zeitraum", "Maßnahme"
        };

        String[][] dataAktivitat = function.arrayListTo2DArrayVonString(model.ListeAllerAktivitatenMitMassnahme());
        view.defultModelAktivitat.setDataVector(dataAktivitat, titleAktivitat);
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