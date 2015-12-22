package jp.co.worksap.intern.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.worksap.intern.util.Utilities;

public class GenerateRoomOccupancyRecordData {

	private static String[] roomID = {"1" , "2" , "3" , "4" , "5" , "6" , "7"};
	private static String[] roomtype = {"SINGLE" , "DOUBLE" , "SEMIDOUBLE" , "TWIN" , "TRIPLE" , "SUITE" , "OTHER"};
	private static String[] customer_source = {"TELEPHONE" , "TRAVEL AGENT" , "COMPANY" ,  "WEBSITE" , "OTHER"};
	private static String[] roomPrice = {"230" , "200" , "190" , "200" , "210" , "300" , "100"};
	
	private static String[] colName = {"OccupancyID" , "RoomID" , "RoomType" , "customerID" , "customer source" , "price" , "price unit" , "check in time" , "check out time"};
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		List<String[]> ret_str = new ArrayList<String[]>();
		ret_str.add(colName);
		
		for (int i = 0; i < 200; i++) {
			List<String> row = new ArrayList<String>();
			row.add(i+1+"");
			row.add(roomID[i%7]);
			row.add(roomtype[i%7]);
			row.add(i+1+""); // customer ID
			row.add(customer_source[i%5]);
			row.add(roomPrice[i%7]);
			row.add("USD");
			
			// date
			Date date = new Date();
			date.setYear(115 - i/100);
			date.setMonth(i/30);
			date.setDate((14-i)%30);
			
			
			
			row.add(Utilities.formatDate(date));
			date.setDate(15);
			row.add(Utilities.formatDate(date));
			
			ret_str.add(row.toArray(new String[row.size()]));
		}
		
		
		ResultWriterImpl rw = new ResultWriterImpl();
		rw.writeResult(ret_str);
		
	}

}
