package reversi;

public class Pos {
	private int x;
	private int y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pos(Pos copy){
		this.x = copy.getX();
		this.y = copy.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public boolean equals(Pos p) {
		if ((this.x == p.getX()) && (this.y == p.getY()))
			return true;
		return false;
	}
	

}
