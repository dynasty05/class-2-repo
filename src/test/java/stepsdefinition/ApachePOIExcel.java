package stepsdefinition;

import com.github.javafaker.Faker;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.codec.language.Soundex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ribake on 03/03/2018.
 */
public class ApachePOIExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String sheetName = "Ribake's Sheet1";

    private WebDriver driver = Hooks.driver;

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

    @When("^I read the excel file \"(.*?)\"$")
    public void i_read_the_excel_file(String arg1) throws Throwable {
        // get the sample workbook
        StringBuffer pathBuffer = new StringBuffer();
        String slash = System.getProperty("file.separator");
        pathBuffer.append(System.getProperty("user.dir")).
                   append(slash).
                   append("src").
                   append(slash).
                   append("test").append(slash).
                   append("resources").append(slash).
                   append(arg1);
        String pathToWorkbook = pathBuffer.toString();
        workbook = new XSSFWorkbook(new FileInputStream(pathToWorkbook));
    }

    @Then("^I access a worksheet$")
    public void i_access_a_worksheet() throws Throwable {
        // get the workbook sheet
        sheet = workbook.getSheet("Sheet1");
    }

    @Then("^I access the rows and cells$")
    public void i_access_the_rows_and_cells() throws Throwable {
        // actual data rows begin from row 1
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            XSSFRow row = sheet.getRow(i);

            // for each cell/column in row
            for (int j=0; j < row.getPhysicalNumberOfCells(); j++){
                XSSFCell cell = row.getCell(j);
//                cell.getStringCellValue()
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                }
            }

            // new line before printing next row
            System.out.println("\n");
        }
    }

    /* @Then("^I convert the sheet data into hash with keys as column headers$")
    public void i_convert_the_sheet_data_into_hash_with_keys_as_column_headers() throws Throwable {
        HashMap<String,List<String>> sheetData = new HashMap();

        // for top row, go down each column, making first row value the key
        Row headerRow = sheet.getRow(0);
        for(int c=0; c<headerRow.getPhysicalNumberOfCells(); c++){
            String header = headerRow.getCell(c).getStringCellValue();

            // the values in every row of this column
            List<String> values = new ArrayList();
            for (int r=1; r<sheet.getPhysicalNumberOfRows(); r++){
                values.add(sheet.getRow(r).getCell(c).getStringCellValue());
            }
            sheetData.put(header, values);

        }

    } */


    // ANOTHER APPROACH: Converts sheet data into a list of mappings btw header and cell values
    @Then("^I convert the sheet data into hash with keys as column headers$")
    public void i_convert_the_sheet_data_into_hash_with_keys_as_column_headers() throws Throwable {
        sheet = workbook.getSheet("Sheet1");
        List<HashMap<String,String>> hashedSheetData = new ArrayList<HashMap<String, String>>();

        // for top row, go down each column and map header value to each cell
        Row headerRow = sheet.getRow(0);
        for(int c=0; c<headerRow.getPhysicalNumberOfCells(); c++){
            String header = headerRow.getCell(c).getStringCellValue();
            System.out.println("Current Column " + header);

            // the values in every row of this column
            for (int r=1; r<sheet.getPhysicalNumberOfRows(); r++){
                HashMap<String,String> headerToCell = new HashMap();
                Cell cell = sheet.getRow(r).getCell(c);

                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        headerToCell.put(header,cell.getStringCellValue());
                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        headerToCell.put(header,String.valueOf(cell.getNumericCellValue()));
                        break;

                }

                // add the hashmap to the list
                System.out.println("Current mapping for "+ header + "\t" + headerToCell.get(header));
                hashedSheetData.add(headerToCell);
            }

        }

    }


    @When("^I open practiceselenium website$")
    public void i_open_practiceselenium_website() throws Throwable {
        Hooks.driver.get("http://www.practiceselenium.com/practice-form.html");
    }

    @When("^I fill the form with data from excel$")
    public void i_fill_the_form_with_data_from_excel() throws Throwable {
        // fill form with data in row 1 in excel sheet
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        driver.findElement(By.name("firstname")).sendKeys(sheet.getRow(1).getCell(0).getStringCellValue());
        driver.findElement(By.name("lastname")).sendKeys(sheet.getRow(1).getCell(1).getStringCellValue());
        driver.findElement(By.id("sex-1")).click();
        driver.findElement(By.id("exp-2")).click();
        driver.findElement(By.id("datepicker")).sendKeys(sheet.getRow(1).getCell(4).getStringCellValue());
        driver.findElement(By.id("tea3")).click();
        driver.findElement(By.id("tool-1")).click();
        Select continents_select = new Select(driver.findElement(By.id("continents")));
        continents_select.selectByVisibleText(sheet.getRow(1).getCell(7).getStringCellValue());
        Select another_select_list = new Select(driver.findElement(By.id("selenium_commands")));
        another_select_list.selectByVisibleText(sheet.getRow(1).getCell(8).getStringCellValue());

    }

    @When("^I hit submit button$")
    public void i_hit_submit_button() throws Throwable {
        driver.findElement(By.id("submit")).click();
    }

    @Then("^I go back to Welcome page and verify title$")
    public void i_go_back_to_Welcome_page_and_verify_title() throws Throwable {
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(actualTitle,"Welcome");
    }





}
