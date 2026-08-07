package uiTests.seleniumWebForm.pages;

import uiTests.seleniumWebForm.base.BasePage;
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
    private final By select = By.name("my-select");

    public WebFormPage(WebDriver driver) {
        super(driver);
    }

    public void enterTextInput(String text) {
        type(textInput, text);
    }

    public void enterTextInReadonly(String text) {
        type(readOnlyInput, text);
    }

    public void enterPass(String pass) {
        type(passInput, pass);
    }
    public void getTextFromSelect(String text) {
        selectByText(select, text);
    }

    public void getValueFromSelect(String value) {
        selectByValue(select, value);
    }

    public String getSelectedOptionText() {
        return selectedOptionText(select);
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

    public String getAttributeDisabled() {
        return isHaveDomAttributeDisabled(disabledInput);
    }

    public String getAttributeReadonly() {
        return  isHaveDomAttributeReadonly(readOnlyInput);
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
