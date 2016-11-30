package View.Insert;

import Controller.Insert.InsertStatusController;
import Model.Insert.InsertStatusModel;

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
 * Created by annelie on 02.05.16.
 */
public class    InsertStatusView extends JFrame implements Observer {
    private InsertStatusModel _model;
    private InsertStatusController _controller;

    public JTextField statusTextField;
    public JTextField statusTextField1;
    public JComboBox studentCb;
    public JButton sendenBtn;
    public JButton sendenBtn1;
    public JButton zurueckBtn;

    public boolean hilfsfenster;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public InsertStatusView(InsertStatusModel model, String name) {
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);

        /**
         * Controller
         */
        _controller = new InsertStatusController(this._model, this);

        /**
         * Set JFrame
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 774, 528);
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

    public JPanel makeJpanel() {
        Border blackline, raisedetched, loweredetched,
                raisedbevel, loweredbevel, empty;

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        this.add(makeMenuBar());

        /**
         * Panels
         */
        JPanel contentPane = new JPanel();
        contentPane.setBounds(100, 100, 774, 488);
        contentPane.setLayout(null);

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(135, 89, 504, 98);
        controlPanel.setLayout(null);
        controlPanel.setBorder(loweredetched);
        contentPane.add(controlPanel);

        JPanel controlPanel1 = new JPanel();
        controlPanel1.setBounds(135, 266, 504, 147);
        controlPanel1.setLayout(null);
        controlPanel1.setBorder(loweredetched);
        contentPane.add(controlPanel1);

        /**
         * Labels
         */
        JLabel infoLabel = new JLabel("Neuer Status einfügen");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(141, 45, 242, 25);
        contentPane.add(infoLabel);

        JLabel nameStatusLbl = new JLabel("Name des Status");
        nameStatusLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        nameStatusLbl.setBounds(26, 39, 150, 17);
        controlPanel.add(nameStatusLbl);

        JLabel nameStatusLbl1 = new JLabel("Name des Status");
        nameStatusLbl1.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        nameStatusLbl1.setBounds(23, 92, 150, 17);
        controlPanel1.add(nameStatusLbl1);

        JLabel studentLbl = new JLabel("Student");
        studentLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        studentLbl.setBounds(23, 44, 150, 17);
        controlPanel1.add(studentLbl);

        /**
         * Titled Border
         */
        TitledBorder title, title1;

        title = BorderFactory.createTitledBorder(
                loweredetched, "Neuer Status");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 14));
        controlPanel.setBorder(title);

        title1 = BorderFactory.createTitledBorder(
                loweredetched, "Neuer Status einen existierenden Studenten zuordnen");
        title1.setTitleJustification(TitledBorder.LEFT);
        title1.setTitleFont(new Font("Sans Serif", Font.PLAIN, 14));
        controlPanel1.setBorder(title1);

        /**
         * Text Fields
         */
        statusTextField = new JTextField();
        statusTextField.setColumns(10);
        statusTextField.addKeyListener(_controller);
        statusTextField.setBounds(194, 34, 284, 27);
        statusTextField.setBorder(loweredetched);
        controlPanel.add(statusTextField);

        statusTextField1 = new JTextField();
        statusTextField1.setColumns(10);
        statusTextField1.setBounds(191, 87, 284, 27);
        statusTextField1.addKeyListener(_controller);
        statusTextField1.setBorder(loweredetched);
        controlPanel1.add(statusTextField1);


        /**
         * Combo Box
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
        System.out.println(Arrays.toString(ergebnisArray));

        studentCb = new JComboBox(ergebnisArray);
        studentCb.setBounds(191, 43, 284, 27); //191, 87, 284, 27
        studentCb.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        studentCb.addKeyListener(_controller);
        studentCb.addActionListener(_controller);
        adjustScrollBar(studentCb);
        controlPanel1.add(studentCb);


        /**
         * Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        sendenBtn = new JButton(sendenIcon);
        sendenBtn.setFont(tex);
        sendenBtn.setBounds(371, 199, 42, 38);
        sendenBtn.setToolTipText("senden");
        sendenBtn.addActionListener(_controller);
        sendenBtn.setBorder(loweredetched);
        this.add(sendenBtn);

        sendenBtn1 = new JButton(sendenIcon);
        sendenBtn1.setFont(tex);
        sendenBtn1.setToolTipText("senden");
        sendenBtn1.setBounds(371, 425, 42, 38);
        sendenBtn1.addActionListener(_controller);
        sendenBtn1.setBorder(loweredetched);
        this.add(sendenBtn1);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueckBtn = new JButton(homeIcon);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(684, 365, 48, 48);
        zurueckBtn.addActionListener(_controller);
        zurueckBtn.setBorder(loweredetched);
        this.add(zurueckBtn);


        return contentPane;
    }

    /**
     * public void setHilfsfenster(boolean bool){
     hilfsfenster = bool;
     }

     public boolean getHilfsfenster(){
     return hilfsfenster;
     }
     */

    public void initView(){
        // Clear Text Field
        this.statusTextField.setText("");
        this.statusTextField1.setText("");
        this.studentCb.setSelectedIndex(0);
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
