// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.


import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import junit.framework.TestCase;

import java.util.Random;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}
	
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}

	public void testBlowup4(){
		//no Digits
		assertEquals("abjsfiefmrefiefmrgj", StringCode.blowup("abjsfiefmrefiefmrgj"));
		//only one digit
		assertEquals("aa", StringCode.blowup("1a"));
		// only digits also digit at end
		assertEquals("1111111111", StringCode.blowup("011111111111"));
		// 0 and digit at and
		assertEquals("", StringCode.blowup("02"));
	}

	private String generateRandomStr(int length){
		Random rnd = new Random();
		String random_str = "";
		for(int k = 0; k < length; k++){
			int rand_int = rnd.nextInt(4);
			if(rand_int == 1){
				int curr_digit = rnd.nextInt(9);
				random_str += (char)('0' + curr_digit);
				continue;
			}
			int rand_char = rnd.nextInt(25);
			random_str += (char)('a' + rand_char);
			//System.out.println(random_str.charAt(random_str.length() - 1));
		}
		return random_str;
	}

	private int calculateDiff(String current){
		int diff = 0;
		for(int k = 0; k < current.length() - 1; k++){
			if(current.charAt(k) >= '0' && current.charAt(k) <= '9'){
				int curr = current.charAt(k) - '0';
				diff += (curr - 1);
			}
		}
		char last_char = current.charAt(current.length() - 1);
		if(last_char >= '0' && last_char <= '9'){
			diff--;
		}
		return diff;
	}

	//This test generates random string and then testes method for its length
	public void testBlowup5(){
		//Test for big Strings
		for(int k = 0; k < 5; k++) {
			String current = generateRandomStr(10000);
			int diff_between_lengths = calculateDiff(current);
			assertEquals(current.length() + diff_between_lengths, StringCode.blowup(current).length());
		}

		for(int k = 0; k < 2; k++){
			String current = generateRandomStr(50000);
			int diff_between_lengths = calculateDiff(current);
			assertEquals(current.length() + diff_between_lengths, StringCode.blowup(current).length());
		}

	}
	
	
	//
	// maxRun
	//
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}
	
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));

	}

	public void testRun4(){
		assertEquals(1, StringCode.maxRun("1"));
		assertEquals(6, StringCode.maxRun("111222222"));
		assertEquals(55, StringCode.maxRun("1111111111111111111111111111111111111111111111111111111"));
	}



	// Need test cases for stringIntersect
	public void testIntersect1(){
		//basic test no same symbols
		assertFalse(StringCode.stringIntersect("aaaaa", "b", 1));
		//having equal substring
		assertTrue(StringCode.stringIntersect("ababs", "abs", 3));
		assertTrue(StringCode.stringIntersect("aboiksafdsfjsdfusdf9o", "asdfasdfasdsafdsfdskfsdjfej", 5));
		assertTrue(StringCode.stringIntersect("asdnasjdnkasdksabdbshdbsdjkfb", "asdrsdfrgbsdjkfblsdfoem", 7));
		//not having equal symbols and length is equal to length of oth string
		assertFalse(StringCode.stringIntersect("aaaaa", "bbbbb", 6));

		//having equal substring but length is different from len
		assertFalse(StringCode.stringIntersect("abcdght", "cdgoip", 5));
	}

	public void testIntersect2(){
		//length higher than second string
		assertFalse(StringCode.stringIntersect("abhfthg", "aas", 4));
		//length higher than first string
		assertFalse(StringCode.stringIntersect("abgf", "abvhfdsur", 5));
		//length higher than both string
		assertFalse(StringCode.stringIntersect("aaa", "bv", 5));
		//no equal symbols, medium length
		assertFalse(StringCode.stringIntersect("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 1));
		//both string are equal and also length is equal to string's length.
		assertTrue(StringCode.stringIntersect("avcx", "avcx", 4));

	}


	public void testIntersect3(){
		//medium length having equal substring
		assertTrue(StringCode.stringIntersect("abchdiuytodkrpatr-=57495012sd,z.ae,qkdfgmcckd,;dsfg'a][[rewerwfdsvvda[w7323rsdfsdfsdv vjo sdlwof,djdskdfmnfeewjsdmfdjfs,msdmfmsmdmdmbifrd56868", "kmppoiuy87769876./.'[]98-=756'jnb,. vjo sdl ps;lsceirw37202340", 8));
		//medium length not having equal substring of expected size
		assertFalse(StringCode.stringIntersect(".............99000000099990-()()))))))(()())())()()())()))(3434324234234234234324", "asdaswer()())())(ccsdftttg)h", 13));
		//first string ""
		assertFalse(StringCode.stringIntersect("", "aaaaasdsds", 2));
		//second String ""
		assertFalse(StringCode.stringIntersect("sadadsda", "", 6));
		//both String ""
		assertFalse(StringCode.stringIntersect("", "", 8));
	}

	public void testIntersect4(){
		//first string prefix of second
		assertTrue(StringCode.stringIntersect("oop", "oopfourthsemester", 3));
		//second suffix of first
		assertTrue(StringCode.stringIntersect("giveup", "up", 2));
		//first big string second low length
		assertTrue(StringCode.stringIntersect("abcdhfgueurfifssjewrowermwerjefmergfoiergermgkerojuahjasgbdjweoidfrepfrpoferpofqwhjqwbjqwbjdewfpoevpoefvw,ndwebjdqwkdjwedwefpoerfpoemsdcmsdcjkwedmwefjwefjkervbhjweflerrvbhj,wdlefvbhkwkbjqwfiuwefkef,gfkj.aefewhlfwejkf.lWEFJEWFLJEWFwfoewfihoifhhdsfmnsdfmnsdflnsdfiohsdofhsdiohfoisdhfojdsfdsm,nfsdivhsdhcsdnfm,ndsm,fnsdlchuiodshciosdklfnwem,snr,ewnfsdchsiduochosdcks,nerlneklfncsduichushcjkanf,mwnlkfmskljciopdsjfm.r,qmlkndfisdgyucgsiehnrnruegwgehgwhjgedwidheifhjwokkhjkbwdvbwdvwvsiduckjewfciueshncjkesnckjsdnjkcnskncjdsncjkdsncjkdsncjndsocnerojioperjgoprkv[okjpnOFNOEJ;GEPAJGALJGKL;DFJKLGVJDFKLVNFDNVUIODFNVOINOnoinoinoinionubtuhkbjnhgyuvhigvuhgvuhbivguvtfvuvutbhgvuhgfhjnogujnogbhivgcdrcfdrytu",
				"asdjsadmcidweifkvjwedwefdsfdsviv fii", 6));
	}
}
