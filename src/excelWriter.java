import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class excelWriter {

    public excelWriter(String type, String fileName, int vSize, int eCount, int cMDSAT, double tMDSAT, int cFF, double tFF, int cDSAT, double tDSAT , int currIteration){
        /*try {

            FileInputStream inputStream = new FileInputStream(new File("src\\Benchmarks.xlsx"));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = null;

            //  Set which sheet to write on
            if (type.equals("CAR"))
                sheet = workbook.getSheetAt(0);

            else if (type.equals("SGB")){
                sheet = workbook.getSheetAt(1);
            }

            //  If renyi graphs
            else {
                sheet = workbook.getSheetAt(2);
            }

            int startingRow = 3;
            int currentRowFill = startingRow + currIteration;

            Cell cell2Update;
            // Graph Name //
            cell2Update = sheet.getRow(currentRowFill).getCell(1);
            cell2Update.setCellValue(fileName);


            //  Fill V and E //
            cell2Update = sheet.getRow(currentRowFill).getCell(2);
            cell2Update.setCellValue(vSize);

            cell2Update = sheet.getRow(currentRowFill).getCell(3);
            cell2Update.setCellValue(eCount);
            //  UPDATE MDSAT    //
            //  C and T
            cell2Update = sheet.getRow(currentRowFill).getCell(7);
            cell2Update.setCellValue(cMDSAT);
            cell2Update = sheet.getRow(currentRowFill).getCell(8);
            cell2Update.setCellValue(tMDSAT);
            //  UPDATE FF    //
            //  C and T
            cell2Update = sheet.getRow(currentRowFill).getCell(9);
            cell2Update.setCellValue(cFF);
            cell2Update = sheet.getRow(currentRowFill).getCell(10);
            cell2Update.setCellValue(tFF);
            //  UPDATE DSAT    //
            //  C and T

            cell2Update = sheet.getRow(currentRowFill).getCell(11);
            cell2Update.setCellValue(cDSAT);
            cell2Update = sheet.getRow(currentRowFill).getCell(12);
            //cell2Update.setCellValue(tDSAT);

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream("src\\Benchmarks.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }*/
        try {
            /*//Read the spreadsheet that needs to be updated
            FileInputStream fsIP= new FileInputStream(new File("src\\Benchmarks.xlsx"));
            //Access the workbook
            HSSFWorkbook wb = new HSSFWorkbook(fsIP);
            //Access the worksheet, so that we can update / modify it.
            HSSFSheet worksheet = wb.getSheetAt(0);
            // declare a Cell object
            Cell cell = null;
            // Access the second cell in second row to update the value
            cell = worksheet.getRow(1).getCell(1);
            // Get current cell value value and overwrite the value
            cell.setCellValue("OverRide existing value");
            //Close the InputStream
            fsIP.close();
            //Open FileOutputStream to write updates
            FileOutputStream output_file =new FileOutputStream(new File("src\\Benchmarks.xlsx"));
            //write changes
            wb.write(output_file);
            //close the stream
            output_file.close();*/


            FileInputStream inputStream = new FileInputStream(new File("src\\Benchmarks_2.xlsx"));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = null;

            //  Set which sheet to write on
            if (type.equals("CAR"))
                sheet = workbook.getSheetAt(0);

            else if (type.equals("SGB")){
                sheet = workbook.getSheetAt(1);
            }

            //  If renyi graphs
            else {
                sheet = workbook.getSheetAt(2);
            }

            int startingRow = 3-1;
            int currentRowFill = startingRow + currIteration;

            Cell cell2Update;
            // Graph Name //
            cell2Update = sheet.getRow(currentRowFill).getCell(1-1);
            cell2Update.setCellValue(fileName);


            //  Fill V and E //
            cell2Update = sheet.getRow(currentRowFill).getCell(2-1);
            cell2Update.setCellValue(vSize);

            cell2Update = sheet.getRow(currentRowFill).getCell(3-1);
            cell2Update.setCellValue(eCount);
            //  UPDATE MDSAT    //
            //  C and T
            cell2Update = sheet.getRow(currentRowFill).getCell(7-1);
            cell2Update.setCellValue(cMDSAT);
            cell2Update = sheet.getRow(currentRowFill).getCell(8-1);
            cell2Update.setCellValue(tMDSAT);
            //  UPDATE FF    //
            //  C and T
            cell2Update = sheet.getRow(currentRowFill).getCell(9-1);
            cell2Update.setCellValue(cFF);
            cell2Update = sheet.getRow(currentRowFill).getCell(10-1);
            cell2Update.setCellValue(tFF);
            //  UPDATE DSAT    //
            //  C and T

            cell2Update = sheet.getRow(currentRowFill).getCell(11-1);
            cell2Update.setCellValue(cDSAT);
            cell2Update = sheet.getRow(currentRowFill).getCell(12-1);
            cell2Update.setCellValue(tDSAT);

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream("src\\Benchmarks_2.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
}
