package honoursproject.webCrawler.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import honoursproject.webCrawler.constants.WebCrawlerConstants;

public class WebCrawlerUtils {
	
	public static void waitSeconds(Integer seconds) throws InterruptedException {
		waitMilliseconds(seconds * 1000);
	}
	
	public static void waitMilliseconds(Integer milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
	
	public static WebDriver getWebDriver() {
		
		System.setProperty(WebCrawlerConstants.WEB_DRIVER_PROPERTY, WebCrawlerConstants.WEB_DRIVER_LOCATION);
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments(WebCrawlerConstants.OPTIONS_INCOGNITO);
		
		return new ChromeDriver(options);
		
	}
	
	public static WebDriverWait getWebDriverWait(WebDriver driver) {
		
		return new WebDriverWait(driver, WebCrawlerConstants.TIMEOUT_DURATION_SECONDS);
		
	}
	
	public static void slowScrollToHalfway(WebDriver driver) {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo({top: document.body.scrollHeight/4, behavior: 'smooth'})");
        
	}
	public static void slowScrollToPageBottom(WebDriver driver) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'})");
        
	}
	
	public static void slowScrollToPageTop(WebDriver driver) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo({top: 0, behavior: 'smooth'})");
        
	}
	
	
	public static void zoomOutOnDocumentReady(WebDriver driver, WebDriverWait waitDriver) throws InterruptedException {
		
		waitDriver.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='25%'");
		WebCrawlerUtils.waitSeconds(2);
		
	}
	
	public static void zoomIn(WebDriver driver) throws InterruptedException {
		
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='100%'");
		WebCrawlerUtils.waitSeconds(2);
		
	}

}
