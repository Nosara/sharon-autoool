import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
            var names = getIds();

            //PASO 2: Open chrome session in debugging mode.
            openChromeSession();

            //PASO 3: Create selenium automated session.
            var driver = createSeleniumSession();
            var actions = new BrowserActions(driver);

            //PASO 4: Navigate to the page where the application is going to start the automated process.
            navigateToPage(driver);

            //PASO 5: Build the functions to direct the process for each ID.
            names.forEach(id ->
            {
                try {
                    searchId(actions, id);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                var a = 0;
            });
//                actions.click(idInputFilterButton);
//            driver.findElement(idInputFilterButton).sendKeys(org.openqa.selenium.Keys.ENTER);

            //add wait


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

        private WebDriver createSeleniumSession(){
            WebDriverManager.chromedriver().setup();
            WebDriver driver;

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

            driver = new ChromeDriver(options);
            driver.switchTo().newWindow(WindowType.TAB);
            return driver;
        }

        private Set<String> getIds(){
            var readFilesNames = new ReadFilesNames();
            return readFilesNames.getFilesNames();
        }

        private void openChromeSession() throws IOException {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "chrome.exe --remote-debugging-port=9222");
            builder.redirectErrorStream(true);
            builder.start();
        }

        private void navigateToPage(WebDriver driver){
            driver.get("http://bpms.dole.com/Metastorm/Default.aspx");
            driver.getTitle().equals("OpenText MBPM");
        }

        private void searchId(BrowserActions actions, String id) throws InterruptedException {
            actions.setText(ByElements.idInput, id);
            actions.sendKeysToElement(ByElements.idInputFilterButton,org.openqa.selenium.Keys.ENTER );
            actions.clickWithJavascriptExecutor(By.xpath("/html/body/form/div[1]/ul/li[10]/a"));

            Thread.sleep(4000);

            var rows = actions.getDriver().findElements(ByElements.dataRows);
        }

        private List<String> getIdData(){
            return null;
        }
}
