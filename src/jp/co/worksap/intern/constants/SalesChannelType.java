package jp.co.worksap.intern.constants;

public enum SalesChannelType {
	TELEPHONE , TRAVEL_AGENT , COMPANY ,  WEBSITE , OTHER;
	
	public static SalesChannelType valueOfString(String src) {
		String raw = src.toLowerCase();
		if (raw.equals("telephone")) {
			return TELEPHONE;
		}

		if (raw.equals("travel agent")) {
			return TRAVEL_AGENT;
		}

		if (raw.equals("company")) {
			return COMPANY;
		}

		if (raw.equals("website")) {
			return WEBSITE;
		}
		
		if (raw.equals("other")) {
			return OTHER;
		}

		throw new IllegalArgumentException("Unknown Price Unit Type : " + raw);
	}
}
