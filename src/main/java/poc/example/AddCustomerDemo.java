package poc.example;

import com.opencsv.CSVReader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AddCustomerDemo {

    WebDriver driver;

    @BeforeClass
    public void doLogin() throws InterruptedException {
        // Set the path of the Chrome driver executable
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver");

        // Create a new Chrome driver instance
        driver = new ChromeDriver();

        driver.get("http://stock.scriptinglogic.net/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("submit")).click();

    }

    @Test(dataProvider = "getCSVData")
    public void addCustomerTest(String name, String address, String phone1, String phone2) {

        driver.findElement(By.xpath("//*[text()='Add Customer']")).click();
        driver.findElement(By.xpath("//input[@id=\"name\"]")).sendKeys(name);
        driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys(address);
        driver.findElement(By.xpath("//input[@id='buyingrate']")).sendKeys(phone1);
        driver.findElement(By.xpath("//input[@id='sellingrate']")).sendKeys(phone2);
        driver.findElement(By.xpath("//input[@value='Add']")).click();
    }


    @DataProvider
    public Object[][] getCSVData() throws IOException {
        Object[][] data = new Object[0][0];

        Reader reader = null;
        CSVReader csvReader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir") + "/src/main/resources/mydata.csv"));
            csvReader = new CSVReader(reader);
            List<String[]> rows = csvReader.readAll();

            data = new Object[rows.size() - 1][4];

            for (int i = 1; i < rows.size(); i++) {
                List<String> column = Arrays.asList(rows.get(i));
                for (int j = 0; j < column.size(); j++) {
                    System.out.println("data : " + column.get(j));
                    data[i-1][j] = column.get(j).trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            csvReader.close();
            reader.close();
        }
        return data;
    }


    @DataProvider
    public Object[][] getExcelData() throws IOException {

        FileInputStream fp = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/book1.xlsx");
        try (XSSFWorkbook workbook = new XSSFWorkbook(fp)) {
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            int rowCount = sheet.getPhysicalNumberOfRows();
            Object[][] data = new Object[rowCount - 1][4];

            for (int i = 0; i < rowCount - 1; i++) {
                XSSFRow row = sheet.getRow(i + 1);

                for (int j = 0; j < 4; j++) {
                    XSSFCell cell = row.getCell(j);
                    data[i][j] = cell.toString().trim();
                    System.out.println("data : " + data[i][j]);
                }
            }
            return data;
        }
    }
}