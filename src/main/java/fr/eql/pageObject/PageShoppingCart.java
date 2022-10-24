package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageShoppingCart extends AbstractFullPage{

    public PageShoppingCart(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy (xpath = "//div[@id=\"Cart\"]/h2")
    WebElement Title;

    @FindBy(xpath = "//input [@name='EST-1']")
    WebElement quantityEST1;

    @FindBy(xpath = "//input[@value=\"Update Cart\"]")
    WebElement updateCart;

    @FindBy (xpath = "//input[@value=\"Update Cart\"]/..")
    WebElement subTotal;


/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public String title () {
        return Title.getText();
    }


    public void setQuantityEST1 (WebDriverWait wait, int nombreAjout){
        seleniumTools.sendKey(wait, quantityEST1, String.valueOf(nombreAjout));
    }

    public void clickUpdateCart (WebDriverWait wait) {
        seleniumTools.clickOnElement(wait, updateCart);
    }

    public double recupererSubtotal() {
        return Double.parseDouble(subTotal.getText().replace("Sub Total: $",""));
    }
}
