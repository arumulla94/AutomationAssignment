package pageobjects;

public class flipkartappObjects
{

    // xpaths related to login functionality
	public final  String loginbutton = "//a[contains(text(),'Login')]";
	public final  String emailid = "//div[@class='_32LSmx']//div[@class='_39M2dM JB4AMj']//input[@class='_2zrpKA _1dBPDZ']";
	public final  String password = "//input[@class='_2zrpKA _3v41xv _1dBPDZ']";
	public final  String signin = "//button[@type='submit']//span[text()='Login']";
	public final  String myaccounttab = "//div[@class='dHGf8H']";
	
	// Xpaths related to search product 
	public final  String searchproduct = "//input[@title='Search for products, brands and more']";
	public final  String serachbutton = "//button[@class='vh79eN' and @type='submit']";
	public final  String listofitems = "//div[@class='_3wU53n']";
	public final  String productpage = "(//div[@class='sUG0yY']//a)[2]";
	public final  String producttopurchase = "//div[@class='_3wU53n' and text()='Sony CyberShot DSC-H300']";

	// Xpaths related to product details
	public final  String nameofproduct = "//span[@class='_35KyD6']";
	public final  String priceofproduct = "//div[@class='_1vC4OE _3qQ9m1']";
	public final  String addcartbutton = "//button[@class='_2AkmmA _2Npkh4 _2MWPVK']";
	public final  String placeorderbutton = "//button[@class='_2AkmmA iwYpF9 _7UHT_c']//span[text()='Place Order']";
	public final  String ordersummarytab = "//span[text()='Order Summary']";
	public final  String nameofprod_checkout = "//div[@class='_325-ji']";
	public final  String sepcofprod_checkout = "//div[@class='v7-Wbf']";
	public final  String priceofprod_checkout ="//span[@class='pMSy0p XU9vZa']";
	public final  String continuebutton = "//button[text()='CONTINUE']";
	public final  String paymenttab = "//span[text()='Payment Options']";
	public final  String listofpayments = "//div[@class='_1GRhLX _17_fE5']//label[@class='_8J-bZE _3C6tOa _2i24Q8']";
	
    // Xpaths related to remove item from cart
	public final  String cartbutton = "//span[text()='Cart']";
	public final  String mycartpage = "//div[@class='_2EoEbp']";
	public final  String removebutton = "//div[text()='Remove']";
	public final  String confpopup = "//div[text()='Remove Item']";
	public final  String confremovebutton = "//div[@class='gdUKd9 _3Z4XMp _2nQDKB' and text()='Remove']";
	public final  String emptycartpage = "//div[@class='hJKWmk' and text()='Your cart is empty!']";

	// Xpaths related to logout functionality
	public final  String logoutbutton = "//div[@class='_1Q5BxB' and text()='Logout']";



}

