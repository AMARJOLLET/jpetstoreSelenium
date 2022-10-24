package fr.eql.pageObject;

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
    @FindBy(xpath = "//a[text()='FI-SW-01']")
    WebElement FISW01;

    @FindBy(xpath = "//div[@id=\"Catalog\"]//td")
    List<WebElement> listProduct;

    @FindBy(xpath = "//a[contains(@href,'workingItemId=EST-1')]")
    WebElement addCartEST1;


/*######################################################################################################################
													METHODES
######################################################################################################################*/

    public PageSubProduct selectProduct (WebDriverWait wait, WebElement we){
        seleniumTools.clickOnElement(wait, we);
        return new PageSubProduct(driver);
    }

    public Map<String, WebElement> returnMapProduct (){
        Map<String, WebElement> mapProduct = new HashMap<>();
        int index = listProduct.size();
        for (int i = 0; i < index; i++){
            mapProduct.put(listProduct.get(i).getText(), listProduct.get(i));
        }
        return mapProduct;
    }
}
