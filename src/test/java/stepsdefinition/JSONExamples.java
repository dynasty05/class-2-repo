package stepsdefinition;

import com.google.gson.*;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.util.Arrays;

/**
 * Created by ribake on 18/03/2018.
 */
public class JSONExamples {
    public String jsonString;
    private WebDriver driver = Hooks.driver;

    @When("^I create json string from object and write to file$")
    public void i_create_json_string_from_object_and_write_to_file() throws Throwable {
        // Object to serialise
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Ribake");
        employee.setLastName("Ikumoluyi");
        employee.setRoles(Arrays.asList("ADMIN", "MANAGER"));

        // Need a Gson object.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert object to JSON string, then bytes
        byte[] employeeBytes = gson.toJson(employee).getBytes();

        // write bytes to file
        FileOutputStream fos = new FileOutputStream("output.json");
        fos.write(employeeBytes);
        fos.flush();
        fos.close();

    }

    @Then("^I print it as a string$")
    public void i_print_it_as_a_string() throws Throwable {
        // read JSON string from the file
        FileInputStream fis = new FileInputStream(new File("output.json"));
        InputStreamReader in = new InputStreamReader(fis);
        BufferedReader buff = new BufferedReader(in);

        // string builder for the string
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = buff.readLine()) != null){
            sb.append(line);
        }
         // print the json string
        System.out.println(sb.toString());

        // if i want to deserialise the string into an object, I use of one Gson's fromJson()
        // methods like so:
        Employee emp = new Gson().fromJson(sb.toString(), Employee.class);

        // TODO: Read up Gson (fromJson) documentation
        // TODO: Do you serialise from a regular java object or from a JSON object?
    }

    @When("^I read json string from a file$")
    public void i_read_json_string_from_a_file() throws FileNotFoundException, IOException {
        FileReader fir = new FileReader(new File("src/test/resources/sample.json"));
        BufferedReader buff = new BufferedReader(fir);

        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = buff.readLine()) != null){
            sb.append(line);
        }
        jsonString = sb.toString();
    }

    @Then("^I parse the string and print keys$")
    public void i_parse_the_string_and_print_keys() {
        // TODO: Read JsonParse documentation

        // Parse the JSON string into a Json tree object (i.e a JsonElement or one of it's subtypes)
        JsonParser jsonParser = new JsonParser();
        // I know the object will be a JsonObject and not a JsonPrimitive or any
        // other subtype of JsonElement
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonString);

        // Accessing values in the JsonObject :
        // 1.) The desc key has a value that is also a JsonObject
        JsonObject descriptionObject = (JsonObject)jsonObject.get("desc");
        // 2.) Accessing the JsonElements in the descriptionObject
        // TODO: Can a JsonElement not also qualify as a JsonObject?
        JsonElement someKeyValue = descriptionObject.get("someKey");
        System.out.println("somekey value in Description object >> " + someKeyValue.getAsString());

    }

    @When("^I open practiceselenium website$")
    public void i_open_practiceselenium_website() {
        driver.get("http://www.practiceselenium.com/practice-form.html");
    }

    @When("^I read the json data file \"(.*?)\"$")
    public void i_read_the_json_data_file(String arg1) throws FileNotFoundException, IOException {
        FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/"+arg1));
        InputStreamReader in = new InputStreamReader(fin);
        BufferedReader bufferedReader = new BufferedReader(in);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        jsonString = sb.toString();
        System.out.println(jsonString);
    }

    @When("^I fill the form with data from json and submit$")
    public void i_fill_the_form_with_data_from_json_and_submit() throws Throwable {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        // What is the difference between casting to Json array and getting the value as jsonarray
//        JsonArray customerArray = (JsonArray)jsonObject.get("table");
        JsonArray customerArray = jsonObject.get("table").getAsJsonArray();
        JsonObject ribake = customerArray.get(0).getAsJsonObject();

        // fill the form with the values from ribake JsonObject
//        System.out.println("RIBAKE'S NAME >> " + ribake.get("firstname").getAsString());
        driver.findElement(By.name("firstname")).sendKeys(ribake.get("firstname").getAsString());
        driver.findElement(By.name("lastname")).sendKeys(ribake.get("lastname").getAsString());
        driver.findElement(By.id("sex-1")).click();
        driver.findElement(By.id("exp-2")).click();
        driver.findElement(By.id("datepicker")).sendKeys(ribake.get("date_stopped").getAsString());
        driver.findElement(By.id("tea3")).click();
        driver.findElement(By.id("tool-1")).click();
        Select continents_select = new Select(driver.findElement(By.id("continents")));
        continents_select.selectByVisibleText(ribake.get("continent").getAsString());
        Select another_select_list = new Select(driver.findElement(By.id("selenium_commands")));
        another_select_list.selectByVisibleText(ribake.get("selenium_commands").getAsString());
        driver.findElement(By.id("submit")).click();

        Assert.assertEquals("Welcome", driver.getTitle());

        // TODO: set up cucumber runner file for suite
    }

}
