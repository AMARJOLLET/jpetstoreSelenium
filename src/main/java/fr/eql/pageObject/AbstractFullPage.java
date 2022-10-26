package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Logging;
import utils.OutilsProjet;
import utils.SeleniumTools;

public abstract class AbstractFullPage extends Logging {
    // Variable
    protected final static String snapshotsDirectory = "target/snapshots/";
    protected final WebDriver driver;

    // Objet
    protected SeleniumTools seleniumTools = new SeleniumTools(className);
    protected OutilsProjet outilsProjet = new OutilsProjet();


    public AbstractFullPage(WebDriver driver) {
        this.driver = driver;
    }

    public HeaderPage getHeader() {
        return new HeaderPage(driver);
    }

}
