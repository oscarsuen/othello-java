/*
* @Board.java
* @Authors: Stuart Rucker, Oscar Suen, Vinayak Kurup
*
* The entire board at any time
*/

class Board {
	private int width;
	private int height;
	private byte[][] array; //black = 1, white = -1, unfilled = 0
	private boolean player; //black true, white false

	public Board() {
		//Default constructor
		this(8, 8);
	}

	public Board(int w, int h) {
		//other constructor
		width = w;
		height = h;
		array = new byte[w][h];
		player = true;
		for (int i = 0; i < width; i++) {
			//iterates through array and sets all values to 0 (unfilled)
			for (int j = 0; j < height; j++) {
				array[i][j] = 0;
			}
		}
		place(4, 3);
		place(3, 3);
		place(3, 4);
		place(4, 4);
	}

	public byte get(int x, int y) {
		//Get's the value at a specific position on the board
		if (x >= 0 && x < width && y >= 0 && y < height) {
			return array[x][y];
		} else {
			return Byte.MAX_VALUE;
		}
	}

	public int getWidth() {
		//returns width of the board
		return width;
	}

	public int getHeight() {
		//returns height of the board
		return height;
	}

	public boolean getPlayer() {
		// AI or player
		return player;
	}

	public void setPlayer(boolean p) {
		//Sets the player
		player = p;
	}

	public byte getPlayeri() {
		return player ? (byte)1 : (byte)(-1);
	}

	public void place(int x, int y) {
		//in the byte array places a piece
		if (x == -1 && y == -1) {
			//unable to place
			player = !player;
			return;
		}
		if (array[x][y] == 0) {
			if (player) {
				array[x][y] = 1;
			} else if (!player) {
				array[x][y] = -1;
			}
			player = !player;
		} else {
			player = !player;
		}
	}

	public void revert(int x, int y) {
		//reverts value to 0
		array[x][y] = 0;
		player = !player;
	}

	public void flip(int x, int y) {
		//changes white to black and vice versa
		array[x][y] = (byte)(-array[x][y]);
	}

	public void set(int x, int y, byte b) {
		//sets the place to either white or black
		array[x][y] = b;
	}

	public Board copy() {
		//copies the  (including player)
		Board s = new Board(width, height);
		for (int x = 0; x < width; x ++) {
			//iterates through and copies the value from the cells
			for (int y = 0; y < height; y ++) {
				s.set(x, y, array[x][y]);
			}
		}
		s.setPlayer(getPlayer()); //copies player
		return s;
	}

	public String toString() {
		//String of the board
		String rtn = "";
		for (int j = 0; j < height; j++) {
			//iterates through board positions
			for (int i = 0; i < width; i++) {
				if (array[i][j] == 0) {
					rtn += "-";
				} else if (array[i][j] == 1) {
					rtn += "X";
				} else if (array[i][j] == -1) {
					rtn += "O";
				}
			}
			rtn += "\n"; //after each lines adds a new line
		}
		return rtn;
	}

	public boolean equals(Board b) {
		//compares the two boards if they're equal
		return array.equals(b.getArray());
	}

	public byte[][] getArray() {
		//returns the board array
		return array;
	}

	public double win() {
		boolean b = false;
		boolean w = false;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (array[i][j] == (byte)(1)) {
					b = true;
				} else if (array[i][j] == (byte)(-1)) {
					w = true;
				}
				if (b && w) {
					return 0;
				}
			}
		}
		if (b) {
			return Double.POSITIVE_INFINITY;
		}
		return Double.NEGATIVE_INFINITY;
	}

}
