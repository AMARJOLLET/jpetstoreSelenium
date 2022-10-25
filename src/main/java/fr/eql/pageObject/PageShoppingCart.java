package fr.eql.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageShoppingCart extends AbstractFullPage{

    public PageShoppingCart(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    @FindBy(xpath = "//div[@id=\"Cart\"]/h2")
    WebElement Title;

    @FindBy(xpath = "//input[@value=\"Update Cart\"]")
    WebElement updateCart;

    @FindBy(xpath = "//div[@id=\"Catalog\"]//tr")
    List<WebElement> listTable;

    @FindBy(xpath = "//div[@id=\"Catalog\"]//th")
    List<WebElement> listLibelleShoppingCart;


/*######################################################################################################################
													METHODES
######################################################################################################################*/
    public String title () {
        return Title.getText();
    }

    public Map<String, WebElement> returnMapProduct (WebDriverWait wait){
        return getHeader().returnMapProductQuickLinks(wait);
    }

    public Map<String, Map<String, WebElement>> returnMapShoppingCart(){
        Map<String, Map<String, WebElement>> mapShoppingCart = new HashMap<>();
        Map<String, WebElement> mapProductCart = new HashMap<>();

        for (int i = 1; i < listTable.size() - 1; i++) {
            List<WebElement> listProductCart = listTable.get(i).findElements(By.xpath("./td"));
            LOGGER.info("Lecture de la ligne " + listProductCart.get(0).getText());
            for (int j = 0; j < listProductCart.size(); j++){
                LOGGER.info("Ajout dans la mapProductCart : " + listProductCart.get(0).getText() + " de : "
                        +  listLibelleShoppingCart.get(j).getText() + " / " + listProductCart.get(j));
                mapProductCart.put(listLibelleShoppingCart.get(j).getText(), listProductCart.get(j));
            }
            mapShoppingCart.put(listProductCart.get(0).getText(), mapProductCart);
        }
        LOGGER.info("Lecture terminé");
        return mapShoppingCart;
    }

    public void setQuantityProduct(WebDriverWait wait, WebDriver driver, WebElement we, int nombreAjout) throws Throwable {
        WebElement inputQuantity = we.findElement(By.xpath("./input"));
        seleniumTools.sendKey(wait, driver, inputQuantity, String.valueOf(nombreAjout));
    }

    public void clickUpdateCart (WebDriverWait wait, WebDriver driver) throws Throwable {
        seleniumTools.clickOnElement(wait, driver, updateCart);
    }

    public double recupererSubtotal() {
        WebElement we = listTable.get(listTable.size()-1).findElement(By.xpath("./td"));
        return Double.parseDouble(we.getText().replace("Sub Total: $","").replace(",",""));
    }
}
