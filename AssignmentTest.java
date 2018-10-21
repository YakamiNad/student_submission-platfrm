import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.*;
import java.util.Arrays;
import java.util.Collections;
import org.junit.rules.ExpectedException;


public class AssignmentTest{
	public ExpectedException thrown = ExpectedException.none();

	// This will make it a bit easier for us to make Date objects
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static Date getDate(String s) {
		try {
			return df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("The test case is broken, invalid SimpleDateFormat parse");
		}
		// unreachable
		return null;
	}
	
	private static void testHelperEquals(String unikey, Date timestamp, Integer grade, Submission actual) {
		assertEquals(unikey, actual.getUnikey());
		assertEquals(timestamp, actual.getTime());
		assertEquals(grade, actual.getGrade());
	}
	//assertEqals(grade,actual.getTime());
	//assertEquals(grade,actual.getTime().lastKey());
	
	// helper method that adds a new appointment AND checks the return value is correct
	private static Submission testHelperAdd(SubmissionHistory history, String unikey, Date timestamp, Integer grade) {
		Submission s = history.add(unikey, timestamp, grade);
		testHelperEquals(unikey, timestamp, grade, s);
		return s;
	}
	private SubmissionHistory buildTinyExample() {
		SubmissionHistory history = new Assignment();
		// submission A:
		history.add("aaaa1234", getDate("2016/09/03 09:00:00"), 68);
		// submission B:
		history.add("aaaa1234", getDate("2016/09/03 16:00:00"), 66);
		// submission C:
		history.add("cccc1234", getDate("2016/09/03 16:00:00"), 73);
		// submission D:
		history.add("aaaa1234", getDate("2016/09/03 18:00:00"), 68);
		//submission E:
		history.add("cccc1234", getDate("2016/09/04 16:00:00"), 74);
		return history;
	}
	@Test(timeout = 100)
	public void testExample2() {
		SubmissionHistory history = buildTinyExample();
		// As the first studnet's mark is the best mark,test whether his first mark will be overwritten;
		Integer example1 = history.getBestGrade("aaaa1234");
		assertEquals(new Integer(68), example1);
		//test the students who didn't exist;
		Integer examplenull = history.getBestGrade("zzzz1234");
		assertNull(examplenull);
	}
	//test more complex submission sets
	@Test(timeout = 100)
	public void moreRemovecases(){
		SubmissionHistory history = new Assignment();
			
			Submission b = testHelperAdd(history, "aaaa1111", new Date(4000000), 10);
			Submission j = testHelperAdd(history, "aaaa1111", new Date(4000001), 68);
			Submission c = testHelperAdd(history, "bbbb1111", new Date(600000), 68);
			Submission e = testHelperAdd(history, "aaaa1111", new Date(1000), 40);
			Submission a = testHelperAdd(history, "aaaa1111", new Date(200000), 56);
			Submission h = testHelperAdd(history, "bbbb1111", new Date(1600000), 23);
			Submission f = testHelperAdd(history, "aaaa1111", new Date(1200000), 80);
			Submission d = testHelperAdd(history, "aaaa1111", new Date(800000), 23);
			Submission g = testHelperAdd(history, "bbbb1111", new Date(1400000), 40);
			Submission i = testHelperAdd(history, "aaaa1111", new Date(1800000), 90);

			assertEquals(new Integer(90), history.getBestGrade("aaaa1111"));
			assertEquals(new Integer(68), history.getBestGrade("bbbb1111"));

			history.remove(i);
			assertEquals(new Integer(80), history.getBestGrade("aaaa1111"));
			assertEquals(new Integer(68), history.getBestGrade("bbbb1111"));

			history.remove(f);
			assertEquals(new Integer(68), history.getBestGrade("aaaa1111"));
			assertEquals(new Integer(68), history.getBestGrade("bbbb1111"));

			history.remove(c);
			assertEquals(new Integer(68), history.getBestGrade("aaaa1111"));
			assertEquals(new Integer(40), history.getBestGrade("bbbb1111"));

			history.remove(j);
			assertEquals(new Integer(68), history.getBestGrade("aaaa1111"));
			assertEquals(new Integer(40), history.getBestGrade("bbbb1111"));
			
			
		}
	@Test(timeout = 100)
	public void testMoreTopStudents() {
		SubmissionHistory history = new Assignment();
		Submission a1 = testHelperAdd(history, "a", new Date(100000), 10);
		Submission b1 = testHelperAdd(history, "b", new Date(100000), 10);
		Submission c1 = testHelperAdd(history, "c", new Date(100000), 10);
		Submission d1 = testHelperAdd(history, "d", new Date(100000), 10);
		Submission e1 = testHelperAdd(history, "e", new Date(100000), 10);
		Submission f1 = testHelperAdd(history, "f", new Date(100000), 15); //best

		Submission a2 = testHelperAdd(history, "a", new Date(200000), 10);
		Submission b2 = testHelperAdd(history, "b", new Date(200000), 50);
		Submission c2 = testHelperAdd(history, "c", new Date(200000), 5);
		Submission d2 = testHelperAdd(history, "d", new Date(200000), 15); //best
		Submission e2 = testHelperAdd(history, "e", new Date(200000), 15); //best
		Submission f2 = testHelperAdd(history, "f", new Date(200000), 5);
		
		Submission a3= testHelperAdd(history, "a", new Date(150000), 10);
		Submission b3 = testHelperAdd(history, "b", new Date(250000), 25);
		Submission c3 = testHelperAdd(history, "c", new Date(250000), 5);
		Submission d3 = testHelperAdd(history, "d", new Date(250000), 7); //best
		Submission e3 = testHelperAdd(history, "e", new Date(250000), 15); //best
		Submission f3 = testHelperAdd(history, "f", new Date(250000), 2);
		
		List<String> studentsExpected = Arrays.asList("b","c","f","d");
		List<String> studentsActual = history.listRegressions();
		
		//sort both lists, to make it easier to compare them
		Collections.sort(studentsExpected);
		Collections.sort(studentsActual);

		assertEquals(studentsExpected, studentsActual);
		
	}
		
		
	}
            

