package core.AppiumDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import core.config.ConfigLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class DriverManager {

    public static AppiumDriver appiumDriver;
    // private static final ThreadLocal<AppiumDriver> _drivers = new ThreadLocal<>();

    public static void initDriver(String driverType) throws MalformedURLException, IOException {
        switch (driverType.toLowerCase()) {
            case "local" -> {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                URL appiumServer = new URL(ConfigLoader.getConfig("local.remoteUrl"));
                String platform = ConfigLoader.getConfig("local.platform");

                switch (platform.toLowerCase()) {
                    case "android" -> {
                        // Set Android capabilities
                        capabilities.setCapability("platformName", ConfigLoader.getConfig("local.Android.platformName"));
                        capabilities.setCapability("appium:deviceName", ConfigLoader.getConfig("local.Android.deviceName"));
                        capabilities.setCapability("appium:appPackage", ConfigLoader.getConfig("local.Android.appPackage"));
                        capabilities.setCapability("appium:appActivity", ConfigLoader.getConfig("local.Android.appActivity"));
                        capabilities.setCapability("appium:automationName", ConfigLoader.getConfig("local.Android.automationName"));
                        capabilities.setCapability("appium:noReset", ConfigLoader.getConfig("local.Android.noReset"));

                        appiumDriver = new AndroidDriver(appiumServer, capabilities);
                        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    }
                    // case "ios": {
                    //     // Set iOS capabilities
                    //     capabilities.setCapability("platformName", "iOS");
                    //     capabilities.setCapability("deviceName", "iPhone Simulator"); // or real device name
                    //     capabilities.setCapability("bundleId", "your.app.bundle.id"); // Your app's bundle ID
                    //     capabilities.setCapability("automationName", "XCUITest");
                    //     capabilities.setCapability("noReset", true);
                    //     appiumDriver = new IOSDriver(appiumServer, capabilities);
                    //     appiumDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
                    // }
                    default ->
                        throw new IllegalArgumentException("Unsupported platform: " + platform);
                }
            }

            case "browserstack" -> {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                HashMap<String, Object> bstackOptions = new HashMap<>();
                bstackOptions.put("userName", "ngohoangty_FZpPco");
                bstackOptions.put("accessKey", "C6nEK8WaZP8WxppBhSh2");
                bstackOptions.put("appiumVersion", "2.6.0");
                capabilities.setCapability("platformName", "android");
                capabilities.setCapability("appium:platformVersion", "14.0");
                capabilities.setCapability("appium:deviceName", "Google Pixel 9");
                capabilities.setCapability("appium:app", "bs://06edb8571a76d524f256c8438477308981cbe643");
                capabilities.setCapability("appium:automationName", "UIAutomator2");
                capabilities.setCapability("bstack:options", bstackOptions);

                appiumDriver = new AndroidDriver(new URL("https://hub.browserstack.com/wd/hub"), capabilities);
                appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }

            case "saucelab" -> {
                MutableCapabilities caps = new MutableCapabilities();
                caps.setCapability("platformName", "Android");
                caps.setCapability("appium:app", "storage:filename=android.wdio.native.app.v1.0.8.apk");  // The filename of the mobile app
                caps.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
                caps.setCapability("appium:platformVersion", "12.0");
                caps.setCapability("appium:automationName", "UiAutomator2");
                MutableCapabilities sauceOptions = new MutableCapabilities();
                sauceOptions.setCapability("username", "oauth-ngo.hoangty.sk3-8ec54");
                sauceOptions.setCapability("accessKey", "a0d4b26e-1837-4873-82a5-b1e02f21a9cb");
                sauceOptions.setCapability("build", "appium-build-G3ENG");
                sauceOptions.setCapability("name", "<your test name>");
                sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
                caps.setCapability("sauce:options", sauceOptions);

                URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
                appiumDriver = new AndroidDriver(url, caps);
                appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
        }
    }

    public static void quitDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
        }
    }
}
