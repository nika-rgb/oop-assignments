package Sudoku;

import com.sun.glass.ui.Size;
import javafx.util.Pair;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.

	//instance variables
	private Spot [][] baseGrid; // grid witch stores main grid
 	private String solutionString; // string witch contains solution string
 	private int solveCounter; // variable witch contains number of solutions with given grid.
	private long elapsed;
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
			"3 7 0 0 0 0 0 8 0",
			"0 0 1 0 9 3 0 0 0",
			"0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2",
			"0 0 0 0 4 0 0 0 0",
			"5 2 0 0 0 6 7 9 0",
			"6 0 0 0 2 1 0 4 0",
			"0 0 0 5 3 0 9 0 0",
			"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}




	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
		sudoku.solve();
	}


	/**
	 * class witch stores single spot in the grid
	 */

	public class Spot{
		//spot class instance variables
		private int row;
		private int col;
		private int val;
		public Spot(int row, int col, int value){
			if((row < 0 || row > SIZE) || (col < 0 || col > SIZE))
				throw new invalidSudokuBoardCoordinateException("Invalid coordinate row: " + row + ", Column: " + col + " .");
			if(value < 0 || value > 9)
				throw new invalidSudokuValueException("Invalid Value value: " + value + ".(value must be between 0 and 9)");
			this.row = row;
			this.col = col;
			this.val = value;
		}


		public int getValue(){ return val;}

		/**
		 * method sets new value.
		 * @param newValue
		 */
		public void setValue(int newValue){
			if(newValue < 0 || newValue > 9)
				throw new invalidSudokuValueException("Invalid Value value: " + newValue + ". (value must be between 0 and 9)");
			val = newValue;
		}

		/**
		 * returns row in witch this spot is located
		 * @return row
		 */
		public int getRow(){
			return row;
		}

		/**
		 * returns column in witch this spot is located
 		 * @return col
		 */
		public int getCol() {
			return col;
		}

		@Override
		public String toString(){
			return "row: " + row + ", column: " + col + ", value: " + val;
		}

	}


	/**
	 * method initializes instance variables of Sudoku class.
	 * @param ints
	 */
	private void initialize(int [][] ints){
		baseGrid = new Spot [SIZE][SIZE];
		for(int k = 0; k < SIZE; k++){
			for(int j = 0; j < SIZE; j++){
				baseGrid[k][j] = createNewSpot(k, j, ints[k][j]);
			}
		}
		solutionString = null;
		solveCounter = 0;
		elapsed = 0;
	}

	
	

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		initialize(ints);
	}


	public Sudoku(String text){
		this(textToGrid(text));
	}

	/**
	 * factory method to create inner spot class
	*/
	public Spot createNewSpot(int row, int col, int value){
		return new Spot(row, col, value);
	}


	/**
	 * returns boolean array arr[k] is true if value k is vacant in given column
	 * @param col
	 * @return
	 */
	private boolean [] freeInColumn(int col){
		boolean [] freeInCol = new boolean[SIZE + 1];
		Arrays.fill(freeInCol, true);
		for(int k = 0; k < SIZE; k++){
			int currVal = baseGrid[k][col].getValue();
			if(currVal != 0) {
				if(!freeInCol[currVal]) return new boolean[0];
				freeInCol[currVal] = false;
			}

		}
		return freeInCol;

	}

	/**
	 * returns boolean array arr[k] is true if value k is vacant in given row
	 * @param row
	 * @return
	 */
	private boolean [] freeInRow(int row){
		boolean [] freeInRow = new boolean[SIZE + 1];
		Arrays.fill(freeInRow, true);
		for(int k = 0; k < SIZE; k++){
			int currVal = baseGrid[row][k].getValue();
			if(currVal != 0) {
				if(!freeInRow[currVal]) return new boolean[0];
				freeInRow[currVal] = false;
			}

		}
		return freeInRow;
	}

	/**
	 * returns returns boolean array arr[k] is true if value k is vacant in given square.
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean [] freeInSquare(int row, int col){
		row = (row / PART) * PART;
		col = (col / PART) * PART;
		int squareWidth = PART;
		int squareHeight = PART;
		boolean [] freeInSq = new boolean[SIZE + 1];
		Arrays.fill(freeInSq, true);
		for(int k = 0; k < squareHeight; k++){
			for(int j = 0; j < squareWidth; j++){
				int currVal = baseGrid[row + k][col + j].getValue();
				if(currVal != 0){
					if(!freeInSq[currVal]) return new boolean[0];
					freeInSq[currVal] = false;
				}


			}
		}
		return freeInSq;
	}


	/**
	 * Function takes as parameter current state of each row, each grid and each square eachRow[i][j] contains if in ith row j can be set as value in arbitrary spot.
	 * same for each row, and same for each square.
	 * @param eachRow
	 * @param eachCol
	 * @param eachSq
	 * @param currRow
	 * @param currCol
	 * @return
	 */
	private int getNumFreeValues(boolean [][] eachRow, boolean [][] eachCol, boolean [][] eachSq, int currRow, int currCol){
		int result = 0;
		int squareInd = (currRow /PART) * PART + (currCol / PART); // index of square.
		for (int k = 1; k < SIZE + 1; k++) {
			if(eachRow[currRow][k] && eachCol[currCol][k] && eachSq[squareInd][k])
				result++;
		}
		return result;
	}


	private void setInfo(ArrayList < Pair <Spot, Integer> > gridInfo, boolean [][] eachRow, boolean [][] eachCol, boolean [][] eachSquare){
		for(int k = 0 ; k < SIZE; k++){
			for(int j = 0; j < SIZE; j++){
				if(baseGrid[k][j].getValue() == 0){
					int numFreeValues = getNumFreeValues(eachRow, eachCol, eachSquare, k, j);
					gridInfo.add(new Pair<>(baseGrid[k][j], numFreeValues));
				}
			}
		}
	}



	/**
	 * Function returns array witch contains pairs, each pairs' key is spot which is free in base grid and value is amount of values witch this spot can take.
	 * @return
	 */
	private void analyzeGrid(ArrayList < Pair <Spot, Integer> > gridInfo){
		boolean [][] eachRow = new boolean[SIZE][];
		boolean [][] eachCol = new boolean[SIZE][];
		boolean [][] eachSq = new boolean[SIZE][];

		int currRow = 0;
		int currCol = 0;
		for(int k = 0; k < SIZE; k++){
			eachRow[k] = freeInRow(k);
			if(eachRow[k].length == 0) return;
			eachCol[k] = freeInColumn(k);
			if(eachCol[k].length == 0) return;
			eachSq[k] = freeInSquare(currRow, currCol);
			if(eachSq[k].length == 0) return;
			currRow += PART;
			if(k % PART == 2 && k != 0) {
				currCol += 3;
				currRow = 0;
			}
		}

		setInfo(gridInfo, eachRow, eachCol, eachSq);
	}


	private void solvePuzzle(ArrayList < Pair <Spot, Integer> > gridInfo, int currIndex){
		if(gridInfo.size() == currIndex) {
			solveCounter++;
			if(solutionString.equals(""))
				solutionString = gridToString(baseGrid);
			return;
		}

		Pair <Spot, Integer> current = gridInfo.get(currIndex);

		Spot currentSpot = current.getKey();

		boolean [] freeInSq = freeInSquare(currentSpot.getRow(), currentSpot.getCol());
		boolean [] freeInRow = freeInRow(currentSpot.getRow());
		boolean [] freeInCol = freeInColumn(currentSpot.getCol());


		for(int k = 1; k < SIZE + 1; k++) {
			if (freeInSq[k] && freeInRow[k] && freeInCol[k]) {
				currentSpot.setValue(k);
				solvePuzzle(gridInfo, currIndex + 1);
				currentSpot.setValue(0);
				if (solveCounter >= MAX_SOLUTIONS) return;
			}
		}
	}



	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		if(solutionString == null){
			//solve puzzle
			long startTime = System.currentTimeMillis();
			solutionString = "";
			ArrayList < Pair <Spot, Integer> > gridInfo = new ArrayList<>();
			analyzeGrid(gridInfo);
			if(gridInfo.isEmpty()){
				solveCounter = 0;
				return solveCounter;
			}
			gridInfo.sort(Comparator.comparingInt(Pair::getValue));

			int currIndex = 0;
			solvePuzzle(gridInfo, currIndex);
			long endTime = System.currentTimeMillis();
			elapsed = endTime - startTime;
		}
		return solveCounter;
	}
	
	public String getSolutionText() {
		if(solutionString == null)
			throw new illegalSudokuOperationException("getSolutionText method can't be called before calling method solve");
		return solutionString; // YOUR CODE HERE
	}
	
	public long getElapsed() {
		if(solutionString == null)
			throw new illegalSudokuOperationException("getElapsed method can't be called before calling method solve");
		return elapsed; // YOUR CODE HERE
	}


	private String gridToString(Spot [][] grid){
		StringBuilder strBuilder = new StringBuilder();
		for(int k = 0; k < SIZE; k++){
			for(int j = 0; j < SIZE - 1; j++){
				strBuilder.append("" + grid[k][j].getValue() + " ");
			}
			strBuilder.append("" + grid[k][SIZE - 1].getValue() + "\n");
		}
		return strBuilder.toString();
	}


	@Override
	public String toString(){ return gridToString(baseGrid); }
}
