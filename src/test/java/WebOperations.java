import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class WebOperations {

    static WebDriver driver;
    public static ExtentReports report =null;
    public static ExtentTest logger =null;


    public static String heatmapXpath = "//div[contains(text(),'View heatmap')]";
    public static String elementListButtonXpath = "//span[@data-qa='jepucefiga']";
    public static String emailButtonXpath = "(//table[@id='element-list--content']//td[contains(.,'email')])[1]";
    public static String visibilityBlockXpath = "//div[@id='_vwo_glass']";
    TakesScreenshot scrShot =((TakesScreenshot)driver);


    public void openBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://app.vwo.com/#/analyze/heatmap/129/reports?token=eyJhY2NvdW50X2lkIjo2LCJleHBlc%20mltZW50X2lkIjoxMjksImNyZWF0ZWRfb24iOjE1MDc3ODk0ODcsInR5cGUiOiJjYW1wYWlnbiIsI%20nZlcnNpb24iOjEsImhhc2giOiJiMzlmMTQ4MWE0ZDMyN2Q4MDllNTM1YzVlNWFjOGVlMCJ9");
        ExtentHtmlReporter extent = new ExtentHtmlReporter((System.getProperty("user.dir"))+"//reports//Report_Script.html");
        report= new ExtentReports();
        report.attachReporter(extent);
        Reporter.log("website launched");
    }

    public void clickHeatMap(){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        Actions action = new Actions(driver);
        action.moveByOffset(600, 600).perform();
        WebElement heatmapButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(heatmapXpath)));
        heatmapButton.click();
        takeScreenshot("clickHeatMap.png");
        Reporter.log("heat map clicked");
    }

    public  void switchTab(){
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));  // Assert That new tab is opening
        Assert.assertEquals(driver.getTitle(),"VWO | #1 A/B Testing Tool in the World");
        takeScreenshot("switchtab.png");
        Reporter.log("user switched to a new tab");
    }

    public void clickElementList(){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().frame("heatmap-iframe");
        WebElement elementListButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementListButtonXpath)));
        elementListButton.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.switchTo().window(tabs.get(1));
        driver.switchTo().frame("element-list-iframe");
        takeScreenshot("clickElementList.png");
        Reporter.log("clicked on element list frame");
    }

    public void clickEmailbutton(){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        WebElement emailButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(emailButtonXpath)));
        emailButton.click();
        driver.switchTo().defaultContent();
        takeScreenshot("clickEmailbutton.png");
        Reporter.log("clicked on email button");
    }

    public void checkBlockVisibility(){
        String displayValue=driver.findElement(By.xpath(visibilityBlockXpath)).getCssValue("display");
        Assert.assertEquals(displayValue, "block");
            takeScreenshot("checkBlockVisibility.png");
        Reporter.log("block visibility checked");
    }

    public void takeScreenshot(String filename) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile , new File("/Users/kritikasodhi/Downloads/Wingify/snapshots/" + filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterMethod
    public void closeBrowser(ITestResult r){

        if(r.getStatus()== ITestResult.FAILURE)
        {
            logger.fail("Fail Test");
        }

        else if(r.getStatus()==ITestResult.SUCCESS)
        {

            logger.pass("Pass Test");

        }

        report.flush();
        driver.quit();
        Reporter.log("website closed");
    }

}
