import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class OpenSiteSelenium {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test
    void openSelenium() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals("Web form", title);

        WebElement pass = driver.findElement(By.name("my-password"));
        WebElement submitBtn = driver.findElement((By.cssSelector("button")));

        pass.sendKeys("myPassword");
        submitBtn.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();

        assertEquals("Received!", value);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
