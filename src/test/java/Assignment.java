import org.testng.annotations.Test;


public class Assignment {

    @Test
    public void testAssignment(){
        WebOperations operations = new WebOperations();
        operations.openBrowser();
        operations.clickHeatMap();
        operations.switchTab();
        operations.clickElementList();
        operations.clickEmailbutton();
        operations.checkBlockVisibility();
    }
}
