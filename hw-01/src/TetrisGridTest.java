import junit.framework.TestCase;
import java.util.*;

public class TetrisGridTest extends TestCase {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false },
			{false, true, true }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testClear2(){
		boolean [][] before = {
				{true}
		};
		boolean [][] after = {
				{false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear3(){
		boolean [][] before ={
				{false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		boolean [][] after = {
				{false}
		};

		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear4(){
		boolean [][] before = {
				{true, false, true, true, false, true},
				{true, true, false, true, false, true},
				{true, false, false, true, false, true}
		};

		boolean [][] after = {
				{false, true, false, false, false, false},
				{true, false, false, false, false, false},
				{false, false, false, false, false, false}
		};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear5(){
		boolean [][] before = {
				{true, true, true, false},
				{true, true, true, false},
				{false, true, true, true},
				{false, true, true, true}
		};

		boolean [][] after = {
				{true, false, false, false},
				{true, false, false, false},
				{false, true, false, false},
				{false, true, false, false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear6(){
		boolean [][] before = {
				{true, false, true, false, false, true, false, false, true, true, false}
		};
		boolean [][] after = {
				{false, false, false, false, false, false, false, false, false, false, false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear7(){
		boolean [][] before = {
				{true},
				{true},
				{true},
				{true},
				{true},
				{true}
		};
		boolean [][] after = {
				{false},
				{false},
				{false},
				{false},
				{false},
				{false}
		};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue(Arrays.deepEquals(after, tetris.getGrid()));

	}
	public void testClear8(){
		boolean [][] before ={
				{true},
				{false},
				{true},
				{false},
				{false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		boolean [][] after = {
				{true},
				{false},
				{true},
				{false},
				{false}
		};
		assertTrue(Arrays.deepEquals(after, tetris.getGrid()));
	}


	int typeOfRow(boolean [][] grid, int column_ind){
		boolean isEmpty = true;
		boolean isFull = true;
		for(int k = 0; k < grid.length; k++){
			if(!grid[k][column_ind]){
				isFull = false;
			}
			else{
				isEmpty = false;
			}
		}
		if(isEmpty) return -1;
		if(isFull) return 1;
		return 0;
	}

	boolean [][] generateGrid(int row, int column){
		boolean [][] tmp = new boolean[row][column];
		Random rnd = new Random();
		for(int k = 0; k < row; k++){
			for(int j = 0; j < column; j++){
				int r = rnd.nextInt(4000);
				if(r == 0) {
					tmp[k][j] = false;
					continue;
				}
				tmp[k][j] = true;
			}
		}
		return tmp;
	}


	public void testClear9(){
		boolean [][] before = generateGrid(1000, 2000);
		int num_deleted = 0;
		int num_empty = 0;

		for(int k = 0; k < before[0].length; k++){
			if(typeOfRow(before, k) == -1){
				num_empty++;
			}else if(typeOfRow(before, k) == 1){
				num_deleted++;
			}
		}
		TetrisGrid tr = new TetrisGrid(before);
		tr.clearRows();
		boolean [][] after = tr.getGrid();
		int num_empty_after = 0;

		for(int k = before[0].length - 1; k >= 0; k--){
			if(typeOfRow(after, k) == 0){
				break;
			}
			num_empty_after++;
		}
		assertEquals(num_empty + num_deleted, num_empty_after);
	}




}
