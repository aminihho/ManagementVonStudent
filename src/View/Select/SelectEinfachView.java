package View.Select;

import Controller.Select.SelectEinfachController;
import Model.Select.SelectEinfachModel;

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
public class SelectEinfachView extends JFrame implements Observer {

    private SelectEinfachModel _model;
    private SelectEinfachController _controller;

    public JButton senden;
    public JButton zurueck;
    public JComboBox attributCB, wertCB;
    public  JTextField wertAttTextField;


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Daten einer Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    String[] attString;

    public SelectEinfachView(SelectEinfachModel model,String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);


        /**
         * Controller
         */
        _controller = new SelectEinfachController(this._model, this);


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
        JLabel infoLabel = new JLabel("Einfache Suche");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(145, 44, 242, 25);
        controlPanel.add(infoLabel);

        JLabel attLbl = new JLabel("Nach welchem Attribut möchten Sie suchen?");
        attLbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        attLbl.setBounds(55, 57, 369, 24);
        inhaltPanel.add(attLbl);

        JLabel wertLbl = new JLabel("Wählen Sie einen Wert aus:");
        wertLbl.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        wertLbl.setBounds(55, 189, 369, 29);
        inhaltPanel.add(wertLbl);


        /**
         * Combo Box
         */
        attString = new String[]{"-", "Student - Nachname", "Student - Vorname", "Urz (TUC)", "Aktivität - Name und ID", "Maßnahme - Name", "Status", "Fakultät"};
        attributCB = new JComboBox(attString);
        attributCB.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        attributCB.addActionListener(_controller);
        attributCB.setBounds(55, 104, 369, 28);
        inhaltPanel.add(attributCB);


        String[] nameArray = new String[]{"Wählen Sie einen Attribut aus"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(nameArray);
        wertCB = new JComboBox();
        wertCB.setModel(model);
        wertCB.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        wertCB.addActionListener(_controller);
        wertCB.setBounds(55, 236, 369, 28);
        wertCB.setEnabled(false);
        adjustScrollBar(wertCB);
        inhaltPanel.add(wertCB);


        /**
         * Text Fields
         * wertAttTextField = new JTextField();
         wertAttTextField.setBounds(55, 236, 369, 27);
         inhaltPanel.add(wertAttTextField);
         */


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

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueck = new JButton(homeIcon);
        zurueck.setFont(tex);
        zurueck.setBounds(686, 378, 49, 49);
        zurueck.addActionListener(_controller);
        zurueck.setBorder(loweredetched);
        controlPanel.add(zurueck);


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
