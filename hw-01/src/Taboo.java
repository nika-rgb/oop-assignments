
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	private Map <T, ArrayList<T> > rules;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		this.rules = new HashMap<T, ArrayList<T> >();
		for(int k = 0; k < rules.size() - 1; k++){
			T current = rules.get(k);
			T next = rules.get(k + 1);
			if(next == null || current == null) continue;
			ArrayList <T> n_follow;
			if(!this.rules.containsKey(current)){
				n_follow  = new ArrayList<T>();
			}else{
				n_follow = this.rules.get(current);
			}
			n_follow.add(next);
			this.rules.put(current, n_follow);
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(!this.rules.containsKey(elem)){
			return Collections.emptySet();
		}
		Set <T> result_set = new HashSet<>(this.rules.get(elem));
		return result_set; // YOUR CODE HERE
	}



	private boolean canReduce(T last_reducer, T current){
		if(!this.rules.containsKey(last_reducer))
			return false;
		for(T tmp: this.rules.get(last_reducer)){
			if(tmp.equals(current)) return true;
		}
		return false;
	}

	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @paramlist collection to reduce
	 */
	public void reduce(List<T> list) {
		if (list.size() != 0) {
			T last_reducer = list.get(0);
			int index = 1;
			while (index < list.size()) {
				if (canReduce(last_reducer, list.get(index))) {
					list.remove(index);
				} else {
					last_reducer = list.get(index);
					index++;
				}
			}
		}
	}
}
