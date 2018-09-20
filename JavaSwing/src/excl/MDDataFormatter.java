package excl;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.DataFormatter;

public class MDDataFormatter extends DataFormatter{

	/***************************************************************************************
	 * 값이 DOUBLE 형인 경우
	 * 1. 소수점 n번째 자리까지 남기기
	 * 
	 * 2. 소수점 6자리 이하 절삭 방지
	 * 원레값이 0.515549401221113 인데 절삭되어
	 * 0.515549 로 표시되는 형상 발생
	 * 
	 * 3. 소수점 이하 0 출력 방지
	 * ***************************************************************************************/
	@Override
	public String formatRawCellContents(double value, int formatIndex, String formatString) {
		
		DecimalFormat df;
		
		int maxLength = 16;
		
		String resultValue = Double.toString(value);
		
//		if(0.7674691243937627 == value) {
//			System.out.println();
//		}
		
		int cutting = 0;
		int length = 0;
		
		try {
			length = resultValue.getBytes("ms949").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int nInteger = (int)value;
		String sInteger = String.valueOf(nInteger);
		
		if(length > maxLength) {
			if(nInteger == 0) maxLength = 18;
			
			cutting = maxLength - sInteger.length() - 1; // -1 = .
			
			double dValue = Double.parseDouble(String.format("%." + String.valueOf(cutting) + "f", value));
			
			resultValue = Double.toString(dValue);
			
			if(nInteger == 0) {
				double dRound = Math.round(value * Math.pow(10.0, 15))/Math.pow(10.0, 15);
				double dFloor = Math.floor(value * Math.pow(10.0, 15))/Math.pow(10.0, 15);
				
				long imsi10 = (long)(value * Math.pow(10.0, cutting-1)) * 10;
				long imsi01 = (long)(value * Math.pow(10.0, cutting));
				
				int judge = (int)(imsi01 - imsi10);
				
				if(judge > 5) {
					resultValue = Double.toString(dRound) + " ";
				} else {
					resultValue = Double.toString(dFloor) + " ";
				}
			}
		} // end if(length > maxLength)
		
		if(nInteger != 0) {
			// 소수점 이하 0 출력 방지
			df = new DecimalFormat("#." + "#####" + "#####" + "#####" + "###");
			resultValue = df.format(Double.parseDouble(resultValue));
		}
		
//		if("0.767469124393762 ".equals(resultValue)) {
//			System.out.println();
//		}
		
		return resultValue;
	} // end public String formatRawCellContents(double value, int formatIndex, String formatString)
	
}
