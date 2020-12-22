package honoursproject.webCrawler.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import honoursproject.webCrawler.data.InputEntry;
import honoursproject.webCrawler.exceptions.WebCrawlerUsageException;

public class InputReaderService {
	
	public static final InputReaderService INSTANCE = new InputReaderService();
	
	private static final String CSV_EXTENSION = "csv";
	
	private InputReaderService() {
		// hide constructor
	}
	
	/** Exposed Methods **/
	
	public List<InputEntry> readInputFile(String filename) throws Exception {
		
		Objects.requireNonNull(filename);
		
		File file = getFile(filename);
		
		return scanFile(file);
	}
	
	
	/** Utilities **/
	
	List<InputEntry> scanFile(File file) throws FileNotFoundException{
		
		List<InputEntry> allEntries = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(file);) {

			while (scanner.hasNextLine()) {
				allEntries.add(new InputEntry(scanner.nextLine()));
			}


		} catch (FileNotFoundException e) {
			throw e;
		}
		
		return allEntries;
	}
	
	File getFile(String filename) throws Exception {
		
		validateFilename(filename);
		
		File file = new File(filename);
		
		if(!file.exists()) {
			throw new FileNotFoundException("Input file cannot be found");
		}
		
		return file;
		
	}
	
	void validateFilename(String filename) throws Exception {
		
		List<String> fileParts = Arrays.asList(filename.split("\\."));
		
		Integer FilePartsSize = fileParts.size();
		
		if(FilePartsSize < 2) {
			throw new WebCrawlerUsageException("Input file is missing an extension - must be a csv file");
		} 
		
		// in case of periods being use in paths, get the last item in file parts
		String fileExtension = fileParts.get(FilePartsSize - 1);
		
		if(!fileExtension.equalsIgnoreCase(CSV_EXTENSION)) {
			throw new WebCrawlerUsageException("Input file extension is invalid - must be a csv file");
		}
		
	}

}
