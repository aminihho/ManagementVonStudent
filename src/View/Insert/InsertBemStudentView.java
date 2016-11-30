package View.Insert;

import Controller.Insert.InsertBemStudentController;
import Model.Insert.InsertBemStudentModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 17.09.16.
 */
public class InsertBemStudentView extends JFrame implements Observer {

    InsertBemStudentModel _model;
    InsertBemStudentController _controller;

    public JButton sendenBtn, zurueckBtn;
    public JComboBox studentCb;
    public JComboBox statusCb;
    public JComboBox status1Cb;
    public JTextField bemerkung1, bemerkung2, bemerkung3;
    public JButton neuerStudentbtn;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    public InsertBemStudentView(InsertBemStudentModel model, String name){
        super(name);
        /**
         * Model
         */
        this._model = model;
        this._model.addObserver(this);

        /**
         * Controller
         */
        _controller = new InsertBemStudentController(this._model, this);

        /**
         * Set JFrame
         */

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 774, 500);
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

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(145, 70, 484, 330);

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        controlPanel.setBorder(loweredetched);


        /**
         * Labels
         */
        JLabel infoLabel = new JLabel("Bemerkung einer Person eintragen");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(145, 38, 400, 25);
        this.add(infoLabel);

        JLabel studentLbl = new JLabel("Person *");
        studentLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        studentLbl.setBounds(35, 50, 89, 27);
        controlPanel.add(studentLbl);

        JLabel statusLbl = new JLabel("Bemerkung *");
        statusLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        statusLbl.setBounds(35, 159, 123, 27);
        controlPanel.add(statusLbl);

        JLabel status1Lbl = new JLabel("Bemerkung");
        status1Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        status1Lbl.setBounds(35, 229, 89, 27);
        controlPanel.add(status1Lbl);

        JLabel status2Lbl = new JLabel("Bemerkung");
        status2Lbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        status2Lbl.setBounds(35, 299, 89, 27);
        //controlPanel.add(status2Lbl);

        JLabel neuerStudentLbl = new JLabel("Neue Person");
        neuerStudentLbl.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        neuerStudentLbl.setBounds(316, 99, 103, 15);
        controlPanel.add(neuerStudentLbl);


        /**
         * TextArea
         */
        bemerkung1 = new JTextField();
        bemerkung1.addKeyListener(_controller);
        bemerkung1.setBorder(loweredetched);
        bemerkung1.setBounds(150, 160, 310, 29);
        controlPanel.add(bemerkung1);

        bemerkung2 = new JTextField();
        bemerkung2.addKeyListener(_controller);
        bemerkung2.setBorder(loweredetched);
        bemerkung2.setBounds(150, 230, 310, 29);
        controlPanel.add(bemerkung2);



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

        studentCb = new JComboBox(ergebnisArray);
        studentCb.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        studentCb.addActionListener(_controller);
        studentCb.setSelectedItem(_model.defaultObject);
        studentCb.addKeyListener(_controller);
        adjustScrollBar(studentCb);
        studentCb.setBounds(150, 50, 310, 27);
        controlPanel.add(studentCb);


        /**
         * Buttons
         */
        Icon sendenIcon = new ImageIcon(getClass().getResource("/res/send32.png"));
        sendenBtn = new JButton(sendenIcon);
        sendenBtn.setFont(tex);
        sendenBtn.setBounds(371, 417, 42, 40);
        sendenBtn.setBorder(loweredetched);
        sendenBtn.setToolTipText("senden");
        sendenBtn.addActionListener(_controller);
        this.add(sendenBtn);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueckBtn = new JButton(homeIcon);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(680, 353, 49, 49);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.addActionListener(_controller);
        this.add(zurueckBtn);

        Icon neuIcon = new ImageIcon(getClass().getResource("/res/neu2.png"));
        neuerStudentbtn = new JButton(neuIcon);
        neuerStudentbtn.setBounds(422, 89, 38, 38);
        neuerStudentbtn.setBorder(loweredetched);
        neuerStudentbtn.setToolTipText("Neue Person eintragen");
        neuerStudentbtn.addActionListener(_controller);
        controlPanel.add(neuerStudentbtn);


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
