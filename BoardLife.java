class BoardLife {
	private boolean[][] array;
	private int width;
	private int height;

	public BoardLife() {
		this(8,8);
	}
	
	public BoardLife(int w, int h) {
		width = w;
		height = h;
		array = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				array[i][j] = false;
			}
		}
	}

	public boolean[][] getArray() {
		return array;
	}

	public void apply(RuleLife r) {
		boolean[][] orig = copy();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (orig[i][j]) {
					array[i][j] = r.getS()[r.neighbors(orig,i,j,width,height)];
				}
				if (!orig[i][j]) {
					array[i][j] = r.getB()[r.neighbors(orig,i,j,width,height)];
				}
			}
		}
	}

	private boolean[][] copy() {
		boolean[][] copy= new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				copy[i][j] = array[i][j];
			}
		}
		return copy;
	}

	private int i(boolean b) {
		return b ? 1:0;
	}

	public void flip(int i, int j) {
		array[i][j] = !array[i][j];
	}

	public String toString() {
		String rtn = "";
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				rtn += (i(array[i][j])) + " ";
			}
			rtn += "\n";
		}
		return rtn;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean get(int i, int j) {
		return array[i][j];
	}
}