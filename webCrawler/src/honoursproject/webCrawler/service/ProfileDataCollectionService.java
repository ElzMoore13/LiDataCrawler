package honoursproject.webCrawler.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import honoursproject.webCrawler.constants.WebCrawlerConstants;
import honoursproject.webCrawler.data.InputEntry;
import honoursproject.webCrawler.data.ProfileData;
import honoursproject.webCrawler.utils.ProfileDataUtils;
import honoursproject.webCrawler.utils.WebCrawlerUtils;

public class ProfileDataCollectionService {
	
	public static final ProfileDataCollectionService INSTANCE = new ProfileDataCollectionService();
	
	private ProfileDataCollectionService() {
		// hide constructor
	}
	
	
	/** Exposed Methods **/
	
	
	/**
	 * 
	 * Method to collect information from the profiled profile. Uses driver to navigate to profile
	 * url, then collects, text from about, experience, and skills section - used to create a 
	 * ProfileData object to return. Throws an InterruptedException if thread sleeps (used to 
	 * simulate a real user) are interrupted.
	 * 
	 * @param driver - WebDriver to use
	 * @param profile - InputEntry for the target profile
	 * @return ProfileData object with all of the information collected from the target profile
	 * @throws InterruptedException - if thread sleeps get interrupted 
	 */
	public ProfileData getProfileInformation(WebDriver driver, WebDriverWait wait, InputEntry profile) throws InterruptedException {

		String url = profile.getLinkedInUrl();
		String name = profile.getName();
		
		System.out.printf("\nStarting data collection of profile for: %s \n\n", name);
        
        loadProfilePage(driver, wait, url);
        
        String aboutText = collectAboutSection(driver, name);

        String allExperience = collectExperienceSection(driver, name);
        
        String allSkills = collectSkillsSection(driver, name);
        
        
        return new ProfileData(profile, aboutText, allExperience, allSkills);
		
	}
	
	private void loadProfilePage(WebDriver driver, WebDriverWait wait, String url) throws InterruptedException {
		
	    driver.get(url);
	    
        WebCrawlerUtils.zoomOutOnDocumentReady(driver, wait);
        
        WebCrawlerUtils.slowScrollToPageBottom(driver);
        WebCrawlerUtils.waitMilliseconds(3);
        WebCrawlerUtils.slowScrollToPageTop(driver);
        
        WebCrawlerUtils.zoomIn(driver);
	        
	}
	
	private String collectAboutSection(WebDriver driver, String name) throws InterruptedException {
		
        System.out.println("collecting about section...");
        
        String aboutSeeMoreSelector = "#line-clamp-show-more-button";
        String aboutTextSelector = ".pv-profile-section.pv-about-section .pv-about__summary-text";
        
        clickIfPresentBySelector(driver, aboutSeeMoreSelector, "about");
        List<WebElement> aboutSection = safeGetWebElementsListBySelector(driver, name, aboutTextSelector);
        
        return ProfileDataUtils.concatenateElementText(aboutSection);
        
	}
	
	private String collectExperienceSection(WebDriver driver, String name) throws InterruptedException {

        System.out.println("collecting experience section...");
        WebCrawlerUtils.slowScrollToHalfway(driver);
        
        String experienceSectionListSelector = "#experience-section ul.pv-profile-section__section-info";
        String experienceSeeMoreSelector = ".pv-experience-section__see-more button.pv-profile-section__see-more-inline";
        
        clickIfPresentBySelector(driver, experienceSeeMoreSelector, "experience");
        WebElement experienceElement = safeGetWebElementBySelector(driver, name, experienceSectionListSelector);
        
        List<WebElement> experienceTexts = new ArrayList<>();
        experienceTexts.add(experienceElement);
        
        if(experienceElement != null ) {
        	
        	// check for show more role
        	
        	experienceTexts = experienceElement.findElements(By.xpath(".//li")); // or try By.tag("li");
        	
        	// within each list item, check for show more role 
        	int initCount = experienceTexts.size();
        	experienceTexts = experienceSeeMoreHelper(driver, experienceTexts, experienceElement);
        	
        	int count = 0;
        	int size = experienceTexts.size();
        	while(size > initCount && count < 5) {
        		
        		initCount = experienceTexts.size();
        		experienceTexts = experienceSeeMoreHelper(driver, experienceTexts, experienceElement);
        		
        		size = experienceTexts.size();
        		count++; // safety to prevent infinite loop
        	}
        	
        	String seeMoreDescription = "button.inline-show-more-text__button.link";
        	clickIfPresentBySelector(driver, seeMoreDescription, "experienceDescription");
        	
        	experienceTexts = experienceElement.findElements(By.xpath(".//li")); // or try By.tag("li");
	        
        }
        
        return ProfileDataUtils.concatenateElementText(experienceTexts);
        
	}
	
	private String collectSkillsSection(WebDriver driver, String name) throws InterruptedException {
		
        System.out.println("collecting skills section...");
        WebCrawlerUtils.slowScrollToPageTop(driver);
        
        String skillsSeeMoreSelector = "button.pv-skills-section__additional-skills"; 
        
        // check for show more
        clickIfPresentBySelector(driver, skillsSeeMoreSelector, "skills");
        
        String skillSelector = "span.pv-skill-category-entity__name-text";
        List<WebElement> skillsTexts = safeGetWebElementsListBySelector(driver, name, skillSelector);
        
        return ProfileDataUtils.concatenateElementText(skillsTexts);
	}
	
	private List<WebElement> experienceSeeMoreHelper(WebDriver driver, List<WebElement> experienceTexts, WebElement experienceElement) throws InterruptedException {
		
		for(WebElement section : experienceTexts) {
    		
    		// check for show more roles option
    		
    		String showMoreRolesButtonXPath = ".//button";
    		clickIfPresent(driver, section, showMoreRolesButtonXPath, "experience");
    		
    	}
		
		WebCrawlerUtils.waitSeconds(3);
		
		return experienceElement.findElements(By.xpath(".//li")); // or try By.tag("li");
	}

	
	private void clickIfPresentBySelector(WebDriver driver, String selector, String section) throws InterruptedException {
		
		List<WebElement> clickables = new ArrayList<>();
		
		try {
        	clickables = driver.findElements(By.cssSelector(selector));
        } catch(Exception e) {
        	System.out.println("Error looking for \"see more\" button detected in " + section + " section");
        }
		
		if(clickables.isEmpty()) {
			System.out.println("No \"see more\" button detected in " + section + " section");
			return;
		}
		
		WebCrawlerUtils.waitSeconds(1);
		
		for(WebElement clickable : clickables) {
			safeClick(driver, clickable);
		}
		
		WebCrawlerUtils.waitSeconds(1);
		
	}
	
	private void clickIfPresent(WebDriver driver, WebElement element, String xPath, String section) throws InterruptedException {
		
		List<WebElement> clickables = new ArrayList<>();
		
		try {
        	clickables = element.findElements(By.xpath(xPath));
        } catch(Exception e) {
        	System.out.println("no \"see more\" button detected in " + section + " section");
        }
		
		for(WebElement clickable : clickables) {
			safeClick(driver, clickable);
		}
		
	}
	
	private void safeClick(WebDriver driver, WebElement clickable) throws InterruptedException {
		
		if(!clickable.isDisplayed()) {
			System.out.printf("'Clicking' element %s  is not displayed\n", clickable.getTagName());
			return;
		}
		
		// check that it says more not less or fewer
		String buttonText = clickable.getText();
		if(!StringUtils.isBlank(buttonText) && 
				( buttonText.contains("less") || buttonText.contains("few"))) {
			System.out.printf("Button text looks like it's already expanded: '%s'\n", buttonText);
			return;
		}
		
		try {
			
			Actions action = new Actions(driver);
			action.moveToElement(clickable)
				  .click()
				  .perform();
			
		} catch (Exception e) {
			System.out.printf("Error 'clicking' element %s \n", clickable.getTagName());
			
			e.printStackTrace();
		}
		
		WebCrawlerUtils.waitSeconds(2);
	}
		
	private WebElement safeGetWebElementBySelector(WebDriver driver, String profileName, String selector){
		
		WebElement element = null;
		
		try {
			element = driver.findElement(By.cssSelector(selector));
		} catch (NoSuchElementException e) {
			System.out.printf("No such element '%s' exception for profile %s \n\n", selector, profileName);
			e.printStackTrace();
		}
		
		return element;
	}
	
	private List<WebElement> safeGetWebElementsListBySelector(WebDriver driver, String profileName, String selector){
		
		List<WebElement> elements = new ArrayList<>();
		
		elements = driver.findElements(By.cssSelector(selector));
				

		if(elements.isEmpty()) {
			System.out.printf("No such element '%s' exception for profile %s \n\n", selector, profileName);
		}
		
		return elements;
		
	}
	

}
