package jp.co.worksap.intern.core;

import jp.co.worksap.intern.entities.handler.StaffHandler;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.user.BusinessManager;
import jp.co.worksap.intern.entities.user.RegionManager;
import jp.co.worksap.intern.entities.user.SalesManager;
import jp.co.worksap.intern.entities.user.StaffManager;
import jp.co.worksap.intern.util.Utilities;

public class Main {

	public static void main(String[] args) {

		System.out.println("\n****************            ****************\n");
		System.out.println("         Welcome to Hotel Compass");
		System.out.println("\n****************            ****************\n");
		
		
		while(true){
		
		System.out.println("Please login with your staff ID: (input 0 to exit)");
		
		// get staff by input staff ID
		StaffDTO staff;
		while(true){
			long id = Utilities.readInteger(null);
			// exit
			if(id == 0){
				System.out.println("\n\n****************            ****************\n");
				System.out.println("         Thanks for using Hotel Compass");
				System.out.println("\n****************            ****************\n");
				return;
			}
				
			staff = StaffHandler.getStaffDTOByID(id);
			if(staff == null)
				System.out.println("Sorry not found your staff ID, please input again:");
			else
				break;
		}
		
		// Welcome and remind task track
		System.out.println("\nWelcome "+ staff.getPosition().toString() + " Mr./Ms." + staff.getName());
		System.out.println("Your Security level is " + staff.getRank());
		
		// Different type of manager, different logic
		switch (staff.getPosition()) {
		case business_manager:
			new BusinessManager(staff).startService();
			break;
		case sales_manager:
			new SalesManager(staff).startService();
			break;
		case region_manager:
			new RegionManager(staff).startService();
			break;
		case staff:
			new StaffManager(staff).startService();
			break;
		default:
			System.out.println("Sorry, this function has not been implemented yet.");
			break;
		}
		
		}
		
	}

}
