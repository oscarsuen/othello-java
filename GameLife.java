public class GameLife {
	BoardLife b;
	RuleLife r;

	public GameLife() {
		this.b = new BoardLife();
		this.r = new RuleLife();
	}

	public GameLife(RuleLife r) {
		this.b = new BoardLife();
		this.r = r;
	}

	public GameLife(int w, int h, RuleLife r) {
		this.b = new BoardLife(w,h);
		this.r = r;
	}

	public GameLife(int w, int h, int[] b, int[] s) {
		this.b = new BoardLife(w,h);
		this.r = new RuleLife(b,s);
	}

	public void step() {
		b.apply(r);
	}

	public void flip(int i, int j) {
		b.flip(i,j);
	}

	public String toString() {
		return b.toString();
	}

	public int getWidth() {
		return b.getWidth();
	}

	public int getHeight() {
		return b.getHeight();
	}

	public boolean get(int i, int j) {
		return b.get(i,j);
	}
	
	public void setRule(String s){
		switch(s){
		case "LIFE":
			r = RuleLife.LIFE;
		case "REPLICATOR":
			r = RuleLife.REPLICATOR;
			break;
		case "SEEDS":
			r = RuleLife.SEEDS;
			break;
		case "LIFE_WO_DEATH":
			r = RuleLife.LIFE_WO_DEATH;
			break;
		case "LIFE_34":
			r = RuleLife.LIFE_34;
			break;
		case "DIAMOEBA":
			r = RuleLife.DIAMOEBA;
			break;
		case "TWO_BY_TWO":
			r = RuleLife.TWO_BY_TWO;
			break;
		case "DAY_NIGHT":
			r = RuleLife.DAY_NIGHT;
			break;
		case "HIGH_LIFE":
			r = RuleLife.HIGH_LIFE;
		case "MORLEY":
			r = RuleLife.MORLEY;
		default:
			r= RuleLife.DAY_NIGHT;
		}
		System.out.println("Changing Rule");
	}
	public boolean[][] getArray() {
		return b.getArray();
	}
}