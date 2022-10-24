package fr.eql.test;


import fr.eql.pageObject.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelenium extends AbstractTestSelenium {

    // Parametre
    String loggin = "j2ee";
    String password = "j2ee";
    int nombreAjout = 2;

    String categoryProduct = "fish"; //toLowerCase()
    String product = "FI-SW-01";
    String subProduct = "EST-1";


    @Test
    void run() {
        LOGGER.info("DEBUT DU TEST");
        // Driver URL
        driver.get("https://petstore.octoperf.com/");

        PageBienvenue pageBienvenue = new PageBienvenue(driver);
        LOGGER.info("Connexion à la page de bienvenue");
        assertEquals("Welcome to JPetStore 6", pageBienvenue.title(wait));
        PageAccueil pageAccueil = pageBienvenue.clickEnterTheStore(wait);
        LOGGER.info("Affichage de la page d'accueil non connecté");
        //assertEquals("", pageAccueil.WelcomeContent(wait));
        PageConnexion pageConnexion = pageAccueil.clickSignIn(wait);
        LOGGER.info("Signin");
        pageAccueil = pageConnexion.seConnecter(wait, loggin, password);
        LOGGER.info("Connexion");
        assertEquals("Welcome ABC!", pageAccueil.WelcomeContent(wait), "Le message de bienvenue n'est pas celui attentu");
        Map<String, WebElement> mapCategory = pageAccueil.returnMapProduct(wait);
        PageCategoryProduct pageCategoryProduct = pageAccueil.clickOnProduct(wait, mapCategory.get(categoryProduct));
        LOGGER.info("Accès à la page Fish");
        Map<String, WebElement> mapProduct = pageCategoryProduct.returnMapProduct();
        PageSubProduct pageSubProduct = pageCategoryProduct.selectProduct(wait, mapProduct.get(product));
        LOGGER.info("Accès à la page" + product);
        Map<String, WebElement> mapSubProduct = pageSubProduct.returnMapSubProduct();
        PageShoppingCart pageShoppingCart = pageSubProduct.addCartSubProduct(wait, mapSubProduct.get(subProduct));
        LOGGER.info("Ajout "+ subProduct +" au panier");
        assertEquals("Shopping Cart", pageShoppingCart.title(), "Le titre n'est pas celui attentu");
        LOGGER.info("Modification de la quantité avec " + nombreAjout);
        pageShoppingCart.setQuantityEST1(wait, nombreAjout);
        LOGGER.info("Update de la cart");
        pageShoppingCart.clickUpdateCart(wait);
        assertEquals(16.5 * nombreAjout, pageShoppingCart.recupererSubtotal(), 0.0, "Le Subtotal n'est pas celui attentu");
        LOGGER.info("FIN DU TEST");




        LOGGER.info("FIN DU TEST");
    }
}
