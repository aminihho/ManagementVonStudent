package View.Update;

import Controller.Update.UpdateAktivitaetController;
import Model.Sonstiges.Functions;
import Model.Update.UpdateAktivitaetModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.06.16.
 */
public class UpdateAktivitaetView extends JFrame implements Observer{
    // Model.
    private UpdateAktivitaetModel model;
    // Controller.
    private UpdateAktivitaetController controller;

    public JTextField textZeitraum,textBeschreibung, aktName;
    //public JLabel labelAkName;
    public JButton  zurueckBtn, enter2Btn, enterBtn;
    public JComboBox massnahmeCb;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public UpdateAktivitaetView(UpdateAktivitaetModel _model,String name)
    {
        setTitle(name);
        model = _model;
        model.addObserver(this);
        //Controller
        controller = new UpdateAktivitaetController(this.model,this);
        makeView();

    }

    /**
     *  Erzeugt View ,baut die Oberfläche auf .
     */
    private void makeView()
    {
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(694, 410);
        this.setLocationRelativeTo(null);
        this. setResizable(false);
        this.setContentPane(make_contentPanel());
        /// Fenster  mit login_controler als Listner
        addWindowListener( controller);
        setVisible(true);
    }

     private JPanel  make_contentPanel(){

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

         /**
          * Hauptpanel
          */
         JPanel controlPanel = new JPanel();
         controlPanel.setBounds(10, 10, 694, 410);
         controlPanel.setLayout(null);


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
                 loweredetched, "Aktivität ändern");
         title.setTitleJustification(TitledBorder.LEFT);
         title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

         title2 = BorderFactory.createTitledBorder(
                 loweredetched, "Maßnahme der Aktivität ändern");
         title2.setTitleJustification(TitledBorder.LEFT);
         title2.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

         panel.setBorder(title);
         controlPanel.add(panel);

         panel2.setBorder(title2);
         controlPanel.add(panel2);


        /**
         * Labels
         */
        JLabel lblAktivitsname = new JLabel("ID der Aktivität:");
        lblAktivitsname.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblAktivitsname.setBounds(40, 82, 120, 15); //
        panel.add(lblAktivitsname);

        JLabel lblBeschreibung = new JLabel("Aktivität:");
        lblBeschreibung.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblBeschreibung.setBounds(40, 37, 120, 15);
        panel.add(lblBeschreibung);

        JLabel lblZeitraum = new JLabel("Zeitraum:");
        lblZeitraum.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblZeitraum.setBounds(40, 130, 108, 15);
        panel.add(lblZeitraum);

         JLabel massnahmeLbl = new JLabel("Maßnahme:");
         massnahmeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 14));
         massnahmeLbl.setBounds(40, 43, 119, 15);
         panel2.add(massnahmeLbl);

        /**
         * Text Fields
         */


         aktName = new JTextField();
         aktName.setText(model.getAktivitaet());
         aktName.addKeyListener(controller);
         aktName.setBounds(177, 76, 325, 27); //
         aktName.setBorder(loweredetched);
         panel.add(aktName);

         textBeschreibung = new JTextField();
         textBeschreibung.setText(model.getBeschreibung());
         textBeschreibung.setColumns(10);
         textBeschreibung.addKeyListener(controller);
         textBeschreibung.setBorder(loweredetched);
         textBeschreibung.setBounds(177, 31, 325, 27); //
         panel.add(textBeschreibung);

        textZeitraum = new JTextField();
        textZeitraum.setText(model.getZeitraum());
        textZeitraum.addKeyListener(controller);
        textZeitraum.setColumns(10);
        textZeitraum.setBorder(loweredetched);
        textZeitraum.setBounds(177, 124, 325, 27); //
        panel.add(textZeitraum);

         /**
          * Combo Box
          */

         ArrayList<String> massnahmeListe = model.returnMassnahmeName();

         int zeilenAnzahl= massnahmeListe.size(); int i=0;
         String[] massnahmeArray=new String[zeilenAnzahl];

         massnahmeArray[0] = "";
         for(i = 1;i < zeilenAnzahl;i++) {
             massnahmeArray[i] = massnahmeListe.get(i).toString();
         }

         massnahmeCb = new JComboBox(massnahmeArray);
         massnahmeCb.setBounds(178, 37, 325, 27);
         massnahmeCb.addKeyListener(controller);
         massnahmeCb.setFont(new Font("Sans Serif", Font.PLAIN, 14));
         massnahmeCb.setSelectedItem(model.getMassnahme());
         massnahmeCb.addActionListener(controller);
         panel2.add(massnahmeCb);


        /**
         * Buttons
         */

         /**
          * enterBtn = new JButton("enter");
          enterBtn.addActionListener(controller);
          enterBtn.setBorder(loweredetched);
          enterBtn.setFont(tex);
          enterBtn.setBounds(317, 215, 52, 32);
          controlPanel.add(enterBtn);
          */

         Icon sendIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
         enterBtn = new JButton(sendIcon);
         enterBtn.addActionListener(controller);
         enterBtn.setBorder(loweredetched);
         enterBtn.setFont(tex);
         enterBtn.setBounds(317, 335, 42, 38);
         controlPanel.add(enterBtn);

         Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
         zurueckBtn = new JButton(backIcon);
         zurueckBtn.addActionListener(controller);
         zurueckBtn.setBorder(loweredetched);
         zurueckBtn.setFont(tex);
         zurueckBtn.setBounds(620, 270, 50, 50);
         controlPanel.add(zurueckBtn);

        return controlPanel;
    }


    public void update(Observable o, Object arg) {


    }
}
