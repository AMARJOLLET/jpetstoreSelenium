package fr.eql.pageObject;

import org.openqa.selenium.By;
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
    public PageShoppingCart addCartSubProductSample (WebDriverWait wait, WebDriver driver, String subProductName) throws Throwable {
        WebElement we = driver.findElement(By.xpath("//div[@id='Catalog']//a[contains(@href,'workingItemId="+subProductName+"')]"));
        seleniumTools.clickOnElement(wait, driver, we);
        return new PageShoppingCart(driver);
    }

    public Map<String, WebElement> returnMapSubProduct (){
        Map<String, WebElement> mapProduct = new HashMap<>();
        int index = (listSubProduct.size())/2;
        for (int i = 0; i < index; i++){
            LOGGER.info("Ajout dans la map : " + listSubProduct.get(i).getText() + " / " + listSubProduct.get(i+1));
            mapProduct.put(listSubProduct.get(i).getText(), listSubProduct.get(i+1));
        }
        return mapProduct;
    }

    public PageShoppingCart addCartSubProduct (WebDriverWait wait, WebDriver driver, WebElement we) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, we);
        return new PageShoppingCart(driver);
    }
}
