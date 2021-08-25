import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.equals("")){
			return 0;
		}
		int max_run = -1;
		char current = str.charAt(0);
		int current_run = 1;
		for(int k = 1; k < str.length(); k++){
			if(str.charAt(k) == current){
				current_run++;
			}
			else{
				if(current_run > max_run){
					max_run = current_run;
				}
				current_run = 1;
				current = str.charAt(k);
			}
		}
		if(current_run > max_run) max_run = current_run;
		return max_run;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param //str
	 * @return blown up string
	 */

	private static boolean isDigit(char curr){
		if(curr >= '0' && curr <= '9') return true;
		return false;
	}
	public static String blowup(String str) {
		if(str.equals("")) return "";
		String result = "";
		for(int k = 0; k < str.length(); k++){
			if(isDigit(str.charAt(k))){
				if(k == str.length() - 1) continue;
				int num_repeates = (str.charAt(k) - '0');
				char next = str.charAt(k + 1);
				String append_str = "";
				for(int j = 0; j < num_repeates; j++){
					append_str += next;
				}
				result += append_str;
			}else{
				result += str.charAt(k);
			}
		}
		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if(b.length() < len || a.length() < len) return false;
		HashSet <String> hs = new HashSet<String>();
		String substr = a.substring(0, len);
		int e_index = len;
		while(e_index < a.length()){
			hs.add(substr);
			substr = substr.substring(1) + a.charAt(e_index);
			e_index++;
		}
		hs.add(substr);
		e_index = len;
		substr = b.substring(0, e_index);
		while(e_index < b.length()){
			if(hs.contains(substr)) return true;
			substr = substr.substring(1) + b.charAt(e_index);
			e_index++;
		}
		return hs.contains(substr);
	}
}
