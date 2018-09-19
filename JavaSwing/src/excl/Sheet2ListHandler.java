package excl;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

public class Sheet2ListHandler implements SheetContentsHandler {
	//collection 객체
	private List<String[]> rows;
	// collenction 에 추가 될 객체 startRow 에서 초기화 함
	private String[] row;
	// collection 내 객체를 String[] 로 잡았기 때문에 배열의 길이 생성 시 받도록 설계
	private int columnCnt;
	// cell 이벤트 처리 시 해당 cell 의 데이터가 배열 어디에 저장되어야 할지 가리키는 pointer
	private int currColNum = 0;
	
	private String preColumnNum ="";
	private String crrColumnNum ="";
	
	
	List<HashMap<String, String>> list;// = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hMap;// = new HashMap<String, String>();
	
	
	// 외부 collection 과 배열 size 를 받기 위해 추가한 부분
	public Sheet2ListHandler(List<String[]> rows, int columnsCnt) {
		this.rows = rows;
		this.columnCnt = columnsCnt;
	}
	
	public Sheet2ListHandler(List<HashMap<String, String>> list) {
		this.list = list;
	}

	// Row 의 시작 부분에서 발생하는 이벤트를 처리하는 method
	@Override
	public void startRow(int rowNum) {
//		// TODO Auto-generated method stub
//		this.row = new String[columnCnt];
		currColNum = 0;
		hMap = new HashMap<String, String>();
	}
	
	// Row 의 끝에서 발생하는 이벤트를 처리하는 method
	@Override
	public void endRow() {
//		// cell 이벤트에서 담아놓은 row String[] 를 collection 에 추가
//		// 데이터가 하나도 없는 row 는 collection 에 추가하지 않도록 조건 추가
//		boolean addFlag = false;
//		for(String data : row) {
//			if(!"".equals(data)) addFlag = true;
//		}
//		
//		if(addFlag) rows.add(row);
		
		// 다음 row 체크를 위해 초기화
		crrColumnNum = "";
		preColumnNum = "";
		
		list.add(hMap);
	}
	
	@Override
	public void cell(String columnNum, String value) {
		hMap.put(columnNum, value == null ? "" : value);
//		if("BA1".equals(columnNum)){
//			System.out.println(columnNum);
//		}
//		
//		if("BB1".equals(columnNum)){
//			System.out.println(columnNum);
//		}
//		
//		// col 체크 공란인경우 cell 을 타지 않고 있음
//		crrColumnNum = columnNum;
//		if("".equals(preColumnNum)) {
//			String crrCol = getColNm(crrColumnNum);
//			if("B".equals(crrCol)){
//				hMap.put("attr" + currColNum++, "");
//			}
//		}
//		else {
//			String preCol = getColNm(preColumnNum);
//			String crrCol = getColNm(crrColumnNum);
//			
//			int length = crrCol.length() - 1;
//			//System.out.println(crrColumnNum + " size : " + crrCol.length());
//			
//			
////			if("AZ".equals(preCol) && "BA".equals(crrCol)) {
////				//System.out.println("skip");
////			}
//			if(preCol.length() == crrCol.length()){
//				if(preCol.length() == 2 && crrCol.length() == 2 ) {
//					if((int)preCol.charAt(0) != (int)crrCol.charAt(0)
//					&& (int)preCol.charAt(1) != (int)crrCol.charAt(1)
//					) {
//						//System.out.println("skip");
//					}
//				} else {
//					int nPreCol = (int)preCol.charAt(length) ;
//					int nCrrCol = (int)crrCol.charAt(length) ;
//					if(nPreCol + 1 != nCrrCol) {
//						//System.out.println("공란무시함");
//						hMap.put("attr" + currColNum++, "");
//					}
//				}
//			}
//		}
//		
//		hMap.put("attr" + currColNum++, value == null ? "" : value);
//		
//		preColumnNum = columnNum;
//		
//		// cell 이벤트 발생 시 해당 cell의 주소와 값을 받아옴.
//		//row[currColNum++] = value == null ? "" : value;
	}
	
	private String getColNm(String columnNum) {
		String result = "";
		int lengtn = columnNum.length();
		for(int i = 0 ; i < lengtn ; i++) {
			String str = String.valueOf(columnNum.charAt(i));
			if( !isStringDouble(str) ) {
				result += str;
			}
		}
		return result;
	}
	
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