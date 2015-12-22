package jp.co.worksap.intern.service.analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.constants.SalesChannelType;
import jp.co.worksap.intern.entities.handler.OccupancyHandler;
import jp.co.worksap.intern.entities.occupancy.OccupancyDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.util.Utilities;
import jp.co.worksap.intern.writer.ResultWriterImpl;

/**
 * provide analysis service for business manager</br> include:</br> 
 *   1> room customer source analysis</br> 
 *   2> room occupancy analysis
 * 
 * @author fsc
 *
 */
public class BusinessAnalysis {

	private PeriodType period;
	private Calendar[] timeNode; // period 1~3 : timeRange[0]~timeRange[1] [1]~[2] [2]~[3]
	
	private List<OccupancyDTO> firstPeriodOccupancy;
	private List<OccupancyDTO> secondPeriodOccupancy;
	private List<OccupancyDTO> thirdPeriodOccupancy;
	
	// Service
	private OccupancyResult firstOccupancyResult;
	private OccupancyResult secondOccupancyResult;
	private OccupancyResult thirdOccupancyResult;
	
	private ComparisionOccuResult comparisionOccuResult;  
	
	// output format
	private static final String[] EMPTYLINE = {};

	/**
	 * 
	 * room customer source analysis
	 * 
	 */
	public void roomCustomerAnalysis() {
		
		period = Utilities.readPeriod(null);
		timeNode = readCurrentTimeAndRange(period);
		
		firstPeriodOccupancy = OccupancyHandler.getOccupancyDTOByTime(timeNode[0] , timeNode[1]);
		secondPeriodOccupancy = OccupancyHandler.getOccupancyDTOByTime(timeNode[1] , timeNode[2]);
		thirdPeriodOccupancy = OccupancyHandler.getOccupancyDTOByTime(timeNode[2] , timeNode[3]);
		
		firstOccupancyResult = new OccupancyResult(firstPeriodOccupancy);
		secondOccupancyResult = new OccupancyResult(secondPeriodOccupancy);
		thirdOccupancyResult = new OccupancyResult(thirdPeriodOccupancy);
		
		comparisionOccuResult = new ComparisionOccuResult(firstOccupancyResult , secondOccupancyResult , thirdOccupancyResult);
		
		// output result
		outputResult(period);
		
	}

	private void outputResult(PeriodType period) {
		List<String[]> result = new ArrayList<String[]>();
		String[] title = {"Sales channel Analysis Report"};
		String[] period_title = {"" , "Period:"+ period.toString()};

		result.add(title);
		result.add(period_title);
		result.add(EMPTYLINE);
		
		// total occupancy
		String[] total_title = {"" , "Sales Channels analysis in Total occurancy rate" , "" , "" , "(Growth is between periods)" };
		String[] total_Cols = {"" , "Total Occupancy rate / Sales Channels" , "Period1 Total rate" , "Period2 Total rate" , "Period3 Total rate" , "Growth1 (%)" , "Growth2 (%)"};
		result.add(total_title);
		
		result.add(total_Cols);
		for(SalesChannelType sct : SalesChannelType.values()){
			List<String> row = new ArrayList<String>();
			row.add("");
			row.add(sct.toString());
			addToRowPercent(row , firstOccupancyResult.getTotal().get(sct));
			addToRowPercent(row , secondOccupancyResult.getTotal().get(sct));
			addToRowPercent(row , thirdOccupancyResult.getTotal().get(sct));
			addToRowPercent(row , comparisionOccuResult.getFirstTotalGrowth().get(sct));
			addToRowPercent(row , comparisionOccuResult.getSecondTotalGrowth().get(sct));
			
			result.add(row.toArray(new String[row.size()]));
		}
		
		result.add(EMPTYLINE);
		
		// roomtype aspect
		String[] roomType_title = {"" , "Sales Channels analysis in Room type aspect" , "" , "" , "(Growth is between periods)" };
		List<String> roomType_Cols = new ArrayList<String>();
		roomType_Cols.add("");
		roomType_Cols.add("");
		for(RoomType rt : RoomType.values()){
			roomType_Cols.add(rt.toString() + " (Period1)");
			roomType_Cols.add(rt.toString() + " (Period2)");
			roomType_Cols.add(rt.toString() + " Growth1 (%)");
			roomType_Cols.add(rt.toString() + " (Curr)");
			roomType_Cols.add(rt.toString() + " Growth2 (%)");
		}
		result.add(roomType_title);
		result.add(roomType_Cols.toArray(new String[roomType_Cols.size()]));
		for(SalesChannelType sct : SalesChannelType.values()){
			List<String> row = new ArrayList<String>();
			// first
			row.add("");
			row.add(sct.toString());
			for(RoomType rt : RoomType.values()){
				// first
				Map<SalesChannelType , Float> sub = firstOccupancyResult.getRoomTypeMap().get(rt);
				if(sub == null || sub.get(sct) == null)
					row.add("0");
				else
					row.add(String.format("%.2f", sub.get(sct)) + "%");
				
				//second
				sub = secondOccupancyResult.getRoomTypeMap().get(rt);
				if(sub == null || sub.get(sct) == null)
					row.add("0");
				else
					row.add(String.format("%.2f", sub.get(sct)) + "%");
				
				// growth1
				sub = comparisionOccuResult.getFirstRoomTypeGrowthMap().get(rt);
				if(sub == null || sub.get(sct) == null)
					row.add("0%");
				else
					row.add(String.format("%.2f", sub.get(sct)) + "%");
				
				// third
				sub = thirdOccupancyResult.getRoomTypeMap().get(rt);
				if(sub == null || sub.get(sct) == null)
					row.add("0");
				else
					row.add(String.format("%.2f", sub.get(sct)) + "%");
				
				// growth2
				sub = comparisionOccuResult.getSecondRoomTypeGrowthMap().get(rt);
				if(sub == null || sub.get(sct) == null)
					row.add("0");
				else
					row.add(String.format("%.2f", sub.get(sct)) + "%");
			}
			result.add(row.toArray(new String[row.size()]));
			
		}
		
		result.add(EMPTYLINE);
		// weekday weekend
		// total occupancy
		String[] date_title = { "",
				"Sales Channels Occupancy analysis in Weekday/weekend (Occupancy rate)", "", "",
				"(Growth is between periods)" };
		String[] date_Cols = { "", "Total Occupancy rate / Sales Channels",
				"Weekday (Period1)", "Weekday (Period2)",
				"Growth1 (%)", "Weekday (Period3)", "Growth2 (%)",
				"",
				"Weekend (Period1)", "Weekend (Period2)",
				"Growth1 (%)", "Weekend (Period3)", "Growth2 (%)",};
		result.add(date_title);
		result.add(date_Cols);
		for (SalesChannelType sct : SalesChannelType.values()) {
			List<String> row = new ArrayList<String>();
			// weekday
			row.add("");
			row.add(sct.toString());
			addToRowPercent(row, firstOccupancyResult.getWeekdayMap().get(sct));
			addToRowPercent(row, secondOccupancyResult.getWeekdayMap().get(sct));
			addToRowPercent(row, comparisionOccuResult.getFirstWeekdayGrowthMap().get(sct));
			addToRowPercent(row, thirdOccupancyResult.getWeekdayMap().get(sct));
			addToRowPercent(row, comparisionOccuResult.getSecondWeekdayGrowthMap().get(sct));
			
			//weekend
			row.add("");
			row.add(sct.toString());
			addToRowPercent(row, firstOccupancyResult.getWeekendMap().get(sct));
			addToRowPercent(row, secondOccupancyResult.getWeekendMap().get(sct));
			addToRowPercent(row, comparisionOccuResult.getFirstWeekendGrowthMap().get(sct));
			addToRowPercent(row, thirdOccupancyResult.getWeekendMap().get(sct));
			addToRowPercent(row, comparisionOccuResult.getSecondWeekendGrowthMap().get(sct));

			result.add(row.toArray(new String[row.size()]));
		}
		
 		
		ResultWriterImpl rw = new ResultWriterImpl(Constants.DEFAULT_CSV_OUTPUT_FOLDER
				+ "SalesChannelAnalysisReport.csv");
		rw.writeResult(result);
		System.out.println("Report Generate success ! path is " + Constants.DEFAULT_CSV_OUTPUT_FOLDER
				+ "SalesChannelAnalysisReport.csv\n");
		
	}

	private void addToRowPercent(List<String> row, Float res) {
		if(res == null)
			row.add("0%");
		else
			row.add(String.format("%.2f", res) + "%");
	}

	private void addToRow(List<String> row, Float res) {
		if(res == null)
			row.add("0");
		else
			row.add(res.toString());
	}

	/**
	 * read Current Time from console and cal the time node
	 * 
	 * @param period
	 * @return
	 */
	private Calendar[] readCurrentTimeAndRange(PeriodType period) {
		Calendar node0 = Calendar.getInstance();
		Calendar node1 = Calendar.getInstance();
		Calendar node2 = Calendar.getInstance();
		Calendar node3 = Calendar.getInstance();
		node0.set(Calendar.HOUR_OF_DAY, 0);
		node0.set(Calendar.MINUTE, 0);
		node0.set(Calendar.SECOND, 0);
		node1.set(Calendar.HOUR_OF_DAY, 0);
		node1.set(Calendar.MINUTE, 0);
		node1.set(Calendar.SECOND, 0);
		node2.set(Calendar.HOUR_OF_DAY, 0);
		node2.set(Calendar.MINUTE, 0);
		node2.set(Calendar.SECOND, 0);
		node3.set(Calendar.HOUR_OF_DAY, 0);
		node3.set(Calendar.MINUTE, 0);
		node3.set(Calendar.SECOND, 0);

		int time = Utilities.readTimeFromConsole(period);
		switch (period) {
		case WEEKLY:
			node0.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			node1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			node2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			node3.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			if (time == 0) {
				node0.add(Calendar.DATE, -14);
				node1.add(Calendar.DATE, -7);
				// node2.set(Calendar.DATE, );
				node3.add(Calendar.DATE, 7);
			} else {
				node0.add(Calendar.DATE, -21);
				node1.add(Calendar.DATE, -14);
				node2.add(Calendar.DATE, -7);
				// node3.set(Calendar.DATE, );
			}
			break;
		case MONTHLY:
			node0.set(Calendar.DAY_OF_MONTH, 1);
			node1.set(Calendar.DAY_OF_MONTH, 1);
			node2.set(Calendar.DAY_OF_MONTH, 1);
			node3.set(Calendar.DAY_OF_MONTH, 1);
			if (time == 0) {
				node0.add(Calendar.MONTH, -2);
				node1.add(Calendar.MONTH, -1);
				// node2.set(Calendar.DATE, );
				node3.add(Calendar.MONTH, 1);
			} else {
				node0.add(Calendar.MONTH, -3);
				node1.add(Calendar.MONTH, -2);
				node2.add(Calendar.MONTH, -1);
				// node3.set(Calendar.DATE, );
			}
			break;
		case YEARLY:
			node0.set(Calendar.MONTH, Calendar.JANUARY);
			node0.set(Calendar.DAY_OF_MONTH, 1);
			node1.set(Calendar.MONTH, Calendar.JANUARY);
			node1.set(Calendar.DAY_OF_MONTH, 1);
			node2.set(Calendar.MONTH, Calendar.JANUARY);
			node2.set(Calendar.DAY_OF_MONTH, 1);
			node3.set(Calendar.MONTH, Calendar.JANUARY);
			node3.set(Calendar.DAY_OF_MONTH, 1);
			if (time == 0) {
				node0.add(Calendar.YEAR, -2);
				node1.add(Calendar.YEAR, -1);
				// node2.set(Calendar.DATE, );
				node3.add(Calendar.YEAR, 1);
			} else {
				node0.add(Calendar.YEAR, -3);
				node1.add(Calendar.YEAR, -2);
				node2.add(Calendar.YEAR, -1);
				// node3.set(Calendar.DATE, );
				break;
			}
		}
		return new Calendar[]{node0, node1, node2, node3};
	}

}
