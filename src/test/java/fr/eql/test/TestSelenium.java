package fr.eql.test;


import fr.eql.pageObject.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import utils.OutilsProjet;
import utils.SeleniumTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelenium extends AbstractTestSelenium {

    // Chargement JDD
    ArrayList<Map<String, String>> listJDD = outilsProjet.loadCsvSeveralJDD("listeDeCourse");

    // Parametre
    String username = "j2ee";
    String password = "j2ee";
    double unitPrice;
    double subTotal;

    public TestSelenium() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL
        driver.get("http://192.168.102.174:8081/jpetstore");

        PageBienvenue pageBienvenue = new PageBienvenue(driver);
        LOGGER.info("Connexion à la page de bienvenue");
        assertEquals("Welcome to JPetStore 6", pageBienvenue.title(wait), "Le message de bienvenue n'est pas celui attentu");
        PageAccueil pageAccueil = pageBienvenue.clickEnterTheStore(wait, driver);
        LOGGER.info("Affichage de la page d'accueil non connecté");
        assertTrue(pageAccueil.signInDisplay());
        PageConnexion pageConnexion = pageAccueil.clickSignIn(wait, driver);
        LOGGER.info("Signin");
        pageAccueil = pageConnexion.seConnecter(wait, driver, username, password);
        LOGGER.info("Connexion avec username : " + username);
        assertEquals("Welcome ABC!", pageAccueil.WelcomeContent(wait), "Le message de bienvenue n'est pas celui attentu");
        LOGGER.info("Accès à la page Fish");
        for (int i = 1; i < listJDD.size(); i++) {
            LOGGER.info("TEST AVEC LE PRODUIT : " + listJDD.get(i).get("product") + " ET SON SOUS-PRODUIT " + listJDD.get(i).get("subProduct"));
            Map<String, WebElement> mapCategory = pageAccueil.returnMapProduct(wait);
            PageCategoryProduct pageCategoryProduct = pageAccueil.clickOnProduct(wait, driver, mapCategory.get(listJDD.get(i).get("categoryProduct")));
            PageSubProduct pageSubProduct = pageCategoryProduct.selectProductSample(wait, driver, listJDD.get(i).get("product"));
//        Map<String, WebElement> mapProduct = pageCategoryProduct.returnMapProduct();
//        PageSubProduct pageSubProduct = pageCategoryProduct.selectProduct(wait, driver, mapProduct.get(product));
            LOGGER.info("Accès à la page" + listJDD.get(i).get("product"));
            PageShoppingCart pageShoppingCart = pageSubProduct.addCartSubProductSample(wait, driver, listJDD.get(i).get("subProduct"));
//        Map<String, WebElement> mapSubProduct = pageSubProduct.returnMapSubProduct();
//        PageShoppingCart pageShoppingCart = pageSubProduct.addCartSubProduct(wait, driver, mapSubProduct.get(subProduct));
            LOGGER.info("Ajout " + listJDD.get(i).get("subProduct") + " au panier");
            assertEquals("Shopping Cart", pageShoppingCart.title(), "Le titre n'est pas celui attentu");
            Map<String, Map<String, WebElement>> mapShoppingCart = pageShoppingCart.returnMapShoppingCart();
            LOGGER.info("Une seul entrée : " + listJDD.get(i).get("subProduct"));
            int nombreAjout = Integer.parseInt(OutilsProjet.generateRandomNumber(1));
            LOGGER.info("Modification de la quantité avec " + nombreAjout);
            pageShoppingCart.setQuantityProduct(wait, driver, mapShoppingCart.get(listJDD.get(i).get("subProduct")).get("Quantity"), nombreAjout);
            LOGGER.info("Update de la carte");
            pageShoppingCart.clickUpdateCart(wait, driver);
            mapShoppingCart = pageShoppingCart.returnMapShoppingCart();
            unitPrice = Double.parseDouble(mapShoppingCart.get(listJDD.get(i).get("subProduct")).get("List Price").getText().substring(1));
            double priceTotalProductUnitaire = Double.parseDouble(mapShoppingCart.get(listJDD.get(i).get("subProduct")).get("Total Cost").getText().substring(1).replace(",",""));
            assertEquals(unitPrice * nombreAjout, priceTotalProductUnitaire, 0.0, "Le prix total " + listJDD.get(i).get("subProduct") + " n'est pas celui attentu");
            subTotal = subTotal + priceTotalProductUnitaire;
        }

        LOGGER.info("Vérification des prix");
        PageShoppingCart pageShoppingCart = new PageShoppingCart(driver);
        assertEquals(subTotal, pageShoppingCart.recupererSubtotal(), 0.0, "Le Subtotal n'est pas celui attentu");

        LOGGER.info("FIN DU TEST");
    }
}
