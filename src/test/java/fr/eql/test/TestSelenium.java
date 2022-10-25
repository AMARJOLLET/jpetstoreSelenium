package fr.eql.test;


import fr.eql.pageObject.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import utils.SeleniumTools;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelenium extends AbstractTestSelenium {

    // Chargement JDD
    Map<String, String> mapChargementJDD = outilsProjet.chargementCSVJDD("listeDeCourse");

    // Parametre
    String username = "j2ee";
    String password = "j2ee";
    int nombreAjout = 10;

    String categoryProduct = mapChargementJDD.get("categoryProduct");
    String product = mapChargementJDD.get("product");
    String subProduct = mapChargementJDD.get("subProduct");

    public TestSelenium() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL
        driver.get("https://petstore.octoperf.com/");

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
        LOGGER.info("TEST AVEC LE PRODUIT : " + product + " ET SON SOUS-PRODUIT " + subProduct);
        Map<String, WebElement> mapCategory = pageAccueil.returnMapProduct(wait);
        PageCategoryProduct pageCategoryProduct = pageAccueil.clickOnProduct(wait, driver, mapCategory.get(categoryProduct));
        LOGGER.info("Accès à la page Fish");
        PageSubProduct pageSubProduct = pageCategoryProduct.selectProductSample(wait, driver, product);
//        Map<String, WebElement> mapProduct = pageCategoryProduct.returnMapProduct();
//        PageSubProduct pageSubProduct = pageCategoryProduct.selectProduct(wait, driver, mapProduct.get(product));
        LOGGER.info("Accès à la page" + product);
        PageShoppingCart pageShoppingCart = pageSubProduct.addCartSubProductSample(wait, driver, subProduct);
//        Map<String, WebElement> mapSubProduct = pageSubProduct.returnMapSubProduct();
//        PageShoppingCart pageShoppingCart = pageSubProduct.addCartSubProduct(wait, driver, mapSubProduct.get(subProduct));
        LOGGER.info("Ajout " + subProduct + " au panier");
        assertEquals("Shopping Cart", pageShoppingCart.title(), "Le titre n'est pas celui attentu");
        Map<String, Map<String, WebElement>> mapShoppingCart = pageShoppingCart.returnMapShoppingCart();
        LOGGER.info("Une seul entrée : " + subProduct);
        LOGGER.info("Modification de la quantité avec " + nombreAjout);
        pageShoppingCart.setQuantityProduct(wait, driver, mapShoppingCart.get(subProduct).get("Quantity"), nombreAjout);
        LOGGER.info("Update de la carte");
        pageShoppingCart.clickUpdateCart(wait, driver);
        mapShoppingCart = pageShoppingCart.returnMapShoppingCart();
        LOGGER.info("Vérification des prix");
        double unitPrice = Double.parseDouble(mapShoppingCart.get(subProduct).get("List Price").getText().substring(1));
        double priceTotalProductUnitaire = Double.parseDouble(mapShoppingCart.get(subProduct).get("Total Cost").getText().substring(1));
        assertEquals(unitPrice * nombreAjout, priceTotalProductUnitaire, 0.0, "Le prix total " + subProduct + " n'est pas celui attentu");
        assertEquals(unitPrice * nombreAjout, pageShoppingCart.recupererSubtotal(), 0.0, "Le Subtotal n'est pas celui attentu");

        LOGGER.info("FIN DU TEST");
    }
}
