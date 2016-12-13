import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	// program data
	StringBuilder sb = new StringBuilder();
	char [] array;
	List <String> separatedData = new ArrayList<String>();

	
	// GUI elements
	Button convert = new Button("Convert to ONP");
	TextField equation = new TextField();
	TextField converted = new TextField();
	final Text desc = new Text("Converted equation:");
	
	// layout
	Scene scene;
	Stage primaryStage;
	VBox vb = new VBox();
	StackPane root = new StackPane();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		this.scene = initializeScene();
		initializeHandlers();
		primaryStage.setTitle("LM - LAB 4_2");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main (String args[]) {
		launch(args);
	}
	
	private Scene initializeScene() {
		equation.setPromptText("Insert proper equation here...");
		converted.setPromptText("Converted equation will appear here...");
		vb.setSpacing(20);
		vb.setPadding(new Insets(20));
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(equation, convert, desc, converted);
		root.getChildren().addAll(vb);
		Scene scene = new Scene(root, 400, 400);
		return scene;
	}
	private void initializeHandlers() {
		
		convert.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validate(equation.getText())) {
					convertEquation();
				} else {
					sb = new StringBuilder();
					equation.clear();
					converted.clear();
					equation.setPromptText("Wrong equation, type another one!");
				}
			}
    		
    	});
    	
    }
	
	boolean validate(String s) {
		Pattern p = Pattern.compile("[\\d\\(\\)\\*\\/\\+\\-\\^]+");
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	void convertEquation () {
		String s = equation.getText();
		char array [] = s.toCharArray();
		
		
		for (int i = 0; i<array.length; i++) {
			char temp = array[i];
			if (String.valueOf(temp).matches("\\d")) {
				int newI = checkIfNextDec(array, i);
				if (!(newI == i)) {
					updateFor(i,newI,array);
					i = newI;
				} else {
					separatedData.add(String.valueOf(temp));
				}
			} else if (String.valueOf(temp).matches("\\-")) {
				
				int newI = checkIfNextDec(array, i);
				if (!(newI == i)) {
					updateFor(i,newI,array);
					i = newI;
				} else {
					separatedData.add(String.valueOf(temp));
				}
			} else {
				separatedData.add(String.valueOf(temp));
			}
		}

		int size = separatedData.size()-1;
		String response [] = ONPconverter.infixToRPN(separatedData.toArray(new String [size]));
		StringBuilder sb = new StringBuilder();
		for (String element : response) {
			sb.append(element+" ");
		}
		converted.clear();
		converted.setText(sb.toString());
		sb = new StringBuilder();
		separatedData.clear();
	}
	
	int checkIfNextDec(char array[], int i) {
		if (i + 1 >= array.length) {return i;}
		if (String.valueOf(array[++i]).matches("\\d")) {
			return checkIfNextDec(array, i);
		} else {
			return i-1;
		}
	}
	
	int checkIfMinus (char array[], int i) {
		String operator = "[\\d\\(\\*\\/\\+\\-\\^]";
		if (i == 0) { 
			do {
				i++;
			} while(String.valueOf(i).matches("\\d"));
			return --i;	
		}
		else if (String.valueOf(array[i-1]).matches(operator)) {
			do {
				i++;
			} while(String.valueOf(i).matches("\\d"));
			return --i;
		} else {
			return i;
		}
	}
	
	void updateFor(int i, int newI, char [] array) {
		sb = new StringBuilder();
		for (int x = i; x <= newI; x++) {
			sb.append(array[x]);
		}
		separatedData.add(sb.toString());
	}
	
}
