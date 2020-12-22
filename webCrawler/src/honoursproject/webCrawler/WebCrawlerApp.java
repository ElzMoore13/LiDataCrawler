package honoursproject.webCrawler;

import java.util.List;

import org.openqa.selenium.WebDriver;

import honoursproject.webCrawler.constants.WebCrawlerConstants;
import honoursproject.webCrawler.data.InputEntry;
import honoursproject.webCrawler.data.ProfileData;
import honoursproject.webCrawler.service.InputReaderService;
import honoursproject.webCrawler.service.OutputWriterService;
import honoursproject.webCrawler.utils.WebCrawlerUtils;

public class WebCrawlerApp {

	public static void main(String[] args) {
		
		String testInputFile = "./InputFiles/input_test.csv";
		Boolean isTest = false;
		
		String filename =  WebCrawlerConstants.DEFAULT_INPUT_FILE_PATH;
		
		if (args.length >= 1) {
			// update filename if a new one was provided
			filename = args[0];
		}
		
		if(isTest) {
			filename = testInputFile;
		}
		
		// set up webDriver 
		WebDriver driver = WebCrawlerUtils.getWebDriver();
		WebCrawler webCrawler = new WebCrawler(driver);
		
		try {
			
			// read file to get list of profiles to read 
			List<InputEntry> allProfiles = InputReaderService.INSTANCE.readInputFile(filename);
			
			if(allProfiles.isEmpty()) {
				System.out.println("\n No input entries created");
				return;
			}
			// perform data collection 
			List<ProfileData> collectedData = webCrawler.getLinkedInProfileData(allProfiles);
			
			// write output
			OutputWriterService.INSTANCE.writeOutputFile(collectedData);
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	
	}

}
