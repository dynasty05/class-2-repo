package stepsdefinition;

import com.github.javafaker.Faker;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * Created by ribake on 03/03/2018.
 */
public class ApachePOIExcel {
    private XSSFWorkbook workbook;
    private String sheetName = "Ribake's Sheet1";

    @When("^I create the excel workbook \"(.*?)\"$")
    public void i_create_the_excel_workbook(String arg1) throws Throwable {
        // create a workbook
        workbook = new XSSFWorkbook();

        // add a worksheet to the workbook
        XSSFSheet sheet1 = workbook.createSheet(sheetName);

        Faker faker = new Faker();

        // Create two rows with 3 columns each
        for (int i = 0; i < 2; i++) {
            XSSFRow row = sheet1.createRow(i);
            row.createCell(0).setCellValue(faker.name().firstName());
            row.createCell(1).setCellValue(faker.address().streetAddressNumber());
            row.createCell(2).setCellValue(faker.phoneNumber().phoneNumber());
        }
        FileOutputStream fileOutput = new FileOutputStream(arg1);
        workbook.write(fileOutput);
        fileOutput.flush();
        fileOutput.close();
    }

    @Then("^I print the data inside the workbook$")
    public void i_print_the_data_inside_the_workbook() throws Throwable {
        // Read the contents of the Excel sheet
        XSSFSheet sheet = workbook.getSheet(sheetName);
        // Row by row
        for(int i=0; i < sheet.getPhysicalNumberOfRows(); i++){
            Row currentRow = sheet.getRow(i);
            // Each column of current row
            for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
                Cell currentCell = currentRow.getCell(j);
                System.out.println(currentCell.getStringCellValue() + "\t");

            }
        }

    }


    @Then("^I perform delete operations on a workbook$")
    public void i_perform_delete_operations_on_a_workbook() throws Throwable {
        XSSFSheet sheet = workbook.getSheet(sheetName);

        // remove row 1
        sheet.removeRow(sheet.getRow(1));

        // remove column 2 (or 3?) from row 0
        Row row1 = sheet.getRow(0);
        row1.removeCell(row1.getCell(2));

        // print remaining contents of sheet
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            Row currentRow = sheet.getRow(i);
            for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++){
                System.out.println(currentRow.getCell(j).getStringCellValue() + "\t");
            }

        }

    }
}
