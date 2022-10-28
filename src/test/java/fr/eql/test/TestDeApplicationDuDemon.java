package fr.eql.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.OutilsProjet;
import utils.SeleniumTools;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class TestDeApplicationDuDemon extends AbstractTestSelenium{
    // Variable
    int cellule;
    int deplacerCellule;
    String dateStart = "";
    String dateEnd = "";


    @Test
    public void run() throws Throwable {
        driver.get("http://localhost/TutorialHtml5HotelPhp/");

        for (int j = 0; j < 5; j++) {

            String nameResa = "Resa"+(j+1);
            do {
                deplacerCellule = Integer.parseInt(OutilsProjet.generateRandomNumber(1));
                cellule = Integer.parseInt(OutilsProjet.generateRandomNumber(1));
            } while (deplacerCellule > 50 && cellule > 50);

            LOGGER.info("DEBUT DU TEST");

            try {
                List<WebElement> listResa = driver.findElements(By.xpath("//div[@class=\"scheduler_default_event_inner\"]"));
                int index = listResa.size();
                LOGGER.info("Il y a : " + listResa.size() + " réservations trouvées, suppression ...");
                for (int i = 0; i < index; i++) {
                    listResa = driver.findElements(By.xpath("//div[@class=\"scheduler_default_event_inner\"]"));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10000));
                    LOGGER.info("Début de la suppression de la réservation : " + (i + 1));
                    seleniumTools.mouseOver(wait, listResa.get(0));
                    WebElement deleteRoom = driver.findElement(By.xpath("//div[contains(@class,'delete')]"));
                    seleniumTools.clickOnElement(wait, deleteRoom);
                    LOGGER.info("Présence de l'alerte de suppression");

                    WebElement alertOn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class=\"scheduler_default_message\"]"))));
                    assertion.verifyEquals("Deleted.", alertOn.getText());
                    LOGGER.info("Absence de l'alerte de suppression");
                    boolean alertOff = wait.until(ExpectedConditions.invisibilityOf(driver.findElement(
                            By.xpath("//div[@class=\"scheduler_default_message\"]"))));
                    assertion.verifyTrue(alertOff);


                    LOGGER.info("Réservation : " + index + " supprimée");
                }
                LOGGER.info("Suppression effectué");
            } catch (NoSuchElementException e) {
                LOGGER.info("Pas de réservation encore");
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

            List<WebElement> listRoom = driver.findElements(By.xpath("//div[@class='scheduler_default_matrix']//div[@class='scheduler_default_cell']"));

            LOGGER.info("Ajout d'une nouvelle résa : " + nameResa + " ...");
            seleniumTools.clickOnElement(wait, listRoom.get(cellule));

            WebElement iframeNewRoom = driver.findElement(By.xpath("//iframe[contains(@src,'new.php')]"));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeNewRoom));

            WebElement nameField = driver.findElement(By.id("name"));
            WebElement startDateField = driver.findElement(By.id("start"));
            WebElement endDateField = driver.findElement(By.id("end"));
            WebElement saveButton = driver.findElement(By.xpath("//input[@type='submit']"));

            dateStart = outilsProjet.changementDate(startDateField.getAttribute("value"));
            dateEnd = outilsProjet.changementDate(endDateField.getAttribute("value"));

            seleniumTools.sendKey(wait, nameField, nameResa);
            seleniumTools.clickOnElement(wait, saveButton);
            driver.switchTo().defaultContent();
            LOGGER.info("Création de la résa effectué");

            LOGGER.info("Vérification de la résa ...");
            WebElement roomResa = driver.findElement(By.xpath("//div[@class=\"scheduler_default_event_inner\"]"));
            int ffs = (nameResa + " ("+dateStart+" - "+dateEnd+")").length();

            assertion.verifyEquals(nameResa + " ("+dateStart+" - "+dateEnd+")", roomResa.getText().substring(0, ffs));
            LOGGER.info("Vérification de la résa effectué");

            LOGGER.info("Drag and drop de la résa");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            listRoom = driver.findElements(By.xpath("//div[@class='scheduler_default_matrix']//div[@class='scheduler_default_cell']"));
            if (deplacerCellule >= listRoom.size()) {
                deplacerCellule = 10;
            }
            seleniumTools.dragAndDrop(wait, roomResa, listRoom.get(deplacerCellule));
            LOGGER.info("Présence de l'alerte de suppression");
            WebElement alertOn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class=\"scheduler_default_message\"]"))));
            assertion.verifyEquals("Update successful", alertOn.getText());
            LOGGER.info("Absence de l'alerte de suppression");
            boolean alertOff = wait.until(ExpectedConditions.invisibilityOf(driver.findElement(
                    By.xpath("//div[@class=\"scheduler_default_message\"]"))));
            assertion.verifyTrue(alertOff);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

            LOGGER.info("Suppression de la résa créée ...");
            roomResa = driver.findElement(By.xpath("//div[@class=\"scheduler_default_event_inner\"]"));
            seleniumTools.mouseOver(wait, roomResa);
            WebElement deleteRoom = driver.findElement(By.xpath("//div[contains(@class,'delete')]"));
            seleniumTools.clickOnElement(wait, deleteRoom);
            LOGGER.info("Suppression de la résa créée effectué");

            LOGGER.info("Assertion que la résa a été supprimé");
            listRoom = driver.findElements(By.xpath("//div[@class='scheduler_default_matrix']//div[@class='scheduler_default_cell']"));
            assertion.verifyEquals("", listRoom.get(cellule).getText());

            alertOn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class=\"scheduler_default_message\"]"))));
            assertion.verifyEquals("Deleted.", alertOn.getText());
            LOGGER.info("Absence de l'alerte de suppression");
            alertOff = wait.until(ExpectedConditions.invisibilityOf(driver.findElement(
                    By.xpath("//div[@class=\"scheduler_default_message\"]"))));
            assertion.verifyTrue(alertOff);

            LOGGER.info("FIN DU TEST");
        }
    }

}
