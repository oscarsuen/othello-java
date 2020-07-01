//AI.java
/*
*
* @Authors: Vinayak Kurup, Oscar Suen, Stuart Rucker
* @Version: February 26, 2015
*
* AI.java--> uses GameStates to decide the best move to play at the time
*/

public class AI {

	private GameState current;

	public AI(Board init, int depth) {
		// Current board and depth to look forward
		current = new GameState(init, (byte)1, (byte)(-1), (byte)(-1));
		generateDepth(0, depth, current);
		negamaxab(current, (byte)(1), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public void generateDepth(int depth, int target, GameState root) {
		// calls recurisvely on itsef
		if (depth == target) { //base case (terminates when depth = target)
			return;
		}
		for (GameState child : root.findAllChildren()) { //goes through each of the children of the GameState
			generateDepth(depth + 1, target, child);
		}
	}

	public Board bestMove(GameState root) {
		//Figures out the best move to play
		double m = Double.NEGATIVE_INFINITY; //init m
		Board b = null;
		for (GameState c : root.getChildren()) {
			//Goes through all the children and ends with the GameState
			//that is the greatest value (best move)
			if (m < c.getValue()) { //b/c m is greatest val, will be true first time
				m = c.getValue();
				b = c.getBoard();
				current = c;
			}
		}
		return b;
	}

	public GameState getCurrent() {
		//returns current GameState
		return current;
	}

	public void update(Board b, byte x, byte y) {
		//Updates the board and the right position
		//Checks through all the gamestates and sets
		//The current GameState as the best move option
		for (GameState s : current.getChildren()) {
			if (s.getX() == x && s.getY() == y) {
				current = s;
				return;
			}
		}
		System.out.println("No children equal to current board position");
		current = current.getChildren().get(0);
	}

	public void addLayers(GameState root, int i) {
		if (root.getChildren().isEmpty()) {
			generateDepth(0, i, root);
			return;
		}
		for (GameState s : root.getChildren()) {
			addLayers(s, i);
		}
	}

	public double negamaxab(GameState root, byte player, double alpha, double beta) {
		//v4 negamax with pruning
		if (root.isTerminal()) {
			return player * root.setValue(root.getScore());
		}
		double bestValue = Double.NEGATIVE_INFINITY;
		for (GameState s : root.getChildren()) {
			double val = -negamaxab(s, (byte)(-player), -beta, -alpha);
			bestValue = Math.max(bestValue, val);
			alpha = Math.max(alpha, val);
			if (alpha >= beta) {
				break;
			}
		}
		return root.setValue(bestValue);
	}

	/*public double negamax(GameState root, byte player) {
	    //V3 negamax w/o a-b pruning
	    if (root.isTerminal()) {
	        return player * root.setValue(root.getScore());
	    }
	    double bestValue = Double.NEGATIVE_INFINITY;
	    for (GameState s : root.getChildren()) {
	        double val = -negamax(s, (byte)(-player));
	        bestValue = Math.max(bestValue, val);
	    }
	    return root.setValue(bestValue);
	}*/

	/*public double negamin(GameState root, byte player) {
	    //V3 negamin w/o a-b pruning
	    if (root.isTerminal()) {
	        return player * root.setValue(root.getScore());
	    }
	    double bestValue = Double.MAX_VALUE;
	    for (GameState s : root.getChildren()) {
	        double val = -negamin(s, (byte)(-player));
	        bestValue = Math.min(bestValue, val);
	    }
	    return root.setValue(bestValue);
	}*/

	/*public double computeValue(GameState root) {
	    //V1 computed values by averaging bottom nodes
	    if (root.getChildren().isEmpty()) {
	        root.addValue(root.getScore());
	        //System.out.println("Leaf Score: " + root.getScore());
	        return root.getScore();
	    } else {
	        int rtn = 0;
	        for (GameState s : root.getChildren()) {
	            root.addValue(computeValue(s));
	        }
	        root.avg();
	        //System.out.println("Internal Value: " + root.getValue());
	        return root.getValue();
	    }
	}*/

	/*public double minimax(GameState root, boolean mplayer) {
	    //V2 basic minimax, superceded by negamin
	    //Copy of wikipedia algorithm translated to java
	    if (root.isTerminal()) {//change to isTerminal later
	        root.setValue(root.getScore());
	        return root.getScore();
	    }
	    if (mplayer) {
	        double bestValue = Double.MIN_VALUE;
	        for (GameState s : root.getChildren()) {
	            double val = minimax(s, false);
	            bestValue = Math.max(bestValue, val);
	        }
	        root.setValue(bestValue);
	        return bestValue;
	    } else {
	        double bestValue = Double.MAX_VALUE;
	        for (GameState s : root.getChildren()) {
	            double val = minimax(s, true);
	            bestValue = Math.min(bestValue, val);
	        }
	        root.setValue(bestValue);
	        return bestValue;
	    }
	}*/

}

