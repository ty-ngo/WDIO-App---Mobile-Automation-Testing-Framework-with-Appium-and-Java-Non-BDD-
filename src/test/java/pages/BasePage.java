package pages;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class BasePage {

    private Element _btnTab(String tabName) { return new Element(AppiumBy.accessibilityId(tabName)); }

    public void goToTab(String tabName) {
        _btnTab(tabName).click();
    }
}
