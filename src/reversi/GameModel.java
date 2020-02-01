package reversi;

import java.util.ArrayList;
import java.util.List;

public abstract class GameModel {
	public enum BoardColor{None, Black, White};
	public interface IndexUpdate {
		int update(int i);
	}
	public static IndexUpdate increment = (i)->{return i + 1;};
	public static IndexUpdate decrement = (i)->{return i - 1;};
	public static IndexUpdate identity = (i)->{return i;};
	
	private List <List<BoardColor>> checkerboard;
	private int halfSize;
	private int count;
	private BoardColor prevColor;
	private BoardColor winner;
	
	public abstract void movePiece(int i, int j, BoardColor color);
	
	public GameModel(int halfSize) {
		this.halfSize = halfSize;
		this.checkerboard = new ArrayList<>();
		for (int i = 0; i < 2 * this.halfSize; i++) {
			List<BoardColor> newList = new ArrayList<>(2 * this.halfSize);
			for (int j = 0; j < 2 * this.halfSize; j++) {
				newList.add(BoardColor.None);
			}
			checkerboard.add(newList);
		}
		this.checkerboard.get(halfSize-1).set(halfSize-1, BoardColor.Black);
		this.checkerboard.get(halfSize-1).set(halfSize, BoardColor.White);
		this.checkerboard.get(halfSize).set(halfSize-1, BoardColor.White);
		this.checkerboard.get(halfSize).set(halfSize, BoardColor.Black);
		this.prevColor = BoardColor.White;
		this.winner = BoardColor.None;
		this.count = 4 * this.halfSize * this.halfSize;
		this.count -= 4;
	}
	private BoardColor boardGet(int i, int j) {
		if (i < 0 ||
			i >= 2 * halfSize ||
			j < 0 ||
			j >= 2 * halfSize) {
			return BoardColor.None;
		}
		return this.checkerboard.get(i).get(j);
	}
	
	private void boardSet(int i, int j, BoardColor color) {
		if (i < 0 ||
			i >= 2 * halfSize ||
			j < 0 ||
			j >= 2 * halfSize) {
			return;
		}
		this.checkerboard.get(i).set(j, color);
		movePiece(i, j, color);
		return;
	}
	private void checkWinner() {
		if (count == 0 && this.winner == BoardColor.None) {
			int white = 0;
			int black = 0;
			for (int i = 0; i < 2 * this.halfSize; i++) {
				for (int j = 0; j < 2 * this.halfSize; j++) {
					if (boardGet(i, j) == BoardColor.Black)
						black++;
					if (boardGet(i, j) == BoardColor.White)
						white++;
				}
			}
			if (white > black) {
				this.winner = BoardColor.White;
			} else if (black > white) {
				this.winner = BoardColor.Black;
			}
		}
		
	}
	private boolean recFlip(BoardColor startColor, int i, int j, IndexUpdate iUpdate, IndexUpdate jUpdate) {
		if (boardGet(i, j) == BoardColor.None) {
			return false;
		} else if (boardGet(i, j) != startColor) {
			boolean result = recFlip(startColor, iUpdate.update(i), jUpdate.update(j), iUpdate, jUpdate);
			if (result) {
				boardSet(i, j, startColor);
			}
			return result;
		} else {
			return true;
		}
	}
	private boolean flip(BoardColor startColor, int i, int j, IndexUpdate iUpdate, IndexUpdate jUpdate) {
		if (startColor == boardGet(iUpdate.update(i), jUpdate.update(j)))
			return false;
		return recFlip(startColor, iUpdate.update(i), jUpdate.update(j), iUpdate, jUpdate);
	}
	
	public boolean moves(BoardColor color, int i, int j) {
		if (color == this.prevColor ||
				i < 0 ||
				i >= 2 * halfSize ||
				j < 0 ||
				j >= 2 * halfSize) {
			return false;
		}
		// Check current location
		if (boardGet(i, j) != BoardColor.None) {
			return false;
		}
		
		boolean validLoc = false;
		// Change color
		// Up 
		validLoc |= flip(color, i, j, decrement, identity);
		// Up-Right
		validLoc |= flip(color, i, j, decrement, increment);
		// Right
		validLoc |= flip(color, i, j, identity, increment);
		// Right-Down
		validLoc |= flip(color, i, j, increment, increment);
		// Down
		validLoc |= flip(color, i, j, increment, identity);
		// Down-Left
		validLoc |= flip(color, i, j, increment, decrement);
		// Left
		validLoc |= flip(color, i, j, identity, decrement);
		// Left-Up
		validLoc |= flip(color, i, j, decrement, decrement);
		
		if (validLoc) {
			boardSet(i, j, color);
			this.prevColor = color;
			this.count--;
			this.checkWinner();
			return true;
		} else {
			return false;
		}
	}
	public BoardColor getWinner() {
		if (this.count > 0) {
			return null;
		}
		return this.winner;
	}
	public static void main(String[] args) {
		return;
	}
}
