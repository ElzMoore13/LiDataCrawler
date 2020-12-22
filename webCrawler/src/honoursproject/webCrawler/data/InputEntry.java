package honoursproject.webCrawler.data;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * Used to represent a line read from the input file. 
 * File is expected to be a .csv, and the first two columns should be a name and url.
 * 
 * @author emoore
 *
 */
public class InputEntry {
	
	// unifiedId, name, ghIdOriginal, ghIdNew, ghUrl, soId, soUrl, liUrl
	
	private String unifiedId;
	private String name;
	private String githubIdOriginal;
	private String githubIdNew;
	private String githubUrl;
	private String stackOverflowId;
	private String stackOverflowUrl;
	private String linkedInUrl;
	
	public InputEntry(String entryString) {
		
		List<String> entryCols = Arrays.asList(entryString.split(","));
		
		if(entryCols.size() != 8) {
			System.out.println("uhoh, something went wrong creating the entry object...");
			// do something here
			return;
		}
		
		this.unifiedId = entryCols.get(0);
		this.name = entryCols.get(1);
		this.githubIdOriginal = entryCols.get(2);
		this.githubIdNew = entryCols.get(3);
		this.githubUrl = entryCols.get(4);
		this.stackOverflowId = entryCols.get(5);
		this.stackOverflowUrl = entryCols.get(6);
		this.linkedInUrl = entryCols.get(7);
		
	}
	
	public String getUnifiedId() {
		return unifiedId;
	}

	public void setUnifiedId(String unifiedId) {
		this.unifiedId = unifiedId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGithubIdOriginal() {
		return githubIdOriginal;
	}

	public void setGithubIdOriginal(String githubIdOriginal) {
		this.githubIdOriginal = githubIdOriginal;
	}

	public String getGithubIdNew() {
		return githubIdNew;
	}

	public void setGithubIdNew(String githubIdNew) {
		this.githubIdNew = githubIdNew;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getStackOverflowId() {
		return stackOverflowId;
	}

	public void setStackOverflowId(String stackOverflowId) {
		this.stackOverflowId = stackOverflowId;
	}

	public String getStackOverflowUrl() {
		return stackOverflowUrl;
	}

	public void setStackOverflowUrl(String stackOverflowUrl) {
		this.stackOverflowUrl = stackOverflowUrl;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	
	@Override
	public String toString() {
		return "[name=" + name + ", profileUrl=" + linkedInUrl + "]";
	}
	

}
