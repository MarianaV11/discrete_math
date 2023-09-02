import perform_operation.Operation;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		ArrayList<Character> operations = new ArrayList();

		System.out.print("Informe o número de proposições: ");
		int numberOfPrepositions = scanner.nextInt();

		Operation execution = new Operation(numberOfPrepositions);

		System.out.print("Escolha os símbolos de suas proposições: ");
		for (int i = 0; i < numberOfPrepositions; i++) {
			operations.add(scanner.next().charAt(0));
		}

		execution.creatPropArrays(operations);

		scanner.nextLine();

		// System.out.print("Digite aqui sua fórmula: ");
		// String formula = scanner.nextLine();
		// formula = (formula.replaceAll("\\s", "")).toUpperCase();
		String formula = "(AVB)" + '→' + "(AV~B)" + '^' + "(AVB)" + 'V' + "(AVB)";

		boolean isValidate = false;
		if (formula != "")
			isValidate = execution.validator(formula, operations);
		if (isValidate) {
			String lastResult = execution.prepareToOperate(formula);
			execution.tableAppearance();
			System.out.println(lastResult);
		}
	}
}