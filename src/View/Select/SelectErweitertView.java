package View.Select;

import Controller.Select.SelectEinfachController;
import Controller.Select.SelectErweitertController;
import Model.Select.SelectEinfachModel;
import Model.Select.SelectErweitertModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by annelie on 06.05.16.
 */
public class SelectErweitertView extends JFrame implements Observer {

    private SelectErweitertModel _model;
    private SelectErweitertController _controller;

    public JButton senden;
    public JRadioButton union, schnittmenge;
    public JButton zurueck;
    public JButton addAtt2, addAtt3;
    public JButton minusAtt2, minusAtt3;
    public JLabel att2Lbl, wert2Lbl, att3Lbl, wert3Lbl;
    public JComboBox attributCB, wertCB, attributCB2, wertCB2, attributCB3, wertCB3;
    public  JTextField wertAttTextField;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    String[] attString;

    public SelectErweitertView(SelectErweitertModel model,String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);


        /**
         * Controller
         */
        _controller = new SelectErweitertController(this._model, this);


        /**
         * Set JFrame
         */

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 774, 528);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(makeJpanel());
        setLocationRelativeTo(null);
        setBackground(Color.darkGray);
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
        JMenuBar menuBar = makeMenuBar();
        /**
         * Panels
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        controlPanel.setBounds(0, 0, 774, 528);
        controlPanel.setLayout(null);
        controlPanel.add(menuBar);

        JPanel inhaltPanel = new JPanel();
        inhaltPanel.setBounds(146, 79, 484, 345);
        inhaltPanel.setLayout(null);
        controlPanel.add(inhaltPanel);

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        inhaltPanel.setBorder(loweredetched);


        /**
         * Labels
         */
        JLabel infoLabel = new JLabel("Erweiterte Suche");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(145, 44, 242, 25);
        controlPanel.add(infoLabel);

        //Label & Wert 1
        JLabel attLbl = new JLabel("Attribut:");
        attLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        attLbl.setBounds(30, 25, 369, 24);
        inhaltPanel.add(attLbl);

        JLabel wertLbl = new JLabel("Wert:");
        wertLbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        wertLbl.setBounds(30, 65, 369, 29);
        inhaltPanel.add(wertLbl);


        //Label & Wert 2
        att2Lbl = new JLabel("Attribut:");
        att2Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        att2Lbl.setBounds(30, 135, 369, 24);
        att2Lbl.setEnabled(false);
        inhaltPanel.add(att2Lbl);

        wert2Lbl = new JLabel("Wert:");
        wert2Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        wert2Lbl.setBounds(30, 175, 369, 29);
        wert2Lbl.setEnabled(false);
        inhaltPanel.add(wert2Lbl);


        //Label & Wert 3
        att3Lbl = new JLabel("Attribut:");
        att3Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        att3Lbl.setBounds(30, 249, 369, 24);
        att3Lbl.setEnabled(false);
        inhaltPanel.add(att3Lbl);

        wert3Lbl = new JLabel("Wert:");
        wert3Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        wert3Lbl.setBounds(30, 289, 369, 29);
        wert3Lbl.setEnabled(false);
        inhaltPanel.add(wert3Lbl);


        /**
         * Combo Box
         */
        attString = new String[]{"-", "Student - Nachname", "Student - Vorname", "Urz (TUC)", "Aktivität - Name und ID", "Maßnahme - Name", "Status", "Fakultät"};
        attributCB = new JComboBox(attString);
        attributCB.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        attributCB.addActionListener(_controller);
        attributCB.setBounds(115, 25, 340, 28);
        inhaltPanel.add(attributCB);


        String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
        wertCB = new JComboBox();
        wertCB.setModel(model);
        wertCB.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        wertCB.addActionListener(_controller);
        wertCB.setBounds(115, 65, 340, 28);
        wertCB.setEnabled(false);
        adjustScrollBar(wertCB);
        inhaltPanel.add(wertCB);


        attributCB2 = new JComboBox(attString);
        attributCB2.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        attributCB2.addActionListener(_controller);
        attributCB2.setEnabled(false);
        attributCB2.setBounds(115, 135, 340, 28);
        inhaltPanel.add(attributCB2);


        DefaultComboBoxModel model2 = new DefaultComboBoxModel(nameArray);
        wertCB2 = new JComboBox();
        wertCB2.setModel(model2);
        wertCB2.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        wertCB2.addActionListener(_controller);
        wertCB2.setBounds(115, 175, 340, 28);
        wertCB2.setEnabled(false);
        adjustScrollBar(wertCB2);
        inhaltPanel.add(wertCB2);

        attributCB3 = new JComboBox(attString);
        attributCB3.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        attributCB3.addActionListener(_controller);
        attributCB3.setEnabled(false);
        attributCB3.setBounds(115, 249, 340, 28);
        inhaltPanel.add(attributCB3);


        DefaultComboBoxModel model3 = new DefaultComboBoxModel(nameArray);
        wertCB3 = new JComboBox();
        wertCB3.setModel(model3);
        wertCB3.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        wertCB3.addActionListener(_controller);
        wertCB3.setBounds(115, 289, 340, 28);
        wertCB3.setEnabled(false);
        adjustScrollBar(wertCB3);
        inhaltPanel.add(wertCB3);


        /**
         * Text Fields
         * wertAttTextField = new JTextField();
         wertAttTextField.setBounds(55, 236, 369, 27);
         inhaltPanel.add(wertAttTextField);
         */


        /**
         * Raydio Buttons
         */

        Icon unionIcon = new ImageIcon(getClass().getResource("/res/or32.png"));
        JLabel labelUnion = new JLabel(unionIcon);
        labelUnion.setBounds(590, 35, 42, 40); //
        labelUnion.setToolTipText("Das Suchergebnis enthält alle Personen, die mindestens eine der Bedingungen erfüllen");
        controlPanel.add(labelUnion);

        union = new JRadioButton();
        union.setFont(tex);
        union.setBounds(572, 50, 20, 12); //
        union.addActionListener(_controller);
        union.setBorder(loweredetched);
        union.setToolTipText("Die Ergebnismenge enthält alle Personen, die mindestens eine der Bedingungen erfüllen");
        controlPanel.add(union);

        Icon schnittmengeIcon = new ImageIcon(getClass().getResource("/res/and32.png"));
        JLabel labelSchnittmenge = new JLabel(schnittmengeIcon);
        labelSchnittmenge.setBounds(498, 35, 42, 40);
        labelSchnittmenge.setToolTipText("Die Ergebnismenge enthält nur Personen, die alle Bedingungen erfüllen");
        controlPanel.add(labelSchnittmenge);

        schnittmenge = new JRadioButton();
        schnittmenge.setFont(tex);
        schnittmenge.setSelected(true);
        schnittmenge.setBounds(480, 50, 20, 12);
        schnittmenge.addActionListener(_controller);
        schnittmenge.setBorder(loweredetched);
        schnittmenge.setToolTipText("Die Ergebnismenge enthält nur Personen, die alle Bedingungen erfüllen");
        controlPanel.add(schnittmenge);

        //Gruppe
        ButtonGroup group = new ButtonGroup();
        group.add(union);
        group.add(schnittmenge);


        /**
         * Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        senden = new JButton(sendenIcon);
        senden.setFont(tex);
        senden.setBounds(371, 440, 42, 40);
        senden.addActionListener(_controller);
        senden.setBorder(loweredetched);
        controlPanel.add(senden);

        Icon zurueckIcon = new ImageIcon("images/undo-outline-48.png");
        zurueck = new JButton("<html>zur<br>ück</html>");
        zurueck.setFont(tex);
        zurueck.setBounds(686, 385, 49, 49);
        zurueck.addActionListener(_controller);
        zurueck.setBorder(loweredetched);
        //controlPanel.add(zurueck);

        Icon addIcon = new ImageIcon(getClass().getResource("/res/plus.png"));
        addAtt2 = new JButton(addIcon);
        addAtt2.setBounds(30, 100, 27, 27); //427
        addAtt2.addActionListener(_controller);
        addAtt2.setBorder(loweredetched);
        inhaltPanel.add(addAtt2);

        addAtt3 = new JButton(addIcon);
        addAtt3.setBounds(30, 213, 27, 27); //427
        addAtt3.addActionListener(_controller);
        addAtt3.setBorder(loweredetched);
        inhaltPanel.add(addAtt3);

        Icon subIcon = new ImageIcon(getClass().getResource("/res/minus.png"));
        minusAtt2 = new JButton(subIcon);
        minusAtt2.setBounds(65, 100, 27, 27); //427
        minusAtt2.addActionListener(_controller);
        minusAtt2.setVisible(false);
        minusAtt2.setBorder(loweredetched);
        inhaltPanel.add(minusAtt2);

        minusAtt3 = new JButton(subIcon);
        minusAtt3.setBounds(65, 213, 27, 27); //427
        minusAtt3.addActionListener(_controller);
        minusAtt3.setVisible(false);
        minusAtt3.setBorder(loweredetched);
        inhaltPanel.add(minusAtt3);


        return controlPanel;
    }

    private JMenuBar makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 774, 21);

        /**
         *  Menu Datei
         */
        JMenu dateiMnBar = new JMenu("Datei");
        dateiMnBar.setFont(tex);
        menuBar.add(dateiMnBar);

        JMenuItem druckenMn = new JMenuItem("Drucken");
        druckenMn.setFont(tex);
        druckenMn.addActionListener(_controller);
        dateiMnBar.add(druckenMn);

        JMenu exportMnBar = new JMenu("Exportieren");
        exportMnBar.setFont(tex);
        dateiMnBar.add(exportMnBar);

        JMenuItem excelMn = new JMenuItem("Datenbank als Excel-Tabelle exportieren");
        excelMn.setFont(tex);
        excelMn.addActionListener(_controller);
        exportMnBar.add(excelMn);

        JMenuItem pfdMn = new JMenuItem("Datenbank als Pdf exportieren");
        pfdMn.setFont(tex);
        pfdMn.addActionListener(_controller);
        exportMnBar.add(pfdMn);

        /**
         * Menu Suchen
         */
        JMenu suchenMnBar = new JMenu("Suchen");
        suchenMnBar.setFont(tex);
        menuBar.add(suchenMnBar);

        JMenuItem einfacheSucheMn = new JMenuItem("Einfache Suche");
        einfacheSucheMn.setFont(tex);
        einfacheSucheMn.addActionListener(_controller);
        suchenMnBar.add(einfacheSucheMn);

        JMenuItem optimierteSuche = new JMenuItem("Erweiterte Suche");
        optimierteSuche.setFont(tex);
        optimierteSuche.addActionListener(_controller);
        suchenMnBar.add(optimierteSuche);

        /**
         * Menu Einfügen
         */
        JMenu einfügenMnBar = new JMenu("Einfügen");
        einfügenMnBar.setFont(tex);
        menuBar.add(einfügenMnBar);
        JMenuItem aktStudentMn  = new JMenuItem(INSERT_AKT_STU);
        aktStudentMn.setFont(tex);
        aktStudentMn.addActionListener(_controller);
        einfügenMnBar.add(aktStudentMn);


        JMenuItem statusStudentMn  = new JMenuItem(INSERT_STATUS_STUDENT);
        statusStudentMn.setFont(tex);
        statusStudentMn.addActionListener(_controller);
        einfügenMnBar.add(statusStudentMn);

        JMenuItem bemStudentMn  = new JMenuItem(INSERT_BEMERKUNG);
        bemStudentMn.setFont(tex);
        bemStudentMn.addActionListener(_controller);
        einfügenMnBar.add(bemStudentMn);

        JMenuItem neuerStudentMn = new JMenuItem(INSERT_STUDENT);
        neuerStudentMn.setFont(tex);
        neuerStudentMn.addActionListener(_controller);
        einfügenMnBar.add(neuerStudentMn);

        JMenuItem neueAktMn  = new JMenuItem(INSERT_AKT);
        neueAktMn.setFont(tex);
        neueAktMn.addActionListener(_controller);
        einfügenMnBar.add(neueAktMn);


        JMenuItem neuerStatusMn  = new JMenuItem(INSERT_STATUS);
        neuerStatusMn.setFont(tex);
        neuerStatusMn.addActionListener(_controller);
        einfügenMnBar.add(neuerStatusMn);


        /**
         * Menu Ändern
         */
        JMenu aendernMnBar = new JMenu("Ändern");
        aendernMnBar.setFont(tex);
        menuBar.add(aendernMnBar);

        JMenuItem aendernStuMn = new JMenuItem(UPDATE_STUDENT);
        aendernStuMn.setFont(tex);
        aendernStuMn.addActionListener(_controller);
        aendernMnBar.add(aendernStuMn);

        JMenuItem aendernAktMn = new JMenuItem(UPDATE_AKTIVITAET);
        aendernAktMn.setFont(tex);
        aendernAktMn.addActionListener(_controller);
        aendernMnBar.add(aendernAktMn);

        JMenuItem aendernStatusMn = new JMenuItem(UPDATE_STATUS);
        aendernStatusMn.setFont(tex);
        aendernStatusMn.addActionListener(_controller);
        aendernMnBar.add(aendernStatusMn);

        JMenuItem aendernMassnahme = new JMenuItem(UPDATE_MASSNAHME);
        aendernMassnahme.setFont(tex);
        aendernMassnahme.addActionListener(_controller);
        aendernMnBar.add(aendernMassnahme);

        return menuBar;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
