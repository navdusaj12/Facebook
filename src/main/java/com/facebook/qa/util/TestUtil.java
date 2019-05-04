package com.facebook.qa.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.facebook.qa.base.testbase;

public class TestUtil extends testbase {

	public static long PAGE_LOAD_TIMEOUT = 40;
	public static long IMPLICIT_WAIT = 40;
	public static String TESTDATA_SHEET_PATH = prop.getProperty("TestDataFile")+prop.getProperty("SheetName");

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;
	//Function to read data from excel sheet.
	public static Object[][] getTestData(String sheetName) {
		
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataFormatter formatter = new DataFormatter();
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {		
				Cell cell = sheet.getRow(i+1).getCell(k);
				data[i][k] = formatter.formatCellValue(cell); 
			}
		}
		return data;
	}		
	//Function to validate is Alert is present.
		public static boolean isAlertPresent() {
		    try{
		       WebDriverWait wait = new WebDriverWait(driver, 5);
		       wait.until(ExpectedConditions.alertIsPresent());
		       return true;
		    }
		    catch (NoAlertPresentException noAlert) {
		      return false;
		    }
		    catch (TimeoutException timeOutEx){
		      return false;
		    }
		}
}
