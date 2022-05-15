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
          /*  APPROACH TO FOLLOW
                    1. Read files in order to get each of the Ids.
                    2. Open chrome session in debugging mode.
                    3. Create selenium automated session.
                    4. Navigate to the page where the application is going to start the automated process.
                    5. Build the functions to direct the process for each ID.
                    6. Run the process for each id and collect the information.
                    7. Write the information for each ID in the response.text.
            */

            //PASO 1: Read files in order to get each of the Ids.
            var readFilesNames = new ReadFilesNames();
            var names = readFilesNames.getFilesNames();

            //PASO 2: Open chrome session in debugging mode.
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "chrome.exe --remote-debugging-port=9222");
            builder.redirectErrorStream(true);
            builder.start();

            //PASO 3: Create selenium automated session.
            WebDriverManager.chromedriver().setup();
            WebDriver driver;

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

            driver = new ChromeDriver(options);
            driver.switchTo().newWindow(WindowType.TAB);

            var actions = new BrowserActions(driver);


            //PASO 4: Navigate to the page where the application is going to start the automated process.
            driver.get("http://bpms.dole.com/Metastorm/Default.aspx");

            //PASO 5: Build the functions to direct the process for each ID.

            /*
            * Input name para el id ctl00$phMainContent$CustomListPart$CustomListPart_Grid$ctl00$ctl02$ctl01$Filter_InvoiceNumber
            *
            * input filter para el id ctl00$phMainContent$CustomListPart$CustomListPart_Grid$ctl00$ctl02$ctl01$ctl16
            *
            * */


            //PASO 6: Run the process for each id and collect the information.

            //PASO 7: Write the information for each ID in the response.text.
            var responseList = Arrays.asList(
                    "1234", "PO-LUMINIC.INC-653",  "PO-LUMINIC.INC-653", "PO-LUMINIC.INC-653",
                    "3456", "PO-LUMINIC.INC-653",  "PO-LUMINIC.INC-653", "PO-LUMINIC.INC-653",
                    "7235", "NO HAY");

            var writeResponse = new WriteResposeFile();

            writeResponse.writeResponses(responseList);

//            //Test
//            driver.get("https://google.com");
//
//            driver.getTitle(); // => "Google"
//
//            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//
//            WebElement searchBox = driver.findElement(By.name("q"));
//
//            searchBox.sendKeys("Selenium");
//            actions.click(By.name("btnK"));
////            searchButton.click();
//
//            searchBox = driver.findElement(By.name("q"));
//            searchBox.getAttribute("value"); // => "Selenium"
//
//            driver.quit();
        }
}
