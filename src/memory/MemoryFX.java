package memory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class MemoryFX extends Application{

	

	@Override
	public void start(Stage meinStage) throws Exception {
		
		System.out.println("new start");
		FlowPane rootNode = new MemoryFeld().initGUI(new FlowPane());
		Scene meinScene = new Scene(rootNode, 480, 560);
		meinStage.setTitle("Memory");
		meinStage.setScene(meinScene);
		meinStage.setResizable(false);
		meinStage.show();
		
		
		
	}
	
public static void main(String[] args) {
	launch(args);
		

	}

}
