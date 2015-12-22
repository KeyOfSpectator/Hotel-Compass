package jp.co.worksap.intern.entities.occupancy;

import java.util.Date;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class OccupancyDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3711231132225685346L;

	private Long occupancyId;
	private Long roomId;
	private String roomType;
	private Long customerId;
	private String customerSource;
	private Long price;
	private String priceUnit;
	private Date checkInTime;
	private Date checkOutTime;

	/**
	 * Default Constructor 
	 * 
	 * @param occupancyId
	 * @param roomId
	 * @param roomType
	 * @param customerId
	 * @param customerSource
	 * @param price
	 * @param priceUnit
	 * @param checkInTime
	 * @param checkOutTime
	 */
	public OccupancyDTO(Long occupancyId, Long roomId, String roomType,
			Long customerId, String customerSource, Long price,
			String priceUnit, Date checkInTime, Date checkOutTime) {
		super();
		this.occupancyId = occupancyId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.customerId = customerId;
		this.customerSource = customerSource;
		this.price = price;
		this.priceUnit = priceUnit;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}

	public Long getOccupancyId() {
		return occupancyId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public Long getPrice() {
		return price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

}
