/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Sonstiges;

import Model.Select.SelectErweitertModel;
import Model.Sonstiges.ExcelModel;
import View.Sonstiges.ExcelView;
import com.itextpdf.layout.element.Cell;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

/**
 * @author kourda
 */
public class ExcelController extends WindowAdapter implements ActionListener, KeyListener {

    private String pathFolder = "";
    private String pahtFile = "";
    private ExcelModel model;
    private ExcelView view;
    // Excel Varialbles 
    private XSSFRow row;
    private XSSFCell cell;
    private CTTableColumn column;
    private CTTableColumns columns;
    private XSSFSheet sheet;
    private CellStyle cellStyle1;
    private CellStyle cellStyle2;
    private CellStyle backgroundStyle;
    // Variable of Drop Down Liste 
    XSSFDataValidationHelper validationHelper = null;
    CellRangeAddressList addressList = null;
    DataValidationConstraint constraint = null;
    DataValidation dataValidation = null;
    private Workbook wb;
    private int firstRow = 6;
    private int lastRow = 9;
    private int firstColumn = 0;
    private int lastColumn = 10;
    private int global = 0, indiceCurrentAktivi = 0, indiceCurrentStatus = 0, indiceCurrentBemerkungen = 0;

    public ExcelController(ExcelModel model, ExcelView view) {
        this.model = model;
        this.view = view;
    }

    private void makeStyle() {
        //Style
        this.cellStyle1 = this.wb.createCellStyle();
        this.cellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        this.cellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = this.wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        this.cellStyle1.setFont(font);

        this.cellStyle2 = this.wb.createCellStyle();
        this.cellStyle2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        this.cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font2 = this.wb.createFont();
        font2.setColor(IndexedColors.BLACK.getIndex());
        this.cellStyle2.setFont(font2);
    }

    public void createExcelDatei() throws JSONException {
        try {
            this.wb = new XSSFWorkbook();
            XSSFSheet sheet = (XSSFSheet) this.wb.createSheet();
            this.sheet = sheet;

            //Create 
            XSSFTable table = this.sheet.createTable();
            table.setDisplayName("StudentsTablle");
            CTTable cttable = table.getCTTable();
            this.makeStyle();
            //Set which area the table should be placed i
            cttable.setId(1);
            CTTableColumns columns = cttable.addNewTableColumns();
            this.columns = columns;

            this.makeExcelDatei();
            //Create a File Excel
            this.createDirectory();
            this.createFileName();

            FileOutputStream fileOut = new FileOutputStream(this.pahtFile);

            this.wb.write(fileOut);
            fileOut.close();
            Desktop dt = Desktop.getDesktop(); 
            dt.open(new File( this.pahtFile));

        } catch (FileNotFoundException f) {
            System.out.print(f.toString());
        } catch (IOException io) {
            System.out.print(io.toString());
        }

    }

    private ArrayList<String> takeElementOfJsonObjectByKey(JSONObject object, String key, ArrayList<String> array) throws JSONException {

        if (object.has(key)) {
            String stringObject = object.get(key).toString();
            array.add(stringObject);

            //this.saveDataInExcel(indice, stringObject, style);
        } else {
            array.add("keine Daten");
        }
        return array;

    }

    private void makeExcelDatei() throws JSONException, IOException {
        this.makeTitleOfPage();
        this.makeTitleOfTable();
        int start = this.firstRow + 1;
        this.global = start;
        int indiceaktivitat = 0, indiceStatus = 0;
        String string = this.model.getStudentsInfo().toString();

        JSONObject studentsInfo = new JSONObject(string);
        JSONArray students = studentsInfo.getJSONArray("studenten");

        for (int i = 0; i < students.length(); i++) {
            this.indiceCurrentAktivi = 0;
            this.indiceCurrentStatus = 0;
            this.indiceCurrentBemerkungen = 0;
            ArrayList<String> arrayInfo = new ArrayList<>();
            ArrayList<String> arrayaktivitaet = new ArrayList<>();
            ArrayList<String> arrayStatus = new ArrayList<>();
            ArrayList<String> arrayBemerkungen = new ArrayList<>();
            CellStyle style;
            if (i % 2 == 0) {
                style = this.cellStyle1;
            } else {
                style = this.cellStyle2;
            }
            //get Json Object 
            JSONObject student = students.getJSONObject(i);
            // Save the information about Person in Liste
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "urztuc", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "vorname", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "name", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "fakultaet", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "geburtsdatum", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "email", arrayInfo);
            arrayInfo = this.takeElementOfJsonObjectByKey(student, "telefon", arrayInfo);
            // Save the Status of Student in Liste
            if (student.has("status")) {

                JSONArray status = student.getJSONArray("status");
                JSONObject statusObject = status.getJSONObject(0);

                for (int j = 0; j < statusObject.length(); j++) {
                    String index = "status" + Integer.toString(j);
                    arrayStatus.add(statusObject.getString(index).toString());
                }
            } else {
                arrayStatus.add("");
            }
            // Save the Bemerkungen in ArrayListe 
            if (student.has("Bemerkungen")) {
                JSONArray bemerkungen = student.getJSONArray("Bemerkungen");
                JSONObject bemerkungenObject = bemerkungen.getJSONObject(0);

                for (int j = 0; j < bemerkungenObject.length(); j++) {
                    String index = "bermerkung" + Integer.toString(j);
                    arrayBemerkungen.add(bemerkungenObject.getString(index).toString());
                }

            } else {
                arrayBemerkungen.add("");
            }
            // Save the Aktivitaeten of Student in Liste 
            if (student.has("aktivitaeten")) {
                String aktivZeitraum = "";
                String aktivitaet_id = "";
                String aktivitaet_name = "";
                String aktivitaet_art = "";
                String aktivitaet_durchfuerung = "";

                JSONArray aktivitaeten = student.getJSONArray("aktivitaeten");

                for (int j = 0; j < aktivitaeten.length(); j++) {

                    JSONObject aktivitaet = aktivitaeten.getJSONObject(j);
                    if (aktivitaet.length() == 3) {
                        aktivitaet_id = aktivitaet.get("aktivitaet_id").toString();
                        arrayaktivitaet.add(aktivitaet_id);
                        aktivitaet_name = aktivitaet.get("aktivitaet_name").toString();
                        arrayaktivitaet.add(aktivitaet_name);
                        aktivZeitraum = aktivitaet.get("aktivitaet_zeitraum").toString();
                        arrayaktivitaet.add(aktivZeitraum);
                        arrayaktivitaet.add("");
                        arrayaktivitaet.add("");
                    }
                    if (aktivitaet.length() == 5) {
                        aktivitaet_id = aktivitaet.get("aktivitaet_id").toString();
                        arrayaktivitaet.add(aktivitaet_id);
                        aktivitaet_name = aktivitaet.get("aktivitaet_name").toString();
                        arrayaktivitaet.add(aktivitaet_name);
                        aktivZeitraum = aktivitaet.get("aktivitaet_zeitraum").toString();
                        arrayaktivitaet.add(aktivZeitraum);
                        aktivitaet_art = aktivitaet.get("aktivitaet_art").toString();
                        arrayaktivitaet.add(aktivitaet_art);
                        aktivitaet_durchfuerung = aktivitaet.get("aktivitaet_durchfuerung").toString();
                        arrayaktivitaet.add(aktivitaet_durchfuerung);
                    }

                }
            } else {
                arrayaktivitaet.add("");
                arrayaktivitaet.add("");
                arrayaktivitaet.add("");
                arrayaktivitaet.add("");
                arrayaktivitaet.add("");

            }

            this.drawInExcel(arrayInfo, arrayStatus, arrayaktivitaet, arrayBemerkungen, style);
        }

    }

    private void drawInExcel(ArrayList<String> info, ArrayList<String> status, ArrayList<String> aktivi, ArrayList<String> bemerkungen, CellStyle style) {

        int aktiviAnzahl = aktivi.size() / 5;
        int max = Math.max(status.size(), aktiviAnzahl);
        max = Math.max(max, bemerkungen.size());
        int nbRepetion = this.global + max;
        int i = 0;

        for (i = this.global; i < nbRepetion; i++) {
            this.row = this.sheet.createRow(i);
            if (i == this.global) {
                for (int k = 0; k < 7; k++) {
                    this.saveDataInExcel(k, info.get(k).toString(), style);
                }
            }

            int stopStatus = this.global + status.size();
            if (i < stopStatus) {
                this.saveDataInExcel(13, status.get(this.indiceCurrentStatus).toString(), style);
            }

            int stopBemerkung = this.global + bemerkungen.size();
            if (i < stopBemerkung) {
                this.saveDataInExcel(12, bemerkungen.get(this.indiceCurrentBemerkungen).toString(), style);
            }
            int stopIndiceAkti = aktiviAnzahl + this.global;
            int stop = 0;
            if (i < stopIndiceAkti) {
                stop = this.indiceCurrentAktivi + 5;
                int indice = 7;
                for (int k = this.indiceCurrentAktivi; k < stop; k++) {
                    this.saveDataInExcel(indice, aktivi.get(k).toString(), style);
                    indice++;
                }
            }

            if ((i >= stopIndiceAkti) && (aktiviAnzahl <= max)) {
                for (int k = 7; k < 12; k++) {
                    this.saveDataInExcel(k, "", style);
                }
            }
            if ((i >= stopBemerkung) && (bemerkungen.size() <= max)) {
                this.saveDataInExcel(12, "", style);
            }
            if ((i >= stopStatus) && (status.size() <= max)) {
                this.saveDataInExcel(13, "", style);
            }
            if ((i > this.global) && (i < this.global + max)) {
                for (int k = 0; k < 7; k++) {
                    this.saveDataInExcel(k, "", style);
                }
            }
            this.indiceCurrentAktivi = stop;
            this.indiceCurrentStatus++;
            this.indiceCurrentBemerkungen++;
        }

        this.global = this.global + max;
    }

    private void saveDataInExcel(int indice, String data, CellStyle style) {
        this.column = this.columns.addNewTableColumn();
        this.column.setId(indice);
        this.sheet.autoSizeColumn(indice);
        this.cell = this.row.createCell(indice);

        this.cell.setCellStyle(style);
        this.cell.setCellValue(data);
    }

    private void createFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String fileName = format.format(date).toString();
        this.pahtFile = this.pathFolder + "/" + fileName + ".xls";
    }

    private void createDirectory() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDate localDate = LocalDate.now();
        String nameOfFolder = Integer.toString(localDate.getYear()) + "-" + localDate.getMonth().toString();
        this.pathFolder = "/home/kourda/Downloads/" + nameOfFolder;
        SelectErweitertModel model = new SelectErweitertModel();
        String attribute = model.getAttribute();

        File file = new File(this.pathFolder);
        if (!file.exists()) {
            file.mkdirs();
        }

    }

    private void makeTitleOfPage() throws IOException {

        this.sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 13));
        this.row = this.sheet.createRow(0);
        XSSFCell cell2 = this.row.createCell(0);
        String title = "Inprotuc Datenbank | Informationen zur Personen \nSuchkriterien: ";
        ArrayList<String> array2 = this.model.getQueryInfo();
        ArrayList<String> array = this.deleteEmptyValueOArray(array2);
        String info = "";
        if (array.size() == 2) {
            info = array.get(0) + " / " + array.get(1) + ".";
        }
        if (array.size() == 4) {
            info = array.get(0) + "/" + array.get(1) + ", ";
            info = info + array.get(2) + "/" + array.get(3) + ".";
        }
        if (array.size() == 6) {
            info = array.get(0) + "/" + array.get(1) + ", ";
            info = info + array.get(2) + "/" + array.get(3) + ", ";
            info = info + array.get(4) + "/" + array.get(5) + ".";
        }
        cell2.setCellValue(title + info);
        CellStyle cellStyle = this.wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        // font 
        Font font = this.wb.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);
        font.setColor(HSSFColor.BLACK.index);

        cellStyle.setFont(font);
        cell2.setCellStyle(cellStyle);

    }

    private void makeTitleOfTable() {
        this.row = this.sheet.createRow(this.firstRow);
        CellStyle cellStyle = this.wb.createCellStyle();
        // font 
        Font font = this.wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(HSSFFont.FONT_ARIAL);

        font.setBold(true);
        font.setColor(HSSFColor.BLACK.index);

        cellStyle.setFont(font);
        this.saveDataInExcel(0, "Urz", cellStyle);
        this.saveDataInExcel(1, "Vorname", cellStyle);
        this.saveDataInExcel(2, "Name", cellStyle);
        this.saveDataInExcel(3, "Fakultät", cellStyle);
        this.saveDataInExcel(4, "Geburtsdatum", cellStyle);
        this.saveDataInExcel(5, "E-Mail", cellStyle);
        this.saveDataInExcel(6, "Telefonnummer", cellStyle);
        this.saveDataInExcel(7, "Aktivitäten-ID", cellStyle);
        this.saveDataInExcel(8, "Aktivitätenname", cellStyle);
        this.saveDataInExcel(9, "Zeitraum", cellStyle);
        this.saveDataInExcel(10, "Art", cellStyle);
        this.saveDataInExcel(11, "Durchführung", cellStyle);
        this.saveDataInExcel(12, "Bemerkung", cellStyle);
        this.saveDataInExcel(13, "Status", cellStyle);

    }

    private ArrayList<String> deleteEmptyValueOArray(ArrayList<String> array) {
        ArrayList<String> array2 = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            if (!array.get(i).isEmpty()) {
                array2.add(array.get(i));
            }
        }
        return array2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
