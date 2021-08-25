import junit.framework.TestCase;

import java.awt.*;
import java.awt.font.TextHitInfo;
import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}

	public void testSampleSize1(){
		Piece p1 = new Piece(Piece.STICK_STR);
		assertEquals(1, p1.getWidth());
		assertEquals(4, p1.getHeight());
		Piece p2 = new Piece(Piece.L1_STR);
		assertEquals(2, p2.getWidth());
		assertEquals(3, p2.getHeight());
	}

	public void testSampleSize2(){
		Piece p1 = new Piece(Piece.SQUARE_STR);
		assertEquals(2, p1.getWidth());
		assertEquals(2, p1.getHeight());
		Piece p2 = new Piece(Piece.S1_STR);
		assertEquals(3, p2.getWidth());
		assertEquals(2, p2.getHeight());
		Piece p3 = new Piece(Piece.S2_STR);
		assertEquals(3, p3.getWidth());
		assertEquals(2, p3.getHeight());
	}
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}


	public void testSampleSkirt1(){
		Piece p1 = new Piece(Piece.S2_STR);
		assertTrue(Arrays.equals(new int [] {1 , 0, 0}, p1.getSkirt()));
		Piece p2 = new Piece(Piece.L1_STR);
		assertTrue(Arrays.equals(new int [] {0, 0}, p2.getSkirt()));
		p1 = new Piece(Piece.SQUARE_STR);
		assertTrue(Arrays.equals(new int [] {0, 0}, p1.getSkirt()));
		p2 = new Piece(Piece.STICK_STR);
		assertTrue(Arrays.equals(new int [] {0}, p2.getSkirt()));
	}

	public  void testSampleSkirt2(){
		Piece p1 = new Piece(Piece.L1_STR);
		assertTrue(Arrays.equals(new int [] {0, 0}, p1.getSkirt()));
		p1 = new Piece(Piece.PYRAMID_STR);
		assertTrue(Arrays.equals(new int [] {0, 0, 0}, p1.getSkirt()));
		p1 = new Piece(Piece.S1_STR);
		assertTrue(Arrays.equals(new int [] {0, 0, 1}, p1.getSkirt()));
	}



	public void testSampleComputeNextRotation1(){
		Piece p = new Piece(Piece.L1_STR);
		Piece rotated = p.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint[] {new TPoint(0, 0),
		new TPoint(1, 0), new TPoint(2, 0), new TPoint(2, 1)},rotated.getBody()));
		assertEquals(3, rotated.getWidth());
		assertEquals(2, rotated.getHeight());
		assertTrue(Arrays.equals(new int [] {0, 0, 0}, rotated.getSkirt()));
		Piece secondRotate = rotated.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint[] {new TPoint(0, 2),
		new TPoint(1, 0), new TPoint(1, 1), new TPoint(1, 2)}, secondRotate.getBody()));
		assertEquals(3, secondRotate.getHeight());
		assertEquals(2, secondRotate.getWidth());
		assertTrue(Arrays.equals(new int [] {2, 0}, secondRotate.getSkirt()));
		Piece thirdRotate = secondRotate.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint [] {new TPoint(0, 0),
		new TPoint(0, 1), new TPoint(1,1), new TPoint(2, 1)}, thirdRotate.getBody()));
		assertEquals(2, thirdRotate.getHeight());
		assertEquals(3, thirdRotate.getWidth());
		assertTrue(Arrays.equals(new int [] {0, 1, 1}, thirdRotate.getSkirt()));
	}

	public void testSampleComputeNextRotation2(){
		Piece p = new Piece(Piece.PYRAMID_STR);
		Piece rotated = p.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint [] {new TPoint(0, 1),
		new TPoint(1, 0), new TPoint(1, 1), new TPoint(1, 2)}, rotated.getBody()));
		assertEquals(2, rotated.getWidth());
		assertEquals(3, rotated.getHeight());
		Piece secondRotate = rotated.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint [] {new TPoint(0, 1),
		new TPoint(1, 0), new TPoint(1, 1), new TPoint(2, 1)}, secondRotate.getBody()));
		assertEquals(3, secondRotate.getWidth());
		assertEquals(2, secondRotate.getHeight());
		Piece thirdRotate = secondRotate.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint [] {new TPoint(0, 0),
				new TPoint(0, 1), new TPoint(0, 2), new TPoint(1, 1)}, thirdRotate.getBody()));
		assertEquals(3, thirdRotate.getHeight());
		assertEquals(2, thirdRotate.getWidth());
	}

	public void testSampleComputeNextRotation3(){
		Piece p = new Piece(Piece.S1_STR);
		Piece rotated = p.computeNextRotation();
		assertTrue(Arrays.equals(new TPoint [] {new TPoint(0, 1),
				new TPoint(0, 2), new TPoint(1, 0), new TPoint(1, 1)}, rotated.getBody()));
		assertEquals(2, rotated.getWidth());
		assertEquals(3, rotated.getHeight());
		assertTrue(Arrays.equals(new int [] {1, 0}, rotated.getSkirt()));
		Piece secondRotate = rotated.computeNextRotation();
		assertTrue(Arrays.equals(p.getBody(), secondRotate.getBody()));
	}

	public void testSampleEquals1(){
		Piece p1 = new Piece(Piece.PYRAMID_STR);
		assertTrue(p1.equals(p1));
		Piece p2 = new Piece(Piece.L1_STR);
		assertFalse(p2.equals(p1));
	}

	public void testSampleEquals2(){
		Piece p1 = new Piece(Piece.STICK_STR);
		Piece p2 = new Piece(Piece.STICK_STR);
		assertTrue(p1.equals(p2));
		Piece p3 = p1.computeNextRotation();
		assertFalse(p3.equals(p1));
	}

	public void testSampleEquals3(){
		Piece p1 = new Piece(Piece.STICK_STR);
		Piece p2 = p1.computeNextRotation();
		Piece p3 = p2.computeNextRotation();
		assertTrue(p1.equals(p3));
	}

	public void testSampleEquals4(){
		Piece p1 = new Piece(Piece.SQUARE_STR);
		assertTrue(p1.equals(p1.computeNextRotation()));
		Piece p2 = new Piece(Piece.S2_STR);
		Piece p3 = new Piece(Piece.S1_STR);
		assertFalse(p2.equals(p3));
		assertTrue(p2.equals(new Piece(Piece.S2_STR)));
		Piece p4 = new Piece(Piece.L1_STR);
		Piece p5 = new Piece(Piece.L2_STR);
		assertFalse(p4.equals(p5));
	}

	public void testSampleMakeFastRotations1(){
		Piece [] pieces = Piece.getPieces();
		Piece start = new Piece(Piece.STICK_STR);
		assertTrue(start.equals(pieces[Piece.STICK]));
		Piece f = pieces[Piece.STICK].fastRotation();
		assertTrue(f.equals(start.computeNextRotation()));
		assertTrue(f.fastRotation().equals(start));
	}

	public void testSampleMakeFastRotations2(){
		Piece [] pieces = Piece.getPieces();
		Piece start = new Piece(Piece.SQUARE_STR);
		assertTrue(start.equals(pieces[Piece.SQUARE]));
		assertTrue(pieces[Piece.SQUARE].fastRotation().equals(start));
	}

	public void testSampleMakeFastRotation3(){
		Piece [] pieces = Piece.getPieces();
		Piece start = new Piece(Piece.L1_STR);
		assertTrue(start.equals(pieces[Piece.L1]));
		Piece rotate = pieces[Piece.L1].fastRotation();
		Piece startR = start.computeNextRotation();
		assertTrue(rotate.equals(startR));
		rotate = rotate.fastRotation();
		startR = startR.computeNextRotation();
		assertTrue(rotate.equals(startR));
		rotate = rotate.fastRotation();
		startR = startR.computeNextRotation();
		assertTrue(rotate.equals(startR));
		rotate = rotate.fastRotation();
		assertTrue(rotate.equals(start));
	}

	public void testSampleMakeFastRotation4(){
		Piece [] pieces = Piece.getPieces();
		Piece start = new Piece(Piece.PYRAMID_STR);
		assertTrue(start.equals(pieces[Piece.PYRAMID]));
		Piece rotate = pieces[Piece.PYRAMID].fastRotation();
		Piece rotateR = start.computeNextRotation();
		assertTrue(rotateR.equals(rotate));
		rotate = rotate.fastRotation();
		rotateR = rotateR.computeNextRotation();
		assertTrue(rotate.equals(rotateR));
		rotate = rotate.fastRotation();
		rotateR = rotateR.computeNextRotation();
		assertTrue(rotate.equals(rotateR));
		rotate = rotate.fastRotation();
		assertTrue(rotate.equals(start));
	}

}
