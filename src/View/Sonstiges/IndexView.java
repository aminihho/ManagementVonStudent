package View.Sonstiges;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import Controller.Sonstiges.IndexController;
import Model.Sonstiges.IndexModel;

/**
 * Created by annelie on 18.04.16.
 */
public class IndexView extends JFrame implements Observer {

    public IndexController _indexController;
    public IndexModel _indexModel;

    public JButton btnSenden;

    Border blackline, raisedetched, loweredetched,
            raisedbevel, loweredbevel, empty;
    Font tex = new Font("Te X Gyre Heros", Font.PLAIN, 15);
    String INSERT_STUDENT = "Neuer Student einfügen";
    String INSERT_AKT_STU = "Aktivität eines Studenten eintragen";
    String INSERT_STATUS_STUDENT = "Status eines Studenten eintragen";
    String INSERT_AKT = "Neue Aktivität einfügen";
    String INSERT_STATUS = "Neuer Status einfügen";
    String UPDATE_STUDENT = "Daten eines Studenten ändern oder löschen";
    String UPDATE_AKTIVITAET = "Aktivität ändern oder löschen";
    String UPDATE_STATUS = "Status ändern oder löschen";

    public IndexView(IndexModel model, String name){
        super(name);
        /**
         * Model
         */
        this._indexModel = model;
        this._indexModel.addObserver(this);

        /**
         * Controller
         */
        _indexController = new IndexController(this._indexModel, this);

        /**
         * Set JFrame
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(makeJpanel());
        //contentPane.add(makeMenuBar());
        setLocationRelativeTo(null);
        setBackground(Color.darkGray);
        setVisible(true);

    }

    public JPanel makeJpanel() {
        JMenuBar menuBar = makeMenuBar();

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(0, 0,800, 550);
        controlPanel.setLayout(null);
        controlPanel.add(menuBar);

        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        //controlPanel.setBorder(loweredetched);


        JLabel labelImage = new JLabel();
        labelImage.setIcon(new ImageIcon("images/inprotuc.jpg"));
        labelImage.setBounds(10, 10, 800, 550);
        controlPanel.add(labelImage);

        return  controlPanel;
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
        druckenMn.addActionListener(_indexController);
        dateiMnBar.add(druckenMn);

        JMenu exportMnBar = new JMenu("Exportieren");
        exportMnBar.setFont(tex);
        dateiMnBar.add(exportMnBar);

        JMenuItem excelMn = new JMenuItem("Datenbank als Excel-Tabelle exportieren");
        excelMn.setFont(tex);
        excelMn.addActionListener(_indexController);
        exportMnBar.add(excelMn);

        JMenuItem pfdMn = new JMenuItem("Datenbank als Pdf exportieren");
        pfdMn.setFont(tex);
        pfdMn.addActionListener(_indexController);
        exportMnBar.add(pfdMn);

        /**
         * Menu Suchen
         */
        JMenu suchenMnBar = new JMenu("Suchen");
        suchenMnBar.setFont(tex);
        menuBar.add(suchenMnBar);

        JMenuItem einfacheSucheMn = new JMenuItem("Einfache Suche");
        einfacheSucheMn.setFont(tex);
        einfacheSucheMn.addActionListener(_indexController);
        suchenMnBar.add(einfacheSucheMn);

        JMenuItem optimierteSuche = new JMenuItem("Erweiterte Suche");
        optimierteSuche.setFont(tex);
        optimierteSuche.addActionListener(_indexController);
        suchenMnBar.add(optimierteSuche);

        /**
         * Menu Einfügen
         */
        JMenu einfügenMnBar = new JMenu("Einfügen");
        einfügenMnBar.setFont(tex);
        menuBar.add(einfügenMnBar);


        JMenuItem neuerStudentMn = new JMenuItem(INSERT_STUDENT);
        neuerStudentMn.setFont(tex);
        neuerStudentMn.addActionListener(_indexController);
        einfügenMnBar.add(neuerStudentMn);


        JMenuItem aktStudentMn  = new JMenuItem(INSERT_AKT_STU);
        aktStudentMn.setFont(tex);
        aktStudentMn.addActionListener(_indexController);
        einfügenMnBar.add(aktStudentMn);


        JMenuItem statusStudentMn  = new JMenuItem(INSERT_STATUS_STUDENT);
        statusStudentMn.setFont(tex);
        statusStudentMn.addActionListener(_indexController);
        einfügenMnBar.add(statusStudentMn);


        JMenuItem neueAktMn  = new JMenuItem(INSERT_AKT);
        neueAktMn.setFont(tex);
        neueAktMn.addActionListener(_indexController);
        einfügenMnBar.add(neueAktMn);


        JMenuItem neuerStatusMn  = new JMenuItem(INSERT_STATUS);
        neuerStatusMn.setFont(tex);
        neuerStatusMn.addActionListener(_indexController);
        einfügenMnBar.add(neuerStatusMn);

        /**
         * Menu Ändern
         */
        JMenu aendernMnBar = new JMenu("Ändern");
        aendernMnBar.setFont(tex);
        menuBar.add(aendernMnBar);

        JMenuItem aendernStuMn = new JMenuItem(UPDATE_STUDENT);
        aendernStuMn.setFont(tex);
        aendernStuMn.addActionListener(_indexController);
        aendernMnBar.add(aendernStuMn);

        JMenuItem aendernAktMn = new JMenuItem(UPDATE_AKTIVITAET);
        aendernAktMn.setFont(tex);
        aendernAktMn.addActionListener(_indexController);
        aendernMnBar.add(aendernAktMn);

        JMenuItem aendernStatusMn = new JMenuItem(UPDATE_STATUS);
        aendernStatusMn.setFont(tex);
        aendernStatusMn.addActionListener(_indexController);
        aendernMnBar.add(aendernStatusMn);


        return menuBar;
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
