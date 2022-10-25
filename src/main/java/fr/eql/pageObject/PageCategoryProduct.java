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

public class PageCategoryProduct extends AbstractFullPage{

    public PageCategoryProduct(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(xpath = "//div[@id=\"Catalog\"]//td")
    List<WebElement> listProduct;

/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public PageSubProduct selectProduct (WebDriverWait wait, WebDriver driver, WebElement we) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, we);
        return new PageSubProduct(driver);
    }

    public PageSubProduct selectProductSample (WebDriverWait wait, WebDriver driver, String productName) throws Throwable {
        WebElement we = driver.findElement(By.xpath("//div[@id='Catalog']//a[contains(@href, 'productId="+productName+"')]"));
        seleniumTools.clickOnElement(wait, driver, we);
        return new PageSubProduct(driver);
    }

    public Map<String, WebElement> returnMapProduct (){
        Map<String, WebElement> mapProduct = new HashMap<>();
        int index = listProduct.size();
        for (int i = 0; i < index; i++){
            LOGGER.info("Ajout dans la map : " + listProduct.get(i).getText() + " / " + listProduct.get(i));
            mapProduct.put(listProduct.get(i).getText(), listProduct.get(i));
        }
        return mapProduct;
    }
}
