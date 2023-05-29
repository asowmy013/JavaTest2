import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestScript2 {

    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testContactFormSubmission() {
        driver.get("https://global.hitachi-solutions.com/");

        WebElement contactUsButton = driver.findElement(By.xpath("//*[@id='primary-nav']/ul[3]"));
        contactUsButton.click();

        WebElement header = driver.findElement(By.cssSelector("header.hero-topographic"));
        WebElement headingElement = header.findElement(By.tagName("h1"));
        Assert.assertEquals("Contact Us – We’d like to hear from you!", headingElement.getText());

        WebElement firstName = driver.findElement(By.xpath("//*[@id='input_5_1']"));
        WebElement lastName = driver.findElement(By.xpath("//*[@id='input_5_3']"));
        WebElement companyName = driver.findElement(By.xpath("//*[@id='input_5_5']"));
        WebElement companyEmailId = driver.findElement(By.xpath("//*[@id='input_5_6']"));
        WebElement country = driver.findElement(By.xpath("//*[@id='input_5_8']"));
        WebElement description = driver.findElement(By.xpath("//*[@id='input_5_12']"));
        WebElement checkBox = driver.findElement(By.id("label_5_11_2"));
        WebElement submit = driver.findElement(By.id("gform_submit_button_5"));

        firstName.sendKeys("Test1");
        lastName.sendKeys("lastname");
        companyName.sendKeys("Hitachi");
        companyEmailId.sendKeys("Test1@hitachi.com");

        Select select = new Select(country);
        select.selectByVisibleText("United States");

        description.sendKeys("Irvine, United States\n" +
                "100 Spectrum Center Drive, Suite 350, Irvine CA 92618, United States\n" +
                "Corporate Phone: 949-242-1300");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkBox));

        javaScriptClick(checkBox);
        javaScriptClick(submit);

        WebElement confirmationHeader = driver.findElement(By.cssSelector("header.hero-topographic.bg-white"));
        WebElement confirmationHeading = confirmationHeader.findElement(By.tagName("h1"));
        Assert.assertEquals("Thank You!", confirmationHeading.getText());
    }

    public void javaScriptClick(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
