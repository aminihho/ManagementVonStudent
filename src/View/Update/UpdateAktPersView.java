package View.Update;

import Controller.Update.UpdateAktPersController;
import Model.Sonstiges.Functions;
import Model.Update.UpdateAktPersModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 31.08.16.
 */
public class UpdateAktPersView extends JFrame implements Observer {

    // Model.
    private UpdateAktPersModel model;
    // Controller.
    private UpdateAktPersController controller;



    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    public JPanel panel_2;
    public JLabel durchführungLbl, artLbl;
    public JRadioButton jaRBtn, neinRBtn;
    public JButton enterBtn, zurueckBtn, refreshBtn;
    public JComboBox aktCb;
    public JTextField artAktTxtFld;
    public ButtonGroup groupDurchfuehrungRBtn;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);
    Font tex12 = new Font("Sans Serif", Font.PLAIN, 12);

    public UpdateAktPersView(UpdateAktPersModel _model, String name){
        model = _model;
        model.addObserver(this);
        //Controller
        controller = new UpdateAktPersController(this.model, this);
        setTitle(name);
        /**
         * Set JFrame
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 400);

        /**
         *  getContentPane()
         */
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.add(makeJpanel());
        setVisible(true);

    }

    private void adjustScrollBar(JComboBox box) {
        if (box.getItemCount() == 0) return;
        Object comp = box.getUI().getAccessibleChild(box, 0);
        if (!(comp instanceof JPopupMenu)) {
            return;
        }
        JPopupMenu popup = (JPopupMenu) comp;
        JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public JPanel makeJpanel() {
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(10, 10, 640, 400);

        /**
         * Panel
         */
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(60, 22, 492, 122);
        panel_1.setLayout(null);

        panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setEnabled(false);
        panel_2.setBounds(60, 166, 492, 130);

        controlPanel.add(panel_1);
        controlPanel.add(panel_2);

        /**
         * Titled Border
         */
        TitledBorder title, title2;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Aktivität einer Person ändern");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        title2 = BorderFactory.createTitledBorder(
                loweredetched, "Für Aktivitäten der Art Mobilität");
        title2.setTitleJustification(TitledBorder.LEFT);
        title2.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        panel_1.setBorder(title);
        panel_2.setBorder(title2);

        /**
         * Labels
         */
        JLabel aktLbl = new JLabel("neue Aktivität");
        aktLbl.setBounds(33, 57, 128, 19);
        aktLbl.setFont(tex);
        panel_1.add(aktLbl);

        durchführungLbl = new JLabel("Durchführung");
        durchführungLbl.setEnabled(false);
        durchführungLbl.setFont(tex);
        durchführungLbl.setBounds(33, 34, 121, 27);
        panel_2.add(durchführungLbl);

        artLbl = new JLabel("Art");
        artLbl.setEnabled(false);
        artLbl.setFont(tex);
        artLbl.setBounds(33, 73, 39, 27);
        panel_2.add(artLbl);

        /**
         * Combo Box
         */
        //String für die Namen der Aktivitaeten wird hier erstellt
        ArrayList<String> dataAktivitaet = model.returnAktivitaetNameUndID();
        String[] dataAktivitaetArray = dataAktivitaet.toArray(new String[dataAktivitaet.size()]);
        int sizeAkt = dataAktivitaetArray.length;
        int k = 0;

        String[] ergebnisArrayAkt = new String[sizeAkt/2];

        for (int l =0; l< sizeAkt/2; l++){
            String aktKomplett = dataAktivitaetArray[k] + " – " + dataAktivitaetArray[k+1];
            ergebnisArrayAkt[l] = aktKomplett;
            k+=2;
        }

        aktCb = new JComboBox(ergebnisArrayAkt);
        adjustScrollBar(aktCb);
        aktCb.setBounds(150, 53, 310, 27);
        aktCb.setSelectedItem(model.defaultObject);
        aktCb.addActionListener(controller);
        aktCb.addKeyListener(controller);
        aktCb.setFont(tex12);
        panel_1.add(aktCb);

        /**
         * Rafio Button
         */
        jaRBtn = new JRadioButton("ja");
        jaRBtn.setFont(tex);
        jaRBtn.setEnabled(false);
        jaRBtn.setBorder(loweredetched);
        jaRBtn.setBounds(150, 36, 52, 23);
        panel_2.add(jaRBtn);

        neinRBtn = new JRadioButton("nein");
        neinRBtn.setFont(tex);
        neinRBtn.setEnabled(false);
        neinRBtn.setBorder(loweredetched);
        neinRBtn.setBounds(220, 36, 65, 23);
        panel_2.add(neinRBtn);

        groupDurchfuehrungRBtn = new ButtonGroup();
        groupDurchfuehrungRBtn.add(jaRBtn);
        groupDurchfuehrungRBtn.add(neinRBtn);

        /**
         * TextField
         */
        artAktTxtFld = new JTextField();
        artAktTxtFld.setColumns(10);
        artAktTxtFld.setEnabled(false);
        artAktTxtFld.setBorder(loweredetched);
        artAktTxtFld.setBounds(150, 73, 310, 27);
        panel_2.add(artAktTxtFld);

        /**
         * Button
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        enterBtn = new JButton(sendenIcon);
        enterBtn.setFont(tex);
        enterBtn.setBorder(loweredetched);
        enterBtn.setBounds(285, 306, 42, 38);
        enterBtn.addActionListener(controller);
        enterBtn.addKeyListener(controller);
        controlPanel.add(enterBtn);

        Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
        zurueckBtn = new JButton(backIcon);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.addActionListener(controller);
        zurueckBtn.setBounds(560, 244, 50, 50);
        controlPanel.add(zurueckBtn);



        return  controlPanel;

    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
