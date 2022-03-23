package SudokuSolver;

import java.util.Arrays;

/**
 * Represents a single square on the Sudoku board.
 * Contains a solved value and a list of possible values,
 * which represent pencil marks in a Sudoku puzzle
 */
public class Square
{
	/**
	 * The solved value of the square
	 */
	public int value;

	/**
	 * The list of possible values for the square. This array should
	 * maintain a size of 9, and each possible value should be represented
	 * by true or false within the array. If the value is true, then the
	 * value is a possible value for the square
	 */
	public boolean[] possibleVals;

	/**
	 * Constructs a new Square with no value and
	 * all possible values set to true
	 */
	Square()
	{
		this(0);
		Arrays.fill(possibleVals, true);
	}

	/**
	 * Constructs a new Square with value and all
	 * possible values set to false
	 * @param value The value of this square
	 */
	Square(final int value)
	{
		this.value = value;
		this.possibleVals = new boolean[9];
	}

	/**
	 * Sets all possible values to false
	 */
	void makeImpossible()
	{
		Arrays.fill(possibleVals, false);
	}

	/**
	 * Sets all possible values to true
	 */
	void makePossible()
	{
		Arrays.fill(possibleVals, true);
	}

	/**
	 * Returns true if this square has a value
	 * @return True if this square has a value between 1 and 9, false otherwise
	 */
	boolean hasValue()
	{
		return value >= 1 && value <= 9;
	}
}
