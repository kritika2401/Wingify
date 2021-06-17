import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.lang.reflect.Method;

public class Reporter {


    public static ExtentReports extent;
    public static ExtentTest logger;
    public ExtentHtmlReporter htmlReporter;

    public void reportMethod(Method testMethod){
        htmlReporter = new ExtentHtmlReporter("/Users/kritikasodhi/Downloads/Wingify/reports/AutomationReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        logger = extent.createTest(testMethod.getName());
    }
}
