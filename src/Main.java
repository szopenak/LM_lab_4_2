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
		for (char c : array) {
			String response = ONP.get(c);
			if (!(response == null)) {sb.append(response);}
		}
		String last = ONP.get('#');
		if (!(last == null)) {sb.append(last);}
		converted.clear();
		converted.setText(sb.toString());
		sb = new StringBuilder();
	}
	
	
}
