package jp.co.worksap.intern.service.analysis;

import java.util.HashMap;
import java.util.Map;

import jp.co.worksap.intern.constants.SalesChannelType;
import jp.co.worksap.intern.entities.room.RoomType;

public class ComparisionOccuResult {

	private static Map<SalesChannelType, Float> firstTotalGrowth;
	private static Map<SalesChannelType, Float> secondTotalGrowth;
	private static Map<RoomType, Map<SalesChannelType, Float>> firstRoomTypeGrowthMap;
	private static Map<RoomType, Map<SalesChannelType, Float>> secondRoomTypeGrowthMap;
	private static Map<SalesChannelType, Float> firstWeekdayGrowthMap;
	private static Map<SalesChannelType, Float> secondWeekdayGrowthMap;
	private static Map<SalesChannelType, Float> firstWeekendGrowthMap;
	private static Map<SalesChannelType, Float> secondWeekendGrowthMap;

	public ComparisionOccuResult(OccupancyResult firstOccupancyResult,
			OccupancyResult secondOccupancyResult,
			OccupancyResult thirdOccupancyResult) {
		// total growth
		firstTotalGrowth = calTotalGrowth(firstOccupancyResult,
				secondOccupancyResult);
		secondTotalGrowth = calTotalGrowth(secondOccupancyResult,
				thirdOccupancyResult);

		// room type growth
		firstRoomTypeGrowthMap = new HashMap<RoomType, Map<SalesChannelType, Float>>();
		secondRoomTypeGrowthMap = new HashMap<RoomType, Map<SalesChannelType, Float>>();
		for (RoomType rt : RoomType.values()) {
			Map<SalesChannelType, Float> firstRoomTypeGrowth = calRoomTypeGrowth(
					rt, firstOccupancyResult, secondOccupancyResult);
			Map<SalesChannelType, Float> secondRoomTypeGrowth = calRoomTypeGrowth(
					rt, secondOccupancyResult, thirdOccupancyResult);
			firstRoomTypeGrowthMap.put(rt, firstRoomTypeGrowth);
			secondRoomTypeGrowthMap.put(rt, secondRoomTypeGrowth);
		}

		// weekday
		firstWeekdayGrowthMap = calWeekdayGrowth(firstOccupancyResult,
				secondOccupancyResult);
		secondWeekdayGrowthMap = calWeekdayGrowth(secondOccupancyResult,
				thirdOccupancyResult);

		// weekend
		firstWeekendGrowthMap = calWeekendGrowth(firstOccupancyResult,
				secondOccupancyResult);
		secondWeekendGrowthMap = calWeekendGrowth(secondOccupancyResult,
				thirdOccupancyResult);

	}

	private Map<SalesChannelType, Float> calWeekendGrowth(
			OccupancyResult firstOccupancyResult,
			OccupancyResult secondOccupancyResult) {
		Map<SalesChannelType, Float> ret = new HashMap<SalesChannelType, Float>();

		for (SalesChannelType sct : SalesChannelType.values()) {

			Float f1 = firstOccupancyResult.getWeekdayMap().get(sct);
			Float f2 = secondOccupancyResult.getWeekdayMap().get(sct);
			Float cnt = 0F;
			if (f1 == null || f1 == 0F) {
				if (f2 == null || f2 == 0F)
					cnt = 0F;
				else
					cnt = 100F;
			} else {
				if (f2 == null || f2 == 0F)
					cnt = -100F;
				else
					cnt = 1F * (f2 / f1 - 1F) * 100;
			}
			ret.put(sct, cnt);
		}
		return ret;
	}

	private Map<SalesChannelType, Float> calWeekdayGrowth(
			OccupancyResult firstOccupancyResult,
			OccupancyResult secondOccupancyResult) {
		Map<SalesChannelType, Float> ret = new HashMap<SalesChannelType, Float>();

		for (SalesChannelType sct : SalesChannelType.values()) {
			Float f1 = firstOccupancyResult.getWeekdayMap().get(sct);
			Float f2 = secondOccupancyResult.getWeekdayMap().get(sct);
			Float cnt = 0F;
			if (f1 == null || f1 == 0F) {
				if (f2 == null || f2 == 0F)
					cnt = 0F;
				else
					cnt = 100F;
			} else {
				if (f2 == null || f2 == 0F)
					cnt = -100F;
				else
					cnt = 1F * (f2 / f1 - 1F) * 100;
			}
			ret.put(sct, cnt);
		}
		return ret;
	}

	private Map<SalesChannelType, Float> calRoomTypeGrowth(RoomType rt,
			OccupancyResult firstOccupancyResult,
			OccupancyResult secondOccupancyResult) {

		Map<SalesChannelType, Float> ret = new HashMap<SalesChannelType, Float>();

		for (SalesChannelType sct : SalesChannelType.values()) {
			Float f1 = 0F, f2 = 0F;
			Map<SalesChannelType, Float> tmp = firstOccupancyResult
					.getRoomTypeMap().get(rt);
			if (tmp == null)
				f1 = 0F;
			else
				f1 = firstOccupancyResult.getRoomTypeMap().get(rt).get(sct);
			tmp = secondOccupancyResult.getRoomTypeMap().get(rt);
			if (tmp == null)
				f2 = 0F;
			else
				f2 = secondOccupancyResult.getRoomTypeMap().get(rt).get(sct);

			Float cnt = 0F;
			if (f1 == null || f1 == 0F) {
				if (f2 == null || f2 == 0F)
					cnt = 0F;
				else
					cnt = 100F;
			} else {
				if (f2 == null || f2 == 0F)
					cnt = -100F;
				else
					cnt = 1F * (f2 / f1 - 1F) * 100;
			}
			ret.put(sct, cnt);
		}
		return ret;

	}

	private Map<SalesChannelType, Float> calTotalGrowth(
			OccupancyResult firstOccupancyResult,
			OccupancyResult secondOccupancyResult) {

		Map<SalesChannelType, Float> ret = new HashMap<SalesChannelType, Float>();

		for (SalesChannelType sct : SalesChannelType.values()) {
			Float f1 = firstOccupancyResult.getTotal().get(sct);
			Float f2 = secondOccupancyResult.getTotal().get(sct);
			Float cnt = 0F;
			if (f1 == null || f1 == 0F) {
				if (f2 == null || f2 == 0F)
					cnt = 0F;
				else
					cnt = 100F;
			} else {
				if (f2 == null || f2 == 0F)
					cnt = -100F;
				else
					cnt = 1F * (f2 / f1 - 1F) * 100;
			}
			ret.put(sct, cnt);
		}
		return ret;
	}

	public static Map<SalesChannelType, Float> getFirstTotalGrowth() {
		return firstTotalGrowth;
	}

	public static Map<SalesChannelType, Float> getSecondTotalGrowth() {
		return secondTotalGrowth;
	}

	public static Map<RoomType, Map<SalesChannelType, Float>> getFirstRoomTypeGrowthMap() {
		return firstRoomTypeGrowthMap;
	}

	public static Map<RoomType, Map<SalesChannelType, Float>> getSecondRoomTypeGrowthMap() {
		return secondRoomTypeGrowthMap;
	}

	public static Map<SalesChannelType, Float> getFirstWeekdayGrowthMap() {
		return firstWeekdayGrowthMap;
	}

	public static Map<SalesChannelType, Float> getSecondWeekdayGrowthMap() {
		return secondWeekdayGrowthMap;
	}

	public static Map<SalesChannelType, Float> getFirstWeekendGrowthMap() {
		return firstWeekendGrowthMap;
	}

	public static Map<SalesChannelType, Float> getSecondWeekendGrowthMap() {
		return secondWeekendGrowthMap;
	}

}
