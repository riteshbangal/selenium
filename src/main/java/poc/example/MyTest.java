package poc.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class MyTest {
    @Test
    public void test() {
        // Set the path of the Chrome driver executable
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver");

        // Create a new Chrome driver instance
        WebDriver driver = new ChromeDriver();

        // Navigate to a web page
        driver.get("https://www.google.com");

        // Perform some actions on the web page
        // ...

        // Close the browser
        driver.quit();
    }
}
