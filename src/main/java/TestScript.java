import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestScript {



    @Test
    public void Test1()
    {

       // System.setProperty("webdriver.chrome.driver","C:\\Users\\MC66MM\\Downloads\\chromedriver_win32\\chromedriver.exe");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://global.hitachi-solutions.com/");

        //*[@id="primary-nav"]/ul[3]

        WebElement contactUsButton = driver.findElement(By.xpath("//*[@id='primary-nav']/ul[3]"));

        contactUsButton.click();


        WebElement header = driver.findElement(By.cssSelector("header.hero-topographic"));

        // Find the h1 element within the header
        WebElement headingElement = header.findElement(By.tagName("h1"));

        //validating the header on contact us page;
        Assert.assertEquals("Contact Us – We’d like to hear from you!",headingElement.getText());

        WebElement firstName = driver.findElement(By.xpath("//*[@id='input_5_1']"));

        WebElement lastName = driver.findElement(By.xpath("//*[@id='input_5_3']"));

        WebElement companyName = driver.findElement(By.xpath("//*[@id='input_5_5']"));


        WebElement companyEmail_id = driver.findElement(By.xpath("//*[@id='input_5_6']"));

        WebElement country = driver.findElement(By.xpath("//*[@id='input_5_8']"));


        WebElement description = driver.findElement(By.xpath("//*[@id='input_5_12']"));

        WebElement checkBox = driver.findElement(By.id("label_5_11_2"));

        WebElement submit = driver.findElement(By.id("gform_submit_button_5"));


        firstName.sendKeys("Test1");
        lastName.sendKeys("lastname");
        companyName.sendKeys("Hitachi");
        companyEmail_id.sendKeys("Test1@hitachi.com");

        Select select = new Select(country);
        select.selectByVisibleText("United States");

        description.sendKeys("Irvine, United States\n" +
                "100 Spectrum Center Drive, Suite 350, Irvine CA 92618, United States\n" +
                "Corporate Phone: 949-242-1300");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkBox));


        ScrollElement(checkBox,driver);
        javaScriptExecutor(checkBox,driver);

        javaScriptExecutor(submit,driver);

        WebElement confimationMessage = driver.findElement(By.cssSelector("header.hero-topographic bg-white"));

        // Find the h1 element within the header
        WebElement confimationMessages = header.findElement(By.tagName("h1"));


        Assert.assertEquals("Thank You!",headingElement.getText());

        driver.quit();




    }


    public void javaScriptExecutor(WebElement element , WebDriver driver){

        JavascriptExecutor jsExecutor1 = (JavascriptExecutor) driver;
        jsExecutor1.executeScript("arguments[0].click();", element);

    }

    public void ScrollElement(WebElement element , WebDriver driver){

        JavascriptExecutor jsExecutor1 = (JavascriptExecutor) driver;
        jsExecutor1.executeScript("arguments[0].scrollIntoView(true);", element);

    }

}
