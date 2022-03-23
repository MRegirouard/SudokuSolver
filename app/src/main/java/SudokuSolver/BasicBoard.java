package SudokuSolver;

/**
 * A basic Sudoku board, with no GUI
 */
class BasicBoard implements Board
{
	private Square[][] board;

	/**
	 * Create a new board, with all squares having a
	 * value of 0 and all possible values set to true
	 */
	BasicBoard()
	{
		board = new Square[9][9];

		for (int y = 0; y < 9; y++)
		{
			board[y] = new Square[9];

			for (int x = 0; x < 9; x++)
				board[y][x] = new Square();
		}
	}

	/**
	 * Create a new board, with all squares having a
	 * value of 0 and all possible values set to false
	 * @param board The board to copy
	 */
	BasicBoard(final int[][] board)
	{
		this.board = new Square[9][9];

		for (int y = 0; y < 9; y++)
		{
			this.board[y] = new Square[9];

			for (int x = 0; x < 9; x++)
				this.board[y][x] = new Square(board[y][x]);
		}
	}

	/**
	 * Create a new board, from an array of strings representing
	 * each row. Strings should have a 1-9 value for each square,
	 * or a " " for an empty square. Squares with a value will have
	 * all possible values set to false, and squares with no value
	 * will have all possible values set to true.
	 */
	BasicBoard(final String[] rowStrs)
	{
		board = new Square[9][9];

		for (int y = 0; y < 9; y++)
		{
			board[y] = new Square[9];

			for (int x = 0; x < 9; x++)
			{
				char c = rowStrs[y].charAt(x);

				if (c != ' ')
					board[y][x] = new Square(Integer.valueOf(String.valueOf(c)));
				else
					board[y][x] = new Square();
			}
		}
	}

	public Square[][] getBoard()
	{
		return board;
	}
}