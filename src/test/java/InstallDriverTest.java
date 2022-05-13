import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class InstallDriverTest {
        @Test
        public void chromeSession() throws IOException {
//            var readFilesNames = new ReadFilesNames();
//
//            var names = readFilesNames.getFilesNames();
//
//            for (String name : names) {
//                System.out.println(name);
//            }
//
//
//
//            var responseList = Arrays.asList(
//                    "1234", "PO-LUMINIC.INC-653",  "PO-LUMINIC.INC-653", "PO-LUMINIC.INC-653",
//                    "3456", "PO-LUMINIC.INC-653",  "PO-LUMINIC.INC-653", "PO-LUMINIC.INC-653",
//                    "7235", "NO HAY");
//
//            var writeResponse = new WriteResposeFile();
//
//            writeResponse.writeResponses(responseList);

            //Session setup
            WebDriverManager.chromedriver().setup();

            WebDriver driver;

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

            driver = new ChromeDriver(options);


            driver.switchTo().newWindow(WindowType.TAB);

            var actions = new BrowserActions(driver);


            //Test
            driver.get("https://google.com");

            driver.getTitle(); // => "Google"

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement searchBox = driver.findElement(By.name("q"));

            searchBox.sendKeys("Selenium");
            actions.click(By.name("btnK"));
//            searchButton.click();

            searchBox = driver.findElement(By.name("q"));
            searchBox.getAttribute("value"); // => "Selenium"

            driver.quit();
        }
}
