package jp.co.worksap.intern.util;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import jp.co.worksap.intern.constants.Messages;
import jp.co.worksap.intern.entities.staff.RankType;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.task.TaskDTO;
import jp.co.worksap.intern.service.analysis.PeriodType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Miscellaneous utilities.
 * 
 */
public final class Utilities {

	private static final Log logger = LogFactory.getLog(Utilities.class);
	
	private static final Scanner scanner = new Scanner(System.in);
	
	// Constant
	private static final String optionFormat = "[%d] %s";
	private static final String optionQuery = "input option index or name to choose (default[ %s ]): ";
	private static final String optionWrong = "wrong option, please enter again:";

	// Must not be instantiated
	private Utilities() {
	}

	/**
	 * close reader without any exception.
	 * 
	 * @param reader
	 */
	public static void closeSilently(FileReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/**
	 * close reader without any exception.
	 * 
	 * @param reader
	 */
	public static void closeSilently(CSVReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/**
	 * flush first, and close writer without any exception.
	 * 
	 * @param writer
	 */
	public static void closeSilently(CSVWriter writer) {
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.fatal(Messages.ERROR_CSV_FILE_CLOSE, e);
			}
		}
	}

	/** default datetime format */
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");

	public static synchronized Date parseDateTimeStr(String datetimeStr)
			throws IllegalArgumentException {
		try {
			return datetimeFormat.parse(datetimeStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Date Time String!");
		}
	}
	
	public static synchronized String formatDateTime(Date date) {
		return datetimeFormat.format(date);
	}
	
	/** default date format */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd");

	public static synchronized Date parseDateStr(String dateStr)
			throws IllegalArgumentException {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Date String!");
		}
	}
	
	public static synchronized String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/** default time format */
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm");

	public static synchronized Date parseTimeStr(String timeStr)
			throws IllegalArgumentException {
		try {
			return timeFormat.parse(timeStr);
		} catch (ParseException e) {
			logger.fatal(Messages.ERROR_PARSE_DATE, e);
			throw new IllegalArgumentException("Failed to parse Time String!");
		}
	}

	public static synchronized String formatTime(Date time) {
		return timeFormat.format(time);
	}

	/** input a valid Long number, default is defaultValue
	 *  if default value is null, no default value.
	 * @param defaultValue
	 * @return input value
	 */
	public static long readInteger(Long defaultValue) {
		while (true) {
			String inputStr = scanner.nextLine();
			long ret;
			try {
				ret = Long.valueOf(inputStr).longValue();
				return ret;
			} catch (NumberFormatException e) {
				if (inputStr == "" && defaultValue != null) {
					return defaultValue;
				}
			}
		}
	}

	/** Read option index from console </br>
	 *  defaultIndx is 1 if defaultIndx is null</br>
	 * 
	 * @param title        (null is default "\nPlease choose the options below:\n")
	 * @param funcs 		
	 * @param defaultIndx  (null is the first one)
	 * @return
	 */
	public static int readFromConsoleForOptions(String title, String[] funcs , Integer defaultIndx) {
		if(funcs == null || funcs.length == 0)
			throw new IllegalArgumentException();
		
		int funcs_len = funcs.length;
		if(defaultIndx == null || defaultIndx < 0 || defaultIndx >= funcs_len)
			defaultIndx = 0;
		
		String title_tmp = title;
		if (title == null || title_tmp.length() == 0) {
			System.err.println("\nPlease choose the options below:\n");
		} else {
			System.out.println();
			System.out.println(title);
			System.out.println();
		}
		
		for (int i = 0; i < funcs_len; i++) {
			System.out.println(String.format(optionFormat, i+1, funcs[i]));
		}

		while(true){
			System.out.print(String.format(optionQuery, defaultIndx+1+""));
			String inputStr = scanner.nextLine();
			int indx = -1;
			try{
				indx = Integer.valueOf(inputStr);
				indx--;
			} catch(NumberFormatException e) {
				if("".equals(inputStr))
					return defaultIndx;
				
				for(int i=0; i<funcs_len; i++){
					if(funcs[i].equalsIgnoreCase(inputStr)){
						indx = i;
						break;
					}
				}
			}
			if(indx < 0 || indx >= funcs_len){
				System.out.println(optionWrong);
			}
			else
				return indx;
		}
		
	}
	
	/** read functions from the console
	 * @param title
	 * @param funcs
	 * @param defaultValue
	 * @return
	 */
	public static int readFunctionsFromConsole(String title,
			List<String> funcs, Integer defaultValue) {
		int indx = readFromConsoleForOptions(title, funcs.toArray(new String[funcs.size()]), defaultValue);
		return indx;
	}
	
	/** read time period from console
	 * 
	 * @param defaultValue
	 * @return
	 */
	public static PeriodType readPeriod(Integer defaultIndx) {
		String[] str= {"Weekly" , "Monthly" , "Yearly"};
		int indx = readFromConsoleForOptions("Please select time period:", str, defaultIndx);
		switch (indx) {
		case 0:
			return PeriodType.WEEKLY;
		case 1:
			return PeriodType.MONTHLY;
		case 2:
			return PeriodType.YEARLY;
		default:
			return PeriodType.WEEKLY;
		}
	}

	 
	/** Check whether the function is in the rank of permission
	 * 
	 * 
	 * @param optionIndx
	 * @param rank
	 * @param permission_list
	 * @return
	 */
	public static boolean filterPermission(int optionIndx, RankType rank,
			int[][] permission_list) {
		switch (rank) {
		case officer:
			return checkPermission(optionIndx , permission_list[0]);
		case expert:
			return checkPermission(optionIndx , permission_list[1]);
		case senior:
			return checkPermission(optionIndx , permission_list[2]);
		case assistant:
			return checkPermission(optionIndx , permission_list[3]);
		default:
			return false;
		}
	}

	/** Check one role's permission
	 * @param optionIndx
	 * @param permission_list
	 * @return
	 */
	private static boolean checkPermission(int optionIndx, int[] permission_list) {
		// 0 is Quit
//		if(optionIndx == 0)
//			return true;
		for (int i = 0; i < permission_list.length; i++) {
			if(optionIndx == permission_list[i])
				return true;
		}
		return false;
	}

	/** read time from console
	 * 
	 * @param period
	 * @return
	 */
	public static int readTimeFromConsole(PeriodType period) {
		switch (period) {
		case WEEKLY:
			return readFromConsoleForOptions("Please select the week you want to analyze", new String[]{"This Week", "Last Week" }, 0);
		case MONTHLY:
			return readFromConsoleForOptions("Please select the month you want to analyze", new String[]{"This Month", "Last Month" }, 0);
		case YEARLY:
			return readFromConsoleForOptions("Please select the year you want to analyze", new String[]{"This Year", "Last Year" }, 0);
		default:
			break;
		}
		return 0;
	}

	public static boolean isWorkday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		return (day != Calendar.FRIDAY && day != Calendar.SATURDAY);
	}

	public static TaskDTO readTask(StaffDTO staff) {
		System.out.println("Please input task title:\n");
		String title = scanner.nextLine();
		System.out.println("Please input excute member\n");
		Long excuteId = readInteger(null);
		
		int startDay = readFromConsoleForOptions("Please select the task start date", new String[]{"today" , "tomorrow"}, 0);
		Calendar ca = Calendar.getInstance();
		Date startTime = ca.getTime();
		if(startDay == 1){
			ca.add(Calendar.DAY_OF_YEAR , 1);
			startTime = ca.getTime();
		}
		
		Calendar en = Calendar.getInstance();
		Date endTime = null;
		PeriodType period = Utilities.readPeriod(null);
		switch (period) {
		case MONTHLY:
			en.add(Calendar.MONTH, 1);
			endTime = ca.getTime();
			break;
		case YEARLY:
			en.add(Calendar.YEAR, 1);
			endTime = ca.getTime();
			break;
		case WEEKLY:
			en.add(Calendar.WEEK_OF_YEAR, 1);
			endTime = ca.getTime();
			break;

		default:
			break;
		}
		
		
		TaskDTO ret = new TaskDTO(null , staff.getStaffId(), title, excuteId, startTime, endTime);
		
		System.out.println("Task assignment success!");
		
		return ret;
	}

}
