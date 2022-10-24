package fr.eql.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Logging;
import utils.SeleniumTools;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


public class AbstractTestSelenium extends Logging {
    // LOGGER
    public Logger LOGGER = LoggerFactory.getLogger(className);
    SeleniumTools seleniumTools = new SeleniumTools(className);

    // Driver
    protected static WebDriver driver;
    protected WebDriverWait wait;
    protected int implicitWaitingTime = 2;
    protected int explicitWaitingTime = 10;

    //
    String navigateur = "chrome";

    @BeforeEach
    void startup() {
        LOGGER.info("Setup LOGGER ...");
        System.setProperty("logFileName", this.className);
        LOGGER.info("Setup LOGGER effectué");

        LOGGER.info("Setup Choix driver " + navigateur + " ...");

        switch (navigateur.toLowerCase()) {
            case "firefox" :
                System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome" :
                System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "edge" :
                System.setProperty("webdriver.edge.driver", "src/main/resources/driver/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
        }
        LOGGER.info("Setup Choix driver " + navigateur + " effectué");

        LOGGER.info("Setup wait et driver ...");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitingTime));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitingTime));
        LOGGER.info("Setup wait et driver effectué");
    }




    @AfterEach
    void tearDown() throws InterruptedException {
        LOGGER.info("Arret du driver ...");
        driver.quit();
        LOGGER.info("Arret du driver effectué");
    }


}
