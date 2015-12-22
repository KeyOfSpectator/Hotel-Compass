package jp.co.worksap.intern.entities.room;

public enum RoomType {
//	SINGLE, SEMIDOUBLE, DOUBLE;
	SINGLE,DOUBLE,SEMIDOUBLE,TWIN,TRIPLE,SUITE,OTHER;

	public String toString() {
		switch (this) {
		case SINGLE:
			return "SINGLE";
		case SEMIDOUBLE:
			return "SEMIDOUBLE";
		case DOUBLE:
			return "DOUBLE";
		case TWIN:
			return "TWIN";
		case TRIPLE:
			return "TRIPLE";
		case SUITE:
			return "SUITE";
		case OTHER:
			return "OTHER";
		default:
			return ""; 
		}
	}

	public static RoomType valueOfString(String src) {
		String raw = src.toLowerCase();
		if (raw.equals("double")) {
			return DOUBLE;
		}

		if (raw.equals("semidouble")) {
			return SEMIDOUBLE;
		}

		if (raw.equals("single")) {
			return SINGLE;
		}
		
		if (raw.equals("twin")) {
			return TWIN;
		}
		
		if (raw.equals("triple")) {
			return TRIPLE;
		}
		
		if (raw.equals("suite")) {
			return SUITE;
		}
		
		if (raw.equals("other")) {
			return OTHER;
		}

		throw new IllegalArgumentException("Unknown Room Type : " + raw);
	}
	
	Long getPrice(RoomType roomType){
		switch (roomType) {
		case SINGLE:
			return 230L;
		case DOUBLE:
			return 200L;
		case SEMIDOUBLE:
			return 190L;
		case TWIN:
			return 200L;
		case TRIPLE:
			return 210L;
		case SUITE:
			return 300L;
		case OTHER:
			return 100L;

		default:
			break;
		}
		return 100L;
	}
}
