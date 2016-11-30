package View.Update;

import Controller.Update.UpdatePersonController;
import Model.Update.UpdatePersonModel;
import View.Renderer.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by annelie & Bani
 */
public class UpdatePersonView extends JFrame implements Observer {
    // Model.
    private UpdatePersonModel model;
    // Controller.
    private UpdatePersonController controller;

    //JButtonEditor
    JButtonEditor btn ;
    JButtonEditorStatus btnStatus ;
    JButtonEditorBemerkung btnBemerkung ;
    public JButton btnUpdate;
    public JButton btnReturn;
    public JButton btnLoeschen;
    public JButton refreshBtn;
    public JTextField  textvorname;
    public JTextField textName;
    public JTextField textFakult;
    public JTextField textEmail;
    public JTextField textGDatum;
    public JTextField textTelfonne;
    public JTextField textUrz;


    //Button für löschen eine Aktivitaet:
    public JButton btndelAkt;   public JButton btndelStatus, btndelBermerkung;

    /**
     * Aktivitat Tabelle
     */
    public JTable tableAktivitaet ;
    public DefaultTableModel modelAktivitaet;
    public String [] titleTabelleAkivitaet;
    public JPopupMenu popupMenuAktivitaet;


    /**
     * Status Tabelle
     */
    public JTable tableStatus;
    public DefaultTableModel modelStatus;
    public String[] titleTabelleStatus;
    public JPopupMenu popupMenuStatus;


    /**
     * Bemerkung
     */
    DefaultListModel listenModell;
    public  JPanel panel_9;
    JPanel panel_10;JPanel panel_1;
    public JTable tableBemerkung;
    public DefaultTableModel modelBemerkung;
    public String[] titleTabelleBemerkung;
    public JPopupMenu popupMenuBemerkung;

    Font tex13 = new Font("Sans Serif", Font.PLAIN, 13);
    Font tex = new Font("Sans Serif", Font.PLAIN, 14);

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";


    public UpdatePersonView(UpdatePersonModel _model,String _titel) {
        setTitle(_titel);

        //Model
        model=_model;
        model.addObserver(this);

        //Controller
        controller=new UpdatePersonController(this.model,this);

        // Hier informier ich mein Class ButtonEditor, das er ein Model und Konroll hat
        btn = new JButtonEditor(model,controller );
        btnStatus= new JButtonEditorStatus(model,controller);

        // hier werden eine Kopie von die Button , die das Class ButtonEditor und ButtonEditorStatus besitzt gemacht , um das
        // Kontroler dieses Klass auf diese Button zugreifen kann .
        btndelAkt=btn.button_table;
        btndelStatus=btnStatus.button_table;

        //Bemerkung Tabelle
        btnBemerkung=new JButtonEditorBemerkung(model, controller);
        btndelBermerkung=btnBemerkung.button_table;

        MakeView();

    }

    public void innit()
    {
        controller.inialilView();
    }

    /**
     *  Erzeugt View ,baut die Oeuberflaeche auf .
     */
    private void MakeView(){
        /// ContentPane
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(810, 730);
        this.setLocationRelativeTo(null);
        this. setResizable(false);
        this.setContentPane(make_contentPanel());
        this.add(makeMenuBar());
        /// Fenster  mit login_controler als Listner
        addWindowListener( controller);
        setVisible(true);
    }






    private JPanel  make_contentPanel(){

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        //Hauptpanel
        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 710, 730);
        panel.setLayout(null);

        //Hauptpanel mit Border
        panel_1 = new JPanel();
        panel_1.setBounds(35, 35, 755, 655); //Breite, Höhe
        panel_1.setBorder(new TitledBorder(null, "Daten Bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, new Font("Sans Serif", Font.PLAIN, 14), null));
        panel.add(panel_1);
        panel_1.setLayout(null);

        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Aktivitaet
         *
         *----------------------------------------------------------------------------------------*/

        // Panel Aktivitaet
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_3.setBounds(53, 30, 646, 143);
        panel_1.add(panel_3);
        panel_3.setLayout(null);

        // Panel Aktivitaet mit Border
        JPanel panel_4 = new JPanel();
        //panel_4.setBorder(new TitledBorder(null, "Aktivit\u00E4ten", TitledBorder.LEADING, TitledBorder.TOP, new Font("Sans Serif", Font.PLAIN, 14), Color.BLACK));
        panel_4.setBounds(10, 11, 626, 122);
        panel_3.add(panel_4);

        /**
         * JTable Aktiviataet
         */
        // Die Daten für das Table
        String[][] data = new String[][]{
                {"", "", "" },
                {"", "", "" },
                {"", "", ""}
        };
        // Die Column-Titles
        titleTabelleAkivitaet = new String[]{"Aktivität", "ID der Aktivität", "Zeitraum","Durchfürung","Art"};
        // Das JTable initialisieren
        modelAktivitaet =new DefaultTableModel();
        tableAktivitaet = new JTable(modelAktivitaet);
        tableAktivitaet.addMouseListener(controller);
        modelAktivitaet.setDataVector(data,titleTabelleAkivitaet);
        JScrollPane sPane=new JScrollPane(tableAktivitaet);

        sPane.setPreferredSize(new Dimension(610, 110));
        JPanel panelTabAktivitat = new JPanel(new BorderLayout());
        panelTabAktivitat.add(sPane,BorderLayout.CENTER );
        panel_4.add(panelTabAktivitat);

        //PopUpMenu
        //Item
        popupMenuAktivitaet = new JPopupMenu();
        JMenuItem neuAktivitaetItem = new JMenuItem("Aktivität ersetzen");
        neuAktivitaetItem.setFont(tex13);
        popupMenuAktivitaet.add(neuAktivitaetItem);

        //Item
        JMenuItem aendernAktivitaetItem = new JMenuItem("Aktivität ändern");
        aendernAktivitaetItem.setFont(tex13);
        popupMenuAktivitaet.add(aendernAktivitaetItem);

        //Item
        JMenuItem showAktivitaetItem = new JMenuItem("Aktivität anzeigen");
        showAktivitaetItem.setFont(tex13);
        //popupMenuAktivitaet.add(showAktivitaetItem);

        // Listeners
        neuAktivitaetItem.addActionListener(controller);
        showAktivitaetItem.addActionListener(controller);
        aendernAktivitaetItem.addActionListener(controller);

        popupMenuAktivitaet.addPopupMenuListener(controller);
        popupMenuAktivitaet.addMouseListener(controller);
        // an Tabelle hinzufügen
        tableAktivitaet.setComponentPopupMenu(popupMenuAktivitaet);



        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Status
         *
         *----------------------------------------------------------------------------------------*/


        //Pane Status:
        JPanel panel_5 = new JPanel();
        panel_5.setLayout(null);
        panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_5.setBounds(385, 445, 312, 131); // Latitud, Altura, Breite, Höhe
        panel_1.add(panel_5);

        //Pane Status mit border
        JPanel panel_6 = new JPanel();
        //panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Status", TitledBorder.LEADING, TitledBorder.TOP, new Font("Sans Serif", Font.PLAIN, 14), new Color(0, 0, 0)));
        panel_6.setBounds(10, 11, 292, 109);
        panel_5.add(panel_6);

        // Die Daten für das Table
        String[][] dataStatus = new String[][] {{""}};
        // Die Column-Titles
        titleTabelleStatus = new String[]{"Status"};

        // Das JTable initialisieren
        modelStatus =new DefaultTableModel();
        tableStatus = new JTable(modelStatus);
        tableStatus.addMouseListener(controller);
        modelStatus.setDataVector(dataStatus,titleTabelleStatus);

        JScrollPane sPaneStatus=new JScrollPane(tableStatus);
        sPaneStatus.setPreferredSize(new Dimension(282,100));
        JPanel panelTabStatus = new JPanel(new BorderLayout());
        panelTabStatus.add(sPaneStatus,BorderLayout.CENTER );
        panel_6.add(panelTabStatus);

        //PopUpMenu
        popupMenuStatus = new JPopupMenu();

        // Item
        JMenuItem neuStatusItem = new JMenuItem("Status ersetzen");
        neuStatusItem.setFont(tex13);
        popupMenuStatus.add(neuStatusItem);
        // Item
        JMenuItem aendernStatusItem = new JMenuItem("Status ändern");
        aendernStatusItem.setFont(tex13);
        popupMenuStatus.add(aendernStatusItem);

        // Item
        JMenuItem showStatusItem = new JMenuItem("Status anzeigen");
        showStatusItem.setFont(tex13);
        //popupMenuStatus.add(showStatusItem);

        // Listeners
        neuStatusItem.addActionListener(controller);
        showStatusItem.addActionListener(controller);
        aendernStatusItem.addActionListener(controller);
        //
        popupMenuStatus.addPopupMenuListener(controller);
        popupMenuStatus.addMouseListener(controller);
        // an Tabelle hinzufügen
        tableStatus.setComponentPopupMenu(popupMenuStatus);

        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Bemerkung
         *
         *----------------------------------------------------------------------------------------*/
        modelBemerkung =new DefaultTableModel();
        tableBemerkung = new JTable( modelBemerkung );
        tableBemerkung.addMouseListener(controller);

        String[][] dataBem = new String[][] {{""}};
        String [] titleT={"Bemerkung","löschen"};
        setDataVector(dataBem,modelBemerkung,tableBemerkung,"Bemerkung",titleT );
        JScrollPane scrollPaneList = new JScrollPane(tableBemerkung);
        scrollPaneList.setPreferredSize(new Dimension(290,95));

        //Panel Bemerkung
        JPanel panel_9 = new JPanel();
        panel_9.setVisible(true);
        panel_9.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_9.setBounds(53, 447, 320, 127); // latitud, altitud
        panel_9.setLayout(null);

        //Panel Bemerkung mit Border
        JPanel panel_10 = new JPanel();
        panel_10.setVisible(true);
        panel_10.add(scrollPaneList,BorderLayout.CENTER);
        panel_10.setBounds(10, 11, 300, 107);
        //panel_10.setBorder(new TitledBorder(null, "Bemerkung", TitledBorder.LEADING, TitledBorder.TOP, new Font("Sans Serif", Font.PLAIN, 14), Color.GRAY));

        panel_9.add(panel_10);
        panel_1.add(panel_9);

        //PopUpMenu
        //Item
        popupMenuBemerkung = new JPopupMenu();
        JMenuItem neuBemerkungItem = new JMenuItem("Bemerkung ersetzen");
        neuBemerkungItem.setFont(tex13);
        popupMenuBemerkung.add(neuBemerkungItem);
        //Item
        JMenuItem showBemerkungItem = new JMenuItem("Bemerkung anzeigen");
        showBemerkungItem.setFont(tex13);
        //popupMenuBemerkung.add(showBemerkungItem);

        // Listeners
        popupMenuBemerkung.addPopupMenuListener(controller);
        neuBemerkungItem.addActionListener(controller);
        showBemerkungItem.addActionListener(controller);
        //
        popupMenuBemerkung.addMouseListener(controller);
        // An Tabelle hinzufügen
        tableBemerkung.setComponentPopupMenu(popupMenuBemerkung);


        /**
         * Persönliche Information von Student
         */

        JPanel panel_7 = new JPanel();
        panel_7.setLayout(null);
        panel_7.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_7.setBounds(53, 180, 645, 259);
        panel_1.add(panel_7);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                loweredetched, "Persönliche Information");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        JPanel panel_8 = new JPanel();
        panel_8.setBorder(title);
        panel_8.setBounds(17, 11, 614, 240);
        panel_7.add(panel_8);
        panel_8.setLayout(null);


        /**
         * JLabels und TextFields
         */
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblName.setBounds(25, 30, 60, 14);
        panel_8.add(lblName);

        textName = new JTextField();
        textName.setColumns(10);
        textName.addKeyListener(controller);
        textName.setBounds(151, 28, 254, 20);
        //textName.setEditable(false);
        panel_8.add(textName);

        JLabel lblVorname = new JLabel("Vorname:");
        lblVorname.setBounds(25, 55, 99, 24);
        lblVorname.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        panel_8.add(lblVorname);

        textvorname = new JTextField();
        textvorname.setBounds(151, 58, 254, 20);
        textvorname.addKeyListener(controller);
        textvorname.setColumns(10);
        textvorname.setText("");
        //textvorname.setEditable(false);
        panel_8.add(textvorname);


        JLabel lblUrz = new JLabel("Urz TUC:");
        lblUrz.setBounds(25, 91, 99, 10);
        lblUrz.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        panel_8.add( lblUrz );

        textUrz = new JTextField();
        textUrz.setBounds(151, 88, 254, 20);
        textUrz.addKeyListener(controller);
        textUrz.setColumns(10);
        textUrz.setText("");
        //textvorname.setEditable(false);
        panel_8.add(textUrz);

        JLabel lblFakultt = new JLabel("Fakult\u00E4t:");
        lblFakultt.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblFakultt.setBounds(25, 120, 116, 14);
        panel_8.add(lblFakultt);

        textFakult = new JTextField();
        textFakult.setColumns(10);
        textFakult.addKeyListener(controller);
        textFakult.setBounds(151, 118, 254, 20);
        //textFakult.setEditable(false);
        panel_8.add(textFakult);

        JLabel lblEtc = new JLabel("Geburtsdatum:");
        lblEtc.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblEtc.setBounds(25, 150, 130, 14);
        panel_8.add(lblEtc);

        textGDatum = new JTextField();
        //textGDatum.setEditable(false);
        textGDatum.setColumns(10);
        textGDatum.addKeyListener(controller);
        textGDatum.setBounds(151, 148, 254, 20);
        panel_8.add(textGDatum);

        JLabel lblEmail = new JLabel("E-Mail:");
        lblEmail.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblEmail.setBounds(25, 180, 83, 14);
        panel_8.add(lblEmail);

        textEmail = new JTextField();
        textEmail.setColumns(10);
        textEmail.addKeyListener(controller);
        textEmail.setBounds(151, 178, 254, 20);
        //textEmail.setEditable(false);
        //textEmail.addActionListener(controller);
        panel_8.add(textEmail);

        JLabel lblTelefonnummr = new JLabel("Telefonnummer:");
        lblTelefonnummr.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        lblTelefonnummr.setBounds(25, 210, 130, 14);
        panel_8.add(lblTelefonnummr);

        textTelfonne = new JTextField();
        textTelfonne.setColumns(10);
        textTelfonne.addKeyListener(controller);
        textTelfonne.setBounds(151, 208, 254, 20);
        //textTelfonne.setEditable(false);
        panel_8.add(textTelfonne);

        /**
         * JButtons
         */
        Icon sendIcon = new ImageIcon(getClass().getResource("/res/send323.png"));
        btnUpdate = new JButton(sendIcon);
        btnUpdate.setBounds(315, 600, 38, 38);
        btnUpdate.setBorder(loweredetched);
        btnUpdate.setToolTipText("Änderungen senden");
        btnUpdate.setFont(tex);
        btnUpdate.addActionListener(controller);
        panel_1.add(btnUpdate);

        Icon deleteIcon = new ImageIcon(getClass().getResource("/res/trash.png"));
        btnLoeschen = new JButton(deleteIcon);
        btnLoeschen.setBounds(408, 601, 38, 38);
        btnLoeschen.setBorder(loweredetched);
        btnLoeschen.setToolTipText("Person löschen");
        btnLoeschen.setFont(tex);
        btnLoeschen.addActionListener(controller);
        panel_1.add(btnLoeschen);

        Icon backIcon = new ImageIcon(getClass().getResource("/res/back.png"));
        btnReturn = new JButton(backIcon);
        btnReturn.setBounds(650, 590, 50, 50);
        btnReturn.setFont(tex);
        btnReturn.setToolTipText("Zurück");
        btnReturn.setBorder(loweredetched);
        btnReturn.addActionListener(controller);
        panel_1.add(btnReturn );

        Icon refreshIcon = new ImageIcon(getClass().getResource("/res/refresh.png"));
        refreshBtn = new JButton(refreshIcon);
        refreshBtn.setBounds(53, 588, 50, 50);
        refreshBtn.setFont(tex);
        refreshBtn.setToolTipText("Neu laden");
        refreshBtn.setBorder(loweredetched);
        refreshBtn.addActionListener(controller);
        panel_1.add(refreshBtn );



        return panel;

    }

    public void drawTabelleBemerkung ( )
    {


    }


    /*-----------------------------------------------------------------------------------------
     *                                 update Information der Studen
     *                             @param1:enthält alle Information über Student
     * ---------------------------------------------------------------------------------------*/
    public void  updatePersonlicheInformation(String[] data){
        textName.setText(data[0]);
        textvorname.setText(data[1]);
        textUrz.setText( data[2]);
        textFakult.setText(data[3]);
        textGDatum.setText(data[4]);
        textEmail.setText(data[5]);
        textTelfonne.setText(data[6]);

    }

    /**
     *
     * @param dataStatus zelle der Table
     * @param tabelModel DefaultTableModel
     * @param table JTable
     * @param tableName name der Table
     */
    public void  setDataVector(String [][] dataStatus,DefaultTableModel tabelModel,JTable table,String tableName,String[]title ){
        //tabelModel = new DefaultTableModel();
        //table = new JTable();
        tabelModel.setDataVector(dataStatus,title);

        // button hier ein Instanz von JButtonEditor
        if (tableName=="Status"){
            table.getColumn(table.getColumnName(1)).setCellRenderer(new JButtonRendererStatus());
            table.getColumn(tableStatus.getColumnName(1)).setCellEditor(btnStatus);
        }

        if (tableName=="Aktivitat" && ((controller.mob))){
            table.getColumn(table.getColumnName(5)).setCellRenderer(new JButtonRenderer());
            table.getColumn(table.getColumnName(5)).setCellEditor(btn);
        }

        if (tableName=="Aktivitat" && (!controller.mob)){
            table.getColumn(table.getColumnName(3)).setCellRenderer(new JButtonRenderer());
            table.getColumn(table.getColumnName(3)).setCellEditor(btn);
        }

        if (tableName == "Bemerkung"){
            table.getColumn(table.getColumnName(1)).setCellRenderer(new JButtonRenderBemerkung());
            table.getColumn(table.getColumnName(1)).setCellEditor(btnBemerkung);

        }

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


    /*
     *  diese Funktion intializiert eines leer DataTableModel.
     */
    public void  setDatavetorNull(String [][] dataStatus,DefaultTableModel tabelModel,String[]title )
    {

        tabelModel.setDataVector(dataStatus,title);
    }

    public void update(Observable o, Object arg) {

    }


}