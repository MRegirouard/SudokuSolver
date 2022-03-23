package SudokuSolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A Sudoku board
 */
public interface Board
{
	/**
	 * Get the entire board as an array of Squares
	 * @return The Squares that make up this board
	 */
	public Square[][] getBoard();

	/**
	 * Gets a square at a specific x and y location on the board
	 * @param y The y location of the square, 0-8
	 * @param x The x location of the square, 0-8
	 * @return The square at the specified location
	 */
	public default Square getSquare(final int y, final int x)
	{
		return getBoard()[y][x];
	}

	/**
	 * Gets a square at a specific index within a 3x3 box within the board
	 * @param boxRow The row of the box, 0-2
	 * @param boxCol The column of the box, 0-2
	 * @param boxIndex The index of the square within the box, 0-8
	 * @return The square at the specified index within the box
	 */
	public default Square getSquareInBox(final int boxRow, final int boxCol, final int boxIndex)
	{
		final int row = (boxRow * 3) + boxIndex / 3;
		final int col = (boxCol * 3) + boxIndex % 3;
		return getSquare(row, col);
	}

	/**
	 * Marks all squares as having all possible values
	 */
	public default void makePossible()
	{
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				getSquare(y, x).makePossible();
	}

	/**
	 * Marks all squares as having not possible values
	 */
	public default void makeImpossible()
	{
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				getSquare(y, x).makeImpossible();
	}

	/**
	 * Checks a row on the board to ensure all numbers
	 * are unique. Empty squares are not allowed.
	 * @param rowNum The row number to check, 0-8
	 * @return True if numbers in the row are unique, false if there are duplicates
	 */
	public default boolean checkRow(final int rowNum)
	{
		final boolean[] nums = new boolean[9];

		for (int x = 0; x < 9; x++)
		{
			if (getSquare(rowNum, x).value < 1)
				return false;

			if (nums[getSquare(rowNum, x).value - 1])
				return false;

			nums[getSquare(rowNum, x).value - 1] = true;
		}

		return true;
	}

	/**
	 * Checks a column on the board to ensure all numbers
	 * are unique. Empty squares are not allowed.
	 * @param colNum The column number to check, 0-8.
	 * @return True if numbers in the column are unique, false if there are duplicates or empty squares
	 */
	public default boolean checkColumn(final int colNum)
	{
		final boolean[] nums = new boolean[9];

		for (int y = 0; y < 9; y++)
		{
			if (getSquare(y, colNum).value < 1)
				return false;

			if (nums[getSquare(y, colNum).value - 1])
				return false;

			nums[getSquare(y, colNum).value - 1] = true;
		}

		return true;
	}

	/**
	 * Checks a 3x3 box on the board to ensure all numbers
	 * are unique. Empty squares are not allowed.
	 * @param boxRowNum The row number of the box to check, 0-2
	 * @param boxColNum The column number of the box to check, 0-2
	 * @return True if numbers in the box are unique, false if there are duplicates or empty squares
	 */
	private boolean checkBox(final int boxRowNum, final int boxColNum)
	{
		boolean[] nums = new boolean[9];

		for (int x = boxColNum * 3; x < (boxColNum * 3) + 3; x++)
		{
			for (int y = boxRowNum * 3; y < (boxRowNum * 3) + 3; y++)
			{
				if (getSquare(y, x).value < 1)
					return false;

				if (nums[getSquare(y, x).value - 1])
					return false;

				nums[getSquare(y, x).value - 1] = true;
			}
		}

		return true;
	}

	/**
	 * Checks if the board has been solved, by checking rows,
	 * columns, and boxes. Empty squares are not allowed.
	 * @return True if the board is solved, false otherwise
	 */
	public default boolean checkSolved()
	{
		for (int y = 0; y < 9; y++)
			if (!checkRow(y))
				return false;

		for (int x = 0; x < 9; x++)
			if (!checkColumn(x))
				return false;

		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
				if (!checkBox(y, x))
					return false;

		return true;
	}


	/**
	 * Checks a row on the board to ensure all numbers
	 * are unique. Empty squares are allowed.
	 * @param rowNum The row number to check, 0-8
	 * @return True if numbers in the row are unique, false if there are duplicates
	 */
	private boolean checkPartialSolRow(final int rowNum)
	{
		final boolean[] nums = new boolean[9];

		for (int x = 0; x < 9; x++)
		{
			final Square square = getSquare(rowNum, x);

			if (square.hasValue())
			{
				if (nums[square.value - 1])
					return false;

				nums[square.value - 1] = true;
			}
		}

		return true;
	}

	/**
	 * Checks a column on the board to ensure all numbers
	 * are unique. Empty squares are allowed.
	 * @param colNum The column number to check, 0-8
	 * @return True if numbers in the column are unique, false if there are duplicates
	 */
	private boolean checkPartialSolCol(final int colNum)
	{
		final boolean[] nums = new boolean[9];

		for (int y = 0; y < 9; y++)
		{
			final Square square = getSquare(y, colNum);

			if (square.hasValue())
			{
				if (nums[square.value - 1])
					return false;

				nums[square.value - 1] = true;
			}
		}

		return true;
	}

	/**
	 * Checks a 3x3 box on the board to ensure all numbers
	 * are unique. Empty squares are allowed.
	 * @param boxRow The row number of the box to check, 0-2
	 * @param boxCol The column number of the box to check, 0-2
	 * @return True if numbers in the box are unique, false if there are duplicates
	 */
	private boolean checkPartialSolBox(final int boxRow, final int boxCol)
	{
		final boolean[] nums = new boolean[9];

		for (int i = 0; i < 9; i++)
		{
			final Square square = getSquareInBox(boxRow, boxCol, i);

			if (square.hasValue())
			{
				if (nums[square.value - 1])
					return false;

				nums[square.value - 1] = true;
			}
		}

		return true;
	}

	/**
	 * Checks if the board is validly partially solved
	 * @return True if no values have duplicates, false if there are duplicates
	 */
	public default boolean checkPartialSol()
	{
		for (int y = 0; y < 9; y++)
			if (!checkPartialSolRow(y))
				return false;

		for (int x = 0; x < 9; x++)
			if (!checkPartialSolCol(x))
				return false;

		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
				if (!checkPartialSolBox(y, x))
					return false;

		return true;
	}

	/**
	 * Checks if the given square has no duplicate
	 * values in its row, column, and box.
	 * @param y The row number of the square to check, 0-8
	 * @param x The column number of the square to check, 0-8
	 * @return True if the square is valid or empty, false if there are duplicates
	 */
	public default boolean validateSquare(final int y, final int x)
	{
		final Square square = getSquare(y, x);

		if (square.hasValue())
		{
			if (!checkRow(y))
				return false;

			if (!checkColumn(x))
				return false;

			if (!checkBox(y / 3, x / 3))
				return false;
		}

		return true;
	}

	/**
	 * A board difficulty, for fetching random boards from the API
	 */
	public static enum Difficulty
	{
		EASY, MEDIUM, HARD;

		/**
		 * Gets the difficulty from the given string, "easy", "medium", or "hard"
		 * @param difficulty The difficulty string
		 * @return The difficulty, or null if the string is invalid
		 */
		public static Difficulty fromString(final String difficulty)
		{
			if (difficulty.equalsIgnoreCase("easy"))
				return EASY;
			else if (difficulty.equalsIgnoreCase("medium"))
				return MEDIUM;
			else if (difficulty.equalsIgnoreCase("hard"))
				return HARD;
			else
				return null;
		}
	}

	/**
	 * Creates a new random board with the given difficulty and of the
	 * given type, by fetching a board from the API
	 * @param difficulty The difficulty of the board
	 * @param boardClass The type of board to create
	 * @return A new board of the given difficulty and type
	 */
	public static Board fromDifficulty(final Difficulty difficulty, final Class <? extends Board> boardClass)
	{
		final int[][] boardNums = new int[9][9];

		try
		{
			final URL url = new URL("https://sugoku.herokuapp.com/board?difficulty=" + difficulty.toString().toLowerCase());
			final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			final String inputLine = in.readLine();

			int y = 0;
			int x = 0;
			char c;

			for (int i = 0; i < inputLine.length(); i++)
			{
				c = inputLine.charAt(i);

				if (c >= '0' && c <= '9')
				{
					boardNums[y][x] = c - '0';
					x++;
				}
				else if (c == ']')
				{
					y++;
					x = 0;
				}
			}

		}
		catch (final Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return new BasicBoard(boardNums);
	}

	/**
	 * Gets this board as a String, for printing.
	 * The String is formatted as a 9x9 grid of numbers, with
	 * "-" and "|" separating the boxes. Possible values are
	 * not shown, and values are indicated by their 1-9 number.
	 * An empty square is indicated by a " ".
	 * @return This board as a String
	 */
	public default String toPrettyString()
	{
		String str = "";

		for (int y = 0; y < 9; y++)
		{
			if (y % 3 == 0)
			{
				for (int i = 0; i < 13; i++)
					str += "-";

				str += "\n";
			}

			for (int x = 0; x < 9; x++)
			{
				if (x % 3 == 0)
					str += "|";

				if (getSquare(y, x).value == 0)
					str += " ";
				else
					str += String.valueOf(getSquare(y, x).value);
			}

			str += "|\n";
		}

		for (int i = 0; i < 13; i++)
			str += "-";

		str += "\n";

		return str;
	}
}
