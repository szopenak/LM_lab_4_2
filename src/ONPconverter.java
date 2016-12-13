import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ONPconverter {
	// Associativity constants for operators
	private static final int LEFT_ASSOC = 0;
	private static final int RIGHT_ASSOC = 1;

	// Supported operators
	private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>();
	static {
		// Map<"token", []{precendence, associativity}>
		OPERATORS.put("+", new int[] { 0, LEFT_ASSOC });
		OPERATORS.put("-", new int[] { 0, LEFT_ASSOC });
		OPERATORS.put("*", new int[] { 5, LEFT_ASSOC });
		OPERATORS.put("/", new int[] { 5, LEFT_ASSOC });
		OPERATORS.put("%", new int[] { 5, LEFT_ASSOC });
		OPERATORS.put("^", new int[] { 10, RIGHT_ASSOC });
	}

	private static boolean isOperator(String token) {
		return OPERATORS.containsKey(token);
	}

	private static boolean isAssociative(String token, int type) {
		if (!isOperator(token)) {
			throw new IllegalArgumentException("Invalid token: " + token);
		}
		if (OPERATORS.get(token)[1] == type) {
			return true;
		}
		return false;
	}

	private static final int cmpPrecedence(String token1, String token2) {
		if (!isOperator(token1) || !isOperator(token2)) {
			throw new IllegalArgumentException("Invalied tokens: " + token1
					+ " " + token2);
		}
		return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
	}

	public static String[] infixToRPN(String[] inputTokens) {
		ArrayList<String> out = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		// For all the input tokens read the next token
		for (String token : inputTokens) {
			if (isOperator(token)) {
				// If token is an operator (x) 
				while (!stack.empty() && isOperator(stack.peek())) {
					if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(
							token, stack.peek()) <= 0)
							|| (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(
									token, stack.peek()) < 0)) {
						out.add(stack.pop()); 	
						continue;
					}
					break;
				}
				// Push the new operator on the stack 
				stack.push(token);
			} else if (token.equals("(")) {
				stack.push(token); 	
			} else if (token.equals(")")) {
				while (!stack.empty() && !stack.peek().equals("(")) {
					out.add(stack.pop()); 
				}
				stack.pop(); 
			} else {
				out.add(token); 
			}
		}
		while (!stack.empty()) {
			out.add(stack.pop()); 
		}
		String[] output = new String[out.size()];
		return out.toArray(output);
	}
}
