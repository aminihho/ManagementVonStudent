package View.Update;

import Controller.Update.IndexUpdateMassnahmeController;
import Controller.Update.IndexUpdateStatusController;
import Model.Sonstiges.Functions;
import Model.Update.IndexUpdateMassnahmeModel;
import Model.Update.IndexUpdateStatusModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie on 20.08.16.
 */
public class IndexUpdateMassnahmeView extends JFrame implements Observer {

    public JButton loschenBtn,updateBtn,zurueckBtn, neuBtn, refreshBtn;
    public DefaultTableModel defultModelAktivitat;
    public JTable massnahmeTabelle;

    // Model.
    private IndexUpdateMassnahmeModel model;
    // Controller.
    private IndexUpdateMassnahmeController controller;

    Font tex = new Font("Sans Serif", Font.PLAIN, 14);
    Font texMedium = new Font("Sans Serif", Font.PLAIN, 15);

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    public IndexUpdateMassnahmeView(IndexUpdateMassnahmeModel _model, String _titel){
        model=_model;
        model.addObserver(this);
        controller = new IndexUpdateMassnahmeController(this.model, this);
        //zuweisen der Name der Fenster.
        setTitle(_titel);

        MakeView();
    }

    /**
     *  Erzeugt View ,baut die Oeuberflaeche auf .
     */
    private void MakeView()
    {
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(774, 528);
        this.setLocationRelativeTo(null);
        this. setResizable(false);
        this.setContentPane(make_contentPanel());
        /// Fenster  mit login_controler als Listner
        addWindowListener( controller);
        setVisible(true);
    }

    public void update(Observable o, Object arg) {


    }

    /**
     *
     * @return ein Panel, in dem alle Komponanten dargestellt werden.
     */

    private JPanel  make_contentPanel(){
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 774, 528);
        panel.setLayout(null);

        JMenuBar menuBar = makeMenuBar();
        panel.add(menuBar);

        /**
         * Labels
         */
        JLabel infoLabel = new JLabel("Maßnahme ändern oder löschen");
        infoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        infoLabel.setBounds(30, 55, 300, 40);
        panel.add(infoLabel);

        Icon neuIcon = new ImageIcon(getClass().getResource("/res/neu2.png"));
        neuBtn = new JButton(neuIcon);
        neuBtn.setBorder(loweredetched);
        neuBtn.setToolTipText("Neue Maßnahme eintragen");
        neuBtn.addActionListener(controller);
        neuBtn.setBounds(250, 435, 38, 38);
        neuBtn.setFont(tex);
        panel.add(neuBtn);

        Icon loeschenIcon = new ImageIcon(getClass().getResource("/res/trash.png"));
        loschenBtn= new JButton(loeschenIcon);
        loschenBtn.setBounds(350, 435, 38, 38);
        loschenBtn.setFont(tex);
        loschenBtn.setToolTipText("Maßnahme löschen");
        loschenBtn.setBorder(loweredetched);
        loschenBtn.addActionListener(controller);
        panel.add(loschenBtn);

        Icon editIcon = new ImageIcon(getClass().getResource("/res/edit.png"));
        updateBtn = new JButton(editIcon);
        updateBtn.setBounds(450, 435, 38, 38);
        updateBtn.setBorder(loweredetched);
        updateBtn.setFont(tex);
        updateBtn.setToolTipText("Maßnahme ändern");
        updateBtn.addActionListener(controller);
        panel.add(updateBtn);


        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home3.png"));
        zurueckBtn = new JButton(homeIcon);
        zurueckBtn.setFont(tex);
        zurueckBtn.setBounds(692, 428, 50, 50);
        zurueckBtn.setBorder(loweredetched);
        zurueckBtn.addActionListener(controller);
        panel.add(zurueckBtn);

        Icon refreshIcon = new ImageIcon(getClass().getResource("/res/refresh.png"));
        refreshBtn = new JButton(refreshIcon);
        refreshBtn.setFont(tex);
        refreshBtn.setToolTipText("Neu laden");
        refreshBtn.setBounds(23, 428, 50, 50);
        refreshBtn.setBorder(loweredetched);
        refreshBtn.addActionListener(controller);
        panel.add(refreshBtn);


        // Die Daten für das Table
        Functions useFunction = new Functions();
        String[][] dataAktivitat = useFunction.arrayListTo2DArrayVonString(this.model.returnAllMassnahmeArrayList());

        // Die Column-Titles
        String[] titleStatus = new String[]{
                "Maßnahme"
        };

        /*
         * Das JTable initialisieren
         */
        defultModelAktivitat =new DefaultTableModel();

        massnahmeTabelle = new JTable(defultModelAktivitat);

        massnahmeTabelle.addMouseListener(controller);

        //Button_intialisieren_Table();
        defultModelAktivitat.setDataVector(dataAktivitat,titleStatus);

        JScrollPane sPaneStatus=new JScrollPane(massnahmeTabelle);
        sPaneStatus.setPreferredSize(new Dimension(700,310));
        JPanel panelTabStatus = new JPanel(new BorderLayout());
        panelTabStatus.setBorder(loweredbevel);
        panelTabStatus.add(sPaneStatus,BorderLayout.CENTER );
        panelTabStatus.setBounds(24,100 , 720, 310);
        /////////////////////////////
        panel.add(panelTabStatus);
        return panel;
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
        druckenMn.addActionListener(controller);
        dateiMnBar.add(druckenMn);

        JMenu exportMnBar = new JMenu("Exportieren");
        exportMnBar.setFont(tex);
        dateiMnBar.add(exportMnBar);

        JMenuItem excelMn = new JMenuItem("Datenbank als Excel-Tabelle exportieren");
        excelMn.setFont(tex);
        excelMn.addActionListener(controller);
        exportMnBar.add(excelMn);

        JMenuItem pfdMn = new JMenuItem("Datenbank als Pdf exportieren");
        pfdMn.setFont(tex);
        pfdMn.addActionListener(controller);
        exportMnBar.add(pfdMn);

        /**
         * Menu Suchen
         */
        JMenu suchenMnBar = new JMenu("Suchen");
        suchenMnBar.setFont(tex);
        menuBar.add(suchenMnBar);

        JMenuItem einfacheSucheMn = new JMenuItem("Einfache Suche");
        einfacheSucheMn.setFont(tex);
        einfacheSucheMn.addActionListener(controller);
        suchenMnBar.add(einfacheSucheMn);

        JMenuItem optimierteSuche = new JMenuItem("Erweiterte Suche");
        optimierteSuche.setFont(tex);
        optimierteSuche.addActionListener(controller);
        suchenMnBar.add(optimierteSuche);

        /**
         * Menu Einfügen
         */
        JMenu einfügenMnBar = new JMenu("Einfügen");
        einfügenMnBar.setFont(tex);
        menuBar.add(einfügenMnBar);

        JMenuItem aktStudentMn  = new JMenuItem(INSERT_AKT_STU);
        aktStudentMn.setFont(tex);
        aktStudentMn.addActionListener(controller);
        einfügenMnBar.add(aktStudentMn);


        JMenuItem statusStudentMn  = new JMenuItem(INSERT_STATUS_STUDENT);
        statusStudentMn.setFont(tex);
        statusStudentMn.addActionListener(controller);
        einfügenMnBar.add(statusStudentMn);

        JMenuItem bemStudentMn  = new JMenuItem(INSERT_BEMERKUNG);
        bemStudentMn.setFont(tex);
        bemStudentMn.addActionListener(controller);
        einfügenMnBar.add(bemStudentMn);

        JMenuItem neuerStudentMn = new JMenuItem(INSERT_STUDENT);
        neuerStudentMn.setFont(tex);
        neuerStudentMn.addActionListener(controller);
        einfügenMnBar.add(neuerStudentMn);

        JMenuItem neueAktMn  = new JMenuItem(INSERT_AKT);
        neueAktMn.setFont(tex);
        neueAktMn.addActionListener(controller);
        einfügenMnBar.add(neueAktMn);


        JMenuItem neuerStatusMn  = new JMenuItem(INSERT_STATUS);
        neuerStatusMn.setFont(tex);
        neuerStatusMn.addActionListener(controller);
        einfügenMnBar.add(neuerStatusMn);

        /**
         * Menu Ändern
         */
        JMenu aendernMnBar = new JMenu("Ändern");
        aendernMnBar.setFont(tex);
        menuBar.add(aendernMnBar);

        JMenuItem aendernStuMn = new JMenuItem(UPDATE_STUDENT);
        aendernStuMn.setFont(tex);
        aendernStuMn.addActionListener(controller);
        aendernMnBar.add(aendernStuMn);

        JMenuItem aendernAktMn = new JMenuItem(UPDATE_AKTIVITAET);
        aendernAktMn.setFont(tex);
        aendernAktMn.addActionListener(controller);
        aendernMnBar.add(aendernAktMn);

        JMenuItem aendernStatusMn = new JMenuItem(UPDATE_STATUS);
        aendernStatusMn.setFont(tex);
        aendernStatusMn.addActionListener(controller);
        aendernMnBar.add(aendernStatusMn);

        JMenuItem aendernMassnahme = new JMenuItem(UPDATE_MASSNAHME);
        aendernMassnahme.setFont(tex);
        aendernMassnahme.addActionListener(controller);
        aendernMnBar.add(aendernMassnahme);

        return menuBar;
    }

}
