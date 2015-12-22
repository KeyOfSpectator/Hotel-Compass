package jp.co.worksap.intern.entities.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class TaskDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3711231132225385346L;

	private Long taskId;
	private Long assignerId;
	private String taskTitle;
	private Long excuterId;
	private Date startTime;
	private Date endTime;
	
	
	public TaskDTO(Long taskId, Long assignerId, String taskTitle,
			Long excuterId, Date startTime, Date endTime) {
		super();
		this.taskId = taskId;
		this.assignerId = assignerId;
		this.taskTitle = taskTitle;
		this.excuterId = excuterId;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public Long getTaskId() {
		return taskId;
	}


	public Long getAssignerId() {
		return assignerId;
	}


	public String getTaskTitle() {
		return taskTitle;
	}


	public Long getExcuterId() {
		return excuterId;
	}


	public Date getStartTime() {
		return startTime;
	}


	public Date getEndTime() {
		return endTime;
	}
	
	
	
}
