package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utilities.Excel_Reader;
import methodclass.flipkartappClass;

public class PurchaseProduct {
	
	flipkartappClass flipclass = new flipkartappClass();

	@BeforeTest
	@Parameters("browser") 
	public void browserSetup(String browser) throws Exception
	{
		flipclass.launchBrowser(browser);
		Reporter.log("Browser launced successfully");
	}
	
	@DataProvider
	public Object[][] testdata() throws Exception{
		Object[][] data = getdata("testdata.xlsx", "Sheet1");
		return data;
	}
	
	public Object[][] getdata(String Excelname,String testcase) throws Exception{
		
		String path = System.getProperty("user.dir") + "\\DataSheet\\" +Excelname;
		
		Excel_Reader data = new Excel_Reader(path);
		
		int rowNum = data.getRowCount(testcase);
		
		int colNum = data.getColumnCount(testcase);
		
		
		Object sampledata[][] = new Object[rowNum - 1][colNum];
		
		for(int i = 2; i<=rowNum; i++)
		{
			for(int j=0; j<colNum; j++)
			{
				sampledata[i-2][j] = data.getCellData(testcase, j, i);
				System.out.println(sampledata);
			}
		}

		return sampledata;
	}
	
	@Test (dataProvider = "testdata")
	public  void buyProduct(String EmailId, String Password, String Runmode) throws Exception
	{
		if(Runmode.equals("N"))
		{
			throw new SkipException("No Test case to execute");
		}
		
		flipclass.logintoFlipkart(EmailId, Password);
		Reporter.log("Logged into Flipkart Application Successfullly");
		flipclass.searchProduct();
		Reporter.log("Product added to cart");
		flipclass.productAddtoCart();
		Reporter.log("Validated product deails and added to cart");
		flipclass.removeItemfromCard();
		Reporter.log("Item removed from cart");

	}

	@AfterTest
	public void closedriver() throws Exception
	{	
		flipclass.logoutfromFlipkart();
		flipclass.endTest();
		Reporter.log("Driver closed successfully");	
	}


}


