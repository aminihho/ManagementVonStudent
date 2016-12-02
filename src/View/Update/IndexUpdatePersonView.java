package View.Update;

import Controller.Update.IndexUpdatePersonController;
import Model.Sonstiges.Functions;
import Model.Update.IndexUpdatePersonModel;
import View.Renderer.JButtonEditor;
import View.Renderer.JButtonEditorAktivitatet;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Created by annelie and kourda on 13.06.16.
 */
public class IndexUpdatePersonView extends JFrame implements Observer {

    /**
     * Model & Controller
     */
    public  IndexUpdatePersonModel model;
    private IndexUpdatePersonController controller;

    JButtonEditor buton;
    public  JButton table_modifer_button;
    public JButtonEditorAktivitatet btnAktivitat;
    public   JButton buttonAktivitatinofo ;


    /**
     * JLabels
     */
    public JLabel lblEmailValue;
    public JLabel lblFakultatValue;
    public JLabel lblGeburstsdatumValue;
    public JLabel lblNameValue;
    public JLabel lblVornameValue;
    public JLabel lblTelefonValue;
    public JLabel lblUrzValue;

    /**
     * Person Tabelle
     */
    public JTable tableStudent;
    public String[] titelTabelleStudent;
    public DefaultTableModel modelStudent;
    public JPopupMenu popupMenuPerson;

    /**
     * Aktivitat Tabelle
     */
    public JTable tableAktivitaet;
    public DefaultTableModel modelAktivitaet;
    public String [] titleTabeleAkivitaet;
    public JPopupMenu popupMenuAktivitaet;

    /**
     * Status Tabelle
     */
    public JTable tabelleStatus;
    public String[] titleTabelleStatus;
    public DefaultTableModel modelStatus;
    public JPopupMenu popupMenuStatus;

    /**
     * Bemerkung Tabelle
     */
    public static JPanel panel_2;
    public JPanel panelBemHintergrund, panelBemVordergrund;
       public JTable tableBemerkung;
    public String[] titleTabelleBemerkung;
    public DefaultTableModel modelBemerkung;
    public JPopupMenu popupMenuBemerkung;

    /**
     * Button Update
     */
    public JButton printBtn;
    public JButton btnupdateDate;
    public JButton refreshBtn;
    public JButton suchBtn;
    public JButton homeBtn;


    Font tex13 = new Font("Sans Serif", Font.PLAIN, 13);
    Font tex = new Font("Sans Serif", Font.PLAIN, 14);
    Font texBold = new Font("Sans Serif", Font.PLAIN, 14);
    Font texMedium = new Font("Sans Serif", Font.PLAIN, 15);

    String INSERT_STUDENT = "Neue Person einfügen", INSERT_AKT_STU = "Aktivität einer Person eintragen", INSERT_STATUS_STUDENT = "Status einer Person eintragen", INSERT_AKT = "Neue Aktivität einfügen", INSERT_STATUS = "Neuer Status einfügen", INSERT_BEMERKUNG = "Bemerkung einer Person eintragen";
    String UPDATE_STUDENT = "Person ändern oder löschen", UPDATE_AKTIVITAET = "Aktivität ändern oder löschen", UPDATE_STATUS = "Status ändern oder löschen", UPDATE_MASSNAHME = "Maßnahme ändern oder löschen";
    String SELECT_EINFACH = "Einfache Suche", SELECT_ERWEITERT = "Erweiterte Suche";
    String DATEI_DRUCKEN = "Drucken", DATEI_EXPO = "Exportieren", DATEI_PDF = "Datenbank als Pdf exportieren", DATEI_EXCEL = "Datenbank als Excel-Tabelle exportieren";

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;

    /*---------------------------------------------------------------------------
     *Konstruktor: Setzt das MVC
     * ------------------------------------------------------------------------*/
    public IndexUpdatePersonView (IndexUpdatePersonModel model, String titel){

        //MODEL
        this.model = model;
        this.model.addObserver(this);

        //CONTROLLER
        controller=new IndexUpdatePersonController(this.model,this);

        //VIEW
        setTitle(titel);

        //TODO: Wofür?
        btnAktivitat=new JButtonEditorAktivitatet(model, controller);
        buttonAktivitatinofo=btnAktivitat.button_table;

        makeView();
    }

    private void makeView(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1100, 700);
        this.setLocationRelativeTo(null);
        this. setResizable(false);
        this.setContentPane(panel());
        setVisible(true);
    }

    public void  updatePersonlicheInformation(String[] data){
        lblNameValue.setText(data[0]);
        lblVornameValue.setText(data[1]);
        lblUrzValue.setText(data[2]);
        lblFakultatValue.setText(data[3]);
        lblGeburstsdatumValue.setText(data[4]);
        lblEmailValue.setText(data[5]);
        lblTelefonValue.setText(data[6]);
    }

    public void setColumnRenderer(JTable table, TableColumn Column) {

        //Set up tool tips for cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Maßnahme: ");
        Column.setCellRenderer(renderer);
    }




    private JPanel panel(){

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();

        /**----------------------------------------------------------------------------------------
         *
         * Hauptpanel
         *
         *----------------------------------------------------------------------------------------*/

        //Hauptpanel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1000, 676);
        panel.setLayout(null);
        JMenuBar menuBar = makeMenuBar();
        panel.add(menuBar);

        //Hauptpanel LINKS mit Border
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Person", TitledBorder.LEADING, TitledBorder.TOP,texMedium, new Color(0, 0, 0)));
        panel_1.setBounds(26, 34,390,619);
        panel.add(panel_1);


        Functions classFunction =new Functions() ;

        //Hauptpanel RECHTS mit Border
        panel_2 = new JPanel();
        String label = "Informationen zur Person";
        panel_2.setBorder(new TitledBorder(null, label, TitledBorder.LEADING, TitledBorder.TOP, texMedium, Color.BLACK));
        panel_2.setBounds(439, 34, 635, 619);
        panel.add(panel_2);
        panel_2.setLayout(null);

        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Person
         *
         *----------------------------------------------------------------------------------------*/

        //Unterpanel
        JPanel panelPerson = new JPanel();
        panelPerson.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panelPerson.setBounds(25, 30, 340, 520);

        // Table und modelStudent
        String[][] data = classFunction.arrayListTo2DArrayVonString(this.model.ListeAllerStudenten());
        titelTabelleStudent = new String[]{"Nachname","Vorname","Urz (DB)"};
        modelStudent = new DefaultTableModel();
        tableStudent = new JTable(modelStudent);
        tableStudent.addMouseListener(controller);
        modelStudent.setDataVector(data, titelTabelleStudent);
        // Scrollpane
        JScrollPane sPane = new JScrollPane(tableStudent);
        sPane.setPreferredSize(new Dimension(340,570));
        panel_1.add(sPane, BorderLayout.CENTER);

        //PopUpMenu
        popupMenuPerson = new JPopupMenu();
        JMenuItem neuPersonItem = new JMenuItem("Neue Person");
        JMenuItem neuAktivitaetItem = new JMenuItem("Neue Aktivität");
        JMenuItem neuBemerkungItem = new JMenuItem("Neue Bemerkung");
        JMenuItem neuStatusItem = new JMenuItem("Neuer Status");
        neuPersonItem.setFont(tex13);
        neuAktivitaetItem.setFont(tex13);
        neuBemerkungItem.setFont(tex13);
        neuStatusItem.setFont(tex13);
        popupMenuPerson.add(neuAktivitaetItem);
        popupMenuPerson.add(neuStatusItem);
        popupMenuPerson.add(neuBemerkungItem);
        popupMenuPerson.add(neuPersonItem);
        // Listeners
        neuPersonItem.addActionListener(controller);
        neuAktivitaetItem.addActionListener(controller);
        neuBemerkungItem.addActionListener(controller);
        neuStatusItem.addActionListener(controller);
        popupMenuPerson.addPopupMenuListener(controller);
        popupMenuPerson.addMouseListener(controller);
        // An Tabelle hinzufügem
        tableStudent.setComponentPopupMenu(popupMenuPerson);

        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Bemerkung
         *
         *----------------------------------------------------------------------------------------*/

        //Data & Titel
        titleTabelleBemerkung = new String[]{"Bemerkungen"};
        String[][] dataBem = new String[][] {{""}};
        //Table und modelBemerkung
        modelBemerkung=new DefaultTableModel();
        modelBemerkung.setDataVector(dataBem, titleTabelleBemerkung);
        tableBemerkung=new JTable(modelBemerkung);
        tableBemerkung.addMouseListener(controller);
        tableBemerkung.setEnabled(true);
        // Scrollpane
        JScrollPane spanelBemerkung =new JScrollPane(tableBemerkung);
        spanelBemerkung.setPreferredSize(new Dimension(270,108));

        //Unterpanel
        panelBemHintergrund = new JPanel();
        panelBemHintergrund.setEnabled(true);
        panelBemHintergrund.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panelBemHintergrund.setBounds(27, 414, 300, 140);
        panelBemHintergrund.setLayout(null);

        //Vorpanel mit Border
        panelBemVordergrund = new JPanel();
        panelBemVordergrund.add(spanelBemerkung,BorderLayout.CENTER);
        panelBemVordergrund.setBounds(10, 11, 285, 120);
        //panelBemVordergrund.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, tex, Color.GRAY));
        panelBemHintergrund.add(panelBemVordergrund);
        panel_2.add(panelBemHintergrund);

        //PopUpMenu
        popupMenuBemerkung = new JPopupMenu();
        JMenuItem showBemerkungItem = new JMenuItem("Bemerkung anzeigen");
        showBemerkungItem.setFont(tex13);
        popupMenuBemerkung.add(showBemerkungItem);
        // Listeners
        popupMenuBemerkung.addPopupMenuListener(controller);
        showBemerkungItem.addActionListener(controller);
        popupMenuBemerkung.addMouseListener(controller);
        // An Tabelle hinzufügen
        tableBemerkung.setComponentPopupMenu(popupMenuBemerkung);


        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Aktivitaet
         *
         *----------------------------------------------------------------------------------------*/

        //Unterpanel
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_3.setBounds(25, 30, 588, 145);
        panel_2.add(panel_3);
        panel_3.setLayout(null);

        //Vorpanel mit Border
        JPanel panel_4 = new JPanel();
        //panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, texMedium, Color.BLACK));
        panel_4.setBounds(10, 11, 568, 120);

        //Table und modelAktivitaet
        String [][]dataAktivitaet = new String[][]{{"",""}};
        titleTabeleAkivitaet = new String[]{"Aktivität", "ID der Aktivität", "Zeitraum","Durchfürung","Art"};
        modelAktivitaet = new DefaultTableModel();
        tableAktivitaet = new JTable(modelAktivitaet);
        modelAktivitaet.setDataVector(dataAktivitaet, titleTabeleAkivitaet);
        tableAktivitaet.getColumnModel().getColumn(0).setPreferredWidth(102);
        tableAktivitaet.addMouseListener(controller);
        //Scrollpane
        JScrollPane spane2 = new JScrollPane(tableAktivitaet);
        spane2.setPreferredSize(new Dimension(552,113));
        panel_4.add(spane2,BorderLayout.CENTER);
        panel_3.add(panel_4);

        //PopUpMenu
        popupMenuAktivitaet = new JPopupMenu();
        JMenuItem showAktivitaetItem = new JMenuItem("Aktivität anzeigen");
        showAktivitaetItem.setFont(tex13);
        popupMenuAktivitaet.add(showAktivitaetItem);
        // Listeners
        showAktivitaetItem.addActionListener(controller);
        popupMenuAktivitaet.addPopupMenuListener(controller);
        popupMenuAktivitaet.addMouseListener(controller);
        // an Tabelle hinzufügen
        tableAktivitaet.setComponentPopupMenu(popupMenuAktivitaet);




        /**----------------------------------------------------------------------------------------
         *
         * Tabelle Status
         *
         *----------------------------------------------------------------------------------------*/

        //Unterpanel
        JPanel panel_5 = new JPanel();
        panel_5.setLayout(null);
        panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_5.setBounds(335, 414, 276, 140);
        panel_2.add(panel_5);

        //Vorpanel mit Border
        JPanel panel_6 = new JPanel();
        //panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, tex, new Color(0, 0, 0)));
        panel_6.setBounds(10, 8, 258, 120);

        //Data & Titel
        titleTabelleStatus = new String[]{"Status"};
        String[][] dataStatus = new String[][] {{""}};

        //Table
        modelStatus=new DefaultTableModel();
        modelStatus.setDataVector(dataStatus, titleTabelleStatus);
        tabelleStatus=new JTable(modelStatus);
        tabelleStatus.addMouseListener(controller);
        tabelleStatus.setEnabled(true);

        // Scrollpane
        JScrollPane spanestatus=new JScrollPane(tabelleStatus);
        spanestatus.setPreferredSize(new Dimension(242,112));
        panel_6.add(spanestatus,BorderLayout.CENTER);
        panel_5.add(panel_6);

        //PopUpMenu
        popupMenuStatus = new JPopupMenu();
        // Item1
        JMenuItem showStatusItem = new JMenuItem("Status anzeigen");
        showStatusItem.setFont(tex13);
        popupMenuStatus.add(showStatusItem);
        // Listeners
        showStatusItem.addActionListener(controller);
        popupMenuStatus.addPopupMenuListener(controller);
        popupMenuStatus.addMouseListener(controller);
        // an Tabelle hinzufügen
        tabelleStatus.setComponentPopupMenu(popupMenuStatus);


        /**----------------------------------------------------------------------------------------
         *
         * Tabelle 4, Persönliche Information
         *
         *----------------------------------------------------------------------------------------*/
        JPanel panel_7 = new JPanel();
        panel_7.setLayout(null);
        panel_7.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_7.setBounds(25, 187, 588, 215);
        panel_2.add(panel_7);

        JPanel panel_8 = new JPanel();

        TitledBorder title;
        title = BorderFactory.createTitledBorder(
                loweredetched, "Persönliche Information");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleFont(new Font("Sans Serif", Font.PLAIN, 15));

        panel_8.setBorder(title);
        panel_8.setBounds(20, 10, 550, 190);
        panel_7.add(panel_8);
        panel_8.setLayout(null);


        /*
         * JLabels und JTextFields
         */

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(texBold);
        lblName.setBounds(25, 31, 200, 17);
        panel_8.add(lblName);

        lblNameValue = new JLabel("........................");
        lblNameValue.setFont(tex);
        lblNameValue.setBounds(150, 31, 300, 17);
        panel_8.add(lblNameValue);


        JLabel lblVorname = new JLabel("Vorname:");
        lblVorname.setFont(texBold);
        lblVorname.setBounds(25, 51, 200, 17); //
        panel_8.add(lblVorname);

        lblVornameValue = new JLabel("........................");
        lblVornameValue.setFont(tex);
        lblVornameValue.setBounds(150, 51, 300, 17); //
        panel_8.add(lblVornameValue);


        JLabel lblUrz = new JLabel("Urz TUC:");
        lblUrz.setFont(texBold);
        lblUrz.setBounds(25, 71, 200, 17);
        panel_8.add(lblUrz);

        lblUrzValue = new JLabel("........................");
        lblUrzValue.setFont(tex);
        lblUrzValue.setBounds(150, 71, 300, 17);
        panel_8.add(lblUrzValue);

        JLabel lblFakultat = new JLabel("Fakultät:");
        lblFakultat.setFont(texBold);
        lblFakultat.setBounds(25, 91, 115, 20);
        panel_8.add(lblFakultat);

        lblFakultatValue = new JLabel("........................");
        lblFakultatValue.setFont(tex);
        lblFakultatValue.setBounds(150, 91, 300, 20);
        panel_8.add(lblFakultatValue);

        JLabel lblGeburstsdatum = new JLabel("Geburstdatum:");
        lblGeburstsdatum.setFont(texBold);
        lblGeburstsdatum.setBounds(25, 111, 300, 20);//
        panel_8.add(lblGeburstsdatum);

        lblGeburstsdatumValue = new JLabel("........................");
        lblGeburstsdatumValue.setBounds(150, 111, 200, 20);
        lblGeburstsdatumValue.setFont(tex);
        panel_8.add(lblGeburstsdatumValue);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(25, 131, 50, 20);
        lblEmail.setFont(texBold);
        panel_8.add(lblEmail);

        lblEmailValue= new JLabel("................................");
        lblEmailValue.setFont(tex);
        lblEmailValue.setBounds(150, 131, 380, 20);
        panel_8.add(lblEmailValue);

        JLabel lblTelefon = new JLabel("Telefonnummer:");
        lblTelefon.setBounds(25, 155, 141, 14);
        lblTelefon.setFont(texBold);
        panel_8.add(lblTelefon);

        lblTelefonValue = new JLabel("................................");
        lblTelefonValue.setFont(tex);
        lblTelefonValue.setBounds(150, 155, 310, 16);
        panel_8.add(lblTelefonValue);


        /**
         * Button update
         */

        Icon printIcon = new ImageIcon(getClass().getResource("/res/print.png"));
        printBtn = new JButton(printIcon);
        printBtn.setFont(tex);
        printBtn.setToolTipText("Person drucken");
        printBtn.setBounds(212, 568, 38,38); //353, 568, 38,38
        printBtn.addActionListener(controller);
        printBtn.setBorder(loweredetched);
        panel_2.add(printBtn);

        Icon editIcon = new ImageIcon(getClass().getResource("/res/edit.png"));
        btnupdateDate=new JButton(editIcon);
        btnupdateDate.setFont(tex);
        btnupdateDate.setBorder(loweredetched);
        btnupdateDate.setBounds(406, 568, 38,38); //309, 568, 38,38
        btnupdateDate.setToolTipText("Person bearbeiten");
        btnupdateDate.addActionListener(controller);
        panel_2.add(btnupdateDate);

        Icon homeIcon = new ImageIcon(getClass().getResource("/res/home322.png"));
        homeBtn=new JButton(homeIcon);
        homeBtn.setFont(tex);
        homeBtn.addActionListener(controller);
        homeBtn.setBorder(loweredetched);
        homeBtn.setToolTipText("Zurück zur Startseite");
        homeBtn.setBounds(309, 568, 38 ,38);
        panel_2.add(homeBtn);



        Icon refreshIcon = new ImageIcon(getClass().getResource("/res/refresh.png"));
        refreshBtn = new JButton(refreshIcon);
        refreshBtn.setFont(tex);
        refreshBtn.setBounds(28, 562, 50, 50);
        refreshBtn.setBorder(loweredetched);
        refreshBtn.setToolTipText("Neu laden");
        refreshBtn.addActionListener(controller);
        panel_2.add(refreshBtn);

        Icon suchIcon = new ImageIcon(getClass().getResource("/res/search2trsf.png"));
        suchBtn = new JButton(suchIcon);
        suchBtn.setFont(tex);
        suchBtn.setBounds(561, 562, 50, 50);
        suchBtn.setToolTipText("Person suchen");
        suchBtn.setBorder(loweredetched);
        suchBtn.addActionListener(controller);
        panel_2.add(suchBtn);


        /**
         * START: ERSTE VIEW
         * Information vom ersten Student in der Tabelle darstellen
         */
        controller.datenVomErstenStudentDarstellen();

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

    /*---------------------------------------------------------------------------------------
     *     Desinstaliert MVC
     *---------------------------------------------------------------------------------------*/
    public void release(){
        /// Contorler Desinstalieren
        /// Model Desinstalieren

    }

    /*-----------------------------------------------------------------------------------------
     *   Ueberschreibt Interfacemehtode , legt Reaktion auf Aenderungen fest
     * ---------------------------------------------------------------------------------------*/
    public void update(Observable arg0, Object arg1) {
    }
}

