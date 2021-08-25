import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		Map<T, Integer> counts = new HashMap<T, Integer>();
		Iterator<T> it_f = a.iterator();
		while(it_f.hasNext()){
			T next = it_f.next();
			if(counts.containsKey(next)){
				counts.put(next, counts.get(next) + 1);
			}else{
				counts.put(next, 1);
			}
		}

		Iterator <T>  it_s = b.iterator();
		while(it_s.hasNext()){
			T next = it_s.next();
			if(counts.containsKey(next)){
				counts.put(next, counts.get(next) - 1);
			}
		}
		int result = 0;
		for(T key: counts.keySet()){
			if(counts.get(key) == 0) result++;
		}
		return result;
	}
	
}
