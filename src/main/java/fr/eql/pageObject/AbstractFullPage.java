package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Logging;
import utils.OutilsProjet;
import utils.SeleniumTools;

public abstract class AbstractFullPage extends AbstractBlockPage {
    private final HeaderPage header;


    public AbstractFullPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
    }

    public HeaderPage getHeader() {
        return header;
    }

}
