package perform_operation;

public class Connectives {
	static protected int numberOfPrepositions;
	static protected int numberInColumn;

	static protected boolean[][] truthTable;
	public Connectives(int numberOfPrepositions) {
		Connectives.numberOfPrepositions = numberOfPrepositions;
		Connectives.numberInColumn = (int) Math.pow(2, numberOfPrepositions);

		truthTable = new boolean[numberOfPrepositions][numberInColumn];
	}

/* TODO: Need to see if works Denial operation and why it needs "truth table" used in last class and if we can change it.
*
*	public static boolean[][] Negação(int coluna1) {
*		boolean[][] result = new boolean[numberInColumn][1];
*		for (int row = 0; row < numberInColumn; row++) {
*			for (int column = 0; column < 1; column++) {
*				result[row][column] = !tabelaVerdade[row][coluna1];
*			}
*		}
*		return result;
*	}
*
*	public static boolean[][] NegaçãoOperação(boolean[][] result1) {
*		boolean[][] result = new boolean[numberInColumn][1];
*		for (int row = 0; row < numberInColumn; row++) {
*			result[row][0] = !result1[row][0];
*		}
*		return result;
*	}
*/
	public static boolean[] conjunction(boolean[] prepositionOne, boolean[] prepositionTwo) {
		boolean[] result = new boolean[numberInColumn];
		for (int row = 0; row < numberInColumn; row++) {
			result[row] = prepositionOne[row] && prepositionTwo[row];
		}

		return result;
	}

	public static boolean[] disjunction(boolean[] prepositionOne, boolean[] prepositionTwo) {
		boolean[] result = new boolean[numberInColumn];
		for (int row = 0; row < numberInColumn; row++) {
			if (prepositionOne[row]|| prepositionTwo[row])
				result[row] = true;
		}

		return result;
	}

	public static boolean[] conditional(boolean[] prepositionOne, boolean[] prepositionTwo) {
		boolean[] result = new boolean[numberInColumn];
		for (int row = 0; row < numberInColumn; row++) {
			result[row] = !prepositionOne[row] || prepositionTwo[row];
		}

		return result;
	}

	public static boolean[] biconditional(boolean[] prepositionOne, boolean[] prepositionTwo) {
		boolean[] result = new boolean[numberInColumn];
		for (int row = 0; row < numberInColumn; row++) {
			for (int column = 0; column < 1; column++) {
				result[row] = (prepositionOne[row] && prepositionTwo[row])
						|| (!prepositionOne[row] && !prepositionTwo[row]);
			}
		}

		return result;
	}
}