package honoursproject.webCrawler.utils;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import honoursproject.webCrawler.constants.WebCrawlerConstants;

public class ProfileDataUtils {
	
	
	public static String csvStringEscape(String text) {
		return StringEscapeUtils.escapeCsv(text);
	}
	
	public static String concatenateElementText(List<WebElement> allElements) {
		
		if(allElements.isEmpty()) {
			System.out.println("No elements found");
			return "N/A";
		}
		
		String allText = "";
		
		for(WebElement element : allElements) {
			
			// check for see more - consider adding param for that check ? 
			
			String text = element.getText();
			
        	
        	if(StringUtils.isNotBlank(text)) {
        		allText += element.getText();
        		allText += WebCrawlerConstants.PROFILE_DELIMETER;
        	}
        	
		}
		
		return ProfileDataUtils.csvStringEscape(allText);
	}

}
