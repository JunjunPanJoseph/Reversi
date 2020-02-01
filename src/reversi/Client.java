package reversi;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
	ReversiController controller;
	@Override
	public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        controller = new ReversiController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        controller.gameStart(6);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		System.out.println("Main");
		launch(args);
	}
}
