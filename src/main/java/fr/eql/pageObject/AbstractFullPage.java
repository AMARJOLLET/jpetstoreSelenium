package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Logging;
import utils.OutilsProjet;
import utils.SeleniumTools;

public abstract class AbstractFullPage extends Logging {
    protected OutilsProjet outilsProjet = new OutilsProjet();
    protected SeleniumTools seleniumTools = new SeleniumTools();

    protected final WebDriver driver;
    //protected WebDriverWait wait;

    public AbstractFullPage(WebDriver driver) {
        this.driver = driver;
    }


}
