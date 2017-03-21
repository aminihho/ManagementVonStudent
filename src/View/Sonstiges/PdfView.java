package View.Sonstiges;

import Controller.Insert.InsertAktivitaetController;
import Controller.Sonstiges.PdfController;
import Model.Sonstiges.PdfModel;



import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.text.StyleConstants;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by annelie  and Kourda.
 */
public class PdfView implements Observer {
    private PdfController controller;
    private PdfModel model;

    public static String filePath = "";

    String [][] aktivitat, status, bemerkung;
    String [] person;

    public PdfView(PdfModel model, String name) throws FileNotFoundException, IOException {
        /**
         * Model
         */
        this.model = model;
        this.model.addObserver(this);
        /**
         * Controller
         */
        controller = new PdfController(this.model, this);

        // set File Path to Save PDF
        setFilePath();
    }

    public void setFilePath() throws FileNotFoundException, IOException {

        String[] person = model.getPerson();
        String nachname = person[0];
        String vorname = person[1];

        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File(nachname + "_" + vorname + ".pdf"));

        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult== JFileChooser.APPROVE_OPTION){

            filePath = dialog.getSelectedFile().getPath();

            File file = new File(filePath);
            file.getParentFile().mkdirs();
            //Create a new PDF in selected Path
            createPdf(filePath);
        }
    }



    public void createPdf(String filePath) throws FileNotFoundException, IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(filePath);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf);

        person = model.getPerson();

        PdfFont helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        // Page Header
        document.add(new Paragraph("InProTUC Datenbank | Informationen zur Person").setFont(helvetica).setFontSize(10).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph());

        // Haupttitel
        document.add(new Paragraph(person[0] + ", " + person[1]).setFont(helveticaBold).setFontSize(16));

        /**
         * Persönliche Information
         */
        Div divPerson = new Div();
        // Margin
        divPerson.setMarginTop(5);

        // Titel PERSÖNLICHE DATEN
        divPerson.add(new Paragraph("Persönliche Daten").setFont(helveticaBold).setFontSize(13));
        //Separator
        SolidLine line = new SolidLine(1f);
        line.setColor(Color.BLACK);
        LineSeparator ls = new LineSeparator(line);
        ls.setWidthPercent(50);
        //ls.setMarginTop(5);
        ls.setMarginBottom(10);
        divPerson.add(ls);

        // Daten der Person
        divPerson.add(new Paragraph("Urz TUC: " + person[2]).setFont(helvetica).setFontSize(11));
        divPerson.add(new Paragraph("Fakultät: " + person[3]).setFont(helvetica).setFontSize(11));
        divPerson.add(new Paragraph("Geburtsdatum: " + person[4]).setFont(helvetica).setFontSize(11));
        divPerson.add(new Paragraph("Email: " + person[5]).setFont(helvetica).setFontSize(11));
        divPerson.add(new Paragraph("Telefonnummer: " + person[6]).setFont(helvetica).setFontSize(11));
        divPerson.setKeepTogether(true);

        document.add(divPerson);

        /**
         * Aktivitäten
         */
        if(model.akt){
            aktivitat = model.getAktivitat();
            int rows = aktivitat.length;

            Div divAktivitaet = new Div();

            // Margin
            divAktivitaet.setMarginTop(20);

            // Titel AKTIVITÄT
            if(rows==1){
                divAktivitaet.add(new Paragraph("Aktivität").setFont(helveticaBold).setFontSize(13));
            } else {
                divAktivitaet.add(new Paragraph("Aktivitäten").setFont(helveticaBold).setFontSize(13));
            }

            // Separator
            SolidLine lineAkt = new SolidLine(1f);
            lineAkt.setColor(Color.BLACK);
            LineSeparator lsAkt = new LineSeparator(lineAkt);
            lsAkt.setWidthPercent(50);
            //lsAkt.setMarginTop(5);
            lsAkt.setMarginBottom(10);
            divAktivitaet.add(lsAkt);

            int nrSpalten;
            String [] titel;

            // TItel Table Aktivität
            if (model.mob){
                // Aktivität MIT Mobilität
                nrSpalten = 6;
                titel = new String[]{"Aktivität", "ID der Aktivität", "Zeitraum", "Durchführung", "Art", "Maßnahme"};

            } else {
                // Aktivität OHNE Mobilität
                nrSpalten = 4;
                titel = new String[]{"Aktivität", "ID der Aktivität", "Zeitraum", "Maßnahme"};
            }

            Table tableAktivitaet = new Table(nrSpalten);
            String massnahme = new String();


            // Add Titel to Table
            for (int k = 0; k < nrSpalten; k++){
                tableAktivitaet.addHeaderCell(titel[k]).setFontSize(11);
            }
            // Add Daten to Table
            for (int i = 0; i < rows; i++){

                for (int j = 0; j < nrSpalten; j++){

                    if(j < nrSpalten-1){
                        tableAktivitaet.addCell(aktivitat[i][j]).setFontSize(11);
                    } else if(j == nrSpalten-1){
                        tableAktivitaet.addCell(massnahme).setFontSize(11);
                    }

                    // Holt Maßnahme
                    if(j == 1){
                        String nameAktivitaet = aktivitat[i][j];
                        massnahme = model.getMassnahme(nameAktivitaet);
                    }
                }
            }

            divAktivitaet.add(tableAktivitaet);
            document.add(divAktivitaet);
        }

        /**
         * Status
         */
        if(model.sta){
            status = model.getStatus();
            Div divStatus = new Div();

            // Titel STATUS
            divStatus.add(new Paragraph("Status").setFont(helveticaBold).setFontSize(13));

            // Margin
            divStatus.setMarginTop(20);

            // Separator
            SolidLine lineStat = new SolidLine(1f);
            lineStat.setColor(Color.BLACK);
            LineSeparator lsStat = new LineSeparator(lineStat);
            lsStat.setWidthPercent(50);
            //lsStat.setMarginTop(5);
            lsStat.setMarginBottom(10);
            divStatus.add(lsStat);

            // Liste
            int nrStatus = status.length;
            List listStatus = new List(ListNumberingType.DECIMAL).setSymbolIndent(12).setFont(helvetica);

            for (int i = 0; i < nrStatus; i++){
                listStatus.add(status[i][0]).setFontSize(11);
            }

            divStatus.add(listStatus);
            document.add(divStatus);
        }

        /**
         * Bemerkung
         */
        if( model.bem ){
            bemerkung = model.getBemerkung();
            int nrBemerkung = bemerkung.length;

            Div divBemerkung = new Div();

            // Titel STATUS
            if(nrBemerkung == 1){
                divBemerkung.add(new Paragraph("Bemerkung").setFont(helveticaBold).setFontSize(13));
            } else {
                divBemerkung.add(new Paragraph("Bemerkungen").setFont(helveticaBold).setFontSize(13));
            }

            // Margin
            divBemerkung.setMarginTop(20);

            // Separator
            SolidLine lineBem = new SolidLine(1f);
            lineBem.setColor(Color.BLACK);
            LineSeparator lsBem = new LineSeparator(lineBem);
            lsBem.setWidthPercent(50);
            //lsBem.setMarginTop(5);
            lsBem.setMarginBottom(10);
            divBemerkung.add(lsBem);

            // Liste
            List listBemerkung = new List().setSymbolIndent(12).setFont(helvetica).setListSymbol("\u2022");

            for (int i = 0; i < nrBemerkung; i++){
                listBemerkung.add(bemerkung[i][0]).setFontSize(11);
            }

            divBemerkung.add(listBemerkung);
            document.add(divBemerkung);
        }

        //Close document
        document.close();
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
