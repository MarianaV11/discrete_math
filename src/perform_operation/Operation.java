package perform_operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation extends Connectives {
	public static Map<String, boolean[]> charArrayMap = new HashMap<>();

	public Operation(int numberOfPrepositions) {
		super(numberOfPrepositions);
	}

	public static final Character[] SimbolosPermitidos = {
			'^', 'v', '→', '↔', '~'
	};
	public static final Character[] Parenthesis = {
			'(', ')'
	};

	private static int formulaProgression = 0;

	public enum TypeOfOperation {
		isConnective,
		isConjunction,
		isDisjunction,
		isConditional,
		isBiconditional;
	}

	// Check if the operations only contains the especified propositions
	public static boolean ProposiçõesValidas(String formula, ArrayList<Character> arraylist) {
		for (char i : formula.toCharArray()) {
			char iUpper = Character.toUpperCase(i);
			if (Character.isLetter(i) && !arraylist.contains(iUpper) && iUpper != 'V') {
				return false;
			}
		}
		return true;
	}

	// validate the formula
	public static boolean SimbolosValidos(String formula) {
		Stack<Character> stack = new Stack<>();
		boolean isConnective = false;
		for (char f : formula.toCharArray()) {
			if (!isSimboloPermitido(f, Parenthesis)) {
				if (f == 'V')
					f = Character.toLowerCase(f);

				if (!isConnective) {
					if (!Character.isLetter(f) && f != '~') {
						System.out.println("Character not allowed: " + f);
						return false;
					}
					if (f == '~')
						isConnective = false;
					else
						isConnective = true;
				} else {
					if (!isSimboloPermitido(f, SimbolosPermitidos)) {
						System.out.println("Character not allowed: " + f);
						return false;
					}
					isConnective = false;
				}
			}

			if (f == '(') {
				stack.push(f);
			} else if (f == ')') {
				if (stack.isEmpty() || stack.pop() != '(') {
					return false;
				}
			}

			if (formula.contains("()")) {
				return false;
			}
		}

		if (!stack.isEmpty())
			System.out.println("Parentheses unclosed or wrong positioned");
		return stack.isEmpty();
	}

	// Aids in the SimbolosValidos function to check whether a specific symbol
	// according to the characters array passed to it.
	private static boolean isSimboloPermitido(char c, Character[] simbols) {
		for (char permitido : simbols) {
			if (c == permitido) {
				return true;
			}
		}
		return false;
	}

	// return if array is valid or not
	public static boolean validator(String formula, ArrayList<Character> arraylist) {
		if (SimbolosValidos(formula) && ProposiçõesValidas(formula, arraylist)) {
			System.out.println("É válido");
			return true;
		} else {
			System.out.println("Não é válido");
			return false;
		}
	}

	// create the proposition's array
	public static void creatPropArrays(ArrayList<Character> preosition) {
		for (int i = numberOfPrepositions - 1; i >= 0; i--) {
			boolean[] currentColumn = new boolean[numberInColumn];

			int defineValidity = (numberOfPrepositions - i - 1) * 2;
			if (defineValidity == 0)
				defineValidity = 1;
			boolean aux = false;

			for (int j = 0; j < currentColumn.length; j++) {
				if (j % defineValidity == 0)
					aux = !aux;
				currentColumn[j] = aux;
			}
			charArrayMap.put(preosition.get(i) + "", currentColumn);
		}
	}

	// go through the string formula preparing and operating then
	public static String prepareToOperate(String formula) {
		String prev = "";
		String actual = "";
		String index = "";
		String lastResult = "";
		boolean denial = false;

		TypeOfOperation currentOperation = TypeOfOperation.isConnective;

		char[] form = formula.toCharArray();

		System.out.println("formula inicio:" + formula);

		for (int i = 0; i < form.length; i++) {
			System.out.println(currentOperation);
			char f = form[i];
			formulaProgression++;

			if (f == '(') {
				System.out.println("formula progression: " + formulaProgression);
				String newFormula = removeCharacter(formula, formulaProgression);
				String aux = prepareToOperate(newFormula);
				System.out.println("aux: " + aux);
				System.out.println("index2: " + index);
				if (currentOperation != TypeOfOperation.isConnective) {
					actual = aux;
					if (index != "")
						prev = index;
				} else
					prev = aux;
				i = formulaProgression - 1;
				f = form[i];
				System.out.println("new f: " + f);
			} else if (f == ')') {
				lastResult = index;
				index = "";
				return lastResult;
			} else {
				actual = f + "";
			}

			// System.out.println(f);

			if (denial) {
				boolean[] result = Connectives.denial(charArrayMap.get(actual));
				index = "~" + actual;
				charArrayMap.put(index, result);
				actual = index;
				denial = false;
				System.out.println("Actual: " + actual);
			}

			if (f == '~')
				denial = true;

			if (currentOperation == TypeOfOperation.isConjunction && !denial) {
				boolean[] result = Connectives.conjunction(charArrayMap.get(prev), charArrayMap.get(actual));
				index = "(" + prev + "^" + actual + ")";
				System.out.println("index: " + index);
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isDisjunction && !denial) {
				System.out.println(prev + "v" + actual);
				boolean[] result = Connectives.disjunction(charArrayMap.get(prev), charArrayMap.get(actual));
				index = "(" + prev + "v" + actual + ")";
				System.out.println("index: " + index);
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isConditional && !denial) {
				boolean[] result = Connectives.conditional(charArrayMap.get(prev), charArrayMap.get(actual));
				index = "(" + prev + "→" + actual + ")";
				System.out.println("index: " + index);
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isBiconditional && !denial) {
				boolean[] result = Connectives.biconditional(charArrayMap.get(prev), charArrayMap.get(actual));
				index = "(" + prev + "↔" + actual + ")";
				System.out.println("index: " + index);
				charArrayMap.put(index, result);
			}

			if (!denial)
				currentOperation = checkKindOfOperation(f);

			if (Character.isLetter(f) && f != 'V' && index == "") {
				prev = f + "";
			} else if (Character.isLetter(f) && f != 'V') {
				prev = index;
			}

		}
		return index;
	}

	// Aids prepareToOperate by checking wictch operation has to do.
	public static TypeOfOperation checkKindOfOperation(char character) {
		switch (character) {
			case '^':
				return TypeOfOperation.isConjunction;
			case 'V':
				return TypeOfOperation.isDisjunction;
			case '→':
				return TypeOfOperation.isConditional;
			case '↔':
				return TypeOfOperation.isBiconditional;
		}
		return TypeOfOperation.isConnective;
	}

	// display the table result
	public static void tableAppearance() {
		charArrayMap.forEach((key, value) -> {
			System.out.print(key + " | ");
		});
		System.out.println();
		int arrayLength = charArrayMap.values().iterator().next().length;
		for (int i = 0; i < arrayLength; i++) {
			StringBuilder row = new StringBuilder();
			for (boolean[] array : charArrayMap.values()) {
				row.append(array[i] ? 'T' : 'F').append(" | ");
			}
			System.out.println(row.toString().replaceAll(" \\| $", ""));
		}
	}

	public static void displayColumn(String index, String formula) {
		boolean[] arr = charArrayMap.get(index);

		System.out.println();
		System.out.println("---------- result column ----------");
		System.out.println(formula);
		for (var item : arr) {
			System.out.println("| " + (item ? 'T' : 'F') + " |");
		}
	}

	public static void displayColumn(String index) {
		displayColumn(index, index);
	}

	public static void extractParenthesesExpressions(String input) {
		Pattern pattern = Pattern.compile("\\(([^()]+)\\)");
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String expression = matcher.group(1);
			System.out.println(expression);
		}
	}

	// Aid prepareToOperate removing the formula caracteres to operate without then.
	public static String removeCharacter(String input, int quantidade) {
		// Verificar se a quantidade é válida
		if (quantidade >= 0 && quantidade <= input.length()) {
			// Remover os caracteres da esquerda para a direita
			return input.substring(quantidade);
		} else {
			// Se a quantidade não for válida, retornar a string original
			return input;
		}
	}

}