import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GitHubTest {

    @BeforeEach
    public void initDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to set session timeout */
            put("sessionTimeout", "15m");
        }});
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://192.168.100.3:4444/wd/hub"), options);
        driver.manage().window().setSize(new Dimension(1920, 1024));
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    @DisplayName("Example for demo")
    public void testIssue() {
        step("Open main page", () -> {
            open("https://github.com");
        });
        step("Open page with repositories", () -> {
            $x("//*[contains(@class, 'header-search-input')]").click();
            $x("//*[contains(@class, 'header-search-input')]").sendKeys("LevantsevichVsevolod/sample_project");
            $x("//*[contains(@class, 'header-search-input')]").submit();
            $x("//a[@href='/LevantsevichVsevolod/sample_project']").click();
        });
        step("Check title", () -> {
            $x("//a[@href=\"/LevantsevichVsevolod/sample_project\"]").shouldBe(Condition.visible);
        });
    }

    @AfterEach
    public void stopDriver() {
        Optional.of(WebDriverRunner.getWebDriver()).ifPresent(WebDriver::quit);
    }
}
