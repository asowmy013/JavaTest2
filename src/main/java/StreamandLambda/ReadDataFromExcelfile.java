package StreamandLambda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadDataFromExcelfile {

       public void TestData()
       {
            String filePath = "C:\\Users\\MC66MM\\OneDrive - ING\\Desktop\\GDP Training.xlsx";

            try (FileInputStream fileInputStream = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(fileInputStream)) {

                // Assuming the data is present in the first sheet
                Sheet sheet = workbook.getSheetAt(0);

                // Iterate over rows
                for (Row row : sheet) {
                    // Iterate over cells
                    for (Cell cell : row) {
                        // Extract value based on cell type
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
                            case BLANK:
                                System.out.print("BLANK\t");
                                break;
                            default:
                                System.out.print("\t");
                        }
                    }
                    System.out.println(); // Move to the next line after each row
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
    }


