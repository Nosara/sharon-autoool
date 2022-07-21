import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InstallDriverTest {
        @Test
        public void chromeSession() throws IOException, InterruptedException {

            System.out.println("STEP 1: Reading files in order to get each of the Ids.");
            var names = getIds();

            System.out.println("There are " + names.size() + " docs.");

            System.out.println("STEP 2: Opening chrome session in debugging mode.");
//            openChromeSession();
//            Run this command : chrome.exe --remote-debugging-port=9222

            System.out.println("STEP 3: Creating selenium automated session.");
            var driver = createSeleniumSession();
            var actions = new BrowserActions(driver);

            System.out.println("STEP 4: Navigating to the page where the application is going to start the automated process.");
            navigateToPage(driver);

            System.out.println("STEP 5: Running the process for each id and collect the information.");
            var resultsList = new ArrayList<String>();

            names.forEach(id ->
            {
                try {
                    resultsList.addAll(searchId(actions, id));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            Thread.sleep(4000);

            System.out.println("Step 6: Write the information for each ID in the response.text.");
            var writeResponse = new WriteResposeFile();

            writeResponse.writeResponses(resultsList);
            driver.quit();
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

        private List<String> getIds(){
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

        private List<String> searchId(BrowserActions actions, String id) throws InterruptedException {
            actions.setText(ByElements.idInput, id);
            actions.sendKeysToElement(ByElements.idInputFilterButton, Keys.ENTER );
            actions.clickWithJavascriptExecutor(ByElements.equalToOption);

            Thread.sleep(4000);

            var data = new ArrayList<String>();

            var rows = actions.getDriver().findElements(ByElements.dataRows);

            rows.forEach(r-> {
                    var cells = r.findElements(ByElements.td);
                    final String[] rowText = {""};
                        cells.forEach(c-> {
                            Optional<String> t = Optional.of(actions.getText(c));
                           var text = t.isPresent() && (t.get().isBlank() || t.get().isEmpty())? "*" : t.get();
                            rowText[0] = rowText[0] + text +"~";
                                }
                        );
                        data.add(rowText[0]);
                    });

            return getIdData(id, data);
        }

        private List<String> getIdData(String id, List<String> rowsText){
            var response =  rowsText.stream().map(r ->{
                       var e =  r.split("~");

                       return e[0].equals("*")? "NO HAY, SORRY MAMASITA :C" : e[3]+" > "+e[6]+" > "+e[9]+" > "+e[11]+" > "+ e[13];
                    }).collect(Collectors.toList());

            response.add(0, id);

            return response;
        }
}
