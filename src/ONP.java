import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ONP {
	static List<Character> stack = new ArrayList<Character>();
	static Pattern p;
	static Matcher m;
	static String get(Character c) {
		p = Pattern.compile("\\d");
		m = p.matcher(String.valueOf(c));
		if (m.matches()) { 
			return String.valueOf(c);}
		else if (c.equals('(')) {stack.add(0,c);}
		else if (c.equals(')')) { 
			StringBuilder sb = new StringBuilder();
			Character last = stack.get(0);
			while (!last.equals('(')) {
				sb.append(String.valueOf(stack.remove(0)));
				last = stack.get(0);
			}
			stack.remove(0);
			return sb.toString();
		} 
		// operators
		p = Pattern.compile("[\\+\\-\\*\\/\\^]");
		m = p.matcher(String.valueOf(c));
		if (m.matches()) {
			return lookStack(c);}
		else if (c.equals('#')) {
			StringBuilder sb = new StringBuilder();
			for (Character x : stack){
				sb.append(x);
			}
			stack.clear();
			return sb.toString();
		}
		return null;
	}
	
	static String lookStack (Character ch) {
		int charValue = getRank(ch);
		int lastIndex = 0;
		boolean throwOutput = false;
		for (int index = 0; index < stack.size(); index ++) {
			Character temp = stack.get(index);
			if (temp.equals('(')) { 
				break;}
			if (charValue < getRank(temp)) {
				throwOutput = true;
			}
			lastIndex = index;
		}
		if (throwOutput == false) {
			stack.add(0,ch);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0 ; i <= lastIndex; i++) {
				Character xxx = stack.remove(0);
				sb.append(xxx);
			}
			//stack.remove(0);
			stack.add(0,ch);
			return sb.toString();
		}
		return null;
	}
	

	static int getRank(Character c) {
		if (c.equals('+') || c.equals('-') || c.equals(')')) {return 1;}
		else if (c.equals('*') || c.equals('/')) {return 2;}
		else if (c.equals('^')) {return 3;}
		else return 0;
	}
}
