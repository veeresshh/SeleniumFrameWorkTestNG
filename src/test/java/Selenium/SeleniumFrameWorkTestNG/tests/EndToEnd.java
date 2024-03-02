package Selenium.SeleniumFrameWorkTestNG.tests;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EndToEnd {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver Driver = new ChromeDriver();
		Driver.manage().window().maximize();	
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		Driver.get("https://rahulshettyacademy.com/client/");	
		Driver.findElement(By.id("userEmail")).sendKeys("veeresshh@gmail.com");
		Driver.findElement(By.id("userPassword")).sendKeys("Abcd@123");
		Driver.findElement(By.id("login")).click();
		
		String Product = "ZARA COAT 3";
		//Get the List of All Items
		WebDriverWait Wait = new WebDriverWait(Driver, Duration.ofSeconds(5));  //see next steps to understand this
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> Products = Driver.findElements(By.cssSelector(".mb-3"));
		
		//Sort the list of Items 
		WebElement ProductsList = Products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(Product)).findFirst().orElse(null);
		
		// Add Selected Item to the Cart
		ProductsList.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//Wait untill Loader [ See the top step to define wait ]
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		Wait.until(ExpectedConditions.invisibilityOf(Driver.findElement(By.cssSelector(".ng-animating"))));
		//Another way of writing it [ Consumes lot of time ]
		//Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		
		//Click on Cart
		Driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		Thread.sleep(3000);
		//Fetch the List of Items inside the cart
		List<WebElement> CartItems = Driver.findElements(By.cssSelector(".cartSection h3"));		
		//to find if items match
		Boolean Match = CartItems.stream().anyMatch(CartItem->CartItem.getText().equalsIgnoreCase(Product));
		Assert.assertTrue(Match);

		//Click on Checkout
		Driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//Use Action class to select country
		Actions A = new Actions(Driver);
		
		A.sendKeys(Driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		Driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		
		//Click on Place Order
		Driver.findElement(By.xpath("//a[normalize-space()='Place Order']")).click();
		
		//Validation of Thank you Page
		String Validation = Driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(Validation.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		Thread.sleep(3000);
		Driver.findElement(By.xpath("//i[@class='fa fa-sign-out']")).click();

	}

}
