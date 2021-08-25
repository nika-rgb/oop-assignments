package Sudoku;

public class illegalSudokuOperationException extends RuntimeException {
    public illegalSudokuOperationException(String message){
        System.out.println(message);
    }
}
