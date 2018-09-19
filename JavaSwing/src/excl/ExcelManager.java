package excl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExcelManager {

	private static ExcelManager excelMng;

	public ExcelManager() {
		// TODO Auto-generated constructor stub
	}

	public static ExcelManager getInstance() {
		if (excelMng == null) excelMng = new ExcelManager();
		return excelMng;
	}
	
	public List<String> getSheetsNm(File file) throws Exception {
		int pos = file.getName().lastIndexOf(".");
		String ext = file.getName().substring( pos + 1 );
		
		List<String> list = new ArrayList<String>();
		/*--------------------------------------------------
		 * case .xls
		 * -------------------------------------------------*/
		if(ext.equals("xls")) {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
			// 엑셀파일의 시트 존재 유무 확인
			if (workbook.getNumberOfSheets() < 1) return null;
			int sheetCnt = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetCnt; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				list.add(sheet.getSheetName());
			}
		}
		/*--------------------------------------------------
		 * case .xlsx
		 * -------------------------------------------------*/
		else {
			//OPCPackage 파일을 읽거나 쓸 수 있는 상태의 컨테이너를 생성함
			OPCPackage opc = OPCPackage.open(file);
			//opc 컨테이너 XSSF형식으로 읽어옴. 이 Reader는 적은 메모리로 sax parsing 을 하기 쉽게 만들어줌.
			XSSFReader xssfReader = new XSSFReader(opc);
			//XSSFReader 에서 sheet 별 collection 으로 분할해서 가져옴
			XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator)xssfReader.getSheetsData();

			//통합문서 내의 모든 Sheet 에서 공유되는 스타일 테이블
			StylesTable styles = xssfReader.getStylesTable();
			//ReadOnlySharedStringsTable
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);
			
			int readCnt = 0;
			while(itr.hasNext()) {
				InputStream sheetStream = itr.next();
				String sheetname =  itr.getSheetName();
				list.add(sheetname);
				sheetStream.close();
			}
			
			opc.close();
		}
		
		return list;
	}
	
	public List<HashMap<String, String>> getSheetData(File file, int sheetNum) throws Exception {
		int pos = file.getName().lastIndexOf(".");
		String ext = file.getName().substring( pos + 1 );
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		/*--------------------------------------------------
		 * case .xls
		 * -------------------------------------------------*/
		if(ext.equals("xls")) {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
			// 엑셀파일의 시트 존재 유무 확인
			if (workbook.getNumberOfSheets() < 1) return null;
			HSSFSheet sheet = workbook.getSheetAt(sheetNum);
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				if(sheet.getRow(i) != null) {
					list.add(readCellData(sheet.getRow(i)));
				}
			}
		}
		/*--------------------------------------------------
		 * case .xlsx
		 * -------------------------------------------------*/
		else {
			//source 2
			//OPCPackage 파일을 읽거나 쓸 수 있는 상태의 컨테이너를 생성함
			OPCPackage opc = OPCPackage.open(file);
			//opc 컨테이너 XSSF형식으로 읽어옴. 이 Reader는 적은 메모리로 sax parsing 을 하기 쉽게 만들어줌.
			XSSFReader xssfReader = new XSSFReader(opc);
			//XSSFReader 에서 sheet 별 collection 으로 분할해서 가져옴
			XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator)xssfReader.getSheetsData();

			//통합문서 내의 모든 Sheet 에서 공유되는 스타일 테이블
			StylesTable styles = xssfReader.getStylesTable();
			//ReadOnlySharedStringsTable
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);
			
			List<String[]> dataList = new ArrayList<String[]>();
			
			int currSheetNum = 0;
			while(itr.hasNext()) {
				InputStream sheetStream = itr.next();
				if(currSheetNum == sheetNum) {
					InputSource sheetSource = new InputSource(sheetStream);
					
					Sheet2ListHandler sheet2ListHandler = new Sheet2ListHandler(list);
					
					ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, sheet2ListHandler, false);
					
					SAXParserFactory saxFactory = SAXParserFactory.newInstance();
					SAXParser saxParser = saxFactory.newSAXParser();
					
					XMLReader sheetParser = saxParser.getXMLReader();
					sheetParser.setContentHandler(handler);
					
					sheetParser.parse(sheetSource);
				}
				sheetStream.close();
				currSheetNum++;
			}
			
			opc.close();
		}
		
		return list;
	}
	
	/** **************************************************
	 * 엑셀파일 파싱후 HashMap리스트를 반환
	 ************************************************** */
	public List<HashMap<String, String>> getListExcel(File file) throws Exception {
		
		int pos = file.getName().lastIndexOf(".");
		String ext = file.getName().substring( pos + 1 );
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		FileInputStream fis = new FileInputStream(file);
		
		/*--------------------------------------------------
		 * case .xls
		 * -------------------------------------------------*/
		if(ext.equals("xls")) {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			// 엑셀파일의 시트 존재 유무 확인
			if (workbook.getNumberOfSheets() < 1) return null;
			
			// 첫번째 시트를 읽음
			HSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				list.add(readCellData(sheet.getRow(i)));
			}
		}
		/*--------------------------------------------------
		 * case .xlsx
		 * -------------------------------------------------*/
		else {
			//source 2
			//OPCPackage 파일을 읽거나 쓸 수 있는 상태의 컨테이너를 생성함
			OPCPackage opc = OPCPackage.open(fis);
			//opc 컨테이너 XSSF형식으로 읽어옴. 이 Reader는 적은 메모리로 sax parsing 을 하기 쉽게 만들어줌.
			XSSFReader xssfReader = new XSSFReader(opc);
			//XSSFReader 에서 sheet 별 collection 으로 분할해서 가져옴
			XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator)xssfReader.getSheetsData();

			//통합문서 내의 모든 Sheet 에서 공유되는 스타일 테이블
			StylesTable styles = xssfReader.getStylesTable();
			//ReadOnlySharedStringsTable
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);
			
			List<String[]> dataList = new ArrayList<String[]>();
			
			int readCnt = 0;
			while(itr.hasNext()) {
				InputStream sheetStream = itr.next();
				InputSource sheetSource = new InputSource(sheetStream);
				
				//Sheet2ListHandler sheet2ListHandler = new Sheet2ListHandler(dataList, 27);
				Sheet2ListHandler sheet2ListHandler = new Sheet2ListHandler(list);
				
				ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, sheet2ListHandler, false);
				
				SAXParserFactory saxFactory = SAXParserFactory.newInstance();
				SAXParser saxParser = saxFactory.newSAXParser();
				
				XMLReader sheetParser = saxParser.getXMLReader();
				sheetParser.setContentHandler(handler);
				
				sheetParser.parse(sheetSource);
				
				sheetStream.close();
			}
			
			opc.close();
		}
		return list;
		
	}
	
	/** **************************************************
	 * xls
	 ************************************************** */
	private HashMap<String, String> readCellData(HSSFRow row) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int maxNum = row.getLastCellNum();
		for (int i = 0; i < maxNum; i++) {
			hMap.put("attr" + i, getStringCellData(row.getCell(i)));
		}
		return hMap;
	}
	
	private String getStringCellData(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat();
		FormulaEvaluator evaluator = new

		HSSFWorkbook().getCreationHelper().createFormulaEvaluator();
		if (cell != null) {
			String data = null;
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN:
				boolean bdata = cell.getBooleanCellValue();
				data = String.valueOf(bdata);
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					data = formatter.format(cell.getDateCellValue());
				} else {
					double ddata = cell.getNumericCellValue();
					data = df.format(ddata);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				data = cell.toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
			case HSSFCell.CELL_TYPE_ERROR:
			case HSSFCell.CELL_TYPE_FORMULA:
				if (!(cell.toString() == "")) {
					if (evaluator.evaluateFormulaCell(cell) == HSSFCell.CELL_TYPE_NUMERIC) {

						double fddata = cell.getNumericCellValue();
						data = df.format(fddata);
					} else if (evaluator.evaluateFormulaCell(cell) ==

					HSSFCell.CELL_TYPE_STRING) {
						data = cell.getStringCellValue();
					} else if (evaluator.evaluateFormulaCell(cell) ==

					HSSFCell.CELL_TYPE_BOOLEAN) {
						boolean fbdata = cell.getBooleanCellValue();
						data = String.valueOf(fbdata);
					}
					break;
				}
			default:
				data = cell.toString();
			}
			return data;
		}
		else {
			return null;
		}
	}
	
	/** **************************************************
	 * xlsx
	 ************************************************** */
	private HashMap<String, String> readCellData(XSSFRow row) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int maxNum = row.getLastCellNum();
		for (int i = 0; i < maxNum; i++) {
			hMap.put("attr" + i, getStringCellData(row.getCell(i)));
		}
		return hMap;
	}
	
	private String getStringCellData(XSSFCell cell) {
		DecimalFormat df = new DecimalFormat();
		FormulaEvaluator evaluator = new

		XSSFWorkbook().getCreationHelper().createFormulaEvaluator();
		if (cell != null) {
			String data = null;
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BOOLEAN:
				boolean bdata = cell.getBooleanCellValue();
				data = String.valueOf(bdata);
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					data = formatter.format(cell.getDateCellValue());
				} else {
					double ddata = cell.getNumericCellValue();
					data = df.format(ddata);
				}
				break;
			case XSSFCell.CELL_TYPE_STRING:
				data = cell.toString();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
			case XSSFCell.CELL_TYPE_ERROR:
			case XSSFCell.CELL_TYPE_FORMULA:
				if (!(cell.toString() == "")) {
					if (evaluator.evaluateFormulaCell(cell) == XSSFCell.CELL_TYPE_NUMERIC) {

						double fddata = cell.getNumericCellValue();
						data = df.format(fddata);
					} else if (evaluator.evaluateFormulaCell(cell) ==

					XSSFCell.CELL_TYPE_STRING) {
						data = cell.getStringCellValue();
					} else if (evaluator.evaluateFormulaCell(cell) ==

					XSSFCell.CELL_TYPE_BOOLEAN) {
						boolean fbdata = cell.getBooleanCellValue();
						data = String.valueOf(fbdata);
					}
					break;
				}
			default:
				data = cell.toString();
			}
			return data;
		} else {
			return null;
		}
	}
	
}

