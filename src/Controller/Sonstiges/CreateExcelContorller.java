/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Sonstiges;


import com.itextpdf.layout.element.Cell;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;
/**
 *
 * @author kourda
 */
public class CreateExcelContorller {
     private XSSFRow row;
    private XSSFCell cell;
    private CTTableColumn column;
    private CTTableColumns columns;
    private XSSFSheet sheet;
    private int firstRow = 3; 
    private int lastRow = 9; 
    private int firstColumn = 0;
    private int lastColumn = 2;
    
    public void createTableExcelAction() {
        try {
            Workbook wb = new XSSFWorkbook();
            XSSFSheet sheet = (XSSFSheet) wb.createSheet();
            this.sheet = sheet;
            //Create 
            XSSFTable table = this.sheet.createTable();
            table.setDisplayName("StudentsTablle");
            CTTable cttable = table.getCTTable();
            //Style configurations
            CTTableStyleInfo style = cttable.addNewTableStyleInfo();
            style.setName("inprotucTable");
            style.setShowColumnStripes(false);
            style.setShowRowStripes(true);
            //Set which area the table should be placed in

            cttable.setId(1);
            cttable.setName("Test");
            cttable.setTotalsRowCount(1);

            CTTableColumns columns = cttable.addNewTableColumns();
            this.columns = columns;
            this.columns.setCount(3);

            // save the data in Excel Tabele before that u create an excel File
            this.writeInTableExcelAction();

            //Create a File Excel
            FileOutputStream fileOut = new FileOutputStream("/home/kourda/Downloads/table.xls");
            wb.write(fileOut);
            fileOut.close();

        } catch (FileNotFoundException f) {
            System.out.print(f.toString());
        } catch (IOException io) {
            System.out.print(io.toString());
        }
    }

    private void writeInTableExcelAction() {

        for (int i=this.firstRow; i <= this.lastRow; i++) {
            //Create column
            this.column = this.columns.addNewTableColumn();
            this.column.setName("Column");
            this.column.setId(i + 1);
            //Create row
            this.row = this.sheet.createRow(i);
            for (int j = this.firstColumn; j <= this.lastColumn; j++) {
                //Create cell
                this.cell = this.row.createCell(j);
                if (i == this.firstRow) {
                    this.cell.setCellValue("Column" + j);
                } else {
                    this.cell.setCellValue("04");
                }
            }
        }
    }
    
}
