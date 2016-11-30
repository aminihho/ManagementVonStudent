package Controller.Sonstiges;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by annelie on 09.11.16.
 */
public class PdfTest {

    public static String filePath = "";

    public PdfTest() throws IOException {

        JFileChooser dialog = new JFileChooser();
        int dialogResult = dialog.showSaveDialog(null);

        if (dialogResult== JFileChooser.APPROVE_OPTION){

            filePath = dialog.getSelectedFile().getPath();

            System.out.println(filePath);

            File file = new File(filePath);
            file.getParentFile().mkdirs();
            createPdf(filePath);
        }
    }

    public void createPdf(String filePath) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(filePath);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        //Add paragraph to the document
        document.add(new Paragraph("Hello PDF-World!"));

        //Close document
        document.close();
    }
}
