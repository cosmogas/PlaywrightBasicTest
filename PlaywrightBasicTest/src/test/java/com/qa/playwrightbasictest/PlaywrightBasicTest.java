package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlaywrightBasicTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void teardown() {
        context.close();
    }

    @AfterAll
    static void teardownAll() {
        browser.close();
        playwright.close();
    }

    @Test
    @Order(1)
    @DisplayName("Verify DemoQA home page title")
    void verifyTitle() {
        page.navigate("https://demoqa.com/");
        assertEquals("DEMOQA", page.title().toUpperCase());
    }

    @Test
    @Order(2)
    @DisplayName("Open Text Box and check header")
    void openTextBoxAndCheck() {
        page.navigate("https://demoqa.com/text-box");
        Locator header = page.locator(".text-center");
        assertEquals("Text Box", header.textContent().trim());
    }

    @Test
    @Order(3)
    @DisplayName("Fill TextBox and submit")
    void fillTextBoxForm() {
        page.navigate("https://demoqa.com/text-box");

        page.fill("#userName", "Playwright Tester");
        page.fill("#userEmail", "tester@playwright.dev");
        page.fill("#currentAddress", "123 Broadway");
        page.fill("#permanentAddress", "456 Market Street");
        page.click("#submit");

        assertTrue(page.locator("#output").isVisible());
        assertTrue(page.locator("#name").textContent().contains("Playwright Tester"));
    }
}


