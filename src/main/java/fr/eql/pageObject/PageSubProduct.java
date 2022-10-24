package fr.eql.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageSubProduct extends AbstractFullPage{

    public PageSubProduct(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /*######################################################################################################################
                                                      WEBELEMENTS
    ######################################################################################################################*/

    @FindBy(xpath = "//div[@id=\"Catalog\"]//a")
    List<WebElement> listSubProduct;

/*######################################################################################################################
													METHODES
######################################################################################################################*/

    public void selectProduct (WebDriverWait wait, WebElement we){
        seleniumTools.clickOnElement(wait, we);
    }

    public Map<String, WebElement> returnMapSubProduct (){
        Map<String, WebElement> mapProduct = new HashMap<>();
        int index = (listSubProduct.size())/2;
        for (int i = 0; i < index; i++){
            mapProduct.put(listSubProduct.get(i).getText(), listSubProduct.get(i+1));
        }
        return mapProduct;
    }

    public PageShoppingCart addCartSubProduct (WebDriverWait wait, WebElement we) {
        seleniumTools.clickOnElement(wait, we);
        return new PageShoppingCart(driver);
    }
}
