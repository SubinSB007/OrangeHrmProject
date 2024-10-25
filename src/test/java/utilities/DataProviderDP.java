package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviderDP {
	//DataProvider 1

	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
	    String path = System.getProperty("user.dir")+"/TestData/orangeHrmLoginData.xlsx"; //taking Excel file from testData

	    ExcelUtility xlutil = new ExcelUtility(path); //creating an object for XLUtility and passing the path of the excel file

	    int totalrows = xlutil.getRowCount("Sheet1"); //it will return value starting from index 1
	    int totalcols = xlutil.getCellCount("Sheet1", 1); //it will return value starts from 0ne

	    String logindata[][] = new String[totalrows-1][totalcols]; //created for two dimension array which can store, doesn't need the header row of excel file

	    for (int i = 1; i <= totalrows-1; i++) { //read the data from xl storing in two dimensional array
	        for (int j = 0; j < totalcols; j++) {
	            logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j); //1,0
	            //array   							excel file
	        }
	    }

	    return logindata; //returning two dimension array
	}
	//DataProvider 2
	@DataProvider(name="dp")
	String[][] negativeLoginData(){
		String logindata[][]= {{"Admin","123456"},{"ad","admin123"}};
		return logindata;
	}
	
	//DataProvider 3
	
	//DataProvider 4

}
