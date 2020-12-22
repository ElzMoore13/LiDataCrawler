package honoursproject.webCrawler.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import honoursproject.webCrawler.constants.WebCrawlerConstants;
import honoursproject.webCrawler.data.ProfileData;

public class OutputWriterService {
	
	public static final OutputWriterService INSTANCE = new OutputWriterService();
	
	private OutputWriterService() {
		// hide constructor
	}
	
	/** Exposed Methods **/
	
	public void writeOutputFile(List<ProfileData> profileDataList) {
		
		try {
			
			// open file for writing - deletes current contents if the file already exists
			PrintWriter writer = getPrintWriter();
			
			// write header
			writer.println("UnifiedId,Name,GitHubId_Original,GitHubId_new,GitHubUrl,StackOverflowId,StackOverflowUrl,LinkedInUrl,About,Experience,Skills");
			
			for(ProfileData profileData : profileDataList){
				  
				  writer.println(profileData.toCsvFormat());
			  
			}
			
			// close the file
			writer.close();
	      
	    } catch (FileNotFoundException e) {
	        System.out.printf("Error: Cannot open file %s for writing.\n", WebCrawlerConstants.OUTPUT_FILE_PATH);
	    } catch (IOException e) {
	        System.out.printf("Error: Cannot write to file %s.\n", WebCrawlerConstants.OUTPUT_FILE_PATH);
	    } 
		
	}
	
	/** Utilities **/
	
	private PrintWriter getPrintWriter() throws IOException {
		
		return new PrintWriter(new FileWriter(WebCrawlerConstants.OUTPUT_FILE_PATH));
	}
	

}
