package Sudoku;

public class invalidSudokuBoardCoordinateException extends RuntimeException{
    public invalidSudokuBoardCoordinateException(String message){
        System.out.println(message);
    }
}
