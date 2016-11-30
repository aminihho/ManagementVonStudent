package View.Update;

import Controller.Update.InsertAktivitaetControllerKlein;
import Model.Sonstiges.Functions;
import Model.Update.InsertAktivitaetModelKlein;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.08.16.
 */
public class InsertAktivitaetViewKlein extends JFrame implements Observer {

    // Model.
    private InsertAktivitaetModelKlein model;
    // Controller.
    private InsertAktivitaetControllerKlein controller;

    public JTextField idTxtFld, nameTxtFld, zeitraumTxtFld;
    public JComboBox massnahmeCb;
    //public JLabel massnahmeLbl;
    public JButton zurueckBtn, enterBtn;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public InsertAktivitaetViewKlein(InsertAktivitaetModelKlein _model, String name) {
        setTitle(name);
        model = _model;
        model.addObserver(this);
        //Controller
        controller = new InsertAktivitaetControllerKlein(this.model, this);

        makeView();
    }

    /**
     * Erzeugt View ,baut die Oeuberflaeche auf .
     */
    private void makeView() {
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(694, 410);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(make_contentPanel());
        /// Fenster  mit login_controler als Listner
        //addWindowListener(controller);
        setVisible(true);
    }

    private JPanel make_contentPanel() {

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        /**
         * Hauptpanel
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(10, 10, 694, 410);
        controlPanel.setLayout(null);

        /**
         * Panel mit Border
         */
        JPanel panel = new JPanel();
        panel.setBounds(64, 26, 544, 177);
        panel.setBorder(loweredetched);
        panel.setLayout(null);

        JPanel panel2 = new JPanel();
        panel2.setBounds(64, 230, 544, 92);
        panel2.setBorder(loweredetched);
        panel2.setLayout(null);


        /**
         * Titled Border
         */
        TitledBorder title, title2;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Neue Aktivität");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        title2 = BorderFactory.createTitledBorder(
                loweredetched, "Maßnahme der neuen Aktivität");
        title2.setTitleJustification(TitledBorder.LEFT);
        title2.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        panel.setBorder(title);
        controlPanel.add(panel);

        panel2.setBorder(title2);
        controlPanel.add(panel2);


        /**
         * Labels
         */

        JLabel idLbl = new JLabel("ID der Aktivität *");
        idLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        idLbl.setBounds(40, 82, 150, 15); //
        panel.add(idLbl);

        JLabel nameLbl = new JLabel("Aktivität *");
        nameLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        nameLbl.setBounds(40, 37, 150, 15);
        panel.add(nameLbl);

        JLabel zeitraumLbl = new JLabel("Zeitraum");
        zeitraumLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        zeitraumLbl.setBounds(40, 130, 150, 15);
        panel.add(zeitraumLbl);

        JLabel massnahmeLbl = new JLabel("Maßnahme *");
        massnahmeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        massnahmeLbl.setBounds(40, 43, 150, 15);
        panel2.add(massnahmeLbl);


        /**
         * Text Fields
         */
        idTxtFld = new JTextField();
        idTxtFld.setBounds(177, 76, 325, 27); //
        idTxtFld.setBorder(loweredetched);
        idTxtFld.addKeyListener(controller);
        panel.add(idTxtFld);

        nameTxtFld = new JTextField();
        nameTxtFld.setBounds(177, 31, 325, 27);
        nameTxtFld.addKeyListener(controller);
        nameTxtFld.setBorder(loweredetched);
        panel.add(nameTxtFld);

        zeitraumTxtFld = new JTextField();
        zeitraumTxtFld.setBounds(177, 124, 325, 27);
        zeitraumTxtFld.setBorder(loweredetched);
        zeitraumTxtFld.addKeyListener(controller);
        panel.add(zeitraumTxtFld);

        /**
         * Combo Box
         */
        Functions funktion = new Functions();

        ArrayList<String> dataMassnahme = model.returnMassnahmeName();
        String[] dataArray = funktion.arrayListTo1DStringFuerCB(dataMassnahme);

        massnahmeCb = new JComboBox(dataArray);
        massnahmeCb.setBounds(178, 37, 325, 27);
        massnahmeCb.addKeyListener(controller);
        massnahmeCb.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        //massnahmeCb.setSelectedItem(model.getMassnahme());
        massnahmeCb.addActionListener(controller);
        panel2.add(massnahmeCb);


        /**
         * Buttons
         */
        Icon sendIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        enterBtn = new JButton(sendIcon);
        enterBtn.addActionListener(controller);
        enterBtn.addKeyListener(controller);
        enterBtn.setBorder(loweredetched);
        enterBtn.setFont(tex);
        enterBtn.setBounds(317, 335, 42, 40);
        controlPanel.add(enterBtn);


        Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
        zurueckBtn = new JButton(backIcon);
        zurueckBtn.addActionListener(controller);
        zurueckBtn.addKeyListener(controller);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(620, 270, 50, 50);
        controlPanel.add(zurueckBtn);

        return controlPanel;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
