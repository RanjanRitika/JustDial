package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	private static FileInputStream fileInput;
	private static FileOutputStream fileOutput;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;

	// write a row into excel given row index
	public static void writeRow(String[] data, int row_num, String fileName, String sheetName) {
		try {
			fileInput = new FileInputStream(".//excelOutput//" + fileName);
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found.");
			fileInput = null;
		}
		try {
			if (fileInput == null) {
				// if file is not found new file is created.
				workbook = new XSSFWorkbook();
			}
			else {
				workbook = new XSSFWorkbook(fileInput);
			}
		} catch (IOException e) {
			System.out.println(fileName + " not found.");
		}
		try {
			sheet = workbook.createSheet(sheetName);
		} catch (Exception e1) {
			sheet = workbook.getSheet(sheetName);
		}
		XSSFRow row = sheet.createRow(row_num);
		for (int i = 0; i < data.length; i++) {
			row.createCell(i).setCellValue(data[i]);
		}
		try {
			fileInput.close();
			fileOutput = new FileOutputStream(".\\excelOutput\\" + fileName);
			workbook.write(fileOutput);
			fileOutput.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// writing a column in excel given col number.
	public static void writeColumn(String[] data, int col, String fileName, String sheetName) {
		try {
			fileInput = new FileInputStream(".//excelOutput//" + fileName);
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found.");
		}
		try {
			workbook = new XSSFWorkbook(fileInput);
		} catch (IOException e) {
			System.out.println(fileName + " not found.");
		}
		try {
			sheet = workbook.createSheet(sheetName);
		} catch (Exception e1) {
			sheet = workbook.getSheet(sheetName);
		}
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < data.length; i++) {
			row = sheet.createRow(i + 1);
			row.createCell(col).setCellValue(data[i]);
		}
		try {
			fileInput.close();
			fileOutput = new FileOutputStream(".\\excelOutput\\" + fileName);
			workbook.write(fileOutput);
			fileOutput.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createExcelFile(String fileName) {
		try {
			fileOutput = new FileOutputStream(".\\excelOutput\\" + fileName);
			workbook.write(fileOutput);
			fileOutput.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}