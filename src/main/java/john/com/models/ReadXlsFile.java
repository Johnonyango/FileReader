package john.com.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import john.com.utilitiesDb.DbConnection;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXlsFile {
    DbConnection dbConnection;
    public void readFile() throws IOException {
        FileInputStream fis=new FileInputStream(new File("/home/john/FileManager/Employee.xlsx"));
        XSSFWorkbook wb=new XSSFWorkbook(fis);
        XSSFSheet sheet=wb.getSheetAt(0);
        FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();

        try {
            dbConnection = new DbConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(Row row: sheet)
        {

            for(Cell cell: row)
            {
                switch(formulaEvaluator.evaluateInCell(cell).getCellType())
                {

                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue()+ "\t\t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue()+ "\t\t");
                        break;
                }
            }
            System.out.println();
        }
    }
    public void dbData(){
        PreparedStatement st = (PreparedStatement) dbConnection;

    }
}