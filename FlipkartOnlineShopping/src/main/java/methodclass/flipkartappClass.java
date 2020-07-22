package methodclass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pageobjects.flipkartappObjects;

public  class flipkartappClass {

	public static flipkartappObjects login = new flipkartappObjects();
	private static final Logger log = LoggerFactory.getLogger(flipkartappClass.class);
	String exptdproductname = "Sony CyberShot DSC-H300";
	WebDriver driver;	
	static ExtentReports report = new ExtentReports(System.getProperty("user.dir")+"\\TestReports\\HTMLReports\\ExtentReportResults.html");
	static ExtentTest test = report.startTest("ExtentDemo");

	/**
	 * 
	 * @param browser
	 * @throws Exception
	 * @Description : Method for Browser Initialization
	 */

	public   void launchBrowser(String browser) throws Exception
	{
		try {

			log.info("Initializing Browser");

			String userDirectory = System.getProperty("user.dir");

			//Check if parameter is passed as 'chrome'
			if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", userDirectory + "\\drivers\\chromedriver.exe");

				driver = new ChromeDriver();

				driver.manage().window().maximize();

				test.log(LogStatus.INFO, "Navigated to the Chrome Browser");
			}

			//Check if parameter is passed as 'edge'
			else if(browser.equalsIgnoreCase("edge"))
			{
				System.setProperty("webdriver.edge.driver", userDirectory + "\\drivers\\msedgedriver.exe");

				driver = new EdgeDriver();
				
				driver.manage().window().maximize();

				test.log(LogStatus.INFO, "Navigated to the Edge Browser");
			}
			
			//Check if parameter is passed as 'firefox'
			else if(browser.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", userDirectory + "\\drivers\\geckodriver.exe");

				driver = new FirefoxDriver();
				
				driver.manage().window().maximize();

				test.log(LogStatus.INFO, "Navigated to the Firefox Browser");
			}

			log.info("Browser Launced Successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Unable to launch the browser");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to login to Flipkart Application
	 */

	@SuppressWarnings("deprecation")
	public void logintoFlipkart(String EmailId, String Password)  throws Exception
	{
		try
		{	
			log.info("Logging into Flipkart Application");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.get("https://www.flipkart.com");

			test.log(LogStatus.INFO,"FLipkart url launced successfully");

			// enter the user credentials to login

			driver.findElement(By.xpath(login.emailid)).sendKeys(EmailId);

			driver.findElement(By.xpath(login.password)).sendKeys(Password);

			driver.findElement(By.xpath(login.signin)).click();

			WebDriverWait wait = new WebDriverWait(driver,30);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.myaccounttab)));

			if(driver.findElement(By.xpath(login.myaccounttab)).isDisplayed())
			{
				test.log(LogStatus.PASS, "Logged into Flipkart Application successfully");
			}
			else
			{
				test.log(LogStatus.FAIL,"Unable to login into FLipkart Application");
				captureScreenshot("loginfail", ".png");		
			}
			log.info("Logged into Flipkart Application");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Unable to login to application");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to search for the product
	 */

	@SuppressWarnings("deprecation")
	public void searchProduct()  throws Exception
	{
		try
		{
			log.info("Searching for product : Camera");

			//driver.navigate().refresh();

			WebDriverWait wait = new WebDriverWait(driver,50);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.searchproduct)));

			WebElement searchtab = driver.findElement(By.xpath(login.searchproduct));

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			jse.executeScript("arguments[0].click()", searchtab);

			searchtab.sendKeys("camera" + Keys.ENTER);

			Assert.assertEquals("Cameras", driver.findElement(By.xpath(login.productpage)).getText());

			test.log(LogStatus.PASS, "Redirected to product page");

			// Fetching the items displayed on page 
			List<WebElement> noofitems = driver.findElements(By.xpath(login.listofitems));

			if(noofitems.size()>0)
			{
				for(int i=1; i<noofitems.size(); i++)
				{
					String listofproductnames = driver.findElement(By.xpath("(//div[@class='_3wU53n'])["+i+"]")).getText();

					// Comparing with the required item
					if(exptdproductname.equalsIgnoreCase(listofproductnames))

						test.log(LogStatus.PASS,"Item found from list " + listofproductnames);
				}
			}
			else
			{
				test.log(LogStatus.FAIL,"No Items are displayed on web page");
				captureScreenshot("ItemnotFound", ".png");	
			}
			log.info("Required Product is fetched");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Not able to fetch the required product");
			throw new Exception();
		}

	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to validate product details & add the product to cart
	 */

	public void productAddtoCart() throws Exception 
	{
		try {

			log.info("Product adding to cart");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			String mainWindow=driver.getWindowHandle();

			driver.findElement(By.xpath(login.producttopurchase)).click();     

			for (String winHandle : driver.getWindowHandles())
			{
				// WebDriver switch to the next found window i.e. newly opened window
				driver.switchTo().window(winHandle); 
			}

			//verifying the product details between product search page vs checkout page 
			verifyProductDetails();
			//validating the payment options
			validatePaymentOptions();

			//closing the child window and switching back to parent window
			driver.close(); 
			driver.switchTo().window(mainWindow); 

			log.info("Product added to cart successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Unable to add product to cart");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to verify product details between product search page vs checkout page 
	 */

	@SuppressWarnings("deprecation")
	public void verifyProductDetails() throws Exception
	{
		try {	
			log.info("Verifying product details");

			//fetching the product details from product search page
			String productname = driver.findElement(By.xpath(login.nameofproduct)).getText();

			String productprice = driver.findElement(By.xpath(login.priceofproduct)).getText();

			WebDriverWait wait = new WebDriverWait(driver,30);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.addcartbutton)));

			driver.findElement(By.xpath(login.addcartbutton)).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.placeorderbutton)));

			driver.findElement(By.xpath(login.placeorderbutton)).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.ordersummarytab)));

			// Navigated to order summary page
			if(driver.findElement(By.xpath(login.ordersummarytab)).isDisplayed())
			{
				test.log(LogStatus.PASS,"Order Summary page is displayed");

				//fetching the product details from product checkout page
				String prodname_checkout = driver.findElement(By.xpath(login.nameofprod_checkout)).getText();

				String prodspec_checkout = driver.findElement(By.xpath(login.sepcofprod_checkout)).getText();

				String concat_name_spec = prodname_checkout + "  (" + prodspec_checkout+ ")";

				//comparing product name displayed on search screen vs checkout screen 
				Assert.assertEquals(productname, concat_name_spec, "Product name ");

				test.log(LogStatus.PASS,"Product name matches & product name which specifications is : " + concat_name_spec);

				//comparing product price displayed on search screen vs checkout screen
				String prodprice_checkout = driver.findElement(By.xpath(login.priceofprod_checkout)).getText();

				Assert.assertEquals(productprice, prodprice_checkout, "Product price matches");

				test.log(LogStatus.PASS,"Product Price matches & price is : " + prodprice_checkout );
			}
			else
			{
				test.log(LogStatus.FAIL,"Order Summary Tab is not displayed");
				captureScreenshot("FailedprodDetails", ".png");
			}

			log.info("Product details verified successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Verification of product details failed");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to validate the payment options
	 */
	@SuppressWarnings("deprecation")
	public void validatePaymentOptions() throws Exception
	{
		try
		{
			log.info("Validating payment options");

			driver.findElement(By.xpath(login.continuebutton)).click();

			WebDriverWait wait = new WebDriverWait(driver,30);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.paymenttab)));

			if(driver.findElement(By.xpath(login.paymenttab)).isDisplayed())     
			{
				//fetching the list of payment options displayed
				List<WebElement> paymentoptions =  driver.findElements(By.xpath(login.listofpayments));

				if(paymentoptions.size()>0)
				{
					for(int i=1; i<paymentoptions.size(); i++)
					{
						String listofpayments = driver.findElement(By.xpath("(//div[@class='_1GRhLX _17_fE5']//label[@class='_8J-bZE _3C6tOa _2i24Q8'])["+i+"]")).getAttribute("for");
						test.log(LogStatus.PASS, "PaymentOptions " + listofpayments);	
					}
				}
				else
					test.log(LogStatus.FAIL,"No Payment Options are displayed");
				captureScreenshot("payoptions", ".png");
			}
			else
			{
				test.log(LogStatus.FAIL," Payments tab id not displayed");
				captureScreenshot("paymenttab", ".png");
			}

			log.info("Validated Payment Options Successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Payment Option validation failed");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to remove the selected product from cart
	 */

	@SuppressWarnings("deprecation")
	public void removeItemfromCard() throws Exception 
	{
		try 
		{
			log.info("To remove selected product from cart");

			//naviagte to cart page
			if(driver.findElement(By.xpath(login.cartbutton)).isDisplayed())
			{
				test.log(LogStatus.PASS,"Cart tab is displayed");
				driver.findElement(By.xpath(login.cartbutton)).click();
				test.log(LogStatus.INFO, "Cart tab clicked");
			}
			else
			{
				test.log(LogStatus.FAIL," Cart tab not displayed");
				captureScreenshot("carttab", ".png");
			}
			Thread.sleep(4000);

			//verifying the product name which is selected
			String verifyproduct = driver.findElement(By.xpath("//a[@class='_325-ji _3ROAwx']")).getText();

			Assert.assertEquals(exptdproductname, verifyproduct , "Verifying Product name to remove item from cart");

			test.log(LogStatus.PASS,"Product name verified");

			//clicking on remove item
			driver.findElement(By.xpath(login.removebutton)).click();

			WebDriverWait wait = new WebDriverWait(driver,10);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(login.confpopup)));

			//validating the confirmation popup for remove item
			if(driver.findElement(By.xpath(login.confpopup)).isDisplayed())
			{
				test.log(LogStatus.PASS,"Remove Item Conformation Popup is displayed");
				driver.findElement(By.xpath(login.confremovebutton)).click();
			}
			else
			{
				test.log(LogStatus.FAIL,"Remove Item Conformation Popup is not displayed");
				captureScreenshot("removeitem", ".png");
			}

			//validating the cart page after removing the item
			if(driver.findElement(By.xpath(login.emptycartpage)).isDisplayed())
			{
				test.log(LogStatus.PASS,"Empty Card");
			}
			else {
				test.log(LogStatus.INFO,"Items Present");
			}

			log.info("Product removed from cart");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Unable to remove item from cart");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to logout from application
	 */
	public  void logoutfromFlipkart() throws Exception 
	{
		try
		{
			log.info("Logout from flipkart application");

			Actions actions = new Actions(driver);

			//mouseover to My Accounts tab and click on logut button
			WebElement 	accountstab = driver.findElement(By.xpath(login.myaccounttab));

			actions.moveToElement(accountstab).perform();

			WebElement logoutbutton = driver.findElement(By.xpath(login.logoutbutton));

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			jse.executeScript("arguments[0].click()", logoutbutton);
			//logoutbutton.click();

			//validating the page after logout
			if(driver.findElement(By.xpath(login.loginbutton)).isDisplayed())
			{
				test.log(LogStatus.PASS,"Logged out from Flipkart Application Successfully");
			}
			else
			{
				test.log(LogStatus.FAIL, "Not logged out from flipkart application");
				captureScreenshot("logoutfail", ".png");
			}

			// to close the web driver
			driver.close();
			log.info("Logged out from flipkart application");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Unable to logout from Flipkart Application");
			throw new Exception();
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param extension
	 * @throws IOException
	 * @Description : Method to capture the screenshot
	 */

	public void captureScreenshot(String fileName,String extension)throws IOException
	{
		try{

			// Take the screenshot and store as file format
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			// Open the current date and time
			String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

			//Copy the screenshot on the desire location with different name using current date and time
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\TestReports\\Screenshots\\" + fileName+" "+timestamp+extension));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Description : Method to generate the reports aftertest
	 */
	public  void endTest() throws Exception
	{
		try
		{
			report.endTest(test);
			report.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
