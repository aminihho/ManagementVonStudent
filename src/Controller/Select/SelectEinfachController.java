package Controller.Select;

import Model.Insert.*;
import Model.Select.SelectEinfachModel;


import Model.Select.SelectErweitertModel;
import Model.Sonstiges.Functions;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by annelie on 06.05.16.
 */
public class SelectEinfachController implements ActionListener,KeyListener {
    private SelectEinfachModel _model;
    private SelectEinfachView _view;

    String[] AttributeKomplett = {"name", "vorname", "fakultaet", "", ""};
    String attribut;
    String query = "", queryUrz = "";

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    String wahlAttr, wert;

    public SelectEinfachController(SelectEinfachModel model, SelectEinfachView view){
        this._model = model;
        this._view = view;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        /**
         * Combo Box
         * Speichert die Asuwahl des Combobox in einer Variabel
         */
        if (source instanceof JComboBox) {
            Object sourceCB = actionEvent.getSource();
            Functions funktion = new Functions();

            if (sourceCB.equals(_view.attributCB)){

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlAttr = (String) cb.getSelectedItem();

                if(!wahlAttr.equals("-")){
                    _view.wertCB.setEnabled(true);
                }

                if(wahlAttr.equals("Student - Nachname")){
                    String query = "SELECT DISTINCT name FROM student ORDER BY name;";
                    ArrayList<String> listeName = _model.select(query, "name");
                    String[] nameArray = funktion.arrayListTo1DString(listeName);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB.setModel(model);
                }

                if(wahlAttr.equals("Student - Vorname")){
                    String query = "SELECT DISTINCT vorname FROM student ORDER BY vorname;";
                    ArrayList<String> listeVorname = _model.select(query, "vorname");
                    String[] vornameArray = funktion.arrayListTo1DString(listeVorname);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(vornameArray);
                    _view.wertCB.setModel(model);
                }

                if(wahlAttr.equals("Fakultät")){
                    String query = "SELECT DISTINCT fakultaet FROM student ORDER BY fakultaet;";
                    ArrayList<String> listeFaku = _model.select(query, "fakultaet");
                    String[] fakuArray = funktion.arrayListTo1DString(listeFaku);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(fakuArray);
                    _view.wertCB.setModel(model);
                }

                if(wahlAttr.equals("Status")){
                    String query = "SELECT status_typ FROM status ORDER BY status_typ;";
                    ArrayList<String> listeStatus = _model.select(query, "status_typ");
                    String[] statusArray = funktion.arrayListTo1DString(listeStatus);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(statusArray);
                    _view.wertCB.setModel(model);

                }

                if(wahlAttr.equals("Urz (TUC)")){
                    String query = "SELECT urztuc FROM student ORDER BY urztuc;";
                    ArrayList<String> listeUrz = _model.select(query, "urztuc");
                    String[] urzArray = funktion.arrayListTo1DString(listeUrz);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(urzArray);
                    _view.wertCB.setModel(model);
                }

                if(wahlAttr.equals("Aktivität - Name und ID")){
                    String query = "SELECT beschreibung, aktivitaet_name FROM aktivitaet ORDER BY beschreibung;";
                    String[] spalten = new String[]{"beschreibung", "aktivitaet_name"};
                    ArrayList<String> listeAktivitaet = _model.selectMultiple(query, spalten);
                    ArrayList<String> listeAktivitaetConcat = funktion.ConcateSpalten(2, listeAktivitaet, '–');
                    String[] aktiviArray = funktion.arrayListTo1DString(listeAktivitaetConcat);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(aktiviArray);
                    _view.wertCB.setModel(model);

                }

                if(wahlAttr.equals("Maßnahme - Name")){
                    String query = "SELECT massnahme_name FROM massnahme ORDER BY massnahme_name;";
                    ArrayList<String> listeMassnahme = _model.select(query, "massnahme_name");
                    String[] massnahmeArray = funktion.arrayListTo1DString(listeMassnahme);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(massnahmeArray);
                    _view.wertCB.setModel(model);
                }


                else if(wahlAttr.equals("-")){
                    String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB.setModel(model);
                    _view.wertCB.setEnabled(false);

                }
            }
        }

        /**
         * Betätigung vom Zurück-Button
         */
        if (source instanceof JButton) {
            if (source.equals(_view.zurueck)) {
                _view.setVisible(false);
                IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            /**
             * Betätigung vom Senden-Button
             */
            if (source.equals(_view.senden)) {

                Object wert = _view.wertCB.getSelectedItem();

                if(wahlAttr.equals("Student - Nachname")) {

                    query = "SELECT name, vorname, urz FROM student WHERE name = '"+ wert +"' ORDER BY name;";
                    queryUrz = "SELECT urz FROM student WHERE name = '"+ wert +"' ORDER BY name LIMIT 1;";

                    boolean queryNotEmpty = _model.queryNotEmpty(query);

                    if(queryNotEmpty){
                        IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                        suchModel.setSuchQuery(query);
                        suchModel.setSuchQueryUrz(queryUrz);

                        _view.dispose();
                        _model.closeConnection();

                        IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                    }

                    else {
                        DialogFenster dialog = new DialogFenster();
                        dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                    }
                }

                if(wahlAttr.equals("Student - Vorname")){

                    query = "SELECT name, vorname, urz FROM student WHERE vorname = '"+ wert +"' ORDER BY name;";
                    queryUrz = "SELECT urz FROM student WHERE vorname = '"+ wert +"' ORDER BY name LIMIT 1;";

                    boolean queryNotEmpty = _model.queryNotEmpty(query);

                    if(queryNotEmpty){
                        IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                        suchModel.setSuchQuery(query);
                        suchModel.setSuchQueryUrz(queryUrz);

                        _view.dispose();
                        _model.closeConnection();

                        IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                    }

                    else {
                        DialogFenster dialog = new DialogFenster();
                        dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                    }
                }

                if(wahlAttr.equals("Urz (TUC)")){

                    query = "SELECT name, vorname, urz FROM student WHERE urztuc = '"+ wert +"' ORDER BY name;";
                    queryUrz = "SELECT urz FROM student WHERE urztuc = '"+ wert +"' ORDER BY name LIMIT 1;";

                    boolean queryNotEmpty = _model.queryNotEmpty(query);

                    if(queryNotEmpty){
                        IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                        suchModel.setSuchQuery(query);
                        suchModel.setSuchQueryUrz(queryUrz);

                        _view.dispose();
                        _model.closeConnection();

                        IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                    }

                    else {
                        DialogFenster dialog = new DialogFenster();
                        dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                    }
                }

                if(wahlAttr.equals("Fakultät")){

                    query = "SELECT name, vorname, urz FROM student WHERE fakultaet = '"+ wert +"' ORDER BY name;";
                    queryUrz = "SELECT urz FROM student WHERE fakultaet = '"+ wert +"' ORDER BY name LIMIT 1;";

                    boolean queryNotEmpty = _model.queryNotEmpty(query);

                    if(queryNotEmpty){
                        IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                        suchModel.setSuchQuery(query);
                        suchModel.setSuchQueryUrz(queryUrz);

                        _view.dispose();
                        _model.closeConnection();

                        IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                    }

                    else {
                        DialogFenster dialog = new DialogFenster();
                        dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                    }
                }

                 if(wahlAttr.equals("Status")){

                    query = "SELECT name, vorname, student.urz FROM student " +
                            "INNER JOIN student_status ON student_status.urz = student.urz  " +
                            "WHERE status_typ = '"+ wert +"' ORDER BY name;";

                     queryUrz = "SELECT student.urz FROM student " +
                                "INNER JOIN student_status ON student_status.urz = student.urz  " +
                                "WHERE status_typ = '"+ wert +"' ORDER BY name LIMIT 1;";

                     boolean queryNotEmpty = _model.queryNotEmpty(query);

                     if(queryNotEmpty){
                         IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                         suchModel.setSuchQuery(query);
                         suchModel.setSuchQueryUrz(queryUrz);

                         _view.dispose();
                         _model.closeConnection();

                         IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                     }

                     else {
                         DialogFenster dialog = new DialogFenster();
                         dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                     }
                 }

                 if(wahlAttr.equals("Aktivität - Name und ID")){

                     String[] wertArray = wert.toString().split("–");
                     String wertString = wertArray[1];
                     wertString = wertString.replaceAll("\\s+","");

                    query = "SELECT name, vorname, student.urz FROM student  " +
                            "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                            "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                            "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  " +
                            "WHERE aktivitaet.aktivitaet_name = '"+ wertString +"' ORDER BY name;";

                     queryUrz = "SELECT student.urz FROM student  " +
                                "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                                "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                                "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  " +
                                "WHERE aktivitaet.aktivitaet_name = '"+ wertString +"' ORDER BY name LIMIT 1;";


                     boolean queryNotEmpty = _model.queryNotEmpty(query);

                     if(queryNotEmpty){
                         IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                         suchModel.setSuchQuery(query);
                         suchModel.setSuchQueryUrz(queryUrz);

                         _view.dispose();
                         _model.closeConnection();

                         IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                     }

                     else {
                         DialogFenster dialog = new DialogFenster();
                         dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                     }
                 }

                 if(wahlAttr.equals("Maßnahme - Name")){

                     query = "SELECT name, vorname, student.urz FROM student " +
                             "INNER JOIN s_m_a ON student.urz = s_m_a.urz " +
                             "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id " +
                             "INNER JOIN massnahme ON m_a.massnahme_name = massnahme.massnahme_name " +
                             "WHERE massnahme.massnahme_name = '"+ wert +"' ORDER BY name;";

                     queryUrz = "SELECT student.urz FROM student " +
                                "INNER JOIN s_m_a ON student.urz = s_m_a.urz " +
                                "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id " +
                                "INNER JOIN massnahme ON m_a.massnahme_name = massnahme.massnahme_name " +
                                "WHERE massnahme.massnahme_name = '"+ wert +"' ORDER BY name LIMIT 1;";

                     boolean queryNotEmpty = _model.queryNotEmpty(query);

                     if(queryNotEmpty){
                         IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                         suchModel.setSuchQuery(query);
                         suchModel.setSuchQueryUrz(queryUrz);

                         _view.dispose();
                         _model.closeConnection();

                         IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                     }

                     else {
                         DialogFenster dialog = new DialogFenster();
                         dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
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
                InsertStudentVIew fensterInsert = new InsertStudentVIew(new InsertStudentModel(),"InProTUC Datenbank | Person neu einfügen");
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

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
