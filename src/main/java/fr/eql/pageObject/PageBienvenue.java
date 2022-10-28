package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBienvenue extends AbstractFullPage{

    public PageBienvenue(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(xpath = "//div[@id='Content']//a")
    WebElement buttonEnterTheStore;

    @FindBy(xpath = "//div[@id=\"Content\"]/h2")
    WebElement title;

/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public String title(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText();
    }

    public PageAccueil clickEnterTheStore (WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, buttonEnterTheStore);
        return new PageAccueil(driver);
    }

}
