package pages;

import core.element.Element;
import io.appium.java_client.AppiumBy;

public class DragPage extends BasePage {

    private Element drag(String position) {
        return new Element(AppiumBy.accessibilityId("drag-" + position));
    }

    private Element drop(String position) {
        return new Element(AppiumBy.accessibilityId("drop-" + position));
    }

    private final Element _lblSuccess = new Element(AppiumBy.androidUIAutomator("new UiSelector().text(\"You made it, click retry if you want to try it again.\")"));

    public void dragAndDrop() {
        String[] row = {"1","2","3"};
        String[] col = {"l","c","r"};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drag(col[i] + row[j]).dragToElement(drop(col[i] + row[j]));
            }
        }
    }

    public boolean isSuccessful() {
        return _lblSuccess.isDisplayed();
    }
}
