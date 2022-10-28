package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageAccueil extends AbstractFullPage{

    public PageAccueil(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(xpath = "//div[@id=\"MainImageContent\"]//area")
    List<WebElement> listCategoryAnimals;

    @FindBy (id = "WelcomeContent")
    WebElement WelcomeContent;

/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public boolean signInDisplay (){
        return getHeader().signIn.isDisplayed();
    }

    public String WelcomeContent(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(WelcomeContent));
        return WelcomeContent.getText();
    }

    public PageConnexion clickSignIn (WebDriverWait wait) throws Throwable {
        return getHeader().clickSignIn(wait);
    }

    public PageCategoryProduct clickOnProduct (WebDriverWait wait, WebElement we) throws Throwable {
        seleniumTools.clickOnElement(wait, we);
        return new PageCategoryProduct(driver);
    }

    public Map<String, WebElement> returnMapProduct (WebDriverWait wait){
        return getHeader().returnMapProductQuickLinks(wait);
    }

}
