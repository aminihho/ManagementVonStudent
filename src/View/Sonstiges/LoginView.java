package View.Sonstiges;

import Controller.Sonstiges.LoginController;
import Model.Sonstiges.LoginModel;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * Created by annelie on 13.06.16.
 */
public class LoginView extends JFrame implements Observer   {

    /// zum View gehoeriger Model
    public LoginModel _loginModel;
    /// zum View gehoeriger Controller
    private LoginController _loginControler;

    public static final String  ACTION_LOGIN = "login";
    public JPasswordField passwordFld;
    public JTextField userNameTxtFld;
    public JLabel errorUsernameLbl, errorPassLbl;
    public JLabel usernameLbl, passwortLbl;
    public JButton sendBtn;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    /*---------------------------------------------------------------------------------------
     * Kostruktor
     * ---------------------------------------------------------------------------------------*/
    public LoginView (LoginModel model,String titel ){
        //MODEL
        this._loginModel=model;
        this._loginModel.addObserver(this);
        //CONTROLLER
        _loginControler = new LoginController(this._loginModel,this);
        //VIEW
        super.setTitle(titel);
        makeView();
    }

    private void makeView(){
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 195);
        this.setLocationRelativeTo(null);
        this. setResizable(false);
        this.setContentPane(make_contentPanel());
        /// Fenster  mit login_controler als Listner
        addWindowListener( _loginControler);
        setVisible(true);
    }

    private JPanel  make_contentPanel(){
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        JPanel panel = new JPanel();
        panel.setBounds(31, 89, 400, 195);
        panel.setLayout(null);


        /**
         * Username
         */
        usernameLbl = new JLabel("Username: ");
        usernameLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        usernameLbl.setBounds(32, 38, 150, 30);
        panel.add(usernameLbl);

        userNameTxtFld = new JTextField();
        userNameTxtFld.setBounds(122, 41, 241, 27);
        userNameTxtFld.setBorder(loweredetched);
        userNameTxtFld.addKeyListener( _loginControler );
        panel.add(userNameTxtFld);

        errorUsernameLbl = new JLabel("Der Username ist falsch");
        errorUsernameLbl.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        errorUsernameLbl.setVisible(false);
        errorUsernameLbl.setForeground(Color.RED);
        errorUsernameLbl.setBounds(122, 12, 250, 30); //122, 65, 250, 30
        panel.add(errorUsernameLbl);

        /**
         * Passwort
         */
        passwortLbl = new JLabel("Passwort: ");
        passwortLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        passwortLbl.setBounds(32, 90, 150, 30);
        panel.add(passwortLbl);

        passwordFld = new JPasswordField();
        passwordFld.setBounds(122, 93, 241, 27);
        passwordFld.setBorder(loweredetched);
        passwordFld.addActionListener(_loginControler);
        passwordFld.addKeyListener(_loginControler);
        panel.add(passwordFld);

        errorPassLbl = new JLabel("Das Passwort ist falsch");
        errorPassLbl.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        errorPassLbl.setVisible(false);
        errorPassLbl.setForeground(Color.RED);
        errorPassLbl.setBounds(122, 65, 250, 30); //122, 117, 250, 30
        panel.add(errorPassLbl);

        /**
         * Button
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        sendBtn = new JButton("login");
        sendBtn.setBorder(loweredetched);
        sendBtn.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        sendBtn.addActionListener(_loginControler);
        sendBtn.setBounds(175, 145, 50, 25);
        panel.add(sendBtn);


        return panel;
    }

    /*---------------------------------------------------------------------------------------
     *     Desinstaliert MVC
     * ---------------------------------------------------------------------------------------*/
    public void release(){
        /// Contorler Desinstalieren
        //to do enchala
        /// Model Desinstalieren
        // to do enchlala
    }
    /*-----------------------------------------------------------------------------------------
  	 *   Ueberschreibt Interfacemehtode , legt Reaktion auf Aenderungen fest
  	 * ---------------------------------------------------------------------------------------*/
    public void update(Observable m , Object o ){
        /// to do
    }

}
