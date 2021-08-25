import javafx.scene.input.PickResult;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.function.ThrowingRunnable;

import javax.swing.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	Piece [] basics;


	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		b.place(pyr1, 0, 0);
		basics = Piece.getPieces();
	}



	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(2, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertTrue(b.getGrid(1, 3));
		assertFalse(b.getGrid(2, 3));
	}



	public void testConstructor(){

		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				Board b = new Board(0, 3);
			}
		});

		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				Board b = new Board(-1, 4);
			}
		});

		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				Board b = new Board(8, -4);
			}
		});

		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				Board b = new Board(8, 0);
			}
		});
	}

	public void testGetRowWidth(){
		Board board = new Board(4, 4);
		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				board.getRowWidth(-1);
			}
		});
		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				board.getRowWidth(6);
			}
		});
		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				board.getColumnHeight(-2);
			}
		});

		assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				board.getColumnHeight(7);
			}
		});
	}


	// place multiple pieces
	public void testPlace1(){
		Board board = new Board(3, 6);
		Piece p = basics[Piece.SQUARE];
		assertFalse(board.getGrid(0, 0));
		board.place(p, 0, 0);
		assertTrue(board.getGrid(0, 0));
		assertEquals(2, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(0, board.getRowWidth(2));
		assertEquals(2, board.getColumnHeight(0));
		assertEquals(2, board.getColumnHeight(1));
		assertEquals(0, board.getColumnHeight(2));
		assertEquals(2, board.getMaxHeight());
		board.commit();
		Piece p1 = basics[Piece.STICK];
		int result = board.place(p1, 2, 0);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		assertEquals(4, board.getMaxHeight());
		assertEquals(3, board.getRowWidth(0));
		assertEquals(3, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(0, board.getRowWidth(4));
		assertEquals(4, board.getColumnHeight(2));
		board.commit();
		Piece p2 = basics[Piece.S1];
		p2 = p2.computeNextRotation();
		assertFalse(board.getGrid(1, 2));
		result = board.place(p2, 0, 2);
		assertEquals(result, Board.PLACE_ROW_FILLED);
		assertEquals(3, board.getRowWidth(3));
		assertEquals(2, board.getRowWidth(2));
		assertEquals(1, board.getRowWidth(4));
		assertEquals(5, board.getMaxHeight());
		assertEquals(4, board.getColumnHeight(1));
		board.commit();
	}

	// test for invalid x or y
	public void testPlace2(){
		Board s = new Board(4, 5);
		Piece p1 = basics[Piece.L1];
		int result = s.place(p1, -2, 2);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);

		result = s.place(p1, 4, -2);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);

		result = s.place(p1, 3, 9);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);

		result = s.place(p1, 4, 3);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
	}


	// test Piece out of bounds.
	public void testPlace3(){
		Board board = new Board(3, 6);
		Piece p1 = basics[Piece.L2];
		int result = board.place(p1, 2, 0);
		assertEquals(result, Board.PLACE_OUT_BOUNDS);
		board.commit();
		result = board.place(p1, 1, 0);
		board.commit();
		assertEquals(result, Board.PLACE_BAD);
		board.commit();
		Piece p2 = p1.computeNextRotation();
		result = board.place(p2,1, 3);
		assertEquals(result, Board.PLACE_OUT_BOUNDS);
	}

	// test Piece overlaps other piece.
	public void testPlace4(){
		Board board = new Board(4, 8);
		Piece p1 = basics[Piece.PYRAMID];
		Piece p2 = basics[Piece.STICK];
		int result = board.place(p1, 0, 0);
		assertEquals(result, Board.PLACE_OK);
		board.commit();
		result = board.place(p2, 1, 0);
		assertEquals(Board.PLACE_BAD, result);
	}

	// test place : not yet commited;
	public void testPlace5(){
		Board board = new Board(5, 7);
		Piece p1 = basics[Piece.STICK];
		Piece p2 = p1.computeNextRotation();
		int result =  board.place(p1, 0, 0);
		assertEquals(result, Board.PLACE_OK);
		assertThrows(RuntimeException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				board.place(p2, 1, 0);
			}
		});
	}

	public void testPlace6(){
		Board board = new Board(4, 5);
		board.place(basics[Piece.L2], 0, 0);
		board.commit();
		board.place(basics[Piece.STICK].computeNextRotation(), 0, 3);
		board.commit();
		assertEquals(1, board.clearRows());
	}

	// test dropHeight
	public void testDropHeight1(){
		Board board = new Board(4, 9);
		Piece p = basics[Piece.S1];
		p = p.computeNextRotation();
		assertEquals(0, board.dropHeight(p, 0));
	}



	public void testDropHeight2(){
		//test Drop height and other properties too.
		Board board = new Board(9, 9);
		Piece p1 = basics[Piece.PYRAMID];
		int res = board.place(p1, 0, 0);
		assertEquals(res, Board.PLACE_OK);
		board.commit();
		Piece p2 = p1.computeNextRotation().computeNextRotation();
		int dropHeight = board.dropHeight(p2, 1);
		assertEquals(1, dropHeight);
		res = board.place(p2, 1, dropHeight);
		assertEquals(res, Board.PLACE_OK);
		board.commit();
		Piece p3 = basics[Piece.SQUARE];
		dropHeight = board.dropHeight(p2, 1);
		assertEquals(3, dropHeight);
		res = board.place(p3, 3, dropHeight);
		assertEquals(res, Board.PLACE_OK);
		assertEquals(5, board.getMaxHeight());
	}

	public void testDropHeight3(){
		// test drop height and also other properties
		Board board = new Board(5, 9);
		Piece p1 = basics[Piece.L1];
		int res = board.place(p1, 0, 0);
		assertEquals(res, Board.PLACE_OK);
		board.commit();
		Piece p2 = basics[Piece.SQUARE];
		res = board.place(p2, 1, 1);
		assertEquals(Board.PLACE_OK, res);
		board.commit();
		assertEquals(3, board.getMaxHeight());
		assertEquals(3, board.dropHeight(p2, 1));
	}


	public void testClearRows1(){
		//nothing to clear
		Board board = new Board(4, 9);
		assertEquals(0, board.clearRows());
		board.commit();
		Piece p1 = basics[Piece.STICK];
		p1 = p1.computeNextRotation();
		int res = board.place(p1, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, res);
		board.commit();
		//place and then clear one row
		assertEquals(1, board.clearRows());
		assertEquals(0, board.getMaxHeight());
		assertEquals(0, board.getRowWidth(0));
		assertEquals(0, board.getColumnHeight(0));
		board.commit();
		//after second clear nothing must change
		assertEquals(0, board.clearRows());
		assertEquals(0, board.getMaxHeight());
		assertEquals(0, board.getRowWidth(0));
		assertEquals(0, board.getColumnHeight(0));
	}

	public void testClearRows2(){
		//clear every row
		Board board = new Board(4, 5);
		Piece p = basics[Piece.STICK];
		p = p.computeNextRotation();
		board.place(p, 0, 0);
		board.commit();
		board.place(p, 0, 1);
		board.commit();
		board.place(p, 0, 2);
		board.commit();
		board.place(p, 0, 3);
		board.commit();
		board.place(p, 0, 4);
		board.commit();
		assertEquals(5, board.clearRows());
		assertEquals(0, board.getMaxHeight());
		assertEquals(0, board.getColumnHeight(0));
		assertEquals(0, board.getRowWidth(1));
	}

	public void testClearRows3(){
		Board board = new Board(4, 8);
		Piece p1 = basics[Piece.STICK];
		p1 = p1.computeNextRotation();
		board.place(p1, 0, 0);
		board.commit();
		Piece p2 = basics[Piece.STICK];
		board.place(p2, 0, 1);
		board.commit();
		Piece p3 = basics[Piece.L2];
		board.place(p3, 2, 1);
		board.commit();
		Piece p4 = p3.computeNextRotation();
		p4 = p4.computeNextRotation();
		board.place(p4, 2, 2);
		board.commit();
		board.place(p1, 0, 5);
		board.commit();
		assertEquals(6, board.getMaxHeight());
		assertEquals(2, board.clearRows());
		assertEquals(4, board.getMaxHeight());
		assertFalse(board.getGrid(1, 0));
		assertEquals(3, board.getRowWidth(0));
		assertEquals(3, board.getRowWidth(1));
		assertEquals(3, board.getRowWidth(2));
		assertEquals(3, board.getRowWidth(3));
		assertEquals(0, board.getRowWidth(4));
	}


	public void testClearRows5(){
		Board board = new Board(5, 9);
		Piece p1 = basics[Piece.STICK];
		Piece p2 = p1.computeNextRotation();
		Piece p3 = basics[Piece.L2];
		p3 = p3.computeNextRotation().computeNextRotation();
		Piece p4 = basics[Piece.L1];
		p4 = p4.computeNextRotation().computeNextRotation();
		Piece p5 = basics[Piece.SQUARE];
		board.place(p2, 0, 0);
		board.commit();
		board.place(p1, 4, 0);
		board.commit();
		board.place(p2, 0, 1);
		board.commit();
		board.place(p3, 3, 2);
		board.commit();
		board.place(p4, 0, 2);
		board.commit();
		board.place(p2, 0, 5);
		board.commit();
		board.place(p1, 4, 5);
		board.commit();
		board.place(p5, 1, 6);
		board.commit();
		board.place(p2, 0, 8);
		board.commit();
		assertEquals(4, board.clearRows());
	}

	public void testClearRows6(){
		Board board = new Board(4, 9);
		board.place(basics[Piece.STICK].computeNextRotation(), 0, 0);
		board.commit();
		board.place(basics[Piece.L2], 2, 1);
		board.commit();
		board.place(basics[Piece.STICK].computeNextRotation(), 0, 4);
		board.commit();
		board.place(basics[Piece.STICK].computeNextRotation(), 0, 5);
		board.commit();
		board.place(basics[Piece.S2], 0, 6);
		board.commit();
		board.place(basics[Piece.S1].computeNextRotation(), 2, 6);
		board.commit();
		assertEquals(4, board.clearRows());
		assertEquals(5, board.getMaxHeight());
		assertEquals(2, board.getRowWidth(0));
		assertEquals(1, board.getRowWidth(1));
		assertEquals(3, board.getRowWidth(3));
		assertEquals(1, board.getRowWidth(4));
		assertEquals(0, board.getRowWidth(5));
		assertEquals(5, board.getColumnHeight(2));
		assertEquals(0, board.getColumnHeight(0));
		assertEquals(0, board.clearRows());
		assertEquals(3, board.getRowWidth(3));
		assertEquals(5, board.getColumnHeight(2));
	}




	public void testUndo1(){
		Board board = new Board(4, 9);
		board.place(basics[Piece.L2].computeNextRotation(), 0, 4);
		assertEquals(6, board.getMaxHeight());
		board.clearRows();
		board.undo();
		assertFalse(board.getGrid(0, 3));
		assertEquals(0, board.getMaxHeight());
		board.place(basics[Piece.S1], 1, 3);
		board.undo();

	}



	public void testUndo2(){
		// no changes on board calling undo must not change anything.
		Board board = new Board(4, 10);
		int max_height = board.getMaxHeight();
		int row_width = board.getRowWidth(0);
		int column_height = board.getColumnHeight(2);
		board.undo();
		assertEquals(max_height, board.getMaxHeight());
		assertEquals(row_width, board.getRowWidth(0));
		assertEquals(column_height, board.getColumnHeight(2));


		//place one figure and commit check that after undo nothing must change
		board.place(basics[Piece.PYRAMID], 1, 3);
		assertEquals(5, board.getMaxHeight());
		assertEquals(0, board.getColumnHeight(0));
		board.commit();
		board.undo();
		assertEquals(5, board.getMaxHeight());
		assertEquals(0, board.getColumnHeight(0));


		//place other figure and than undo check that everything must be in start state
		board.place(basics[Piece.SQUARE], 1, 0);
		assertEquals(2, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		board.undo();
		assertEquals(0, board.getRowWidth(0));
		assertEquals(0, board.getRowWidth(1));
		assertEquals(5, board.getMaxHeight());
		assertEquals(0, board.getColumnHeight(0));
	}


	public void testUndo3(){
		// add few figures and then check attributes
		Board b = new Board(4, 9);
		b.place(basics[Piece.STICK].computeNextRotation(), 0, 0);
		b.commit();
		b.place(basics[Piece.L1], 0, 1);
		b.commit();
		b.place(basics[Piece.L2], 2, 1);
		b.commit();
		b.place(basics[Piece.STICK].computeNextRotation(), 0, 4);
		b.commit();
		assertEquals(5, b.getMaxHeight());
		assertEquals(4, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(5, b.getColumnHeight(0));

		// call clear rows and check few attributes
		assertEquals(3, b.clearRows());
		assertEquals(2, b.getMaxHeight());
		assertEquals(2, b.getRowWidth(0));
		assertEquals(2, b.getColumnHeight(0));


		// undo and check that board returns to its first state.
		b.undo();

		assertEquals(5, b.getMaxHeight());
		assertEquals(4, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(5, b.getColumnHeight(0));

	}

	public void testUndo4(){
		//place some figures commit then place one last figure call clear rows and then check that it returns to first state
		Board board = new Board(4, 6);
		board.place(basics[Piece.STICK].computeNextRotation(), 0, 0);
		board.commit();
		board.place(basics[Piece.SQUARE], 0, 1);
		board.commit();
		board.place(basics[Piece.SQUARE], 2, 1);
		board.commit();
		board.place(basics[Piece.S2], 0, 3);
		assertEquals(5, board.getMaxHeight());
		assertEquals(5, board.getColumnHeight(0));
		assertEquals(4, board.getColumnHeight(2));
		assertEquals(3, board.getColumnHeight(3));
		assertEquals(4, board.getRowWidth(0));
		assertEquals(3, board.clearRows());
		board.undo();
		assertEquals(3, board.getMaxHeight());
		assertEquals(4, board.getRowWidth(0));
		assertEquals(4, board.getRowWidth(0));
	}

	public void testUndo5(){
		Board board = new Board(4, 6);
		board.place(basics[Piece.PYRAMID], 0, 0);
		board.commit();
		board.place(basics[Piece.STICK], 3, 0);
		board.commit();
		board.place(basics[Piece.STICK], 2, 1);
		board.commit();
		board.place(basics[Piece.STICK], 0, 1);
		assertEquals(5, board.getMaxHeight());
		assertEquals(4, board.getRowWidth(0));
		assertEquals(5, board.getColumnHeight(0));
		assertEquals(5, board.getColumnHeight(2));
		assertEquals(2, board.getColumnHeight(1));
		assertEquals(4, board.getColumnHeight(3));
		assertEquals(3, board.getRowWidth(2));
		assertEquals(2, board.getRowWidth(4));
		assertEquals(2, board.clearRows());
		assertEquals(3, board.getMaxHeight());
		assertEquals(2, board.getColumnHeight(3));
		assertEquals(3, board.getRowWidth(0));
		board.undo();
		assertEquals(5, board.getMaxHeight());
		assertEquals(4, board.getRowWidth(0));
		assertEquals(1, board.getColumnHeight(0));
		assertEquals(5, board.getColumnHeight(2));
		assertEquals(2, board.getColumnHeight(1));
		assertEquals(4, board.getColumnHeight(3));
		assertEquals(3, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(4));
		board.place(basics[Piece.STICK], 0, 1);
		board.commit();
		board.place(basics[Piece.L2].computeNextRotation(), 1 , 4);
		assertEquals(6, board.getMaxHeight());
		assertEquals(6, board.getColumnHeight(3));
		assertEquals(3, board.getRowWidth(5));
		board.undo();
		assertEquals(3, board.getRowWidth(3));
	}

	public void testUndo6(){
		// add figure than add figure out of bounds undo, then add figure in bad place undo
		Board board = new Board(4, 9);
		board.place(basics[Piece.S1], 1, 0);
		board.commit();
		int result = board.place(basics[Piece.L2], 3,0);
		assertEquals(3, board.getRowWidth(0));
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		board.undo();
		assertEquals(2, board.getRowWidth(0));
		result = board.place(basics[Piece.SQUARE], 2, 0);
		assertEquals(result, Board.PLACE_BAD);
		board.undo();
		assertEquals(2, board.getRowWidth(0));
	}

	public void testSanityCheck1(){
		Board b = new Board(5, 9);
		b.place(basics[Piece.S2].computeNextRotation(), 0, 0);
		b.commit();
		b.place(basics[Piece.L2], 1, 0);
		b.commit();
	}

	public void testAll1(){
		Board b = new Board(6, 9);
		int result = b.place(basics[Piece.SQUARE], 0, 0);
		assertEquals(Board.PLACE_OK, result);
		b.commit();
		assertEquals(2, b.getRowWidth(0));
		assertEquals(2, b.getMaxHeight());
		assertEquals(2, b.getColumnHeight(0));
		assertEquals(Board.PLACE_OK, b.place(basics[Piece.STICK], 5, 0));
		b.commit();
		assertEquals(4, b.getMaxHeight());
		assertEquals(4, b.getColumnHeight(5));
		assertEquals(Board.PLACE_BAD, b.place(basics[Piece.L1].computeNextRotation(), 4, 3));
		assertEquals(2, b.getRowWidth(3));
		b.undo();
		assertEquals(4, b.getMaxHeight());
		assertEquals(4, b.getColumnHeight(5));
		assertEquals(3, b.getRowWidth(0));
		assertEquals(3, b.dropHeight(basics[Piece.S2].computeNextRotation(), 4));
		assertEquals(b.PLACE_OK, b.place(basics[Piece.S2].computeNextRotation(), 4, 3));
		b.commit();
		assertEquals(2, b.getRowWidth(3));
		assertEquals(6, b.getMaxHeight());
		assertEquals(b.PLACE_ROW_FILLED, b.place(basics[Piece.PYRAMID], 2, 0));
		assertEquals(6, b.getRowWidth(0));
		b.commit();
		assertEquals(b.PLACE_OUT_BOUNDS, b.place(basics[Piece.SQUARE], 5, 6));
		b.undo();
		Piece lRotated = basics[Piece.L1].computeNextRotation().computeNextRotation();
		assertEquals(b.PLACE_OK, b.place(lRotated, 1, 1));
		b.commit();
		assertEquals(5, b.getRowWidth(1));
		assertEquals(4, b.getRowWidth(3));
		assertEquals(b.PLACE_ROW_FILLED, b.place(basics[Piece.S1].computeNextRotation(), 3, 1));
		b.commit();
		assertEquals(2, b.clearRows());
		b.commit();
		assertEquals(4, b.getMaxHeight());
		assertEquals(4, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(Board.PLACE_ROW_FILLED, b.place(basics[Piece.STICK].computeNextRotation(), 0, 2));
		assertEquals(6, b.getRowWidth(2));
		b.undo();
		assertEquals(2, b.getRowWidth(2));

	}

	public void testAll2(){
		Board b = new Board(4, 9);
		assertEquals(Board.PLACE_ROW_FILLED, b.place(basics[Piece.STICK].computeNextRotation(), 0, 0));
		b.commit();
		assertEquals(1, b.getMaxHeight());
		assertEquals(4, b.getRowWidth(0));
		assertEquals(Board.PLACE_OK, b.place(basics[Piece.S2].computeNextRotation(), 0, 1));
		b.commit();
		assertEquals(Board.PLACE_OK, b.place(basics[Piece.L2], 1, 1));
		b.commit();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(basics[Piece.STICK], 3, 1));
		b.commit();
		assertEquals(5, b.getMaxHeight());
		assertEquals(5, b.getColumnHeight(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(4, b.getRowWidth(0));
		assertEquals(3, b.clearRows());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(2, b.getMaxHeight());
		b.undo();
		assertEquals(5, b.getMaxHeight());
		assertEquals(4, b.dropHeight(basics[Piece.S2].computeNextRotation(),2));
		assertEquals(3, b.dropHeight(basics[Piece.S2].computeNextRotation(), 0));
		assertEquals(Board.PLACE_ROW_FILLED, b.place(basics[Piece.S2].computeNextRotation(), 0, 3));
		b.commit();
		assertEquals(6, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(5));
		assertEquals(3, b.getRowWidth(4));
		assertEquals(4, b.getRowWidth(0));
		assertEquals(4, b.clearRows());
		assertEquals(2, b.getMaxHeight());
		b.undo();
	}
	public void testAll3(){

	}

	public void testAll4(){

	}

	public void testAll5(){

	}

	public void testAll6(){

	}



}
