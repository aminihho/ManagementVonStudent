package View.Insert;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import Controller.Insert.InsertStudentController;
import Model.Insert.InsertStudentModel;

/**
 * Created by annelie on 18.04.16.
 */
public class InsertStudentVIew extends JFrame implements Observer{
    private InsertStudentModel _model;
    private InsertStudentController _controller;

    public JButton senden, zurueck;
    public JTextField vornameTextField;
    public JTextField nachnameTextField;
    public JTextField urzTextField;
    public JTextField textField_4;
    public JTextField fakuTextField;
    public JTextField telTextField;
    public JTextField emailTextField;
    public JTextArea bemerkungTextArea;
    public JComboBox monatCb, jahrCb, tagCb;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);


    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    String[] tagString, monatString, jahrString;
    String nachnameString, vornameString, urzString, fakuString, telString, emailString, bemerkString;
    String wahlTag, wahlMonat, wahlJahr, gebDatString;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    public InsertStudentVIew(InsertStudentModel model, String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);

        /**
         * Controller
         */
        _controller = new InsertStudentController(this._model, this);

        /**
         * Set JFrame
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0,0, 774, 528);
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

    public JPanel makeJpanel(){
        /**
         * Panel
         */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(145, 79, 484, 365);
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        controlPanel.setBorder(loweredetched);

        /**
         *  Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        senden = new JButton(sendenIcon);
        senden.setFont(tex);
        senden.setBounds(371, 456, 42, 38);
        senden.addActionListener(_controller);
        senden.setToolTipText("senden");
        senden.setBorder(loweredetched);
        this.add(senden);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueck = new JButton(homeIcon);
        zurueck.setFont(tex);
        zurueck.setBounds(680, 395, 49, 49);
        zurueck.addActionListener(_controller);
        zurueck.setBorder(loweredetched);
        this.add(zurueck);

        /**
         * Text Fields
         */
        nachnameTextField = new JTextField();
        nachnameTextField.setColumns(10);
        nachnameTextField.setBounds(155, 20, 284, 27);
        nachnameTextField.setBorder(loweredetched);
        nachnameTextField.addKeyListener(_controller);
        controlPanel.add(nachnameTextField);

        vornameTextField = new JTextField();
        vornameTextField.setBounds(155, 59, 284, 27);
        vornameTextField.addKeyListener(_controller);
        vornameTextField.setBorder(loweredetched);
        controlPanel.add(vornameTextField);
        vornameTextField.setColumns(10);

        urzTextField = new JTextField();
        urzTextField.setColumns(10);
        urzTextField.setBounds(155, 98, 284, 27);
        urzTextField.setBorder(loweredetched);
        urzTextField.addKeyListener(_controller);
        controlPanel.add(urzTextField);


        fakuTextField = new JTextField();
        fakuTextField.setColumns(10);
        fakuTextField.setBounds(155, 176, 284, 27);
        fakuTextField.addKeyListener(_controller);
        fakuTextField.setBorder(loweredetched);
        controlPanel.add(fakuTextField);

        telTextField = new JTextField();
        telTextField.setBounds(155, 215, 284, 27);
        telTextField.setBorder(loweredetched);
        telTextField.addKeyListener(_controller);
        controlPanel.add(telTextField);
        telTextField.setColumns(10);

        emailTextField = new JTextField();
        emailTextField.setColumns(10);
        emailTextField.setBounds(155, 254, 284, 27);
        emailTextField.addKeyListener(_controller);
        emailTextField.setBorder(loweredetched);
        controlPanel.add(emailTextField);

        bemerkungTextArea = new JTextArea();
        bemerkungTextArea.setBounds(155, 293, 284, 57);
        //JScrollPane scrollPane = new JScrollPane(textArea);
        bemerkungTextArea.setBorder(loweredetched);
        bemerkungTextArea.addKeyListener(_controller);
        bemerkungTextArea.setEditable(true);
        controlPanel.add(bemerkungTextArea);

        /**
         *  Combo Box
         */

        monatString = new String[]{"-", "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

        monatCb = new JComboBox(monatString);
        monatCb.setBounds(231, 137, 125, 27);
        monatCb.setFont(tex);
        monatCb.addActionListener(_controller);
        monatCb.addKeyListener(_controller);
        controlPanel.add(monatCb);

        jahrString = new String[82];
        jahrString[0] = "-";
        int jahrCounter = 1945;
        for(int i = 1; i<82; i++){
            jahrString[i] = String.valueOf(jahrCounter);
            jahrCounter++;
        }


        jahrCb = new JComboBox(jahrString);
        jahrCb.setBounds(363, 137, 76, 27);
        jahrCb.setFont(tex);
        jahrCb.addActionListener(_controller);
        jahrCb.addKeyListener(_controller);
        controlPanel.add(jahrCb);

        tagString = new String[32];
        tagString[0] = "-";
        for(int i  = 1; i<32; i++){
            tagString[i] = String.valueOf(i);
        }

        tagCb = new JComboBox(tagString);
        tagCb.setBounds(155, 137, 67, 27);
        tagCb.setFont(tex);
        tagCb.addKeyListener(_controller);
        tagCb.addActionListener(_controller);
        controlPanel.add(tagCb);

        /**
         * Labels
         */
        JLabel infoLbl = new JLabel("Person neu einfügen");
        infoLbl.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLbl.setBounds(145, 44, 242, 25);
        this.add(infoLbl);

        JLabel vornameLbl = new JLabel("Vorname *");
        vornameLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        vornameLbl.setBounds(30, 65, 120, 15);
        controlPanel.add(vornameLbl);

        JLabel nachnameLbl = new JLabel("Nachname *");
        nachnameLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        nachnameLbl.setBounds(30, 26, 120, 15);
        controlPanel.add(nachnameLbl);

        JLabel geburtsdatumLbl = new JLabel("Geburtsdatum");
        geburtsdatumLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        geburtsdatumLbl.setBounds(30, 139, 115, 15);
        controlPanel.add(geburtsdatumLbl);

        JLabel urzLbl = new JLabel("Urz TUC *");
        urzLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        urzLbl.setBounds(30, 104, 115, 15);
        controlPanel.add(urzLbl);

        JLabel fakuLbl = new JLabel("Fakultät");
        fakuLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        fakuLbl.setBounds(30, 178, 115, 15);
        controlPanel.add(fakuLbl);

        JLabel telLbl = new JLabel("Telefon");
        telLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        telLbl.setBounds(30, 217, 115, 15);
        controlPanel.add(telLbl);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        emailLbl.setBounds(31, 256, 70, 15);
        controlPanel.add(emailLbl);

        JLabel bemerkugenLbl = new JLabel("Bemerkung");
        bemerkugenLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        bemerkugenLbl.setBounds(30, 293, 107, 27);
        controlPanel.add(bemerkugenLbl);

        return controlPanel;
    }

    public void initView(){
        // Clear Text Field
        this.nachnameTextField.setText("");
        this.vornameTextField.setText("");
        this.urzTextField.setText("");
        this.fakuTextField.setText("");
        this.telTextField.setText("");
        this.emailTextField.setText("");
        this.bemerkungTextArea.setText("");
        this.tagCb.setSelectedIndex(0);
        this.monatCb.setSelectedIndex(0);
        this.jahrCb.setSelectedIndex(0);
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
