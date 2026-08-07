package uiTests.seleniumWebForm.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

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

    protected String getMessageFromPage(By locator) {
        return driver.findElement(locator).getText();
    }

    protected void selectByText(By locator, String text) {
        new Select(driver.findElement(locator)).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        new Select(driver.findElement(locator)).selectByValue(value);
    }

    protected String selectedOptionText(By locator) {
        return new Select(driver.findElement(locator)).getFirstSelectedOption().getText();
    }

    protected String isHaveDomAttributeDisabled(By locator) {
        return driver.findElement(locator).getDomAttribute("disabled");
    }

    protected String isHaveDomAttributeReadonly(By locator) {
        return driver.findElement(locator).getDomAttribute("readonly");
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
