package Controller.Update;

import Model.Update.UpdateAktPersModel;
import View.Sonstiges.DialogFenster;
import View.Update.UpdateAktPersView;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by annelie on 31.08.16.
 */
public class UpdateAktPersController implements ActionListener, KeyListener {

    //Modell:
    private UpdateAktPersModel model;
    //View:
    private UpdateAktPersView view;

    public String a = "", nameAkt = "";

    boolean mobilitaet;

    public UpdateAktPersController(UpdateAktPersModel _model, UpdateAktPersView _view){
        model = _model;
        view = _view;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        DialogFenster dialog = new DialogFenster();

        /**
         * Betätigung des Enter-Buttons
         */
        if(source == view.enterBtn){

            String urz = model.urz;
            String neuAktivitaet = model.defaultObject.toString();
            int id_m_a = model.findId_m_a(nameAkt);

            // Überprüfen ob Person schon Aktivität hat
            String query = "SELECT urz, id_m_a FROM s_m_a WHERE urz = '"+urz+"' AND id_m_a = '"+ id_m_a +"';";
            String[] spalten = {"urz", "id_m_a"};
            System.out.println(query);
            ArrayList<String> wiederholung = model.selectMultiple(query, spalten);

            if (nameAkt == ""){
                dialog.errorDialog(view, "Bitte wählen Sie eine neue Aktivität aus");
            }

            if(!wiederholung.isEmpty()){
                dialog.errorDialog(view, "Die Person ist bereits für die Aktivität \""+ a +"\" registriert");
            }

            else if ((!nameAkt.equals("")) && wiederholung.isEmpty()){

                //Update wird durchgeführt
                String defaultNameAktivitaet = model.defaultNameAktivitaet;
                int defaultId_s_m_a = model.findId_s_m_a(model.defaultIDAktivitaet);

                // Wenn die Aktivität mobilität ist, dann aus student_mob löschen
                if(defaultNameAktivitaet.substring(0, 2).equals("M_")){
                    boolean entfernenErgebnis = model.deleteMobilität(defaultId_s_m_a);

                    if(!entfernenErgebnis){
                        dialog.errorDialog(view, "Die Aktivität Mobilität konnte nicht vollständig gelöscht werden");
                    }

                }

                // Update
                boolean ergebnis = model.updatePersAktivitaet(id_m_a);

                /**
                 * Aktivität ist NICHT Mobilität
                 */
                if(!mobilitaet){
                    if(ergebnis){
                        dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Aktivität einer Person ändern");
                        view.dispose();
                    }

                    else {
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        dialog.infoDialog(view, model.getFehlerString());
                    }
                }

                /**
                 * Aktivität IST Mobilität
                 */
                if(mobilitaet){
                    String durchführung, art;
                    int id_s_m_a = model.findId_s_m_a(id_m_a);
                    System.out.println("id_s_m_a" + id_s_m_a);

                    art = view.artAktTxtFld.getText();

                    if(view.jaRBtn.isSelected())
                        durchführung = "ja";

                    else
                        durchführung = "nein";

                    boolean ergebnis2 = model.insertIntoMobilität(id_s_m_a, durchführung, art);

                    if(ergebnis && ergebnis2){
                        dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Aktivität einer Person ändern");
                        view.dispose();
                    }

                    else {
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        dialog.infoDialog(view, model.getFehlerString());
                    }



                }

            }


        }

        /**
         * Betätigung des Zurück-Buttons
         */
        if(source == view.zurueckBtn){
            model.closeConnection();
            view.dispose();
        }

        /**
         * Auswahl Combo Box
         */
        if (source instanceof JComboBox) {

            // Auswahl wird ausgegeben
            a = (String) view.aktCb.getSelectedItem();//get the selected item
            // Der Primary Key wird gesucht
            String[] aktivitaet = a.split("–");
            nameAkt = aktivitaet[1];
            nameAkt = nameAkt.replaceAll("\\s+","");
            System.out.println(nameAkt.substring(0, 2));

            if (nameAkt.substring(0, 2).equals("M_")){
                mobilitaet = true;
                System.out.println("Mobilität einschalten");
                mobVerwalten(mobilitaet);
            }


            else {
                mobilitaet = false;
                mobVerwalten(mobilitaet);
            }


        }



    }

    public void mobVerwalten(boolean mob){
        if(mob){
            view.panel_2.setEnabled(true);
            view.jaRBtn.setEnabled(true);
            view.neinRBtn.setEnabled(true);
            view.artAktTxtFld.setEnabled(true);
            view.durchführungLbl.setEnabled(true);
            view.artLbl.setEnabled(true);
        }

        else{
            view.panel_2.setEnabled(false);
            view.jaRBtn.setEnabled(false);
            view.neinRBtn.setEnabled(false);
            view.artAktTxtFld.setEnabled(false);
            view.durchführungLbl.setEnabled(false);
            view.artLbl.setEnabled(false);
            view.groupDurchfuehrungRBtn.clearSelection();
        }
    }

    public void keyPressed(KeyEvent keyEvent) {

        Object source = keyEvent.getKeyCode();
        DialogFenster dialog = new DialogFenster();

        if(source == KeyEvent.VK_ENTER){

            String urz = model.urz;
            String neuAktivitaet = model.defaultObject.toString();
            int id_m_a = model.findId_m_a(nameAkt);

            // Überprüfen ob Person schon Aktivität hat
            String query = "SELECT urz, id_m_a FROM s_m_a WHERE urz = '"+urz+"' AND id_m_a = '"+ id_m_a +"';";
            String[] spalten = {"urz", "id_m_a"};
            System.out.println(query);
            ArrayList<String> wiederholung = model.selectMultiple(query, spalten);

            if (nameAkt == ""){
                dialog.errorDialog(view, "Bitte wählen Sie eine neue Aktivität aus");
            }

            if(!wiederholung.isEmpty()){
                dialog.errorDialog(view, "Die Person ist bereits für die Aktivität \""+ a +"\" registriert");
            }

            else if ((!nameAkt.equals("")) && wiederholung.isEmpty()){

                //Update wird durchgeführt
                String defaultNameAktivitaet = model.defaultNameAktivitaet;
                int defaultId_s_m_a = model.findId_s_m_a(model.defaultIDAktivitaet);

                // Wenn die Aktivität mobilität ist, dann aus student_mob löschen
                if(defaultNameAktivitaet.substring(0, 2).equals("M_")){
                    boolean entfernenErgebnis = model.deleteMobilität(defaultId_s_m_a);

                    if(!entfernenErgebnis){
                        dialog.errorDialog(view, "Die Aktivität Mobilität konnte nicht vollständig gelöscht werden");
                        String fehlerString = model.getFehlerString();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(view, fehlerString);
                        }
                    }

                }

                // Update
                boolean ergebnis = model.updatePersAktivitaet(id_m_a);

                /**
                 * Aktivität ist NICHT Mobilität
                 */
                if(!mobilitaet){
                    if(ergebnis){
                        dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Aktivität einer Person ändern");
                        view.dispose();
                    }

                    else {
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = model.getFehlerString();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(view, fehlerString);
                        }
                    }
                }

                /**
                 * Aktivität IST Mobilität
                 */
                if(mobilitaet){
                    String durchführung, art;
                    int id_s_m_a = model.findId_s_m_a(id_m_a);
                    System.out.println("id_s_m_a" + id_s_m_a);

                    art = view.artAktTxtFld.getText();

                    if(view.jaRBtn.isSelected())
                        durchführung = "ja";

                    else
                        durchführung = "nein";

                    boolean ergebnis2 = model.insertIntoMobilität(id_s_m_a, durchführung, art);

                    if(ergebnis && ergebnis2){
                        dialog.erfolgDialog(view, "Die neue Aktivität wurde erfolgreich gespeichert", "Aktivität einer Person ändern");
                        view.dispose();
                    }

                    else {
                        dialog.errorDialog(view, "Es kam ein Fehler beim Speichern der Daten vor");
                        String fehlerString = model.getFehlerString();

                        if(!fehlerString.isEmpty()){
                            dialog.infoDialog(view, fehlerString);
                        }
                    }


                }

            }

        }

    }

    public void keyTyped(KeyEvent keyEvent) {



    }

    public void keyReleased(KeyEvent keyEvent) {


    }





}
