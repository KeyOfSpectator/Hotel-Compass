package jp.co.worksap.intern.service.analysis;

public enum PeriodType {
	WEEKLY,MONTHLY,YEARLY;
	
	public String toString() {
		switch (this) {
		case WEEKLY:
			return "Weekly";
		case MONTHLY:
			return "Monthly";
		case YEARLY:
			return "Yearly";
		default:
			return ""; 
		}
	}
}
