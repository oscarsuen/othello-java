/*
*
* @Authors: Vinayak Kurup, Oscar Suen, Stuart Rucker
* @Version: February 26, 2015
*
* Game wrapper that performs gameplay
*
*/
class Game {
	private Board b;
	private AI CPU;

	public Game() {
		this(8, 8, 6);
	}

	public Game(int w, int h, int l) {
		b = new Board(w, h);
		CPU = new AI(b, l);
	}

	public boolean play(int i, int j) {
		if (b.get(i, j) == 0) {
			if (Engine.capture(b, i, j, (byte)1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void print() {
		System.out.println(b.toString());
	}

	public Board getBoard() {
		return b;
	}

	public boolean CPUPLay(byte x, byte y) {
		boolean played = false;
		CPU.update(b, x, y);
		CPU.negamaxab(CPU.getCurrent(), (byte)(1), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		b = CPU.bestMove(CPU.getCurrent());
		CPU.addLayers(CPU.getCurrent(), 2);
		return played;
	}

	public int getScore(byte c) {
		int score = 0;
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				if (b.get(x, y) == c) {
					score += 1;
				}
			}
		}
		return score;
	}

	public boolean canPLay(byte c) {
		return Engine.canPLay(b, c);
	}
}
