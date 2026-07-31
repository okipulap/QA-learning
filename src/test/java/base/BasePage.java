package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }

    protected String getMessage(By locator) {
        return driver.findElement(locator).getText();
    }

    protected boolean isEnabled(By locator) {
        return driver.findElement(locator).isEnabled();
    }

    protected boolean isDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }

    protected String getTitleBase() {
        return driver.getTitle();
    }
}
