//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

import java.util.Arrays;

public class TetrisGrid {
	private boolean [][] grid;

	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */

	private void constructNew(boolean [][] grid){
		this.grid = new boolean[grid.length][grid[0].length];
		for(int k = 0; k < grid.length; k++){
			this.grid[k] = Arrays.copyOf(grid[k], grid[k].length);
		}
	}

	public TetrisGrid(boolean[][] grid) { constructNew(grid); }

	private boolean mustClear(int y){
		for(int x = 0; x < grid.length; x++){
			if(!this.grid[x][y]) return false;
		}
		return true;
	}

	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		int y = 0;
		boolean [][] new_grid = new boolean[this.grid.length][this.grid[0].length];
		int tmp_y = 0;
		while(y != this.grid[0].length){
			if(!mustClear(y)) {
				for (int x = 0; x < this.grid.length; x++) {
					new_grid[x][tmp_y] = this.grid[x][y];
				}
				tmp_y++;
			}
			y++;
		}
		for( ; tmp_y < this.grid[0].length; tmp_y++){

			for(int x = 0; x < this.grid.length; x++){
				new_grid[x][tmp_y] = false;
			}
		}
		this.grid = new_grid;
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() { return grid; }
}
