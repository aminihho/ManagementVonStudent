package View.Insert;

import Controller.Insert.InsertAktivitaetController;
import Model.Insert.InsertAktivitaetModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 23.04.16.
 */
public class InsertAktivitaetView extends JFrame implements Observer{

    private InsertAktivitaetModel _model;
    private InsertAktivitaetController _controller;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    String[] dataMassnahmeArray;


    public JRadioButton neueMassnahmeRBtn;
    public JRadioButton bestehendeMassnahmeRBtn;
    public JButton btnSenden;
    public JButton btnZurueck;
    public JComboBox bestehendeMassnahmeCb;
    public JTextField aktTextField;
    public JTextField neueMassnahmeTextField;
    public JTextField zeitraumTextField;
    public JTextField aktBeschreibungTxtFld;
    public ButtonGroup group;

    //public JTextArea beschreibungTextArea;


    public InsertAktivitaetView(InsertAktivitaetModel model, String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);

        /**
         * Controller
         */
        _controller = new InsertAktivitaetController(this._model, this);

        /**
         * Set JFrame
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 774, 528);

        /**
         *  getContentPane().add(makeJpanel(), BorderLayout.CENTER);
         */
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(makeMenuBar());
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
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        /**
         * Panels
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(116, 70, 541, 363);
        controlPanel.setBorder(loweredetched);

        /**
         * Labels
         */
        JLabel infoLabel = new JLabel("Neue Aktivität einfügen");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(116, 40, 242, 25);
        this.add(infoLabel);

        JLabel aktLbl = new JLabel("ID der Aktivität *"); //
        aktLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        aktLbl.setBounds(22, 88, 120, 31);
        controlPanel.add(aktLbl);

        JLabel lblBeschreibung = new JLabel("Aktivität *");
        lblBeschreibung.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        lblBeschreibung.setBounds(22, 40, 150, 17);
        controlPanel.add(lblBeschreibung);

        JLabel lblZeitraum = new JLabel("Zeitraum");
        lblZeitraum.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        lblZeitraum.setBounds(22, 150, 84, 17);
        controlPanel.add(lblZeitraum);


        /**
         * Text Fields
         */
        aktTextField = new JTextField();
        aktTextField.setColumns(10);
        aktTextField.setBounds(242, 93, 261, 27);
        aktTextField.setBorder(loweredetched);
        aktTextField.addKeyListener(_controller);
        controlPanel.add(aktTextField);

        aktBeschreibungTxtFld = new JTextField();
        aktBeschreibungTxtFld.setColumns(10);
        aktBeschreibungTxtFld.setBounds(242, 40, 261, 27); //
        aktBeschreibungTxtFld.setBorder(loweredetched);
        aktBeschreibungTxtFld.addKeyListener(_controller);
        controlPanel.add(aktBeschreibungTxtFld);

        neueMassnahmeTextField = new JTextField();
        neueMassnahmeTextField.setColumns(10);
        neueMassnahmeTextField.setBounds(242, 220, 261, 27);
        neueMassnahmeTextField.setBorder(loweredetched);
        neueMassnahmeTextField.addKeyListener(_controller);
        neueMassnahmeTextField.setEditable(false);
        controlPanel.add(neueMassnahmeTextField);

        zeitraumTextField = new JTextField();
        zeitraumTextField.setColumns(10);
        zeitraumTextField.setBounds(242, 145, 261, 27);
        zeitraumTextField.setBorder(loweredetched);
        zeitraumTextField.addKeyListener(_controller);
        controlPanel.add(zeitraumTextField);



        /**
         * Combo Box
         */

        //String für die Namen der Status wird hier erstellt
        ArrayList<String> dataMassnahme;
        dataMassnahme = _model.returnMassnahmeName();
        String[] dataMassnahmeArray = new String[dataMassnahme.size()+1];
        dataMassnahmeArray[0] = "-";
        int k = 0;

        for(int i = 1; i<= dataMassnahme.size(); i++){
            dataMassnahmeArray[i] = dataMassnahme.get(k);
            k++;
        }

        bestehendeMassnahmeCb = new JComboBox(dataMassnahmeArray);
        bestehendeMassnahmeCb.setBounds(242, 270, 261, 27);
        bestehendeMassnahmeCb.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        bestehendeMassnahmeCb.addKeyListener(_controller);
        adjustScrollBar(bestehendeMassnahmeCb);
        bestehendeMassnahmeCb.setEnabled(false);
        controlPanel.add(bestehendeMassnahmeCb);



        /**
         * Radio Button
         */
        neueMassnahmeRBtn = new JRadioButton("Neue Maßnahme");
        neueMassnahmeRBtn.setBounds(22, 222, 177, 23);
        neueMassnahmeRBtn.addActionListener(_controller);
        neueMassnahmeRBtn.addItemListener(_controller);
        neueMassnahmeRBtn.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        controlPanel.add(neueMassnahmeRBtn);

        bestehendeMassnahmeRBtn = new JRadioButton("Bestehende Maßnahme");
        bestehendeMassnahmeRBtn.setBounds(22, 273, 203, 23);
        bestehendeMassnahmeCb.addActionListener(_controller);
        bestehendeMassnahmeRBtn.addItemListener(_controller);
        bestehendeMassnahmeRBtn.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        controlPanel.add(bestehendeMassnahmeRBtn);

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(neueMassnahmeRBtn);
        group.add(bestehendeMassnahmeRBtn);

        /**
         * Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        btnSenden = new JButton(sendenIcon);
        btnSenden.setBounds(371, 445, 42, 40);
        btnSenden.setFont(tex);
        btnSenden.setToolTipText("senden");
        btnSenden.setBorder(loweredetched);
        btnSenden.addActionListener(_controller);
        this.add(btnSenden);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        btnZurueck = new JButton(homeIcon);
        btnZurueck.setBounds(686, 385, 49, 49);
        btnZurueck.setFont(tex);
        btnZurueck.setBorder(loweredetched);
        btnZurueck.addActionListener(_controller);
        this.add(btnZurueck);


        return controlPanel;
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
