package Selenium.SeleniumFrameWorkTestNG.tests;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import Selenium.SeleniumFrameWorkTestNG.TestComponents.BaseTest;
import Selenium.SeleniumFrameWorkTestNG.TestComponents.Retry;
import Selenium.SeleniumFrameWorkTestNG.PageObjects.CartPage;
import Selenium.SeleniumFrameWorkTestNG.PageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("anshika@gmail.com", "Iamki000");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		@SuppressWarnings("unused")
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
