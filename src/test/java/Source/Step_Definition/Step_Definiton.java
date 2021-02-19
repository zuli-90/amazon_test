package Source.Step_Definition;

import Source.Config.ScreenRecorderUtil;
import Source.Config.WebSDK_Driver_Manager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Step_Definiton {
    static WebDriver driver;

    private Scenario scenario = null;
    private static final long LONG_TIMEOUT = 50;
    private static final long SHORT_TIMEOUT = 15;
    private WebDriverWait wait;
    private String url;
    String api_base_url = "https://www.amazon.com/s?k=";

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        try {
            driver = new ChromeDriver();
        } catch (Exception ex) {
        }
        System.out.println("----------------------------------------- .......Test starting....... -----------------------------------------");
        this.scenario = scenario;

    }

    @After()
    public void afterUp() throws Exception {
        if (this.scenario.isFailed()) {
        }
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Given("^User go  to Amazon \"([^\"]*)\" version$")
    public void userGoToAmazonVersion(String version){
        driver = WebSDK_Driver_Manager.Get_Driver("chrome");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        System.out.println(driver.getTitle());
        driver.manage().timeouts().pageLoadTimeout(LONG_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(LONG_TIMEOUT, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, SHORT_TIMEOUT);
    }



    @Then("^Check if the search box is present$")
    public void checkIfTheSearchBoxIsPresent() {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        element.isDisplayed();
        System.out.println("Search box is displayed");

    }

    @Then("^The length \"([^\"]*)\" of the search specification field matches\\.$")
    public void theLengthOfTheSearchSpecificationFieldMatches(Dimension value) {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        Dimension size = element.getSize();
        Assert.assertEquals(size, value);

        System.out.println("Size of the button : "+ size);

    }

    @Then("^Check \"([^\"]*)\" characters that can be entered in the search field$")
    public void checkTheCharactersThatCanBeEnteredInTheSearchField(String character) {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        element.clear();
        element.sendKeys(character);
        String patternString = ".*$%^#:.*";

        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(character);

        if(matcher.find()){

            System.out.println("Invalid String" );
        }
        else{
            System.out.println("Valid String");
        }
    }

    @Then("^Check the length of \"([^\"]*)\" to be added to the search box\\.$")
    public void checkTheLengthOfToBeAddedToTheSearchBox(String character)  {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        element.clear();
        element.sendKeys(character);
        String typedValue = element.getAttribute("value");

        // Get the length of typed value
        int size = typedValue.length();

        // Assert with expected
        if (size == 10) {
            System.out.println("Max character functionality is working fine.");
        }

        else {
            System.out.println("No limit is set.");
        }
    }

    @Then("^Enter a \"([^\"]*)\" product name and click on the search button\\.$")
    public void enterAProductNameAndClickOnTheSearchIcon(String product) {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        element.clear();
        element.sendKeys(product);

        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @And("^The result with the product name \"([^\"]*)\" is displayed$")
    public void theResultWithTheProductNameIsDisplayed(String product) {
        String string= "\"";
        WebElement name = driver.findElement(By.xpath("//*[@id=\"search\"]/span/div/span/h1/div/div[1]/div/div/span[3]"));
        Assert.assertEquals(name.getText(),string+ product+string);
        System.out.println("Result Product Name is    "+ name.getText());

    }


    @Then("^Clear Search box and click on the search button$")
    public void enterNothingAndClickOnTheSearchButton() {
        WebElement element = driver.findElement(By.id("twotabsearchtextbox"));
        element.clear();
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @And("^User is on \"([^\"]*)\" page$")
    public void userIsOnPage(String url)  {
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, url );
        System.out.println(URL);
    }

    @And("^User get a message: \"([^\"]*)\"\"([^\"]*)\"$")
    public void userGetAMessage(String name, String message){
        WebElement product = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]/div[1]/span/div/div/div[1]/span[1]"));
        Assert.assertEquals(product.getText(), name);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]/div[1]/span/div/div/div[1]/span[2]")));
        WebElement error = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]/div[1]/span/div/div/div[1]/span[2]"));
        Assert.assertEquals(error.getText(), message);

        System.out.println(product.getText()+error.getText());

    }

    @Then("^User select language: \"([^\"]*)\"$")
    public void userSelectLanguage(String country) {
        WebElement language= driver.findElement(By.xpath("//*[@id='icp-nav-flyout']"));
        Actions action = new Actions(driver);
        action.moveToElement(language).build().perform();

        WebElement itemlink= driver.findElement(By.xpath("//*[@id=\"nav-flyout-icp\"]/div[2]/a[*]/span/i"));
        System.out.println( itemlink.getText());
        itemlink.getText().contains(country);
        itemlink.click();
        }



    public List<WebElement> waitVisibilityOfNestedElements(WebElement parent, By by) {
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, by));
    }

    public WebElement waitElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean checkText(WebElement element, String text) {
        return element.getText().equals(text);
    }

    public boolean containsText(WebElement element, String text) {
        return element.getText().contains(text);
    }

    public void clickElement(WebElement element) throws InterruptedException {
        waitElementToBeClickable(element).click();
        Thread.sleep(100);
    }

    public void clickElementAndWait(WebElement element) throws InterruptedException {
        waitElementToBeClickable(element).click();
        TimeUnit.MILLISECONDS.sleep(1000);
    }


    @Then("^Click on DropDown Description and select \"([^\"]*)\"$")
    public void clickOnDropDownDescriptionAndSelect(String categrie) throws Throwable {
        WebElement dropdown = driver.findElement(By.xpath("//*[@id=\"searchDropdownBox\"]"));
        dropdown.click();
        try {
            List<WebElement> menuList = dropdown.findElements(By.tagName("option"));
            for (WebElement categroieElement : menuList) {
                if (categroieElement.getText().contains(categrie)) {
                    categroieElement.click();
                    System.out.println("You select:   " + categroieElement.getText());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());

        }

    }

    @Then("^Click on deliver button$")
    public void clickOnDeliverButton() {
        WebElement delvierButton = driver.findElement(By.xpath("//*[@id=\"nav-global-location-popover-link\"]"));
        delvierButton.click();
    }

    @And("^Choose your location \"([^\"]*)\"$")
    public void chooseYourLocation(String location) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"GLUXCountryListDropdown\"]"))).click();
        WebElement dropdownlist = driver.findElement(By.id("a-popover-4"));
        WebElement dropdownelement = dropdownlist.findElement(By.tagName("ul"));
        WebElement dropdowlink = dropdownelement.findElement(By.tagName("li"));

        try {
            List<WebElement> menuList = dropdowlink.findElements(By.className("a-dropdown-link"));
            for (WebElement locactionElement : menuList) {
                if (locactionElement.getText().contains(location)) {
                    locactionElement.click();
                    System.out.println("You select:   " + locactionElement.getText());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());

        }


    }

}

