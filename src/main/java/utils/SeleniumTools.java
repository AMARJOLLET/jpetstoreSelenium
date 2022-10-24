package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTools extends Logging {


    public void clickOnElement(WebDriverWait wait, WebElement we) {
        wait.until(ExpectedConditions.elementToBeClickable(we));
        we.click();
    }

    public void scrollToElement(WebDriverWait wait, WebDriver driver, WebElement we) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", we);
        wait.until(ExpectedConditions.elementToBeClickable(we));
    }

    public void sendKey(WebDriverWait wait, WebElement we, String string) {
        wait.until(ExpectedConditions.elementToBeClickable(we));
        we.click();
        we.clear();
        we.sendKeys(string);
    }

}
