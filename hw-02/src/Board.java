// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int [] widths;
	private int [] heights;
	private int maxHeight;

	private boolean [][] bGrid;
	private int [] bWidths;
	private int [] bHeights;
	private int bMaxHeight;
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		if(width <= 0 || height <= 0){ throw new IllegalArgumentException("Given width or height isn't valid."); }
		this.width = width;
		this.height = height;
		this.maxHeight = 0;
		grid = new boolean[height][width];
		committed = true;
		widths = new int[height];
		heights = new int[width];
		bWidths = new int [height];
		bHeights = new int [width];
		bGrid = new boolean[height][width];
		bMaxHeight = 0;
		// YOUR CODE HERE
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		return maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			int maxHeight = 0;
			int [] heights = new int [width];

			for(int row = 0; row < height; row++){
				int blocksInRow = 0;

				for(int column = 0; column < width; column++){
					if(grid[row][column]) {
						blocksInRow += 1;
						heights[column] = row + 1;
						if(maxHeight < row + 1)
							maxHeight = row + 1;
					}
				}

				if(blocksInRow != widths[row])
					throw new RuntimeException("Blocks in row " + row + " is not valid.");
			}


			if(maxHeight != this.maxHeight)
				throw new RuntimeException("Max height is not valid");

			if(!Arrays.equals(heights, this.heights))
				throw new RuntimeException("Column heights are not valid");
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		// YOUR CODE HERE
		int maxY = Integer.MIN_VALUE;
		int distOfMaxYFromOrigin = 0;
		int [] skirt = piece.getSkirt();
		for (int k = 0; k < skirt.length; k++){
			if(x + k >= getWidth() || x + k < 0) continue;
			int currY = getColumnHeight(x + k);
			if(currY > maxY) {
				distOfMaxYFromOrigin = k;
				maxY = currY;
			}else if(currY == maxY && skirt[distOfMaxYFromOrigin] > skirt[k]){
				distOfMaxYFromOrigin = k;
				maxY = currY;
			}
		}
		return maxY - skirt[distOfMaxYFromOrigin];
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		if(x < 0 || x >= getWidth())
			throw new IllegalArgumentException("Given row index is not valid.");
		return heights[x]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		if(y < 0 || y >= getHeight())
			throw new IllegalArgumentException("Given column index is not valid");
		return widths[y]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x >= getWidth() || y >= getHeight())
			return true;
		return this.grid[y][x]; // YOUR CODE HERE
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;



	private boolean inBounds(int x, int y){
		return (x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight());
	}


	/**
	 * backup all essential attributes.
	 */
	private void backup(){
		if(committed) {
			System.arraycopy(widths, 0, bWidths, 0, widths.length);
			System.arraycopy(heights, 0, bHeights, 0, heights.length);

			for (int k = 0; k < height; k++) {
				System.arraycopy(grid[k], 0, bGrid[k], 0, width);
			}

			bMaxHeight = maxHeight;
		}
	}


	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem



		if(!inBounds(x, y))
			return PLACE_OUT_BOUNDS;

		if (!committed) throw new RuntimeException("place commit problem");

		backup();

		committed = false;
		int result = PLACE_OK;
		TPoint [] pieceBody = piece.getBody();
		for(int k = 0; k < pieceBody.length; k++){
			int place_x = x + pieceBody[k].x;
			int place_y = y + pieceBody[k].y;
			if(!inBounds(place_x, place_y)) {
				sanityCheck();
				return PLACE_OUT_BOUNDS;
			}
			else if(getGrid(place_x, place_y)) {
				return PLACE_BAD;
			}
			this.grid[place_y][place_x] = true;
			widths[place_y]++;
			if(widths[place_y] == getWidth()){
				result = PLACE_ROW_FILLED;
			}
			if(heights[place_x] < place_y + 1)heights[place_x] = place_y + 1;
			if(heights[place_x] > maxHeight) maxHeight = heights[place_x];
		}


		sanityCheck();
		return result;
	}

	private void changeHeights(){
		Arrays.fill(heights, 0);
		for(int row = 0; row < maxHeight; row++){
			for(int column = 0; column < width; column++){
				if(grid[row][column]){
					heights[column] = row + 1;
				}
			}
		}
	}

	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {

		backup();


		int rowsCleared = 0;
		boolean [] copied_rows = new boolean[height];

		int tmp_ind = 0;


		while(tmp_ind < maxHeight){
			while(tmp_ind < maxHeight && !(widths[tmp_ind] == width || copied_rows[tmp_ind]))
				tmp_ind++;
			if(tmp_ind == maxHeight) break;
			int to = tmp_ind;
			int from = tmp_ind + 1;
			while(from < maxHeight && (widths[from] == width || copied_rows[from]))
				from++;
			if(from == maxHeight) break;
			if(widths[tmp_ind] == width)
				rowsCleared++;
			copied_rows[from] = true;
			System.arraycopy(grid[from],0, grid[to],0, width);
			widths[to] = widths[from];
			tmp_ind++;
		}

		while(tmp_ind < maxHeight){
			boolean [] tmp_row = new boolean[width];
			System.arraycopy(tmp_row ,0, grid[tmp_ind],0, width);
			if(widths[tmp_ind] == width)
				rowsCleared++;
			widths[tmp_ind] = 0;
			tmp_ind++;
		}


		maxHeight -= rowsCleared;

		changeHeights();

		committed = false;
		sanityCheck();
		return rowsCleared;
	}





	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		if(!committed){
			int [] tmp_width = bWidths;
			bWidths = widths;
			widths = tmp_width;

			int [] tmp_height = heights;
			heights = bHeights;
			bHeights = tmp_height;

			boolean [] [] tmp = grid;
			grid = bGrid;
			bGrid = tmp;

			maxHeight = bMaxHeight;
			committed = true;
			sanityCheck();
		}
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


