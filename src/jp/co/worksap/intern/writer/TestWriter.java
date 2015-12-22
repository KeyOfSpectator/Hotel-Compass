package jp.co.worksap.intern.writer;

import java.util.ArrayList;
import java.util.List;

/** Test Writer
 * @author fsc
 *
 */
public class TestWriter {

	private static String[] colStr = {"col1" , "col2" , "col3"};
	private static String[] row1 = {"row11" , "row12" , "row13"};
	private static String[] row2 = {"row21" , "row22" , "row23"};
	
	public static void main(String[] args) {
		
		List<String[]> test_str = new ArrayList<String[]>();
		test_str.add(colStr);
		test_str.add(row1);
		test_str.add(row2);
		
		ResultWriterImpl rw = new ResultWriterImpl();
		rw.writeResult(test_str);
	}

}
