package jp.co.worksap.intern.service.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.worksap.intern.constants.SalesChannelType;
import jp.co.worksap.intern.entities.occupancy.OccupancyDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.util.Utilities;

public class OccupancyResult {

	private List<OccupancyDTO> OccupancyDTOList;
	private Map<SalesChannelType , Float> total;
	private Map<RoomType , Map<SalesChannelType , Float>> roomTypeMap;
	private Map<SalesChannelType , Float> weekdayMap;
	private Map<SalesChannelType , Float> weekendMap;
	
	/**
	 *  calculate Occupancy rate in room type aspects and in weekday/weekend aspects
	 *  
	 * @param firstPeriodOccupancy
	 */
	public OccupancyResult(List<OccupancyDTO> periodOccupancy) {
		this.OccupancyDTOList = periodOccupancy;
		
		// total
		this.total = calAllPercentage();
		// roomTypeMap
		this.roomTypeMap = calRoomTypePercentage();
		// weekdayMap
		this.weekdayMap = calWeekday_weekendPercentage(true);
		// weekendMap
		this.weekendMap = calWeekday_weekendPercentage(false);
		
		
	}

	private Map<SalesChannelType, Float> calAllPercentage() {
		Map<SalesChannelType , Float> ret = new HashMap<SalesChannelType, Float>();
		int sum = 0;
		for(OccupancyDTO odto : OccupancyDTOList){
			sum++;
			Float cnt = ret.get(SalesChannelType.valueOfString(odto.getCustomerSource()));
			if(cnt == null)
				cnt = 0F;
			cnt++;
			ret.put(SalesChannelType.valueOfString(odto.getCustomerSource()) , cnt);
		}
		
		if(sum == 0){
			ret.clear();
			return ret;
		}
		
		for(Entry<SalesChannelType, Float> ent: ret.entrySet()){
			ret.put(ent.getKey(), (ent.getValue() * 100 / sum));
		}
		return ret;
	}
	
	private Map<RoomType, Map<SalesChannelType, Float>> calRoomTypePercentage() {
		
		Map<RoomType, Map<SalesChannelType, Float>> ret = new HashMap<RoomType, Map<SalesChannelType,Float>>();
		for(OccupancyDTO odto : OccupancyDTOList){
			RoomType roomType = RoomType.valueOfString(odto.getRoomType());
			Map<SalesChannelType, Float> sub_map = ret.get(roomType);
			Float cnt = 0F;
			if(sub_map == null){
				sub_map = new HashMap<SalesChannelType, Float>();
			}
			else{
				cnt = sub_map.get(SalesChannelType.valueOfString(odto.getCustomerSource()));
				if(cnt == null)
					cnt = 0F;
			}
			cnt++;
			sub_map.put(SalesChannelType.valueOfString(odto.getCustomerSource()) , cnt);
			
			ret.put(roomType , sub_map);
			
		}
		
		for(RoomType eachRT : RoomType.values()){
			Map<SalesChannelType, Float> sub = ret.get(eachRT);
			if(sub == null)
				continue;
			int sum = 0;
			for(@SuppressWarnings("unused") Entry<SalesChannelType, Float> ent: sub.entrySet()){
				sum ++;
			}
			if(sum == 0)
				sub.clear();
			for(Entry<SalesChannelType, Float> ent: sub.entrySet()){
				sub.put(ent.getKey(), (ent.getValue() * 100 / sum));
			}
			ret.put(eachRT, sub);
		}
		
		return ret;
	}

	/** cal weekday/weekend percentage
	 * @param flag true->weekday false->weekend
	 * @return
	 */
	private Map<SalesChannelType, Float> calWeekday_weekendPercentage(boolean flag) {
		Map<SalesChannelType , Float> ret = new HashMap<SalesChannelType, Float>();
		int sum = 0;
		for(OccupancyDTO odto : OccupancyDTOList){
			if(flag){
				if(!Utilities.isWorkday(odto.getCheckInTime()))
					continue;
			}
			else{
				if(Utilities.isWorkday(odto.getCheckInTime()))
					continue;
			}
					
			sum++;
			Float cnt = ret.get(SalesChannelType.valueOfString(odto.getCustomerSource()));
			if(cnt == null)
				cnt = 0F;
			cnt++;
			ret.put(SalesChannelType.valueOfString(odto.getCustomerSource()) , cnt);
		}
		
		if(sum == 0){
			ret.clear();
			return ret;
		}
		
		for(Entry<SalesChannelType, Float> ent: ret.entrySet()){
			ret.put(ent.getKey(), (ent.getValue() * 100 / sum));
		}
		return ret;
	}

	public Map<SalesChannelType, Float> getTotal() {
		return total;
	}

	public Map<RoomType, Map<SalesChannelType, Float>> getRoomTypeMap() {
		return roomTypeMap;
	}

	public Map<SalesChannelType, Float> getWeekdayMap() {
		return weekdayMap;
	}

	public Map<SalesChannelType, Float> getWeekendMap() {
		return weekendMap;
	}
	
	
}
