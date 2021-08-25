package Sudoku;

public class invalidSudokuValueException extends RuntimeException{
    public invalidSudokuValueException(String message){
        System.out.println(message);
    }
}
