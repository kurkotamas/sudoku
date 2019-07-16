import java.io.IOException;

public class Main {
	public static void main(String args[]) throws InterruptedException, IOException {
		Sudoku sudoku = new Sudoku("sudoku.txt");
		sudoku.kiir();
		sudoku.kitolt(0, 0);
	}
}
