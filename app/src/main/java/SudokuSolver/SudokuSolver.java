package SudokuSolver;

import java.util.Scanner;

public class SudokuSolver
{
	public static void main(String[] args)
	{
		System.out.print("Select [E]asy, [M]edium, [H]ard, or [C]ustom: ");

		Scanner console = new Scanner(System.in);
		String input = console.nextLine();

		Board board;

		if (input.length() == 0)
			input = "c";

		switch (input.toLowerCase().charAt(0))
		{
		case 'c':

			System.out.println("Enter board row by row, starting from the top row.");
			System.out.println("Enter a space for blank squares.");

			String[] boardRows = new String[9];

			for (int y = 0; y < 9; y++)
			{
				System.out.print("Enter row " + (y + 1) + ": ");
				boardRows[y] = console.nextLine();
			}

			board = new BasicBoard(boardRows);
			break;
		default:
			board = Board.fromDifficulty(Board.Difficulty.fromString(input.toLowerCase()), BasicBoard.class);
		}

		console.close();

		System.out.println(board.toString());

		System.out.println("Solving...");

		final long startTime = System.currentTimeMillis();

		Solver solver = new Solver(board);
		solver.solve();

		final long endTime = System.currentTimeMillis();

		System.out.println("Finished.");
		System.out.println(board.toString());

		if (!board.checkSolved())
			System.out.println("No solution found.");
		else
			System.out.println("Board is solved.");

		System.out.println("Time: " + (endTime - startTime) + " milliseconds.");
	}
}
