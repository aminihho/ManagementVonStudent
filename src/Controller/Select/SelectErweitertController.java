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
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by annelie on 06.05.16.
 */
public class SelectErweitertController implements ActionListener,KeyListener {
    private SelectErweitertModel _model;
    private SelectErweitertView _view;

    String[] AttributeKomplett = {"name", "vorname", "fakultaet", "", ""};
    String attribut;
    String query = "", queryUrz = "";
    //DefaultComboBoxModel model;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    String wahlAttr = "", wahlAttr2 = "", wahlAttr3 = "", wert = "", wert2 = "", wert3 = "",akti_wert= "",akti_wert2= "",akti_wert3= "";
    private boolean search2 = false, search3 = false;
    private boolean student = false, status = false, aktivitaet = false;

    public SelectErweitertController(SelectErweitertModel model, SelectErweitertView view){
        this._model = model;
        this._view = view;

    }

    public void searchAttributes2(boolean status){
        _view.att2Lbl.setEnabled(status);
        _view.wert2Lbl.setEnabled(status);

        _view.attributCB2.setEnabled(status);
        //_view.wertCB2.setEnabled(status);

        boolean button = (!status);
        _view.addAtt2.setVisible(button);
        _view.minusAtt2.setVisible(status);

        if (!status){
            _view.attributCB2.setSelectedItem("-");
            String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
            DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
            _view.wertCB2.setModel(model);
            _view.wertCB2.setEnabled(status);
        }
    }

    public void searchAttributes3(boolean status){
        _view.att3Lbl.setEnabled(status);
        _view.wert3Lbl.setEnabled(status);

        _view.attributCB3.setEnabled(status);
        //_view.wertCB3.setEnabled(status);

        boolean button = (!status);
        _view.addAtt3.setVisible(button);
        _view.minusAtt3.setVisible(status);

        if (!status){
            _view.attributCB3.setSelectedItem("-");
            String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
            DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
            _view.wertCB3.setModel(model);
            _view.wertCB3.setEnabled(status);
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();


        if (source instanceof JButton) {

            /**
             * Betätigung vom Zurück-Button
             */
            if (source.equals(_view.zurueck)) {
                _view.setVisible(false);
                IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

            if(source.equals(_view.addAtt2)){
                searchAttributes2(true);
                search2 = true;
            }

            if(source.equals(_view.addAtt3)){
                searchAttributes3(true);
                search3 = true;
            }

            if(source.equals(_view.minusAtt2)){
                searchAttributes2(false);
                search2 = false;

                student = false;
                aktivitaet = false;
                status = false;

            }

            if(source.equals(_view.minusAtt3)){
                searchAttributes3(false);
                search3 = false;

                student = false;
                aktivitaet = false;
                status = false;
            }



            /**
             * Betätigung vom Senden-Button
             */
            if (source.equals(_view.senden)) {

                Object wert = _view.wertCB.getSelectedItem();
                Object wert2 = "", wert3 = "";
                String attachment1 = "", attachment2 = "";
                String attribut = "", attribut2 = "", attribut3 = "";
                
            
  
                

                /**
                 * Attribut bei Student
                 */
               
                if (wahlAttr.equals("Student - Nachname")){
                    attribut = "name";
                }


                if (wahlAttr.equals("Student - Vorname")){
                    attribut = "vorname";
                }


                if (wahlAttr.equals("Urz (TUC)")){
                    attribut = "urztuc";
                }


                if (wahlAttr.equals("Fakultät")){
                    attribut = "fakultaet";
                }



                if (wahlAttr2.equals("Student - Nachname")){
                    attribut2 = "name";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr2.equals("Student - Vorname")){
                    attribut2 = "vorname";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr2.equals("Urz (TUC)")){
                    attribut2 = "urztuc";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr2.equals("Fakultät")){
                    attribut2 = "fakultaet";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr3.equals("Student - Nachname")){
                    attribut3 = "name";
                    wert3 = _view.wertCB3.getSelectedItem();
                }

                if (wahlAttr3.equals("Student - Vorname")){
                    attribut3 = "vorname";
                    wert3 = _view.wertCB3.getSelectedItem();
                }

                if (wahlAttr3.equals("Urz (TUC)")){
                    attribut3 = "urztuc";
                    wert3 = _view.wertCB3.getSelectedItem();
                }

                if (wahlAttr3.equals("Fakultät")){
                    attribut3 = "fakultaet";
                    wert3 = _view.wertCB3.getSelectedItem();
                }

                /**
                 * Attribut bei Status
                 */
                if (wahlAttr.equals("Status")){
                    attribut = "status_typ";
                }


                if (wahlAttr2.equals("Status")){
                    attribut2 = "status_typ";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr3.equals("Status")){
                    attribut3 = "status_typ";
                    wert3 = _view.wertCB3.getSelectedItem();
                }

                /**
                 * Attribut bei Aktvitaet
                 */
                if (wahlAttr.equals("Aktivität - Name und ID")){
                    attribut = "aktivitaet.aktivitaet_name";
                    akti_wert = wert.toString();
                    String[] wertArray = wert.toString().split("–");
                    String wertString = wertArray[1];
                    wert = wertString.replaceAll("\\s+","");
                }

                if (wahlAttr2.equals("Aktivität - Name und ID")){
                    attribut2 = "aktivitaet.aktivitaet_name";
                    wert2 = _view.wertCB2.getSelectedItem();
                    akti_wert2 = wert2.toString();
                    String[] wertArray = wert2.toString().split("–");
                    String wertString = wertArray[1];
                    wert2 = wertString.replaceAll("\\s+","");
                }

                if (wahlAttr3.equals("Aktivität - Name und ID")){
                    attribut3 = "aktivitaet.aktivitaet_name";
                    wert3 = _view.wertCB3.getSelectedItem();
                    akti_wert3 = wert3.toString();
                    String[] wertArray = wert3.toString().split("–");
                    String wertString = wertArray[1];
                    wert3 = wertString.replaceAll("\\s+","");
                }

                /**
                 * Attribut bei Maßnahme
                 */
                if (wahlAttr.equals("Maßnahme - Name"))
                    attribut = "massnahme_name";

                if (wahlAttr2.equals("Maßnahme - Name")){
                    attribut2 = "massnahme_name";
                    wert2 = _view.wertCB2.getSelectedItem();
                }

                if (wahlAttr3.equals("Maßnahme - Name")){
                    attribut3 = "massnahme_name";
                    wert3 = _view.wertCB3.getSelectedItem();
                }
                
                   ArrayList<String> infoQuery = new ArrayList<String>(); 
                if(! wahlAttr.equals("-")){
                    infoQuery.add(wahlAttr);
                    if(wahlAttr.equals("Aktivität - Name und ID")){
                        infoQuery.add(akti_wert.toString());
                    }
                    else{
                         infoQuery.add(wert.toString()); 
                    } 
                     
                   
                }
                 if(! wahlAttr2.equals("-")){
                    infoQuery.add(wahlAttr2); 
                     if(wahlAttr2.equals("Aktivität - Name und ID")){
                        infoQuery.add(akti_wert2.toString());
                    }
                    else{
                         infoQuery.add(wert2.toString()); 
                    }
                }
                 if(! wahlAttr3.equals("-")){
                    infoQuery.add(wahlAttr3); 
                     if(wahlAttr3.equals("Aktivität - Name und ID")){
                        infoQuery.add(akti_wert3.toString());
                    }
                    else{
                         infoQuery.add(wert3.toString()); 
                    }
                }
             
                
                
                
                

                /**
                 * Zuerst wird festgestellt werde Joins für die Erstellung der Query nötig sind
                 */
                if(wahlAttr.equals("Student - Nachname") || wahlAttr.equals("Student - Vorname") || wahlAttr.equals("Urz (TUC)") || wahlAttr.equals("Fakultät")){

                    if((!search2) && (!search3)){
                        student = true;
                    }

                    if((search2) && (!search3)){
                        if (wahlAttr2.equals("Student - Nachname") || wahlAttr2.equals("Student - Vorname") || wahlAttr2.equals("Urz (TUC)") || wahlAttr2.equals("Fakultät")){
                            student = true;
                        }
                    }

                    if((!search2) && (search3)){
                        if (wahlAttr3.equals("Student - Nachname") || wahlAttr3.equals("Student - Vorname") || wahlAttr3.equals("Urz (TUC)") || wahlAttr3.equals("Fakultät")){
                            student = true;
                        }
                    }

                    if((search2) && (search3)){
                        if (wahlAttr2.equals("Student - Nachname") || wahlAttr2.equals("Student - Vorname") || wahlAttr2.equals("Urz (TUC)") || wahlAttr2.equals("Fakultät")){
                            if (wahlAttr3.equals("Student - Nachname") || wahlAttr3.equals("Student - Vorname") || wahlAttr3.equals("Urz (TUC)") || wahlAttr3.equals("Fakultät")){
                                student = true;
                            }
                        }
                    }
                }

                else
                    student= false;


                if(wahlAttr.equals("Status") || wahlAttr2.equals("Status") || wahlAttr3.equals("Status")){
                    status = true;
                    student = false;
                }

                if(wahlAttr.equals("Aktivität - Name und ID") || wahlAttr2.equals("Aktivität - Name und ID") || wahlAttr3.equals("Aktivität - Name und ID")
                        || wahlAttr.equals("Maßnahme - Name") || wahlAttr2.equals("Maßnahme - Name") || wahlAttr3.equals("Maßnahme - Name")){

                    aktivitaet = true;
                    student = false;
                }

                if(student){
                    query = "SELECT DISTINCT name, vorname, urz FROM student";
                    queryUrz = "SELECT urz FROM student";
                }

                if((aktivitaet) && (!status)){

                    query = "SELECT DISTINCT name, vorname, student.urz FROM student  " +
                            "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                            "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                            "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  ";
                    queryUrz = "SELECT student.urz FROM student  " +
                            "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                            "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                            "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  ";
                }

                if((!aktivitaet) && (status)){

                    query = "SELECT DISTINCT name, vorname, student.urz FROM student " +
                            "INNER JOIN student_status ON student_status.urz = student.urz  ";
                    queryUrz = "SELECT student.urz FROM student " +
                            "INNER JOIN student_status ON student_status.urz = student.urz  ";
                }

                if((aktivitaet) && (status)){

                    query = "SELECT DISTINCT name, vorname, student.urz FROM student " +
                            "INNER JOIN student_status ON student_status.urz = student.urz " +
                            "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                            "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                            "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  ";
                    queryUrz = "SELECT name, vorname, student.urz FROM student " +
                            "INNER JOIN student_status ON student_status.urz = student.urz " +
                            "INNER JOIN s_m_a ON student.urz = s_m_a.urz  " +
                            "INNER JOIN m_a ON s_m_a.id_m_a = m_a.id  " +
                            "INNER JOIN aktivitaet ON aktivitaet.aktivitaet_name = m_a.aktivitaet_name  ";
                }

                if((!search2) && (!search3)){

                    query = query + " WHERE " + attribut + " = " + "'" + wert + "';";
                    queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "' ORDER BY name LIMIT 1;";

                    boolean queryNotEmpty = _model.queryNotEmpty(query);

                    if(queryNotEmpty){
                        IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                        suchModel.setSuchQuery(query);
                        suchModel.setSuchQueryUrz(queryUrz);

                        _view.dispose();
                        _model.closeConnection();
                        suchModel.setQueryInfo(infoQuery);
                        IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                    }

                    else {
                        DialogFenster dialog = new DialogFenster();
                        dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                    }

                }

                if(_view.schnittmenge.isSelected()){
                    //Schnittmenge
                    if((!search2) && (search3)){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment2 = " AND " + attribut3 + " = " + "'" + wert3 + "'";
                        query = query + attachment2;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment2 = " AND " + attribut3 + " = " + "'" + wert3 + "'";
                        queryUrz = queryUrz + attachment2;
                        queryUrz = queryUrz + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);

                        if(queryNotEmpty){
                            IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }

                    if((search2) && (!search3)){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " AND " + attribut2 + " = " + "'" + wert2 + "'";
                        query = query + attachment1;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " AND " + attribut2 + " = " + "'" + wert2 + "'";
                        queryUrz = queryUrz + attachment1;
                        queryUrz = queryUrz + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);

                        if(queryNotEmpty){
                            IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }

                    if(search2 && search3){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " AND " + attribut2 + " = " + "'" + wert2 + "'";
                        attachment2 = " AND "  + attribut3 + " = " + "'" + wert3 + "'";
                        query = query + attachment1 + attachment2;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " AND " + attribut2 + " = " + "'" + wert2 + "'";
                        attachment2 = " AND "  + attribut3 + " = " + "'" + wert3 + "'";
                        queryUrz = queryUrz + attachment1 + attachment2;
                        queryUrz = queryUrz  + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);

                        if(queryNotEmpty){
                            IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }
                }

                if(_view.union.isSelected()){
                    // Union
                    if((!search2) && (search3)){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment2 = " OR " + attribut3 + " = " + "'" + wert3 + "'";
                        query = query + attachment2;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment2 = " OR " + attribut3 + " = " + "'" + wert3 + "'";
                        queryUrz = queryUrz + attachment2;
                        queryUrz = queryUrz + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);

                        if(queryNotEmpty){
                            IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }

                    if((search2) && (!search3)){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " OR " + attribut2 + " = " + "'" + wert2 + "'";
                        query = query + attachment1;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " OR " + attribut2 + " = " + "'" + wert2 + "'";
                        queryUrz = queryUrz + attachment1;
                        queryUrz = queryUrz + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);

                        if(queryNotEmpty){
                            IndexUpdatePersonModel suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }

                    if(search2 && search3){
                        query = query + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " OR " + attribut2 + " = " + "'" + wert2 + "'";
                        attachment2 = " OR "  + attribut3 + " = " + "'" + wert3 + "'";
                        query = query + attachment1 + attachment2;
                        query = query + ";";

                        queryUrz = queryUrz + " WHERE " + attribut + " = " + "'" + wert + "'";
                        attachment1 = " OR " + attribut2 + " = " + "'" + wert2 + "'";
                        attachment2 = " OR "  + attribut3 + " = " + "'" + wert3 + "'";
                        queryUrz = queryUrz + attachment1 + attachment2;
                        queryUrz = queryUrz + "ORDER BY name LIMIT 1;";

                        boolean queryNotEmpty = _model.queryNotEmpty(query);
                        IndexUpdatePersonModel suchModel = null;
                        if(queryNotEmpty){
                            suchModel = new IndexUpdatePersonModel();
                            suchModel.setSuchQuery(query);
                            suchModel.setSuchQueryUrz(queryUrz);

                            _view.dispose();
                            _model.closeConnection();
                            suchModel.setQueryInfo(infoQuery);
                            IndexUpdatePersonView fenster = new IndexUpdatePersonView(suchModel, "InProTUC Datenbank | Person ändern");
                        }

                        else {
                            DialogFenster dialog = new DialogFenster();
                            dialog.infoDialog(_view, "Die Suche erzielte keine Treffer");
                        }
                    }
                }
                
             
                 

            }
        }

        /**
         * Combo Box
         * Speichert die Asuwahl des Combobox in einer Variabel
         */
        if (source instanceof JComboBox) {
            Object sourceCB = actionEvent.getSource();
            Functions funktion = new Functions();

            /**----------------------------------
             * Set ComboBox Model 1
             *----------------------------------*/
            if (sourceCB.equals(_view.attributCB)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlAttr = (String) cb.getSelectedItem();
               _model.saveAttribut(wahlAttr);

                if (!wahlAttr.equals("-")) {
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

                }
            }

            /**----------------------------------
             * Set ComboBox Model 2
             *----------------------------------*/
            if (sourceCB.equals(_view.attributCB2)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlAttr2 = (String) cb.getSelectedItem();

                if (!wahlAttr2.equals("-")) {
                    _view.wertCB2.setEnabled(true);
                }

                if(wahlAttr2.equals("Student - Nachname")){
                    String query = "SELECT DISTINCT name FROM student ORDER BY name;";
                    ArrayList<String> listeName = _model.select(query, "name");
                    String[] nameArray = funktion.arrayListTo1DString(listeName);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB2.setModel(model);
                }

                if(wahlAttr2.equals("Student - Vorname")){
                    String query = "SELECT DISTINCT vorname FROM student ORDER BY vorname;";
                    ArrayList<String> listeVorname = _model.select(query, "vorname");
                    String[] vornameArray = funktion.arrayListTo1DString(listeVorname);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(vornameArray);
                    _view.wertCB2.setModel(model);
                }

                if(wahlAttr2.equals("Fakultät")){
                    String query = "SELECT DISTINCT fakultaet FROM student ORDER BY fakultaet;";
                    ArrayList<String> listeFaku = _model.select(query, "fakultaet");
                    String[] fakuArray = funktion.arrayListTo1DString(listeFaku);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(fakuArray);
                    _view.wertCB2.setModel(model);
                }

                if(wahlAttr2.equals("Status")){
                    String query = "SELECT status_typ FROM status ORDER BY status_typ;";
                    ArrayList<String> listeStatus = _model.select(query, "status_typ");
                    String[] statusArray = funktion.arrayListTo1DString(listeStatus);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(statusArray);
                    _view.wertCB2.setModel(model);

                }

                if(wahlAttr2.equals("Urz (TUC)")){
                    String query = "SELECT urztuc FROM student ORDER BY urztuc;";
                    ArrayList<String> listeUrz = _model.select(query, "urztuc");
                    String[] urzArray = funktion.arrayListTo1DString(listeUrz);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(urzArray);
                    _view.wertCB2.setModel(model);
                }

                if(wahlAttr2.equals("Aktivität - Name und ID")){
                    String query = "SELECT beschreibung, aktivitaet_name FROM aktivitaet ORDER BY beschreibung;";
                    String[] spalten = new String[]{"beschreibung", "aktivitaet_name"};
                    ArrayList<String> listeAktivitaet = _model.selectMultiple(query, spalten);
                    ArrayList<String> listeAktivitaetConcat = funktion.ConcateSpalten(2, listeAktivitaet, '–');
                    String[] aktiviArray = funktion.arrayListTo1DString(listeAktivitaetConcat);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(aktiviArray);
                    _view.wertCB2.setModel(model);

                }

                if(wahlAttr2.equals("Maßnahme - Name")){
                    String query = "SELECT massnahme_name FROM massnahme ORDER BY massnahme_name;";
                    ArrayList<String> listeMassnahme = _model.select(query, "massnahme_name");
                    String[] massnahmeArray = funktion.arrayListTo1DString(listeMassnahme);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(massnahmeArray);
                    _view.wertCB2.setModel(model);
                }


                else if(wahlAttr2.equals("-")){
                    String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB2.setModel(model);

                }

            }

            /**----------------------------------
             * Set ComboBox Model 3
             *----------------------------------*/
            if (sourceCB.equals(_view.attributCB3)) {

                JComboBox cb = (JComboBox) actionEvent.getSource();
                wahlAttr3 = (String) cb.getSelectedItem();

                if (!wahlAttr3.equals("-")) {
                    _view.wertCB3.setEnabled(true);
                }

                if(wahlAttr3.equals("Student - Nachname")){
                    String query = "SELECT DISTINCT name FROM student ORDER BY name;";
                    ArrayList<String> listeName = _model.select(query, "name");
                    String[] nameArray = funktion.arrayListTo1DString(listeName);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB3.setModel(model);
                }

                if(wahlAttr3.equals("Student - Vorname")){
                    String query = "SELECT DISTINCT vorname FROM student ORDER BY vorname;";
                    ArrayList<String> listeVorname = _model.select(query, "vorname");
                    String[] vornameArray = funktion.arrayListTo1DString(listeVorname);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(vornameArray);
                    _view.wertCB3.setModel(model);
                }

                if(wahlAttr3.equals("Fakultät")){
                    String query = "SELECT DISTINCT fakultaet FROM student ORDER BY fakultaet;";
                    ArrayList<String> listeFaku = _model.select(query, "fakultaet");
                    String[] fakuArray = funktion.arrayListTo1DString(listeFaku);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(fakuArray);
                    _view.wertCB3.setModel(model);
                }

                if(wahlAttr3.equals("Status")){
                    String query = "SELECT status_typ FROM status ORDER BY status_typ;";
                    ArrayList<String> listeStatus = _model.select(query, "status_typ");
                    String[] statusArray = funktion.arrayListTo1DString(listeStatus);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(statusArray);
                    _view.wertCB3.setModel(model);

                }

                if(wahlAttr3.equals("Urz (TUC)")){
                    String query = "SELECT urztuc FROM student ORDER BY urztuc;";
                    ArrayList<String> listeUrz = _model.select(query, "urztuc");
                    String[] urzArray = funktion.arrayListTo1DString(listeUrz);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(urzArray);
                    _view.wertCB3.setModel(model);
                }

                if(wahlAttr3.equals("Aktivität - Name und ID")){
                    String query = "SELECT beschreibung, aktivitaet_name FROM aktivitaet ORDER BY beschreibung;";
                    String[] spalten = new String[]{"beschreibung", "aktivitaet_name"};
                    ArrayList<String> listeAktivitaet = _model.selectMultiple(query, spalten);
                    ArrayList<String> listeAktivitaetConcat = funktion.ConcateSpalten(2, listeAktivitaet, '–');
                    String[] aktiviArray = funktion.arrayListTo1DString(listeAktivitaetConcat);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(aktiviArray);
                    _view.wertCB3.setModel(model);

                }

                if(wahlAttr3.equals("Maßnahme - Name")){
                    String query = "SELECT massnahme_name FROM massnahme ORDER BY massnahme_name;";
                    ArrayList<String> listeMassnahme = _model.select(query, "massnahme_name");
                    String[] massnahmeArray = funktion.arrayListTo1DString(listeMassnahme);
                    DefaultComboBoxModel model = new DefaultComboBoxModel(massnahmeArray);
                    _view.wertCB3.setModel(model);
                }


                else if(wahlAttr3.equals("-")){
                    String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
                    DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
                    _view.wertCB3.setModel(model);

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
