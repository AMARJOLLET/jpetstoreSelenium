package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageConnexion extends AbstractFullPage{

    public PageConnexion(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(name = "username")
    WebElement fieldUsername;

    @FindBy(name = "password")
    WebElement fieldPassword;

    @FindBy(name = "signon")
    WebElement buttonSigneIn;

/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public PageAccueil seConnecter (WebDriverWait wait, String username, String password) throws Throwable {
        LOGGER.info("Renseigne : " + username);
        seleniumTools.sendKey(wait, fieldUsername, username);
        LOGGER.info("Renseigne : " + password);
        seleniumTools.sendKey(wait, fieldPassword, password);
        seleniumTools.clickOnElement(wait, buttonSigneIn);
        return new PageAccueil(driver);
    }

}
