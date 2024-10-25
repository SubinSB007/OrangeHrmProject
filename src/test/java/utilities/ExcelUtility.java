package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	String path;
	
	public ExcelUtility(String path) {
		this.path=path;
	}
	
	public int getRowCount(String sheetname) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		int rowcount=sheet.getPhysicalNumberOfRows();
		workbook.close();
		fi.close();
		return rowcount;
	}
	
	public int getCellCount(String sheetname,int rownum) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		row=sheet.getRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
		}
	
	public String getCellData(String sheetname,int rownum,int colnum) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter  formatter =new DataFormatter();
		String data;
		try
		{
			//data=cell.toString();
			
			data=formatter.formatCellValue(cell); //returns the formatted value of a cell as a string regardless of the cell type 
		}
		catch(Exception e)
		{
			data="";
		}
		workbook.close();
		fi.close();
		return data;
}
	public void setCellData(String sheetname,int rownum,int colnum,String data) throws IOException {
	 File xlfile=new File(path);
	 if(!xlfile.exists()) {
		 workbook = new XSSFWorkbook();
		 fo = new FileOutputStream(path);

		 workbook.write(fo);
		 }

		 fi = new FileInputStream(path);
		 workbook = new XSSFWorkbook(fi);

		 if (workbook.getSheetIndex(sheetname) == -1) // If sheet not exists then create new Sheet
		 workbook.createSheet(sheetname);

		 sheet = workbook.getSheet(sheetname);

		 if (sheet.getRow(rownum) == null)
		 sheet.createRow(rownum);
		 row = sheet.getRow(rownum);
		 // If row not exists then create new Row

		 cell = row.createCell(colnum);
		 cell.setCellValue(data);

		 fo = new FileOutputStream(path);
		 workbook.write(fo);

		 workbook.close();
		 fi.close();

		 fo.close();
	 }
	
	public void fillGreenColor(String sheetname,int rownum,int colnum) throws IOException  {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style =workbook.createCellStyle();
		
		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
	
	public void fillRedColor(String sheetname,int rownum,int colnum) throws IOException  {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style =workbook.createCellStyle();
		
		style.setFillBackgroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
	
	
}
