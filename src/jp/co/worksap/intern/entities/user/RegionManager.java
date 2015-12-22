package jp.co.worksap.intern.entities.user;

import java.util.ArrayList;
import java.util.List;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.service.analysis.BusinessAnalysis;
import jp.co.worksap.intern.service.process.TaskService;
import jp.co.worksap.intern.util.Utilities;



/** Region Manager service entry
 *  functions and security permission: 
 *             1.benefit region comparision analysis 			    (officer, expert)
 *             2.customer amount region comparision analysis        (officer, expert, senior, assistant)
 *             3.task assignment 						  		   (officer, expert, senior)
 *             4.task receive 											  (If have new task)
 *             0.Quit
 *             
 * @author fsc
 *
 */
public class RegionManager implements User{

	StaffDTO staff;
	TaskService ts;
	
	public RegionManager(StaffDTO staff) {
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
				"benefit region comparision analysis   	   (officer, expert)",
				"customer amount region comparision analysis    (officer, expert, senior, assistant)",
				"task assignment    			           (officer, expert, senior)"
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
				
				System.out.println("\nSorry, this function is not implement yet.");
				
			}
			else{
				System.out.println("\nSorry you have no permission for this function, please choose again:");
				
			}
		}
		
	}
	
}
