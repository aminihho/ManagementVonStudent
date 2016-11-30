package Controller.Sonstiges;

import Model.Update.IndexUpdatePersonModel;
import Model.Sonstiges.LoginModel;
import View.Update.IndexUpdatePersonView;
import View.Sonstiges.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

/**
 * Created by annelie on 13.06.16.
 */
public class LoginController extends WindowAdapter implements ActionListener,KeyListener{

    /// Modle Atribute
    private LoginModel model;
    /// View Atribute
    private LoginView view;

    private boolean loginUser = false, loginPass = false;

    /*---------------------------------------------------------------------------------------
     * Konstruktur zum Setzung von MVC
     * @parm model Login_Model : das Model des Controlers  .
     * @parm view  login : das View des Controlers.
     *
     * ---------------------------------------------------------------------------------------*/
    public LoginController(LoginModel model,LoginView view){
        this.model=model;
        this.view=view;
    }

 /*---------------------------------------------------------------------------------------
  *      Verarbeiten von Eingaben
  *
  * ---------------------------------------------------------------------------------------*/

    public void actionPerformed(ActionEvent arg0) {
        Object source = arg0.getSource();

        if(source.equals(view.sendBtn)){
            loginUser = false; loginPass = false;

            //username abfragen.
            boolean userExists = model.find_user(view.userNameTxtFld.getText());

            if (userExists){
                loginUser = true;
                view.errorUsernameLbl.setVisible(false);
            }

            else {
                loginUser = false;
                view.userNameTxtFld.setText("");
                view.errorUsernameLbl.setVisible(true);
            }

            //password abfragen.
            boolean passExists = model.find_Password(view.passwordFld.getText());

            if (passExists) {
                loginPass = true;
                view.errorPassLbl.setVisible(false);
            }

            else {
                loginPass = false;
                view.passwordFld.setText("");
                view.errorPassLbl.setVisible(true);
            }

            // Falls beide Korrekt sind
            if(loginUser && loginPass){
                view.dispose();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }


        }

    }

    /**
     *
     */

    // Falls der User auf Enter drucken .
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            loginUser = false; loginPass = false;

            //username abfragen.
            boolean userExists = model.find_user(view.userNameTxtFld.getText());

            if (userExists){
                loginUser = true;
                view.errorUsernameLbl.setVisible(false);
            }

            else {
                loginUser = false;
                //view.userNameTxtFld.setText("");
                //view.passwordFld.setText("");
                view.errorUsernameLbl.setVisible(true);
            }

            //password abfragen.
            boolean passExists = model.find_Password(view.passwordFld.getText());

            if (passExists) {
                loginPass = true;
                view.errorPassLbl.setVisible(false);
            }

            else {
                loginPass = false;
                //view.userNameTxtFld.setText("");
                //view.passwordFld.setText("");
                view.errorPassLbl.setVisible(true);
            }

            // Falls beide Korrekt sind
            if(loginUser && loginPass){
                view.dispose();
                model.closeConnection();
                IndexUpdatePersonView fensterUpdatePerson = new IndexUpdatePersonView(new IndexUpdatePersonModel(), "InProTUC Datenbank | Person ändern");
            }

        }

    }
    public void keyReleased(KeyEvent e) {


    }
    public void keyTyped(KeyEvent e) {


    }
}
