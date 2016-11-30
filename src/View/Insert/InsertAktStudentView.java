package View.Insert;

import Controller.Insert.InsertAktStudentController;
import Model.Insert.InsertAktStudentModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.04.16.
 */
public class InsertAktStudentView extends JFrame implements Observer{
    private InsertAktStudentModel _model;
    private InsertAktStudentController _controller;

    public JPanel controlPanel1;
    public JButton senden, zurueck;
    public JButton neuerStudentbtn;
    public JButton neueAktivitaetbtn;
    public JTextField studentTextField;
    public JTextField AktivitätTextField;
    public JTextField semesterTextField;
    public JTextField artTextField;
    public JTextArea bemerkungTextArea;
    public JComboBox studentCb;
    public JComboBox aktivitaetCb;
    public JLabel artLbl;
    public JLabel durchfuehrungLbl;
    public JLabel jaLbl;
    public JLabel neinLbl;
    public JRadioButton jaRBtn;
    public JRadioButton neinRBtn;
    public ButtonGroup groupDurchfuehrungRBtn;
    public int bool;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    String[] nameStudent;


    public InsertAktStudentView(InsertAktStudentModel model, String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);

        /**
         * Controller
         */
        _controller = new InsertAktStudentController(this._model, this);

        /**
         * Set JFrame
         */

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 774, 588);
        getContentPane().add(makeJpanel(), BorderLayout.CENTER);
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

    private void adjustPopupWidth(JComboBox box) {
        if (box.getItemCount() == 0) return;
        Object comp = box.getUI().getAccessibleChild(box, 0);
        if (!(comp instanceof JPopupMenu)) {
            return;
        }
        JPopupMenu popup = (JPopupMenu) comp;
        JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        Object value = box.getItemAt(0);
        Component rendererComp = box.getRenderer().getListCellRendererComponent(null, value, 0, false, false);
        if (rendererComp instanceof JTable) {
            scrollPane.setColumnHeaderView(((JTable) rendererComp).getTableHeader());
        }


        Dimension prefSize = rendererComp.getPreferredSize();
        Dimension size = scrollPane.getPreferredSize();
        size.width = Math.max(size.width, prefSize.width);
        scrollPane.setPreferredSize(size);
        scrollPane.setMaximumSize(size);
        scrollPane.revalidate();
    }

    public JPanel makeJpanel() {
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        this.add(makeMenuBar());

        /**
         * Panels
         */
        JPanel contentPane = new JPanel();
        contentPane.setBounds(100, 100, 774, 578);
        contentPane.setLayout(null);

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(145, 68, 484, 260);
        controlPanel.setLayout(null);
        controlPanel.setBorder(loweredetched);
        contentPane.add(controlPanel);

        controlPanel1 = new JPanel();
        controlPanel1.setBounds(145, 353, 484, 143);
        controlPanel1.setLayout(null);
        controlPanel1.setBorder(loweredetched);
        controlPanel1.setEnabled(false);
        contentPane.add(controlPanel1);

        /**
         * Titled Border
         */
        TitledBorder title, title1;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Für Aktivitäten der Art Mobilität");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));
        controlPanel1.setBorder(title);


        /**
         *  Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        senden = new JButton(sendenIcon);
        senden.setFont(tex);
        senden.setBounds(375, 507, 42, 40);
        senden.setBorder(loweredetched);
        senden.setToolTipText("senden");
        senden.addActionListener(_controller);
        this.add(senden);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueck = new JButton(homeIcon);
        zurueck.setFont(tex);
        zurueck.setBounds(687, 446, 49, 49);
        zurueck.setBorder(loweredetched);
        zurueck.addActionListener(_controller);
        this.add(zurueck);

        Icon neuIcon = new ImageIcon(getClass().getResource("/res/neu2.png"));
        neuerStudentbtn = new JButton(neuIcon);
        neuerStudentbtn.setBounds(422, 80, 38, 38);
        neuerStudentbtn.setBorder(loweredetched);
        neuerStudentbtn.setToolTipText("Neue Person eintragen");
        neuerStudentbtn.addActionListener(_controller);
        controlPanel.add(neuerStudentbtn);

        neueAktivitaetbtn = new JButton(neuIcon);
        neueAktivitaetbtn.setBounds(422, 195, 38,38);
        neueAktivitaetbtn.setBorder(loweredetched);
        neueAktivitaetbtn.addActionListener(_controller);
        neueAktivitaetbtn.setToolTipText("Neue Aktivität eintragen");
        controlPanel.add(neueAktivitaetbtn);

        /**
         * Radio Buttons
         */

        jaRBtn = new JRadioButton("ja");
        jaRBtn.setBounds(168, 45, 52, 23);
        jaRBtn.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        jaRBtn.setVisible(false);
        controlPanel1.add(jaRBtn);

        neinRBtn = new JRadioButton("nein");
        neinRBtn.setBounds(240, 45, 65, 23);
        neinRBtn.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        neinRBtn.setVisible(false);
        controlPanel1.add(neinRBtn);

        groupDurchfuehrungRBtn = new ButtonGroup();
        groupDurchfuehrungRBtn.add(jaRBtn);
        groupDurchfuehrungRBtn.add(neinRBtn);


        /**
         *  Labels
         */
        JLabel infoLabel = new JLabel("Aktivität einer Person eintragen");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(145, 38, 317, 25);
        this.add(infoLabel);

        JLabel studentLbl = new JLabel("Person *");
        studentLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        studentLbl.setBounds(35, 40, 89, 27);
        controlPanel.add(studentLbl);


        JLabel aktivitaetLbl = new JLabel("Aktivität *");
        aktivitaetLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        aktivitaetLbl.setBounds(35, 150, 89, 27);
        controlPanel.add(aktivitaetLbl);

        JLabel neuStudentLbl = new JLabel("Neue Person");
        neuStudentLbl.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        neuStudentLbl.setBounds(316, 90, 103, 15);
        controlPanel.add(neuStudentLbl);

        JLabel neuAktLbl = new JLabel("Neue Aktivität");
        neuAktLbl.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        neuAktLbl.setBounds(316, 205, 103, 15);
        controlPanel.add(neuAktLbl);

        artLbl = new JLabel("Art");
        artLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        artLbl.setBounds(35, 85, 39, 27);
        artLbl.setEnabled(false);
        controlPanel1.add(artLbl);

        durchfuehrungLbl = new JLabel("Durchführung");
        durchfuehrungLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        durchfuehrungLbl.setBounds(35, 40, 121, 27);
        durchfuehrungLbl.setEnabled(false);
        controlPanel1.add(durchfuehrungLbl);

        jaLbl = new JLabel("ja");
        jaLbl.setBounds(192, 45, 39, 20);
        jaLbl.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        jaLbl.setEnabled(false);
        controlPanel1.add(jaLbl);

        neinLbl = new JLabel("nein");
        neinLbl.setBounds(268, 45, 39, 20);
        neinLbl.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        neinLbl.setEnabled(false);
        controlPanel1.add(neinLbl);


        /**
         *  Combo Box
         */

        //String für die Namen der Studenten wird hier erstellt
        ArrayList<String> dataStudent;
        dataStudent = _model.returnStundentName();

        String[] dataStudentArray = dataStudent.toArray(new String[dataStudent.size()]);
        int size = dataStudentArray.length;
        int j = 0;
        String[] ergebnisArray = new String[size/3];

        for (int i = 0; i< size/3; i++){
            String vollName = dataStudentArray[j] + ", " + dataStudentArray[j+1] + " – " + dataStudentArray[j+2];
            ergebnisArray[i] = vollName;
            j+=3;
        }

        //String für die Namen der Aktivitaeten wird hier erstellt
        ArrayList<String> dataAktivitaet;
        dataAktivitaet = _model.returnAktivitaetNameUndID();

        String[] dataAktivitaetArray = dataAktivitaet.toArray(new String[dataAktivitaet.size()]);

        int sizeAkt = dataAktivitaetArray.length;
        int k = 0;

        String[] ergebnisArrayAkt = new String[sizeAkt/2];

        for (int l =0; l< sizeAkt/2; l++){
            String aktKomplett = dataAktivitaetArray[k] + " – " + dataAktivitaetArray[k+1];
            ergebnisArrayAkt[l] = aktKomplett;
            k+=2;
        }


        studentCb = new JComboBox(ergebnisArray);
        studentCb.setBounds(140, 40, 320, 27);
        studentCb.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        studentCb.setSelectedItem(_model.defaultObject);
        adjustScrollBar(studentCb);
        studentCb.addActionListener(_controller);
        controlPanel.add(studentCb);

        aktivitaetCb = new JComboBox(ergebnisArrayAkt);
        aktivitaetCb.setBounds(140, 155, 320, 27);
        aktivitaetCb.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        adjustScrollBar(aktivitaetCb);
        //adjustPopupWidth(aktivitaetCb);
        aktivitaetCb.addActionListener(_controller);
        aktivitaetCb.addKeyListener(_controller);
        controlPanel.add(aktivitaetCb);

        /**
         *  Text Fields
         */

        artTextField = new JTextField();
        artTextField.setColumns(10);
        artTextField.setBounds(140, 85, 320, 27);
        artTextField.setBorder(loweredetched);
        artTextField.addKeyListener(_controller);
        artTextField.setEditable(false);
        controlPanel1.add(artTextField);


        return contentPane;
    }


    private JMenuBar makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1100, 21);

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
