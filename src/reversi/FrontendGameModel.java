package reversi;


import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import reversi.GameModel.BoardColor;

public class FrontendGameModel extends GameModel {
	private Image imageNone;
	private Image imageBlack;
	private Image imageWhite;
	private GridPane grid;
	private List<List<ImageView>> nodeBoard;
	
	private BoardColor currColor; 
	
	public FrontendGameModel(int halfSize, GridPane grid) {
		super(halfSize);
		this.grid = grid;
		this.nodeBoard = new ArrayList<>();
		this.imageNone = new Image("file:/../images/none.png");
		this.imageBlack = new Image("file:/../images/black.png");
		this.imageWhite = new Image("file:/../images/white.png");
		
		this.currColor = BoardColor.Black;
		
		for (int i = 0; i < 2 * halfSize; i++) {
			nodeBoard.add(new ArrayList<ImageView>());
			for (int j = 0; j < 2 * halfSize; j++) {
				ImageView newLoc = new ImageView(imageNone);
				nodeBoard.get(i).add(newLoc);
				grid.add(newLoc, i, j);
				int x = i;
				int y = j;
				newLoc.setOnMouseClicked(new EventHandler<MouseEvent>() { 
			       @Override 
			       public void handle(MouseEvent event) {
			    	   if (moves(currColor, x, y)) {
			    	   		if (currColor == BoardColor.Black) {
			    	   			currColor = BoardColor.White;
			    	   		} else {
			    	   			currColor = BoardColor.Black;
			    	   		}
			    	   	}			    	   	
			       }
				});
			}
		}
		
		movePiece(halfSize-1, halfSize-1, BoardColor.Black);
		movePiece(halfSize-1, halfSize, BoardColor.White);
		movePiece(halfSize, halfSize-1, BoardColor.White);
		movePiece(halfSize, halfSize, BoardColor.Black);
		
	}

	@Override
	public void movePiece(int i, int j, BoardColor color) {
		Image currImage = null;
		switch (color) {
		case None:
			currImage = imageNone;
			break;
		case Black:
			currImage = imageBlack;
			break;
		case White:
			currImage = imageWhite;
			break;
		}
		nodeBoard.get(i).get(j).setImage(currImage);
	}

}
