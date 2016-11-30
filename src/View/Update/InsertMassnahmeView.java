package View.Update;

import Controller.Update.InsertMassnahmeController;
import Model.Update.InsertMassnahmeModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 22.08.16.
 */
public class InsertMassnahmeView extends JFrame implements Observer {
    // Model.
    private InsertMassnahmeModel model;
    // Controller.
    private InsertMassnahmeController controller;

    public JTextField massnahmeTxtFld;
    public JLabel massnahmeLbl;
    public JButton zurueckBtn, enterBtn;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public InsertMassnahmeView(InsertMassnahmeModel _model, String name) {
        setTitle(name);
        model = _model;
        model.addObserver(this);
        //Controller
        controller = new InsertMassnahmeController(this.model, this);

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
        controlPanel.setBounds(10, 10, 694, 250);
        controlPanel.setLayout(null);


        JPanel panel = new JPanel();
        panel.setBounds(70, 32, 544, 125);
        panel.setBorder(loweredetched);
        panel.setLayout(null);


        /**
         * Titled Border
         */
        TitledBorder title, title2;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Neue Maßnahme");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 18));

        panel.setBorder(title);
        controlPanel.add(panel);


        /**
         * Labels
         */
        massnahmeLbl = new JLabel("Name:");
        massnahmeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        massnahmeLbl.setBounds(40, 60, 123, 15);
        panel.add(massnahmeLbl);


        /**
         * Text Fields
         */
        massnahmeTxtFld = new JTextField();
        massnahmeTxtFld.setBounds(177, 56, 325, 27);
        massnahmeTxtFld.setBorder(loweredetched);
        massnahmeTxtFld.addKeyListener(controller);
        panel.add(massnahmeTxtFld);


        /**
         * Buttons
         */
        Icon sendIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        enterBtn = new JButton(sendIcon);
        enterBtn.addActionListener(controller);
        enterBtn.addKeyListener(controller);
        enterBtn.setBorder(loweredetched);
        enterBtn.setFont(tex);
        enterBtn.setBounds(317, 168, 42, 40);
        controlPanel.add(enterBtn);


        Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
        zurueckBtn = new JButton(backIcon);
        zurueckBtn.addActionListener(controller);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(620, 105, 50, 50);
        controlPanel.add(zurueckBtn);

        return controlPanel;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
