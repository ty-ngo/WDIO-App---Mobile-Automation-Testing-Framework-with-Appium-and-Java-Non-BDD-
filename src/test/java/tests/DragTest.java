package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.report.HtmlReporter;
import pages.DragPage;
import pages.HomePage;

public class DragTest extends BaseTest {
    private final HomePage _homePage = new HomePage();
    private final DragPage _dragPage = new DragPage();

    @Test(groups = {"drag"})
    public void dragTest() {
        HtmlReporter.info("1. Go to Drag tab");
        _homePage.goToTab("Drag");

        HtmlReporter.info("2. Drag and drop");
        _dragPage.dragAndDrop();

        HtmlReporter.info("3. Verify that success message is displayed");
        Assert.assertTrue(_dragPage.isSuccessful(), "Success message is not displayed");
    }
}
