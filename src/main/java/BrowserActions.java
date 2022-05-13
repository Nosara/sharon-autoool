import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.Assert.fail;


public class BrowserActions {
    private WebDriver driver;

    private final int MAX_TIMEOUT = 30;
    private final int MAX_ATTEMPTS = 30;

    BrowserActions(WebDriver driver){
        this.driver = driver;
    }

    public BrowserActions click(By by) {
        waitForCondition(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(by)))
                .waitForCondition(ExpectedConditions.elementToBeClickable(by));
        waitForElement(by).click();
        return this;
    }

    public BrowserActions selectOptionByText(By by, String text) {
        Select box = new Select(waitForElement(by));
        waitForCondition(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(by)))
                .waitForCondition(ExpectedConditions.elementToBeClickable(by));
        box.selectByVisibleText(text);
        return this;
    }

    public BrowserActions waitForCondition(long timeOutInSeconds, long sleepInMillis, ExpectedCondition<?>... conditions) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds), Duration.ofMillis(sleepInMillis));
        Arrays.asList(conditions)
                .forEach(wait::until);
        return this;
    }

    public BrowserActions waitForCondition(ExpectedCondition<?>... conditions) {
        return waitForCondition(MAX_TIMEOUT, conditions);
    }

    public BrowserActions waitForCondition(long timeOutInSeconds, ExpectedCondition<?>... conditions) {
        return waitForCondition(timeOutInSeconds, 1000, conditions);
    }

    public WebElement waitForElement(By by) {
        int attempts = 0;
        int size = driver.findElements(by).size();

        while (size == 0) {
            size = driver.findElements(by).size();
            if (attempts == MAX_ATTEMPTS) fail(String.format("Could not find %s after %d seconds",
                    by.toString(),
                    MAX_ATTEMPTS));
            attempts++;
            try {
                Thread.sleep(1000); // sleep for 1 second.
            } catch (Exception x) {
                fail("Failed due to an exception during Thread.sleep!");
                x.printStackTrace();
            }
        }

        if (size > 1) System.err.println("WARN: There are more than 1 " + by.toString() + " 's!");

        return driver.findElement(by);
    }
}
