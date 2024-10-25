package core.element;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import core.AppiumDriver.DriverManager;

public class Element {

    private final By by;

    public Element(By by) {
        this.by = by;
    }

    public String getUiAutomatorSelector() {
        StringBuilder selector = new StringBuilder("new UiSelector()");
        WebElement element = waitForElementToBeVisible(10);

        String resourceId = element.getAttribute("resource-id");
        if (resourceId != null && !resourceId.isEmpty()) {
            selector.append(".resourceId(\"").append(resourceId).append("\")");
        }

        String className = element.getAttribute("class");
        if (className != null && !className.isEmpty()) {
            selector.append(".className(\"").append(className).append("\")");
        }

        String text = element.getAttribute("text");
        if (text != null && !text.isEmpty()) {
            selector.append(".text(\"").append(text).append("\")");
        }

        String description = element.getAttribute("content-desc");
        if (description != null && !description.isEmpty()) {
            selector.append(".description(\"").append(description).append("\")");
        }

        return selector.toString();
    }

    public WebElement waitForElementToBeVisible(int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public WebElement waitForElementToBeClickable(int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(seconds));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public void waitForElementToChange(int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.appiumDriver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.stalenessOf(DriverManager.appiumDriver.findElement(by)));
        } catch (TimeoutException e) {
        }
    }

    public void click() {
        WebElement element = waitForElementToBeVisible(10);
        if (element != null) {
            element.click();
        }
    }

    public void scrollToElement() {
        HashMap<String, String> map = new HashMap<>();
        map.put("strategy", "-android uiautomator");
        map.put("selector", getUiAutomatorSelector());

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: scroll", map);
    }

    public void longClick(long duration) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("duration", duration);

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: longClickGesture", map);
    }

    public void doubleClick() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: doubleClickGesture", map);
    }

    public void simpleClick() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: clickGesture", map);
    }

    public void dragToPoint(Point target) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("endX", target.getX());
        map.put("endY", target.getY());

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: dragGesture", map);
    }

    public void dragToCoordinates(int endX, int endY) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("endX", endX);
        map.put("endY", endY);

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: dragGesture", map);
    }

    public void dragToElement(Element target) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("startX", getCenter().getX());
        map.put("startY", getCenter().getY());
        map.put("endX", target.getCenter().getX());
        map.put("endY", target.getCenter().getY());

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: dragGesture", map);
    }

    public void fling(String direction) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("direction", direction);

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: flingGesture", map);
    }

    public void pinchOpen(float percent) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("percent", percent);

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: pinchOpenGesture", map);
    }

    public void pinchClose(float percent) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("percent", percent);

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: pinchCloseGesture", map);
    }

    public void swipe(String direction, float percent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("direction", direction);
        map.put("percent", String.valueOf(percent));

        ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: swipeGesture", map);
    }

    public boolean scrollFromElement(String direction, float percent) {
        HashMap<String, String> map = new HashMap<>();
        map.put("elementId", ((RemoteWebElement) waitForElementToBeVisible(10)).getId());
        map.put("direction", direction);
        map.put("percent", String.valueOf(percent));

        return (boolean) ((JavascriptExecutor) DriverManager.appiumDriver).executeScript("mobile: scrollGesture", map);
    }

    public void enter(String value) {
        WebElement element = waitForElementToBeVisible(10);
        if (element != null) {
            element.sendKeys(value);
        }
    }

    public void select(String value) {
        WebElement element = waitForElementToBeVisible(10);
        if (element != null) {
            Select select = new Select(element);
            select.selectByVisibleText(value);
        }
    }

    public String text() {
        WebElement element = waitForElementToBeVisible(10);
        return (element != null) ? element.getText().trim() : "";
    }

    public void clear() {
        WebElement element = waitForElementToBeVisible(10);
        element.clear();
    }

    public List<WebElement> findAllElements() {
        return DriverManager.appiumDriver.findElements(by);
    }

    public boolean checkElementExists() {
        return !findAllElements().isEmpty();
    }

    public boolean isDisplayed() {
        WebElement element = waitForElementToBeVisible(10);
        return element.isDisplayed();
    }

    public boolean isEnabled() {
        WebElement element = waitForElementToBeVisible(10);
        return element.isEnabled();
    }

    public String getAttribute(String attr) {
        WebElement element = waitForElementToBeVisible(10);
        return element.getAttribute(attr);
    }

    public Point getLocation() {
        return waitForElementToBeVisible(10).getLocation();
    }

    public int getCoordinateX() {
        return waitForElementToBeVisible(10).getLocation().getX();
    }

    public int getCoordinateY() {
        return waitForElementToBeVisible(10).getLocation().getY();
    }

    public Dimension getSize() {
        return waitForElementToBeVisible(10).getSize();
    }

    public int getWidth() {
        return waitForElementToBeVisible(10).getSize().getWidth();
    }

    public int getHeight() {
        return waitForElementToBeVisible(10).getSize().getHeight();
    }

    public Point getCenter() {
        return new Point(getCoordinateX() + getWidth() / 2, getCoordinateY() + getHeight() / 2);
    }
}
