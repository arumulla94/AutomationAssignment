package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Reader {
	
	public FileOutputStream fileOut = null;
	public String path;
	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	
	
	public Excel_Reader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method will return 2D object Data for each record in excel sheet.
	 * 
	 * @param sheetName
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public String[][] getDataFromSheet(String sheetName, String ExcelName) {
		String dataSets[][] = null;
			try {
				// get sheet from excel workbook
				XSSFSheet sheet = workbook.getSheet(sheetName);
				// count number of active tows
				int totalRow = sheet.getLastRowNum() + 1;
				// count number of active columns in row
				int totalColumn = sheet.getRow(0).getLastCellNum();
				// Create array of rows and column
				dataSets = new String[totalRow - 1][totalColumn];
				// Run for loop and store data in 2D array
				// This for loop will run on rows
				for (int i = 1; i < totalRow; i++) {
					XSSFRow rows = sheet.getRow(i);
					// This for loop will run on columns of that row
					for (int j = 0; j < totalColumn; j++) {
						// get Cell method will get cell
						XSSFCell cell = rows.getCell(j);
						dataSets[i - 1][j] = cell.getStringCellValue();
					}

				}
				return dataSets;
			} catch (Exception e) {
				System.out.println("Exception in reading Xlxs file" + e.getMessage());
				e.printStackTrace();
			}
			return dataSets;
		}


	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum)
	{
		try 
		{
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			XSSFRow row = sheet.getRow(0);
			row= sheet.getRow(rowNum-1);
			
			XSSFCell cell = row.getCell(colNum);
				return cell.getStringCellValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public int getRowCount(String sheetName)
	{
		try {
			 int index = workbook.getSheetIndex(sheetName);
			 if(index == -1)
			 {
				return 0;
			 }else {
				 sheet = workbook.getSheetAt(index);
				 int number = sheet.getLastRowNum() + 1;
				 return number;
			 }
			 
		} catch(Exception e){
			 e.printStackTrace();
		}
		return 0;
	}

	
	public int getColumnCount(String sheetName)
	{
		try {
			 int index = workbook.getSheetIndex(sheetName);
			 if(index == -1)
			 {
				return 0;
			 }else {
				 sheet = workbook.getSheetAt(index);
				 row = sheet.getRow(0);
				 return row.getLastCellNum();
			 }
			 
		} catch(Exception e){
			 e.printStackTrace();
		}
		return 0;
	}
}



