package jp.co.worksap.intern.entities.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffDTOReader;

/** 
 * @author fsc
 *
 */
public class StaffHandler {

	private static List<StaffDTO> staffDTOList;
	
	private static Map<Long , StaffDTO> idToStaffMap;
	
	private StaffHandler(){}
	
	/** get staff list 
	 * @return the staffDTO list
	 */
	public static List<StaffDTO> getStaffDTOList(){
		if(staffDTOList == null){
			try {
				staffDTOList = new StaffDTOReader().getValues();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} 
		}
		return staffDTOList;
	}
	
	/** get the StaffDTO by staffID
	 * @param id : staff ID
	 * @return the staffDTO
	 */
	public static StaffDTO getStaffDTOByID(Long id) {
		getStaffDTOList();
		if (idToStaffMap == null) {
			idToStaffMap = new HashMap<Long, StaffDTO>();
			for (StaffDTO sd : staffDTOList)
				idToStaffMap.put(sd.getStaffId(), sd);
		}
		return idToStaffMap.get(id);
	}
	
	
}
