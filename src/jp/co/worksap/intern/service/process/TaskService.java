package jp.co.worksap.intern.service.process;

import java.util.ArrayList;
import java.util.List;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.task.TaskDTO;
import jp.co.worksap.intern.util.Utilities;


public class TaskService {
	
	private static List<TaskDTO> taskList;
	private static final TaskService TaskService = new TaskService();
	public static TaskService getInstance(){
		return TaskService;
	}
	
	private TaskService(){
		taskList = new ArrayList<TaskDTO>();
	}

	public void addTaskfuncitons(List<String> funcs, StaffDTO staff) {
//		funcs.add("Assign a task");
		if(haveTask(staff.getStaffId())){
			funcs.add("You have a new task , plese check.");
		}
	}


	public void assignTask(StaffDTO staff){
		TaskDTO task = Utilities.readTask(staff);
		
		taskList.add(task);
	}
	
	public void checkTasks(StaffDTO staff){
		List<TaskDTO> l = getTaskByID(staff.getStaffId());
		System.out.println("Your tasks:");
		int ind = 1;
		for(TaskDTO tdto : l){
			System.out.println(ind + ". Title:" + tdto.getTaskTitle() + "    AssignerID:" + tdto.getAssignerId() + "   DeadLine:" + tdto.getEndTime());
			ind++;
		}
	} 
	
	
	private boolean haveTask(Long id) {
		for(TaskDTO tdto : taskList){
			if(tdto.getExcuterId() == id)
				return true;
		}
		return false;
	}
	
	private List<TaskDTO> getTaskByID(Long id) {
		List<TaskDTO> ret = new ArrayList<TaskDTO>();
		for(TaskDTO tdto : taskList){
			if(tdto.getExcuterId() == id)
				ret.add(tdto);
		}
		return ret;
	}

	
}
