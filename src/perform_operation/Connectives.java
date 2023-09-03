package perform_operation;

public class Connectives {
	static protected int numberOfPrepositions;
	static protected int numberInColumn;

	public Connectives(int numberOfPrepositions) {
		Connectives.numberOfPrepositions = numberOfPrepositions;
		Connectives.numberInColumn = (int) Math.pow(2, numberOfPrepositions);
	}

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

	public static boolean[] denial (boolean[] preposition) {
		boolean[] result = new boolean[numberInColumn];
		for (int row = 0; row < numberInColumn; row++) {
			result[row] = !preposition[row];
		}

		return result;
	}
}