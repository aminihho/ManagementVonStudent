package Main;

import Controller.Insert.InsertBemStudentController;
import Model.Insert.*;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;
import Model.Sonstiges.ExcelModel;
import Model.Sonstiges.IndexModel;
import Model.Sonstiges.LoginModel;
import Model.Update.*;
import View.Insert.*;
import View.Select.SelectEinfachView;
import View.Select.SelectErweitertView;
import View.Sonstiges.ExcelView;
import View.Sonstiges.IndexView;
import View.Sonstiges.LoginView;
import View.Update.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by annelie on 18.04.16.
 */
public class Main {
    /**
     * Create a jar file
     */
    //http://introcs.cs.princeton.edu/java/85application/jar/jar.html
    //http://docs.oracle.com/javase/tutorial/deployment/jar/basicsindex.html
    //n


    public static void main(String args[]) throws JSONException{


        //LoginView login = new LoginView(new LoginModel(), "InProTUC Datenbank | Login" );

        //IndexView fenster = new IndexView(new IndexModel(), "Index");

        //InsertStudentVIew fenster = new InsertStudentVIew(new InsertStudentModel(), "Index");

        //InsertAktStudentView fenster = new InsertAktStudentView(new InsertAktStudentModel(), "InProTUC Datenbank | Aktivität eines Studenten eintragen");

        //InsertAktivitaetView fenster = new InsertAktivitaetView(new InsertAktivitaetModel(), "InProTUC Datenbank | Aktivität eines Studenten eintragen");

        //InsertStatusStudentView fenster = new InsertStatusStudentView(new InsertStatusStudentModel(), "InProTUC Datenbank |");

        //InsertStatusView fenster = new InsertStatusView(new InsertStatusModel(), "Fenster");

        //IndexUpdatePersonView login = new IndexUpdatePersonView(new IndexUpdatePersonModel(),"login");

        //TableView fenster = new TableView(new TableModel(), "suche");

        //SelectEinfachView fenster = new SelectEinfachView(new SelectEinfachModel(), "fenster");

        SelectErweitertView fenster = new SelectErweitertView(new SelectErweitertModel(), "");
        
        
        //JSONObject obj = new JSONObject(); 
        //ExcelView fenster = new ExcelView(new ExcelModel(obj), "test"); 

        //IndexUpdatePersonView fenster = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "fenster");

        //IndexUpdateAktivitaetView fnster = new IndexUpdateAktivitaetView(new IndexUpdateAktivitaetModel(), "fenster");

        //IndexUpdateStatusView fnster = new IndexUpdateStatusView(new IndexUpdateStatusModel(), "fenster");

        //IndexUpdateMassnahmeView fnster = new IndexUpdateMassnahmeView(new IndexUpdateMassnahmeModel(), "fenster");

        //InsertBemStudentView fenstwre = new InsertBemStudentView(new InsertBemStudentModel(), "fenster");



    }
}

