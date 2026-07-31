package tests;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.WebFormPage;

public class WebFormTests extends BaseTest {

    @Test
    void successFullSubmit() {
        WebFormPage page = new WebFormPage(driver);

        String title = page.getTitle();
        assertEquals("Web form", title);

        String text = "some test data";
        page.enterTextInput(text);
        assertEquals("some test data", page.getTextFromTextBox());

        page.submit();
        assertEquals("Received!", page.getMessage());

    }
}
