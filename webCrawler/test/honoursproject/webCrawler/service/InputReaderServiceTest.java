package honoursproject.webCrawler.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import honoursproject.webCrawler.data.InputEntry;
import honoursproject.webCrawler.exceptions.WebCrawlerUsageException;

class InputReaderServiceTest {
	
	private static final String REAL_FILE_PATH = "./InputFiles/input_unit_test_test_file.csv";
	private static final String REAL_FILE_PATH_NO_EXTENSION = "./InputFiles/input_unit_test_test_file";
	
	InputReaderService readerService;
	static File realFile;
	
	
	/** SetUp/TearDown **/
	
	@BeforeAll
	public static void addTestFile() {
		
		realFile = new File(REAL_FILE_PATH);
		
		try {
			realFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeEach
	public void setUp() {
		
		readerService = InputReaderService.INSTANCE;
		
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	@AfterAll
	public static void cleanUpTestFile() {
		
		realFile.delete();
		
	}

	
	/** Tests **/
	
	@Test
	void givenNullFilename_whenReadFile_thenNullPointerException() throws Exception {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			readerService.readInputFile(null);
		});
		
	}
	
	@Test
	void givenNoExtension_andFileDoesNotExist_whenReadFile_thenWebCrawlerUsageException() throws Exception {
		
		
		Assertions.assertThrows(WebCrawlerUsageException.class, () -> {
			readerService.readInputFile("notARealFile");
		});
		
	}
	
	@Test
	void givenValidExtension_andFileDoesNotExist_whenReadFile_thenFileNotFoundException() throws Exception {

		Assertions.assertThrows(FileNotFoundException.class, () -> {
			readerService.readInputFile("./InputFiles/notARealFile.csv");
		});
		
	}
	
	@Test
	void givenInvalidExtension_andFileDoesNotExist_whenReadFile_thenWebCrawlerUsageException() throws Exception {

		Assertions.assertThrows(WebCrawlerUsageException.class, () -> {
			readerService.readInputFile("./InputFiles/notARealFile.txt");
		});
		
	}
	
	@Test
	void givenInvalidFileExtension_andFileDoesExist_whenReadFile_thenWebCrawlerUsageException() throws Exception {
		
		Assertions.assertThrows(WebCrawlerUsageException.class, () -> {
			readerService.readInputFile("./InputFiles/badFileExt.txt");
		});
		
	}
	
	@Test
	void givenNoExtension_andCSVFileDoesExist_whenReadFile_thenWebCrawlerUsageException() throws Exception {
		
		Assertions.assertThrows(WebCrawlerUsageException.class, () -> {
			readerService.readInputFile(REAL_FILE_PATH_NO_EXTENSION);
		});
		
	}
	
	@Test
	void givenValidExtension_andCSVFileDoesExist_whenReadFile_thenPass() throws Exception {
		
		Assertions.assertDoesNotThrow(() -> {
			readerService.readInputFile(REAL_FILE_PATH);
		});
		
	}
	
	@Test
	void givenValidFile_whenReadFile_thenReturnList() throws Exception {
		
		InputReaderService spiedService = Mockito.spy(readerService);
		
		File file = getMockedFile();
		List<InputEntry> entryList = new ArrayList<>();
		
		Mockito.when(spiedService.getFile(REAL_FILE_PATH)).thenReturn(file);
		Mockito.doReturn(entryList).when(spiedService).scanFile(file); 
		
		List<InputEntry> actualReturn = spiedService.readInputFile(REAL_FILE_PATH);
		
		Assertions.assertEquals(actualReturn, entryList);
		
	}
	
	
	/** Utilities **/
	
	private File getMockedFile() {
		return Mockito.mock(File.class);
	}

}
