
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

import java.util.Random;


public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
		assertEquals(0, cg.countPlus());
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
		assertEquals(0, cg.countPlus());
	}
	
	public void testCountPlus1(){
		char [][] grid = new char [][] {
					{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
					{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
					{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
					{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
					{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
					{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
					{' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '}
			};

		CharGrid cg = new CharGrid(grid);

		assertEquals(2, cg.countPlus());
		assertEquals(9, cg.charArea('z'));
		assertEquals(25, cg.charArea('p'));
		assertEquals(63, cg.charArea(' '));
		assertEquals(12, cg.charArea('y'));
	}

	public void testCountPlus2(){
		char [][] grid = new char [][]{
				{'+', ' ', '+', '+', '+', '+'},
				{' ', ' ', ' ', '+', '+', '+'},
				{'-', ' ', '-', '+', '+', '1'},
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
		assertEquals(18, cg.charArea('+'));
		assertEquals(3, cg.charArea('-'));
		assertEquals(9, cg.charArea(' '));
		assertEquals(1, cg.charArea('1'));
	}

	public void testCountPlus3(){
		char [][] grid = new char [][]{
				{'+', '+'},
				{'+', '+'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
		assertEquals(4, cg.charArea('+'));
		grid = new char [][]{
				{'a'}
		};
		CharGrid s = new CharGrid(grid);
		assertEquals(0, s.countPlus());
		assertEquals(1, s.charArea('a'));
		assertEquals(0, s.charArea('b'));
	}

	public void testCountPlus4(){
		char [][] grid = new char [][]{
				{'+', '3', '3', '-', '1', '1', '1'},
				{'+', '3', '3', '-', '1', '1', '1'},
				{'+', '-', '-', '-', '-', '-', '1'},
				{'+', '+', '2', '-', '1', '1', '3'},
				{'+', '+', '+', '-', '1', '1', '1'},
				{'+', '+', '2', '3', '3', '1', '3'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(3, cg.countPlus());
		assertEquals(18, cg.charArea('+'));
		assertEquals(25, cg.charArea('-'));
		assertEquals(36, cg.charArea('3'));
		assertEquals(3, cg.charArea('2'));
	}




	public void testCharGrid5(){
		char [][] grid = new char [][] {
				{'b'}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(1, ch.charArea('b'));
		assertEquals(0, ch.countPlus());
	}

	public void testCharGrid6(){
		char [][] grid = new char [][] {
				{'a', 'b', 'b', 'c', 'c', 'c', 'd'}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(3, ch.charArea('c'));
		assertEquals(2, ch.charArea('b'));
		assertEquals(0, ch.countPlus());
	}

	public void testCharGrid7(){
		char [][] grid = new char [][]{
				{'a'},
				{'a', 'b'},
				{'a', 'a', 'a', 'a'},

		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(12, ch.charArea('a'));
		assertEquals(1, ch.charArea('b'));
		assertEquals(0, ch.charArea('c'));
		assertEquals(0, ch.countPlus());
	}

	public void testCharGrid8(){
		char [][] grid = new char[][]{
				{},
				{}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(0, ch.charArea('a'));
		assertEquals(0, ch.countPlus());
	}

	public void testCharGrid9(){
		char [][] grid = new char[][]{
				{'a', 'b'},
				{'b', 'b', 'b', 'd'},
				{'a', 'b'}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(3, ch.charArea('a'));
		assertEquals(9, ch.charArea('b'));
		assertEquals(1, ch.countPlus());
	}

	public void testCharGrid10(){
		char [][] grid = new char[][]{
				{'b', 'a', 'v', 'v'},
				{},
				{'a', 'a', 'v', 'e', 'd'},
				{'d', 't', 'o'}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(0, ch.countPlus());
		assertEquals(6, ch.charArea('a'));
		assertEquals(6, ch.charArea('v'));
	}

	public void testCharGrid11(){
		char [][] grid = new char[][]{
				{'a', 'i', 'c', 'b'},
				{'i', 'i', 'i'},
				{'d', 'i'},
				{},
				{'i', 'a', 'b'},
				{},
				{'a', 'i', 'b', 'i', 'i'},
				{'i', 'i', 'i', 't'},
				{'a', 'i'}
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(45, ch.charArea('i'));
		assertEquals(2, ch.countPlus());
		assertEquals(18, ch.charArea('a'));
		assertEquals(1, ch.charArea('t'));
	}

	public void testCharGrid12(){
		char [][] grid = new char [][]{
		};
		CharGrid ch = new CharGrid(grid);
		assertEquals(0, ch.charArea('a'));
		assertEquals(0, ch.countPlus());
	}
	
}
