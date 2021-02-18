package Source.Run_Test;


import Source.Config.Report_Manager;
import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(Cucumber.class)
@CucumberOptions(       features = { "src/test/java/Features/Search Functional Desktop Testing.feature"},
        plugin = { "com.cucumber.listener.ExtentCucumberFormatter:"},
        monochrome = true,
        glue= {""})


public class Amazon_Search_TestRun extends Report_Manager {

    @BeforeClass
    public static void setup() {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        extentProperties.setReportPath("target/Test_Reports/Amazon/"+timeStamp.replace(":","_")+".html");
    }

    @AfterClass
    public static void writeExtentReport(){
        Reporter.loadXMLConfig(Report_Manager.getReportConfigPath());
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("Machine", "Windows 12" + "66 Bit");
        Reporter.setSystemInfo("Selenium", "3.13.2");
        Reporter.setSystemInfo("Maven", "3.5.1");
        Reporter.setSystemInfo("Java Version", "8");
    }


}
