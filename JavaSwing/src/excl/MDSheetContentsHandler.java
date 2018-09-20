package excl;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

public class MDSheetContentsHandler implements SheetContentsHandler {
	List<HashMap<String, String>> list;// = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hMap;// = new HashMap<String, String>();
	
	
	public MDSheetContentsHandler(List<HashMap<String, String>> list) {
		this.list = list;
	}

	// Row 의 시작 부분에서 발생하는 이벤트를 처리하는 method
	@Override
	public void startRow(int rowNum) {
		// TODO Auto-generated method stub
		hMap = new HashMap<String, String>();
		
	}
	
	// Row 의 끝에서 발생하는 이벤트를 처리하는 method
	@Override
	public void endRow() {
		list.add(hMap);
	}
	
	@Override
	public void cell(String columnNum, String value) {
		String str;
		if(value == null) {
			str = "";
		}
		else if("ERROR:#NULL!".equals(value)) {
			str = "";
		}
		else {
			str = value;
		}
		
		hMap.put(columnNum, str);
	} // end public void cell(String columnNum, String value)
	
	private boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void headerFooter(String paramString1, boolean paramBoolean, String paramString2) {
		// sheet 의 첫 row 와 마지막 row 를 처리하는 method
	}


}