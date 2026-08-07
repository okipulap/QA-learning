package uiTests.seleniumWebForm.tests;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import uiTests.seleniumWebForm.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uiTests.seleniumWebForm.pages.WebFormPage;

public class WebFormTests extends BaseTest {

    @Test
    void successfulSubmitTest() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();
        assertEquals("Web form", title);

        String text = "some test data";
        page.enterTextInput(text);
        assertEquals("some test data", page.getTextFromTextBox());

        page.submit();
        assertEquals("Received!", page.getMessage());

    }

    @Test
    void emptyTextInputTest() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();
        assertThat(title).isEqualTo("Web form");

        String empty = "";
        page.enterTextInput(empty);
        assertThat(page.getTextFromTextBox()).isEmpty();

        page.submit();
        assertThat("Received!").isEqualTo(page.getMessage());
    }

    @Test
    void disabledInputShouldBeDisabledTest() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();

        assertThat(title).isEqualTo("Web form");
        assertThat(page.disabledInputIsEnabled()).isFalse();
        assertThat(page.disabledInputIsDisplayed()).isTrue();
    }

    @Test
    void readonlyInputShouldBeReadonlyTest() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();

        assertThat(title).isEqualTo("Web form");
        assertThat(page.readOnlyInputIsDisplayed()).isTrue();
        assertThat(page.readOnlyInputIsEnabled()).isTrue();
    }

    @ParameterizedTest
    @CsvSource ({
        "1, One",
        "2, Two",
        "3, Three"
    })
    void selectOptionByValueTest(String value, String text) {
        WebFormPage page = new WebFormPage(driver);

        page.getValueFromSelect(value);

        assertThat(page.getSelectedOptionText()).isEqualTo(text);
    }
}
