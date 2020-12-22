package honoursproject.webCrawler.data;

public class ProfileData {
	
	private String unifiedId;
	private String name;
	private String githubIdOriginal;
	private String githubIdNew;
	private String githubUrl;
	private String stackOverflowId;
	private String stackOverflowUrl;
	private String linkedInUrl;
	private String about;
	private String experience;
	private String skills;
	
	public ProfileData() {
		
	}
	
	public ProfileData(InputEntry inputEntry, String about, String experience, String skills) {
		
		this.unifiedId = inputEntry.getUnifiedId();
		this.name = inputEntry.getName();
		this.githubIdOriginal = inputEntry.getGithubIdOriginal();
		this.githubIdNew = inputEntry.getGithubIdNew();
		this.githubUrl = inputEntry.getGithubUrl();
		this.stackOverflowId = inputEntry.getStackOverflowId();
		this.stackOverflowUrl = inputEntry.getStackOverflowUrl();
		this.linkedInUrl = inputEntry.getLinkedInUrl();
		this.about = about;
		this.experience = experience;
		this.skills = skills;
		
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	
	public String toCsvFormat() {
		// unifiedId, name, ghIdOriginal, ghIdNew, ghUrl, soId, soUrl, liUrl
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", unifiedId, name, githubIdOriginal, githubIdNew, githubUrl, stackOverflowId, stackOverflowUrl, linkedInUrl, about, experience, skills);
	}

	
	@Override
	public String toString() {
		return "ProfileData [unifiedId=" + unifiedId + ", name=" + name + ", githubIdOriginal=" + githubIdOriginal
				+ ", githubIdNew=" + githubIdNew + ", githubUrl=" + githubUrl + ", stackOverflowId=" + stackOverflowId
				+ ", stackOverflowUrl=" + stackOverflowUrl + ", linkedInUrl=" + linkedInUrl + ", about=" + about
				+ ", experience=" + experience + ", skills=" + skills + "]";
	}
	

}
