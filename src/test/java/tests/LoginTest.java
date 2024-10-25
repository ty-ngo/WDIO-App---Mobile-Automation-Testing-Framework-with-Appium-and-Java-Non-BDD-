package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.AppiumDriver.DriverHelper;
import core.report.HtmlReporter;
import pages.LoginPage;
import pages.HomePage;

public class LoginTest extends BaseTest {

    private final HomePage _homePage = new HomePage();
    private final LoginPage _loginPage = new LoginPage();

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
            {"user1@gmail.com", "password1"},
            {"user2@gmail.com", "password2"},
            {"user3@gmail.com", "password3"}
        };
    }

    @Test(dataProvider = "loginData", groups = {"login"})
    public void loginTest(String email, String password) {
        HtmlReporter.info("1. Go to Login tab");
        _homePage.goToTab("Login");

        HtmlReporter.info("2. Login with email and password");
        _loginPage.login(email, password);

        HtmlReporter.info("3. Verify the alert text");
        Assert.assertEquals(DriverHelper.getAlertText(), "Success\nYou are logged in!");

        HtmlReporter.info("4. Accept the alert");
        DriverHelper.acceptAlert();
    }
}
