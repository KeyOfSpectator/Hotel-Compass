//package jp.co.worksap.intern.entities.task;
//
//import java.io.IOException;
//import java.util.Date;
//
//import jp.co.worksap.intern.constants.Constants;
//import jp.co.worksap.intern.entities.AbstractDTOReader;
//import jp.co.worksap.intern.util.Utilities;
//
//public class TaskDTOReader extends AbstractDTOReader<TaskDTO> {
//	private static final int COLUMN_INDEX_OCCUPANCY_ID = 0;
//	private static final int COLUMN_INDEX_ROOM_ID = 1;
//	private static final int COLUMN_INDEX_ROOM_TYPE = 2;
//	private static final int COLUMN_INDEX_CUSTOMER_ID = 3;
//	private static final int COLUMN_INDEX_CUSTOMER_SOURCE = 4;
//	private static final int COLUMN_INDEX_PRICE = 5;
//	private static final int COLUMN_INDEX_PRICE_UNIT = 6;
//	private static final int COLUMN_INDEX_CHECKIN_TIME = 7;
//	private static final int COLUMN_INDEX_CHECKOUT_TIME = 8;
//
//	private String fileAddress = Constants.DEFAULT_CSV_FOLDER + "Occupancy_RECORD.csv";
//
//	/**
//	 * use default file address
//	 * 
//	 * @throws IOException
//	 */
//	public TaskDTOReader() throws IOException {
//		super(TaskDTOReader.class.getName());
//		super.init();
//	}
//
//	/**
//	 * use customize file address
//	 * 
//	 * @param fileAddress
//	 * @throws IOException
//	 */
//	public TaskDTOReader(final String fileAddress) throws IOException {
//		super(TaskDTOReader.class.getName());
//		this.fileAddress = fileAddress;
//		super.init();
//	}
//
//	@Override
//	protected String getFileArress() {
//		return fileAddress;
//	}
//
//	@Override
//	protected TaskDTO convertArrayToDTO(String[] value) throws IOException {
//		
//		Long occupancyId = Long.valueOf(value[COLUMN_INDEX_OCCUPANCY_ID]);
//		Long roomId = Long.valueOf(value[COLUMN_INDEX_ROOM_ID]);
//		String roomType = value[COLUMN_INDEX_ROOM_TYPE];
//		Long customerId = Long.valueOf(value[COLUMN_INDEX_CUSTOMER_ID]);;
//		String customerSource = value[COLUMN_INDEX_CUSTOMER_SOURCE];
//		Long price = Long.valueOf(value[COLUMN_INDEX_PRICE]);;
//		String priceUnit = value[COLUMN_INDEX_PRICE_UNIT];
//		Date checkInTime = Utilities.parseDateStr(value[COLUMN_INDEX_CHECKIN_TIME]);
//		Date checkOutTime = Utilities.parseDateStr(value[COLUMN_INDEX_CHECKOUT_TIME]);
//		
//		TaskDTO dto = new TaskDTO(occupancyId , roomId , roomType , customerId , customerSource , price , priceUnit , checkInTime , checkOutTime);
//		return dto;
//	}
//}
