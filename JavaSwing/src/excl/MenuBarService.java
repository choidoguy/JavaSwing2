package excl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MenuBarService {
	public static List<HashMap<String, String>> fileLoader(File file) {
		ExcelManager manager = ExcelManager.getInstance();
		List<HashMap<String, String>> list = null;
		try {
			list = manager.getListExcel(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<String> getSheetsNm(File file) {
		ExcelManager manager = ExcelManager.getInstance();
		List<String> list = null;
		
		try {
			list = manager.getSheetsNm(file);
		} catch (Exception e) {
			String message = e.getMessage();
			System.out.println(message);
			//e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<HashMap<String, String>> getSheetData(File file, int sheetNum) {
		ExcelManager manager = ExcelManager.getInstance();
		List<HashMap<String, String>> list = null;
		
		try {
			long start = System.currentTimeMillis();
			
			list = manager.getSheetData(file, sheetNum);
			
			long end = System.currentTimeMillis();
			
			System.out.println("    getSheetData : " + (end-start)/1000 + " 초 걸림");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}