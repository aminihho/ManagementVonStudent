package View.Update;

import Controller.Update.UpdateMassnahmeController;
import Model.Sonstiges.Functions;
import Model.Update.UpdateMassnahmeModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 20.08.16.
 */
public class UpdateMassnahmeView extends JFrame implements Observer {

    // Model.
    private UpdateMassnahmeModel model;
    // Controller.
    private UpdateMassnahmeController controller;

    public JTextField massnahmeTxtFld;
    public JLabel massnahmeLbl;
    public JButton zurueckBtn, enterBtn;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public UpdateMassnahmeView(UpdateMassnahmeModel _model, String name) {
        setTitle(name);
        model = _model;
        model.addObserver(this);
        //Controller
        controller = new UpdateMassnahmeController(this.model, this);

        makeView();
    }

    /**
     * Erzeugt View ,baut die Oeuberflaeche auf .
     */
    private void makeView() {
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(694, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(make_contentPanel());
        /// Fenster  mit login_controler als Listner
        addWindowListener(controller);
        setVisible(true);
    }

    private JPanel make_contentPanel() {

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        /**
         * Hauptpanel
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(10, 10, 694, 250);
        controlPanel.setLayout(null);


        JPanel panel = new JPanel();
        panel.setBounds(70, 32, 544, 140);
        panel.setBorder(loweredetched);
        panel.setLayout(null);


        /**
         * Titled Border
         */
        TitledBorder title, title2;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Maßnahme ändern");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 18));

        panel.setBorder(title);
        controlPanel.add(panel);


        /**
         * Labels
         */
        massnahmeLbl = new JLabel("Neuer Name:");
        massnahmeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        massnahmeLbl.setBounds(40, 66, 119, 15);
        panel.add(massnahmeLbl);


        /**
         * Text Fields
         */
        massnahmeTxtFld = new JTextField();
        massnahmeTxtFld.setText(model.getMassnahme());
        massnahmeTxtFld.setBounds(177, 62, 325, 27);
        massnahmeTxtFld.addKeyListener(controller);
        massnahmeTxtFld.setBorder(loweredetched);
        panel.add(massnahmeTxtFld);


        /**
         * Buttons
         */
        Icon sendIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        enterBtn = new JButton(sendIcon);
        enterBtn.addActionListener(controller);
        enterBtn.setBorder(loweredetched);
        enterBtn.setFont(tex);
        enterBtn.setBounds(317, 180, 42, 38);
        controlPanel.add(enterBtn);


        Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
        zurueckBtn = new JButton(backIcon);
        zurueckBtn.addActionListener(controller);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(620, 120, 50, 50);
        controlPanel.add(zurueckBtn);

        return controlPanel;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
