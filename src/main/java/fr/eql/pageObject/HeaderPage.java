package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderPage extends AbstractBlockPage {

    public HeaderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(xpath = "//div[@id='MenuContent']/a[contains(@href,'viewCart')]")
    WebElement viewCart;

    @FindBy(xpath = "//div[@id='MenuContent']/a[contains(@href,'signon')]")
    WebElement signIn;

    @FindBy(xpath = "//div[@id='MenuContent']/a[contains(@href,'signoff')]")
    WebElement signOff;

    @FindBy(xpath = "//div[@id='QuickLinks']/a")
    List<WebElement> listProductQuickLinks;

/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public PageShoppingCart clickViewCart(WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, viewCart);
        return new PageShoppingCart(driver);
    }

    public PageConnexion clickSignIn(WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, signIn);
        return new PageConnexion(driver);
    }

    public void clickSignOff(WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, signOff);
    }

    public Map<String, WebElement> returnMapProductQuickLinks(){
        Map<String, WebElement> mapProductQuickLinks = new HashMap<>();
        for (WebElement we : listProductQuickLinks){
            String libelle = we.getAttribute("href");
            String indexStr = "categoryId=";
            libelle = libelle.substring(libelle.indexOf(indexStr)+indexStr.length());
            LOGGER.info("Ajout dans la map : " + libelle + " / " + we);
            mapProductQuickLinks.put(libelle, we);
        }
        return mapProductQuickLinks;
    }

}
