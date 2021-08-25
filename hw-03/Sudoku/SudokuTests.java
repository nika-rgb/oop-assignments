package Sudoku;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.*;

import static org.junit.Assert.*;

public class SudokuTests {
    private Sudoku sudoku;
    @Before
    public void init(){
        sudoku = new Sudoku(new int[9][9]);
    }


    @Test
    public void testSpot1(){
        Sudoku.Spot spot = sudoku.createNewSpot(5, 6, 2);
        assertEquals(2, spot.getValue());
        spot.setValue(3);
        assertEquals(3, spot.getValue());
    }


    @Test
    public void testSpot2(){
        Sudoku.Spot spot = sudoku.createNewSpot(5, 5, 7);
        assertEquals(5, spot.getCol());
        assertEquals(5, spot.getRow());
    }

    @Test
    public void testSpot3(){
        assertThrows(invalidSudokuValueException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(5, 6, 10);
            }
        });

        assertThrows(invalidSudokuValueException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(5, 6, -1);
            }
        });
    }


    @Test
    public void testSpot4(){
        assertThrows(invalidSudokuBoardCoordinateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(10, 6, 4);
            }
        });

        assertThrows(invalidSudokuBoardCoordinateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(-1, 6, 4);
            }
        });

        assertThrows(invalidSudokuBoardCoordinateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(5, 112, 4);
            }
        });

        assertThrows(invalidSudokuBoardCoordinateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku.Spot spot = sudoku.createNewSpot(8, -8, 4);
            }
        });

    }

    @Test
    public void testSpot5(){
        Sudoku.Spot spot = sudoku.createNewSpot(8, 2, 4);
        assertThrows(invalidSudokuValueException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                spot.setValue(36);
            }
        });
    }


    @Test
    public void testToString1(){
        Sudoku board = new Sudoku(Sudoku.easyGrid);
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("1 6 4 0 0 0 0 0 2\n");
        resultStr.append("2 0 0 4 0 3 9 1 0\n");
        resultStr.append("0 0 5 0 8 0 4 0 7\n");
        resultStr.append("0 9 0 0 0 6 5 0 0\n");
        resultStr.append("5 0 0 1 0 2 0 0 8\n");
        resultStr.append("0 0 8 9 0 0 0 3 0\n");
        resultStr.append("8 0 9 0 4 0 2 0 0\n");
        resultStr.append("0 7 3 5 0 9 0 0 1\n");
        resultStr.append("4 0 0 0 0 0 6 7 9\n");
        Assert.assertEquals(resultStr.toString(), board.toString());
    }

    @Test
    public void testToString2(){
        Sudoku board = new Sudoku(Sudoku.mediumGrid);
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("5 3 0 0 7 0 0 0 0\n");
        resultStr.append("6 0 0 1 9 5 0 0 0\n");
        resultStr.append("0 9 8 0 0 0 0 6 0\n");
        resultStr.append("8 0 0 0 6 0 0 0 3\n");
        resultStr.append("4 0 0 8 0 3 0 0 1\n");
        resultStr.append("7 0 0 0 2 0 0 0 6\n");
        resultStr.append("0 6 0 0 0 0 2 8 0\n");
        resultStr.append("0 0 0 4 1 9 0 0 5\n");
        resultStr.append("0 0 0 0 8 0 0 7 9\n");
        Assert.assertEquals(resultStr.toString(), board.toString());
    }

    @Test
    public void testToString3(){
        Sudoku board = new Sudoku(Sudoku.hardGrid);
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("3 7 0 0 0 0 0 8 0\n");
        resultStr.append("0 0 1 0 9 3 0 0 0\n");
        resultStr.append("0 4 0 7 8 0 0 0 3\n");
        resultStr.append("0 9 3 8 0 0 0 1 2\n");
        resultStr.append("0 0 0 0 4 0 0 0 0\n");
        resultStr.append("5 2 0 0 0 6 7 9 0\n");
        resultStr.append("6 0 0 0 2 1 0 4 0\n");
        resultStr.append("0 0 0 5 3 0 9 0 0\n");
        resultStr.append("0 3 0 0 0 0 0 5 1\n");
        Assert.assertEquals(resultStr.toString(), board.toString());
    }


    private void initializeBoardInfo(HashMap <Integer, ArrayList < Pair<Integer, Integer> > > boardInfo, String board){
        StringTokenizer tok = new StringTokenizer(board, " \n");
        int row = 0; int column = 0;
        while(tok.hasMoreTokens()){
            String token = tok.nextToken();
            int value = Integer.parseInt(token);
            ArrayList < Pair<Integer, Integer> > current;
            if(boardInfo.containsKey(value)){ current = boardInfo.get(value); }
            else{
                current = new ArrayList<>();
                boardInfo.put(value, current);
            }
            current.add(new Pair<>(row, column));
            row++;
            if(row == Sudoku.SIZE){
                row = 0;
                column++;
            }
        }
    }

    private boolean checkBoard(HashMap <Integer, ArrayList < Pair<Integer, Integer> > > boardInfo){
        boolean [] tmpRow = new boolean[Sudoku.SIZE];
        boolean [] tmpCol = new boolean[Sudoku.SIZE];
        boolean [] tmpSq = new boolean[Sudoku.SIZE + 1];
        for(Integer value : boardInfo.keySet()){
            if(value == 0) return false;
            ArrayList < Pair<Integer, Integer> > positions = boardInfo.get(value);

            for(int k = 0; k < positions.size(); k++){
                Pair <Integer, Integer> currentPoint = positions.get(k);
                if(!tmpRow[currentPoint.getKey()]) tmpRow[currentPoint.getKey()] = true;
                else return false;
                if(!tmpCol[currentPoint.getValue()]) tmpCol[currentPoint.getValue()] = true;
                else return false;
                int squareInd = (currentPoint.getKey() / Sudoku.PART) * Sudoku.PART + (currentPoint.getValue() / Sudoku.PART);
                if(!tmpSq[squareInd]) tmpSq[squareInd] = true;
                else return false;
            }
            Arrays.fill(tmpCol, false);
            Arrays.fill(tmpSq, false);
            Arrays.fill(tmpRow, false);
        }
        return true;
    }


    public boolean validSudokuBoard(String board){
        HashMap <Integer, ArrayList < Pair<Integer, Integer> > > boardInfo = new HashMap<>();
        initializeBoardInfo(boardInfo, board);
        return checkBoard(boardInfo);
    }

    @Test
    public void testSolve1(){
        String board = "1,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,0,0,0 0,0,0,0,0,0,00,,8 0,0,0,0,0,0,0,0,0";

        Sudoku solver = new Sudoku(board);
        int numSolutions = solver.solve();
        assertEquals(100, numSolutions);
        assertTrue(solver.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solver.getSolutionText()));
    }

    @Test
    public void testSolve2(){
        Sudoku solver = new Sudoku(Sudoku.hardGrid);
        int numSolutions = solver.solve();
        assertEquals(1, numSolutions);
        System.out.println(solver.getElapsed());
        assertTrue(solver.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solver.getSolutionText()));
    }

    @Test
    public void testSolve3(){
        Sudoku solver = new Sudoku(Sudoku.mediumGrid);
        int numSolutions = solver.solve();
        assertEquals(1, numSolutions);
        assertTrue(solver.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solver.getSolutionText()));
    }

    @Test
    public void testSolve4(){
        Sudoku solve = new Sudoku(Sudoku.easyGrid);
        int numSolutions = solve.solve();
        assertEquals(1, numSolutions);
        assertTrue(solve.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solve.getSolutionText()));
    }

    @Test
    public void testSolve5(){
        StringBuilder input = new StringBuilder();
        input.append("3 7 0 0 0 0 0 8 0").append("0 0 1 0 9 3 0 0 0").append("0 4 0 0 8 0 0 0 3");
        input.append("0 9 3 8 0 0 0 1 2").append("0 0 0 0 4 0 0 0 0").append("5 2 0 0 0 6 7 9 0");
        input.append("6 0 0 0 2 1 0 4 0").append("0 0 0 5 3 0 9 0 0").append("0 3 0 0 0 0 0 5 1");
        Sudoku solver = new Sudoku(input.toString());
        int numSolutions = solver.solve();
        assertEquals(4, numSolutions);
        assertTrue(solver.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solver.getSolutionText()));
    }
    @Test
    public void testSolve6(){
        StringBuilder input = new StringBuilder();
        input.append("3 0 0 0 0 0 0 8 0").append("0 0 1 0 9 3 0 0 0").append("0 4 0 7 8 0 0 0 3");
        input.append("0 9 3 8 0 0 0 1 2").append("0 0 0 0 4 0 0 0 0").append("5 2 0 0 0 6 7 9 0");
        input.append("6 0 0 0 2 1 0 4 0").append("0 0 0 5 3 0 9 0 0").append("0 3 0 0 0 0 0 5 1");
        Sudoku solver = new Sudoku(input.toString());
        int numSolutions = solver.solve();
        assertEquals(6, numSolutions);
        assertTrue(solver.getElapsed() <= 1000);
        assertTrue(validSudokuBoard(solver.getSolutionText()));
    }

    @Test
    public void testSolve7(){
        Sudoku solver = new Sudoku(Sudoku.hardGrid);
        assertThrows(illegalSudokuOperationException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                solver.getElapsed();
            }
        });

        assertThrows(illegalSudokuOperationException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                solver.getSolutionText();
            }
        });
    }

    @Test
    public void testSolve8(){
        StringBuilder input = new StringBuilder();
        input.append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        input.append("0,0,0,5,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        input.append("0,0,0,5,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        Sudoku solver = new Sudoku(input.toString());
        solver.solve();
        assertEquals(0, solver.solve());
    }

    @Test
    public void testSolve9(){
        StringBuilder input = new StringBuilder("");
        input.append("0,0,0,3,0,0,2,0,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        input.append("0,0,0,0,0,0,0,1,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        input.append("0,0,0,5,0,0,0,0,0").append("0,0,0,0,0,0,0,1,0").append("0,0,0,0,0,0,0,0,0");
        Sudoku solver = new Sudoku(input.toString());
        int numSolutions = solver.solve();
        assertEquals(0, numSolutions);
        assertEquals("", solver.getSolutionText());
    }

    @Test
    public void testSolve10(){
        StringBuilder input = new StringBuilder("");
        input.append("0,0,0,0,0,0,2,0,0").append("0,0,0,0,0,0,0,8,0").append("0,0,0,0,0,0,0,0,8");
        input.append("0,0,0,0,0,0,0,1,0").append("0,0,0,0,0,0,0,0,0").append("0,0,0,0,0,0,0,0,0");
        input.append("0,0,0,5,0,0,0,0,1").append("5,0,0,0,0,0,8,0,0").append("0,0,0,0,0,0,0,0,0");
        Sudoku solver = new Sudoku(input.toString());
        int numSolutions = solver.solve();
        assertEquals(0, numSolutions);
        assertEquals("", solver.getSolutionText());
    }


}
