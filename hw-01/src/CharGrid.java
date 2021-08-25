// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

import javax.management.remote.rmi._RMIConnection_Stub;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CharGrid {
	private char[][] grid;
	private Map<Character, Integer> smallest_areas;
	private int num_pluses;
	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	private void constructNew(char [][] grid){
		if(grid.length == 0){
			this.grid = new char[0][0];
		}else {
			this.grid = new char[grid.length][grid[0].length];
			for (int k = 0; k < grid.length; k++) {
				this.grid[k] = Arrays.copyOf(grid[k], grid[k].length);
			}
		}
	}


	private void countAreas(Map <Character, ArrayList<Integer> > edge_points){
		ArrayList <Integer> tmp;
		for(Character curr_char : edge_points.keySet()){
			tmp = edge_points.get(curr_char);
			int area = (tmp.get(2) - tmp.get(0) + 1) * (tmp.get(1) - tmp.get(3) + 1);
			this.smallest_areas.put(curr_char, area);
		}
	}

	private void precalculateAreas(){
		Map <Character, ArrayList<Integer> > edge_points = new HashMap<Character, ArrayList<Integer> >();
		for(int k = 0; k < this.grid.length; k++){
			for(int j = 0; j < this.grid[k].length; j++){
				char curr = this.grid[k][j];
				ArrayList <Integer> edges;
				if(edge_points.containsKey(curr)){
					edges = edge_points.get(curr);
					for(int i = 0; i < edges.size(); i++){
						if(k > edges.get(2))
							edges.set(2, k);
						if(j < edges.get(3))
							edges.set(3, j);
						else if (j > edges.get(1))
							edges.set(1, j);
					}
				}else{
					edges = new ArrayList<Integer>(4);
					edges.add(0, k);
					edges.add(1, j);
					edges.add(2, k);
					edges.add(3, j);

				}
				edge_points.put(curr, edges);
			}
		}
		countAreas(edge_points);
	}

	private boolean inBounds(int curr_row, int curr_col){
		return curr_row >= 0 && curr_row < this.grid.length && curr_col >= 0 && curr_col < this.grid[curr_row].length;
	}


	private int getLength(char current, int dir_row, int dir_col, int start_row, int start_col){
		int curr_row = start_row + dir_row;
		int curr_col = start_col + dir_col;
		if(!inBounds(curr_row, curr_col) || this.grid[curr_row][curr_col] != current) return 0;
		int length = 0;
		while(inBounds(curr_row, curr_col) && this.grid[curr_row][curr_col] == current){
			length++;
			curr_col += dir_col;
			curr_row += dir_row;
		}
		return length;
	}

	private void precalculatePluses(){
		int counter = 0;
		for(int k = 0; k < this.grid.length; k++){
			for(int j = 0; j < this.grid[k].length; j++){
				char current = this.grid[k][j];
				int north_l = getLength(current, -1, 0, k, j);
				if(north_l < 1)	continue;
				int south_l = getLength(current, 1, 0, k, j);
				if(north_l != south_l) continue;
				int west_l = getLength(current, 0, -1, k, j);
				if(west_l != south_l) continue;
				int east_l = getLength(current, 0, 1, k, j);
				if(east_l != west_l) continue;
				counter++;
			}
		}
		this.num_pluses = counter;
	}

	public CharGrid(char[][] grid) {
		constructNew(grid);
		this.smallest_areas = new HashMap<Character, Integer>();
		precalculateAreas();
		precalculatePluses();
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if(!this.smallest_areas.containsKey(ch)){
			return 0;
		}
		return this.smallest_areas.get(ch);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		return this.num_pluses;
	}
	
}
