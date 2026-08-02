package tests;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.WebFormPage;

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
    void disabledInputShouldBeDisabled() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();

        assertThat(title).isEqualTo("Web form");
        assertThat(page.disabledInputIsEnabled()).isFalse();
        assertThat(page.disabledInputIsDisplayed()).isTrue();
    }

    @Test
    void readonlyInputShouldBeReadonly() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();

        assertThat(title).isEqualTo("Web form");
        assertThat(page.readOnlyInputIsDisplayed()).isTrue();
        assertThat(page.readOnlyInputIsEnabled()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "One, 1",
            "Two, 2",
            "Three, 3"
    })
    void shouldAcceptSuggestedValue(String ) {

    }
}
