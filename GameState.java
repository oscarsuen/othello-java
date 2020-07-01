/*
*
* @Authors: Stuart Rucker, Oscar Suen, Vinayak Kurup
* @Version: February 23, 2015
*
* GameState: State of the board at any time
*/

import java.util.LinkedList;

public class GameState {
	private Board b;
	private LinkedList<GameState> children; //pointers to the subsequent possible gamestates
	private double v; //"score" of board-- how good the board is
	private byte color;
	private byte moveX;
	private byte moveY;

	public GameState(Board input_board, byte color1, byte x, byte y) {
		// Constructor for new GameState
		// Initializes everything
		children = new LinkedList<GameState>();
		b = input_board;
		v = Double.NaN;
		color = color1;
		moveX = x;
		moveY = y;
	}


	public LinkedList<GameState> findAllChildren() {
		//Finds all of the children Gamestates (possible moves from current gamestate)
		// check each play location X,Y
		// also updates children (we need to remember that)
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				if (b.get(x, y) == 0) { // if an empty square
					Board a = getNextBoard(b, x, y, color);
					if (a != null) {
						children.add(new GameState(a, (byte)(-color), (byte)x, (byte)y));
					}
				}
			}
		}
		if (children.isEmpty()) {
			children.add(new GameState(b, (byte)(-color), (byte)(-1), (byte)(-1)));
		}
		return children;
	}


	public double getScore() {
		//Returns a number (helps AI decide next move) on how good the board is
		double sum = 0;
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				sum += b.get(x, y);
			}
		}
		sum += 3 * b.get(0, 0);
		sum += 3 * b.get(b.getWidth() - 1, 0);
		sum += 3 * b.get(0, b.getHeight() - 1);
		sum += 3 * b.get(b.getWidth() - 1, b.getHeight() - 1);
		sum += Math.random() / 2 - 0.5;
		sum += b.win();
		return sum;
	}


	public Board getNextBoard(Board c, int x, int y, byte color) {
		// board configuration after playing a tile on board c, at position x, y
		Board r = c.copy();
		if (Engine.capture(r, x, y, color)) {
			return r;
		} else {
			return null;
		}
	}

	public boolean isTerminal() {
		//Needs implementation of whether node is win/loss for minimax
		return children.isEmpty();
	}

	public LinkedList<GameState> getChildren() {
		return children;
	}

	public Board getBoard() {
		return b;
	}

	public double getValue() {
		return v;
	}

	public double setValue(double va) {
		v = va;
		return v;
	}

	public byte getX() {
		return moveX;
	}

	public byte getY() {
		return moveY;
	}
}
