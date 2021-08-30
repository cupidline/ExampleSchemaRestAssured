package utils;

import java.io.File;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DataUtility {

	public static final String dataFilePath = System.getProperty("user.dir") + File.separator + "resources"
			+ File.separator + "TestData.xlsx";

	public static String getDataFromExcel(String sheetName, String ID) {
		String result = "unable to read data";
		try {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(dataFilePath);
			System.out.println("Selected data file is :" + dataFilePath);
			String strQuery = "Select * from " + sheetName + " where ID='" + ID + "'";
			Recordset recordset = connection.executeQuery(strQuery);

			while (recordset.next()) {
				result = recordset.getField("Value");
			}

			recordset.close();
			connection.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}
		return result;

	}

}
