package jp.co.worksap.intern.entities.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.worksap.intern.entities.occupancy.OccupancyDTO;
import jp.co.worksap.intern.entities.occupancy.OccupancyDTOReader;

/** 
 * 
 * @author fsc
 *
 */
public class OccupancyHandler {
	
	private static List<OccupancyDTO> OccupancyDTOList;

	private static List<OccupancyDTO> getOccupancyDTOLIST() {
		if(OccupancyDTOList == null){
			try {
				OccupancyDTOList = new OccupancyDTOReader().getValues();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return OccupancyDTOList;
	}

	
	/** search OccupancyDTO by rank of time
	 * 
	 * @param calendar
	 * @param calendar2
	 * @return
	 */
	public static List<OccupancyDTO> getOccupancyDTOByTime(Calendar calendar, Calendar calendar2) {
		
		List<OccupancyDTO> all = getOccupancyDTOLIST();
		
		List<OccupancyDTO> ret = new ArrayList<OccupancyDTO>();
		
		int startDay = (int) (calendar.getTime().getTime() / 1000 / 3600 / 24);
		int endDay = (int) (calendar2.getTime().getTime() / 1000 / 3600 / 24);
		
		for (OccupancyDTO odto : all) {
			Calendar cal_tmp = Calendar.getInstance();
			cal_tmp.setTime(odto.getCheckInTime());
			int day = (int) (cal_tmp.getTime().getTime() / 1000 / 3600 / 24);
			if(day >= startDay && day <= endDay)
				ret.add(odto);
		}
		return ret;
	}

	
	
	
}
