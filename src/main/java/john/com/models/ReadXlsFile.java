package john.com.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import john.com.utilitiesDb.DbConnection;
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

        try {
            dbConnection = new DbConnection();
            int a = 0;
            for(Row row: sheet) {
                if (a==0) {
                    a++;
                    continue;
                }
                dbData(row.getCell(0).getStringCellValue(), (int)row.getCell(1).getNumericCellValue(), row.getCell(2).getStringCellValue());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public boolean dbData(String name , int age,String town ){
        try {
            if (dbConnection==null){
                try {
                    dbConnection = new DbConnection();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            PreparedStatement st = dbConnection.getConnection().prepareStatement("INSERT INTO Employee(Name, Age, Town) VALUES(?, ?, ?)");
            st.setString(1, name);
            st.setInt(2, age);
            st.setString(3, town);
            return dbConnection.execute(st);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

//    Test main method
    public static void main(String[] args) {
        ReadXlsFile resd = new ReadXlsFile();
//        resd.dbData("John" , 21, "Nairobi");
        try {
            resd.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}