
import junit.framework.TestCase;


import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}

	public void testSameCount3() {
		List <String> a = Arrays.asList("abc", "bcd", "jki", "acdhdsfsu", "abc", "acdhdsfsu", "bcd", "sawe");
		assertEquals(2, Appearances.sameCount(a, Arrays.asList("", "bcd", "jki", "abc", "bcd", "yrr")));
		assertEquals(5, Appearances.sameCount(a, a));
		assertEquals(0, Appearances.sameCount(a, Arrays.asList("abc", "abc", "abc")));
	}

	public void testSameCount4(){
		List <String> a = Arrays.asList("");
		assertEquals(1, Appearances.sameCount(a, Arrays.asList("")));
		List < List <Integer> > b = Arrays.asList(Arrays.asList(5, 7, 9, 64, 59),Arrays.asList(89, 5, 458, 1335));
		assertEquals(0, Appearances.sameCount(b, Arrays.asList(Arrays.asList(5, 8, 88, 298, 12))));
		assertEquals(1, Appearances.sameCount(b, Arrays.asList(Arrays.asList(5, 7, 9, 64, 59))));
	}

	public void testSameCount5(){
		//empty lists
		List <String> a = new ArrayList<String>();
		List <String> b = new ArrayList<String>();
		assertEquals(0, Appearances.sameCount(a, b));
		//evolve technique
		a.add("aaab");
		assertEquals(0, Appearances.sameCount(a, b));
		b.add("cd");
		assertEquals(0, Appearances.sameCount(a, b));
		b.add("aaab");
		assertEquals(1, Appearances.sameCount(a, b));
		b.add("aaab");
		assertEquals(0, Appearances.sameCount(a, b));
		a.add("aaab");
		a.add("iodspd");
		assertEquals(1, Appearances.sameCount(a, b));
		a.add("cd");
		assertEquals(2, Appearances.sameCount(a, b));
	}

	private List <String> generateList(int length){
		List <String> lst = new ArrayList<String>();
		Random rnd = new Random();
		for(int k = 0; k < length; k++){
			int rnd_number = rnd.nextInt(9);
			char curr = (char)('0' + rnd_number);
			lst.add(curr + "");
		}
		return lst;
	}

	int getNumSames(List <String> first, List <String> second){
		int [] frequency = new int [10];
		Arrays.fill(frequency, -1);
		for(int k = 0; k < first.size(); k++) {
			if(frequency[(first.get(k).charAt(0) - '0')] == -1){
				frequency[(first.get(k).charAt(0) - '0')] = 0;
			}
			frequency[(first.get(k).charAt(0) - '0')]++;
		}
		for(int k = 0; k < second.size(); k++)
			frequency[(second.get(k).charAt(0)) - '0']--;
		int result = 0;
		for(int k = 0; k < frequency.length; k++){
			if(frequency[k] == 0)
				result++;
		}
		return result;
	}


	public void testSameCount6(){
		for(int k = 0; k < 100; k++) {
			List<String> first = generateList(50000);
			List<String> second = generateList(50000);
			assertEquals(getNumSames(first, second), Appearances.sameCount(first, second));
		}

	}
}
