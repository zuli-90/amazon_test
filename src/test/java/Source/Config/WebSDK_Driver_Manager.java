package Source.Config;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebSDK_Driver_Manager {

    private static Properties properties = null;
    static {
        try {
            properties = new Properties();
            System.setProperty("webdriver.chrome.driver", "src//test//java//resources//chromedriver");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public static WebDriver Get_Driver(String browser) {
        WebDriver driver ;

        switch (Objects.requireNonNull(getProperty(browser))) {
            case "firefox":

                driver = new FirefoxDriver();
                driver.get("https://www.amazon.com/");
                break;

            case "chrome":
                driver = new ChromeDriver();
                break;

            default:
                driver = new ChromeDriver();
                driver.get("https://www.amazon.com/");
        }
        return driver;
    }

    public static WebDriver Get_Mobile(String browser) {
        WebDriver driver ;

        switch (Objects.requireNonNull(getProperty(browser))) {
            case "firefox":

                driver = new FirefoxDriver();
                driver.get("https://www.amazon.com/");
                break;

            case "chrome":
                driver = new ChromeDriver();
                break;

            default:
                driver = new ChromeDriver();
                driver.get("https://www.amazon.com/");
        }
        return driver;
    }


    public static String getProperty(String key) {

        return properties == null ? null : properties.getProperty(key, "");
    }



}