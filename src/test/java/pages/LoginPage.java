package pages;

import io.appium.java_client.AppiumBy;
import core.element.Element;

public class LoginPage extends BasePage {
    private final Element _txtEmail = new Element(AppiumBy.accessibilityId("input-email"));
    private final Element _txtPassword = new Element(AppiumBy.accessibilityId("input-password"));
    private final Element _btnLogin = new Element(AppiumBy.accessibilityId("button-LOGIN"));

    public void login(String email, String password) {
        _txtEmail.enter(email);
        _txtPassword.enter(password);
        _btnLogin.click();
    }
}
