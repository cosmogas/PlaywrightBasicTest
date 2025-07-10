package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class PlaywrightAdvancedTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
    }

    @AfterEach
    void closePage() {
        if (page != null) page.close();
    }

    @AfterAll
    void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }



    @Test
    @DisplayName("2. Navigate to Elements page")
    void navigateToElements() {
        page.navigate("https://demoqa.com");
        page.locator(".card-body h5:has-text('Elements')").click();
        assertTrue(page.url().contains("elements"));
    }

    @Test
    @DisplayName("3. Fill and submit Text Box form")
    void fillTextBox() {
        page.navigate("https://demoqa.com/text-box");
        page.fill("#userName", "Alice");
        page.fill("#userEmail", "alice@example.com");
        page.fill("#currentAddress", "123 Main St");
        page.fill("#permanentAddress", "456 Other St");
        page.click("#submit");
        assertTrue(page.locator("#output").isVisible());
    }



    @Test
    @DisplayName("5. Select radio button")
    void selectRadioButton() {
        page.navigate("https://demoqa.com/radio-button");
        page.locator("label[for=yesRadio]").click();
        assertTrue(page.locator(".text-success").textContent().contains("Yes"));
    }



    @Test
    @DisplayName("7. Select dropdown value")
    void selectDropdown() {
        page.navigate("https://demoqa.com/select-menu");
        page.selectOption("#oldSelectMenu", "4");
        assertEquals("4", page.locator("#oldSelectMenu").inputValue());
    }





    @Test
    @DisplayName("10. Scroll into view and verify")
    void scrollAndClick() {
        page.navigate("https://demoqa.com");
        page.locator("footer").scrollIntoViewIfNeeded();
        assertTrue(page.locator("footer").isVisible());
    }
}

