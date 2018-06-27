package core;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Firefox {
static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {

		// cleaning up the logs of the output of the console
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		
		String driverPath = "";
		
		String[] url_array = { 
		         "http://alex.academy/exe/payment/index.html",
		         "http://alex.academy/exe/payment/index2.html",
		         "http://alex.academy/exe/payment/index3.html",
		         "http://alex.academy/exe/payment/index4.html",
		         "http://alex.academy/exe/payment/indexE.html"
		};
		
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./resources/webdrivers/mac/geckodriver.sh";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/geckodriver.exe";
		else
			throw new IllegalArgumentException("Unknown OS");

		System.setProperty("webdriver.gecko.driver", driverPath);

		FirefoxProfile testprofile = new FirefoxProfile();
		testprofile.setPreference("dom.webnotifications.enabled", false);
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(FirefoxDriver.PROFILE, testprofile);
		FirefoxOptions opt = new FirefoxOptions();
		opt.merge(dc);

		driver = new FirefoxDriver(opt);
		driver.manage().window().maximize();
		
		for (String url: url_array) {

		driver.get(url);
		
		final long start = System.currentTimeMillis();
		// "$1,654.55";
		String string_monthly_payment = driver.findElement(By.id("id_monthly_payment")).getText();
//		System.out.println(string_monthly_payment);
		String regex = "^"
		+ "(?:\\$)?"
		+ "(?:\\s*)?"
		+ "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)"
		+ "$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string_monthly_payment);
		m.find();
		// 1,654.55
		double monthly_payment = Double.parseDouble(m.group(1).replaceAll(",", ""));
		// 1654.22 * 12 = 19854.60
		double annual_payment = new BigDecimal(monthly_payment * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();
		// 19854.6
		DecimalFormat df = new DecimalFormat("0.00");
		String f_annual_payment = df.format(annual_payment);
		driver.findElement(By.id("id_annual_payment")).sendKeys(String.valueOf(f_annual_payment));
		driver.findElement(By.id("id_validate_button")).click();
		String actual_result = driver.findElement(By.id("id_result")).getText();
		final long finish = System.currentTimeMillis();
		System.out.println("String: \t" + m.group(0)); // capturing whole thing
		System.out.println("Annual Payment: " + f_annual_payment);
		System.out.println("Result: \t" + actual_result);
		System.out.println("Response time: \t" + (finish - start) + " milliseconds");
		
		}
		
		driver.quit();
		
	}

}

