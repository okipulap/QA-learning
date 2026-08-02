package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebFormPage extends BasePage {
    private final By textInput = By.name("my-text");
    private final By passInput = By.name("my-password");
    private final By textArea = By.cssSelector("textarea");
    private final By submitBtn = By.cssSelector("button");
    private final By disabledInput = By.name("my-disabled");
    private final By readOnlyInput = By.name("my-readonly");
    private final By message = By.id("message");

    public WebFormPage(WebDriver driver) {
        super(driver);
    }

    public void enterTextInput(String text) {
        type(textInput, text);
    }

    public void enterPass(String pass) {
        type(passInput, pass);
    }

    public void enterTextArea(String text) {
        type(textArea, text);
    }

    public void submit() {
        click(submitBtn);
    }

    public boolean disabledInputIsDisplayed() {
       return isDisplayed(disabledInput);
    }

    public boolean disabledInputIsEnabled() {
        return isEnabled(disabledInput);
    }

    public boolean readOnlyInputIsDisplayed() {
        return isDisplayed(readOnlyInput);
    }

    public boolean readOnlyInputIsEnabled() {
        return isEnabled(readOnlyInput);
    }

    public String getMessage() {
        return getMessageFromPage(message);
    }

    public String getTitle() {
        return getTitleBase();
    }

    public String getTextFromTextBox() {
       return getText(textInput);
    }
}
