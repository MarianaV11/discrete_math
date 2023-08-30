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
			'^', 'v', '→', '↔'
	};
	public static final Character[] Parenthesis = {
			'(', ')'
	};

	public enum TypeOfOperation {
		isConnective,
		isConjunction,
		isDisjunction,
		isConditional,
		isDenial,
		isBiconditional;
	}

	public static boolean ProposiçõesValidas(String formula, ArrayList<Character> arraylist) {
		for (char i : formula.toCharArray()) {
			char iUpper = Character.toUpperCase(i);
			if (Character.isLetter(i) && !arraylist.contains(iUpper) && iUpper != 'V') {
				return false;
			}
		}
		return true;
	}

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

	private static boolean isSimboloPermitido(char c, Character[] simbols) {
		for (char permitido : simbols) {
			if (c == permitido) {
				return true;
			}
		}
		return false;
	}

	public static boolean validator(String formula, ArrayList<Character> arraylist) {
		if (SimbolosValidos(formula) && ProposiçõesValidas(formula, arraylist)) {
			System.out.println("É válido");
			return true;
		} else {
			System.out.println("Não é válido");
			return false;
		}
	}

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

	public static void prepareToOperate(String formula) {
		String aux = "";

		TypeOfOperation currentOperation = TypeOfOperation.isConnective;

		for (char f : formula.toCharArray()) {
			if (currentOperation == TypeOfOperation.isConjunction) {
				System.out.println(aux);
				boolean[] result = Connectives.conjunction(charArrayMap.get(aux), charArrayMap.get(f + ""));
				String index = aux + "^" + f;
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isDisjunction) {
				boolean[] result = Connectives.disjunction(charArrayMap.get(aux), charArrayMap.get(f + ""));
				String index = aux + "v" + f;
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isConditional) {
				boolean[] result = Connectives.conditional(charArrayMap.get(aux), charArrayMap.get(f + ""));
				String index = aux + "→" + f;
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isBiconditional) {
				boolean[] result = Connectives.biconditional(charArrayMap.get(aux), charArrayMap.get(f + ""));
				String index = aux + "↔" + f;
				charArrayMap.put(index, result);
			}

			if (currentOperation == TypeOfOperation.isDenial) {}

			currentOperation = checkKindOfOperation(f);

			if (Character.isLetter(f) && f != 'V') {
				aux = f + "";
			}
		}
		tableAppearance();
	}

	public static TypeOfOperation checkKindOfOperation (char character) {
		switch (character) {
			case '^':
				return TypeOfOperation.isConjunction;
			case 'V':
				return TypeOfOperation.isDisjunction;
			case '→':
				return TypeOfOperation.isConditional;
			case '↔':
				return TypeOfOperation.isBiconditional;
			case '~':
				return TypeOfOperation.isDenial;
		}
		return TypeOfOperation.isConnective;
	}

	public static void tableAppearance () {
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

	public static void extractParenthesesExpressions(String input) {
		Pattern pattern = Pattern.compile("\\(([^()]+)\\)");
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String expression = matcher.group(1);
			System.out.println(expression);
		}
	}

}