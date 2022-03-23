package SudokuSolver;

import java.util.LinkedList;
import java.util.List;

/**
 * A Sudoku solver
 */
public class Solver
{
	private final Board board;

	/**
	 * Create a new solver, using the given board
	 * @param board The board to solve
	 */
	Solver(final Board board)
	{
		this.board = board;
	}

	/**
	 * Updates each square's possible values, by setting them all
	 * to true and then removing any values that already exist in
	 * the same row, column, or box.
	 */
	public void updatePossibles()
	{
		board.makePossible();

		for (int y = 0; y < 9; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				final Square square = board.getSquare(y, x);

				if (square.hasValue())
				{
					square.makeImpossible();

					// Remove the value from all other squares in the row
					for (int x2 = 0; x2 < 9; x2++)
						board.getSquare(y, x2).possibleVals[square.value - 1] = false;

					// Remove the value from all other squares in the column
					for (int y2 = 0; y2 < 9; y2++)
						board.getSquare(y2, x).possibleVals[square.value - 1] = false;

					// Remove the value from all other squares in the box
					for (int i = 0; i < 9; i++)
						board.getSquareInBox(y / 3, x / 3, i).possibleVals[square.value - 1] = false;

					// Remove possible values from itself
					for (int i = 0; i < 9; i++)
						board.getSquare(y, x).possibleVals[i] = false;
				}
			}
		}
	}

	/**
	 * Gets a list of all squares that do not have values
	 * @return A list of all squares that do not have values
	 */
	public List<int[]> findUnsolved()
	{
		final List<int[]> unsolved = new LinkedList<int[]>();

		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				if (!board.getSquare(y, x).hasValue())
					unsolved.add(new int[] {y, x});

		return unsolved;
	}

	/**
	 * Solves the board, using the backtracking algorithm
	 */
	public void solve()
	{
		solve(findUnsolved(), 0);
	}

	/**
	 * Solves the board, using the backtracking algorithm. Operates
	 * recursively on a list of unsolved squares, by attempting to
	 * use each of a square's possible values. If the board is not
	 * longer valid, returns false right away instead of exploring
	 * known invalid solutions.
	 * @param unsolved The list of unsolved squares
	 * @param index The index to start solving at
	 * @return True if the board is solved, false if the board is invalid
	 */
	private boolean solve(List<int[]> unsolved, int index)
	{
		if (index == unsolved.size()) // Check for full solution at the end
			return board.checkSolved();

		final Square square = board.getSquare(unsolved.get(index)[0], unsolved.get(index)[1]);

		for (int i = 0; i < 9; i++) // Try each possible value
		{
			if (square.possibleVals[i])
			{
				square.value = i + 1; // Attempt solution

				// If board is still valid, go deeper
				if (board.validateSquare(unsolved.get(index)[0], unsolved.get(index)[1]))
					if (solve(unsolved, index + 1)) // If the solution is found, return true
						return true;

				square.value = 0; // Reset
			}
		}

		return false; // If no solution is found, return false -- backtrack
	}
}
