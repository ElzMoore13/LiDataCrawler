# LiDataCrawler
#### Eliza Moore - COMP 4905
The goal of this project was to contribute to another project on predicting the expertise topics of software developers based on their activity and contributions to GitHub and Stack Overflow. The topics will then be compared to self-declared topics of expertise that, in this case, will come from the LinkedIn profiles of the software developers used as the project subjects.

This project (LiDataCralwer) aimed to develop a data crawler to collect the self-reported data by extracting expertise topics from the developer profiles in LinkedIn from a list of provided users.

## Usage
To use the LiDataCrawler:
1. Include LinkedIn login credentials in `WebCrawlerConstants` class
* `SIGN_IN_EMAIL`
* `SIGN_IN_PASSWORD`
2. Add input file  in `webCrawler` > `InputFiles` folder
* Must be named `input.csv`
* Expected entry order:
  * `unifiedId,Name,GH_UserId,GH_ID_fetched,GitHub URL,SO_UserId,StackOverflow URL,LinkedIn`
3. **Run the `WebCrawlerApp` class**
4. **Find output data in `webCrawler` > `OutputFiles` folder named `webCrawler_data_output.csv`**
* note that any file existing at this location with the same name will be overwritten
* Data in the order of:
   * `UnifiedId,Name,GitHubId_Original,GitHubId_new,GitHubUrl,StackOverflowId,StackOverflowUrl,LinkedInUrl,About,Experience,Skills`


## Packages Included
1. webCrawler
	* `WebCrawler.java`
	* `WebCrawlerApp.java`
2. webCrawler.constants
	* `WebCrawlerConstants.java`
3. webCrawler.data
	* `InputEntry.java`
	* `ProfileData.java`
4. webCrawler.exceptions
	* `WebCrawlerUsageException.java`
5. webCrawler.service
	* `InputReaderService.java`
	* `OutputWriterService.java`
	* `ProfileDataCollectionService.java`
	* `WebDriverInitializationService.java`
6. webCrawler.utils
	* `ProfileDataUtils.java`
	* `WebCrawlerUtils.java`


## High-level Sequence Diagrams
High-Level overview of the program
![High-Level Sequence Diagram](https://github.com/ElzMoore13/COMP4905_DataCrawler/tree/main/Assets/sequenceDiagram_highLevel.png)

Closer look at profile data collection
![Profile Data Collection Sequence Diagram](https://github.com/ElzMoore13/COMP4905_DataCrawler/tree/main/Assets/sequenceDiagram_profileData)
