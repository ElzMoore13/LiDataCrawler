package honoursproject.webCrawler;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import honoursproject.webCrawler.constants.WebCrawlerConstants;
import honoursproject.webCrawler.data.InputEntry;
import honoursproject.webCrawler.data.ProfileData;
import honoursproject.webCrawler.exceptions.WebCrawlerUsageException;
import honoursproject.webCrawler.service.ProfileDataCollectionService;
import honoursproject.webCrawler.utils.WebCrawlerUtils;

public class WebCrawler {
	
	private ProfileDataCollectionService profileService = ProfileDataCollectionService.INSTANCE;
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public WebCrawler(WebDriver driver) {
		this.driver = driver;
		this.wait = WebCrawlerUtils.getWebDriverWait(driver);
	}
	
	public List<ProfileData> getLinkedInProfileData(List<InputEntry> profilesToCheck) throws InterruptedException, WebCrawlerUsageException {
		
		List<ProfileData> collectedProfileData = new ArrayList<>();
		
		try {
			logInToLinkedIn();
		} catch (Exception e) {
			System.out.println("WebCrawler experienced errors logging in to LinkedIn");
			throw e;
		}
		
		WebCrawlerUtils.waitSeconds(15);
		
		for(InputEntry profile : profilesToCheck) {
			
			try {
				collectedProfileData.add(profileService.getProfileInformation(driver, wait, profile));
			} catch (NoSuchElementException e) {
				System.out.printf("No such element exception for profile %s \n", profile.getName());
				e.printStackTrace();
			} catch (Exception e) {
				System.out.printf("Error scraping data for profile %s \n", profile.getName());
				e.printStackTrace();
			}
			
			// add sufficient wait times between profile navigation to simulate real user
//			WebCrawlerUtils.waitSeconds(35);  // use for testing 
			WebCrawlerUtils.waitSeconds(45);  // use for collection larger amount of data 
		}		
		
		return collectedProfileData;
		
	}
	
	
	/** Utilities **/
	
	private void logInToLinkedIn() throws InterruptedException {
		
		driver.get(WebCrawlerConstants.LINKEDIN_WEB_ADDRESS);
		
		WebCrawlerUtils.waitSeconds(5);
		
		// LinkedIn sometimes navigates to a sign up page instead of the normal login form
		if(navigateFromSignUpIfPresent()) {
			loginFromSignUpForm();
		} else {
			loginFromMainForm();
		}
		
        WebCrawlerUtils.waitSeconds(7);
		
	}
	
	private boolean isSignUpPagePresent() {
		
		String signInLinkText = "Sign In";
		
		try {
			driver.findElement(By.linkText(signInLinkText));
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return true;
	}
	
	private void loginFromMainForm() throws InterruptedException {
		
		String signInButtonClass = "sign-in-form__submit-button";
        String emailFieldId = "session_key";
        String passwordFieldId = "session_password";
		
        loginFromForm(emailFieldId, passwordFieldId, signInButtonClass);
        
	}
	
	private void loginFromSignUpForm() throws InterruptedException {
		
		String signInButtonClass = "submit-button";
        String emailFieldId = "login-email";
        String passwordFieldId = "login-password";
		
        loginFromForm(emailFieldId, passwordFieldId, signInButtonClass);
        
	}
	
	private void loginFromForm(String emailFieldId, String passwordFieldId, String signInButtonClass) throws InterruptedException {
		
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(emailFieldId)));// By.className(signInButtonSelector)));
        emailField.sendKeys(WebCrawlerConstants.SIGN_IN_EMAIL + Keys.TAB);
        
        WebCrawlerUtils.waitSeconds(5);
        
        WebElement passwordField = driver.findElement(By.id(passwordFieldId));
        passwordField.sendKeys(WebCrawlerConstants.SIGN_IN_PASSWORD);
        
        WebCrawlerUtils.waitSeconds(5);
        
        driver.findElement(By.className(signInButtonClass)).click();
        
	}
	
	private boolean navigateFromSignUpIfPresent() throws InterruptedException {
		
		String signInXPath = "//html/body/main/div/div/form[2]/section/p/a";
		WebElement signInButton;
		
		try {
			signInButton = driver.findElement(By.xpath(signInXPath));
		} catch (Exception e) {
			System.out.println("\nDidn't find Sign In link, must be on main form....\n");
			return false;
		}
		
		signInButton.click();
		
		return true;
		
	}

}
