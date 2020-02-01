package reversi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ReversiController {
	private FrontendGameModel game;
	
	public ReversiController(){
	}
	@FXML
	private Button passButton;
	@FXML
	private Text currentPlayer;
	@FXML
	private GridPane grid;
	
	public void gameStart(int size) {
		// TODO Auto-generated method stub
		grid.getChildren().clear();
		this.game = new FrontendGameModel(size, grid);
		
		
	}
}
