package jp.co.worksap.intern.entities.user;

import java.util.ArrayList;
import java.util.List;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.service.analysis.BusinessAnalysis;
import jp.co.worksap.intern.service.process.TaskService;
import jp.co.worksap.intern.util.Utilities;



/** Business Manager service entry
 *  functions and security permission: 
 *             1.room customer source analysis 			  				   (officer, expert)
 *             2.room occupancy/conflict rate analysis  (officer, expert, senior, assistant)
 *             3.task assignment 						  		   (officer, expert, senior)
 *             4.task receive 											  (If have new task)
 *             0.Quit
 *             
 * @author fsc
 *
 */
public class BusinessManager implements User{

	StaffDTO staff;
	TaskService ts;
	
	public BusinessManager(StaffDTO staff) {
		this.staff = staff;
		this.ts = TaskService.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see jp.co.worksap.intern.entities.user.User#startService()
	 */
	@Override
	public void startService() {
		
		BusinessAnalysis ba = new BusinessAnalysis();
		
		
		// filter business manager's functions
		String[] funcs_all = {
				"room Sales channel analysis  	   (officer, expert)",
				"room occupancy/conflict rate analysis  (officer, expert, senior, assistant)",
				"task assignment    			   (officer, expert, senior)"
		};
		
		int[][] permission_list = {
				{0,1,2,3},
				{0,1,2,3},
				{1,2,3},
				{1,3}
		};
		
		List<String> funcs = new ArrayList<String>();
		for (int i = 0; i < funcs_all.length; i++) {
			funcs.add(funcs_all[i]);
		}
		
		// task notify function
		ts.addTaskfuncitons(funcs , staff);
		
		// Quit
		funcs.add("Quit");
		
		while(true){
			// read option from console
			int optionIndx = Utilities.readFunctionsFromConsole("Please select the functions below:" , funcs , null);
			
			// Quit
			if(optionIndx == funcs.size()-1)
				return;
			
			// permission filter
			if( Utilities.filterPermission(optionIndx , staff.getRank() , permission_list)){
				switch (optionIndx) {
				case 0:
					ba.roomCustomerAnalysis();
					break;
				case 1:
//					ba.roomOccupancyAndBenefitAnalysis();
					System.out.println("\nSorry, this function is not implement yet.");
					break;
				case 2:
					ts.assignTask(staff);
					break;
				case 3:
					ts.checkTasks(staff);
					break;
				default:
					System.out.println("Wrong function, please choose again:\n");
					break;
				}
				
			}
			else{
				System.out.println("\nSorry you have no permission for this function, please choose again:");
				
			}
		}
		
		
		
		
		
	}
	
	
	
}
