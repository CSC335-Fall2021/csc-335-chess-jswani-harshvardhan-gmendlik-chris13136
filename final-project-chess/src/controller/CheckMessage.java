import java.io.Serializable;

public class CheckMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	

	public static final int WHITE=0;
	public static final int BLACK=1;
	
	private int x;
	private int y;
	private int color;
	
	public CheckMessage(int x, int y, int c) {
		this.x = x;
		this.y = y;

		this.color = c;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getColor() { return color; }
}